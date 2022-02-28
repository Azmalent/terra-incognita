package azmalent.terraincognita.common.block.fruit;

import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Random;
import java.util.function.Supplier;

@SuppressWarnings("deprecation")
public abstract class AbstractFruitBlock extends Block implements BonemealableBlock {
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
    public BlockState updateShape(@Nonnull BlockState stateIn, @NotNull Direction facing, @NotNull BlockState facingState, @NotNull LevelAccessor level, @NotNull BlockPos currentPos, @NotNull BlockPos facingPos) {
        return !this.canSurvive(stateIn, level, currentPos) ? Blocks.AIR.defaultBlockState() : super.updateShape(stateIn, facing, facingState, level, currentPos, facingPos);
    }

    @Nonnull
    @Override
    public InteractionResult use(@NotNull BlockState state, Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand handIn, @NotNull BlockHitResult hit) {
        if (level.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        if (player.isSpectator()) {
            return InteractionResult.CONSUME;
        }

        if (state.getValue(AGE) == 7) {
            level.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());

            ItemStack stack = new ItemStack(item.get());
            ItemEntity itemEntity = new ItemEntity(level, pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D, stack);
            itemEntity.setDefaultPickUpDelay();
            level.addFreshEntity(itemEntity);

            return InteractionResult.SUCCESS;
        }

        return super.use(state, level, pos, player, handIn, hit);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(AGE) < 7;
    }

    @Override
    public void randomTick(@Nonnull BlockState state, @Nonnull ServerLevel level, @Nonnull BlockPos pos, Random random) {
        if (ForgeHooks.onCropsGrowPre(level, pos, state, random.nextInt(growthChance) == 0)) {
            level.setBlock(pos, state.setValue(AGE,state.getValue(AGE) + 1), 2);
            ForgeHooks.onCropsGrowPost(level, pos, state);
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
    public ItemStack getCloneItemStack(@NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull BlockState state) {
        return new ItemStack(item.get());
    }

    @Nonnull
    @Override
    public String getDescriptionId() {
        return item.get().getDescriptionId();
    }

    //IGrowable implementation
    @Override
    @ParametersAreNonnullByDefault
    public boolean isValidBonemealTarget(BlockGetter level, BlockPos pos, BlockState state, boolean isClient) {
        return state.getValue(AGE) < 7;
    }

    @ParametersAreNonnullByDefault
    public boolean isBonemealSuccess(Level level, Random random, BlockPos pos, BlockState state) {
        return true;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void performBonemeal(ServerLevel level, Random random, BlockPos pos, BlockState state) {
        int age = Math.min(state.getValue(AGE) + Mth.nextInt(random,2, 6), 7);
        level.setBlock(pos, state.setValue(AGE, age), 2);
    }
}
