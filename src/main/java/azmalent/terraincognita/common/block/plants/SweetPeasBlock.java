package azmalent.terraincognita.common.block.plants;

import azmalent.cuneiform.lib.util.ItemUtil;
import azmalent.terraincognita.common.integration.ModIntegration;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ToolActions;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import java.util.Random;

@SuppressWarnings("deprecation")
public class SweetPeasBlock extends VineBlock {
    public static final BooleanProperty CUT = BooleanProperty.create("cut");

    public SweetPeasBlock() {
        super(Properties.copy(Blocks.VINE));
        registerDefaultState(super.defaultBlockState().setValue(CUT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(CUT);
    }

    @Override
    public void randomTick(BlockState state, @NotNull ServerLevel level, @Nonnull BlockPos pos, @Nonnull Random random) {
        if (!state.getValue(CUT)) {
            super.randomTick(state, level, pos, random);
        }
    }

    public BlockState getConnections(BlockState state, BlockGetter blockReader, BlockPos pos) {
        BlockPos blockpos = pos.above();
        if (state.getValue(UP)) {
            state = state.setValue(UP, isAcceptableNeighbour(blockReader, blockpos, Direction.DOWN));
        }

        for(Direction direction : Direction.Plane.HORIZONTAL) {
            BooleanProperty property = getPropertyForFace(direction);
            if (state.getValue(property)) {
                boolean attached;
                if (isAcceptableNeighbour(blockReader, pos.relative(direction), direction)) {
                    attached = true;
                } else {
                    BlockState vineAbove = blockReader.getBlockState(pos.above());
                    attached = vineAbove.is(this) && !vineAbove.getValue(CUT) && vineAbove.getValue(property);
                }

                state = state.setValue(property, attached);
            }
        }

        return state;
    }

    @Nonnull
    @Override
    public InteractionResult use(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (ModIntegration.QUARK.canCutVines()) {
            ItemStack heldItem = player.getItemInHand(hand);
            if (!state.getValue(CUT) && heldItem.canPerformAction(ToolActions.SHEARS_CARVE)) {
                BlockState newState = state.setValue(CUT, true);
                world.setBlockAndUpdate(pos, newState);

                world.playSound(player, pos, SoundEvents.SHEEP_SHEAR, SoundSource.PLAYERS, 0.5F, 1F);
                ItemUtil.damageHeldItem(player, hand);

                return InteractionResult.SUCCESS;
            }
        }

        return super.use(state, world, pos, player, hand, hit);
    }
}
