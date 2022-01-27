package azmalent.terraincognita.common.block.trees;

import azmalent.terraincognita.common.registry.ModItems;
import azmalent.terraincognita.common.registry.ModWoodTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;

import javax.annotation.Nonnull;

import net.minecraft.world.level.block.state.BlockBehaviour.OffsetType;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("deprecation")
public class HazelnutBlock extends AbstractFruitBlock {
    private static final VoxelShape SHAPE = box(6, 12, 6, 10, 16, 10);

    public HazelnutBlock() {
        super(Block.Properties.of(Material.DECORATION).strength(0.2F).sound(SoundType.WOOD), ModItems.HAZELNUT, 10);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        Vec3 offset = state.getOffset(level, pos);
        return SHAPE.move(offset.x, offset.y, offset.z);
    }

    @Nonnull
    @Override
    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }

    @Override
    public boolean canSurvive(@NotNull BlockState state, LevelReader level, BlockPos pos) {
        BlockState up = level.getBlockState(pos.above());
        return up.is(ModWoodTypes.HAZEL.LEAVES.get()) && !up.getValue(LeavesBlock.PERSISTENT);
    }
}
