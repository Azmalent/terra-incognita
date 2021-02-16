package azmalent.terraincognita.common.block.trees;

import azmalent.terraincognita.common.init.ModWoodTypes;
import com.google.common.collect.Lists;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.state.IntegerProperty;
import net.minecraft.state.StateContainer;
import net.minecraft.state.properties.BlockStateProperties;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Direction;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorld;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.Random;

@SuppressWarnings("deprecation")
public class AppleBlock extends AbstractFruitBlock {
    private static final VoxelShape SMALL_SHAPE = makeCuboidShape(6, 9, 6, 10, 13, 10);
    private static final VoxelShape BIG_SHAPE = makeCuboidShape(5, 7, 5, 11, 13, 11);

    public AppleBlock() {
        super(Block.Properties.create(Material.MISCELLANEOUS, MaterialColor.RED).hardnessAndResistance(0.2F).sound(SoundType.WOOD), () -> Items.APPLE);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        Vector3d offset = state.getOffset(worldIn, pos);
        return (state.get(AGE) < 2 ? SMALL_SHAPE : BIG_SHAPE).withOffset(offset.x, offset.y, offset.z);
    }

    @Nonnull
    @Override
    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockState up = worldIn.getBlockState(pos.up());
        return (up.isIn(ModWoodTypes.APPLE.LEAVES.getBlock()) || up.isIn(ModWoodTypes.APPLE.BLOSSOMING_LEAVES.getBlock())  && !up.get(LeavesBlock.PERSISTENT));
    }
}
