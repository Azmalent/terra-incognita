package azmalent.terraincognita.common.block.plants;

import azmalent.terraincognita.common.integration.ModIntegration;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.VineBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;

import javax.annotation.Nonnull;
import java.util.Random;

import net.minecraft.world.level.block.state.BlockBehaviour.Properties;

import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;

@SuppressWarnings("deprecation")
public class SweetPeasBlock extends VineBlock {
    public static final BooleanProperty BURNT = BooleanProperty.create("burnt");

    public SweetPeasBlock() {
        super(Properties.copy(Blocks.VINE));
        registerDefaultState(super.defaultBlockState().setValue(BURNT, false));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(BURNT);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel worldIn, @Nonnull BlockPos pos, @Nonnull Random random) {
        if (!state.getValue(BURNT)) {
            super.randomTick(state, worldIn, pos, random);
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
                    attached = vineAbove.is(this) && !vineAbove.getValue(BURNT) && vineAbove.getValue(property);
                }

                state = state.setValue(property, attached);
            }
        }

        return state;
    }

    @Nonnull
    @Override
    public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        if (ModIntegration.QUARK.canBurnVineTips()) {
            Item heldItem = player.getItemInHand(hand).getItem();
            if (!state.getValue(BURNT) && (heldItem == Items.FLINT_AND_STEEL || heldItem == Items.FIRE_CHARGE)) {
                BlockState newState = state.setValue(BURNT, true);
                world.setBlockAndUpdate(pos, newState);

                world.playSound(player, pos, SoundEvents.FLINTANDSTEEL_USE, SoundSource.PLAYERS, 0.5F, 1F);
                if (world instanceof ServerLevel) {
                    ServerLevel serverWorld = (ServerLevel) world;
                    serverWorld.sendParticles(ParticleTypes.FLAME, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 5, 0.25, 0.25, 0.25, 0.01);
                    serverWorld.sendParticles(ParticleTypes.SMOKE, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 20, 0.25, 0.25, 0.25, 0.01);
                }

                return InteractionResult.SUCCESS;
            }
        }

        return super.use(state, world, pos, player, hand, hit);
    }
}
