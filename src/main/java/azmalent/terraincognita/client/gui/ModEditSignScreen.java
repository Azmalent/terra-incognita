package azmalent.terraincognita.client.gui;

import azmalent.terraincognita.client.renderer.blockentity.ModSignRenderer;
import azmalent.terraincognita.common.block.signs.ModStandingSignBlock;
import azmalent.terraincognita.common.tile.ModSignBlockEntity;
import azmalent.terraincognita.network.NetworkHandler;
import azmalent.terraincognita.network.S2CUpdateSignMessage;
import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.*;
import com.mojang.math.Matrix4f;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.font.TextFieldHelper;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.blockentity.SignRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.client.resources.model.Material;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import java.util.stream.IntStream;

@SuppressWarnings({"ConstantConditions", "CodeBlock2Expr"})
@OnlyIn(Dist.CLIENT)
public class ModEditSignScreen extends Screen {
    private final SignRenderer.SignModel signModel = new SignRenderer.SignModel();
    /** Reference to the sign object. */
    private final ModSignBlockEntity sign;
    /** Counts the number of screen updates. */
    private int updateCounter;
    /** The index of the line that is being edited. */
    private int editLine;
    private TextFieldHelper textInputUtil;
    private final String[] text;

    public ModEditSignScreen(ModSignBlockEntity sign) {
        super(new TranslatableComponent("sign.edit"));
        this.text = IntStream.range(0, 4).mapToObj(sign::getText).map(Component::getString).toArray(String[]::new);
        this.sign = sign;
    }

    @Override
    protected void init() {
        this.minecraft.keyboardHandler.setSendRepeatsToGui(true);
        this.addButton(new Button(this.width / 2 - 100, this.height / 4 + 120, 200, 20, CommonComponents.GUI_DONE, (p_238847_1_) -> {
            this.close();
        }));
        this.sign.setEditable(false);
        this.textInputUtil = new TextFieldHelper(
            () -> this.text[this.editLine],
            (line) -> {
                this.text[this.editLine] = line;
                this.sign.setText(this.editLine, new TextComponent(line));
            },
            TextFieldHelper.createClipboardGetter(this.minecraft),
            TextFieldHelper.createClipboardSetter(this.minecraft), (string) -> {
                return this.minecraft.font.width(string) <= 90;
            });
    }

    @Override
    public void removed() {
        this.minecraft.keyboardHandler.setSendRepeatsToGui(false);
        ClientPacketListener connection = this.minecraft.getConnection();
        if (connection != null) {
            S2CUpdateSignMessage signMessage = new S2CUpdateSignMessage(sign.getBlockPos(), sign.signText, sign.getTextColor().getId());
            NetworkHandler.sendToServer(signMessage);
        }

        this.sign.setEditable(true);
    }

    @Override
    public void tick() {
        ++this.updateCounter;
        if (!this.sign.getType().isValid(this.sign.getBlockState().getBlock())) {
            this.close();
        }
    }

    private void close() {
        this.sign.setChanged();
        this.minecraft.setScreen(null);
    }

    @Override
    public boolean charTyped(char codePoint, int modifiers) {
        this.textInputUtil.charTyped(codePoint);
        return true;
    }

    @Override
    public void onClose() {
        this.close();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        if (keyCode == 265) {
            this.editLine = this.editLine - 1 & 3;
            this.textInputUtil.setCursorToEnd();
            return true;
        } else if (keyCode != 264 && keyCode != 257 && keyCode != 335) {
            return this.textInputUtil.keyPressed(keyCode) || super.keyPressed(keyCode, scanCode, modifiers);
        } else {
            this.editLine = this.editLine + 1 & 3;
            this.textInputUtil.setCursorToEnd();
            return true;
        }
    }

    @Override
    public void render(@Nonnull PoseStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        Lighting.setupForFlatItems();
        this.renderBackground(matrixStack);
        drawCenteredString(matrixStack, this.font, this.title, this.width / 2, 40, 16777215);
        matrixStack.pushPose();
        matrixStack.translate(this.width / 2.0D, 0.0D, 50.0D);
        float f = 93.75F;
        matrixStack.scale(93.75F, -93.75F, 93.75F);
        matrixStack.translate(0.0D, -1.3125D, 0.0D);
        BlockState blockstate = this.sign.getBlockState();
        boolean flag = blockstate.getBlock() instanceof ModStandingSignBlock;
        if (!flag) {
            matrixStack.translate(0.0D, -0.3125D, 0.0D);
        }

        boolean flag1 = this.updateCounter / 6 % 2 == 0;
        float f1 = 0.6666667F;
        matrixStack.pushPose();
        matrixStack.scale(0.6666667F, -0.6666667F, -0.6666667F);
        MultiBufferSource.BufferSource irendertypebuffer$impl = this.minecraft.renderBuffers().bufferSource();
        Material rendermaterial = ModSignRenderer.getMaterial(blockstate.getBlock());
        VertexConsumer ivertexbuilder = rendermaterial.buffer(irendertypebuffer$impl, this.signModel::renderType);
        this.signModel.sign.render(matrixStack, ivertexbuilder, 15728880, OverlayTexture.NO_OVERLAY);
        if (flag) {
            this.signModel.stick.render(matrixStack, ivertexbuilder, 15728880, OverlayTexture.NO_OVERLAY);
        }

        matrixStack.popPose();
        float f2 = 0.010416667F;
        matrixStack.translate(0.0D, 0.33333334F, 0.046666667F);
        matrixStack.scale(0.010416667F, -0.010416667F, 0.010416667F);
        int i = this.sign.getTextColor().getTextColor();
        int j = this.textInputUtil.getCursorPos();
        int k = this.textInputUtil.getSelectionPos();
        int l = this.editLine * 10 - this.text.length * 5;
        Matrix4f matrix4f = matrixStack.last().pose();

        for(int i1 = 0; i1 < this.text.length; ++i1) {
            String s = this.text[i1];
            if (s != null) {
                if (this.font.isBidirectional()) {
                    s = this.font.bidirectionalShaping(s);
                }

                float f3 = (float)(-this.minecraft.font.width(s) / 2);
                this.minecraft.font.drawInBatch(s, f3, (float)(i1 * 10 - this.text.length * 5), i, false, matrix4f, irendertypebuffer$impl, false, 0, 15728880, false);
                if (i1 == this.editLine && j >= 0 && flag1) {
                    int j1 = this.minecraft.font.width(s.substring(0, Math.max(Math.min(j, s.length()), 0)));
                    int k1 = j1 - this.minecraft.font.width(s) / 2;
                    if (j >= s.length()) {
                        this.minecraft.font.drawInBatch("_", (float)k1, (float)l, i, false, matrix4f, irendertypebuffer$impl, false, 0, 15728880, false);
                    }
                }
            }
        }

        irendertypebuffer$impl.endBatch();

        for(int i3 = 0; i3 < this.text.length; ++i3) {
            String s1 = this.text[i3];
            if (s1 != null && i3 == this.editLine && j >= 0) {
                int j3 = this.minecraft.font.width(s1.substring(0, Math.max(Math.min(j, s1.length()), 0)));
                int k3 = j3 - this.minecraft.font.width(s1) / 2;
                if (flag1 && j < s1.length()) {
                    fill(matrixStack, k3, l - 1, k3 + 1, l + 9, -16777216 | i);
                }

                if (k != j) {
                    int l3 = Math.min(j, k);
                    int l1 = Math.max(j, k);
                    int i2 = this.minecraft.font.width(s1.substring(0, l3)) - this.minecraft.font.width(s1) / 2;
                    int j2 = this.minecraft.font.width(s1.substring(0, l1)) - this.minecraft.font.width(s1) / 2;
                    int k2 = Math.min(i2, j2);
                    int l2 = Math.max(i2, j2);
                    Tesselator tessellator = Tesselator.getInstance();
                    BufferBuilder bufferbuilder = tessellator.getBuilder();
                    RenderSystem.disableTexture();
                    RenderSystem.enableColorLogicOp();
                    RenderSystem.logicOp(GlStateManager.LogicOp.OR_REVERSE);
                    bufferbuilder.begin(7, DefaultVertexFormat.POSITION_COLOR);
                    bufferbuilder.vertex(matrix4f, (float)k2, (float)(l + 9), 0.0F).color(0, 0, 255, 255).endVertex();
                    bufferbuilder.vertex(matrix4f, (float)l2, (float)(l + 9), 0.0F).color(0, 0, 255, 255).endVertex();
                    bufferbuilder.vertex(matrix4f, (float)l2, (float)l, 0.0F).color(0, 0, 255, 255).endVertex();
                    bufferbuilder.vertex(matrix4f, (float)k2, (float)l, 0.0F).color(0, 0, 255, 255).endVertex();
                    bufferbuilder.end();
                    BufferUploader.end(bufferbuilder);
                    RenderSystem.disableColorLogicOp();
                    RenderSystem.enableTexture();
                }
            }
        }

        matrixStack.popPose();
        Lighting.setupFor3DItems();
        super.render(matrixStack, mouseX, mouseY, partialTicks);
    }
}
