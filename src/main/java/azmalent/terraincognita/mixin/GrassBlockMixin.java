package azmalent.terraincognita.mixin;

import azmalent.cuneiform.lib.util.BiomeUtil;
import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.init.ModBlocks;
import azmalent.terraincognita.common.world.ModVegetation;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.GrassBlock;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.BlockClusterFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.FlowersFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.BiomeDictionary;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

import static net.minecraftforge.common.BiomeDictionary.Type.COLD;
import static net.minecraftforge.common.BiomeDictionary.Type.HOT;

@Mixin(GrassBlock.class)
public class GrassBlockMixin {
    private static BlockClusterFeatureConfig getCustomFlowerConfig(Random rand, IWorldReader world, BlockPos pos) {
        Biome biome = world.getBiome(pos);
        RegistryKey<Biome> biomeKey = BiomeUtil.getBiomeKey(biome);
        boolean cold = BiomeDictionary.hasType(biomeKey, COLD);
        boolean hot = BiomeDictionary.hasType(biomeKey, HOT);

        float f = rand.nextFloat();
        switch (biome.getCategory()) {
            case SWAMP:
                if (f < 0.33 && !cold) return ModVegetation.Configs.SWAMP_FLOWERS;
                break;
            case SAVANNA:
                if (f < 0.33) return ModVegetation.Configs.SAVANNA_FLOWERS;
                break;
            case DESERT:
                if (f < 0.66) return ModVegetation.Configs.DESERT_MARIGOLDS;
                break;
            case EXTREME_HILLS:
                if (f < 0.33 && pos.getY() >= TIConfig.Flora.edelweissMinimumY.get() && !hot) {
                    return ModVegetation.Configs.EDELWEISS;
                } else if (f < 0.66) {
                    return ModVegetation.Configs.ALPINE_FLOWERS;
                }
                break;
            case JUNGLE:
                if (f < 0.66) return ModVegetation.Configs.JUNGLE_FLOWERS;
                break;
            case TAIGA: case ICY:
                if (f < 0.33) return ModVegetation.Configs.ARCTIC_FLOWERS;
                break;
        }

        return null;
    }

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    @Redirect(method = "grow", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/feature/FlowersFeature;getFlowerToPlace(Ljava/util/Random;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/gen/feature/IFeatureConfig;)Lnet/minecraft/block/BlockState;"))
    private <TConfig extends IFeatureConfig> BlockState getFlowerToPlace(FlowersFeature<TConfig> self, Random random, BlockPos pos, TConfig config, ServerWorld world, Random random1, BlockPos pos1, BlockState blockState) {
        BlockClusterFeatureConfig customFlowerConfig = getCustomFlowerConfig(random, world, pos);
        if (customFlowerConfig != null) {
            return customFlowerConfig.stateProvider.getBlockState(random, pos);
        }

        BlockState flower = self.getFlowerToPlace(random, pos, config);
        if (flower.isIn(Blocks.DANDELION) && TIConfig.Flora.dandelionPuff.get()) {
            if (random.nextFloat() < TIConfig.Flora.dandelionPuffChance.get()) {
                flower = ModBlocks.DANDELION_PUFF.getBlock().getDefaultState();
            }
        }
        else if (flower.isIn(Blocks.POPPY) && TIConfig.Flora.arcticFlowers.get()) {
            if (world.getBiome(pos).getCategory() == Biome.Category.ICY) {
                flower = ModBlocks.ARCTIC_POPPY.getBlock().getDefaultState();
            }
        }

        return flower;
    }
}
