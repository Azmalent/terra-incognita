package azmalent.terraincognita.common.registry;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.client.renderer.entity.ButterflyRenderer;
import azmalent.terraincognita.client.renderer.entity.ModBoatRenderer;
import azmalent.terraincognita.common.entity.ModBoatEntity;
import azmalent.terraincognita.common.entity.butterfly.AbstractButterflyEntity;
import azmalent.terraincognita.common.entity.butterfly.ButterflyEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.world.gen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("unchecked")
public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, TerraIncognita.MODID);

    public static final RegistryObject<EntityType<ModBoatEntity>> BOAT = register("boat",
        EntityType.Builder.<ModBoatEntity>create(ModBoatEntity::new, EntityClassification.MISC).size(1.375F, 0.5625F).setCustomClientFactory(ModBoatEntity::new).trackingRange(10)
    );

    public static final RegistryObject<EntityType<ButterflyEntity>> BUTTERFLY = register("butterfly",
        EntityType.Builder.<ButterflyEntity>create(ButterflyEntity::new, EntityClassification.AMBIENT).size(0.5f, 0.5f).setCustomClientFactory(ButterflyEntity::new).trackingRange(5)
    );

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String id, EntityType.Builder<T> builder) {
        return ENTITIES.register(id, () -> builder.build(TerraIncognita.prefix(id).toString()));
    }

    public static void registerAttributes() {
        GlobalEntityTypeAttributes.put(BUTTERFLY.get(), AbstractButterflyEntity.bakeAttributes().create());
    }

    public static void registerSpawns() {
        EntitySpawnPlacementRegistry.register(BUTTERFLY.get(), EntitySpawnPlacementRegistry.PlacementType.ON_GROUND, Heightmap.Type.MOTION_BLOCKING_NO_LEAVES, ButterflyEntity::canSpawn);
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(BOAT.get(), ModBoatRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BUTTERFLY.get(), ButterflyRenderer<ButterflyEntity>::new);
    }
}
