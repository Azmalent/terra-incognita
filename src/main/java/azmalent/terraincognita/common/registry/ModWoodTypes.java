package azmalent.terraincognita.common.registry;

import azmalent.terraincognita.common.block.woodtypes.AppleWoodType;
import azmalent.terraincognita.common.block.woodtypes.HazelWoodType;
import azmalent.terraincognita.common.block.woodtypes.ModWoodType;
import azmalent.terraincognita.common.block.trees.AppleTree;
import azmalent.terraincognita.common.block.trees.HazelTree;
import com.google.common.collect.Lists;
import net.minecraft.block.Block;
import net.minecraft.block.material.MaterialColor;

import java.util.List;
import java.util.Map;
import java.util.function.BiConsumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class ModWoodTypes {
    public static final AppleWoodType APPLE = new AppleWoodType("apple", new AppleTree(), MaterialColor.WOOD, MaterialColor.ORANGE_TERRACOTTA);
    public static final HazelWoodType HAZEL = new HazelWoodType("hazel", new HazelTree(), MaterialColor.WOOD, MaterialColor.BROWN);

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
    private static Supplier<Block>[] getWoodBlockSuppliers(BiConsumer<List<Supplier<Block>>, ModWoodType> func) {
        List<Supplier<Block>> suppliers = Lists.newArrayList();
        ModWoodTypes.VALUES.forEach(woodType -> func.accept(suppliers, woodType));

        return suppliers.toArray(new Supplier[0]);
    }

    public static Supplier<Block>[] getChests() {
        return getWoodBlockSuppliers((list, wood) -> list.add(wood.CHEST::getBlock));
    }

    public static Supplier<Block>[] getTrappedChests() {
        return getWoodBlockSuppliers((list, wood) -> list.add(wood.TRAPPED_CHEST::getBlock));
    }

    public static Supplier<Block>[] getSigns() {
        return getWoodBlockSuppliers((list, wood) -> {
            list.add(wood.SIGN::getBlock);
            list.add(wood.WALL_SIGN::getBlock);
        });
    }
}
