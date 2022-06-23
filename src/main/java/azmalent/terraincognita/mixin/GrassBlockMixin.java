package azmalent.terraincognita.mixin;

import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.GrassBlock;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Random;

/**
 * @reason Required for Terra Incognita (and other modded) flowers to spawn when bonemealing.
 */
@Mixin(GrassBlock.class)
public class GrassBlockMixin {
    private Random argRandom;

    @Inject(method = "performBonemeal", at = @At("HEAD"))
    public void storeRandom(ServerLevel level, Random random, BlockPos pos, BlockState state, CallbackInfo ci) {
        argRandom = random;
    }

    @Redirect(method = "performBonemeal", at = @At(value = "INVOKE", target = "Ljava/util/List;get(I)Ljava/lang/Object;"))
    public <T> T get(List<T> self, int index) {
        return Util.getRandom(self, argRandom);
    }
}
