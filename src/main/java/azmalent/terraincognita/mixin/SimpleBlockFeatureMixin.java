package azmalent.terraincognita.mixin;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.block.plant.SmallLilyPadBlock;
import azmalent.terraincognita.core.registry.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.SimpleBlockFeature;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Random;

/**
 * @reason Required for dandelion puffs and small lily pads to generate.
 */
@Mixin(SimpleBlockFeature.class)
public class SimpleBlockFeatureMixin {
    @Redirect(method = "place", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/feature/stateproviders/BlockStateProvider;getState(Ljava/util/Random;Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;"))
    private BlockState getBlockToPlace(BlockStateProvider self, Random random, BlockPos blockPos) {
        BlockState blockState = self.getState(random, blockPos);

        if (blockState.is(Blocks.DANDELION) && TIConfig.Flora.dandelionPuff.get()) {
            if (random.nextFloat() < TIConfig.Flora.dandelionPuffChance.get()) {
                return ModBlocks.DANDELION_PUFF.defaultBlockState();
            }
        } else if (blockState.is(Blocks.LILY_PAD) && TIConfig.Flora.smallLilyPads.get()) {
            if (random.nextFloat() < TIConfig.Flora.smallLilyPadChance.get()) {
                var state = ModBlocks.SMALL_LILY_PAD.defaultBlockState();
                return state.setValue(SmallLilyPadBlock.LILY_PADS, random.nextInt(1, 5));
            }
        }

        return blockState;
    }
}
