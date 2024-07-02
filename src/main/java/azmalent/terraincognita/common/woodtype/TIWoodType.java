package azmalent.terraincognita.common.woodtype;

import azmalent.cuneiform.registry.BlockEntry;
import azmalent.cuneiform.registry.ItemEntry;
import azmalent.cuneiform.util.DataUtil;
import azmalent.cuneiform.util.ItemUtil;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.block.woodset.*;
import azmalent.terraincognita.common.block.woodset.chest.TIChestBlock;
import azmalent.terraincognita.common.block.woodset.chest.TITrappedChestBlock;
import azmalent.terraincognita.common.block.woodset.sign.TIStandingSignBlock;
import azmalent.terraincognita.common.block.woodset.sign.TIWallSignBlock;
import azmalent.terraincognita.common.item.TIBoatItem;
import azmalent.terraincognita.common.item.block.TIChestItem;
import azmalent.terraincognita.core.registry.ModBlocks;
import azmalent.terraincognita.integration.ModIntegration;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static azmalent.terraincognita.TerraIncognita.REGISTRY_HELPER;

public class TIWoodType extends WoodType {
    public final String name;
    public final MaterialColor woodColor;
    public final MaterialColor barkColor;

    public final BlockEntry<SaplingBlock> SAPLING;
    public final BlockEntry<LeavesBlock> LEAVES;
    public final BlockEntry<RotatedPillarBlock> LOG;
    public final BlockEntry<RotatedPillarBlock> STRIPPED_LOG;
    public final BlockEntry<RotatedPillarBlock> WOOD;
    public final BlockEntry<RotatedPillarBlock> STRIPPED_WOOD;
    public final BlockEntry<Block> PLANKS;
    public final BlockEntry<StairBlock> STAIRS;
    public final BlockEntry<SlabBlock> SLAB;
    public final BlockEntry<FenceBlock> FENCE;
    public final BlockEntry<FenceGateBlock> FENCE_GATE;
    public final BlockEntry<TIWallSignBlock> WALL_SIGN;
    public final BlockEntry<TIStandingSignBlock> SIGN;
    public final BlockEntry<DoorBlock> DOOR;
    public final BlockEntry<TrapDoorBlock> TRAPDOOR;
    public final BlockEntry<WoodButtonBlock> BUTTON;
    public final BlockEntry<PressurePlateBlock> PRESSURE_PLATE;
    public final BlockEntry<TIChestBlock> CHEST;
    public final BlockEntry<TITrappedChestBlock> TRAPPED_CHEST;
    public final BlockEntry<TIBeehiveBlock> BEEHIVE;

    //Quark
    public final BlockEntry<Block> VERTICAL_PLANKS;
    public final BlockEntry<TIVerticalSlabBlock> VERTICAL_SLAB;
    public final BlockEntry<TIBookshelfBlock> BOOKSHELF;
    public final BlockEntry<LadderBlock> LADDER;
    public final BlockEntry<TIWoodPostBlock> POST;
    public final BlockEntry<TIWoodPostBlock> STRIPPED_POST;
    public final BlockEntry<TIHedgeBlock> HEDGE;
    public final BlockEntry<TILeafCarpetBlock> LEAF_CARPET;
    //TODO: hollow log (1.19)

    //Woodworks
    public final BlockEntry<RotatedPillarBlock> BOARDS;
    public final BlockEntry<TILeafPileBlock> LEAF_PILE;

    //Farmer's Delight
    public final BlockEntry<? extends Block> CABINET;

    public final ItemEntry<TIBoatItem> BOAT;
    public final ResourceLocation BOAT_TEXTURE;

    //Boatload


    @SuppressWarnings({"deprecation"})
    public TIWoodType(String name, AbstractTreeGrower tree, MaterialColor woodColor, MaterialColor barkColor) {
        super(TerraIncognita.prefix(name).toString());
        register(this);

        this.name = name;
        this.woodColor = woodColor;
        this.barkColor = barkColor;

        BOAT_TEXTURE = TerraIncognita.prefix("textures/entity/boat/" + name + ".png");

        SAPLING = ModBlocks.createPlant(name + "_sapling", () -> new SaplingBlock(tree, Block.Properties.copy(Blocks.OAK_SAPLING)));
        LEAVES  = REGISTRY_HELPER.createBlock(name + "_leaves", this::createLeaves).cutoutMippedRender().build();

        LOG           = REGISTRY_HELPER.createBlock(name + "_log", () -> createLogBlock(woodColor, barkColor)).blockItem(CreativeModeTab.TAB_BUILDING_BLOCKS).build();
        STRIPPED_LOG  = REGISTRY_HELPER.createBlock("stripped_" + name + "_log", () -> createLogBlock(woodColor, woodColor)).blockItem(CreativeModeTab.TAB_BUILDING_BLOCKS).build();
        WOOD          = REGISTRY_HELPER.createBlock(name + "_wood", () -> createWoodBlock(barkColor)).blockItem(CreativeModeTab.TAB_BUILDING_BLOCKS).build();
        STRIPPED_WOOD = REGISTRY_HELPER.createBlock("stripped_" + name + "_wood", () -> createWoodBlock(barkColor)).blockItem(CreativeModeTab.TAB_BUILDING_BLOCKS).build();

        PLANKS     = REGISTRY_HELPER.createBlock(name + "_planks", () -> createPlanks(woodColor)).blockItem(CreativeModeTab.TAB_BUILDING_BLOCKS).build();
        STAIRS     = REGISTRY_HELPER.createBlock(name + "_stairs", () -> new StairBlock(PLANKS.defaultBlockState(), Block.Properties.copy(PLANKS.get()))).blockItem(CreativeModeTab.TAB_BUILDING_BLOCKS).build();
        SLAB       = REGISTRY_HELPER.createBlock(name + "_slab", () -> new SlabBlock(Block.Properties.copy(PLANKS.get()))).blockItem(CreativeModeTab.TAB_BUILDING_BLOCKS).build();
        FENCE      = REGISTRY_HELPER.createBlock(name + "_fence", FenceBlock::new, Block.Properties.of(Material.WOOD, woodColor).strength(2.0F, 3.0F).sound(SoundType.WOOD)).build();
        FENCE_GATE = REGISTRY_HELPER.createBlock(name + "_fence_gate", FenceGateBlock::new, Block.Properties.of(Material.WOOD, woodColor).strength(2.0F, 3.0F).sound(SoundType.WOOD)).build();

        BUTTON         = REGISTRY_HELPER.createBlock(name + "_button", WoodButtonBlock::new, Block.Properties.copy(Blocks.OAK_BUTTON)).blockItem(CreativeModeTab.TAB_REDSTONE).build();
        PRESSURE_PLATE = REGISTRY_HELPER.createBlock(name + "_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.of(Material.WOOD, woodColor).noCollission().strength(0.5F).sound(SoundType.WOOD))).blockItem(CreativeModeTab.TAB_REDSTONE).build();

        DOOR     = REGISTRY_HELPER.createBlock(name + "_door", DoorBlock::new, Block.Properties.of(Material.WOOD, woodColor).strength(3.0F).sound(SoundType.WOOD).noOcclusion()).tallBlockItem(CreativeModeTab.TAB_REDSTONE).cutoutRender().build();
        TRAPDOOR = REGISTRY_HELPER.createBlock(name + "_trapdoor", TrapDoorBlock::new, Block.Properties.of(Material.WOOD, woodColor).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn((state, reader, pos, entity) -> false)).blockItem(CreativeModeTab.TAB_REDSTONE).cutoutRender().build();

        WALL_SIGN     = REGISTRY_HELPER.createBlock(name + "_wall_sign", () -> new TIWallSignBlock(this)).noItemForm().build();
        SIGN          = REGISTRY_HELPER.createBlock(name + "_sign", () -> new TIStandingSignBlock(this)).blockItem(block -> new SignItem(new Item.Properties().stacksTo(16).tab(CreativeModeTab.TAB_DECORATIONS), getSign(), WALL_SIGN.get())).build();
        CHEST         = REGISTRY_HELPER.createBlock(name + "_chest", () -> new TIChestBlock(this)).blockItem(TIChestItem::new, CreativeModeTab.TAB_DECORATIONS).build();
        TRAPPED_CHEST = REGISTRY_HELPER.createBlock(name + "_trapped_chest", () -> new TITrappedChestBlock(this)).blockItem(TIChestItem::new, CreativeModeTab.TAB_REDSTONE).build();
        BEEHIVE       = REGISTRY_HELPER.createBlock(name + "_beehive", TIBeehiveBlock::new, Block.Properties.copy(Blocks.BEEHIVE)).blockItem(CreativeModeTab.TAB_DECORATIONS).build();

        //TODO: fix missing block items here
        VERTICAL_PLANKS = REGISTRY_HELPER.createBlock("vertical_" + name + "_planks", () -> createPlanks(woodColor)).blockItem(CreativeModeTab.TAB_BUILDING_BLOCKS).build();
        VERTICAL_SLAB   = REGISTRY_HELPER.createBlock(name + "_vertical_slab", TIVerticalSlabBlock::new, Block.Properties.of(Material.WOOD, woodColor).strength(2.0F, 3.0F).sound(SoundType.WOOD)).blockItem(CreativeModeTab.TAB_BUILDING_BLOCKS).build();
        BOOKSHELF       = REGISTRY_HELPER.createBlock(name + "_bookshelf", TIBookshelfBlock::new, Block.Properties.of(Material.WOOD, woodColor).strength(1.5F).sound(SoundType.WOOD)).blockItem(CreativeModeTab.TAB_BUILDING_BLOCKS).build();
        LADDER          = REGISTRY_HELPER.createBlock(name + "_ladder", LadderBlock::new, Block.Properties.copy(Blocks.LADDER)).cutoutRender().build();
        POST            = REGISTRY_HELPER.createBlock(name + "_post", () -> new TIWoodPostBlock(barkColor)).cutoutRender().build();
        STRIPPED_POST   = REGISTRY_HELPER.createBlock("stripped_" + name + "_post", () -> new TIWoodPostBlock(woodColor)).cutoutRender().build();
        HEDGE           = REGISTRY_HELPER.createBlock(name + "_hedge", () -> new TIHedgeBlock(woodColor)).cutoutMippedRender().build();
        LEAF_CARPET     = REGISTRY_HELPER.createBlock(name + "_leaf_carpet", TILeafCarpetBlock::new).cutoutMippedRender().build();

        BOARDS = REGISTRY_HELPER.createBlock(name + "_boards", () -> new RotatedPillarBlock(Block.Properties.copy(Blocks.OAK_PLANKS).color(woodColor))).blockItem(CreativeModeTab.TAB_BUILDING_BLOCKS).build();
        LEAF_PILE = REGISTRY_HELPER.createBlock(name + "_leaf_pile", TILeafPileBlock::new).blockItem(CreativeModeTab.TAB_DECORATIONS).build();

        CABINET = ModIntegration.FARMERS_DELIGHT.registerCabinet(this);

        BOAT = REGISTRY_HELPER.createItem(name + "_boat", () -> new TIBoatItem(this));
    }

    //For use in the constructor
    private TIStandingSignBlock getSign() {
        return SIGN.get();
    }

    public ResourceLocation getChestTexture(ChestType type, boolean trapped) {
        String location = "entity/chest/" + name + "/";
        if (trapped) location += "trapped_";
        location += type.getSerializedName();

        return TerraIncognita.prefix(location);
    }

    protected RotatedPillarBlock createLogBlock(MaterialColor topColor, MaterialColor barkColor) {
        return new RotatedPillarBlock(Block.Properties.of(Material.WOOD, (state) ->
                state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : barkColor
            ).strength(2.0F).sound(SoundType.WOOD));
    }

    protected RotatedPillarBlock createWoodBlock(MaterialColor color) {
        return new RotatedPillarBlock(Block.Properties.of(Material.WOOD, color).strength(2.0F).sound(SoundType.WOOD));
    }

    protected Block createPlanks(MaterialColor color) {
        return new Block(Block.Properties.of(Material.WOOD, color).strength(2.0F, 3.0F).sound(SoundType.WOOD));
    }

    protected LeavesBlock createLeaves() {
        return new LeavesBlock(Block.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion()
            .isValidSpawn((state, reader, pos, entity) -> entity == EntityType.OCELOT || entity == EntityType.PARROT)
            .isSuffocating((state, reader, pos) -> false)
            .isViewBlocking((state, reader, pos) -> false)
        );
    }

    public void initFlammability() {
        DataUtil.registerFlammable(LEAVES, 30, 60);
        DataUtil.registerFlammable(LOG, 5, 5);
        DataUtil.registerFlammable(STRIPPED_LOG, 5, 5);
        DataUtil.registerFlammable(WOOD, 5, 5);
        DataUtil.registerFlammable(STRIPPED_WOOD, 5, 5);
        DataUtil.registerFlammable(PLANKS, 5, 20);
        DataUtil.registerFlammable(STAIRS, 5, 20);
        DataUtil.registerFlammable(SLAB, 5, 20);
        DataUtil.registerFlammable(FENCE, 5, 20);
        DataUtil.registerFlammable(FENCE_GATE, 5, 20);

        //Quark
        DataUtil.registerFlammable(VERTICAL_PLANKS, 5, 20);
        DataUtil.registerFlammable(VERTICAL_SLAB, 5, 20);
        DataUtil.registerFlammable(BOOKSHELF, 30, 20);
        DataUtil.registerFlammable(POST, 5, 20);
        DataUtil.registerFlammable(STRIPPED_POST, 5, 20);
        DataUtil.registerFlammable(LEAF_CARPET, 30, 60);
        DataUtil.registerFlammable(HEDGE, 5, 20);

        //TODO: flammability
    }

    @OnlyIn(Dist.CLIENT)
    public void registerBlockColors(BlockColors colors) {
        colors.register((state, reader, pos, color) -> reader != null && pos != null ? BiomeColors.getAverageFoliageColor(reader, pos) : FoliageColor.get(0.5D, 1.0D),
            LEAVES.get(), LEAF_CARPET.get(), HEDGE.get()
        );
    }

    @OnlyIn(Dist.CLIENT)
    public void registerItemColors(ItemColors itemColors, BlockColors blockColors) {
        itemColors.register((stack, index) -> blockColors.getColor(ItemUtil.getBlockFromItem(stack).defaultBlockState(), null, null, index),
            LEAVES, LEAF_CARPET, HEDGE
        );
    }
}
