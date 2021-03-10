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
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.trees.Tree;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.TallBlockItem;
import net.minecraft.state.properties.ChestType;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.RegistryObject;

public class ModWoodType {
    public final String name;

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

    @SuppressWarnings({"ConstantConditions", "deprecation"})
    public ModWoodType(String id, Tree tree, MaterialColor woodColor, MaterialColor barkColor) {
        this.name = id;

        SAPLING = PottablePlantEntry.createPlant(id + "_sapling", () -> new SaplingBlock(tree, Block.Properties.from(Blocks.OAK_SAPLING)));
        LEAVES = ModBlocks.HELPER.newBuilder(id + "_leaves", this::createLeaves).cutoutMippedRender().build();
        LOG = ModBlocks.HELPER.newBuilder(id + "_log", () -> createLogBlock(woodColor, barkColor)).withItemGroup(ItemGroup.BUILDING_BLOCKS).build();
        STRIPPED_LOG = ModBlocks.HELPER.newBuilder("stripped_" + id + "_log", () -> createLogBlock(woodColor, woodColor)).withItemGroup(ItemGroup.BUILDING_BLOCKS).build();
        WOOD = ModBlocks.HELPER.newBuilder(id + "_wood", () -> createWoodBlock(barkColor)).withItemGroup(ItemGroup.BUILDING_BLOCKS).build();
        STRIPPED_WOOD = ModBlocks.HELPER.newBuilder("stripped_" + id + "_wood", () -> createWoodBlock(barkColor)).withItemGroup(ItemGroup.BUILDING_BLOCKS).build();
        PLANKS = ModBlocks.HELPER.newBuilder(id + "_planks", () -> createPlanks(woodColor)).withItemGroup(ItemGroup.BUILDING_BLOCKS).build();
        STAIRS = ModBlocks.HELPER.newBuilder(id + "_stairs", () -> new StairsBlock(PLANKS.getBlock().getDefaultState(), Block.Properties.from(PLANKS.getBlock()))).withItemGroup(ItemGroup.BUILDING_BLOCKS).build();
        SLAB = ModBlocks.HELPER.newBuilder(id + "_slab", () -> new SlabBlock(Block.Properties.from(PLANKS.getBlock()))).withItemGroup(ItemGroup.BUILDING_BLOCKS).build();
        FENCE = ModBlocks.HELPER.newBuilder(id + "_fence", () -> new FenceBlock(Block.Properties.create(Material.WOOD, woodColor).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD))).build();
        FENCE_GATE = ModBlocks.HELPER.newBuilder(id + "_fence_gate", () -> new FenceGateBlock(Block.Properties.create(Material.WOOD, woodColor).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD))).build();
        SIGN = ModBlocks.HELPER.newBuilder(id + "_sign", () -> new ModStandingSignBlock(woodColor, this)).withBlockItem(block -> new ModSignItem(this)).build();
        WALL_SIGN = ModBlocks.HELPER.newBuilder(id + "_wall_sign", () -> new ModWallSignBlock(woodColor, this)).withoutItemForm().build();
        DOOR = ModBlocks.HELPER.newBuilder(id + "_door", () -> new DoorBlock(Block.Properties.create(Material.WOOD, woodColor).hardnessAndResistance(3.0F).sound(SoundType.WOOD).notSolid())).withBlockItem(block -> new TallBlockItem(block, new Item.Properties().group(ItemGroup.REDSTONE))).cutoutRender().build();
        TRAPDOOR = ModBlocks.HELPER.newBuilder(id + "_trapdoor", () -> new TrapDoorBlock(Block.Properties.create(Material.WOOD, woodColor).hardnessAndResistance(3.0F).sound(SoundType.WOOD).notSolid().setAllowsSpawn((state, reader, pos, entity) -> false))).withItemGroup(ItemGroup.REDSTONE).cutoutRender().build();
        BUTTON = ModBlocks.HELPER.newBuilder(id + "_button", () -> new WoodButtonBlock(Block.Properties.from(Blocks.OAK_BUTTON))).withItemGroup(ItemGroup.REDSTONE).build();
        PRESSURE_PLATE = ModBlocks.HELPER.newBuilder(id + "_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Block.Properties.create(Material.WOOD, woodColor).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD))).withItemGroup(ItemGroup.REDSTONE).build();
        CHEST = ModBlocks.HELPER.newBuilder(id +  "_chest", () -> new ModChestBlock(this, woodColor)).withItemProperties(new Item.Properties().group(ItemGroup.DECORATIONS).setISTER(() -> ModChestISTER::forNormalChest)).build();
        TRAPPED_CHEST = ModBlocks.HELPER.newBuilder(id +  "_trapped_chest", () -> new ModTrappedChestBlock(this, woodColor)).withItemProperties(new Item.Properties().group(ItemGroup.REDSTONE).setISTER(() -> ModChestISTER::forTrappedChest)).build();
        BOAT = ModItems.ITEMS.register(id + "_boat", () -> new ModBoatItem(this));

        SIGN_TEXTURE = TerraIncognita.prefix("entity/sign/" + name);
        BOAT_TEXTURE = TerraIncognita.prefix("textures/entity/boat/" + name + ".png");
    }

    public ResourceLocation getChestTexture(ChestType type, boolean trapped) {
        String location = "entity/chest/" + name + "_";
        if (trapped) location += "trapped_";
        location += type.getString();

        return TerraIncognita.prefix(location);
    }

    protected Block createLogBlock(MaterialColor topColor, MaterialColor barkColor) {
        return new RotatedPillarBlock(Block.Properties.create(Material.WOOD, (state) ->
                state.get(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? topColor : barkColor
            ).hardnessAndResistance(2.0F).sound(SoundType.WOOD));
    }

    protected Block createWoodBlock(MaterialColor color) {
        return new RotatedPillarBlock(Block.Properties.create(Material.WOOD, color).hardnessAndResistance(2.0F).sound(SoundType.WOOD));
    }

    protected Block createPlanks(MaterialColor color) {
        return new Block(Block.Properties.create(Material.WOOD, color).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD));
    }

    protected Block createLeaves() {
        return new LeavesBlock(Block.Properties.create(Material.LEAVES).hardnessAndResistance(0.2F).tickRandomly().sound(SoundType.PLANT).notSolid()
            .setAllowsSpawn((state, reader, pos, entity) -> entity == EntityType.OCELOT || entity == EntityType.PARROT)
            .setSuffocates((state, reader, pos) -> false)
            .setBlocksVision((state, reader, pos) -> false)
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
