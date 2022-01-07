package azmalent.terraincognita.common.registry;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.client.renderer.entity.ButterflyRenderer;
import azmalent.terraincognita.client.renderer.entity.CactusNeedleRenderer;
import azmalent.terraincognita.client.renderer.entity.ModBoatRenderer;
import azmalent.terraincognita.common.entity.CactusNeedleEntity;
import azmalent.terraincognita.common.entity.ModBoatEntity;
import azmalent.terraincognita.common.entity.butterfly.AbstractButterflyEntity;
import azmalent.terraincognita.common.entity.butterfly.ButterflyEntity;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.DefaultAttributes;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, TerraIncognita.MODID);

    public static final RegistryObject<EntityType<ModBoatEntity>> BOAT = register("boat",
        EntityType.Builder.<ModBoatEntity>of(ModBoatEntity::new, MobCategory.MISC).sized(1.375F, 0.5625F).setCustomClientFactory(ModBoatEntity::new).clientTrackingRange(10)
    );

    public static final RegistryObject<EntityType<ButterflyEntity>> BUTTERFLY = register("butterfly",
        EntityType.Builder.<ButterflyEntity>of(ButterflyEntity::new, MobCategory.AMBIENT).sized(0.5f, 0.5f).setCustomClientFactory(ButterflyEntity::new).clientTrackingRange(5)
    );

    public static final RegistryObject<EntityType<CactusNeedleEntity>> CACTUS_NEEDLE = register("cactus_needle",
        EntityType.Builder.<CactusNeedleEntity>of(CactusNeedleEntity::new, MobCategory.MISC).sized(0.5F, 0.5F).clientTrackingRange(4).updateInterval(20)
    );

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String id, EntityType.Builder<T> builder) {
        return ENTITIES.register(id, () -> builder.build(TerraIncognita.prefix(id).toString()));
    }

    public static void registerAttributes() {
        DefaultAttributes.put(BUTTERFLY.get(), AbstractButterflyEntity.bakeAttributes().build());
    }

    public static void registerSpawns() {
        SpawnPlacements.register(BUTTERFLY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, ButterflyEntity::canSpawn);
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerRenderers() {
        RenderingRegistry.registerEntityRenderingHandler(BOAT.get(), ModBoatRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(BUTTERFLY.get(), ButterflyRenderer<ButterflyEntity>::new);
        RenderingRegistry.registerEntityRenderingHandler(CACTUS_NEEDLE.get(), CactusNeedleRenderer::new);
    }
}
