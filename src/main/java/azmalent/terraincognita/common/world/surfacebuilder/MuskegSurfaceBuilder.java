package azmalent.terraincognita.common.world.surfacebuilder;

import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilderBaseConfiguration;

import javax.annotation.Nonnull;
import java.util.Random;

public class MuskegSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderBaseConfiguration> {
    public MuskegSurfaceBuilder() {
        super(SurfaceBuilderBaseConfiguration.CODEC);
    }

    public void apply(@Nonnull Random random, @Nonnull ChunkAccess chunkIn, @Nonnull Biome biomeIn, int x, int z, int startHeight, double noise, @Nonnull BlockState defaultBlock, @Nonnull BlockState defaultFluid, int seaLevel, long seed, @Nonnull SurfaceBuilderBaseConfiguration config) {
        if (noise > 1.75) {
            SurfaceBuilder.DEFAULT.apply(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, SurfaceBuilder.CONFIG_PODZOL);
        } else {
            SurfaceBuilder.DEFAULT.apply(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, SurfaceBuilder.CONFIG_GRASS);
        }
    }
}
