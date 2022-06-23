package azmalent.terraincognita.integration.quark;

import azmalent.cuneiform.common.data.FuelHandler;
import azmalent.cuneiform.integration.IntegrationImpl;
import azmalent.cuneiform.registry.BlockEntry;
import azmalent.cuneiform.util.DataUtil;
import azmalent.cuneiform.util.ItemUtil;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.event.ToolInteractionHandler;
import azmalent.terraincognita.common.registry.ModWoodTypes;
import azmalent.terraincognita.integration.quark.block.TIHedgeBlock;
import azmalent.terraincognita.integration.quark.block.TILeafCarpetBlock;
import azmalent.terraincognita.integration.quark.block.TIWoodPostBlock;
import com.google.common.collect.Lists;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import vazkii.quark.base.module.ModuleLoader;
import vazkii.quark.content.building.module.ShearVinesModule;
import vazkii.quark.content.client.module.ChestSearchingModule;

import java.util.List;

@IntegrationImpl("quark")
public final class QuarkIntegration implements IQuarkProxy {
    private final List<QuarkWoodBlockSet> WOOD_BLOCK_SETS = ModWoodTypes.VALUES.stream().map(QuarkWoodBlockSet::new).toList();

    private final BlockEntry<TILeafCarpetBlock> BLOSSOMING_APPLE_LEAF_CARPET = TerraIncognita.REGISTRY_HELPER.createBlock("blossoming_apple_leaf_carpet", TILeafCarpetBlock::new).cutoutMippedRender().build();
    private final BlockEntry<TIHedgeBlock> BLOSSOMING_APPLE_HEDGE = TerraIncognita.REGISTRY_HELPER.createBlock("blossoming_apple_hedge", () -> new TIHedgeBlock(MaterialColor.TERRACOTTA_ORANGE)).cutoutMippedRender().build();

    private final BlockEntry<Block> HAZELNUT_SACK = TerraIncognita.REGISTRY_HELPER.createBlock("hazelnut_sack", Block.Properties.of(Material.WOOL, MaterialColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL)).build();
    private final BlockEntry<Block> SOUR_BERRY_SACK = TerraIncognita.REGISTRY_HELPER.createBlock("sour_berry_sack", Block.Properties.of(Material.WOOL, MaterialColor.TERRACOTTA_ORANGE).strength(0.5F).sound(SoundType.WOOL)).build();

    @Override
    public void register(IEventBus bus) {
        TerraIncognita.LOGGER.info("Integrating with Quark...");

        bus.addListener(this::setup);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            bus.addListener(this::registerBlockColorHandlers);
            bus.addListener(this::registerItemColorHandlers);
        });
    }

    public void setup(FMLCommonSetupEvent event) {
        for (QuarkWoodBlockSet set : WOOD_BLOCK_SETS) {
            ToolInteractionHandler.AXE_STRIPPABLES.put(set.POST.get(), set.STRIPPED_POST.get());

            DataUtil.registerFlammable(set.BOOKSHELF, 30, 20);
            DataUtil.registerFlammable(set.POST, 5, 20);
            DataUtil.registerFlammable(set.STRIPPED_POST, 5, 20);
            DataUtil.registerFlammable(set.HEDGE, 5, 20);
            DataUtil.registerFlammable(set.LEAF_CARPET, 30, 60);

            FuelHandler.registerFuel(1.5f, set.BOOKSHELF, set.LADDER, set.POST, set.STRIPPED_POST);

            DataUtil.registerCompostable(set.LEAF_CARPET, 0.2f);

        }

        DataUtil.registerFlammable(BLOSSOMING_APPLE_HEDGE, 5, 20);
        DataUtil.registerFlammable(BLOSSOMING_APPLE_LEAF_CARPET, 30, 60);
        DataUtil.registerFlammable(HAZELNUT_SACK, 30, 60);
        DataUtil.registerFlammable(SOUR_BERRY_SACK, 30, 60);

        DataUtil.registerCompostable(BLOSSOMING_APPLE_LEAF_CARPET, 0.2f);
        DataUtil.registerCompostable(SOUR_BERRY_SACK, 1);
        DataUtil.registerCompostable(HAZELNUT_SACK, 1);
    }

    @OnlyIn(Dist.CLIENT)
    public void registerBlockColorHandlers(ColorHandlerEvent.Block event) {
        BlockColors colors = event.getBlockColors();

        List<Block> foliageColoredBlocks = Lists.newArrayList(BLOSSOMING_APPLE_LEAF_CARPET.get(), BLOSSOMING_APPLE_HEDGE.get());
        WOOD_BLOCK_SETS.forEach(set -> {
            foliageColoredBlocks.add(set.LEAF_CARPET.get());
            foliageColoredBlocks.add(set.HEDGE.get());
        });

        colors.register((state, reader, pos, color) -> reader != null && pos != null ? BiomeColors.getAverageFoliageColor(reader, pos) : FoliageColor.get(0.5D, 1.0D),
            foliageColoredBlocks.toArray(Block[]::new)
        );
    }

    @OnlyIn(Dist.CLIENT)
    public void registerItemColorHandlers(ColorHandlerEvent.Item event) {
        ItemColors colors = event.getItemColors();
        BlockColors blockColors = event.getBlockColors();

        List<Block> foliageColoredBlocks = Lists.newArrayList(BLOSSOMING_APPLE_LEAF_CARPET.get(), BLOSSOMING_APPLE_HEDGE.get());
        WOOD_BLOCK_SETS.forEach(set -> {
            foliageColoredBlocks.add(set.LEAF_CARPET.get());
            foliageColoredBlocks.add(set.HEDGE.get());
        });

        colors.register((stack, index) -> blockColors.getColor(ItemUtil.getBlockFromItem(stack).defaultBlockState(), null, null, index),
            foliageColoredBlocks.toArray(Block[]::new)
        );
    }

    @Override
    public boolean namesMatch(ItemStack stack) {
        return ChestSearchingModule.namesMatch(stack);
    }

    @Override
    public boolean canLanternConnect(BlockState state, LevelReader level, BlockPos pos) {
        return state.getValue(LanternBlock.HANGING) && (level.getBlockState(pos.above()).getBlock() instanceof TIWoodPostBlock);
    }

    @Override
    public boolean canCutVines() {
        return ModuleLoader.INSTANCE.isModuleEnabled(ShearVinesModule.class);
    }
}
