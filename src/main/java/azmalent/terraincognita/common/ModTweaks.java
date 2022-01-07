package azmalent.terraincognita.common;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.registry.ModBlocks;
import com.google.common.collect.Lists;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.levelgen.feature.stateproviders.ForestFlowerProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.PlainFlowerProvider;

import java.util.List;

public class ModTweaks {
    public static void modifyFlowerGradients() {
        if (TIConfig.Flora.fieldFlowers.get()) {
            List<BlockState> flowers = Lists.newArrayList(PlainFlowerProvider.HIGH_NOISE_FLOWERS);
            flowers.add(ModBlocks.CHICORY.getBlock().defaultBlockState());
            flowers.add(ModBlocks.YARROW.getBlock().defaultBlockState());
            PlainFlowerProvider.HIGH_NOISE_FLOWERS = flowers.toArray(new BlockState[0]);

            List<BlockState> rareFlowers = Lists.newArrayList(PlainFlowerProvider.LOW_NOISE_FLOWERS);
            rareFlowers.add(ModBlocks.DAFFODIL.getBlock().defaultBlockState());
            PlainFlowerProvider.LOW_NOISE_FLOWERS = rareFlowers.toArray(new BlockState[0]);
        }

        List<BlockState> flowerForestGradient = Lists.newArrayList(ForestFlowerProvider.FLOWERS);
        if (TIConfig.Flora.fieldFlowers.get()) {
            flowerForestGradient.add(ModBlocks.DAFFODIL.getBlock().defaultBlockState());
        }

        if (TIConfig.Flora.forestFlowers.get()) {
            flowerForestGradient.add(ModBlocks.WILD_GARLIC.getBlock().defaultBlockState());
            flowerForestGradient.add(ModBlocks.FOXGLOVE.getBlock().defaultBlockState());
            flowerForestGradient.add(ModBlocks.YELLOW_PRIMROSE.getBlock().defaultBlockState());
            flowerForestGradient.add(ModBlocks.PINK_PRIMROSE.getBlock().defaultBlockState());
            flowerForestGradient.add(ModBlocks.PURPLE_PRIMROSE.getBlock().defaultBlockState());
        }

        ForestFlowerProvider.FLOWERS = flowerForestGradient.toArray(new BlockState[0]);
    }

    public static void addExtraTundraSpawns(MobSpawnSettings.Builder spawns) {
        if (TIConfig.Misc.betterTundras.get()) {
            spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 2, 2, 3));
            spawns.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.FOX, 1, 1, 1));
        }
    }
}
