package azmalent.terraincognita.common.init;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.recipe.FiddleheadSuspiciousStewAdditionRecipe;
import azmalent.terraincognita.common.recipe.FiddleheadSuspiciousStewRecipe;
import azmalent.terraincognita.common.recipe.FlowerBandRecipe;
import net.minecraft.block.ComposterBlock;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.IRecipeSerializer;
import net.minecraft.item.crafting.SpecialRecipeSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModRecipes {
    public static DeferredRegister<IRecipeSerializer<?>> RECIPES = DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, TerraIncognita.MODID);

    public static RegistryObject<SpecialRecipeSerializer<?>> FIDDLEHEAD_SUSPICIOUS_STEW;
    public static RegistryObject<SpecialRecipeSerializer<?>> FIDDLEHEAD_SUSPICIOUS_STEW_ADDITION;
    public static RegistryObject<SpecialRecipeSerializer<?>> FLOWER_BAND;

    static {
        if (TIConfig.Food.fiddlehead.get()) {
            FIDDLEHEAD_SUSPICIOUS_STEW = RECIPES.register("fiddlehead_suspicious_stew",
                () -> new SpecialRecipeSerializer<>(FiddleheadSuspiciousStewRecipe::new)
            );
            FIDDLEHEAD_SUSPICIOUS_STEW_ADDITION = RECIPES.register("fiddlehead_suspicious_stew_addition",
                () -> new SpecialRecipeSerializer<>(FiddleheadSuspiciousStewAdditionRecipe::new)
            );
        }

        if (TIConfig.Flora.flowerBand.get()) {
            FLOWER_BAND = RECIPES.register("flower_band",
                () -> new SpecialRecipeSerializer<>(FlowerBandRecipe::new)
            );
        }
    }


    public static void registerComposterRecipes() {
        if (TIConfig.Tweaks.additionalCompostables.get()) {
            ComposterBlock.CHANCES.put(Items.DEAD_BUSH, 0.3f);
            ComposterBlock.CHANCES.put(Items.BAMBOO, 0.65f);
            ComposterBlock.CHANCES.put(Items.POISONOUS_POTATO, 0.65f);
            ComposterBlock.CHANCES.put(Items.CHORUS_FRUIT, 1.0f);
            ComposterBlock.CHANCES.put(Items.POPPED_CHORUS_FRUIT, 1.0f);
            ComposterBlock.CHANCES.put(Items.CHORUS_FLOWER, 1.0f);
        }

        for (ModBlocks.PottablePlantEntry flower : ModBlocks.FLOWERS) {
            ComposterBlock.CHANCES.put(flower.getItem(), 0.65f);
        }

        if (TIConfig.Flora.lotus.get()) {
            ComposterBlock.CHANCES.put(ModBlocks.PINK_LOTUS.getItem(), 0.65f);
            ComposterBlock.CHANCES.put(ModBlocks.WHITE_LOTUS.getItem(), 0.65f);
            ComposterBlock.CHANCES.put(ModBlocks.YELLOW_LOTUS.getItem(), 0.65f);
        }

        if (TIConfig.Flora.smallLilypad.get()) {
            ComposterBlock.CHANCES.put(ModBlocks.SMALL_LILYPAD.getItem(), 0.3f);
        }

        if (TIConfig.Flora.reeds.get()) {
            ComposterBlock.CHANCES.put(ModBlocks.REEDS.getItem(), 0.5f);
        }
    }
}
