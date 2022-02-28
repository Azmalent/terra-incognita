package azmalent.terraincognita.common.registry;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.client.renderer.entity.ButterflyRenderer;
import azmalent.terraincognita.client.renderer.entity.ModBoatRenderer;
import azmalent.terraincognita.common.entity.ModBoatEntity;
import azmalent.terraincognita.common.entity.butterfly.AbstractButterfly;
import azmalent.terraincognita.common.entity.butterfly.Butterfly;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

@Mod.EventBusSubscriber(modid = TerraIncognita.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = TerraIncognita.REG_HELPER.getOrCreateRegistry(ForgeRegistries.ENTITIES);

    public static final RegistryObject<EntityType<ModBoatEntity>> BOAT = register("boat",
        EntityType.Builder.<ModBoatEntity>of(ModBoatEntity::new, MobCategory.MISC)
                .sized(1.375F, 0.5625F)
                .setCustomClientFactory(ModBoatEntity::new)
                .clientTrackingRange(10)
    );

    public static final RegistryObject<EntityType<Butterfly>> BUTTERFLY = register("butterfly",
        EntityType.Builder.<Butterfly>of(Butterfly::new, MobCategory.AMBIENT)
                .sized(0.5f, 0.5f)
                .setCustomClientFactory(Butterfly::new)
                .clientTrackingRange(5)
    );

    private static <T extends Entity> RegistryObject<EntityType<T>> register(String id, EntityType.Builder<T> builder) {
        return ENTITIES.register(id, () -> builder.build(TerraIncognita.prefix(id).toString()));
    }

    @SubscribeEvent
    public static void onAttributeCreation(EntityAttributeCreationEvent event) {
        event.put(BUTTERFLY.get(), AbstractButterfly.bakeAttributes().build());
    }

    public static void registerSpawns() {
        SpawnPlacements.register(BUTTERFLY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Butterfly::canSpawn);
    }
}
