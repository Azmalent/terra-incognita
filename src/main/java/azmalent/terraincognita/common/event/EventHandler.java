package azmalent.terraincognita.common.event;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.block.CaltropsBlock;
import azmalent.terraincognita.common.data.ModItemTags;
import azmalent.terraincognita.common.inventory.BasketStackHandler;
import azmalent.terraincognita.common.item.block.BasketItem;
import azmalent.terraincognita.common.registry.ModBiomes;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModEffects;
import azmalent.terraincognita.common.registry.ModEntities;
import azmalent.terraincognita.common.registry.ModRecipes;
import azmalent.terraincognita.common.world.ModOres;
import azmalent.terraincognita.common.world.ModTrees;
import azmalent.terraincognita.common.world.ModVegetation;
import azmalent.terraincognita.util.ColorUtil;
import net.minecraft.block.BlockState;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.items.ItemHandlerHelper;

@SuppressWarnings("deprecation")
public class EventHandler {
    public static void registerListeners() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(EventHandler::setup);

        MinecraftForge.EVENT_BUS.addListener(EventHandler::onUpdateRecipes);
        MinecraftForge.EVENT_BUS.addListener(EventHandler::onPlayerUseItem);
        MinecraftForge.EVENT_BUS.addListener(EventHandler::onItemPickup);

        MinecraftForge.EVENT_BUS.addListener(FuelHandler::getBurnTime);
        MinecraftForge.EVENT_BUS.addListener(TradeHandler::setupWandererTrades);
        MinecraftForge.EVENT_BUS.addListener(LootHandler::onLoadLootTable);

        BiomeHandler.registerListeners();
        BonemealHandler.registerListeners();
    }

    public static void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(ModVegetation::configureFeatures);
        event.enqueueWork(ModTrees::configureFeatures);
        event.enqueueWork(ModOres::configureFeatures);
        event.enqueueWork(ModBiomes::registerBiomes);

        ModBlocks.initToolInteractions();
        ModBlocks.initFlammability();
        FuelHandler.initFuelValues();
        ModEntities.registerAttributes();
        ModEntities.registerSpawns();
        ModRecipes.initCompostables();
    }

    public static void onUpdateRecipes(RecipesUpdatedEvent event) {
        for (ICraftingRecipe recipe : event.getRecipeManager().getRecipesForType(IRecipeType.CRAFTING)) {
            ColorUtil.saveFlowerDyeRecipe(recipe);
        }
    }

    public static void onPlayerUseItem(LivingEntityUseItemEvent.Start event) {
        if (!(event.getEntity() instanceof PlayerEntity)) return;

        PlayerEntity player = (PlayerEntity) event.getEntityLiving();
        EffectInstance effect = player.getActivePotionEffect(ModEffects.STICKY_MOUTH.get());
        if (effect != null && event.getItem().isFood()) {
            int multiplier = 2 << effect.getAmplifier();
            event.setDuration(event.getDuration() * multiplier);
        }
    }

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
