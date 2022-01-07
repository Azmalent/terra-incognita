package azmalent.terraincognita.common.integration.quark.block;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.item.context.BlockPlaceContext;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.level.BlockGetter;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nonnull;

//Copied from Quark LeafCarpetBlock with minor edits
public class TILeafCarpetBlock extends Block {
    private static final VoxelShape SHAPE = box(0, 0, 0, 16, 1, 16);

    public TILeafCarpetBlock() {
        super(Block.Properties.of(Material.CLOTH_DECORATION).strength(0F).sound(SoundType.GRASS).harvestTool(ToolType.HOE).noOcclusion());
    }

    @Override
    public boolean canBeReplaced(BlockState state, @Nonnull BlockPlaceContext useContext) {
        return true;
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, BlockGetter world, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getCollisionShape(@Nonnull BlockState state, @Nonnull BlockGetter world, @Nonnull BlockPos pos, CollisionContext p_220071_4_) {
        return Shapes.empty();
    }
}
