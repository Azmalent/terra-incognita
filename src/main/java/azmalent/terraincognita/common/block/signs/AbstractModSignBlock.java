package azmalent.terraincognita.common.block.signs;

import azmalent.terraincognita.common.block.blocksets.ModWoodType;
import azmalent.terraincognita.common.integration.ModIntegration;
import azmalent.terraincognita.common.tile.ModSignTileEntity;
import azmalent.terraincognita.network.NetworkHandler;
import azmalent.terraincognita.network.message.EditSignMessage;
import azmalent.terraincognita.network.message.UpdateSignMessage;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemStack;
import net.minecraft.state.StateContainer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

public abstract class AbstractModSignBlock extends AbstractSignBlock {
    protected final ModWoodType woodType;

    public AbstractModSignBlock(MaterialColor color, ModWoodType woodType) {
        super(Block.Properties.create(Material.WOOD, color).doesNotBlockMovement().hardnessAndResistance(1.0F).sound(SoundType.WOOD), WoodType.OAK);
        this.woodType = woodType;
    }

    @Override
    protected void fillStateContainer(StateContainer.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Nonnull
    @Override
    public ActionResultType onBlockActivated(BlockState state, World worldIn, @Nonnull BlockPos pos, PlayerEntity player, @Nonnull Hand handIn, BlockRayTraceResult hit) {
        ItemStack heldStack = player.getHeldItem(handIn);
        boolean canEdit = player.abilities.allowEdit;
        boolean canDye = heldStack.getItem() instanceof DyeItem && canEdit;

        if (worldIn.isRemote) {
            return canDye ? ActionResultType.SUCCESS : ActionResultType.CONSUME;
        }

        TileEntity te = worldIn.getTileEntity(pos);
        if (te instanceof ModSignTileEntity) {
            ModSignTileEntity sign = (ModSignTileEntity) te;
            if (canDye) {
                boolean setColor = sign.setTextColor(((DyeItem) heldStack.getItem()).getDyeColor());
                if (setColor) {
                    UpdateSignMessage message = new UpdateSignMessage(pos, sign.signText, sign.getTextColor().getId());
                    NetworkHandler.sendToServer(message);

                    if (!player.isCreative()) {
                        heldStack.shrink(1);
                    }
                }
            } else {
                if (canEdit && !this.doesSignHaveCommand(sign) && ModIntegration.QUARK.canEditSign(heldStack) && !player.isSneaking()) {
                    NetworkHandler.sendTo((ServerPlayerEntity) player, new EditSignMessage(pos));
                    return ActionResultType.SUCCESS;
                }
            }

            return sign.executeCommand(player) ? ActionResultType.SUCCESS : ActionResultType.PASS;
        }

        return ActionResultType.PASS;
    }

    private boolean doesSignHaveCommand(ModSignTileEntity sign) {
        for (ITextComponent itextcomponent : sign.signText) {
            Style style = itextcomponent == null ? null : itextcomponent.getStyle();
            if (style != null && style.getClickEvent() != null) {
                ClickEvent clickevent = style.getClickEvent();
                if (clickevent.getAction() == ClickEvent.Action.RUN_COMMAND) {
                    return true;
                }
            }
        }
        return false;
    }

    public ResourceLocation getTexture() {
        return this.woodType.SIGN_TEXTURE;
    }

    @Override
    public TileEntity createNewTileEntity(IBlockReader world) {
        return new ModSignTileEntity();
    }

    @Nonnull
    @Override
    public BlockRenderType getRenderType(BlockState state) {
        return BlockRenderType.ENTITYBLOCK_ANIMATED;
    }
}