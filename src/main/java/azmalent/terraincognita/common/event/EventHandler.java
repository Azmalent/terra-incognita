package azmalent.terraincognita.common.event;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.init.*;
import azmalent.terraincognita.util.FlowerColorMap;
import azmalent.terraincognita.common.world.ModVegetation;
import net.minecraft.entity.merchant.villager.VillagerTrades;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.crafting.ICraftingRecipe;
import net.minecraft.item.crafting.IRecipeType;
import net.minecraft.potion.EffectInstance;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.village.WandererTradesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

public class EventHandler {
    public static void registerListeners() {
        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        bus.addListener(EventHandler::setup);

        if (TIConfig.Food.taffy.get()) {
            MinecraftForge.EVENT_BUS.addListener(EventHandler::onSetupTrades);
            MinecraftForge.EVENT_BUS.addListener(EventHandler::onPlayerUseItem);
        }

        if (TIConfig.Flora.flowerBand.get()) {
            MinecraftForge.EVENT_BUS.addListener(EventHandler::onUpdateRecipes);
        }

        BiomeHandler.registerListeners();
        LootHandler.registerListeners();
        BonemealHandler.registerListeners();
    }

    public static void setup(FMLCommonSetupEvent event) {
        event.enqueueWork(() -> {
            ModVegetation.configureFeatures();
            ModBiomes.registerBiomes();
            ModBiomes.registerBiomeTypes();
            ModRecipes.registerComposterRecipes();
        });
    }

    public static void onSetupTrades(WandererTradesEvent event) {
        event.getRareTrades().add(new VillagerTrades.ItemsForEmeraldsTrade(ModItems.TAFFY.get(), 2, 1, 1, 2));
    }

    public static void onUpdateRecipes(RecipesUpdatedEvent event) {
        for (ICraftingRecipe recipe : event.getRecipeManager().getRecipesForType(IRecipeType.CRAFTING)) {
            FlowerColorMap.getFlowerColorFromRecipe(recipe);
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
}
