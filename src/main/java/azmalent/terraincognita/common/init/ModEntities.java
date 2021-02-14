package azmalent.terraincognita.common.init;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.client.renderer.entity.ModBoatRenderer;
import azmalent.terraincognita.common.entity.ButterflyEntity;
import azmalent.terraincognita.common.entity.ModBoatEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("unchecked")
public class ModEntities {
    public static DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, TerraIncognita.MODID);

    public static final RegistryObject<EntityType<ModBoatEntity>> BOAT = register("boat",
        EntityType.Builder.<ModBoatEntity>create(ModBoatEntity::new, EntityClassification.MISC).size(1.375F, 0.5625F).setCustomClientFactory(ModBoatEntity::new).trackingRange(10)
    );

    public static RegistryObject<EntityType<ButterflyEntity>> BUTTERFLY;

    static {
        BUTTERFLY = register("butterfly",
            EntityType.Builder.<ButterflyEntity>create(ButterflyEntity::new, EntityClassification.AMBIENT).size(0.5f, 0.5f).setCustomClientFactory(ButterflyEntity::new).trackingRange(5)
        );
    }

    private static <TEntity extends Entity> RegistryObject register(String id, EntityType.Builder<TEntity> builder) {
        return ENTITIES.register(id, () -> builder.build(TerraIncognita.prefix(id).toString()));
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(BOAT.get(), ModBoatRenderer::new);
    }
}
