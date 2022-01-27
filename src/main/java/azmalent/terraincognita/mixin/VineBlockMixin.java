package azmalent.terraincognita.mixin;

import azmalent.terraincognita.common.block.plants.SweetPeasBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VineBlock.class)
public class VineBlockMixin {
    @SuppressWarnings("ConstantConditions")
    @Inject(method = "getUpdatedState", at = @At("HEAD"), cancellable = true)
    private void getConnections(BlockState state, BlockGetter blockReader, BlockPos pos, CallbackInfoReturnable<BlockState> cir) {
        if (state.getBlock() instanceof SweetPeasBlock) {
            SweetPeasBlock self = (SweetPeasBlock) (Object) this;
            cir.setReturnValue(self.getConnections(state, blockReader, pos));
        }
    }
}
