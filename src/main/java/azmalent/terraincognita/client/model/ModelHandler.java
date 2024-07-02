package azmalent.terraincognita.client.model;

import azmalent.terraincognita.TerraIncognita;
import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;
import java.util.function.Function;
import java.util.function.Supplier;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = TerraIncognita.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModelHandler {
    private static final Map<ModelLayerLocation, ModelData> MODELS = Maps.newHashMap();

    public static final ModelLayerLocation BUTTERFLY = ModelHandler.defineLayer("butterfly", ButterflyModel::new, ButterflyModel::createLayerDefinition);

    private static ModelLayerLocation defineLayer(String id, Function<ModelPart, EntityModel<?>> constructor, Supplier<LayerDefinition> definition) {
        ModelLayerLocation location = new ModelLayerLocation(TerraIncognita.prefix(id), "main");
        MODELS.put(location, new ModelData(constructor, definition));
        return location;
    }

    @SuppressWarnings("unchecked")
    public static <TMob extends Mob, TModel extends EntityModel<TMob>> TModel createModel(ModelLayerLocation location) {
        ModelData data = MODELS.get(location);
        return (TModel) data.constructor.apply(Minecraft.getInstance().getEntityModels().bakeLayer(location));
    }

    @SubscribeEvent
    public static void onRegisterLayers(EntityRenderersEvent.RegisterLayerDefinitions event) {
        MODELS.forEach((location, data) -> event.registerLayerDefinition(location, data.definition));
    }

    private record ModelData(Function<ModelPart, EntityModel<?>> constructor, Supplier<LayerDefinition> definition) {}
}
