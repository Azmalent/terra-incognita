package azmalent.terraincognita.common.registry;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.world.tree.decorator.AppleTreeDecorator;
import azmalent.terraincognita.common.world.tree.decorator.HazelnutTreeDecorator;
import azmalent.terraincognita.mixin.accessor.TreeDecoratorTypeAccessor;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTreeDecorators {
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATORS = TerraIncognita.REG_HELPER.getOrCreateRegistry(ForgeRegistries.TREE_DECORATOR_TYPES);

    public static final RegistryObject<TreeDecoratorType<AppleTreeDecorator>> APPLES = register("apples", AppleTreeDecorator.CODEC);
    public static final RegistryObject<TreeDecoratorType<HazelnutTreeDecorator>> HAZELNUTS = register("hazelnuts", HazelnutTreeDecorator.CODEC);

    private static <P extends TreeDecorator> RegistryObject<TreeDecoratorType<P>> register(String id, Codec<P> codec) {
        return TREE_DECORATORS.register(id, () -> TreeDecoratorTypeAccessor.ti_constructor(codec));
    }
}
