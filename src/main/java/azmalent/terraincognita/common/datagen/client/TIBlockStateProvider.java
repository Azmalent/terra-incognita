package azmalent.terraincognita.common.datagen.client;

import azmalent.cuneiform.registry.BlockEntry;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.block.woodset.*;
import azmalent.terraincognita.common.block.woodset.TIVerticalSlabBlock.VerticalSlabType;
import azmalent.terraincognita.common.block.woodset.chest.TIChestBlock;
import azmalent.terraincognita.common.block.woodset.sign.TIStandingSignBlock;
import azmalent.terraincognita.common.block.woodset.sign.TIWallSignBlock;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModWoodTypes;
import azmalent.terraincognita.common.woodtype.TIWoodType;
import net.minecraft.core.Direction.Axis;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.minecraftforge.client.model.generators.MultiPartBlockStateBuilder;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import vectorwing.farmersdelight.common.block.CabinetBlock;

import java.util.List;
import java.util.Set;

import static azmalent.terraincognita.common.registry.ModBlocks.*;

@SuppressWarnings("DataFlowIssue")
public class TIBlockStateProvider extends BlockStateProvider {
    public TIBlockStateProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, TerraIncognita.MODID, exFileHelper);
    }

    @Override
    protected void registerStatesAndModels() {
        for (TIWoodType type : ModWoodTypes.VALUES) {
            woodType(type);
        }

        blockItem(ModWoodTypes.APPLE.BLOSSOMING_LEAVES.get());
        blockItem(ModWoodTypes.APPLE.BLOSSOMING_LEAF_CARPET.get());

        List<BlockEntry<?>> flowers = FLOWERS.stream().filter(f -> f != CACTUS_FLOWER).toList();
        basicFlowers(flowers, Set.of(FIREWEED, MAGENTA_SAXIFRAGE, YELLOW_SAXIFRAGE));

        crossBlock(CACTUS_FLOWER.get());
        blockItem(SMOOTH_CACTUS.get());

        simpleBlock(CARIBOU_MOSS.get(), models().getBuilder(name(CARIBOU_MOSS.get()))
            .parent(new UncheckedModelFile("block/coral_fan"))
            .texture("fan", blockTexture(CARIBOU_MOSS.get())));
        horizontalBlock(CARIBOU_MOSS_WALL.get(), models().getBuilder(name(CARIBOU_MOSS_WALL.get()))
            .parent(new UncheckedModelFile("block/coral_wall_fan"))
            .texture("fan", blockTexture(CARIBOU_MOSS.get())));
        generatedItem(CARIBOU_MOSS, "block");

        LOTUSES.forEach(lotus -> {
            simpleBlock(lotus.get(), models().getBuilder(name(lotus.get()))
                .parent(new UncheckedModelFile(TerraIncognita.prefix("block/lotus")))
                .texture("flower", suffix(blockTexture(lotus.get()), "_flower")));

            generatedItem(lotus, "block", name(lotus.get()) + "_flower");
        });

        storageBlock(HAZELNUT_SACK.get());
        storageBlock(SOUR_BERRY_SACK.get());

        basicBlock(PEAT.get());
        blockItem(TILLED_PEAT.get());

        basicBlock(MOSSY_GRAVEL.get());

        simpleBlock(CALTROPS.get(), models().crop("caltrops", blockTexture(CALTROPS.get())));
        generatedItem(CALTROPS, "item");

        simpleBlock(WICKER_MAT.get(), models().carpet("wicker_mat", TerraIncognita.prefix("block/wicker")));
    }

    private void woodType(TIWoodType type) {
        Block planks = type.PLANKS.get();

        crossBlock(type.SAPLING.get());
        flowerPot(type.SAPLING.get(), false);

        leaves(type.LEAVES.get(), type.LEAF_CARPET.get());
        logAndWood(type.LOG.get(), type.WOOD.get());
        logAndWood(type.STRIPPED_LOG.get(), type.STRIPPED_WOOD.get());
        post(type.LOG.get(), type.POST.get());
        post(type.STRIPPED_LOG.get(), type.STRIPPED_POST.get());
        hedge(type.LEAVES.get(), type.LOG.get(), type.HEDGE.get());

        planks(planks, type.VERTICAL_PLANKS.get());
        slabAndStairs(planks, type.SLAB.get(), type.VERTICAL_SLAB.get(), type.STAIRS.get());
        buttonAndPressurePlate(planks, type.BUTTON.get(), type.PRESSURE_PLATE.get());
        fenceAndGate(planks, type.FENCE.get(), type.FENCE_GATE.get());
        doorAndTrapdoor(type.DOOR.get(), type.TRAPDOOR.get());
        chest(planks, type.CHEST.get());
        chest(planks, type.TRAPPED_CHEST.get());
        sign(planks, type.SIGN.get(), type.WALL_SIGN.get());
        bookshelf(planks, type.BOOKSHELF.get());
        ladder(planks, type.LADDER.get());
        beehive(type.BEEHIVE.get());
        cabinet(type.CABINET.get());
    }

    private void basicFlowers(List<BlockEntry<?>> flowers, Set<BlockEntry<?>> specialPottedTextures) {
        for (BlockEntry<?> flower : flowers) {
            if (flower.get() instanceof DoublePlantBlock tallFlower) {
                tallPlantBlock(tallFlower);
            } else {
                crossBlock(flower.get());
            }

            flowerPot(flower.get(), specialPottedTextures.contains(flower));
        }
    }

    private String name(Block block) {
        return block.getRegistryName().getPath();
    }

    private ResourceLocation suffix(ResourceLocation location, String suffix) {
        return new ResourceLocation(location.getNamespace(), location.getPath() + suffix);
    }

    private void blockItem(Block block) {
        simpleBlockItem(block, new ModelFile.ExistingModelFile(blockTexture(block), this.models().existingFileHelper));
    }

    private void generatedItem(ItemLike item, String type) {
        generatedItem(item, type, ForgeRegistries.ITEMS.getKey(item.asItem()).getPath());
    }

    private void generatedItem(ItemLike item, String type, String texture) {
        itemModels().withExistingParent(ForgeRegistries.ITEMS.getKey(item.asItem()).getPath(), "item/generated").texture("layer0", TerraIncognita.prefix(type + "/" + texture));
    }

    private ModelFile particle(Block block, ResourceLocation texture) {
        return models().getBuilder(name(block)).texture("particle", texture);
    }

    private ModelFile verticalSlab(TIVerticalSlabBlock slab, ResourceLocation texture) {
        return models().getBuilder(name(slab))
            .parent(new UncheckedModelFile(TerraIncognita.prefix("block/vertical_slab")))
            .texture("side", texture).texture("bottom", texture).texture("top", texture);
    }

    private void verticalSlabBlock(TIVerticalSlabBlock slab, String baseId, ResourceLocation texture) {
        ModelFile model = verticalSlab(slab, texture);

        getVariantBuilder(slab)
            .partialState().with(TIVerticalSlabBlock.TYPE, VerticalSlabType.NORTH)
                .addModels(new ConfiguredModel(model, 0, 0, true))
            .partialState().with(TIVerticalSlabBlock.TYPE, VerticalSlabType.SOUTH)
                .addModels(new ConfiguredModel(model, 0, 180, true))
            .partialState().with(TIVerticalSlabBlock.TYPE, VerticalSlabType.EAST)
                .addModels(new ConfiguredModel(model, 0, 90, true))
            .partialState().with(TIVerticalSlabBlock.TYPE, VerticalSlabType.WEST)
                .addModels(new ConfiguredModel(model, 0, 270, true))
            .partialState().with(TIVerticalSlabBlock.TYPE, VerticalSlabType.DOUBLE)
                .addModels(new ConfiguredModel(models().cubeAll(baseId, texture)));
    }

    private void basicBlock(Block block) {
        simpleBlock(block);
        blockItem(block);
    }

    private void storageBlock(Block block) {
        simpleBlock(block, models().cubeBottomTop(name(block), blockTexture(block), suffix(blockTexture(block), "_bottom"), suffix(blockTexture(block), "_top")));
        blockItem(block);
    }

    private void crossBlock(Block block) {
        simpleBlock(block, models().cross(name(block), blockTexture(block)));
        generatedItem(block, "block");
    }

    private void tallPlantBlock(DoublePlantBlock plant) {
        ModelFile top = models().cross(name(plant) + "_top", suffix(blockTexture(plant), "_top"));
        ModelFile bottom = models().cross(name(plant) + "_bottom", suffix(blockTexture(plant), "_bottom"));

        getVariantBuilder(plant)
            .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.UPPER).addModels(new ConfiguredModel(top))
            .partialState().with(DoublePlantBlock.HALF, DoubleBlockHalf.LOWER).addModels(new ConfiguredModel(bottom));

        generatedItem(plant, "block", name(plant) + "_top");
    }

    private void flowerPot(Block plant, boolean specialTexture) {
        Block potted = ForgeRegistries.BLOCKS.getValue(TerraIncognita.prefix("potted_" + name(plant)));

        ResourceLocation texture;
        if (specialTexture) {
            texture = blockTexture(potted);
        } else if (plant instanceof DoublePlantBlock) {
            texture = suffix(blockTexture(plant), "_top");
        } else {
            texture = blockTexture(plant);
        }

        simpleBlock(potted, models().singleTexture(name(potted), new ResourceLocation("block/flower_pot_cross"), "plant", texture));
    }

    private void leaves(LeavesBlock leaves, TILeafCarpetBlock leafCarpet) {
        simpleBlock(leaves); //TODO: use base leaf model as parent (1.19)

        simpleBlock(leafCarpet, models().getBuilder(name(leafCarpet))
            .parent(new UncheckedModelFile(TerraIncognita.prefix("block/leaf_carpet")))
            .texture("all", blockTexture(leaves)));
        blockItem(leafCarpet);
    }

    private void logAndWood(RotatedPillarBlock log, RotatedPillarBlock wood) {
        axisBlock(log, blockTexture(log), suffix(blockTexture(log), "_top"));
        blockItem(log);

        axisBlock(wood, blockTexture(log), blockTexture(log));
        blockItem(wood);
    }

    private static final BooleanProperty[] CHAINED = new BooleanProperty[]{
        BooleanProperty.create("chain_down"), BooleanProperty.create("chain_up"),
        BooleanProperty.create("chain_north"), BooleanProperty.create("chain_south"), BooleanProperty.create("chain_west"),
        BooleanProperty.create("chain_east")
    };

    private void post(RotatedPillarBlock log, TIWoodPostBlock post) {
        ModelFile model = models().getBuilder(name(post)).parent(new UncheckedModelFile(TerraIncognita.prefix("block/post"))).texture("texture", blockTexture(log));
        ModelFile chainSmall = new UncheckedModelFile(TerraIncognita.prefix("block/chain_small"));
        ModelFile chainSmallTop = new UncheckedModelFile(TerraIncognita.prefix("block/chain_small_top"));
        getMultipartBuilder(post)
            .part().modelFile(model).addModel().condition(RotatedPillarBlock.AXIS, Axis.Y).end()
            .part().modelFile(model).rotationX(90).addModel().condition(RotatedPillarBlock.AXIS, Axis.Z).end()
            .part().modelFile(model).rotationX(90).rotationY(90).addModel().condition(RotatedPillarBlock.AXIS, Axis.X).end()
            .part().modelFile(chainSmall).addModel().condition(CHAINED[0], true).end()
            .part().modelFile(chainSmallTop).addModel().condition(CHAINED[1], true).end()
            .part().modelFile(chainSmallTop).rotationX(90).addModel().condition(CHAINED[2], true).end()
            .part().modelFile(chainSmall).rotationX(90).addModel().condition(CHAINED[3], true).end()
            .part().modelFile(chainSmall).rotationX(90).rotationY(90).addModel().condition(CHAINED[4], true).end()
            .part().modelFile(chainSmallTop).rotationX(90).rotationY(90).addModel().condition(CHAINED[5], true).end();

        blockItem(post);
    }

    private ModelFile hedgePost(String name, ResourceLocation log, ResourceLocation leaf) {
        return models().getBuilder(name).parent(new UncheckedModelFile(TerraIncognita.prefix("block/hedge_post"))).texture("log", log).texture("leaf", leaf);
    }

    private ModelFile hedgeSide(String name, ResourceLocation leaf) {
        return models().getBuilder(name).parent(new UncheckedModelFile(TerraIncognita.prefix("block/hedge_side"))).texture("leaf", leaf);
    }

    private ModelFile hedgeExtend(String name, ResourceLocation leaf) {
        return models().getBuilder(name).parent(new UncheckedModelFile(TerraIncognita.prefix("block/hedge_extend"))).texture("leaf", leaf);
    }

    private void hedge(LeavesBlock leaves, RotatedPillarBlock log, TIHedgeBlock hedge) {
        ModelFile post = this.hedgePost(name(hedge) + "_post", blockTexture(log), blockTexture(leaves));
        ModelFile side = this.hedgeSide(name(hedge) + "_side", blockTexture(leaves));
        ModelFile extend = this.hedgeExtend(name(hedge) + "_extend", blockTexture(leaves));

        MultiPartBlockStateBuilder builder = getMultipartBuilder(hedge);
        builder.part().modelFile(post).addModel().condition(BooleanProperty.create("extend"), false).end().part().modelFile(extend).addModel().condition(BooleanProperty.create("extend"), true);
        PipeBlock.PROPERTY_BY_DIRECTION.forEach((dir, value) -> {
            if (dir.getAxis().isHorizontal()) {
                builder.part().modelFile(side).rotationY((((int) dir.toYRot()) + 180) % 360).uvLock(
                    true).addModel().condition(value, true);
            }
        });

        itemModels().getBuilder(name(hedge)).parent(post);
    }

    private void planks(Block planks, Block verticalPlanks) {
        simpleBlock(planks);

        ModelFile model = new UncheckedModelFile(TerraIncognita.prefix("block/vertical_planks"));
        simpleBlock(verticalPlanks, models().getBuilder(name(verticalPlanks)).parent(model).texture("all", blockTexture(planks)));
        blockItem(verticalPlanks);
    }

    private void slabAndStairs(Block base, SlabBlock slab, TIVerticalSlabBlock verticalSlab, StairBlock stairs) {
        ResourceLocation texture = blockTexture(base);

        slabBlock(slab, texture, texture);
        blockItem(slab);

        verticalSlabBlock(verticalSlab, name(base), texture);
        blockItem(verticalSlab);

        stairsBlock(stairs, texture);
        blockItem(stairs);
    }

    private void buttonAndPressurePlate(Block base, ButtonBlock button, PressurePlateBlock pressurePlate) {
        ResourceLocation texture = blockTexture(base);

        buttonBlock(button, texture);

        ModelFile buttonInventoryModel = models().withExistingParent(name(button) + "_inventory", "block/button_inventory").texture("texture", texture);
        itemModels().getBuilder(name(button)).parent(buttonInventoryModel);

        pressurePlateBlock(pressurePlate, texture);
        blockItem(pressurePlate);
    }

    private void fenceAndGate(Block base, FenceBlock fence, FenceGateBlock fenceGate) {
        fenceBlock(fence, blockTexture(base));
        itemModels().getBuilder(name(fence)).parent(this.models().fenceInventory(name(fence) + "_inventory", blockTexture(base)));

        fenceGateBlock(fenceGate, blockTexture(base));
        blockItem(fenceGate);
    }

    private void doorAndTrapdoor(DoorBlock door, TrapDoorBlock trapdoor) {
        doorBlock(door, suffix(blockTexture(door), "_bottom"), suffix(blockTexture(door), "_top"));
        generatedItem(door, "item");

        trapdoorBlock(trapdoor, blockTexture(trapdoor), true);
        itemModels().getBuilder(name(trapdoor)).parent(this.models().trapdoorOrientableBottom(name(trapdoor) + "_bottom", blockTexture(trapdoor)));
    }

    private void chest(Block planks, TIChestBlock chest) {
        simpleBlock(chest, particle(chest, blockTexture(planks)));
        simpleBlockItem(chest, new UncheckedModelFile(TerraIncognita.prefix("item/chest")));
    }

    private void sign(Block base, TIStandingSignBlock sign, TIWallSignBlock wallSign) {
        simpleBlock(sign, particle(sign, blockTexture(base)));
        simpleBlock(wallSign, particle(wallSign, blockTexture(base)));
        generatedItem(sign, "item");
    }

    private void bookshelf(Block planks, TIBookshelfBlock bookshelf) {
        simpleBlock(bookshelf, models().cubeColumn(name(bookshelf), blockTexture(bookshelf), blockTexture(planks)));
        blockItem(bookshelf);
    }

    private void ladder(Block planks, LadderBlock ladder) {
        horizontalBlock(ladder, models().withExistingParent(name(ladder), "block/ladder").texture("particle", blockTexture(ladder)).texture("texture", blockTexture(ladder)));
        generatedItem(ladder, "block");
    }

    private void beehive(TIBeehiveBlock hive) {
        ModelFile beehive = models().orientableWithBottom(name(hive), suffix(blockTexture(hive), "_side"), suffix(blockTexture(hive), "_front"), suffix(blockTexture(hive), "_end"), suffix(blockTexture(hive), "_end")).texture("particle", suffix(blockTexture(hive), "_side"));
        ModelFile beehiveHoney = models().orientableWithBottom(name(hive) + "_honey", suffix(blockTexture(hive), "_side"), suffix(blockTexture(hive), "_front_honey"), suffix(blockTexture(hive), "_end"), suffix(blockTexture(hive), "_end")).texture("particle", suffix(blockTexture(hive), "_side"));
        horizontalBlock(hive, (state -> state.getValue(BlockStateProperties.LEVEL_HONEY) == 5 ? beehiveHoney : beehive));
        blockItem(hive);
    }

    private void cabinet(Block cabinet) {
        ModelFile cabinetModel = models().orientable(name(cabinet), suffix(blockTexture(cabinet), "_side"), suffix(blockTexture(cabinet), "_front"), suffix(blockTexture(cabinet), "_top"));
        ModelFile cabinetOpenModel = models().orientable(name(cabinet) + "_open", suffix(blockTexture(cabinet), "_side"), suffix(blockTexture(cabinet), "_front_open"), suffix(blockTexture(cabinet), "_top"));

        getVariantBuilder(cabinet)
            .forAllStates(state -> ConfiguredModel.builder()
                .modelFile(state.getValue(CabinetBlock.OPEN) ? cabinetOpenModel : cabinetModel)
                .rotationY(((int) state.getValue(BlockStateProperties.HORIZONTAL_FACING).toYRot() + 180) % 360)
                .build()
            );

        blockItem(cabinet);
    }
}
