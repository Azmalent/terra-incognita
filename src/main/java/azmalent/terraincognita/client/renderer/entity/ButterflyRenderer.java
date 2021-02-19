package azmalent.terraincognita.client.renderer.entity;

import azmalent.terraincognita.client.renderer.entity.model.ButterflyModel;
import azmalent.terraincognita.common.entity.AbstractButterflyEntity;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;

@OnlyIn(Dist.CLIENT)
public class ButterflyRenderer<T extends AbstractButterflyEntity> extends MobRenderer<T, ButterflyModel<T>> {
    public ButterflyRenderer(EntityRendererManager renderManagerIn) {
        super(renderManagerIn, new ButterflyModel<T>(), 0.2f);
    }

    @Nonnull
    @Override
    public ResourceLocation getEntityTexture(T entity) {
        return entity.getTexture();
    }
}
