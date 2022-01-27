package azmalent.terraincognita.common.block.signs;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.block.woodtypes.ModWoodType;
import azmalent.terraincognita.common.integration.ModIntegration;
import azmalent.terraincognita.common.tile.ModSignBlockEntity;
import azmalent.terraincognita.network.NetworkHandler;
import azmalent.terraincognita.network.S2CUpdateSignMessage;
import azmalent.terraincognita.network.S2CEditSignMessage;
import net.minecraft.block.*;
import net.minecraft.world.level.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SignBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.WoodType;
import org.jetbrains.annotations.NotNull;

public abstract class AbstractModSignBlock extends SignBlock {
    protected final ModWoodType woodType;

    public AbstractModSignBlock(ModWoodType woodType) {
        super(Block.Properties.of(Material.WOOD, woodType.woodColor).noCollission().strength(1.0F).sound(SoundType.WOOD), WoodType.OAK);
        this.woodType = woodType;
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(WATERLOGGED);
    }

    @Nonnull
    @Override
    public InteractionResult use(@NotNull BlockState state, Level level, @Nonnull BlockPos pos, Player player, @Nonnull InteractionHand handIn, @NotNull BlockHitResult hit) {
        ItemStack heldStack = player.getItemInHand(handIn);
        boolean canEdit = player.getAbilities().mayBuild;
        boolean canDye = heldStack.getItem() instanceof DyeItem && canEdit;

        if (level.isClientSide) {
            return canDye ? InteractionResult.SUCCESS : InteractionResult.CONSUME;
        }

        BlockEntity te = level.getBlockEntity(pos);
        if (te instanceof ModSignBlockEntity) {
            ModSignBlockEntity sign = (ModSignBlockEntity) te;
            if (canDye) {
                boolean setColor = sign.setTextColor(((DyeItem) heldStack.getItem()).getDyeColor());
                if (setColor) {
                    S2CUpdateSignMessage message = new S2CUpdateSignMessage(pos, sign.signText, sign.getTextColor().getId());
                    TerraIncognita.NETWORK.sendToAllPlayers(message);

                    if (!player.isCreative()) {
                        heldStack.shrink(1);
                    }
                }
            } else {
                if (canEdit && !this.doesSignHaveCommand(sign) && ModIntegration.QUARK.canEditSign(heldStack) && !player.isShiftKeyDown()) {
                    TerraIncognita.NETWORK.sendToPlayer((ServerPlayer) player, new S2CEditSignMessage(pos));
                    return InteractionResult.SUCCESS;
                }
            }

            return sign.executeCommand(player) ? InteractionResult.SUCCESS : InteractionResult.PASS;
        }

        return InteractionResult.PASS;
    }

    private boolean doesSignHaveCommand(ModSignBlockEntity sign) {
        for (Component itextcomponent : sign.signText) {
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
    public BlockEntity newBlockEntity(BlockGetter world) {
        return new ModSignBlockEntity();
    }

    @Nonnull
    @Override
    public RenderShape getRenderShape(@NotNull BlockState state) {
        return RenderShape.ENTITYBLOCK_ANIMATED;
    }
}
