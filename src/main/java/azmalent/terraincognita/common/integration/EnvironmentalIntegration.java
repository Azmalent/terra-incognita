package azmalent.terraincognita.common.integration;

import azmalent.terraincognita.TIConfig;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ObjectHolder;

public class EnvironmentalIntegration {
    private static final String MODID = "environmental";

    @ObjectHolder(MODID + ":blue_delphinium")
    public static final Block BLUE_DELPHINIUM = null;

    @ObjectHolder(MODID + ":pink_delphinium")
    public static final Block PINK_DELPHINIUM = null;

    @ObjectHolder(MODID + ":purple_delphinium")
    public static final Block PURPLE_DELPHINIUM = null;

    @ObjectHolder(MODID + ":white_delphinium")
    public static final Block WHITE_DELPHINIUM = null;

    @SuppressWarnings("ConstantConditions")
    public static void addDelphiniums(WeightedStateProvider provider) {
        if (ModList.get().isLoaded(MODID) && TIConfig.Integration.Environmental.delphiniumsInLushPlains.get()) {
            provider.add(BLUE_DELPHINIUM.defaultBlockState(), 1);
            provider.add(PINK_DELPHINIUM.defaultBlockState(), 1);
            provider.add(PURPLE_DELPHINIUM.defaultBlockState(), 1);
            provider.add(WHITE_DELPHINIUM.defaultBlockState(), 1);
        }
    }
}
