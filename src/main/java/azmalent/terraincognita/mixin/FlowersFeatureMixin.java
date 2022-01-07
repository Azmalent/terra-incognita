package azmalent.terraincognita.mixin;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.registry.ModBlocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.feature.AbstractFlowerFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(AbstractFlowerFeature.class)
public abstract class FlowersFeatureMixin<TConfig extends FeatureConfiguration> {
    @Shadow public abstract BlockPos getNearbyPos(Random random, BlockPos blockPos, TConfig config);

    @Shadow public abstract int getFlowerCount(TConfig config);

    @Shadow public abstract BlockState getFlowerToPlace(Random random, BlockPos blockPos, TConfig config);

    @Shadow public abstract boolean isValidPosition(LevelAccessor iWorld, BlockPos blockPos, TConfig config);

    //TODO: use @Redirect
    @Inject(method = "generate", at = @At("HEAD"), cancellable = true)
    void generate(WorldGenLevel reader, ChunkGenerator chunkGenerator, Random rand, BlockPos centerPos, TConfig config, CallbackInfoReturnable<Boolean> cir) {
        BlockState blockState = this.getFlowerToPlace(rand, centerPos, config);
        boolean success = false;

        for(int j = 0; j < this.getFlowerCount(config); ++j) {
            BlockPos pos = this.getNearbyPos(rand, centerPos, config);
            if (reader.isEmptyBlock(pos) && pos.getY() < 255 && blockState.canSurvive(reader, pos) && this.isValidPosition(reader, pos, config)) {
                BlockState flower = blockState;
                if (flower.is(Blocks.DANDELION) && TIConfig.Flora.dandelionPuff.get()) {
                    if (rand.nextFloat() < TIConfig.Flora.dandelionPuffChance.get()) {
                        flower = ModBlocks.DANDELION_PUFF.getBlock().defaultBlockState();
                    }
                }
                else if (flower.is(Blocks.POPPY) && TIConfig.Flora.arcticFlowers.get()) {
                    if (reader.getBiome(pos).getBiomeCategory() == Biome.BiomeCategory.ICY && rand.nextFloat() < TIConfig.Flora.arcticPoppyChance.get()) {
                        flower = ModBlocks.ARCTIC_POPPY.getBlock().defaultBlockState();
                    }
                }

                reader.setBlock(pos, flower, 2);
                success = true;
            }
        }

        cir.setReturnValue(success);
    }
}
