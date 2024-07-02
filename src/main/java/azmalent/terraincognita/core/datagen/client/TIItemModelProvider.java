package azmalent.terraincognita.core.datagen.client;

import azmalent.cuneiform.registry.MobEntry;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.core.registry.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.world.item.BannerPatternItem;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.RegistryObject;

@SuppressWarnings("DataFlowIssue")
public class TIItemModelProvider extends ItemModelProvider {
    public TIItemModelProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, TerraIncognita.MODID, exFileHelper);
    }

    @Override
    protected void registerModels() {
        ModWoodTypes.VALUES.forEach(type -> {
            basicItem(type.BOAT.asItem());
        });

        basicItem(ModBlocks.BASKET.asItem());
        basicItem(ModBlocks.HANGING_MOSS.asItem());
        basicItem(ModBlocks.SWAMP_REEDS.asItem());
        basicItem(ModBlocks.WICKER_LANTERN.asItem());

        basicItem(ModItems.BERRY_SORBET.asItem());
        basicItem(ModItems.BOTTLED_BUTTERFLY.asItem());
        basicItem(ModItems.CACTUS_NEEDLE.asItem());
        basicItem(ModItems.CANDIED_HAZELNUT.asItem());
        basicItem(ModItems.FERN_FIDDLEHEAD.asItem());
        basicItem(ModItems.HAZELNUT.asItem());
        basicItem(ModItems.HAZELNUT_COOKIE.asItem());
        basicItem(ModItems.KELP_SOUP.asItem());
        basicItem(ModItems.SOUR_BERRIES.asItem());
        basicItem(ModItems.SOUR_BERRY_JAM.asItem());
        basicItem(ModItems.SOUR_BERRY_PIE.asItem());
        basicItem(ModItems.TAFFY.asItem());

        ModBannerPatterns.PATTERN_ITEMS.values().forEach(this::bannerPattern);

        spawnEgg(ModEntities.BUTTERFLY);
    }

    private void bannerPattern(RegistryObject<BannerPatternItem> item) {
        getBuilder(item.getId().getPath()).parent(new ModelFile.UncheckedModelFile("item/creeper_banner_pattern"));
    }

    private void spawnEgg(MobEntry<?> mob) {
        getBuilder(mob.SPAWN_EGG.asItem().getRegistryName().getPath())
            .parent(new ModelFile.UncheckedModelFile("item/template_spawn_egg"));
    }
}
