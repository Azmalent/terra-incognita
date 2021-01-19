package azmalent.terraincognita.mixin;

import azmalent.cuneiform.lib.util.BiomeUtil;
import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.init.ModBiomes;
import azmalent.terraincognita.common.init.ModBlocks;
import com.google.common.collect.Lists;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.GrassBlock;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.BiomeRegistry;
import net.minecraft.world.biome.Biomes;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.gen.feature.FlowersFeature;
import net.minecraft.world.gen.feature.IFeatureConfig;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.registries.ForgeRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

import static net.minecraftforge.common.BiomeDictionary.Type.COLD;
import static net.minecraftforge.common.BiomeDictionary.Type.HOT;

@Mixin(GrassBlock.class)
public class GrassBlockMixin {
    private static ModBlocks.PottablePlantEntry getCustomFlowerToPlace(Random rand, IWorldReader world, BlockPos pos) {
        Biome biome = world.getBiome(pos);
        RegistryKey<Biome> biomeKey = BiomeUtil.getBiomeKey(biome);
        boolean cold = BiomeDictionary.hasType(biomeKey, COLD);
        boolean hot = BiomeDictionary.hasType(biomeKey, HOT);

        float f = rand.nextFloat();

        if (biome.getCategory() == Biome.Category.SWAMP) {
            if (f < 0.33 && !cold) {
                return ModBlocks.FORGET_ME_NOT;
            }
        } else if (biome.getCategory() == Biome.Category.PLAINS || biome.getCategory() == Biome.Category.FOREST) {
            if (f < 0.2 && !cold) {
                return ModBlocks.FORGET_ME_NOT;
            }
        } else if (biome.getCategory() == Biome.Category.SAVANNA || biome.getCategory() == Biome.Category.DESERT) {
            if (f < 0.33) {
                return ModBlocks.MARIGOLD;
            }
        } else if (biome.getCategory() == Biome.Category.EXTREME_HILLS) {
            if (f < 0.33 && pos.getY() >= TIConfig.Flora.edelweissMinimumY.get() && !hot) {
                return ModBlocks.EDELWEISS;
            }
        } else if (biome.getCategory() == Biome.Category.JUNGLE) {
            if (f < 0.33) {
                return ModBlocks.BLUE_IRIS;
            } else if (f < 0.66) {
                return ModBlocks.PURPLE_IRIS;
            }
        } else if (biome.getCategory() == Biome.Category.TAIGA || biome.getCategory() == Biome.Category.ICY) {
            if (f < 0.33) {
                return ModBlocks.FIREWEED;
            }
        }

        return null;
    }

    @SuppressWarnings({"unchecked", "ConstantConditions"})
    @Redirect(method = "grow", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/gen/feature/FlowersFeature;getFlowerToPlace(Ljava/util/Random;Lnet/minecraft/util/math/BlockPos;Lnet/minecraft/world/gen/feature/IFeatureConfig;)Lnet/minecraft/block/BlockState;"))
    private <TConfig extends IFeatureConfig> BlockState getFlowerToPlace(FlowersFeature<TConfig> self, Random random, BlockPos pos, TConfig config, ServerWorld world, Random random1, BlockPos pos1, BlockState blockState) {
        ModBlocks.PottablePlantEntry customFlower = getCustomFlowerToPlace(random, world, pos);
        if (customFlower != null) {
            return customFlower.getBlock().getDefaultState();
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
