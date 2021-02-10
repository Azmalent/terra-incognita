package azmalent.terraincognita.common.init;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.world.treedecorator.AppleTreeDecorator;
import azmalent.terraincognita.mixin.accessor.TreeDecoratorTypeAccessor;
import com.mojang.serialization.Codec;
import net.minecraft.world.gen.treedecorator.TreeDecorator;
import net.minecraft.world.gen.treedecorator.TreeDecoratorType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModTreeDecorators {
    public static final DeferredRegister<TreeDecoratorType<?>> TREE_DECORATORS = DeferredRegister.create(ForgeRegistries.TREE_DECORATOR_TYPES, TerraIncognita.MODID);

    public static final RegistryObject<TreeDecoratorType<AppleTreeDecorator>> APPLES = register("apples", AppleTreeDecorator.CODEC);

    private static <P extends TreeDecorator> RegistryObject<TreeDecoratorType<P>> register(String id, Codec<P> codec) {
        return TREE_DECORATORS.register(id, () -> TreeDecoratorTypeAccessor.constructor(codec));
    }
}
