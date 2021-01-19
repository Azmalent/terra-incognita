package azmalent.terraincognita.mixin;

import azmalent.cuneiform.lib.util.BiomeUtil;
import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.init.ModBlocks;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ISeedReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.FlowersFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraftforge.common.BiomeDictionary;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Random;

@Mixin(FlowersFeature.class)
public abstract class FlowersFeatureMixin<TConfig extends IFeatureConfig> {
    @Shadow public abstract BlockPos getNearbyPos(Random random, BlockPos blockPos, TConfig config);

    @Shadow public abstract int getFlowerCount(TConfig config);

    @Shadow public abstract BlockState getFlowerToPlace(Random random, BlockPos blockPos, TConfig config);

    @Shadow public abstract boolean isValidPosition(IWorld iWorld, BlockPos blockPos, TConfig config);

    //TODO: use @Redirect
    @Inject(method = "generate", at = @At("HEAD"), cancellable = true)
    void generate(ISeedReader reader, ChunkGenerator chunkGenerator, Random rand, BlockPos centerPos, TConfig config, CallbackInfoReturnable<Boolean> cir) {
        BlockState blockState = this.getFlowerToPlace(rand, centerPos, config);
        boolean success = false;

        for(int j = 0; j < this.getFlowerCount(config); ++j) {
            BlockPos pos = this.getNearbyPos(rand, centerPos, config);
            if (reader.isAirBlock(pos) && pos.getY() < 255 && blockState.isValidPosition(reader, pos) && this.isValidPosition(reader, pos, config)) {
                BlockState flower = blockState;
                if (flower.isIn(Blocks.DANDELION) && TIConfig.Flora.dandelionPuff.get()) {
                    if (rand.nextFloat() < TIConfig.Flora.dandelionPuffChance.get()) {
                        flower = ModBlocks.DANDELION_PUFF.getBlock().getDefaultState();
                    }
                }
                else if (flower.isIn(Blocks.POPPY) && TIConfig.Flora.arcticFlowers.get()) {
                    if (reader.getBiome(pos).getCategory() == Biome.Category.ICY) {
                        flower = ModBlocks.ARCTIC_POPPY.getBlock().getDefaultState();
                    }
                }

                reader.setBlockState(pos, flower, 2);
                success = true;
            }
        }

        cir.setReturnValue(success);
    }
}
