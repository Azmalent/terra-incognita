package azmalent.terraincognita.common.init;

import azmalent.cuneiform.lib.registry.BlockEntry;
import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.block.blocksets.PottablePlantEntry;
import azmalent.terraincognita.common.recipe.FiddleheadSuspiciousStewAdditionRecipe;
import azmalent.terraincognita.common.recipe.FiddleheadSuspiciousStewRecipe;
import azmalent.terraincognita.common.recipe.WreathRecipe;
import net.minecraft.block.ComposterBlock;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipe;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Function;

public class ModRecipes {
    public static DeferredRegister<IRecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, TerraIncognita.MODID);

    public static RegistryObject<SpecialRecipeSerializer<?>> FIDDLEHEAD_SUSPICIOUS_STEW;
    public static RegistryObject<SpecialRecipeSerializer<?>> FIDDLEHEAD_SUSPICIOUS_STEW_ADDITION;
    public static RegistryObject<SpecialRecipeSerializer<?>> WREATH;

    static {
        if (TIConfig.Food.fiddlehead.get()) {
            FIDDLEHEAD_SUSPICIOUS_STEW = registerRecipe("fiddlehead_suspicious_stew", FiddleheadSuspiciousStewRecipe::new);
            FIDDLEHEAD_SUSPICIOUS_STEW_ADDITION = registerRecipe("fiddlehead_suspicious_stew_addition", FiddleheadSuspiciousStewAdditionRecipe::new);;
        }

        if (TIConfig.Flora.wreath.get()) {
            WREATH = registerRecipe("flower_band", WreathRecipe::new);
        }
    }

    private static RegistryObject<SpecialRecipeSerializer<?>> registerRecipe(String id, Function<ResourceLocation, SpecialRecipe> constructor) {
        return RECIPES.register(id, () -> new SpecialRecipeSerializer<>(constructor));
    }

    public static void registerCompostable(BlockEntry blockEntry, float value) {
        if (blockEntry != null) {
            ComposterBlock.CHANCES.put(blockEntry.getItem(), value);
        }
    }

    public static void registerCompostable(PottablePlantEntry plantEntry, float value) {
        if (plantEntry != null) {
            ComposterBlock.CHANCES.put(plantEntry.getItem(), value);
        }
    }

    public static void registerComposterRecipes() {
        if (TIConfig.Misc.additionalCompostables.get()) {
            ComposterBlock.CHANCES.put(Items.DEAD_BUSH, 0.3f);
            ComposterBlock.CHANCES.put(Items.BAMBOO, 0.65f);
            ComposterBlock.CHANCES.put(Items.POISONOUS_POTATO, 0.65f);
            ComposterBlock.CHANCES.put(Items.CHORUS_FRUIT, 1.0f);
            ComposterBlock.CHANCES.put(Items.POPPED_CHORUS_FRUIT, 1.0f);
            ComposterBlock.CHANCES.put(Items.CHORUS_FLOWER, 1.0f);
        }

        ModBlocks.FLOWERS.forEach(flower -> registerCompostable(flower, 0.65f));
        ModBlocks.LOTUSES.forEach(lotus -> registerCompostable(lotus, 0.65f));
        registerCompostable(ModBlocks.SMALL_LILY_PAD, 0.3f);
        registerCompostable(ModBlocks.REEDS, 0.5f);
        registerCompostable(ModBlocks.ROOTS, 0.5f);
        registerCompostable(ModBlocks.HANGING_MOSS, 0.3f);

        ModWoodTypes.VALUES.forEach(woodType -> {
            registerCompostable(woodType.SAPLING, 0.3f);
            registerCompostable(woodType.LEAVES, 0.3f);
        });

        registerCompostable(ModWoodTypes.APPLE.BLOSSOMING_LEAVES, 0.3f);
    }
}
