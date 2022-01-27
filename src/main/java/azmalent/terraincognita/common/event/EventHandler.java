package azmalent.terraincognita.common.event;

import azmalent.cuneiform.lib.util.ItemUtil;
import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.ModTweaks;
import azmalent.terraincognita.common.data.ModItemTags;
import azmalent.terraincognita.common.entity.IBottleableEntity;
import azmalent.terraincognita.common.integration.theoneprobe.ButterflyProvider;
import azmalent.terraincognita.common.inventory.BasketStackHandler;
import azmalent.terraincognita.common.item.BottledEntityItem;
import azmalent.terraincognita.common.item.block.BasketItem;
import azmalent.terraincognita.common.recipe.WreathRecipe;
import azmalent.terraincognita.common.registry.*;
import azmalent.terraincognita.common.world.ModConfiguredFeatures;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.InterModComms;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.List;

public class EventHandler {
    public static void registerListeners() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(EventHandler::setup);
        bus.addListener(EventHandler::sendIMCMessages);

        if (TIConfig.Flora.wreath.get()) {
            MinecraftForge.EVENT_BUS.addListener(EventHandler::onUpdateRecipes);
        }

        MinecraftForge.EVENT_BUS.addListener(ModEntities::onAttributeCreation);
        MinecraftForge.EVENT_BUS.addListener(EventHandler::onPlayerUseItem);
        MinecraftForge.EVENT_BUS.addListener(EventHandler::onPlayerInteract);
        MinecraftForge.EVENT_BUS.addListener(EventHandler::onItemPickup);

        MinecraftForge.EVENT_BUS.addListener(BiomeHandler::onLoadBiome);
        MinecraftForge.EVENT_BUS.addListener(LootHandler::onLoadLootTable);
        MinecraftForge.EVENT_BUS.addListener(TradeHandler::setupWandererTrades);

        BonemealHandler.registerListeners();
    }

    public static void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(ModConfiguredFeatures::registerFeatures);
        event.enqueueWork(ModBiomes::registerBiomes);

        ModBlocks.initToolInteractions();
        ModBlocks.initFlammability();
        ModItems.initFuelValues();
        ModEntities.registerSpawns();
        ModRecipes.initCompostables();
        ModTweaks.modifyFlowerGradients();
    }

    public static void sendIMCMessages(InterModEnqueueEvent event) {
        if (ModList.get().isLoaded("theoneprobe")) {
            InterModComms.sendTo("theoneprobe", "getTheOneProbe", ButterflyProvider::new);
        }
    }

    //Build flower to dye map for the wreath recipe
    public static void onUpdateRecipes(RecipesUpdatedEvent event) {
        for (CraftingRecipe recipe : event.getRecipeManager().getAllRecipesFor(RecipeType.CRAFTING)) {
            List<Ingredient> ingredients = recipe.getIngredients();
            if (ingredients.size() != 1) continue;

            for (ItemStack input : ingredients.get(0).getItems()) {
                ItemStack output = recipe.getResultItem();

                if (input.is(ItemTags.SMALL_FLOWERS) && output.is(Tags.Items.DYES)) {
                    WreathRecipe.FLOWER_TO_DYE_MAP.put(input.getItem(), (DyeItem) output.getItem());
                }
            }
        }
    }

    //Eating speed adjustment
    public static void onPlayerUseItem(LivingEntityUseItemEvent.Start event) {
        if (!(event.getEntity() instanceof Player)) return;

        Player player = (Player) event.getEntityLiving();
        MobEffectInstance effect = player.getEffect(ModEffects.STICKY_MOUTH.get());
        if (effect != null && event.getItem().isEdible()) {
            int multiplier = 2 << effect.getAmplifier();
            event.setDuration(event.getDuration() * multiplier);
        }
    }

    //Entity bottling
    public static void onPlayerInteract(PlayerInteractEvent.EntityInteractSpecific event) {
        if (!(event.getTarget() instanceof LivingEntity living) || event.getWorld().isClientSide) {
            return;
        }

        Player player = event.getPlayer();
        InteractionHand hand = event.getHand();
        ItemStack heldStack = player.getItemInHand(hand);

        if (heldStack.getItem() == Items.GLASS_BOTTLE && living instanceof IBottleableEntity && living.isAlive()) {
            Level world = event.getWorld();

            heldStack.shrink(1);

            ItemStack bottle = new ItemStack(((IBottleableEntity) living).getBottledItem());
            if (living.hasCustomName()) {
                bottle.setHoverName(living.getCustomName());
            }

            BottledEntityItem.setBottledEntity(bottle, living);
            living.discard();
            world.playSound(player, event.getPos(), SoundEvents.BOTTLE_FILL_DRAGONBREATH, SoundSource.NEUTRAL, 1.0f, 1.0f);
            player.awardStat(Stats.ITEM_USED.get(Items.GLASS_BOTTLE));

            ItemUtil.giveStackToPlayer(player, bottle, hand);
            player.swing(hand);

            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.CONSUME);
        }
    }

    //Basket auto-pickup
    public static void onItemPickup(EntityItemPickupEvent event) {
        if (event.isCanceled() || event.getResult() == Event.Result.ALLOW) {
            return;
        }

        ItemEntity itemEntity = event.getItem();
        ItemStack stack = itemEntity.getItem().copy();
        Player player = event.getPlayer();
        ItemStack basket = BasketItem.getBasketInHand(player);

        if (basket != null && !stack.isEmpty() && stack.is(ModItemTags.BASKET_STORABLE)) {
            BasketStackHandler stackHandler = BasketItem.getStackHandler(basket);
            ItemStack remainingStack = ItemHandlerHelper.insertItemStacked(stackHandler, stack, false);

            int numPickedUp = stack.getCount() - remainingStack.getCount();
            if (numPickedUp > 0) {
                event.setCanceled(true);
                itemEntity.getItem().shrink(numPickedUp);

                if (!itemEntity.isSilent()) {
                    itemEntity.level.playSound(player, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F,
                        ((itemEntity.level.random.nextFloat() - itemEntity.level.random.nextFloat()) * 0.7F + 1.0F) * 2.0F
                    );
                }

                player.take(itemEntity, numPickedUp);
                player.containerMenu.broadcastChanges();
            }
        }
    }
}
