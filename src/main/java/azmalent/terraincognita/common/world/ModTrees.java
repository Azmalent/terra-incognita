package azmalent.terraincognita.common.world;

import net.minecraft.block.Blocks;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockstateprovider.SimpleBlockStateProvider;
import net.minecraft.world.gen.feature.*;
import net.minecraft.world.gen.foliageplacer.BushFoliagePlacer;
import net.minecraft.world.gen.trunkplacer.StraightTrunkPlacer;

public class ModTrees {
    public static ConfiguredFeature<?, ?> SPRUCE_BUSH = Feature.TREE.withConfiguration(
        (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.SPRUCE_LOG.getDefaultState()),
            new SimpleBlockStateProvider(Blocks.SPRUCE_LEAVES.getDefaultState()),
            new BushFoliagePlacer(FeatureSpread.func_242252_a(1), FeatureSpread.func_242252_a(1), 2),
            new StraightTrunkPlacer(1, 0, 0),
            new TwoLayerFeature(0, 0, 0))).func_236702_a_(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES).build()
    ).chance(4);

    public static ConfiguredFeature<?, ?> DWARF_BIRCH = Feature.TREE.withConfiguration(
        (new BaseTreeFeatureConfig.Builder(new SimpleBlockStateProvider(Blocks.BIRCH_LOG.getDefaultState()),
            new SimpleBlockStateProvider(Blocks.BIRCH_LEAVES.getDefaultState()),
            new BushFoliagePlacer(FeatureSpread.func_242252_a(1), FeatureSpread.func_242252_a(1), 2),
            new StraightTrunkPlacer(2, 0, 0),
            new TwoLayerFeature(0, 0, 0))).func_236702_a_(Heightmap.Type.MOTION_BLOCKING_NO_LEAVES).build()
    ).chance(4);
}
