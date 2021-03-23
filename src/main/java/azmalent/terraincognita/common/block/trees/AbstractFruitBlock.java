package azmalent.terraincognita.common.block.trees;

import com.google.common.collect.Lists;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public abstract class AbstractFruitBlock extends Block {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_0_7;

    private final Supplier<Item> item;
    private final int growthChance;

    protected AbstractFruitBlock(AbstractBlock.Properties properties, Supplier<Item> item, int growthChance) {
        super(properties);
        this.item = item;
        this.growthChance = growthChance;

        this.setDefaultState(getStateContainer().getBaseState().with(AGE, 0));
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(AGE);
    }

    @Nonnull
    @Override
    public BlockState updatePostPlacement(@Nonnull BlockState stateIn, Direction facing, BlockState facingState, IWorld worldIn, BlockPos currentPos, BlockPos facingPos) {
        return !this.isValidPosition(stateIn, worldIn, currentPos) ? Blocks.AIR.getDefaultState() : super.updatePostPlacement(stateIn, facing, facingState, worldIn, currentPos, facingPos);
    }

    @Nonnull
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        if (worldIn.isRemote) {
            return ActionResultType.SUCCESS;
        }

        if (player.isSpectator()) {
            return ActionResultType.CONSUME;
        }

        if (state.get(AGE) == 7) {
            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState());

            ItemStack stack = new ItemStack(item.get());
            ItemEntity itemEntity = new ItemEntity(worldIn, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, stack);
            itemEntity.setDefaultPickupDelay();
            worldIn.addEntity(itemEntity);

            return ActionResultType.SUCCESS;
        }

        return super.onBlockActivated(state, worldIn, pos, player, handIn, hit);
    }

    @Override
    public boolean ticksRandomly(BlockState state) {
        return state.get(AGE) < 7;
    }

    @Override
    public void randomTick(@Nonnull BlockState state, @Nonnull ServerWorld worldIn, @Nonnull BlockPos pos, Random random) {
        if (ForgeHooks.onCropsGrowPre(worldIn, pos, state, random.nextInt(growthChance) == 0)) {
            worldIn.setBlockState(pos, state.with(AGE,state.get(AGE) + 1), 2);
            ForgeHooks.onCropsGrowPost(worldIn, pos, state);
        }
    }

    @Nonnull
    @Override
    public List<ItemStack> getDrops(BlockState state, @Nonnull LootContext.Builder builder) {
        List<ItemStack> drops = Lists.newArrayList();
        if (state.get(AGE) == 7) {
            drops.add(new ItemStack(item.get()));
        }

        return drops;
    }

    @Nonnull
    @Override
    public ItemStack getItem(IBlockReader worldIn, BlockPos pos, BlockState state) {
        return new ItemStack(item.get());
    }

    @Nonnull
    @Override
    public String getTranslationKey() {
        return item.get().getTranslationKey();
    }
}
