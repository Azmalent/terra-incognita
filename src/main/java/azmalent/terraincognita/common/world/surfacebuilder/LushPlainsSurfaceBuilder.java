package azmalent.terraincognita.common.world.surfacebuilder;

import azmalent.terraincognita.common.registry.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.chunk.IChunk;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilder;
import net.minecraft.world.gen.surfacebuilders.SurfaceBuilderConfig;
import net.minecraftforge.common.util.Lazy;

import javax.annotation.Nonnull;
import java.util.Random;

public class LushPlainsSurfaceBuilder extends SurfaceBuilder<SurfaceBuilderConfig> {
    public static final Lazy<SurfaceBuilderConfig> FLOWERING_GRASS_DIRT_GRAVEL_CONFIG = Lazy.of(
        () -> new SurfaceBuilderConfig(ModBlocks.FLOWERING_GRASS.getDefaultState(), Blocks.DIRT.getDefaultState(), Blocks.GRAVEL.getDefaultState()
    ));

    public LushPlainsSurfaceBuilder() {
        super(SurfaceBuilderConfig.field_237203_a_);
    }

    public void buildSurface(@Nonnull Random random, @Nonnull IChunk chunkIn, @Nonnull Biome biomeIn, int x, int z, int startHeight, double noise, @Nonnull BlockState defaultBlock, @Nonnull BlockState defaultFluid, int seaLevel, long seed, @Nonnull SurfaceBuilderConfig config) {
        if (noise > 1.5D) {
            SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, SurfaceBuilder.GRASS_DIRT_GRAVEL_CONFIG);
        } else {
            SurfaceBuilder.DEFAULT.buildSurface(random, chunkIn, biomeIn, x, z, startHeight, noise, defaultBlock, defaultFluid, seaLevel, seed, FLOWERING_GRASS_DIRT_GRAVEL_CONFIG.get());
        }
    }
}
