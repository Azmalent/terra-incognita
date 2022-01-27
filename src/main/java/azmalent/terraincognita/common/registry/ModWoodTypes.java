package azmalent.terraincognita.common.registry;

import azmalent.cuneiform.lib.registry.BlockEntry;
import azmalent.terraincognita.common.block.trees.AppleTree;
import azmalent.terraincognita.common.block.trees.HazelTree;
import azmalent.terraincognita.common.block.woodtypes.AppleWoodType;
import azmalent.terraincognita.common.block.woodtypes.HazelWoodType;
import azmalent.terraincognita.common.block.woodtypes.ModWoodType;
import com.google.common.collect.Lists;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MaterialColor;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ModWoodTypes {
    public static final AppleWoodType APPLE = new AppleWoodType("apple", new AppleTree(), MaterialColor.WOOD, MaterialColor.TERRACOTTA_ORANGE);
    public static final HazelWoodType HAZEL = new HazelWoodType("hazel", new HazelTree(), MaterialColor.WOOD, MaterialColor.COLOR_BROWN);

    public static List<ModWoodType> VALUES;
    public static Map<String, ModWoodType> VALUES_BY_NAME;

    public static void init() {
        VALUES = Lists.newArrayList(APPLE, HAZEL);
        VALUES_BY_NAME = VALUES.stream().collect(Collectors.toMap(type -> type.name, type -> type));
    }

    public static ModWoodType byName(String name) {
        return VALUES_BY_NAME.get(name);
    }

    @SuppressWarnings("unchecked")
    private static List<Supplier<? extends Block>> getWoodBlockSuppliers(BiConsumer<List<Supplier<? extends Block>>, ModWoodType> func) {
        List<Supplier<? extends Block>> suppliers = Lists.newArrayList();
        ModWoodTypes.VALUES.forEach(woodType -> func.accept(suppliers, woodType));

        return suppliers;
    }

    public static List<Supplier<? extends Block>> getChests() {
        return getWoodBlockSuppliers((list, wood) -> list.add(wood.CHEST));
    }

    public static List<Supplier<? extends Block>> getTrappedChests() {
        return getWoodBlockSuppliers((list, wood) -> list.add(wood.TRAPPED_CHEST));
    }

    public static List<Supplier<? extends Block>> getSigns() {
        return getWoodBlockSuppliers((list, wood) -> {
            list.add(wood.SIGN);
            list.add(wood.WALL_SIGN);
        });
    }
}
