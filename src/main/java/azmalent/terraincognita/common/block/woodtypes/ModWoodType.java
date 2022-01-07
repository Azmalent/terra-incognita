package azmalent.terraincognita.common.block.woodtypes;

import azmalent.cuneiform.lib.registry.BlockEntry;
import azmalent.cuneiform.lib.util.DataUtil;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.client.renderer.ModChestISTER;
import azmalent.terraincognita.common.block.PottablePlantEntry;
import azmalent.terraincognita.common.block.chests.ModChestBlock;
import azmalent.terraincognita.common.block.chests.ModTrappedChestBlock;
import azmalent.terraincognita.common.block.signs.ModStandingSignBlock;
import azmalent.terraincognita.common.block.signs.ModWallSignBlock;
import azmalent.terraincognita.common.item.ModBoatItem;
import azmalent.terraincognita.common.item.block.ModSignItem;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModItems;
import net.minecraft.block.*;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.item.TallBlockItem;
import net.minecraft.world.level.block.state.properties.ChestType;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.DoorBlock;
import net.minecraft.world.level.block.FenceBlock;
import net.minecraft.world.level.block.FenceGateBlock;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.PressurePlateBlock;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.SaplingBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.StairBlock;
import net.minecraft.world.level.block.TrapDoorBlock;
import net.minecraft.world.level.block.WoodButtonBlock;

public class ModWoodType {
    public final String name;
    public final MaterialColor woodColor;
    public final MaterialColor barkColor;

    public final PottablePlantEntry SAPLING;
    public final BlockEntry LEAVES;
    public final BlockEntry LOG;
    public final BlockEntry STRIPPED_LOG;
    public final BlockEntry WOOD;
    public final BlockEntry STRIPPED_WOOD;
    public final BlockEntry PLANKS;
    public final BlockEntry STAIRS;
    public final BlockEntry SLAB;
    public final BlockEntry FENCE;
    public final BlockEntry FENCE_GATE;
    public final BlockEntry WALL_SIGN;
    public final BlockEntry SIGN;
    public final BlockEntry DOOR;
    public final BlockEntry TRAPDOOR;
    public final BlockEntry BUTTON;
    public final BlockEntry PRESSURE_PLATE;
    public final BlockEntry CHEST;
    public final BlockEntry TRAPPED_CHEST;
    public final RegistryObject<Item> BOAT;

    public final ResourceLocation SIGN_TEXTURE;
    public final ResourceLocation BOAT_TEXTURE;

    @SuppressWarnings({"deprecation"})
    public ModWoodType(String id, AbstractTreeGrower tree, MaterialColor woodColor, MaterialColor barkColor) {
        this.name = id;
        this.woodColor = woodColor;
        this.barkColor = barkColor;

        SAPLING = ModBlocks.createPlant(id + "_sapling", () -> new SaplingBlock(tree, Block.Properties.copy(Blocks.OAK_SAPLING)));
        LEAVES  = ModBlocks.HELPER.newBuilder(id + "_leaves", this::createLeaves).cutoutMippedRender().build();

        LOG           = ModBlocks.HELPER.newBuilder(id + "_log", () -> createLogBlock(woodColor, barkColor)).withItemGroup(CreativeModeTab.TAB_BUILDING_BLOCKS).build();
        STRIPPED_LOG  = ModBlocks.HELPER.newBuilder("stripped_" + id + "_log", () -> createLogBlock(woodColor, woodColor)).withItemGroup(CreativeModeTab.TAB_BUILDING_BLOCKS).build();
        WOOD          = ModBlocks.HELPER.newBuilder(id + "_wood", () -> createWoodBlock(barkColor)).withItemGroup(CreativeModeTab.TAB_BUILDING_BLOCKS).build();
        STRIPPED_WOOD = ModBlocks.HELPER.newBuilder("stripped_" + id + "_wood", () -> createWoodBlock(barkColor)).withItemGroup(CreativeModeTab.TAB_BUILDING_BLOCKS).build();

        PLANKS     = ModBlocks.HELPER.newBuilder(id + "_planks", () -> createPlanks(woodColor)).withItemGroup(CreativeModeTab.TAB_BUILDING_BLOCKS).build();
        STAIRS     = ModBlocks.HELPER.newBuilder(id + "_stairs", () -> new StairBlock(PLANKS.getBlock().defaultBlockState(), Block.Properties.copy(PLANKS.getBlock()))).withItemGroup(CreativeModeTab.TAB_BUILDING_BLOCKS).build();
        SLAB       = ModBlocks.HELPER.newBuilder(id + "_slab", () -> new SlabBlock(Block.Properties.copy(PLANKS.getBlock()))).withItemGroup(CreativeModeTab.TAB_BUILDING_BLOCKS).build();
        FENCE      = ModBlocks.HELPER.newBuilder(id + "_fence", FenceBlock::new, Block.Properties.of(Material.WOOD, woodColor).strength(2.0F, 3.0F).sound(SoundType.WOOD)).build();
        FENCE_GATE = ModBlocks.HELPER.newBuilder(id + "_fence_gate", FenceGateBlock::new, Block.Properties.of(Material.WOOD, woodColor).strength(2.0F, 3.0F).sound(SoundType.WOOD)).build();

        BUTTON         = ModBlocks.HELPER.newBuilder(id + "_button", WoodButtonBlock::new, Block.Properties.copy(Blocks.OAK_BUTTON)).withItemGroup(CreativeModeTab.TAB_REDSTONE).build();
        PRESSURE_PLATE = ModBlocks.HELPER.newBuilder(id + "_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.of(Material.WOOD, woodColor).noCollission().strength(0.5F).sound(SoundType.WOOD))).withItemGroup(CreativeModeTab.TAB_REDSTONE).build();

        DOOR     = ModBlocks.HELPER.newBuilder(id + "_door", DoorBlock::new, Block.Properties.of(Material.WOOD, woodColor).strength(3.0F).sound(SoundType.WOOD).noOcclusion()).withTallBlockItem(CreativeModeTab.TAB_REDSTONE).cutoutRender().build();
        TRAPDOOR = ModBlocks.HELPER.newBuilder(id + "_trapdoor",TrapDoorBlock::new, Block.Properties.of(Material.WOOD, woodColor).strength(3.0F).sound(SoundType.WOOD).noOcclusion().isValidSpawn((state, reader, pos, entity) -> false)).withItemGroup(CreativeModeTab.TAB_REDSTONE).cutoutRender().build();

        SIGN          = ModBlocks.HELPER.newBuilder(id + "_sign", () -> new ModStandingSignBlock(this)).withBlockItem(block -> new ModSignItem(this)).build();
        WALL_SIGN     = ModBlocks.HELPER.newBuilder(id + "_wall_sign", () -> new ModWallSignBlock(this)).withoutItemForm().build();
        CHEST         = ModBlocks.HELPER.newBuilder(id + "_chest", () -> new ModChestBlock(this)).withItemProperties(new Item.Properties().tab(CreativeModeTab.TAB_DECORATIONS).setISTER(() -> ModChestISTER::forNormalChest)).build();
        TRAPPED_CHEST = ModBlocks.HELPER.newBuilder(id + "_trapped_chest", () -> new ModTrappedChestBlock(this)).withItemProperties(new Item.Properties().tab(CreativeModeTab.TAB_REDSTONE).setISTER(() -> ModChestISTER::forTrappedChest)).build();
        BOAT          = ModItems.ITEMS.register(id + "_boat", () -> new ModBoatItem(this));

        SIGN_TEXTURE = TerraIncognita.prefix("entity/sign/" + name);
        BOAT_TEXTURE = TerraIncognita.prefix("textures/entity/boat/" + name + ".png");
    }

    public ResourceLocation getChestTexture(ChestType type, boolean trapped) {
        String location = "entity/chest/" + name + "_";
        if (trapped) location += "trapped_";
        location += type.getSerializedName();

        return TerraIncognita.prefix(location);
    }

    protected Block createLogBlock(MaterialColor topColor, MaterialColor barkColor) {
        return new RotatedPillarBlock(Block.Properties.of(Material.WOOD, (state) ->
                state.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : barkColor
            ).strength(2.0F).sound(SoundType.WOOD));
    }

    protected Block createWoodBlock(MaterialColor color) {
        return new RotatedPillarBlock(Block.Properties.of(Material.WOOD, color).strength(2.0F).sound(SoundType.WOOD));
    }

    protected Block createPlanks(MaterialColor color) {
        return new Block(Block.Properties.of(Material.WOOD, color).strength(2.0F, 3.0F).sound(SoundType.WOOD));
    }

    protected Block createLeaves() {
        return new LeavesBlock(Block.Properties.of(Material.LEAVES).strength(0.2F).randomTicks().sound(SoundType.GRASS).noOcclusion()
            .isValidSpawn((state, reader, pos, entity) -> entity == EntityType.OCELOT || entity == EntityType.PARROT)
            .isSuffocating((state, reader, pos) -> false)
            .isViewBlocking((state, reader, pos) -> false)
        );
    }

    public void initFlammability() {
        DataUtil.registerFlammable(LEAVES.getBlock(), 30, 60);
        DataUtil.registerFlammable(LOG.getBlock(), 5, 5);
        DataUtil.registerFlammable(STRIPPED_LOG.getBlock(), 5, 5);
        DataUtil.registerFlammable(WOOD.getBlock(), 5, 5);
        DataUtil.registerFlammable(STRIPPED_WOOD.getBlock(), 5, 5);
        DataUtil.registerFlammable(PLANKS.getBlock(), 5, 20);
        DataUtil.registerFlammable(STAIRS.getBlock(), 5, 20);
        DataUtil.registerFlammable(SLAB.getBlock(), 5, 20);
        DataUtil.registerFlammable(FENCE.getBlock(), 5, 20);
        DataUtil.registerFlammable(FENCE_GATE.getBlock(), 5, 20);
    }
}
