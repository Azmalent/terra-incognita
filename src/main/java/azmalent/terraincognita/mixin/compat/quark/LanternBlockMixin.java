package azmalent.terraincognita.mixin.compat.quark;

import azmalent.terraincognita.common.integration.ModIntegration;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LanternBlock.class)
public class LanternBlockMixin {
    @Inject(method = "canSurvive", at = @At("HEAD"), cancellable = true)
    private void canSurvive(BlockState state, LevelReader level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (ModIntegration.QUARK.canLanternConnect(state, level, pos)) {
            cir.setReturnValue(true);
        }
    }
}
