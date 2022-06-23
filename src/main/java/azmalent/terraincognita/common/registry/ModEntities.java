package azmalent.terraincognita.common.registry;

import azmalent.cuneiform.registry.EntityEntry;
import azmalent.cuneiform.registry.MobEntry;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.entity.TIBoat;
import azmalent.terraincognita.common.entity.butterfly.AbstractButterfly;
import azmalent.terraincognita.common.entity.butterfly.Butterfly;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.SpawnPlacements;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraftforge.event.entity.EntityAttributeCreationEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static azmalent.terraincognita.TerraIncognita.REGISTRY_HELPER;

@Mod.EventBusSubscriber(modid = TerraIncognita.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = REGISTRY_HELPER.getOrCreateRegistry(ForgeRegistries.ENTITIES);

    public static EntityEntry<TIBoat> BOAT = REGISTRY_HELPER.createEntity("boat",
        EntityType.Builder.<TIBoat>of(TIBoat::new, MobCategory.MISC)
            .sized(1.375F, 0.5625F)
            .setCustomClientFactory(TIBoat::new)
            .clientTrackingRange(10));

    public static final MobEntry<Butterfly> BUTTERFLY = REGISTRY_HELPER.createMob("butterfly",
        EntityType.Builder.<Butterfly>of(Butterfly::new, MobCategory.AMBIENT)
            .sized(0.5f, 0.5f)
            .setCustomClientFactory(Butterfly::new)
            .clientTrackingRange(5))
        .withSpawnEgg(0xc02f03, 0x0f1016)
        .withAttributes(AbstractButterfly::createAttributes)
        .build();

    @SubscribeEvent
    public static void onSetup(FMLCommonSetupEvent event) {
        SpawnPlacements.register(BUTTERFLY.get(), SpawnPlacements.Type.ON_GROUND, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, Butterfly::canSpawn);
    }
}
