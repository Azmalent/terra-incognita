package azmalent.terraincognita.mixin;

import azmalent.terraincognita.TIConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SugarCaneBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Random;

import static net.minecraft.block.CactusBlock.AGE;

@Mixin(SugarCaneBlock.class)
public class SugarcaneBlockMixin {
    private int getMaxHeight(BlockPos pos) {
        //TODO: optimize
        return new Random(pos.toLong()).nextInt(3) + 2;
    }

    private int getHeight(ServerWorld world, BlockPos pos) {
        Block topBlock = world.getBlockState(pos).getBlock();

        int height = 1;
        while (world.getBlockState(pos.down(height)).getBlock() == topBlock) {
            height++;
        }

        return height;
    }

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    public void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random, CallbackInfo ci) {
        if (!TIConfig.Misc.plantHeightVariation.get()) {
            return;
        }

        SugarCaneBlock self = (SugarCaneBlock) (Object) this;

        if (worldIn.isAirBlock(pos.up())) {
            if (getHeight(worldIn, pos) < getMaxHeight(pos)) {
                int age = state.get(AGE);
                if (ForgeHooks.onCropsGrowPre(worldIn, pos, state, true)) {
                    if (age == 15) {
                        worldIn.setBlockState(pos.up(), self.getDefaultState());
                        worldIn.setBlockState(pos, state.with(AGE, 0), 4);
                    } else {
                        worldIn.setBlockState(pos, state.with(AGE, age + 1), 4);
                    }
                }
            }
        }

        ci.cancel();
    }
}
