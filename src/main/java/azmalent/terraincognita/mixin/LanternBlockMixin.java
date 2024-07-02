package azmalent.terraincognita.mixin;

import azmalent.terraincognita.common.block.woodset.TIWoodPostBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * @reason Required for lanterns to connect to Terra Incognita wooden posts.
 */
@Mixin(LanternBlock.class)
public class LanternBlockMixin {
    @Inject(method = "canSurvive", at = @At("HEAD"), cancellable = true)
    private void canSurvive(BlockState state, LevelReader level, BlockPos pos, CallbackInfoReturnable<Boolean> cir) {
        if (state.getValue(LanternBlock.HANGING) && (level.getBlockState(pos.above()).getBlock() instanceof TIWoodPostBlock)) {
            cir.setReturnValue(true);
        }
    }
}
