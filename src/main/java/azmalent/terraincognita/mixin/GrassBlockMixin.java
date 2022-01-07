package azmalent.terraincognita.mixin;

import azmalent.cuneiform.lib.util.BiomeUtil;
import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.world.ModFlowerFeatures;
import azmalent.terraincognita.util.WorldGenUtil;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.resources.ResourceKey;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biomes;
import net.minecraft.world.level.levelgen.feature.configurations.RandomPatchConfiguration;
import net.minecraft.world.level.levelgen.feature.AbstractFlowerFeature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.BiomeDictionary;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

import static net.minecraftforge.common.BiomeDictionary.Type.COLD;
import static net.minecraftforge.common.BiomeDictionary.Type.HOT;

@Mixin(GrassBlock.class)
public class GrassBlockMixin {
    private static RandomPatchConfiguration getCustomFlowerConfig(Random rand, LevelReader world, BlockPos pos) {
        Biome biome = world.getBiome(pos);
        ResourceKey<Biome> biomeKey = BiomeUtil.getBiomeKey(biome);

        if (biomeKey != Biomes.FLOWER_FOREST && rand.nextBoolean()) {
            switch (WorldGenUtil.getProperBiomeCategory(biome)) {
                case FOREST:
                    if (TIConfig.Flora.forestFlowers.get()) {
                        return ModFlowerFeatures.Configs.FOREST_FLOWERS;
                    }
                    break;

                case SWAMP:
                    if (TIConfig.Flora.swampFlowers.get() && !BiomeDictionary.hasType(biomeKey, COLD)) {
                        return ModFlowerFeatures.Configs.SWAMP_SMALL_FLOWERS;
                    }
                    break;

                case SAVANNA: case DESERT:
                    if (TIConfig.Flora.savannaFlowers.get()) {
                        return ModFlowerFeatures.Configs.SAVANNA_SMALL_FLOWERS;
                    }
                    break;

                case EXTREME_HILLS:
                    if (TIConfig.Flora.alpineFlowers.get() && !BiomeDictionary.hasType(biomeKey, HOT)) {
                        return ModFlowerFeatures.Configs.ALPINE_FLOWERS;
                    }
                    break;

                case JUNGLE:
                    if (TIConfig.Flora.jungleFlowers.get()) {
                        return ModFlowerFeatures.Configs.JUNGLE_FLOWERS;
                    }
                    break;

                case TAIGA: case ICY:
                    if (TIConfig.Flora.arcticFlowers.get()) {
                        return ModFlowerFeatures.Configs.ARCTIC_SMALL_FLOWERS;
                    }
            }
        }

        return null;
    }
    
    @Redirect(method = "grow", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/feature/FlowersFeature;getFlowerToPlace(Ljava/util/Random;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/gen/feature/IFeatureConfig;)Lnet/minecraft/block/BlockState;"))
    private <TConfig extends FeatureConfiguration> BlockState getFlowerToPlace(AbstractFlowerFeature<TConfig> self, Random rand, BlockPos pos, TConfig config, ServerLevel world, Random random1, BlockPos pos1, BlockState blockState) {
        RandomPatchConfiguration customFlowerConfig = getCustomFlowerConfig(rand, world, pos);
        if (customFlowerConfig != null) {
            return customFlowerConfig.stateProvider.getState(rand, pos);
        }

        BlockState flower = self.getRandomFlower(rand, pos, config);
        if (flower.is(Blocks.DANDELION) && TIConfig.Flora.dandelionPuff.get()) {
            if (rand.nextFloat() < TIConfig.Flora.dandelionPuffChance.get()) {
                flower = ModBlocks.DANDELION_PUFF.getBlock().defaultBlockState();
            }
        }
        else if (flower.is(Blocks.POPPY) && TIConfig.Flora.arcticFlowers.get()) {
            if (world.getBiome(pos).getBiomeCategory() == Biome.BiomeCategory.ICY && rand.nextFloat() < TIConfig.Flora.arcticPoppyChance.get()) {
                flower = ModBlocks.ARCTIC_POPPY.getBlock().defaultBlockState();
            }
        }

        return flower;
    }
}
