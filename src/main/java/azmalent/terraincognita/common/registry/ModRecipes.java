package azmalent.terraincognita.common.registry;

import azmalent.cuneiform.util.DataUtil;
import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.recipe.FiddleheadStewAdditionRecipe;
import azmalent.terraincognita.common.recipe.FiddleheadStewRecipe;
import azmalent.terraincognita.common.recipe.WreathRecipe;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.SimpleRecipeSerializer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Function;

import static azmalent.terraincognita.TerraIncognita.REGISTRY_HELPER;

@SuppressWarnings("unused")
public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> RECIPES = REGISTRY_HELPER.getOrCreateRegistry(ForgeRegistries.RECIPE_SERIALIZERS);

    public static final RegistryObject<SimpleRecipeSerializer<?>> FIDDLEHEAD_SUSPICIOUS_STEW = registerRecipe("fiddlehead_suspicious_stew", FiddleheadStewRecipe::new);
    public static RegistryObject<SimpleRecipeSerializer<?>> FIDDLEHEAD_SUSPICIOUS_STEW_ADDITION = registerRecipe("fiddlehead_suspicious_stew_addition", FiddleheadStewAdditionRecipe::new);
    public static final RegistryObject<SimpleRecipeSerializer<?>> WREATH = registerRecipe("wreath", WreathRecipe::new);

    private static RegistryObject<SimpleRecipeSerializer<?>> registerRecipe(String id, Function<ResourceLocation, CustomRecipe> constructor) {
        return RECIPES.register(id, () -> new SimpleRecipeSerializer<>(constructor));
    }

    public static void initCompostables() {
        if (TIConfig.Misc.additionalCompostables.get()) {
            DataUtil.registerCompostable(Items.DEAD_BUSH, 0.3f);
            DataUtil.registerCompostable(Items.BAMBOO, 0.65f);
            DataUtil.registerCompostable(Items.POISONOUS_POTATO, 0.65f);
            DataUtil.registerCompostable(Items.CHORUS_FRUIT, 1.0f);
            DataUtil.registerCompostable(Items.POPPED_CHORUS_FRUIT, 1.0f);
            DataUtil.registerCompostable(Items.CHORUS_FLOWER, 1.0f);
        }

        for (var flower : ModBlocks.FLOWERS) {
            DataUtil.registerCompostable(flower, 0.65f);
        }

        for (var lotus : ModBlocks.LOTUSES) {
            DataUtil.registerCompostable(lotus, 0.65f);
        }

        for (var sweetPea : ModBlocks.SWEET_PEAS) {
            DataUtil.registerCompostable(sweetPea, 0.65f);
        }

        ModWoodTypes.VALUES.forEach(woodType -> {
            DataUtil.registerCompostable(woodType.SAPLING, 0.3f);
            DataUtil.registerCompostable(woodType.LEAVES, 0.3f);
            DataUtil.registerCompostable(woodType.LEAF_CARPET, 0.2f);
        });

        DataUtil.registerCompostable(ModWoodTypes.APPLE.BLOSSOMING_LEAVES, 0.3f);
        DataUtil.registerCompostable(ModWoodTypes.APPLE.BLOSSOMING_LEAF_CARPET, 0.2f);

        DataUtil.registerCompostable(ModBlocks.SMALL_LILY_PAD, 0.3f);
        DataUtil.registerCompostable(ModBlocks.HANGING_MOSS, 0.3f);
        DataUtil.registerCompostable(ModBlocks.CARIBOU_MOSS, 0.3f);
        DataUtil.registerCompostable(ModItems.SOUR_BERRIES, 0.3f);
        DataUtil.registerCompostable(ModBlocks.SOUR_BERRY_SPROUTS, 0.3f);
        DataUtil.registerCompostable(ModItems.CACTUS_NEEDLE, 0.3f);
        DataUtil.registerCompostable(ModBlocks.SWAMP_REEDS, 0.5f);
        DataUtil.registerCompostable(ModBlocks.SMOOTH_CACTUS, 0.5f);
        DataUtil.registerCompostable(ModItems.FERN_FIDDLEHEAD, 0.65f);
        DataUtil.registerCompostable(ModItems.WREATH, 1);

        DataUtil.registerCompostable(ModItems.HAZELNUT, 0.3f);
        DataUtil.registerCompostable(ModItems.HAZELNUT_COOKIE, 0.85f);

        DataUtil.registerCompostable(ModBlocks.HAZELNUT_SACK, 1);
        DataUtil.registerCompostable(ModBlocks.SOUR_BERRY_SACK, 1);
    }
}
