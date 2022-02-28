package azmalent.terraincognita.common.woodtype;

import azmalent.cuneiform.lib.registry.BlockEntry;
import azmalent.cuneiform.lib.registry.ItemEntry;
import azmalent.cuneiform.lib.util.DataUtil;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.block.chest.ModChestBlock;
import azmalent.terraincognita.common.block.chest.ModTrappedChestBlock;
import azmalent.terraincognita.common.block.sign.ModStandingSignBlock;
import azmalent.terraincognita.common.block.sign.ModWallSignBlock;
import azmalent.terraincognita.common.item.ModBoatItem;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.util.PottablePlantEntry;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SignItem;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

public class ModWoodType {
    public final String name;
    public final MaterialColor woodColor;
    public final MaterialColor barkColor;

    public final PottablePlantEntry SAPLING;
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
    public final BlockEntry<ModWallSignBlock> WALL_SIGN;
    public BlockEntry<ModStandingSignBlock> SIGN = null;
    public final BlockEntry<DoorBlock> DOOR;
    public final BlockEntry<TrapDoorBlock> TRAPDOOR;
    public final BlockEntry<WoodButtonBlock> BUTTON;
    public final BlockEntry<PressurePlateBlock> PRESSURE_PLATE;
    public final BlockEntry<ModChestBlock> CHEST;
    public final BlockEntry<ModTrappedChestBlock> TRAPPED_CHEST;
    public final ItemEntry<ModBoatItem> BOAT;

    public final ResourceLocation SIGN_TEXTURE;
    public final ResourceLocation BOAT_TEXTURE;

    @SuppressWarnings({"deprecation"})
    public ModWoodType(String id, AbstractTreeGrower tree, MaterialColor woodColor, MaterialColor barkColor) {
        this.name = id;
        this.woodColor = woodColor;
        this.barkColor = barkColor;

        SAPLING = ModBlocks.createPlant(id + "_sapling", () -> new SaplingBlock(tree, Block.Properties.copy(Blocks.OAK_SAPLING)));
        LEAVES  = TerraIncognita.REG_HELPER.createBlock(id + "_leaves", this::createLeaves).cutoutMippedRender().build();

        LOG           = TerraIncognita.REG_HELPER.createBlock(id + "_log", () -> createLogBlock(woodColor, barkColor)).blockItem(CreativeModeTab.TAB_BUILDING_BLOCKS).build();
        STRIPPED_LOG  = TerraIncognita.REG_HELPER.createBlock("stripped_" + id + "_log", () -> createLogBlock(woodColor, woodColor)).blockItem(CreativeModeTab.TAB_BUILDING_BLOCKS).build();
        WOOD          = TerraIncognita.REG_HELPER.createBlock(id + "_wood", () -> createWoodBlock(barkColor)).blockItem(CreativeModeTab.TAB_BUILDING_BLOCKS).build();
        STRIPPED_WOOD = TerraIncognita.REG_HELPER.createBlock("stripped_" + id + "_wood", () -> createWoodBlock(barkColor)).blockItem(CreativeModeTab.TAB_BUILDING_BLOCKS).build();

        PLANKS     = TerraIncognita.REG_HELPER.createBlock(id + "_planks", () -> createPlanks(woodColor)).blockItem(CreativeModeTab.TAB_BUILDING_BLOCKS).build();
        STAIRS     = TerraIncognita.REG_HELPER.createBlock(id + "_stairs", () -> new StairBlock(PLANKS.defaultBlockState(), Block.Properties.copy(PLANKS.get()))).blockItem(CreativeModeTab.TAB_BUILDING_BLOCKS).build();
        SLAB       = TerraIncognita.REG_HELPER.createBlock(id + "_slab", () -> new SlabBlock(Block.Properties.copy(PLANKS.get()))).blockItem(CreativeModeTab.TAB_BUILDING_BLOCKS).build();
        FENCE      = TerraIncognita.REG_HELPER.createBlock(id + "_fence", FenceBlock::new, Block.Properties.of(Material.WOOD, woodColor).strength(2.0F, 3.0F).sound(SoundType.WOOD)).build();
        FENCE_GATE = TerraIncognita.REG_HELPER.createBlock(id + "_fence_gate", FenceGateBlock::new, Block.Properties.of(Material.WOOD, woodColor).strength(2.0F, 3.0F).sound(SoundType.WOOD)).build();

        BUTTON         = TerraIncognita.REG_HELPER.createBlock(id + "_button", WoodButtonBlock::new, Block.Properties.copy(Blocks.OAK_BUTTON)).blockItem(CreativeModeTab.TAB_REDSTONE).build();
        PRESSURE_PLATE = TerraIncognita.REG_HELPER.createBlock(id + "_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.of(Material.WOOD, woodColor).noCollission().strength(0.5F).sound(SoundType.WOOD))).blockItem(CreativeModeTab.TAB_REDSTONE).build();

        DOOR     = TerraIncognita.REG_HELPER.createBlock(id + "_door", DoorBlock::new, Block.Properties.of(Material.WOOD, woodColor).strength(3.0F).sound(SoundType.WOOD).noOcclusion()).tallBlockItem(CreativeModeTab.TAB_REDSTONE).cutoutRender().build();
        TRAPDOOR = TerraIncognita.REG_HELPER.createBlock(id + "_trapdoor",TrapDoorBlock::new, Block.Properties.of(Material.WOOD, woodColor).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn((state, reader, pos, entity) -> false)).blockItem(CreativeModeTab.TAB_REDSTONE).cutoutRender().build();

        WALL_SIGN     = TerraIncognita.REG_HELPER.createBlock(id + "_wall_sign", () -> new ModWallSignBlock(this)).noItemForm().build();
        SIGN          = TerraIncognita.REG_HELPER.createBlock(id + "_sign", () -> new ModStandingSignBlock(this)).blockItem(block -> new SignItem(new Item.Properties().stacksTo(16).tab(CreativeModeTab.TAB_DECORATIONS), SIGN.get(), WALL_SIGN.get())).build();
        CHEST         = TerraIncognita.REG_HELPER.createBlock(id + "_chest", () -> new ModChestBlock(this)).blockItem(CreativeModeTab.TAB_DECORATIONS).build();
        TRAPPED_CHEST = TerraIncognita.REG_HELPER.createBlock(id + "_trapped_chest", () -> new ModTrappedChestBlock(this)).blockItem(CreativeModeTab.TAB_REDSTONE).build();
        BOAT          = TerraIncognita.REG_HELPER.createItem(id + "_boat", () -> new ModBoatItem(this));

        SIGN_TEXTURE = TerraIncognita.prefix("entity/sign/" + name);
        BOAT_TEXTURE = TerraIncognita.prefix("textures/entity/boat/" + name + ".png");
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
}
