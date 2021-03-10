package azmalent.terraincognita.mixin.compat.quark;

import azmalent.terraincognita.common.integration.ModIntegration;
import net.minecraft.block.BlockState;
import net.minecraft.block.LanternBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LanternBlock.class)
public class LanternBlockMixin {
    @Inject(method = "isValidPosition", at = @At("HEAD"), cancellable = true)
    private void isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (ModIntegration.QUARK.canLanternConnect(state, worldIn, pos)) {
            cir.setReturnValue(true);
        }
    }
}
