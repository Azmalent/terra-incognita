package azmalent.terraincognita.common.world.surfacebuilder;

import azmalent.terraincognita.common.registry.ModBlocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderBaseConfiguration;
import net.minecraftforge.common.util.Lazy;

import javax.annotation.Nonnull;
import java.util.Random;

public class LushPlainsSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderBaseConfiguration> {
    public static final Lazy<SurfaceBuilderBaseConfiguration> FLOWERING_GRASS_DIRT_GRAVEL_CONFIG = Lazy.of(
        () -> new SurfaceBuilderBaseConfiguration(ModBlocks.FLOWERING_GRASS.getDefaultState(), Blocks.DIRT.defaultBlockState(), Blocks.GRAVEL.defaultBlockState()
    ));

    public LushPlainsSurfaceBuilder() {
        super(SurfaceBuilderBaseConfiguration.CODEC);
    }

    public void apply(@Nonnull Random random, @Nonnull ChunkAccess chunkIn, @Nonnull Biome biomeIn, int x, int z, int startHeight, double noise, @Nonnull BlockState defaultBlock, @Nonnull BlockState defaultFluid, int seaLevel, long seed, @Nonnull SurfaceBuilderBaseConfiguration config) {
        if (noise > 1.5D) {
            SurfaceBuilder.DEFAULT.apply(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, SurfaceBuilder.CONFIG_GRASS);
        } else {
            SurfaceBuilder.DEFAULT.apply(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, FLOWERING_GRASS_DIRT_GRAVEL_CONFIG.get());
        }
    }
}
