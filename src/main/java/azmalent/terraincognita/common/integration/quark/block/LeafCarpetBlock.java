package azmalent.terraincognita.common.integration.quark.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.BlockItemUseContext;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.util.math.shapes.VoxelShapes;
import net.minecraft.world.IBlockReader;
import net.minecraftforge.common.ToolType;

import javax.annotation.Nonnull;

//Copied from Quark LeafCarpetBlock with minor edits
public class LeafCarpetBlock extends Block {
    private static final VoxelShape SHAPE = makeCuboidShape(0, 0, 0, 16, 1, 16);

    public LeafCarpetBlock() {
        super(Block.Properties.create(Material.CARPET).hardnessAndResistance(0F).sound(SoundType.PLANT).harvestTool(ToolType.HOE).notSolid());
    }

    @Override
    public boolean isReplaceable(BlockState state, @Nonnull BlockItemUseContext useContext) {
        return true;
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader world, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public VoxelShape getCollisionShape(@Nonnull BlockState state, @Nonnull IBlockReader world, @Nonnull BlockPos pos, ISelectionContext p_220071_4_) {
        return VoxelShapes.empty();
    }
}
