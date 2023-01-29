package azmalent.terraincognita.common.registry;

import azmalent.terraincognita.common.world.tree.decorator.AppleDecorator;
import azmalent.terraincognita.common.world.tree.decorator.HazelnutDecorator;
import azmalent.terraincognita.mixin.accessor.TreeDecoratorTypeAccessor;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static azmalent.terraincognita.TerraIncognita.REGISTRY_HELPER;

public class ModTreeDecorators {
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATORS = REGISTRY_HELPER.getOrCreateRegistry(ForgeRegistries.TREE_DECORATOR_TYPES);

    public static final RegistryObject<TreeDecoratorType<AppleDecorator>> APPLES = register("apples", AppleDecorator.CODEC);
    public static final RegistryObject<TreeDecoratorType<HazelnutDecorator>> HAZELNUTS = register("hazelnuts", HazelnutDecorator.CODEC);

    private static <P extends TreeDecorator> RegistryObject<TreeDecoratorType<P>> register(String id, Codec<P> codec) {
        return TREE_DECORATORS.register(id, () -> TreeDecoratorTypeAccessor.ti_constructor(codec));
    }
}
