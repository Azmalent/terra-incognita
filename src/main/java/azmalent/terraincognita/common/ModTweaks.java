package azmalent.terraincognita.common;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.world.ModTrees;
import com.google.common.collect.Lists;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.world.biome.BiomeGenerationSettings;
import net.minecraft.world.biome.DefaultBiomeFeatures;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraft.world.gen.GenerationStage;
import net.minecraft.world.gen.blockstateprovider.ForestFlowerBlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.PlainFlowerBlockStateProvider;

import java.util.List;

public class ModTweaks {
    public static void modifyFlowerGradients() {
        if (TIConfig.Flora.fieldFlowers.get()) {
            List<BlockState> flowers = Lists.newArrayList(PlainFlowerBlockStateProvider.COMMON_FLOWERS);
            flowers.add(ModBlocks.CHICORY.getBlock().getDefaultState());
            flowers.add(ModBlocks.YARROW.getBlock().getDefaultState());
            PlainFlowerBlockStateProvider.COMMON_FLOWERS = flowers.toArray(new BlockState[0]);

            List<BlockState> rareFlowers = Lists.newArrayList(PlainFlowerBlockStateProvider.RARE_FLOWERS);
            rareFlowers.add(ModBlocks.DAFFODIL.getBlock().getDefaultState());
            PlainFlowerBlockStateProvider.RARE_FLOWERS = rareFlowers.toArray(new BlockState[0]);
        }

        List<BlockState> flowerForestGradient = Lists.newArrayList(ForestFlowerBlockStateProvider.STATES);
        if (TIConfig.Flora.fieldFlowers.get()) {
            flowerForestGradient.add(ModBlocks.DAFFODIL.getBlock().getDefaultState());
        }

        if (TIConfig.Flora.forestFlowers.get()) {
            flowerForestGradient.add(ModBlocks.WILD_GARLIC.getBlock().getDefaultState());
            flowerForestGradient.add(ModBlocks.FOXGLOVE.getBlock().getDefaultState());
            flowerForestGradient.add(ModBlocks.YELLOW_PRIMROSE.getBlock().getDefaultState());
            flowerForestGradient.add(ModBlocks.PINK_PRIMROSE.getBlock().getDefaultState());
            flowerForestGradient.add(ModBlocks.PURPLE_PRIMROSE.getBlock().getDefaultState());
        }

        ForestFlowerBlockStateProvider.STATES = flowerForestGradient.toArray(new BlockState[0]);
    }

    public static void addExtraTundraSpawns(MobSpawnInfo.Builder spawns) {
        spawns.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.SHEEP, 2, 2, 3));
        spawns.withSpawner(EntityClassification.CREATURE, new MobSpawnInfo.Spawners(EntityType.FOX, 1, 1, 1));
    }

    public static void addExtraTundraFeatures(BiomeGenerationSettings.Builder builder) {
        DefaultBiomeFeatures.withSparseBerries(builder);

        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModTrees.SPRUCE_SHRUB);
        builder.withFeature(GenerationStage.Decoration.VEGETAL_DECORATION, ModTrees.TUNDRA_BIRCH);
    }
}
