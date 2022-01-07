package azmalent.terraincognita.mixin;

import azmalent.terraincognita.common.registry.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.EatBlockGoal;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.ForgeEventFactory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(EatBlockGoal.class)
public class EatGrassGoalMixin {
    @Shadow @Final private Mob grassEaterEntity;

    @Shadow @Final private Level entityWorld;

    @Inject(method = "shouldExecute", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/MobEntity;getPosition()Lnet/minecraft/util/math/BlockPos;"), cancellable = true)
    private void shouldExecute(CallbackInfoReturnable<Boolean> cir) {
        BlockPos pos = this.grassEaterEntity.blockPosition();
        if (entityWorld.getBlockState(pos.below()).getBlock() == ModBlocks.FLOWERING_GRASS.getBlock()) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/BlockPos;down()Lnet/minecraft/util/math/BlockPos;"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void tick(CallbackInfo ci, BlockPos blockpos) {
        BlockPos down = blockpos.below();
        if (entityWorld.getBlockState(down).getBlock() == ModBlocks.FLOWERING_GRASS.getBlock()) {
            if (ForgeEventFactory.getMobGriefingEvent(entityWorld, grassEaterEntity)) {
                this.entityWorld.levelEvent(2001, down, Block.getId(ModBlocks.FLOWERING_GRASS.getDefaultState()));
                this.entityWorld.setBlock(down, Blocks.DIRT.defaultBlockState(), 2);
            }

            this.grassEaterEntity.ate();
        }
    }
}
