package azmalent.terraincognita.common.block.plant;

import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraftforge.common.ForgeHooks;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Random;

@SuppressWarnings("deprecation")
public class SourBerrySproutBlock extends TILilyPadBlock implements BonemealableBlock {
    protected static final VoxelShape SHAPE = Block.box(4.0D, 0.0D, 4.0D, 12.0D, 1.5D, 12.0D);

    public SourBerrySproutBlock() {
        super(Block.Properties.of(Material.PLANT).sound(SoundType.SWEET_BERRY_BUSH).noCollission().instabreak().randomTicks());
    }

    @Override
    @ParametersAreNonnullByDefault
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    @Nonnull
    @Override
    @ParametersAreNonnullByDefault
    public ItemStack getCloneItemStack(@NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull BlockState state) {
        return new ItemStack(ModItems.SOUR_BERRIES.get());
    }

    @Override
    @ParametersAreNonnullByDefault
    public void randomTick(@NotNull BlockState state, @NotNull ServerLevel level, @NotNull BlockPos pos, @NotNull Random random) {
        super.randomTick(state, level, pos, random);
        if (level.getRawBrightness(pos, 0) >= 9 && ForgeHooks.onCropsGrowPre(level, pos, state,random.nextInt(5) == 0)) {
            level.setBlock(pos, ModBlocks.SOUR_BERRY_BUSH.defaultBlockState(), 2);
            ForgeHooks.onCropsGrowPost(level, pos, state);
        }
    }

    //BonemealableBlock implementation
    @Override
    @ParametersAreNonnullByDefault
    public boolean isValidBonemealTarget(@NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull BlockState state, boolean isClient) {
        return true;
    }

    @Override
    @ParametersAreNonnullByDefault
    public boolean isBonemealSuccess(@NotNull Level level, @NotNull Random rand, @NotNull BlockPos pos, @NotNull BlockState state) {
        return true;
    }

    @Override
    @ParametersAreNonnullByDefault
    public void performBonemeal(ServerLevel level, @NotNull Random rand, @NotNull BlockPos pos, @NotNull BlockState state) {
        level.setBlock(pos, ModBlocks.SOUR_BERRY_BUSH.defaultBlockState(), 2);
    }
}
