package azmalent.terraincognita.mixin;

import azmalent.terraincognita.core.registry.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.EatBlockGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraftforge.event.ForgeEventFactory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

/**
 * @reason Required for sheep to eat flowering grass.
 */
@Mixin(EatBlockGoal.class)
public class EatGrassGoalMixin {
    @Shadow @Final private Mob mob;

    @Shadow @Final private Level level;

    @Inject(method = "canUse", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/Mob;blockPosition()Lnet/minecraft/core/BlockPos;"), cancellable = true)
    private void shouldExecute(CallbackInfoReturnable<Boolean> cir) {
        BlockPos pos = this.mob.blockPosition();
        if (level.getBlockState(pos.below()).getBlock() == ModBlocks.FLOWERING_GRASS.get()) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/core/BlockPos;below()Lnet/minecraft/core/BlockPos;"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void tick(CallbackInfo ci, BlockPos pos) {
        BlockPos down = pos.below();
        if (level.getBlockState(down).getBlock() == ModBlocks.FLOWERING_GRASS.get()) {
            if (ForgeEventFactory.getMobGriefingEvent(level, mob)) {
                this.level.levelEvent(2001, down, Block.getId(ModBlocks.FLOWERING_GRASS.defaultBlockState()));
                this.level.setBlock(down, Blocks.DIRT.defaultBlockState(), 2);
            }

            this.mob.ate();
            this.mob.gameEvent(GameEvent.EAT, this.mob.eyeBlockPosition());
        }
    }
}
