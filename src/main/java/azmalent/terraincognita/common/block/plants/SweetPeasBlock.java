package azmalent.terraincognita.common.block.plants;

import azmalent.terraincognita.common.integration.ModIntegration;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.VineBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.state.BooleanProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nonnull;
import java.util.Random;

@SuppressWarnings("deprecation")
public class SweetPeasBlock extends VineBlock {
    public static final BooleanProperty BURNT = BooleanProperty.create("burnt");

    public SweetPeasBlock() {
        super(Properties.from(Blocks.VINE));
        setDefaultState(super.getDefaultState().with(BURNT, false));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        super.fillStateContainer(builder);
        builder.add(BURNT);
    }

    @Override
    public void randomTick(BlockState state, ServerWorld worldIn, @Nonnull BlockPos pos, @Nonnull Random random) {
        if (!state.get(BURNT)) {
            super.randomTick(state, worldIn, pos, random);
        }
    }

    public BlockState getConnections(BlockState state, IBlockReader blockReader, BlockPos pos) {
        BlockPos blockpos = pos.up();
        if (state.get(UP)) {
            state = state.with(UP, canAttachTo(blockReader, blockpos, Direction.DOWN));
        }

        for(Direction direction : Direction.Plane.HORIZONTAL) {
            BooleanProperty property = getPropertyFor(direction);
            if (state.get(property)) {
                boolean attached;
                if (canAttachTo(blockReader, pos.offset(direction), direction)) {
                    attached = true;
                } else {
                    BlockState vineAbove = blockReader.getBlockState(pos.up());
                    attached = vineAbove.isIn(this) && !vineAbove.get(BURNT) && vineAbove.get(property);
                }

                state = state.with(property, attached);
            }
        }

        return state;
    }

    @Nonnull
    @Override
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (ModIntegration.QUARK.canBurnVineTips()) {
            Item heldItem = player.getHeldItem(hand).getItem();
            if (!state.get(BURNT) && (heldItem == Items.FLINT_AND_STEEL || heldItem == Items.FIRE_CHARGE)) {
                BlockState newState = state.with(BURNT, true);
                world.setBlockState(pos, newState);

                world.playSound(player, pos, SoundEvents.ITEM_FLINTANDSTEEL_USE, SoundCategory.PLAYERS, 0.5F, 1F);
                if (world instanceof ServerWorld) {
                    ServerWorld serverWorld = (ServerWorld) world;
                    serverWorld.spawnParticle(ParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 5, 0.25, 0.25, 0.25, 0.01);
                    serverWorld.spawnParticle(ParticleTypes.SMOKE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 20, 0.25, 0.25, 0.25, 0.01);
                }

                return ActionResultType.SUCCESS;
            }
        }

        return super.onBlockActivated(state, world, pos, player, hand, hit);
    }
}
