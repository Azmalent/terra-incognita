package azmalent.terraincognita.mixin.compat.quark;

import azmalent.terraincognita.common.integration.ModIntegration;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Lantern;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Lantern.class)
public class LanternBlockMixin {
    @Inject(method = "isValidPosition", at = @At("HEAD"), cancellable = true)
    private void isValidPosition(BlockState state, LevelReader worldIn, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (ModIntegration.QUARK.canLanternConnect(state, worldIn, pos)) {
            cir.setReturnValue(true);
        }
    }
}
