package azmalent.terraincognita.mixin;

import azmalent.terraincognita.TIServerConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

/**
 * @reason Required for height variation. Also affects swamp reeds as it's a subclass of SugarCaneBlock
 */
@Mixin(SugarCaneBlock.class)
public class SugarCaneBlockMixin {
    private int getMaxHeight(BlockPos pos) {
        return new Random(pos.asLong()).nextInt(3) + 2;
    }

    private int getHeight(ServerLevel world, BlockPos pos) {
        Block topBlock = world.getBlockState(pos).getBlock();

        int height = 1;
        while (world.getBlockState(pos.below(height)).getBlock() == topBlock) {
            height++;
        }

        return height;
    }

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random, CallbackInfo ci) {
        if (!TIServerConfig.plantHeightVariation.get()) {
            return;
        }

        SugarCaneBlock self = (SugarCaneBlock) (Object) this;

        if (level.isEmptyBlock(pos.above())) {
            if (getHeight(level, pos) < getMaxHeight(pos)) {
                int age = state.getValue(SugarCaneBlock.AGE);
                if (ForgeHooks.onCropsGrowPre(level, pos, state, true)) {
                    if (age == 15) {
                        level.setBlockAndUpdate(pos.above(), self.defaultBlockState());
                        level.setBlock(pos, state.setValue(SugarCaneBlock.AGE, 0), 4);
                    } else {
                        level.setBlock(pos, state.setValue(SugarCaneBlock.AGE, age + 1), 4);
                    }
                }
            }
        }

        ci.cancel();
    }
}
