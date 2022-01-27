package azmalent.terraincognita.common.block.plants;

import azmalent.terraincognita.common.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@SuppressWarnings("deprecation")
public class SourBerryBushBlock extends WaterlilyBlock implements BonemealableBlock {
    public static final IntegerProperty AGE = BlockStateProperties.AGE_3;

    public SourBerryBushBlock() {
        super(Block.Properties.of(Material.PLANT).sound(SoundType.SWEET_BERRY_BUSH).noCollission().instabreak());
        registerDefaultState(stateDefinition.any().setValue(AGE, 0));
    }

    @Override
    protected void createBlockStateDefinition(@NotNull StateDefinition.@NotNull Builder<Block, BlockState> builder) {
        super.createBlockStateDefinition(builder);
        builder.add(AGE);
    }

    @Override
    public boolean isRandomlyTicking(BlockState state) {
        return state.getValue(AGE) < 3;
    }

    @Override
    public void randomTick(BlockState state, @Nonnull ServerLevel level, @Nonnull BlockPos pos, @Nonnull Random random) {
        int i = state.getValue(AGE);
        if (i < 3 && level.getRawBrightness(pos.above(), 0) >= 9 && ForgeHooks.onCropsGrowPre(level, pos, state,random.nextInt(5) == 0)) {
            level.setBlock(pos, state.setValue(AGE, i + 1), 2);
            ForgeHooks.onCropsGrowPost(level, pos, state);
        }
    }

    @Nonnull
    @Override
    @ParametersAreNonnullByDefault
    public InteractionResult use(BlockState state, @NotNull Level level, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand handIn, @NotNull BlockHitResult hit) {
        int age = state.getValue(AGE);
        if (age < 3 && player.getItemInHand(handIn).getItem() == Items.BONE_MEAL) {
            return InteractionResult.PASS;
        }

        if (age > 1) {
            int amount = 1 + level.random.nextInt(2) + (age == 3 ? 1 : 0);
            popResource(level, pos, new ItemStack(ModItems.SOUR_BERRIES.get(), amount));
            level.playSound(null, pos, SoundEvents.SWEET_BERRY_BUSH_PICK_BERRIES, SoundSource.BLOCKS, 1.0F, 0.8F + level.random.nextFloat() * 0.4F);
            level.setBlock(pos, state.setValue(AGE, 1), 2);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return super.use(state, level, pos, player, handIn, hit);
    }

    @Nonnull
    @Override
    @ParametersAreNonnullByDefault
    public ItemStack getCloneItemStack(@NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull BlockState state) {
        return new ItemStack(ModItems.SOUR_BERRIES.get());
    }

    //IGrowable implementation
    @Override
    public boolean isValidBonemealTarget(@Nonnull BlockGetter level, @Nonnull BlockPos pos, BlockState state, boolean isClient) {
        return state.getValue(AGE) < 3;
    }

    @Override
    public boolean isBonemealSuccess(@Nonnull Level level, @Nonnull Random rand, @Nonnull BlockPos pos, @Nonnull BlockState state) {
        return true;
    }

    @Override
    public void performBonemeal(ServerLevel level, @Nonnull Random rand, @Nonnull BlockPos pos, BlockState state) {
        int age = Math.min(3, state.getValue(AGE) + 1);
        level.setBlock(pos, state.setValue(AGE, age), 2);
    }
}
