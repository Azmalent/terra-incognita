package azmalent.terraincognita.common.init;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.block.blocksets.AppleWoodType;
import azmalent.terraincognita.common.block.blocksets.ModWoodType;
import azmalent.terraincognita.common.block.trees.AppleTree;
import azmalent.terraincognita.common.block.trees.FruitLeavesBlock;
import azmalent.terraincognita.common.block.trees.HazelTree;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.material.MaterialColor;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ModWoodTypes {
    public static final AppleWoodType APPLE = new AppleWoodType("apple", new AppleTree(), MaterialColor.WOOD, MaterialColor.ORANGE_TERRACOTTA, TIConfig.Trees.apple);
    public static final ModWoodType HAZEL = new ModWoodType("hazel", new HazelTree(), MaterialColor.WOOD, MaterialColor.BROWN, TIConfig.Trees.hazel) {
        @Override
        protected Block createLeaves() {
            assert ModBlocks.HAZELNUT != null;
            return new FruitLeavesBlock(ModBlocks.HAZELNUT.getDefaultState(), 100);
        }
    };

    public static List<ModWoodType> VALUES;
    public static Map<String, ModWoodType> VALUES_BY_NAME;

    public static void init() {
        VALUES = Lists.newArrayList(APPLE, HAZEL).stream().filter(ModWoodType::isEnabled).collect(Collectors.toList());
        VALUES_BY_NAME = VALUES.stream().collect(Collectors.toMap(type -> type.name, type -> type));
    }

    public static ModWoodType byName(String name) {
        return VALUES_BY_NAME.get(name);
    }
}
