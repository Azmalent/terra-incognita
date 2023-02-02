package azmalent.terraincognita.datagen.server;

import azmalent.cuneiform.common.data.conditions.RecipeConfigCondition;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.ModItemTags;
import azmalent.terraincognita.common.recipe.WreathRecipe;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModRecipes;
import azmalent.terraincognita.common.registry.ModWoodTypes;
import azmalent.terraincognita.common.woodtype.TIWoodType;
import com.google.common.collect.Maps;
import net.minecraft.advancements.critereon.EntityPredicate;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.recipes.*;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.crafting.ConditionalRecipe;
import net.minecraftforge.common.crafting.conditions.ICondition;
import net.minecraftforge.common.crafting.conditions.IConditionBuilder;
import net.minecraftforge.common.crafting.conditions.ModLoadedCondition;
import org.jetbrains.annotations.NotNull;
import vazkii.quark.base.module.config.FlagRecipeCondition;

import java.util.Map;
import java.util.function.Consumer;

import static azmalent.terraincognita.common.registry.ModWoodTypes.APPLE;

public class TIRecipeProvider extends RecipeProvider implements IConditionBuilder {
    private final ICondition QUARK_LOADED = new ModLoadedCondition("quark");
    private final ICondition WOODWORKS_LOADED = new ModLoadedCondition("woodworks");
    private final ICondition BOATLOAD_LOADED = new ModLoadedCondition("boatload");
    private final ICondition FARMERS_DELIGHT_LOADED = new ModLoadedCondition("farmersdelight");

    private final ICondition CHESTS_ENABLED = or(WOODWORKS_LOADED, QUARK_LOADED);
    private final ICondition BOOKSHELVES_ENABLED = or(WOODWORKS_LOADED, QUARK_LOADED);
    private final ICondition LADDERS_ENABLED = or(WOODWORKS_LOADED, QUARK_LOADED);

    private final Map<ICondition, Map<ItemLike, ItemStack>> DYE_RECIPES = Map.of(
        configCondition("alpine_flowers"), Map.of(
            ModBlocks.ALPINE_PINK, new ItemStack(Items.PINK_DYE),
            ModBlocks.ASTER, new ItemStack(Items.MAGENTA_DYE),
            ModBlocks.EDELWEISS, new ItemStack(Items.WHITE_DYE),
            ModBlocks.GENTIAN, new ItemStack(Items.BLUE_DYE),
            ModBlocks.MAGENTA_SAXIFRAGE, new ItemStack(Items.MAGENTA_DYE),
            ModBlocks.YELLOW_SAXIFRAGE, new ItemStack(Items.YELLOW_DYE)
        ),

        configCondition("arctic_flowers"), Map.of(
            ModBlocks.HEATHER, new ItemStack(Items.PINK_DYE),
            ModBlocks.WHITE_DRYAD, new ItemStack(Items.WHITE_DYE),
            ModBlocks.FIREWEED, new ItemStack(Items.PINK_DYE, 2),
            ModBlocks.WHITE_RHODODENDRON, new ItemStack(Items.WHITE_DYE, 2)
        ),

        configCondition("forest_flowers"), Map.of(
            ModBlocks.FOXGLOVE, new ItemStack(Items.PINK_DYE),
            ModBlocks.WILD_GARLIC, new ItemStack(Items.LIGHT_GRAY_DYE),
            ModBlocks.PINK_PRIMROSE, new ItemStack(Items.PINK_DYE),
            ModBlocks.PURPLE_PRIMROSE, new ItemStack(Items.PURPLE_DYE),
            ModBlocks.YELLOW_PRIMROSE, new ItemStack(Items.YELLOW_DYE)
        ),

        configCondition("plains_flowers"), Map.of(
            ModBlocks.CHICORY, new ItemStack(Items.LIGHT_BLUE_DYE),
            ModBlocks.YARROW, new ItemStack(Items.LIGHT_GRAY_DYE),
            ModBlocks.DAFFODIL, new ItemStack(Items.YELLOW_DYE)
        ),

        configCondition("savanna_flowers"), Map.of(
            ModBlocks.MARIGOLD, new ItemStack(Items.ORANGE_DYE),
            ModBlocks.SNAPDRAGON, new ItemStack(Items.YELLOW_DYE),
            ModBlocks.BLACK_IRIS, new ItemStack(Items.BLACK_DYE),
            ModBlocks.BLUE_IRIS, new ItemStack(Items.BLUE_DYE),
            ModBlocks.PURPLE_IRIS, new ItemStack(Items.PURPLE_DYE),
            ModBlocks.OLEANDER, new ItemStack(Items.PINK_DYE, 2),
            ModBlocks.SAGE, new ItemStack(Items.PURPLE_DYE, 2)
        ),

        configCondition("swamp_flowers"), Map.of(
            ModBlocks.FORGET_ME_NOT, new ItemStack(Items.LIGHT_BLUE_DYE),
            ModBlocks.GLOBEFLOWER, new ItemStack(Items.ORANGE_DYE),
            ModBlocks.WATER_FLAG, new ItemStack(Items.YELLOW_DYE, 2)
        ),

        configCondition("sweet_peas"), Map.of(
            ModBlocks.WHITE_SWEET_PEAS, new ItemStack(Items.WHITE_DYE),
            ModBlocks.PINK_SWEET_PEAS, new ItemStack(Items.PINK_DYE),
            ModBlocks.RED_SWEET_PEAS, new ItemStack(Items.RED_DYE),
            ModBlocks.MAGENTA_SWEET_PEAS, new ItemStack(Items.MAGENTA_DYE),
            ModBlocks.PURPLE_SWEET_PEAS, new ItemStack(Items.PURPLE_DYE),
            ModBlocks.BLUE_SWEET_PEAS, new ItemStack(Items.BLUE_DYE),
            ModBlocks.LIGHT_BLUE_SWEET_PEAS, new ItemStack(Items.LIGHT_BLUE_DYE)
        ),

        configCondition("lotus"), Map.of(
            ModBlocks.PINK_LOTUS, new ItemStack(Items.PINK_DYE),
            ModBlocks.WHITE_LOTUS, new ItemStack(Items.WHITE_DYE),
            ModBlocks.YELLOW_LOTUS, new ItemStack(Items.YELLOW_DYE)
        ),

        configCondition("dandelion_puff"), Map.of(
            ModBlocks.DANDELION_PUFF, new ItemStack(Items.LIGHT_GRAY_DYE)
        ),

        configCondition("cactus_flowers"), Map.of(
            ModBlocks.CACTUS_FLOWER, new ItemStack(Items.PINK_DYE)
        )
    );
    public TIRecipeProvider(DataGenerator gen) {
        super(gen);
    }

    @Override
    protected void buildCraftingRecipes(@NotNull Consumer<FinishedRecipe> consumer) {
        ModWoodTypes.VALUES.forEach(woodType -> {
            var logs = ItemTags.create(TerraIncognita.prefix(woodType.name + "_logs"));

            var enabled = configCondition(woodType.name);

            conditionalRecipe(consumer, planks(logs, woodType.PLANKS), enabled, getItemName(woodType.PLANKS));
            conditionalRecipe(consumer, wood(woodType.LOG, woodType.WOOD), enabled, getItemName(woodType.WOOD));
            conditionalRecipe(consumer, wood(woodType.STRIPPED_LOG, woodType.STRIPPED_LOG), enabled, getItemName(woodType.STRIPPED_WOOD));

            conditionalRecipe(consumer, slab(woodType.SLAB, woodType.PLANKS).group("wooden_slab"), enabled, getItemName(woodType.SLAB));
            conditionalRecipe(consumer, stairs(woodType.STAIRS, woodType.PLANKS).group("wooden_stairs"), enabled, getItemName(woodType.STAIRS));

            conditionalRecipe(consumer, door(woodType.PLANKS, woodType.DOOR), enabled, getItemName(woodType.DOOR));
            conditionalRecipe(consumer, trapdoor(woodType.PLANKS, woodType.TRAPDOOR), enabled, getItemName(woodType.TRAPDOOR));
            conditionalRecipe(consumer, shapeless(woodType.PLANKS, woodType.BUTTON, 1).group("wooden_button"), enabled, getItemName(woodType.BUTTON));
            conditionalRecipe(consumer, pressurePlate(woodType.PLANKS, woodType.PRESSURE_PLATE).group("wooden_pressure_plate"), enabled, getItemName(woodType.PRESSURE_PLATE));
            conditionalRecipe(consumer, fence(woodType.PLANKS, woodType.FENCE), enabled, getItemName(woodType.FENCE));
            conditionalRecipe(consumer, fenceGate(woodType.PLANKS, woodType.FENCE_GATE), enabled, getItemName(woodType.FENCE_GATE));
            conditionalRecipe(consumer, sign(woodType.PLANKS, woodType.SIGN), enabled, getItemName(woodType.SIGN));
            conditionalRecipe(consumer, boat(woodType.PLANKS, woodType.BOAT), enabled, getItemName(woodType.BOAT));

            //Quark or Woodworks
            conditionalRecipe(consumer, chest(woodType.PLANKS, woodType.CHEST), and(enabled, CHESTS_ENABLED), getItemName(woodType.CHEST));
            conditionalRecipe(consumer, trappedChest(woodType.PLANKS, woodType.TRAPPED_CHEST), and(enabled, CHESTS_ENABLED), getItemName(woodType.TRAPPED_CHEST));
            conditionalRecipe(consumer, bookshelf(woodType.PLANKS, woodType.BOOKSHELF), and(enabled, BOOKSHELVES_ENABLED), getItemName(woodType.BOOKSHELF));
            conditionalRecipe(consumer, ladder(woodType.PLANKS, woodType.LADDER), and(enabled, LADDERS_ENABLED), getItemName(woodType.LADDER));

            //Quark
            conditionalRecipe(consumer, verticalPlanks(woodType.PLANKS, woodType.VERTICAL_PLANKS), and(enabled, QUARK_LOADED), getItemName(woodType.VERTICAL_PLANKS));
            conditionalRecipe(consumer, shapeless(woodType.VERTICAL_PLANKS, woodType.PLANKS, 1), and(enabled, QUARK_LOADED), getItemName(woodType.VERTICAL_PLANKS) + "_revert");
            conditionalRecipe(consumer, verticalSlabs(woodType.PLANKS, woodType.VERTICAL_SLAB), and(enabled, QUARK_LOADED), getItemName(woodType.VERTICAL_SLAB));
            conditionalRecipe(consumer, shapeless(woodType.VERTICAL_SLAB, woodType.SLAB, 1), and(enabled, QUARK_LOADED), getItemName(woodType.VERTICAL_SLAB) + "_revert");
            conditionalRecipe(consumer, leafCarpet(woodType.LEAVES, woodType.LEAF_CARPET), and(enabled, QUARK_LOADED), getItemName(woodType.LEAF_CARPET));
            conditionalRecipe(consumer, hedge(logs, woodType.LEAVES, woodType.HEDGE), and(enabled, QUARK_LOADED), getItemName(woodType.HEDGE));
            conditionalRecipe(consumer, post(woodType.WOOD, woodType.POST), and(enabled, QUARK_LOADED), getItemName(woodType.POST));
            conditionalRecipe(consumer, post(woodType.STRIPPED_WOOD, woodType.STRIPPED_POST), and(enabled, QUARK_LOADED), getItemName(woodType.STRIPPED_POST));

            //Woodworks
            conditionalRecipe(consumer, boards(woodType.PLANKS, woodType.BOARDS), and(enabled, WOODWORKS_LOADED), getItemName(woodType.BOARDS));
            conditionalRecipe(consumer, leavesToLeafPile(woodType.LEAVES, woodType.LEAF_PILE), and(enabled, WOODWORKS_LOADED), getItemName(woodType.LEAF_PILE));
            conditionalRecipe(consumer, leafPileToLeaves(woodType.LEAVES, woodType.LEAF_PILE), and(enabled, WOODWORKS_LOADED), getItemName(woodType.LEAVES) + "_from_leaf_pile");
            conditionalRecipe(consumer, beehive(woodType.PLANKS, woodType.BEEHIVE), and(enabled, WOODWORKS_LOADED), getItemName(woodType.BEEHIVE));

            //Farmer's Delight
            conditionalRecipe(consumer, cabinet(woodType.SLAB, woodType.TRAPDOOR, woodType.CABINET), and(enabled, FARMERS_DELIGHT_LOADED), getItemName(woodType.CABINET));
        });

        var apple = configCondition("apple");
        conditionalRecipe(consumer, leafCarpet(APPLE.BLOSSOMING_LEAVES, APPLE.BLOSSOMING_LEAF_CARPET), and(apple, QUARK_LOADED), "blossoming_apple_leaf_carpet");
        conditionalRecipe(consumer, hedge(ModItemTags.APPLE_LOGS, APPLE.BLOSSOMING_LEAVES, APPLE.HEDGE), and(apple, QUARK_LOADED), "blossoming_apple_hedge");
        conditionalRecipe(consumer, leavesToLeafPile(APPLE.BLOSSOMING_LEAVES, APPLE.LEAF_PILE), and(apple, WOODWORKS_LOADED), "blossoming_apple_leaf_pile");
        conditionalRecipe(consumer, leafPileToLeaves(APPLE.BLOSSOMING_LEAVES, APPLE.LEAF_PILE), and(apple, WOODWORKS_LOADED), "blossoming_apple_leaves_from_leaf_pile");

        DYE_RECIPES.forEach((condition, recipes) -> {
            recipes.forEach((flower, output) -> {
                dyeRecipe(consumer, flower, output.getItem(), output.getCount(), condition);
            });
        });
    }

    private RecipeConfigCondition configCondition(String id) {
        return new RecipeConfigCondition(TerraIncognita.MODID, id);
    }

    private void conditionalRecipe(Consumer<FinishedRecipe> consumer, RecipeBuilder recipe, ICondition condition, String id) {
        ConditionalRecipe.builder().addCondition(condition).addRecipe(recipe::save).generateAdvancement().build(consumer, TerraIncognita.prefix(id));
    }

    private void dyeRecipe(Consumer<FinishedRecipe> consumer, ItemLike flower, ItemLike dye, int amount, ICondition condition) {
        conditionalRecipe(consumer, shapeless(flower, dye, amount), condition, "dye_from_" + getItemName(flower));
    }

    private RecipeBuilder shapeless(ItemLike input, ItemLike output, int amount) {
        return ShapelessRecipeBuilder.shapeless(output, amount).requires(input).unlockedBy(getHasName(input), has(input));
    }


    private RecipeBuilder storageBlock(ItemLike input, ItemLike output) {
        return ShapedRecipeBuilder.shaped(output).define('#', input).pattern("###").pattern("###").pattern("###");
    }

    private RecipeBuilder planks(TagKey<Item> logs, ItemLike planks) {
        return ShapelessRecipeBuilder.shapeless(planks, 4).requires(logs).group("planks").unlockedBy("has_logs", has(logs));
    }

    private RecipeBuilder wood(ItemLike log, ItemLike wood) {
        return ShapedRecipeBuilder.shaped(wood, 3).define('#', log).pattern("##").pattern("##").group("bark").unlockedBy("has_log", has(log));
    }

    private RecipeBuilder slab(ItemLike material, ItemLike slab) {
        return slabBuilder(slab, Ingredient.of(material)).unlockedBy(getHasName(material), has(material));
    }

    private RecipeBuilder stairs(ItemLike material, ItemLike stairs) {
        return stairBuilder(stairs, Ingredient.of(material)).unlockedBy(getHasName(material), has(material));
    }

    private RecipeBuilder door(ItemLike material, ItemLike door) {
        return doorBuilder(door, Ingredient.of(material)).unlockedBy(getHasName(material), has(material)).group("wooden_door");
    }

    private RecipeBuilder trapdoor(ItemLike material, ItemLike trapdoor) {
        return trapdoorBuilder(trapdoor, Ingredient.of(material)).unlockedBy(getHasName(material), has(material)).group("wooden_trapdoor");
    }

    private RecipeBuilder pressurePlate(ItemLike material, ItemLike pressurePlate) {
        return pressurePlateBuilder(pressurePlate, Ingredient.of(material)).unlockedBy(getHasName(material), has(material));
    }

    private RecipeBuilder fence(ItemLike planks, ItemLike fence) {
        return fenceBuilder(fence, Ingredient.of(planks)).unlockedBy(getHasName(planks), has(planks)).group("wooden_fence");
    }

    private RecipeBuilder fenceGate(ItemLike planks, ItemLike fenceGate) {
        return fenceGateBuilder(fenceGate, Ingredient.of(planks)).unlockedBy(getHasName(planks), has(planks)).group("wooden_fence_gate");
    }

    private RecipeBuilder sign(ItemLike planks, ItemLike sign) {
        return signBuilder(sign, Ingredient.of(planks)).unlockedBy(getHasName(planks), has(planks)).group("wooden_sign");
    }

    private RecipeBuilder boat(ItemLike planks, ItemLike boat) {
        return ShapedRecipeBuilder.shaped(boat, 1).define('#', planks).pattern("# #").pattern("###").group("boat").unlockedBy("in_water", insideOf(Blocks.WATER));
    }

    private RecipeBuilder chest(ItemLike planks, ItemLike chest) {
        return ShapedRecipeBuilder.shaped(chest).define('#', planks).pattern("###").pattern("# #").pattern("###").group("wooden_chest").unlockedBy("has_lots_of_items", new InventoryChangeTrigger.TriggerInstance(EntityPredicate.Composite.ANY, MinMaxBounds.Ints.atLeast(10), MinMaxBounds.Ints.ANY, MinMaxBounds.Ints.ANY, new ItemPredicate[0]));
    }

    private RecipeBuilder trappedChest(ItemLike planks, ItemLike trappedChest) {
        return ShapelessRecipeBuilder.shapeless(trappedChest).requires(trappedChest).requires(Blocks.TRIPWIRE_HOOK).group("wooden_trapped_chest").unlockedBy("has_tripwire_hook", has(Blocks.TRIPWIRE_HOOK));
    }

    private RecipeBuilder bookshelf(ItemLike planks, ItemLike bookshelf) {
        return ShapedRecipeBuilder.shaped(bookshelf).define('#', planks).define('X', Items.BOOK).pattern("###").pattern("XXX").pattern("###").group("wooden_bookshelf").unlockedBy("has_book", has(Items.BOOK));
    }

    private RecipeBuilder ladder(ItemLike planks, ItemLike ladder) {
        return ShapedRecipeBuilder.shaped(ladder, 4).define('#', planks).define('S', Items.STICK).pattern("S S").pattern("S#S").pattern("S S").group("wooden_ladder").unlockedBy("has_stick", has(Items.STICK));
    }

    private RecipeBuilder leafCarpet(ItemLike leaves, ItemLike leafCarpet) {
        return new ShapedRecipeBuilder(leafCarpet, 3).define('#', leaves).pattern("##").group("leaf_carpet").unlockedBy(getHasName(leaves), has(leaves));
    }

    private RecipeBuilder verticalPlanks(ItemLike planks, ItemLike verticalPlanks) {
        return ShapedRecipeBuilder.shaped(verticalPlanks, 3).define('#',planks).pattern("#").pattern("#").pattern("#").group("vertical_planks").unlockedBy(getHasName(planks), has(planks));
    }

    private RecipeBuilder verticalSlabs(ItemLike material, ItemLike verticalSlab) {
        return ShapedRecipeBuilder.shaped(verticalSlab, 6).define('#', material).pattern("#").pattern("#").pattern("#").group("vertical_slabs").unlockedBy(getHasName(material), has(material));
    }

    private RecipeBuilder hedge(TagKey<Item> logs, ItemLike leaves, ItemLike hedge) {
        return new ShapedRecipeBuilder(hedge, 2).define('#', leaves).define('L', logs).pattern("#").pattern("L").group("hedge").unlockedBy(getHasName(leaves), has(leaves));
    }

    private RecipeBuilder post(ItemLike wood, ItemLike post) {
        return ShapedRecipeBuilder.shaped(post, 8).define('#', wood).pattern("#").pattern("#").pattern("#").group("wooden_post").unlockedBy(getHasName(wood), has(wood));
    }

    private RecipeBuilder beehive(ItemLike planks, ItemLike beehive) {
        return ShapedRecipeBuilder.shaped(beehive).define('#', planks).define('H', Items.HONEYCOMB).pattern("###").pattern("HHH").pattern("###").group("wooden_beehive").unlockedBy("has_honeycomb", has(Items.HONEYCOMB));
    }

    private RecipeBuilder boards(ItemLike planks, ItemLike boards) {
        return ShapedRecipeBuilder.shaped(boards, 3).define('#', planks).pattern("#").pattern("#").pattern("#").group("wooden_boards").unlockedBy(getHasName(planks), has(planks));
    }

    private RecipeBuilder leavesToLeafPile(ItemLike leaves, ItemLike leafPile) {
        return ShapelessRecipeBuilder.shapeless(leafPile, 4).requires(leaves).group("leaf_pile").unlockedBy(getHasName(leaves), has(leaves));
    }

    private RecipeBuilder leafPileToLeaves(ItemLike leaves, ItemLike leafPile) {
        return ShapedRecipeBuilder.shaped(leaves).define('#', leafPile).pattern("##").pattern("##").group("leaves").unlockedBy(getHasName(leafPile), has(leafPile));
    }

    private RecipeBuilder cabinet(ItemLike slab, ItemLike trapdoor, ItemLike cabinet) {
        return ShapedRecipeBuilder.shaped(cabinet).define('_', slab).define('D', trapdoor).pattern("___").pattern("D D").pattern("___").unlockedBy(getHasName(trapdoor), has(trapdoor));
    }
}
