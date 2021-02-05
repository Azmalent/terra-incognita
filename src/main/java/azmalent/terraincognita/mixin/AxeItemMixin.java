package azmalent.terraincognita.mixin;

import azmalent.terraincognita.common.init.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.RotatedPillarBlock;
import net.minecraft.item.AxeItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AxeItem.class)
public class AxeItemMixin {
    @Inject(method = "getAxeStrippingState", at = @At(value = "TAIL"), cancellable = true, remap = false)
    private static void getAxeStrippingState(BlockState originalState, CallbackInfoReturnable<BlockState> cir) {
        if (cir.getReturnValue() == null) {
            Block block = ModBlocks.STRIPPING_MAP.get(originalState.getBlock());
            if (block != null) {
                BlockState state = block.getDefaultState().with(RotatedPillarBlock.AXIS, originalState.get(RotatedPillarBlock.AXIS));
                cir.setReturnValue(state);
            }
        }
    }
}
