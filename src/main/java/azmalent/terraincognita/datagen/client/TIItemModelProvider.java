package azmalent.terraincognita.datagen.client;

import azmalent.cuneiform.registry.MobEntry;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.registry.ModBanners;
import azmalent.terraincognita.common.registry.ModEntities;
import azmalent.terraincognita.common.registry.ModItems;
import azmalent.terraincognita.common.registry.ModWoodTypes;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

@SuppressWarnings("ConstantConditions")
public class TIItemModelProvider extends ItemModelProvider {
    public TIItemModelProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, TerraIncognita.MODID, exFileHelper);
    }

    @Override
    protected void registerModels() {
        ModWoodTypes.VALUES.forEach(type -> {
            registerItem(type.BOAT);
        });

        ModBanners.PATTERN_ITEMS.forEach((pattern, item) -> {
            var name = item.getId().getPath();
            withExistingParent(name, "item/generated")
                .texture("layer0", new ResourceLocation("item/creeper_banner_pattern"));
        });

        registerItem(ModItems.BERRY_SORBET);
        registerItem(ModItems.BOTTLED_BUTTERFLY);
        registerItem(ModItems.CACTUS_NEEDLE);
        registerItem(ModItems.CANDIED_HAZELNUT);
        registerItem(ModItems.FERN_FIDDLEHEAD);
        registerItem(ModItems.HAZELNUT);
        registerItem(ModItems.HAZELNUT_COOKIE);
        registerItem(ModItems.KELP_SOUP);
        registerItem(ModItems.SOUR_BERRIES);
        registerItem(ModItems.SOUR_BERRY_JAM);
        registerItem(ModItems.SOUR_BERRY_PIE);
        registerItem(ModItems.TAFFY);

        withExistingParent("wreath", "item/generated")
            .texture("layer0", TerraIncognita.prefix("item/wreath"))
            .texture("layer1", TerraIncognita.prefix("item/wreath_overlay"));

        withExistingParent("notch_carrot", "item/generated")
            .texture("layer0", new ResourceLocation("item/golden_carrot"));

        withExistingParent("fiddlehead_stew", "item/generated")
            .texture("layer0", TerraIncognita.prefix("item/fiddlehead_stew"));

        registerSpawnEgg(ModEntities.BUTTERFLY);
    }

    private void registerItem(ItemLike item) {
        ResourceLocation itemName = item.asItem().getRegistryName();
        withExistingParent(itemName.getPath(), "item/generated").texture("layer0", TerraIncognita.prefix("item/" + itemName.getPath()));
    }

    @SuppressWarnings("SameParameterValue")
    private void registerSpawnEgg(MobEntry<?> mob) {
        ResourceLocation itemName = mob.SPAWN_EGG.get().getRegistryName();
        withExistingParent(itemName.getPath(), "item/template_spawn_egg");
    }
}
