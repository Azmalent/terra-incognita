package azmalent.terraincognita.common.init;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.client.renderer.TIBoatRenderer;
import azmalent.terraincognita.common.entity.TIBoatEntity;
import net.minecraft.client.renderer.entity.BoatRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.BoatEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("unchecked")
public class ModEntities {
    public static DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, TerraIncognita.MODID);

    private static <TEntity extends Entity> RegistryObject register(String id, EntityType.Builder<TEntity> builder) {
        return ENTITIES.register(id, () -> builder.build(TerraIncognita.prefix(id).toString()));
    }

    public static final RegistryObject<EntityType<TIBoatEntity>> BOAT = null;//register("boat",
//        EntityType.Builder.<TIBoatEntity>create(TIBoatEntity::new, EntityClassification.MISC).size(1.375F, 0.5625F).trackingRange(10)
//    );

    @OnlyIn(Dist.CLIENT)
    public static void registerRenderers() {
        //RenderingRegistry.registerEntityRenderingHandler(BOAT.get(), BoatRenderer::new);
    }
}
