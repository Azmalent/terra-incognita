package azmalent.terraincognita.common.block.trees;

import azmalent.terraincognita.common.registry.ModWoodTypes;
import net.minecraft.block.*;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.item.Items;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;

import javax.annotation.Nonnull;

import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;

@SuppressWarnings("deprecation")
public class AppleBlock extends AbstractFruitBlock {
    private static final VoxelShape SMALL_SHAPE = box(6, 9, 6, 10, 13, 10);
    private static final VoxelShape BIG_SHAPE = box(5, 7, 5, 11, 13, 11);

    public AppleBlock() {
        super(Block.Properties.of(Material.DECORATION, MaterialColor.COLOR_RED).strength(0.2F).sound(SoundType.WOOD), () -> Items.APPLE, 25);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter worldIn, BlockPos pos, CollisionContext context) {
        Vec3 offset = state.getOffset(worldIn, pos);
        return (state.getValue(AGE) < 2 ? SMALL_SHAPE : BIG_SHAPE).move(offset.x, offset.y, offset.z);
    }

    @Nonnull
    @Override
    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }

    @Override
    public boolean canSurvive(BlockState state, LevelReader worldIn, BlockPos pos) {
        BlockState up = worldIn.getBlockState(pos.above());
        return (up.is(ModWoodTypes.APPLE.LEAVES.getBlock()) || up.is(ModWoodTypes.APPLE.BLOSSOMING_LEAVES.getBlock())  && !up.getValue(LeavesBlock.PERSISTENT));
    }
}
