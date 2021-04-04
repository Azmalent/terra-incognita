package azmalent.terraincognita.common.registry;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.recipe.FiddleheadSuspiciousStewAdditionRecipe;
import azmalent.terraincognita.common.recipe.FiddleheadSuspiciousStewRecipe;
import azmalent.terraincognita.common.recipe.WreathRecipe;
import net.minecraft.block.ComposterBlock;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.IItemProvider;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Function;

@SuppressWarnings("unused")
public class ModRecipes {
    public static final DeferredRegister<IRecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, TerraIncognita.MODID);

    public static final RegistryObject<SpecialRecipeSerializer<?>> FIDDLEHEAD_SUSPICIOUS_STEW = registerRecipe("fiddlehead_suspicious_stew", FiddleheadSuspiciousStewRecipe::new);
    public static RegistryObject<SpecialRecipeSerializer<?>> FIDDLEHEAD_SUSPICIOUS_STEW_ADDITION = registerRecipe("fiddlehead_suspicious_stew_addition", FiddleheadSuspiciousStewAdditionRecipe::new);
    public static final RegistryObject<SpecialRecipeSerializer<?>> WREATH = registerRecipe("wreath", WreathRecipe::new);

    private static RegistryObject<SpecialRecipeSerializer<?>> registerRecipe(String id, Function<ResourceLocation, SpecialRecipe> constructor) {
        return RECIPES.register(id, () -> new SpecialRecipeSerializer<>(constructor));
    }

    public static void registerCompostable(RegistryObject<? extends Item> item, float value) {
        ComposterBlock.CHANCES.put(item.get(), value);
    }

    public static void registerCompostable(IItemProvider itemProvider, float value) {
        ComposterBlock.CHANCES.put(itemProvider.asItem(), value);
    }

    public static void initCompostables() {
        if (TIConfig.Misc.additionalCompostables.get()) {
            registerCompostable(Items.DEAD_BUSH, 0.3f);
            registerCompostable(Items.BAMBOO, 0.65f);
            registerCompostable(Items.POISONOUS_POTATO, 0.65f);
            registerCompostable(Items.CHORUS_FRUIT, 1.0f);
            registerCompostable(Items.POPPED_CHORUS_FRUIT, 1.0f);
            registerCompostable(Items.CHORUS_FLOWER, 1.0f);
        }

        ModBlocks.FLOWERS.forEach(flower -> registerCompostable(flower, 0.65f));
        ModBlocks.LOTUSES.forEach(lotus -> registerCompostable(lotus, 0.65f));
        ModBlocks.SWEET_PEAS.forEach(sweetPea -> registerCompostable(sweetPea, 0.65f));
        ModWoodTypes.VALUES.forEach(woodType -> {
            registerCompostable(woodType.SAPLING, 0.3f);
            registerCompostable(woodType.LEAVES, 0.3f);
        });

        registerCompostable(ModBlocks.SMALL_LILY_PAD, 0.3f);
        registerCompostable(ModBlocks.HANGING_MOSS, 0.3f);
        registerCompostable(ModBlocks.CARIBOU_MOSS, 0.3f);
        registerCompostable(ModItems.SOUR_BERRIES, 0.3f);
        registerCompostable(ModBlocks.SOUR_BERRY_SPROUTS, 0.3f);
        registerCompostable(ModItems.CACTUS_NEEDLE, 0.3f);
        registerCompostable(ModBlocks.REEDS, 0.5f);
        registerCompostable(ModBlocks.ROOTS, 0.5f);
        registerCompostable(ModBlocks.SMOOTH_CACTUS, 0.5f);
        registerCompostable(ModBlocks.CACTUS_FLOWER, 0.65f);
        registerCompostable(ModItems.FIDDLEHEAD, 0.65f);
        registerCompostable(ModItems.WREATH, 1);

        registerCompostable(ModWoodTypes.APPLE.BLOSSOMING_LEAVES, 0.3f);
        registerCompostable(ModItems.HAZELNUT, 0.3f);
        registerCompostable(ModItems.HAZELNUT_COOKIE, 0.85f);

    }
}
