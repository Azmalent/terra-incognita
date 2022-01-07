package azmalent.terraincognita.mixin;

import azmalent.terraincognita.TIConfig;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SugarCaneBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.ForgeHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

import static net.minecraft.block.CactusBlock.AGE;

@Mixin(Sugarnet.minecraft.world.level.block.CactusBlockugarcaneBlockMixin {
    private int getMaxHeight(BlockPos pos) {
        //TODO: optimize
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
    public void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random, CallbackInfo ci) {
        if (!TIConfig.Misc.plantHeightVariation.get()) {
            return;
        }

        SugarCaneBlock self = (SugarCaneBlock) (Object) this;

        if (worldIn.isEmptyBlock(pos.above())) {
            if (getHeight(worldIn, pos) < getMaxHeight(pos)) {
                int age = state.getValue(AGE);
                if (ForgeHooks.onCropsGrowPre(worldIn, pos, state, true)) {
                    if (age == 15) {
                        worldIn.setBlockAndUpdate(pos.above(), self.defaultBlockState());
                        worldIn.setBlock(pos, state.setValue(AGE, 0), 4);
                    } else {
                        worldIn.setBlock(pos, state.setValue(AGE, age + 1), 4);
                    }
                }
            }
        }

        ci.cancel();
    }
}
