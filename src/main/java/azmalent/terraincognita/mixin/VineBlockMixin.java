package azmalent.terraincognita.mixin;

import azmalent.terraincognita.common.block.plants.SweetPeasBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.VineBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(VineBlock.class)
public class VineBlockMixin {
    @Inject(method = "func_196545_h", at = @At("HEAD"), cancellable = true)
    private void getConnections(BlockState state, IBlockReader blockReader, BlockPos pos, CallbackInfoReturnable<BlockState> cir) {
        if (state.getBlock() instanceof SweetPeasBlock) {
            SweetPeasBlock self = (SweetPeasBlock) (Object) this;
            cir.setReturnValue(self.getConnections(state, blockReader, pos));
        }
    }
}
