package azmalent.terraincognita.common.registry;

import azmalent.terraincognita.common.world.tree.AppleTree;
import azmalent.terraincognita.common.world.tree.GinkgoTree;
import azmalent.terraincognita.common.world.tree.HazelTree;
import azmalent.terraincognita.common.woodtype.AppleWoodType;
import azmalent.terraincognita.common.woodtype.HazelWoodType;
import azmalent.terraincognita.common.woodtype.TIWoodType;
import azmalent.terraincognita.common.world.tree.LarchTree;
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
    public static final TIWoodType LARCH    = new TIWoodType("larch", new LarchTree(), MaterialColor.WOOD, MaterialColor.WOOD);
    public static final TIWoodType GINKGO   = new TIWoodType("ginkgo", new GinkgoTree(), MaterialColor.WOOD, MaterialColor.COLOR_LIGHT_GREEN);

    public static List<TIWoodType> VALUES;
    public static Map<String, TIWoodType> VALUES_BY_NAME;

    public static void init() {
        VALUES = Lists.newArrayList(APPLE, HAZEL, LARCH, GINKGO);
        VALUES_BY_NAME = VALUES.stream().collect(Collectors.toMap(type -> type.name, type -> type));
    }

    public static TIWoodType byName(String name) {
        return VALUES_BY_NAME.get(name);
    }

    private static List<Supplier<? extends Block>> getWoodBlockSuppliers(BiConsumer<List<Supplier<? extends Block>>, TIWoodType> func) {
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
}
