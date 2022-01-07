package azmalent.terraincognita.mixin.accessor;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecorator;
import net.minecraft.world.level.levelgen.feature.treedecorators.TreeDecoratorType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TreeDecoratorType.class)
public interface TreeDecoratorTypeAccessor {
    @Invoker("<init>")
    @SuppressWarnings("unused")
    static <P extends TreeDecorator> TreeDecoratorType<P> ti_constructor(Codec<P> codec) {
        throw new AssertionError("Failed to apply mixin!");
    }
}
