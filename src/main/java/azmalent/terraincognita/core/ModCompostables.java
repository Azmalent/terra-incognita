package azmalent.terraincognita.core;

import azmalent.cuneiform.util.DataUtil;
import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.core.registry.ModBlocks;
import azmalent.terraincognita.core.registry.ModItems;
import azmalent.terraincognita.core.registry.ModWoodTypes;
import net.minecraft.world.item.Items;

public class ModCompostables {
    public static void init() {
        if (TIConfig.Misc.additionalCompostables.get()) {
            DataUtil.registerCompostable(Items.DEAD_BUSH, 0.3f);
            DataUtil.registerCompostable(Items.BAMBOO, 0.65f);
            DataUtil.registerCompostable(Items.CHORUS_FRUIT, 1.0f);
            DataUtil.registerCompostable(Items.POPPED_CHORUS_FRUIT, 1.0f);
            DataUtil.registerCompostable(Items.CHORUS_FLOWER, 1.0f);
        }

        for (var flower : ModBlocks.FLOWERS) {
            DataUtil.registerCompostable(flower, 0.65f);
        }

        for (var lotus : ModBlocks.LOTUSES) {
            DataUtil.registerCompostable(lotus, 0.65f);
        }

        for (var sweetPea : ModBlocks.SWEET_PEAS) {
            DataUtil.registerCompostable(sweetPea, 0.65f);
        }

        ModWoodTypes.VALUES.forEach(woodType -> {
            DataUtil.registerCompostable(woodType.SAPLING, 0.3f);
            DataUtil.registerCompostable(woodType.LEAVES, 0.3f);
            DataUtil.registerCompostable(woodType.LEAF_CARPET, 0.2f);
        });

        DataUtil.registerCompostable(ModWoodTypes.APPLE.BLOSSOMING_LEAVES, 0.3f);
        DataUtil.registerCompostable(ModWoodTypes.APPLE.BLOSSOMING_LEAF_CARPET, 0.2f);

        DataUtil.registerCompostable(ModBlocks.SMALL_LILY_PAD, 0.3f);
        DataUtil.registerCompostable(ModBlocks.HANGING_MOSS, 0.3f);
        DataUtil.registerCompostable(ModBlocks.CARIBOU_MOSS, 0.3f);
        DataUtil.registerCompostable(ModItems.SOUR_BERRIES, 0.3f);
        DataUtil.registerCompostable(ModBlocks.SOUR_BERRY_SPROUTS, 0.3f);
        DataUtil.registerCompostable(ModItems.CACTUS_NEEDLE, 0.3f);
        DataUtil.registerCompostable(ModBlocks.SWAMP_REEDS, 0.5f);
        DataUtil.registerCompostable(ModBlocks.SMOOTH_CACTUS, 0.5f);
        DataUtil.registerCompostable(ModItems.FERN_FIDDLEHEAD, 0.65f);
        DataUtil.registerCompostable(ModItems.WREATH, 1);

        DataUtil.registerCompostable(ModItems.HAZELNUT, 0.3f);
        DataUtil.registerCompostable(ModItems.HAZELNUT_COOKIE, 0.85f);

        DataUtil.registerCompostable(ModBlocks.HAZELNUT_SACK, 1);
        DataUtil.registerCompostable(ModBlocks.SOUR_BERRY_SACK, 1);
    }
}
