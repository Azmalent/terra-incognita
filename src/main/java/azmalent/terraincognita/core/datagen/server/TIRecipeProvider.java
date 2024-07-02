package azmalent.terraincognita.core.datagen.server;

import azmalent.cuneiform.common.data.conditions.RecipeConfigCondition;
import azmalent.cuneiform.registry.BlockEntry;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.woodtype.TIWoodType;
import azmalent.terraincognita.core.ModItemTags;
import azmalent.terraincognita.core.registry.ModBannerPatterns;
import azmalent.terraincognita.core.registry.ModBlocks;
import azmalent.terraincognita.core.registry.ModItems;
import azmalent.terraincognita.core.registry.ModWoodTypes;
import com.teamabnormals.clayworks.core.registry.ClayworksRecipes;
import com.teamabnormals.woodworks.core.WoodworksConfig;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.*;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.SimpleCookingSerializer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.entity.BannerPattern;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.AndCondition;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import net.minecraftforge.common.crafting.conditions.OrCondition;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

import static com.teamabnormals.clayworks.core.data.server.ClayworksRecipeProvider.KILN_CONFIG;

@SuppressWarnings("DataFlowIssue")
public class TIRecipeProvider extends RecipeProvider {
    private static ICondition configFlag(String flag) {
        return new RecipeConfigCondition(TerraIncognita.MODID, flag);
    }

    private static ICondition and(ICondition... conditions) {
        return new AndCondition(conditions);
    }

    private static ICondition or(ICondition... conditions) {
        return new OrCondition(conditions);
    }

    private static final ICondition QUARK_LOADED = new ModLoadedCondition("quark");
    private static final ICondition WOODWORKS_LOADED = new ModLoadedCondition("woodworks");
    private static final ICondition BOATLOAD_LOADED = new ModLoadedCondition("boatload");
    private static final ICondition FARMERS_DELIGHT_LOADED = new ModLoadedCondition("farmersdelight");

    private static final ICondition DANDELION_PUFF_ENABLED = configFlag("dandelion_puff");
    private static final ICondition FIELD_FLOWERS_ENABLED = configFlag("field_flowers");
    private static final ICondition FOREST_FLOWERS_ENABLED = configFlag("forest_flowers");
    private static final ICondition ALPINE_FLOWERS_ENABLED = configFlag("alpine_flowers");
    private static final ICondition ARCTIC_FLOWERS_ENABLED = configFlag("arctic_flowers");
    private static final ICondition SWAMP_FLOWERS_ENABLED = configFlag("swamp_flowers");
    private static final ICondition SAVANNA_FLOWERS_ENABLED = configFlag("savanna_flowers");
    private static final ICondition CACTUS_FLOWERS_ENABLED = configFlag("cactus_flowers");
    private static final ICondition LOTUS_ENABLED = configFlag("lotus");
    private static final ICondition SWEET_PEAS_ENABLED = configFlag("sweet_peas");
    private static final ICondition HANGING_MOSS_ENABLED = configFlag("hanging_moss");
    private static final ICondition FERN_FIDDLEHEADS_ENABLED = configFlag("fern_fiddleheads");
    private static final ICondition MOSSY_GRAVEL_ENABLED = configFlag("mossy_gravel");
    private static final ICondition SOUR_BERRIES_ENABLED = configFlag("sour_berries");
    private static final ICondition BUTTERFLIES_ENABLED = configFlag("butterflies");

    private static final ICondition APPLE_ENABLED = configFlag("apple");
    private static final ICondition HAZEL_ENABLED = configFlag("hazel");
    private static final ICondition LARCH_ENABLED = configFlag("larch");
    private static final ICondition GINKGO_ENABLED = configFlag("ginkgo");

    public TIRecipeProvider(DataGenerator gen) {
        super(gen);
    }

    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        woodRecipes(consumer, ModWoodTypes.APPLE, APPLE_ENABLED);
        woodRecipes(consumer, ModWoodTypes.HAZEL, HAZEL_ENABLED);
        woodRecipes(consumer, ModWoodTypes.LARCH, LARCH_ENABLED);
        woodRecipes(consumer, ModWoodTypes.GINKGO, GINKGO_ENABLED);

        TagKey<Item> appleLogs = ModItemTags.createTag("apple_logs");
        BlockEntry<?> blossomingLeaves = ModWoodTypes.APPLE.BLOSSOMING_LEAVES;
        conditionalRecipe(consumer, and(APPLE_ENABLED, QUARK_LOADED), ShapedRecipeBuilder.shaped(ModWoodTypes.APPLE.BLOSSOMING_HEDGE, 2).pattern("L").pattern("W").define('L', blossomingLeaves).define('W', appleLogs).unlockedBy("has_any_apple_logs", has(appleLogs)), "compat/quark/crafting/" + name(ModWoodTypes.APPLE.BLOSSOMING_HEDGE));
        conditionalRecipe(consumer, and(APPLE_ENABLED, QUARK_LOADED), ShapedRecipeBuilder.shaped(ModWoodTypes.APPLE.BLOSSOMING_LEAF_CARPET, 3).pattern("LL").define('L', blossomingLeaves).unlockedBy(getHasName(blossomingLeaves), has(blossomingLeaves)), "compat/quark/crafting/" + name(ModWoodTypes.APPLE.BLOSSOMING_LEAF_CARPET));

        buildDyeRecipes(consumer);

        conditionalRecipe(consumer, MOSSY_GRAVEL_ENABLED, ShapelessRecipeBuilder.shapeless(ModBlocks.MOSSY_GRAVEL).requires(Blocks.GRAVEL).requires(Blocks.VINE).unlockedBy(getHasName(Blocks.VINE), has(Blocks.VINE)), "crafting/mossy_gravel_from_vine");
        conditionalRecipe(consumer, MOSSY_GRAVEL_ENABLED, ShapelessRecipeBuilder.shapeless(ModBlocks.MOSSY_GRAVEL).requires(Blocks.GRAVEL).requires(Blocks.MOSS_BLOCK).unlockedBy(getHasName(Blocks.MOSS_BLOCK), has(Blocks.MOSS_BLOCK)), "crafting/mossy_gravel_from_moss_block");

        conditionalRecipe(consumer, HANGING_MOSS_ENABLED, ShapelessRecipeBuilder.shapeless(Blocks.MOSSY_COBBLESTONE).requires(Blocks.COBBLESTONE).requires(ModBlocks.HANGING_MOSS).unlockedBy(getHasName(ModBlocks.HANGING_MOSS), has(ModBlocks.HANGING_MOSS)), "crafting/mossy_cobblestone_from_hanging_moss");
        conditionalRecipe(consumer, HANGING_MOSS_ENABLED, ShapelessRecipeBuilder.shapeless(Blocks.MOSSY_STONE_BRICKS).requires(Blocks.STONE_BRICKS).requires(ModBlocks.HANGING_MOSS).unlockedBy(getHasName(ModBlocks.HANGING_MOSS), has(ModBlocks.HANGING_MOSS)), "crafting/mossy_stone_bricks_from_hanging_moss");
        conditionalRecipe(consumer, and(MOSSY_GRAVEL_ENABLED, HANGING_MOSS_ENABLED), ShapelessRecipeBuilder.shapeless(ModBlocks.MOSSY_GRAVEL).requires(Blocks.GRAVEL).requires(ModBlocks.HANGING_MOSS).unlockedBy(getHasName(ModBlocks.HANGING_MOSS), has(ModBlocks.HANGING_MOSS)), "crafting/mossy_gravel_from_hanging_moss");

        storageBlockRecipes(consumer, HAZEL_ENABLED, ModItems.HAZELNUT, ModBlocks.HAZELNUT_SACK);

        conditionalRecipe(consumer, SOUR_BERRIES_ENABLED, oneIngredientRecipe(ModItems.SOUR_BERRIES, ModBlocks.SOUR_BERRY_SPROUTS), "crafting/sour_berry_sprouts");
        storageBlockRecipes(consumer, SOUR_BERRIES_ENABLED, ModItems.SOUR_BERRIES, ModBlocks.SOUR_BERRY_SACK);

        bannerRecipe(consumer, BUTTERFLIES_ENABLED,      ModItems.BOTTLED_BUTTERFLY, ModBannerPatterns.BUTTERFLY);
        bannerRecipe(consumer, FERN_FIDDLEHEADS_ENABLED, ModItems.FERN_FIDDLEHEAD,   ModBannerPatterns.FERN);
        bannerRecipe(consumer, SWAMP_FLOWERS_ENABLED,    ModBlocks.WATER_FLAG,       ModBannerPatterns.FLEUR_DE_LIS);
    }

    private void buildDyeRecipes(Consumer<FinishedRecipe> consumer) {
        dyeRecipe(consumer, DANDELION_PUFF_ENABLED,  ModBlocks.DANDELION_PUFF, Items.LIGHT_GRAY_DYE);
        dyeRecipe(consumer, FIELD_FLOWERS_ENABLED,   ModBlocks.CHICORY, Items.LIGHT_BLUE_DYE);
        dyeRecipe(consumer, FIELD_FLOWERS_ENABLED,   ModBlocks.DAFFODIL, Items.YELLOW_DYE);
        dyeRecipe(consumer, FIELD_FLOWERS_ENABLED,   ModBlocks.YARROW, Items.LIGHT_GRAY_DYE);

        dyeRecipe(consumer, FOREST_FLOWERS_ENABLED,  ModBlocks.FOXGLOVE, Items.PINK_DYE);
        dyeRecipe(consumer, FOREST_FLOWERS_ENABLED,  ModBlocks.PINK_PRIMROSE, Items.PINK_DYE);
        dyeRecipe(consumer, FOREST_FLOWERS_ENABLED,  ModBlocks.PURPLE_PRIMROSE, Items.PURPLE_DYE);
        dyeRecipe(consumer, FOREST_FLOWERS_ENABLED,  ModBlocks.WILD_GARLIC, Items.LIGHT_GRAY_DYE);
        dyeRecipe(consumer, FOREST_FLOWERS_ENABLED,  ModBlocks.YELLOW_PRIMROSE, Items.LIGHT_GRAY_DYE);

        dyeRecipe(consumer, ALPINE_FLOWERS_ENABLED,  ModBlocks.ALPINE_PINK, Items.PINK_DYE);
        dyeRecipe(consumer, ALPINE_FLOWERS_ENABLED,  ModBlocks.ASTER, Items.MAGENTA_DYE);
        dyeRecipe(consumer, ALPINE_FLOWERS_ENABLED,  ModBlocks.EDELWEISS, Items.WHITE_DYE);
        dyeRecipe(consumer, ALPINE_FLOWERS_ENABLED,  ModBlocks.GENTIAN, Items.BLUE_DYE);
        dyeRecipe(consumer, ALPINE_FLOWERS_ENABLED,  ModBlocks.MAGENTA_SAXIFRAGE, Items.MAGENTA_DYE);
        dyeRecipe(consumer, ALPINE_FLOWERS_ENABLED,  ModBlocks.YELLOW_SAXIFRAGE, Items.LIGHT_GRAY_DYE);

        dyeRecipe(consumer, ARCTIC_FLOWERS_ENABLED,  ModBlocks.FIREWEED, Items.PINK_DYE);
        dyeRecipe(consumer, ARCTIC_FLOWERS_ENABLED,  ModBlocks.HEATHER, Items.PINK_DYE);
        dyeRecipe(consumer, ARCTIC_FLOWERS_ENABLED,  ModBlocks.WHITE_DRYAD, Items.WHITE_DYE);
        dyeRecipe(consumer, ARCTIC_FLOWERS_ENABLED,  ModBlocks.WHITE_RHODODENDRON, Items.WHITE_DYE);

        dyeRecipe(consumer, SWAMP_FLOWERS_ENABLED,   ModBlocks.FORGET_ME_NOT, Items.LIGHT_BLUE_DYE);
        dyeRecipe(consumer, SWAMP_FLOWERS_ENABLED,   ModBlocks.GLOBEFLOWER, Items.ORANGE_DYE);
        dyeRecipe(consumer, SWAMP_FLOWERS_ENABLED,   ModBlocks.WATER_FLAG, Items.YELLOW_DYE);

        dyeRecipe(consumer, SAVANNA_FLOWERS_ENABLED, ModBlocks.BLACK_IRIS, Items.BLACK_DYE);
        dyeRecipe(consumer, SAVANNA_FLOWERS_ENABLED, ModBlocks.BLUE_IRIS, Items.BLUE_DYE);
        dyeRecipe(consumer, SAVANNA_FLOWERS_ENABLED, ModBlocks.PURPLE_IRIS, Items.PURPLE_DYE);
        dyeRecipe(consumer, SAVANNA_FLOWERS_ENABLED, ModBlocks.MARIGOLD, Items.ORANGE_DYE);
        dyeRecipe(consumer, SAVANNA_FLOWERS_ENABLED, ModBlocks.OLEANDER, Items.PINK_DYE);
        dyeRecipe(consumer, SAVANNA_FLOWERS_ENABLED, ModBlocks.SAGE, Items.PURPLE_DYE);
        dyeRecipe(consumer, SAVANNA_FLOWERS_ENABLED, ModBlocks.SNAPDRAGON, Items.YELLOW_DYE);

        dyeRecipe(consumer, CACTUS_FLOWERS_ENABLED,  ModBlocks.CACTUS_FLOWER, Items.PINK_DYE);

        dyeRecipe(consumer, LOTUS_ENABLED, ModBlocks.PINK_LOTUS, Items.PINK_DYE);
        dyeRecipe(consumer, LOTUS_ENABLED, ModBlocks.WHITE_LOTUS, Items.WHITE_DYE);
        dyeRecipe(consumer, LOTUS_ENABLED, ModBlocks.YELLOW_LOTUS, Items.LIGHT_GRAY_DYE);

        ModBlocks.SWEET_PEAS.forEach(block -> {
            String blockName = block.get().getRegistryName().getPath();
            String colorName = blockName.substring(0, blockName.indexOf("_sweet_peas")).toUpperCase();
            DyeColor color = DyeColor.valueOf(colorName);

            dyeRecipe(consumer, SWEET_PEAS_ENABLED, block, DyeItem.byColor(color));
        });

        bakingRecipe(consumer, HANGING_MOSS_ENABLED, ModBlocks.HANGING_MOSS, Items.GREEN_DYE, "dye_from_hanging_moss");
    }

    private String name(ItemLike item) {
        return item.asItem().getRegistryName().getPath();
    }

    private void woodRecipes(Consumer<FinishedRecipe> consumer, TIWoodType type, ICondition enabled) {
        TagKey<Item> anyLogs = ModItemTags.createTag(type.name + "_logs");
        String hasAnyLogs = "has_any_" + type.name + "_logs";

        conditionalRecipe(consumer, enabled, ShapedRecipeBuilder.shaped(type.WOOD, 3).pattern("LL").pattern("LL").define('L', type.LOG).unlockedBy(getHasName(type.LOG), has(type.LOG)), "crafting/" + name(type.WOOD));
        conditionalRecipe(consumer, enabled, ShapedRecipeBuilder.shaped(type.STRIPPED_WOOD, 3).pattern("LL").pattern("LL").define('L', type.STRIPPED_LOG).unlockedBy(getHasName(type.STRIPPED_LOG), has(type.STRIPPED_LOG)), "crafting/" + name(type.STRIPPED_WOOD));
        conditionalRecipe(consumer, enabled, oneIngredientRecipe(anyLogs, type.PLANKS, 4, hasAnyLogs), "crafting/" + name(type.PLANKS));
        conditionalRecipe(consumer, enabled, ShapedRecipeBuilder.shaped(type.STAIRS, 4).pattern("P  ").pattern("PP ").pattern("PPP").define('P', type.PLANKS).unlockedBy(getHasName(type.PLANKS), has(type.PLANKS)), "crafting/" + name(type.STAIRS));
        conditionalRecipe(consumer, enabled, ShapedRecipeBuilder.shaped(type.SLAB, 6).pattern("PPP").define('P', type.PLANKS).unlockedBy(getHasName(type.PLANKS), has(type.PLANKS)), "crafting/" + name(type.SLAB));
        conditionalRecipe(consumer, enabled, ShapedRecipeBuilder.shaped(type.FENCE, 3).pattern("P#P").pattern("P#P").define('P', type.PLANKS).define('#', Items.STICK).unlockedBy(getHasName(type.PLANKS), has(type.PLANKS)), "crafting/" + name(type.FENCE));
        conditionalRecipe(consumer, enabled, ShapedRecipeBuilder.shaped(type.FENCE_GATE).pattern("#P#").pattern("#P#").define('P', type.PLANKS).define('#', Items.STICK).unlockedBy(getHasName(type.PLANKS), has(type.PLANKS)), "crafting/" + name(type.FENCE_GATE));
        conditionalRecipe(consumer, enabled, ShapedRecipeBuilder.shaped(type.SIGN, 3).pattern("PPP").pattern("PPP").pattern(" # ").define('P', type.PLANKS).define('#', Items.STICK).unlockedBy(getHasName(type.PLANKS), has(type.PLANKS)), "crafting/" + name(type.SIGN));
        conditionalRecipe(consumer, enabled, ShapedRecipeBuilder.shaped(type.DOOR, 3).pattern("PP").pattern("PP").pattern("PP").define('P', type.PLANKS).unlockedBy(getHasName(type.PLANKS), has(type.PLANKS)), "crafting/" + name(type.DOOR));
        conditionalRecipe(consumer, enabled, ShapedRecipeBuilder.shaped(type.TRAPDOOR, 3).pattern("PPP").pattern("PPP").define('P', type.PLANKS).unlockedBy(getHasName(type.PLANKS), has(type.PLANKS)), "crafting/" + name(type.TRAPDOOR));
        conditionalRecipe(consumer, enabled, oneIngredientRecipe(type.PLANKS, type.BUTTON), "crafting/" + name(type.BUTTON));
        conditionalRecipe(consumer, enabled, ShapedRecipeBuilder.shaped(type.PRESSURE_PLATE).pattern("PP").define('P', type.PLANKS).unlockedBy(getHasName(type.PLANKS), has(type.PLANKS)), "crafting/" + name(type.PRESSURE_PLATE));
        conditionalRecipe(consumer, enabled, ShapedRecipeBuilder.shaped(type.BOAT).pattern("P P").pattern("PPP").define('P', type.PLANKS).unlockedBy(getHasName(type.PLANKS), has(type.PLANKS)), "crafting/" + name(type.BOAT));

        //Either Quark or Woodworks
        var condition = and(enabled, or(QUARK_LOADED, WOODWORKS_LOADED));
        conditionalRecipe(consumer, condition, ShapedRecipeBuilder.shaped(type.LADDER, 4).pattern("# #").pattern("#P#").pattern("# #").define('#', Items.STICK).define('P', type.PLANKS).unlockedBy(getHasName(type.PLANKS), has(type.PLANKS)), "crafting/" + name(type.LADDER));
        conditionalRecipe(consumer, condition, ShapedRecipeBuilder.shaped(type.BOOKSHELF).pattern("PPP").pattern("BBB").pattern("PPP").define('P', type.PLANKS).define('B', Items.BOOK).unlockedBy(getHasName(type.PLANKS), has(type.PLANKS)), "crafting/" + name(type.BOOKSHELF));
        conditionalRecipe(consumer, condition, ShapedRecipeBuilder.shaped(type.CHEST).pattern("PPP").pattern("P P").pattern("PPP").define('P', type.PLANKS).unlockedBy(getHasName(type.PLANKS), has(type.PLANKS)), "crafting/" + name(type.CHEST));
        conditionalRecipe(consumer, condition, ShapelessRecipeBuilder.shapeless(type.TRAPPED_CHEST).requires(type.CHEST).requires(Blocks.TRIPWIRE_HOOK).unlockedBy(getHasName(type.PLANKS), has(type.PLANKS)), "crafting/" + name(type.TRAPPED_CHEST));

        //Quark
        //TODO: re-add logs to chest recipe (needs to check Quark flag)
        condition = and(enabled, QUARK_LOADED);
        conditionalRecipe(consumer, condition, ShapedRecipeBuilder.shaped(type.VERTICAL_PLANKS, 3).pattern("P").pattern("P").pattern("P").define('P', type.PLANKS).unlockedBy(getHasName(type.PLANKS), has(type.PLANKS)), "compat/quark/crafting/" + name(type.VERTICAL_PLANKS));
        conditionalRecipe(consumer, condition, oneIngredientRecipe(type.VERTICAL_PLANKS, type.PLANKS), "compat/quark/crafting/" + name(type.VERTICAL_PLANKS) + "_revert");
        conditionalRecipe(consumer, condition, ShapedRecipeBuilder.shaped(type.VERTICAL_SLAB, 3).pattern("S").pattern("S").pattern("S").define('S', type.SLAB).unlockedBy(getHasName(type.SLAB), has(type.SLAB)), "compat/quark/crafting/" + name(type.VERTICAL_SLAB));
        conditionalRecipe(consumer, condition, oneIngredientRecipe(type.VERTICAL_SLAB, type.SLAB), "compat/quark/crafting/" + name(type.VERTICAL_SLAB) + "_revert");
        conditionalRecipe(consumer, condition, ShapedRecipeBuilder.shaped(type.POST, 8).pattern("W").pattern("W").pattern("W").define('W', type.WOOD).unlockedBy(getHasName(type.WOOD), has(type.WOOD)), "compat/quark/crafting/" + name(type.POST));
        conditionalRecipe(consumer, condition, ShapedRecipeBuilder.shaped(type.STRIPPED_POST, 8).pattern("W").pattern("W").pattern("W").define('W', type.STRIPPED_WOOD).unlockedBy(getHasName(type.STRIPPED_WOOD), has(type.STRIPPED_WOOD)), "compat/quark/crafting/" + name(type.STRIPPED_POST));
        conditionalRecipe(consumer, condition, ShapedRecipeBuilder.shaped(type.HEDGE, 2).pattern("L").pattern("W").define('L', type.LEAVES).define('W', anyLogs).unlockedBy(hasAnyLogs, has(anyLogs)), "compat/quark/crafting/" + name(type.HEDGE));
        conditionalRecipe(consumer, condition, ShapedRecipeBuilder.shaped(type.LEAF_CARPET, 3).pattern("LL").define('L', type.LEAVES).unlockedBy(getHasName(type.LEAVES), has(type.LEAVES)), "compat/quark/crafting/" + name(type.LEAF_CARPET));

        //Woodworks
        //TODO: check woodworks config flags
        condition = and(enabled, WOODWORKS_LOADED);
        conditionalRecipe(consumer, condition, ShapedRecipeBuilder.shaped(type.BEEHIVE).pattern("PPP").pattern("HHH").pattern("PPP").define('P', type.PLANKS).define('H', Items.HONEYCOMB).unlockedBy(getHasName(type.PLANKS), has(type.PLANKS)), "compat/woodworks/crafting/" + name(type.BEEHIVE));
        conditionalRecipe(consumer, condition, ShapedRecipeBuilder.shaped(type.BOARDS).pattern("P").pattern("P").pattern("P").define('P', type.PLANKS).unlockedBy(getHasName(type.PLANKS), has(type.PLANKS)), "compat/woodworks/crafting/" + name(type.BOARDS));
        conditionalRecipe(consumer, condition, oneIngredientRecipe(type.LEAVES, type.LEAF_PILE, 4), "compat/woodworks/crafting/" + name(type.LEAF_PILE));

        sawingRecipe(consumer, condition, Ingredient.of(anyLogs), type.PLANKS, 4, name(type.PLANKS) + "_from_logs");
        sawingRecipe(consumer, condition, Ingredient.of(anyLogs), type.BOARDS, 4, name(type.BOARDS) + "_from_logs");
        sawingRecipe(consumer, condition, Ingredient.of(anyLogs), type.BUTTON, 4, name(type.BUTTON) + "_from_logs");
        sawingRecipe(consumer, condition, Ingredient.of(anyLogs), type.DOOR, 2, name(type.DOOR) + "_from_logs");
        sawingRecipe(consumer, condition, Ingredient.of(anyLogs), type.FENCE, 4, name(type.FENCE) + "_from_logs");
        sawingRecipe(consumer, condition, Ingredient.of(anyLogs), type.FENCE_GATE, 1, name(type.FENCE_GATE) + "_from_logs");
        sawingRecipe(consumer, condition, Ingredient.of(anyLogs), type.PRESSURE_PLATE, 2, name(type.PRESSURE_PLATE) + "_from_logs");
        sawingRecipe(consumer, condition, Ingredient.of(anyLogs), type.SIGN, 2, name(type.SIGN) + "_from_logs");
        sawingRecipe(consumer, condition, Ingredient.of(anyLogs), type.SLAB, 8, name(type.SLAB) + "_from_logs");
        sawingRecipe(consumer, condition, Ingredient.of(anyLogs), type.STAIRS, 4, name(type.STAIRS) + "_from_logs");
        sawingRecipe(consumer, condition, Ingredient.of(anyLogs), type.TRAPDOOR, 2, name(type.TRAPDOOR) + "_from_logs");
        sawingRecipe(consumer, condition, Ingredient.of(anyLogs), type.LADDER, 4, name(type.LADDER) + "_from_logs");
        sawingRecipe(consumer, condition, Ingredient.of(type.PLANKS), type.BOARDS, 1, name(type.BOARDS) + "_from_planks");
        sawingRecipe(consumer, condition, Ingredient.of(type.PLANKS), type.BUTTON, 1, name(type.BUTTON) + "_from_planks");
        sawingRecipe(consumer, condition, Ingredient.of(type.PLANKS), type.FENCE, 1, name(type.FENCE) + "_from_planks");
        sawingRecipe(consumer, condition, Ingredient.of(type.PLANKS), type.SLAB, 2, name(type.SLAB) + "_from_planks");
        sawingRecipe(consumer, condition, Ingredient.of(type.PLANKS), type.STAIRS, 1, name(type.STAIRS) + "_from_planks");
        sawingRecipe(consumer, condition, Ingredient.of(type.PLANKS), type.LADDER, 1, name(type.LADDER) + "_from_planks");

        //Boatload
        //TODO: big boats

        //Farmer's Delight
        conditionalRecipe(consumer, and(enabled, FARMERS_DELIGHT_LOADED), ShapedRecipeBuilder.shaped(type.CABINET).pattern("SSS").pattern("T T").pattern("SSS").define('S', type.SLAB).define('T', type.TRAPDOOR).unlockedBy(getHasName(type.PLANKS), has(type.PLANKS)), "compat/farmersdelight/crafting/" + name(type.CABINET));
        //TODO: cutting board recipes

        //Create
        //TODO: create recipes
    }

    private ShapelessRecipeBuilder oneIngredientRecipe(ItemLike input, ItemLike output) {
        return oneIngredientRecipe(input, output, 1);
    }

    private ShapelessRecipeBuilder oneIngredientRecipe(ItemLike input, ItemLike output, int amount) {
        return ShapelessRecipeBuilder.shapeless(output, amount).requires(input).unlockedBy(getHasName(input), has(input));
    }

    private ShapelessRecipeBuilder oneIngredientRecipe(TagKey<Item> input, ItemLike output, int amount, String hasName) {
        return ShapelessRecipeBuilder.shapeless(output, amount).requires(input).unlockedBy(hasName, has(input));
    }

    private void storageBlockRecipes(Consumer<FinishedRecipe> consumer, ICondition condition, ItemLike item, ItemLike block) {
        var compress = ShapedRecipeBuilder.shaped(block).pattern("XXX").pattern("XXX").pattern("XXX").define('X', item).unlockedBy(getHasName(item), has(item));
        conditionalRecipe(consumer, and(QUARK_LOADED, condition), compress, "compat/quark/crafting/" + name(block));

        var uncompress = oneIngredientRecipe(block, item, 9);
        conditionalRecipe(consumer, and(QUARK_LOADED, condition), uncompress, "compat/quark/crafting/" + name(block) + "_uncompress");
    }

    private void bannerRecipe(Consumer<FinishedRecipe> consumer, ICondition condition, ItemLike ingredient, BannerPattern pattern) {
        Item patternItem = ModBannerPatterns.PATTERN_ITEMS.get(pattern).get();

        var recipe = ShapelessRecipeBuilder.shapeless(patternItem).requires(Items.PAPER).requires(ingredient).unlockedBy(getHasName(ingredient), has(ingredient));

        conditionalRecipe(consumer, condition, recipe, "crafting/" + name(patternItem));
    }

    private void conditionalRecipe(Consumer<FinishedRecipe> consumer, ICondition condition, RecipeBuilder recipe, String recipeName) {
        ResourceLocation id = TerraIncognita.prefix(recipeName);

        ConditionalRecipe.builder()
            .addCondition(condition)
            .addRecipe(c -> recipe.save(c, id))
            .generateAdvancement(TerraIncognita.prefix("recipes/" + id.getPath()))
            .build(consumer, id);
    }

    private void dyeRecipe(Consumer<FinishedRecipe> consumer, ICondition condition, BlockEntry<?> flower, Item dye) {
        var recipe = oneIngredientRecipe(flower, dye, flower.get() instanceof DoublePlantBlock ? 2 : 1).group(dye.getRegistryName().getPath());
        conditionalRecipe(consumer, condition, recipe, "crafting/%s_from_%s".formatted(name(dye), name(flower)));
    }

    private void smeltingRecipe(Consumer<FinishedRecipe> consumer, ICondition condition, ItemLike input, ItemLike output, String recipeName) {
        var recipe = SimpleCookingRecipeBuilder.cooking(Ingredient.of(input), output, 1, 200, SimpleCookingSerializer.SMELTING_RECIPE).unlockedBy(getHasName(input), has(input));

        conditionalRecipe(consumer, condition, recipe, "smelting/" + recipeName);
    }

    private void bakingRecipe(Consumer<FinishedRecipe> consumer, ICondition condition, ItemLike input, ItemLike output, String recipeName) {
        smeltingRecipe(consumer, condition, input, output, recipeName);

        var recipe = SimpleCookingRecipeBuilder.cooking(Ingredient.of(input), output, 1, 100, ClayworksRecipes.ClayworksRecipeSerializers.BAKING.get()).unlockedBy(getHasName(input), has(input));
        conditionalRecipe(consumer, and(condition, KILN_CONFIG), recipe, "compat/clayworks/baking/" + recipeName);
    }

    private void sawingRecipe(Consumer<FinishedRecipe> consumer, ICondition condition, Ingredient input, ItemLike output, int count, String recipeName) {
        var recipe = ShapelessRecipeBuilder.shapeless(output, count).requires(input).unlockedBy(getHasName(input), has(input));
        conditionalRecipe(consumer, condition, recipe, "compat/woodworks/sawing/" + recipeName);
    }
}
