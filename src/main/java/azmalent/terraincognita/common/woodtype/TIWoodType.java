package azmalent.terraincognita.common.woodtype;

import azmalent.cuneiform.registry.BlockEntry;
import azmalent.cuneiform.registry.ItemEntry;
import azmalent.cuneiform.util.DataUtil;
import azmalent.cuneiform.util.ItemUtil;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.block.chest.TIChestBlock;
import azmalent.terraincognita.common.block.chest.TITrappedChestBlock;
import azmalent.terraincognita.common.block.sign.TIStandingSignBlock;
import azmalent.terraincognita.common.block.sign.TIWallSignBlock;
import azmalent.terraincognita.common.item.TIBoatItem;
import azmalent.terraincognita.common.item.block.TIChestItem;
import azmalent.terraincognita.common.registry.ModBlocks;
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

    public final ItemEntry<TIBoatItem> BOAT;
    public final ResourceLocation BOAT_TEXTURE;

    @SuppressWarnings({"deprecation"})
    public TIWoodType(String id, AbstractTreeGrower tree, MaterialColor woodColor, MaterialColor barkColor) {
        super(TerraIncognita.prefix(id).toString());
        register(this);

        this.name = id;
        this.woodColor = woodColor;
        this.barkColor = barkColor;

        BOAT_TEXTURE = TerraIncognita.prefix("textures/entity/boat/" + name + ".png");

        SAPLING = ModBlocks.createPlant(id + "_sapling", () -> new SaplingBlock(tree, Block.Properties.copy(Blocks.OAK_SAPLING)));
        LEAVES  = TerraIncognita.REGISTRY_HELPER.createBlock(id + "_leaves", this::createLeaves).cutoutMippedRender().build();

        LOG           = TerraIncognita.REGISTRY_HELPER.createBlock(id + "_log", () -> createLogBlock(woodColor, barkColor)).blockItem(CreativeModeTab.TAB_BUILDING_BLOCKS).build();
        STRIPPED_LOG  = TerraIncognita.REGISTRY_HELPER.createBlock("stripped_" + id + "_log", () -> createLogBlock(woodColor, woodColor)).blockItem(CreativeModeTab.TAB_BUILDING_BLOCKS).build();
        WOOD          = TerraIncognita.REGISTRY_HELPER.createBlock(id + "_wood", () -> createWoodBlock(barkColor)).blockItem(CreativeModeTab.TAB_BUILDING_BLOCKS).build();
        STRIPPED_WOOD = TerraIncognita.REGISTRY_HELPER.createBlock("stripped_" + id + "_wood", () -> createWoodBlock(barkColor)).blockItem(CreativeModeTab.TAB_BUILDING_BLOCKS).build();

        PLANKS     = TerraIncognita.REGISTRY_HELPER.createBlock(id + "_planks", () -> createPlanks(woodColor)).blockItem(CreativeModeTab.TAB_BUILDING_BLOCKS).build();
        STAIRS     = TerraIncognita.REGISTRY_HELPER.createBlock(id + "_stairs", () -> new StairBlock(PLANKS.defaultBlockState(), Block.Properties.copy(PLANKS.get()))).blockItem(CreativeModeTab.TAB_BUILDING_BLOCKS).build();
        SLAB       = TerraIncognita.REGISTRY_HELPER.createBlock(id + "_slab", () -> new SlabBlock(Block.Properties.copy(PLANKS.get()))).blockItem(CreativeModeTab.TAB_BUILDING_BLOCKS).build();
        FENCE      = TerraIncognita.REGISTRY_HELPER.createBlock(id + "_fence", FenceBlock::new, Block.Properties.of(Material.WOOD, woodColor).strength(2.0F, 3.0F).sound(SoundType.WOOD)).build();
        FENCE_GATE = TerraIncognita.REGISTRY_HELPER.createBlock(id + "_fence_gate", FenceGateBlock::new, Block.Properties.of(Material.WOOD, woodColor).strength(2.0F, 3.0F).sound(SoundType.WOOD)).build();

        BUTTON         = TerraIncognita.REGISTRY_HELPER.createBlock(id + "_button", WoodButtonBlock::new, Block.Properties.copy(Blocks.OAK_BUTTON)).blockItem(CreativeModeTab.TAB_REDSTONE).build();
        PRESSURE_PLATE = TerraIncognita.REGISTRY_HELPER.createBlock(id + "_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.of(Material.WOOD, woodColor).noCollission().strength(0.5F).sound(SoundType.WOOD))).blockItem(CreativeModeTab.TAB_REDSTONE).build();

        DOOR     = TerraIncognita.REGISTRY_HELPER.createBlock(id + "_door", DoorBlock::new, Block.Properties.of(Material.WOOD, woodColor).strength(3.0F).sound(SoundType.WOOD).noOcclusion()).tallBlockItem(CreativeModeTab.TAB_REDSTONE).cutoutRender().build();
        TRAPDOOR = TerraIncognita.REGISTRY_HELPER.createBlock(id + "_trapdoor", TrapDoorBlock::new, Block.Properties.of(Material.WOOD, woodColor).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn((state, reader, pos, entity) -> false)).blockItem(CreativeModeTab.TAB_REDSTONE).cutoutRender().build();

        WALL_SIGN     = TerraIncognita.REGISTRY_HELPER.createBlock(id + "_wall_sign", () -> new TIWallSignBlock(this)).noItemForm().build();
        SIGN          = TerraIncognita.REGISTRY_HELPER.createBlock(id + "_sign", () -> new TIStandingSignBlock(this)).blockItem(block -> new SignItem(new Item.Properties().stacksTo(16).tab(CreativeModeTab.TAB_DECORATIONS), getSign(), WALL_SIGN.get())).build();
        CHEST         = TerraIncognita.REGISTRY_HELPER.createBlock(id + "_chest", () -> new TIChestBlock(this)).blockItem(TIChestItem::new, CreativeModeTab.TAB_DECORATIONS).build();
        TRAPPED_CHEST = TerraIncognita.REGISTRY_HELPER.createBlock(id + "_trapped_chest", () -> new TITrappedChestBlock(this)).blockItem(TIChestItem::new, CreativeModeTab.TAB_REDSTONE).build();

        BOAT          = TerraIncognita.REGISTRY_HELPER.createItem(id + "_boat", () -> new TIBoatItem(this));
    }

    //For use in the constructor
    private TIStandingSignBlock getSign() {
        return SIGN.get();
    }

    public ResourceLocation getChestTexture(ChestType type, boolean trapped) {
        String location = "entity/chest/" + name + "_";
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
    }

    @OnlyIn(Dist.CLIENT)
    public void registerBlockColors(BlockColors colors) {
        colors.register((state, reader, pos, color) -> reader != null && pos != null ? BiomeColors.getAverageFoliageColor(reader, pos) : FoliageColor.get(0.5D, 1.0D),
            LEAVES.get()
        );
    }

    @OnlyIn(Dist.CLIENT)
    public void registerItemColors(ItemColors itemColors, BlockColors blockColors) {
        itemColors.register((stack, index) -> blockColors.getColor(ItemUtil.getBlockFromItem(stack).defaultBlockState(), null, null, index),
            LEAVES
        );
    }
}
