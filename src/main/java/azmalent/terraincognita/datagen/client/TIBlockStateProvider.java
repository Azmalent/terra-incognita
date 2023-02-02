package azmalent.terraincognita.datagen.client;

import azmalent.cuneiform.registry.BlockEntry;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.block.plant.HangingMossBlock;
import azmalent.terraincognita.common.block.plant.SmallLilyPadBlock;
import azmalent.terraincognita.common.block.plant.SourBerryBushBlock;
import azmalent.terraincognita.common.block.plant.SwampReedsBlock;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModWoodTypes;
import azmalent.terraincognita.common.woodtype.TIWoodType;
import com.google.common.collect.Sets;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.DoublePlantBlock;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;

@SuppressWarnings("ConstantConditions")
public class TIBlockStateProvider extends ExtendedBlockStateProvider {
    public TIBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, TerraIncognita.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        ModWoodTypes.VALUES.forEach(type -> {
            registerWoodType(type);
            registerSmallPlant(type.SAPLING, false);
        });

        registerFlowers();
        registerMiscBlocks();
    }

    private void registerWoodType(TIWoodType type) {
        registerLogAndWood(type.LOG, type.WOOD);
        registerLogAndWood(type.STRIPPED_LOG, type.STRIPPED_WOOD);
        registerLeaves(type.LEAVES, type.LEAF_CARPET, type.LEAF_PILE);

        registerPlanks(type.PLANKS, type.VERTICAL_PLANKS, type.BOARDS);
        registerSlabAndStairs(type.PLANKS, type.SLAB, type.VERTICAL_SLAB, type.STAIRS);
        registerFenceAndFenceGate(type.PLANKS, type.FENCE, type.FENCE_GATE);
        registerButtonAndPressurePlate(type.PLANKS, type.BUTTON, type.PRESSURE_PLATE);
        registerDoorAndTrapdoor(type.DOOR, type.TRAPDOOR);
        registerChests(type.PLANKS, type.CHEST, type.TRAPPED_CHEST);
        registerSigns(type.PLANKS, type.SIGN, type.WALL_SIGN);
        registerBookshelf(type.PLANKS, type.BOOKSHELF);
        registerLadder(type.LADDER);
        registerBeehive(type.BEEHIVE);

        registerHedge(type.LOG, type.LEAVES, type.HEDGE);
        registerPost(type.LOG, type.POST);
        registerPost(type.STRIPPED_LOG, type.STRIPPED_POST);

        registerCabinet(type.CABINET);
    }

    @SuppressWarnings("unchecked")
    private void registerFlowers() {
        var flowersWithUniquePottedTextures = Sets.newHashSet(
            ModBlocks.FIREWEED, ModBlocks.MAGENTA_SAXIFRAGE, ModBlocks.YELLOW_SAXIFRAGE
        );

        ModBlocks.SMALL_FLOWERS.forEach(flower -> {
            registerSmallPlant(flower, flowersWithUniquePottedTextures.contains(flower));
        });

        ModBlocks.TALL_FLOWERS.forEach(flower -> {
            registerTallPlant(flower, flowersWithUniquePottedTextures.contains(flower));
        });
    }

    private void registerMiscBlocks() {
        for (var sweetPea : ModBlocks.SWEET_PEAS) {
            String path = sweetPea.get().getRegistryName().getPath();
            String color = path.substring(0, path.indexOf("_sweet_peas"));

            itemModels().withExistingParent(path, "item/generated")
                .texture("layer0", TerraIncognita.prefix("block/sweet_peas/" + color));
        }

        for (var lotus : ModBlocks.LOTUSES) {
            var name = lotus.get().getRegistryName().getPath();
            itemModels().withExistingParent(name, "item/generated")
                .texture("layer0", TerraIncognita.prefix("block/" + name + "_flower"));
        }

        //Small lily pads
        getVariantBuilder(ModBlocks.SMALL_LILY_PAD.get()).forAllStates(state -> {
            int lilyPads = state.getValue(SmallLilyPadBlock.LILY_PADS);
            var model = lilyPad("small_lilypad_" + lilyPads);
            return ConfiguredModel.builder().modelFile(model).build();
        });

        itemModels().withExistingParent("small_lilypad", "item/generated")
            .texture("layer0", TerraIncognita.prefix("block/small_lilypad_1"));

        //Hanging moss
        getVariantBuilder(ModBlocks.HANGING_MOSS.get()).forAllStates(state -> {
            var variant = state.getValue(HangingMossBlock.VARIANT);
            var texture = TerraIncognita.prefix("block/hanging_moss_" + variant);
            var model = models().cross("hanging_moss_" + variant, texture);
            return ConfiguredModel.builder().modelFile(model).build();
        });

        flatBlockItem(ModBlocks.HANGING_MOSS, "item");

        //Reeds
        getVariantBuilder(ModBlocks.SWAMP_REEDS.get()).forAllStatesExcept(state -> {
            var variant = state.getValue(SwampReedsBlock.VARIANT);
            var texture = TerraIncognita.prefix("block/swamp_reeds_" + variant);
            var model = models().cross("swamp_reeds_" + variant, texture);
            return ConfiguredModel.builder().modelFile(model).build();
        }, BlockStateProperties.WATERLOGGED);

        flatBlockItem(ModBlocks.SWAMP_REEDS, "item");
        registerPottedPlant(ModBlocks.FLOWER_POTS.get("swamp_reeds"), "potted/swamp_reeds");

        axisBlock(ModBlocks.SWAMP_REEDS_BUNDLE.get(), TerraIncognita.prefix("block/swamp_reeds_bundle"), TerraIncognita.prefix("block/swamp_reeds_bundle_top"));
        blockItem(ModBlocks.SWAMP_REEDS_BUNDLE.get());

        //Sour berries
        getVariantBuilder(ModBlocks.SOUR_BERRY_BUSH.get()).forAllStates(state -> {
            int age = state.getValue(SourBerryBushBlock.AGE);
            var model = lilyPad("sour_berry_bush_stage" + age);
            return ConfiguredModel.builder().modelFile(model).build();
        });

        simpleBlock(ModBlocks.SOUR_BERRY_SPROUTS.get(), lilyPad("sour_berry_sprouts"));
        flatBlockItem(ModBlocks.SOUR_BERRY_SPROUTS, "block");

        registerStorageBlock(ModBlocks.SOUR_BERRY_SACK);

        //Misc blocks
        registerBlock(ModBlocks.MOSSY_GRAVEL);
        registerBlock(ModBlocks.PEAT);
        registerCrossBlock(ModBlocks.CACTUS_FLOWER);

        registerBlock(ModBlocks.SMOOTH_CACTUS, models().withExistingParent(blockName(ModBlocks.SMOOTH_CACTUS), "block/cactus")
            .texture("top", TerraIncognita.prefix("block/smooth_cactus_top"))
            .texture("side", TerraIncognita.prefix("block/smooth_cactus_side"))
            .texture("particle", "#side"));

        registerBlock(ModBlocks.WICKER_MAT, models().carpet(blockName(ModBlocks.WICKER_MAT), TerraIncognita.prefix("block/wicker")));

        registerStorageBlock(ModBlocks.HAZELNUT_SACK);

        //Item models for blocks we don't generate here
        simpleBlockItem(ModWoodTypes.APPLE.BLOSSOMING_HEDGE.get(), new UncheckedModelFile(TerraIncognita.prefix("block/blossoming_apple_hedge_post")));
        blockItem(ModWoodTypes.APPLE.BLOSSOMING_LEAVES.get());
        blockItem(ModWoodTypes.APPLE.BLOSSOMING_LEAF_CARPET.get());
        blockItem(ModBlocks.TILLED_PEAT);

        flatBlockItem(ModBlocks.BASKET, "item");
        flatBlockItem(ModBlocks.CALTROPS, "item");
        flatBlockItem(ModBlocks.CARIBOU_MOSS, "block");
        flatBlockItem(ModBlocks.WICKER_LANTERN, "item");
    }

    private ModelFile lilyPad(String name) {
        var texture = TerraIncognita.prefix("block/" + name);
        return models().withExistingParent(name, "block/lily_pad")
            .texture("texture", texture).texture("particle", "#texture");
    }

    private void registerBlock(BlockEntry<?> block) {
        simpleBlock(block.get());
        blockItem(block);
    }

    private void registerBlock(BlockEntry<?> block, ModelFile model) {
        simpleBlock(block.get(), model);
        blockItem(block);
    }

    private void registerStorageBlock(BlockEntry<?> block) {
        var side = blockTexture(block);
        var bottom = suffix(side, "_bottom");
        var top = suffix(side, "_top");
        registerBlock(block, models().cubeBottomTop(side.getPath(), side, bottom, top));
    }

    private void registerCrossBlock(BlockEntry<?> block) {
        simpleBlock(block.get(), models().cross(blockName(block), blockTexture(block)));
        flatBlockItem(block, "block");
    }

    private void registerSmallPlant(BlockEntry<?> plant, boolean uniquePottedTexture) {
        registerCrossBlock(plant);

        var plantName = plant.get().getRegistryName().getPath();
        var texturePath = (uniquePottedTexture ? "potted/" : "") + plantName;
        registerPottedPlant(ModBlocks.FLOWER_POTS.get(plantName), texturePath);
    }

    private void registerTallPlant(BlockEntry<?> plant, boolean uniquePottedTexture) {
        var bottomTexture = suffix(blockTexture(plant), "_bottom");
        var bottom = models().cross(blockName(plant) + "_bottom", bottomTexture);

        var topTexture = suffix(blockTexture(plant), "_top");
        var top = models().cross(blockName(plant) + "_top", topTexture);

        getVariantBuilder(plant.get())
            .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER).addModels(new ConfiguredModel(bottom))
            .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER).addModels(new ConfiguredModel(top));

        var plantName = plant.get().getRegistryName().getPath();
        var texturePath = uniquePottedTexture ? "potted/" + plantName : plantName + "_top";
        registerPottedPlant(ModBlocks.FLOWER_POTS.get(plantName), texturePath);

        itemModels().withExistingParent(blockName(plant), "item/generated").texture("layer0", topTexture);
    }

    private void registerPottedPlant(BlockEntry<?> pottedPlant, String texturePath) {
        var parent = new ResourceLocation("block/flower_pot_cross");
        var texture = TerraIncognita.prefix("block/" + texturePath);
        simpleBlock(pottedPlant.get(), models().singleTexture(blockName(pottedPlant), parent, "plant", texture));
    }
}