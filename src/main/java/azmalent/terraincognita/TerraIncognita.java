package azmalent.terraincognita;

import azmalent.cuneiform.lib.compat.ModCompatUtil;
import azmalent.terraincognita.client.event.ClientEventHandler;
import azmalent.terraincognita.common.event.EventHandler;
import azmalent.terraincognita.common.registry.*;
import azmalent.terraincognita.common.integration.ModIntegration;
import azmalent.terraincognita.common.world.ModTrees;
import azmalent.terraincognita.network.NetworkHandler;
import azmalent.terraincognita.proxy.ClientProxy;
import azmalent.terraincognita.proxy.ServerProxy;
import azmalent.terraincognita.proxy.IProxy;
import net.minecraft.block.Blocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.WorldGenRegistries;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.FancyFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.FancyTrunkPlacer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.OptionalInt;

@Mod(TerraIncognita.MODID)
public class TerraIncognita {
    public static final String MODID = "terraincognita";
    public static final Logger LOGGER = LogManager.getLogger(MODID);

    public static final ItemGroup TAB = new ItemGroup("terraIncognitaTab") {
        @Override
        public ItemStack createIcon() {
            return new ItemStack(ModItems.MOD_ICON.get());
        }
    };

    public static final IProxy PROXY = DistExecutor.safeRunForDist(() -> ClientProxy::new, () -> ServerProxy::new);

    public TerraIncognita() {
        TIConfig.init();

        IEventBus bus = FMLJavaModLoadingContext.get().getModEventBus();
        ModBiomes.BIOMES.register(bus);
        ModBlocks.BLOCKS.register(bus);
        ModContainers.CONTAINERS.register(bus);
        ModEffects.EFFECTS.register(bus);
        ModEntities.ENTITIES.register(bus);
        ModFeatures.FEATURES.register(bus);
        ModItems.ITEMS.register(bus);
        ModLootModifiers.LOOT_MODIFIERS.register(bus);
        ModParticles.PARTICLES.register(bus);
        ModRecipes.RECIPES.register(bus);
        ModSounds.SOUNDS.register(bus);
        ModTileEntities.TILE_ENTITIES.register(bus);
        ModTreeDecorators.TREE_DECORATORS.register(bus);
        ModBanners.register();

        ModCompatUtil.initModProxies(ModIntegration.class, MODID);
        ModIntegration.QUARK.register(bus);
        ModIntegration.SIMPLY_TEA.register(bus);
        ModIntegration.UPGRADE_AQUATIC.register(bus);

        NetworkHandler.registerMessages();

        EventHandler.registerListeners();
        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> ClientEventHandler::registerListeners);
    }

    public static ResourceLocation prefix(String name) {
        return new ResourceLocation(MODID, name);
    }
}
