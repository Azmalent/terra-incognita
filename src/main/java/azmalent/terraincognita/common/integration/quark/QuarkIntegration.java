package azmalent.terraincognita.common.integration.quark;

import azmalent.cuneiform.lib.compat.ModProxyImpl;
import azmalent.cuneiform.lib.registry.BlockEntry;
import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.client.event.ColorHandler;
import azmalent.terraincognita.common.event.FuelHandler;
import azmalent.terraincognita.common.init.ModBlocks;
import azmalent.terraincognita.common.init.ModRecipes;
import com.google.common.collect.Sets;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import vazkii.quark.content.client.module.ChestSearchingModule;

import java.util.Set;

@ModProxyImpl("quark")
public class QuarkIntegration implements IQuarkIntegration {
    private QuarkWoodBlockSet APPLE;
    private BlockEntry BLOSSOMING_APPLE_LEAF_CARPET;

    private Set<QuarkWoodBlockSet> WOOD_BLOCK_SETS = Sets.newHashSet();

    @Override
    public boolean matchesItemSearch(ItemStack stack) {
        return ChestSearchingModule.namesMatch(stack);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void register(IEventBus bus) {
        if (TIConfig.Trees.apple.get()) {
            APPLE = new QuarkWoodBlockSet(ModBlocks.HELPER, "apple", MaterialColor.WOOD, MaterialColor.ORANGE_TERRACOTTA);
            BLOSSOMING_APPLE_LEAF_CARPET = ModBlocks.HELPER.newBuilder("blossoming_apple_leaf_carpet", LeafCarpetBlock::new).build();
            WOOD_BLOCK_SETS.add(APPLE);
        }

        bus.addListener(this::setup);
    }

    public void setup(FMLCommonSetupEvent event) {
        for (QuarkWoodBlockSet set : WOOD_BLOCK_SETS) {
			WoodenPostBlock post = (WoodenPostBlock) set.POST.getBlock();
			post.strippedBlock = set.STRIPPED_POST.getBlock();
        
            set.initFuelValues(FuelHandler.fuelValues);
            set.initFlammability();
            ModRecipes.registerCompostable(set.LEAF_CARPET, 0.2f);
        }

        ModRecipes.registerCompostable(BLOSSOMING_APPLE_LEAF_CARPET, 0.2f);
    }

    @OnlyIn(Dist.CLIENT)
    public void registerBlockColorHandlers(ColorHandlerEvent.Block event) {
        BlockColors colors = event.getBlockColors();

        if(APPLE != null) {
            colors.register((state, reader, pos, color) -> ColorHandler.APPLE_LEAVES_COLOR, APPLE.LEAF_CARPET.getBlock(), BLOSSOMING_APPLE_LEAF_CARPET.getBlock(), APPLE.HEDGE.getBlock());
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void registerItemColorHandlers(ColorHandlerEvent.Item event) {
        ItemColors colors = event.getItemColors();

        if (APPLE != null) {
            colors.register((stack, index) -> index > 0 ? -1 : ColorHandler.APPLE_LEAVES_COLOR, APPLE.LEAF_CARPET.getItem(), BLOSSOMING_APPLE_LEAF_CARPET.getItem(), APPLE.HEDGE.getItem());
        }
    }

    @Override
    @OnlyIn(Dist.CLIENT)
    public void initRenderLayers() {
        for (QuarkWoodBlockSet set : WOOD_BLOCK_SETS) {
            set.initRenderLayers();
        }

        if (BLOSSOMING_APPLE_LEAF_CARPET != null) {
            RenderTypeLookup.setRenderLayer(BLOSSOMING_APPLE_LEAF_CARPET.getBlock(), RenderType.getCutoutMipped());
        }
    }
}
