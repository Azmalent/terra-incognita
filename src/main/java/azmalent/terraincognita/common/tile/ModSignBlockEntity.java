package azmalent.terraincognita.common.tile;

import azmalent.terraincognita.common.registry.ModBlockEntities;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.function.Function;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.CommandSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.DyeColor;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.protocol.game.ClientboundBlockEntityDataPacket;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.util.FormattedCharSequence;
import net.minecraft.world.phys.Vec2;
import net.minecraft.world.phys.Vec3;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.ComponentUtils;
import net.minecraft.network.chat.ClickEvent;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings("ConstantConditions")
public class ModSignBlockEntity extends BlockEntity {
    public final Component[] signText;
    private boolean isEditable;
    private Player player;
    private final FormattedCharSequence[] renderText;
    private DyeColor textColor;
    private boolean glowing;

    public ModSignBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.SIGN.get(), pos, state);
        this.signText = new Component[]{TextComponent.EMPTY, TextComponent.EMPTY, TextComponent.EMPTY, TextComponent.EMPTY};
        this.isEditable = true;
        this.renderText = new FormattedCharSequence[4];
        this.textColor = DyeColor.BLACK;
        this.glowing = false;
    }

    @Nonnull
    @Override
    public CompoundTag save(@NotNull CompoundTag compound) {
        super.save(compound);

        for(int i = 0; i < 4; ++i) {
            String s = Component.Serializer.toJson(this.signText[i]);
            compound.putString("Text" + (i + 1), s);
        }

        compound.putString("Color", this.textColor.getName());
        compound.putBoolean("Glowing", this.glowing);
        return compound;
    }

    @Override
    public void load(@NotNull CompoundTag nbt) {
        this.isEditable = false;
        super.load(nbt);
        this.textColor = DyeColor.byName(nbt.getString("Color"), DyeColor.BLACK);
        this.glowing = nbt.getBoolean("Glowing");

        for(int i = 0; i < 4; ++i) {
            String s = nbt.getString("Text" + (i + 1));
            Component component = Component.Serializer.fromJson(s.isEmpty() ? "\"\"" : s);
            if (this.level instanceof ServerLevel) {
                try {
                    this.signText[i] = ComponentUtils.updateForEntity(this.getCommandSource(null), component, null, 0);
                } catch (CommandSyntaxException commandsyntaxexception) {
                    this.signText[i] = component;
                }
            } else {
                this.signText[i] = component;
            }

            this.renderText[i] = null;
        }

    }

    @OnlyIn(Dist.CLIENT)
    public Component getText(int line) {
        return this.signText[line];
    }

    public void setText(int line, Component signText) {
        this.signText[line] = signText;
        this.renderText[line] = null;
    }

    public boolean isGlowing() {
        return glowing;
    }

    public boolean setGlowing(boolean value) {
        if (glowing != value) {
            glowing = value;
            setChanged();
            this.level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
            return true;
        }

        return false;
    }

    @Nullable
    @OnlyIn(Dist.CLIENT)
    public FormattedCharSequence getRenderMessage(int p_242686_1_, Function<Component, FormattedCharSequence> p_242686_2_) {
        if (this.renderText[p_242686_1_] == null && this.signText[p_242686_1_] != null) {
            this.renderText[p_242686_1_] = p_242686_2_.apply(this.signText[p_242686_1_]);
        }

        return this.renderText[p_242686_1_];
    }

    @Override
    @Nullable
    public ClientboundBlockEntityDataPacket getUpdatePacket() {
        return new ClientboundBlockEntityDataPacket(this.worldPosition, 9, this.getUpdateTag());
    }

    @Nonnull
    @Override
    public CompoundTag getUpdateTag() {
        return this.save(new CompoundTag());
    }

    @Override
    public boolean onlyOpCanSetNbt() {
        return true;
    }

    public boolean getIsEditable() {
        return this.isEditable;
    }

    /**
     * Sets the sign's isEditable flag to the specified parameter.
     */
    @OnlyIn(Dist.CLIENT)
    public void setEditable(boolean isEditableIn) {
        this.isEditable = isEditableIn;
        if (!isEditableIn) {
            this.player = null;
        }

    }

    public void setPlayer(Player playerIn) {
        this.player = playerIn;
    }

    public Player getPlayer() {
        return this.player;
    }

    @SuppressWarnings("SameReturnValue")
    public boolean executeCommand(Player playerIn) {
        for(Component itextcomponent : this.signText) {
            Style style = itextcomponent == null ? null : itextcomponent.getStyle();
            if (style != null && style.getClickEvent() != null) {
                ClickEvent clickevent = style.getClickEvent();
                if (clickevent.getAction() == ClickEvent.Action.RUN_COMMAND) {
                    playerIn.getServer().getCommands().performCommand(this.getCommandSource((ServerPlayer)playerIn), clickevent.getValue());
                }
            }
        }

        return true;
    }

    public CommandSourceStack getCommandSource(@Nullable ServerPlayer playerIn) {
        String s = playerIn == null ? "Sign" : playerIn.getName().getString();
        Component itextcomponent = playerIn == null ? new TextComponent("Sign") : playerIn.getDisplayName();
        return new CommandSourceStack(CommandSource.NULL, Vec3.atCenterOf(this.worldPosition), Vec2.ZERO, (ServerLevel)this.level, 2, s, itextcomponent, this.level.getServer(), playerIn);
    }

    public DyeColor getTextColor() {
        return this.textColor;
    }

    public boolean setTextColor(DyeColor newColor) {
        if (newColor != this.getTextColor()) {
            this.textColor = newColor;
            this.setChanged();
            this.level.sendBlockUpdated(this.getBlockPos(), this.getBlockState(), this.getBlockState(), 3);
            return true;
        } else {
            return false;
        }
    }
}
