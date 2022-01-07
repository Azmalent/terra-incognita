package azmalent.terraincognita.mixin.accessor;

import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(BlockStateProviderType.class)
public interface BlockStateProviderTypeAccessor {
    @Invoker("<init>")
    @SuppressWarnings("unused")
    static <P extends BlockStateProvider> BlockStateProviderType<P> ti_constructor(Codec<P> codec) {
        throw new AssertionError("Failed to apply mixin!");
    }
}
