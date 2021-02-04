package azmalent.terraincognita.common.world.feature.config;

import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.world.gen.Heightmap;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.feature.AbstractFeatureSizeType;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.foliageplacer.FoliagePlacer;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.trunkplacer.AbstractTrunkPlacer;

import java.util.List;

public class HugeTreeFeatureConfig extends BaseTreeFeatureConfig {
    public static final Codec<HugeTreeFeatureConfig> CODEC = RecordCodecBuilder.create((builder) -> builder.group(
            BlockStateProvider.CODEC.fieldOf("trunk_provider").forGetter((p_236693_0_) -> p_236693_0_.trunkProvider),
            BlockStateProvider.CODEC.fieldOf("wood_provider").forGetter((p_236693_0_) -> p_236693_0_.woodProvider),
            BlockStateProvider.CODEC.fieldOf("leaves_provider").forGetter((p_236692_0_) -> p_236692_0_.leavesProvider),
            FoliagePlacer.field_236749_d_.fieldOf("foliage_placer").forGetter((p_236691_0_) -> p_236691_0_.foliagePlacer),
            AbstractFeatureSizeType.CODEC.fieldOf("minimum_size").forGetter((p_236689_0_) -> p_236689_0_.minimumSize),
            TreeDecorator.field_236874_c_.listOf().fieldOf("decorators").forGetter((p_236688_0_) -> p_236688_0_.decorators),
            Codec.INT.fieldOf("max_water_depth").orElse(0).forGetter((p_236687_0_) -> p_236687_0_.maxWaterDepth),
            Codec.BOOL.fieldOf("ignore_vines").orElse(false).forGetter((p_236686_0_) -> p_236686_0_.ignoreVines),
            Heightmap.Type.CODEC.fieldOf("heightmap").forGetter((p_236684_0_) -> p_236684_0_.field_236682_l_
        )).apply(builder, HugeTreeFeatureConfig::new)
    );

    public final BlockStateProvider woodProvider;

    @SuppressWarnings("ConstantConditions")
    protected HugeTreeFeatureConfig(BlockStateProvider trunkProvider, BlockStateProvider woodProvider, BlockStateProvider leavesProvider, FoliagePlacer foliagePlacer, AbstractFeatureSizeType minimumSize, List<TreeDecorator> decorators, int maxWaterDepth, boolean ignoreVines, Heightmap.Type p_i232020_9_) {
        super(trunkProvider, leavesProvider, foliagePlacer, null, minimumSize, decorators, maxWaterDepth, ignoreVines, p_i232020_9_);
        this.woodProvider = woodProvider;
    }

    public BaseTreeFeatureConfig func_236685_a_(List<TreeDecorator> decorators) {
        return new HugeTreeFeatureConfig(this.trunkProvider, this.woodProvider, this.leavesProvider, this.foliagePlacer, this.minimumSize, decorators, this.maxWaterDepth, this.ignoreVines, this.field_236682_l_);
    }

    public static class Builder {
        public final BlockStateProvider trunkProvider;
        public final BlockStateProvider woodProvider;
        public final BlockStateProvider leavesProvider;
        private final FoliagePlacer foliagePlacer;
        private final AbstractFeatureSizeType minimumSize;
        private List<TreeDecorator> decorators = ImmutableList.of();
        private int maxWaterDepth;
        private boolean ignoreVines;
        private Heightmap.Type field_236699_i_ = Heightmap.Type.OCEAN_FLOOR;

        public Builder(BlockStateProvider trunkProvider, BlockStateProvider woodProvider, BlockStateProvider leavesProvider, FoliagePlacer foliagePlacer, AbstractFeatureSizeType minimumSize) {
            this.trunkProvider = trunkProvider;
            this.woodProvider = woodProvider;
            this.leavesProvider = leavesProvider;
            this.foliagePlacer = foliagePlacer;
            this.minimumSize = minimumSize;
        }

        public Builder setDecorators(List<TreeDecorator> decorators) {
            this.decorators = decorators;
            return this;
        }

        public Builder setMaxWaterDepth(int p_236701_1_) {
            this.maxWaterDepth = p_236701_1_;
            return this;
        }

        public Builder setIgnoreVines() {
            this.ignoreVines = true;
            return this;
        }

        public Builder func_236702_a_(Heightmap.Type p_236702_1_) {
            this.field_236699_i_ = p_236702_1_;
            return this;
        }

        public HugeTreeFeatureConfig build() {
            return new HugeTreeFeatureConfig(this.trunkProvider, this.woodProvider, this.leavesProvider, this.foliagePlacer, this.minimumSize, this.decorators, this.maxWaterDepth, this.ignoreVines, this.field_236699_i_);
        }
    }
}
