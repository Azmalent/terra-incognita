package azmalent.terraincognita.mixin;

import azmalent.terraincognita.common.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.event.ForgeEventFactory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(EatGrassGoal.class)
public class EatGrassGoalMixin {
    @Shadow @Final private MobEntity grassEaterEntity;

    @Shadow @Final private World entityWorld;

    @Inject(method = "shouldExecute", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/MobEntity;getPosition()Lnet/minecraft/util/math/BlockPos;"), cancellable = true)
    private void shouldExecute(CallbackInfoReturnable<Boolean> cir) {
        BlockPos pos = this.grassEaterEntity.getPosition();
        if (entityWorld.getBlockState(pos.down()).getBlock() == ModBlocks.FLOWERING_GRASS.getBlock()) {
            cir.setReturnValue(true);
        }
    }

    @Inject(method = "tick", at = @At(value = "INVOKE", target = "Lnet/minecraft/util/math/BlockPos;down()Lnet/minecraft/util/math/BlockPos;"), locals = LocalCapture.CAPTURE_FAILHARD)
    private void tick(CallbackInfo ci, BlockPos blockpos) {
        BlockPos down = blockpos.down();
        if (entityWorld.getBlockState(down).getBlock() == ModBlocks.FLOWERING_GRASS.getBlock()) {
            if (ForgeEventFactory.getMobGriefingEvent(entityWorld, grassEaterEntity)) {
                this.entityWorld.playEvent(2001, down, Block.getStateId(ModBlocks.FLOWERING_GRASS.getDefaultState()));
                this.entityWorld.setBlockState(down, Blocks.DIRT.getDefaultState(), 2);
            }

            this.grassEaterEntity.eatGrassBonus();
        }
    }
}
