package azmalent.terraincognita.common.event;

import azmalent.terraincognita.common.ModTweaks;
import azmalent.terraincognita.common.data.ModItemTags;
import azmalent.terraincognita.common.integration.theoneprobe.ButterflyProvider;
import azmalent.terraincognita.common.inventory.BasketStackHandler;
import azmalent.terraincognita.common.item.block.BasketItem;
import azmalent.terraincognita.common.recipe.WreathRecipe;
import azmalent.terraincognita.common.registry.*;
import azmalent.terraincognita.common.world.ModConfiguredFeatures;
import net.minecraft.block.Block;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.potion.EffectInstance;
import net.minecraft.tags.ItemTags;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
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

        MinecraftForge.EVENT_BUS.addGenericListener(Block.class, IdRemappingHandler::onMissingBlockMappings);
        MinecraftForge.EVENT_BUS.addGenericListener(Item.class, IdRemappingHandler::onMissingItemMappings);

        MinecraftForge.EVENT_BUS.addListener(EventHandler::onUpdateRecipes);
        MinecraftForge.EVENT_BUS.addListener(EventHandler::onPlayerUseItem);
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
        ModEntities.registerAttributes();
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
        for (ICraftingRecipe recipe : event.getRecipeManager().getRecipesForType(IRecipeType.CRAFTING)) {
            List<Ingredient> ingredients = recipe.getIngredients();
            if (ingredients.size() != 1) continue;

            ItemStack[] matchingStacks = ingredients.get(0).getMatchingStacks();
            for (int i = 0, n = matchingStacks.length; i < n; i++) {
                Item input = matchingStacks[i].getItem();
                Item output = recipe.getRecipeOutput().getItem();

                if (input.isIn(ItemTags.SMALL_FLOWERS) && output.isIn(Tags.Items.DYES)) {
                    WreathRecipe.FLOWER_TO_DYE_MAP.put(input, (DyeItem) output.getItem());
                }
            }
        }
    }

    //Eating speed adjustment
    public static void onPlayerUseItem(LivingEntityUseItemEvent.Start event) {
        if (!(event.getEntity() instanceof PlayerEntity)) return;

        PlayerEntity player = (PlayerEntity) event.getEntityLiving();
        EffectInstance effect = player.getActivePotionEffect(ModEffects.STICKY_MOUTH.get());
        if (effect != null && event.getItem().isFood()) {
            int multiplier = 2 << effect.getAmplifier();
            event.setDuration(event.getDuration() * multiplier);
        }
    }

    //Basket auto-pickup
    public static void onItemPickup(EntityItemPickupEvent event) {
        if (event.isCanceled() || event.getResult() == Event.Result.ALLOW) {
            return;
        }

        ItemEntity itemEntity = event.getItem();
        ItemStack stack = itemEntity.getItem().copy();
        PlayerEntity player = event.getPlayer();
        ItemStack basket = BasketItem.getBasketInHand(player);

        if (basket != null && !stack.isEmpty() && stack.getItem().isIn(ModItemTags.BASKET_STORABLE)) {
            BasketStackHandler stackHandler = BasketItem.getStackHandler(basket);
            ItemStack remainingStack = ItemHandlerHelper.insertItemStacked(stackHandler, stack, false);

            int numPickedUp = stack.getCount() - remainingStack.getCount();
            if (numPickedUp > 0) {
                event.setCanceled(true);
                itemEntity.getItem().shrink(numPickedUp);

                if (!itemEntity.isSilent()) {
                    itemEntity.world.playSound(player, player.getPosX(), player.getPosY(), player.getPosZ(),
                        SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F,
                        ((itemEntity.world.rand.nextFloat() - itemEntity.world.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F
                    );
                }

                player.onItemPickup(itemEntity, numPickedUp);
                player.openContainer.detectAndSendChanges();
            }
        }
    }
}
