package azmalent.terraincognita.integration.quark.block;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;

//Copied from Quark LeafCarpetBlock with minor edits
@SuppressWarnings("deprecation")
public class TILeafCarpetBlock extends Block {
    private static final VoxelShape SHAPE = box(0, 0, 0, 16, 1, 16);

    public TILeafCarpetBlock() {
        super(Block.Properties.of(Material.CLOTH_DECORATION).strength(0F).sound(SoundType.GRASS).noOcclusion());
    }

    @Override
    public boolean canBeReplaced(@NotNull BlockState state, @Nonnull BlockPlaceContext useContext) {
        return true;
    }

    @Nonnull
    @Override
    public VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter world, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getCollisionShape(@Nonnull BlockState state, @Nonnull BlockGetter world, @Nonnull BlockPos pos, @NotNull CollisionContext p_220071_4_) {
        return Shapes.empty();
    }
}
