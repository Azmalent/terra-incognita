package azmalent.terraincognita.datagen.client;

import azmalent.cuneiform.registry.BlockEntry;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.block.woodset.*;
import azmalent.terraincognita.common.block.woodset.TIVerticalSlabBlock.VerticalSlabType;
import azmalent.terraincognita.common.block.woodset.chest.TIChestBlock;
import azmalent.terraincognita.common.block.woodset.chest.TITrappedChestBlock;
import azmalent.terraincognita.common.block.woodset.sign.TIStandingSignBlock;
import azmalent.terraincognita.common.block.woodset.sign.TIWallSignBlock;
import azmalent.terraincognita.common.registry.ModWoodTypes;
import azmalent.terraincognita.common.woodtype.TIWoodType;
import azmalent.terraincognita.integration.ModIntegration;
import net.minecraft.core.Direction.Axis;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import net.minecraft.world.level.block.state.properties.BooleanProperty;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockStateProvider;
import net.minecraftforge.client.model.generators.ConfiguredModel;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.client.model.generators.ModelFile.ExistingModelFile;
import net.minecraftforge.client.model.generators.ModelFile.UncheckedModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import vectorwing.farmersdelight.common.block.CabinetBlock;

import static azmalent.terraincognita.common.block.woodset.TIWoodPostBlock.CHAINED;

public abstract class ExtendedBlockStateProvider extends BlockStateProvider {
    public ExtendedBlockStateProvider(DataGenerator gen, String modid, ExistingFileHelper exFileHelper) {
        super(gen, modid, exFileHelper);
    }

    protected void simpleBlock(Block block, ResourceLocation modelName) {
        var model = new UncheckedModelFile(modelName);
        simpleBlock(block, model);
    }

    @SuppressWarnings("ConstantConditions")
    protected String blockName(BlockEntry<?> block) {
        return block.get().getRegistryName().getPath();
    }

    protected ResourceLocation blockTexture(BlockEntry<?> block) {
        return blockTexture(block.get());
    }

    protected BlockModelBuilder blockModel(BlockEntry<?> block) {
        String name = blockName(block);
        return models().getBuilder(name);
    }

    protected BlockModelBuilder blockModel(BlockEntry<?> block, ResourceLocation modelPath) {
        return blockModel(block).parent(new UncheckedModelFile(modelPath));
    }

    protected ResourceLocation suffix(ResourceLocation id, String suffix) {
        return new ResourceLocation(id.getNamespace(), id.getPath() + suffix);
    }

    protected void registerLogAndWood(BlockEntry<RotatedPillarBlock> log, BlockEntry<RotatedPillarBlock> wood) {
        var texture = blockTexture(log);

        logBlock(log.get());
        blockItem(log);

        axisBlock(wood.get(), texture, texture);
        blockItem(wood);
    }

    protected void registerLeaves(BlockEntry<LeavesBlock> leaves, BlockEntry<TILeafCarpetBlock> leafCarpet) {
        var texture = blockTexture(leaves.get());
        var leavesModel = blockModel(leaves, new ResourceLocation("block/leaves")).texture("all", texture);
        simpleBlock(leaves.get(), leavesModel);
        blockItem(leaves);

        var leafCarpetModel = blockModel(leafCarpet, TerraIncognita.prefix("block/leaf_carpet"));
        simpleBlock(leafCarpet.get(), leafCarpetModel.texture("all", blockTexture(leaves)));
        blockItem(leafCarpet);
    }

    protected void registerHedge(BlockEntry<RotatedPillarBlock> log, BlockEntry<LeavesBlock> leaves, BlockEntry<TIHedgeBlock> hedge) {
        var leavesTexture = blockTexture(leaves);

        var post = blockModel(hedge, TerraIncognita.prefix("block/hedge_post"))
            .texture("log", blockTexture(log))
            .texture("leaf", leavesTexture);

        var side = blockModel(hedge, TerraIncognita.prefix("block/hedge_side"))
            .texture("leaf", leavesTexture);

        var extend = blockModel(hedge, TerraIncognita.prefix("block/hedge_extend"))
            .texture("leaf", leavesTexture);

        var builder = getMultipartBuilder(hedge.get());
        builder.part().modelFile(post).addModel().condition(BooleanProperty.create("extend"), false).end()
            .part().modelFile(extend).addModel().condition(BooleanProperty.create("extend"), true);

        PipeBlock.PROPERTY_BY_DIRECTION.forEach((dir, value) -> {
            if (dir.getAxis().isHorizontal()) {
                builder.part().modelFile(side).rotationY((((int) dir.toYRot()) + 180) % 360)
                    .uvLock(true).addModel().condition(value, true);
            }
        });

        this.itemModels().getBuilder(blockName(hedge)).parent(post);
    }

    protected void registerPost(BlockEntry<RotatedPillarBlock> log, BlockEntry<TIWoodPostBlock> post) {
        ModelFile model = blockModel(post, TerraIncognita.prefix("block/post")).texture("texture", blockTexture(log));
        ModelFile chainSmall = new UncheckedModelFile(TerraIncognita.prefix("block/chain_small"));
        ModelFile chainSmallTop = new UncheckedModelFile(TerraIncognita.prefix("block/chain_small_top"));

        getMultipartBuilder(post.get())
            .part().modelFile(model).addModel().condition(RotatedPillarBlock.AXIS, Axis.Y).end()
            .part().modelFile(model).rotationX(90).addModel().condition(RotatedPillarBlock.AXIS, Axis.Z).end()
            .part().modelFile(model).rotationX(90).rotationY(90).addModel().condition(RotatedPillarBlock.AXIS, Axis.X).end()
            .part().modelFile(chainSmall).addModel().condition(CHAINED[0], true).end()
            .part().modelFile(chainSmallTop).addModel().condition(CHAINED[1], true).end()
            .part().modelFile(chainSmallTop).rotationX(90).addModel().condition(CHAINED[2], true).end()
            .part().modelFile(chainSmall).rotationX(90).addModel().condition(CHAINED[3], true).end()
            .part().modelFile(chainSmall).rotationX(90).rotationY(90).addModel().condition(CHAINED[4], true).end()
            .part().modelFile(chainSmallTop).rotationX(90).rotationY(90).addModel().condition(CHAINED[5], true).end();

        this.blockItem(post);
    }

    protected void registerPlanks(BlockEntry<Block> planks, BlockEntry<Block> verticalPlanks) {
        var texture = blockTexture(planks.get());

        simpleBlock(planks.get());
        blockItem(planks);

        String name = blockName(verticalPlanks);
        var model = new UncheckedModelFile(TerraIncognita.prefix("block/vertical_planks"));
        simpleBlock(verticalPlanks.get(), models().getBuilder(name).parent(model).texture("all", texture));
        blockItem(verticalPlanks);
    }

    protected void registerFenceAndFenceGate(BlockEntry<Block> baseBlock, BlockEntry<FenceBlock> fence, BlockEntry<FenceGateBlock> fenceGate) {
        var texture = blockTexture(baseBlock.get());

        fenceBlock(fence.get(), texture);
        itemModels().getBuilder(blockName(fence)).parent(models().fenceInventory(blockName(fence) + "_inventory", texture));

        fenceGateBlock(fenceGate.get(), texture);
        blockItem(fenceGate);
    }

    protected void registerSlabAndStairs(BlockEntry<Block> baseBlock, BlockEntry<SlabBlock> slab, BlockEntry<TIVerticalSlabBlock> verticalSlab, BlockEntry<StairBlock> stairs) {
        var baseBlockName = blockName(baseBlock);
        var texture = blockTexture(baseBlock.get());

        //Slab
        slabBlock(slab.get(), texture, texture);
        blockItem(slab);

        //Vertical slab
        var name = blockName(verticalSlab);
        var baseModel = new UncheckedModelFile(TerraIncognita.prefix("block/vertical_slab"));
        models().getBuilder(name).parent(baseModel)
            .texture("side", texture)
            .texture("bottom", texture)
            .texture("top", texture);

        var model = new UncheckedModelFile(TerraIncognita.prefix("block/" + blockName(verticalSlab)));
        getVariantBuilder(verticalSlab.get())
            .partialState().with(TIVerticalSlabBlock.TYPE, VerticalSlabType.NORTH).addModels(new ConfiguredModel(model, 0, 0, true))
            .partialState().with(TIVerticalSlabBlock.TYPE, VerticalSlabType.SOUTH).addModels(new ConfiguredModel(model, 0, 180, true))
            .partialState().with(TIVerticalSlabBlock.TYPE, VerticalSlabType.EAST).addModels(new ConfiguredModel(model, 0, 90, true))
            .partialState().with(TIVerticalSlabBlock.TYPE, VerticalSlabType.WEST).addModels(new ConfiguredModel(model, 0, 270, true))
            .partialState().with(TIVerticalSlabBlock.TYPE, VerticalSlabType.DOUBLE).addModels(new ConfiguredModel(models().cubeAll(baseBlockName, texture)));

        blockItem(verticalSlab);

        //Stairs
        stairsBlock(stairs.get(), texture);
        blockItem(stairs);
    }

    protected void registerButtonAndPressurePlate(BlockEntry<Block> baseBlock, BlockEntry<? extends ButtonBlock> button, BlockEntry<PressurePlateBlock> pressurePlate) {
        var texture = blockTexture(baseBlock.get());

        buttonBlock(button.get(), texture);
        blockItem(button);

        pressurePlateBlock(pressurePlate.get(), texture);
        blockItem(pressurePlate);
    }

    protected void registerDoorAndTrapdoor(BlockEntry<DoorBlock> door, BlockEntry<TrapDoorBlock> trapdoor) {
        var doorTop = suffix(blockTexture(door), "_top");
        var doorBottom = suffix(blockTexture(door), "_bottom");
        doorBlock(door.get(), doorBottom, doorTop);
        flatBlockItem(door, "item");

        var name = blockName(trapdoor);
        trapdoorBlock(trapdoor.get(), blockTexture(trapdoor), true);
        itemModels().getBuilder(name).parent(models().trapdoorOrientableBottom(name + "_bottom", blockTexture(trapdoor)));
    }

    protected void registerChests(BlockEntry<Block> baseBlock, BlockEntry<TIChestBlock> chest, BlockEntry<TITrappedChestBlock> trappedChest) {
        var texture = blockTexture(baseBlock);

        simpleBlock(chest.get(), blockModel(chest).texture("particle", texture));
        simpleBlockItem(chest.get(), new UncheckedModelFile(TerraIncognita.prefix( "item/chest")));

        simpleBlock(trappedChest.get(), blockModel(chest).texture("particle", texture));
        simpleBlockItem(trappedChest.get(), new UncheckedModelFile(TerraIncognita.prefix("item/chest")));
    }

    protected void registerSigns(BlockEntry<Block> baseBlock, BlockEntry<TIStandingSignBlock> sign, BlockEntry<TIWallSignBlock> wallSign) {
        var texture = blockTexture(baseBlock);

        simpleBlock(sign.get(), blockModel(sign).texture("particle", texture));
        flatBlockItem(sign, "item");

        simpleBlock(wallSign.get(), blockModel(wallSign).texture("particle", texture));
    }

    protected void registerBookshelf(BlockEntry<Block> planks, BlockEntry<TIBookshelfBlock> bookshelf) {
        simpleBlock(bookshelf.get(), models().cubeColumn(blockName(bookshelf), blockTexture(bookshelf), blockTexture(planks)));
        blockItem(bookshelf);
    }

    protected void registerLadder(BlockEntry<LadderBlock> ladder) {
        horizontalBlock(ladder.get(), models().withExistingParent(blockName(ladder), "block/ladder")
            .texture("particle", blockTexture(ladder))
            .texture("texture", blockTexture(ladder)));

        flatBlockItem(ladder, "block");
    }

    protected void registerBeehive(BlockEntry<TIBeehiveBlock> beehive) {
        var name = blockName(beehive);
        var texture = blockTexture(beehive);

        var beehiveModel = models().orientableWithBottom(name, suffix(texture, "_side"), suffix(texture, "_front"), suffix(texture, "_end"), suffix(texture, "_end"))
            .texture("particle", suffix(texture, "_side"));
        var fullBeehiveModel = models().orientableWithBottom(name + "_honey", suffix(texture, "_side"), suffix(texture, "_front_honey"), suffix(texture, "_end"), suffix(texture, "_end"))
            .texture("particle", suffix(texture, "_side"));

        horizontalBlock(beehive.get(), state -> state.getValue(BlockStateProperties.LEVEL_HONEY) == 5 ? fullBeehiveModel : beehiveModel);
        blockItem(beehive);
    }

    protected void registerCabinet(BlockEntry<? extends Block> cabinet) {
        String name = blockName(cabinet);
        this.horizontalBlock(cabinet.get(), state -> {
            String suffix = state.getValue(BlockStateProperties.OPEN) ? "_open" : "";
            return models().orientable(name + suffix,
               TerraIncognita.prefix("block/" + name + "_side"),
               TerraIncognita.prefix("block/" + name + "_front" + suffix),
               TerraIncognita.prefix("block/" + name + "_top"));
        });

        blockItem(cabinet);
    }

    protected void blockItem(BlockEntry<?> block) {
        blockItem(block.get());
    }

    protected void blockItem(Block block) {
        var texture = blockTexture(block);
        simpleBlockItem(block, new ExistingModelFile(texture, this.models().existingFileHelper));
    }

    @SuppressWarnings("ConstantConditions")
    protected void flatBlockItem(ItemLike item, String textureDirectory) {
        var path = ForgeRegistries.ITEMS.getKey(item.asItem()).getPath();
        itemModels().withExistingParent(path, "item/generated")
            .texture("layer0", TerraIncognita.prefix(textureDirectory + "/" + path));
    }
}