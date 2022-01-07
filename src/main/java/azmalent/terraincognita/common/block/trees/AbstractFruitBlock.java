package azmalent.terraincognita.common.block.trees;

import com.google.common.collect.Lists;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

import net.minecraft.core.Direction;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;

@SuppressWarnings("deprecation")
public abstract class AbstractFruitBlock extends Block {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_7;

    private final Supplier<Item> item;
    private final int growthChance;

    protected AbstractFruitBlock(BlockBehaviour.Properties properties, Supplier<Item> item, int growthChance) {
        super(properties);
        this.item = item;
        this.growthChance = growthChance;

        this.registerDefaultState(getStateDefinition().any().setValue(AGE, 0));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Nonnull
    @Override
    public BlockState updateShape(@Nonnull BlockState stateIn, Direction facing, BlockState facingState, LevelAccessor worldIn, BlockPos currentPos, BlockPos facingPos) {
        return !this.canSurvive(stateIn, worldIn, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Nonnull
    @Override
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        if (worldIn.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        if (player.isSpectator()) {
            return InteractionResult.CONSUME;
        }

        if (state.getValue(AGE) == 7) {
            worldIn.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());

            ItemStack stack = new ItemStack(item.get());
            ItemEntity itemEntity = new ItemEntity(worldIn, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, stack);
            itemEntity.setDefaultPickUpDelay();
            worldIn.addFreshEntity(itemEntity);

            return InteractionResult.SUCCESS;
        }

        return super.use(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(AGE) < 7;
    }

    @Override
    public void randomTick(@Nonnull BlockState state, @Nonnull ServerLevel worldIn, @Nonnull BlockPos pos, Random random) {
        if (ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt(growthChance) == 0)) {
            worldIn.setBlock(pos, state.setValue(AGE,state.getValue(AGE) + 1), 2);
            ForgeHooks.onCropsGrowPost(worldIn, pos, state);
        }
    }

    @Nonnull
    @Override
    public List<ItemStack> getDrops(BlockState state, @Nonnull LootContext.Builder builder) {
        List<ItemStack> drops = Lists.newArrayList();
        if (state.getValue(AGE) == 7) {
            drops.add(new ItemStack(item.get()));
        }

        return drops;
    }

    @Nonnull
    @Override
    public ItemStack getCloneItemStack(BlockGetter worldIn, BlockPos pos, BlockState state) {
        return new ItemStack(item.get());
    }

    @Nonnull
    @Override
    public String getDescriptionId() {
        return item.get().getDescriptionId();
    }
}
