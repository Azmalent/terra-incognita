package azmalent.terraincognita.common.block.fruit;

import azmalent.terraincognita.common.registry.ModWoodTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
public class AppleBlock extends AbstractFruitBlock {
    private static final VoxelShape SMALL_SHAPE = box(6, 9, 6, 10, 13, 10);
    private static final VoxelShape BIG_SHAPE = box(5, 7, 5, 11, 13, 11);

    public AppleBlock() {
        super(Block.Properties.of(Material.DECORATION, MaterialColor.COLOR_RED).strength(0.2F).sound(SoundType.WOOD), () -> Items.APPLE, 15);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        Vec3 offset = state.getOffset(level, pos);
        return (state.getValue(AGE) < 2 ? SMALL_SHAPE : BIG_SHAPE).move(offset.x, offset.y, offset.z);
    }

    @Nonnull
    @Override
    public OffsetType getOffsetType() {
        return OffsetType.XZ;
    }

    @Override
    public boolean canSurvive(@NotNull BlockState state, LevelReader level, BlockPos pos) {
        BlockState up = level.getBlockState(pos.above());
        return up.is(ModWoodTypes.APPLE.LEAVES.get()) || up.is(ModWoodTypes.APPLE.BLOSSOMING_LEAVES.get());
    }
}
