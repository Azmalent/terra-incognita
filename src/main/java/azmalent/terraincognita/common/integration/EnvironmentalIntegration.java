package azmalent.terraincognita.common.integration;

import azmalent.terraincognita.TIConfig;
import net.minecraft.block.Block;
import net.minecraft.world.gen.blockstateprovider.WeightedBlockStateProvider;
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
    public static void addDelphiniums(WeightedBlockStateProvider provider) {
        if (ModList.get().isLoaded(MODID) && TIConfig.Integration.Environmental.delphiniumsInLushPlains.get()) {
            provider.addWeightedBlockstate(BLUE_DELPHINIUM.getDefaultState(), 1);
            provider.addWeightedBlockstate(PINK_DELPHINIUM.getDefaultState(), 1);
            provider.addWeightedBlockstate(PURPLE_DELPHINIUM.getDefaultState(), 1);
            provider.addWeightedBlockstate(WHITE_DELPHINIUM.getDefaultState(), 1);
        }
    }
}
