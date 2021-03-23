package azmalent.terraincognita.common.world.surfacebuilder;

import net.minecraft.block.BlockState;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;

import javax.annotation.Nonnull;
import java.util.Random;

public class MuskegSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> {
    public MuskegSurfaceBuilder() {
        super(SurfaceBuilderConfig.field_237203_a_);
    }

    public void buildSurface(@Nonnull Random random, @Nonnull IChunk chunkIn, @Nonnull Biome biomeIn, int x, int z, int startHeight, double noise, @Nonnull BlockState defaultBlock, @Nonnull BlockState defaultFluid, int seaLevel, long seed, @Nonnull SurfaceBuilderConfig config) {
        if (noise > 1.75) {
            SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, SurfaceBuilder.PODZOL_DIRT_GRAVEL_CONFIG);
        } else {
            SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG);
        }
    }
}
