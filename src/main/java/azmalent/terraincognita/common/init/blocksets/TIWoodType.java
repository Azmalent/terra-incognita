package azmalent.terraincognita.common.init.blocksets;

import azmalent.cuneiform.lib.config.options.BooleanOption;
import azmalent.cuneiform.lib.registry.BlockEntry;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.block.signs.TIStandingSignBlock;
import azmalent.terraincognita.common.block.signs.TIWallSignBlock;
import azmalent.terraincognita.common.init.ModBlocks;
import azmalent.terraincognita.common.init.ModItems;
import azmalent.terraincognita.common.item.TIBoatItem;
import azmalent.terraincognita.mixin.accessor.FireBlockAccessor;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.block.trees.Tree;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.SignItem;
import net.minecraft.item.TallBlockItem;
import net.minecraft.util.Direction;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;

public class TIWoodType {
    protected final BooleanOption condition;
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
    public final BlockEntry STANDING_SIGN;
    public final BlockEntry DOOR;
    public final BlockEntry TRAPDOOR;
    public final BlockEntry BUTTON;
    public final BlockEntry PRESSURE_PLATE;
    public final RegistryObject<Item> BOAT;

    public final ResourceLocation SIGN_TEXTURE;
    public final ResourceLocation BOAT_TEXTURE;

    @SuppressWarnings({"ConstantConditions", "deprecation"})
    public TIWoodType(String id, Tree tree, MaterialColor woodColor, MaterialColor barkColor, BooleanOption condition) {
        this.condition = condition;
        this.name = id;

        SAPLING = ModBlocks.makePottablePlant(id + "_sapling", () -> new SaplingBlock(tree, Block.Properties.from(Blocks.OAK_SAPLING)), condition);
        LEAVES = ModBlocks.HELPER.newBuilder(id + "_leaves", this::createLeaves).buildIf(condition);
        LOG = ModBlocks.HELPER.newBuilder(id + "_log", () -> createLogBlock(woodColor, barkColor)).buildIf(condition);
        STRIPPED_LOG = ModBlocks.HELPER.newBuilder("stripped_" + id + "_log", () -> createLogBlock(woodColor, woodColor)).buildIf(condition);
        WOOD = ModBlocks.HELPER.newBuilder(id + "_wood", () -> createWoodBlock(barkColor)).buildIf(condition);
        STRIPPED_WOOD = ModBlocks.HELPER.newBuilder("stripped_" + id + "_wood", () -> createWoodBlock(barkColor)).buildIf(condition);
        PLANKS = ModBlocks.HELPER.newBuilder(id + "_planks", () -> createPlanks(woodColor)).buildIf(condition);
        STAIRS = ModBlocks.HELPER.newBuilder(id + "_stairs", () -> new StairsBlock(PLANKS.getBlock().getDefaultState(), Block.Properties.from(PLANKS.getBlock()))).buildIf(condition);
        SLAB = ModBlocks.HELPER.newBuilder(id + "_slab", () -> new SlabBlock(Block.Properties.from(PLANKS.getBlock()))).buildIf(condition);
        FENCE = ModBlocks.HELPER.newBuilder(id + "_fence", () -> new FenceBlock(Block.Properties.create(Material.WOOD, woodColor).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD))).buildIf(condition);
        FENCE_GATE = ModBlocks.HELPER.newBuilder(id + "_fence_gate", () -> new FenceGateBlock(Block.Properties.create(Material.WOOD, woodColor).hardnessAndResistance(2.0F, 3.0F).sound(SoundType.WOOD))).buildIf(condition);
        WALL_SIGN = ModBlocks.HELPER.newBuilder(id + "_wall_sign", () -> new TIWallSignBlock(woodColor, this)).withoutItemForm().buildIf(condition);
        STANDING_SIGN = ModBlocks.HELPER.newBuilder(id + "_sign", () -> new TIStandingSignBlock(woodColor, this)).withBlockItem((block) -> new SignItem(new Item.Properties().maxStackSize(16).group(TerraIncognita.TAB), block, this.WALL_SIGN.getBlock())).buildIf(condition);
        DOOR = ModBlocks.HELPER.newBuilder(id + "_door", () -> new DoorBlock(Block.Properties.create(Material.WOOD, woodColor).hardnessAndResistance(3.0F).sound(SoundType.WOOD).notSolid())).withBlockItem(block -> new TallBlockItem(block, new Item.Properties().group(TerraIncognita.TAB))).buildIf(condition);
        TRAPDOOR = ModBlocks.HELPER.newBuilder(id + "_trapdoor", () -> new TrapDoorBlock(Block.Properties.create(Material.WOOD, woodColor).hardnessAndResistance(3.0F).sound(SoundType.WOOD).notSolid().setAllowsSpawn((state, reader, pos, entity) -> false))).buildIf(condition);
        BUTTON = ModBlocks.HELPER.newBuilder(id + "_button", () -> new WoodButtonBlock(Block.Properties.from(Blocks.OAK_BUTTON))).buildIf(condition);
        PRESSURE_PLATE = ModBlocks.HELPER.newBuilder(id + "_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, AbstractBlock.Properties.create(Material.WOOD, woodColor).doesNotBlockMovement().hardnessAndResistance(0.5F).sound(SoundType.WOOD))).buildIf(condition);
        BOAT = ModItems.ITEMS.register(id + "_boat", () -> new TIBoatItem(this));

        SIGN_TEXTURE = TerraIncognita.prefix("entity/sign/" + name);
        BOAT_TEXTURE = TerraIncognita.prefix("textures/entity/boat/" + name + ".png");
    }

    public boolean isEnabled() {
        return condition.get();
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
        if (!isEnabled()) {
            return;
        }

        FireBlockAccessor fire = (FireBlockAccessor) Blocks.FIRE;
        fire.TI_SetFireInfo(LEAVES.getBlock(), 30, 60);
        fire.TI_SetFireInfo(LOG.getBlock(), 5, 5);
        fire.TI_SetFireInfo(STRIPPED_LOG.getBlock(), 5, 5);
        fire.TI_SetFireInfo(WOOD.getBlock(), 5, 5);
        fire.TI_SetFireInfo(STRIPPED_WOOD.getBlock(), 5, 5);
        fire.TI_SetFireInfo(PLANKS.getBlock(), 5, 20);
        fire.TI_SetFireInfo(STAIRS.getBlock(), 5, 20);
        fire.TI_SetFireInfo(SLAB.getBlock(), 5, 20);
        fire.TI_SetFireInfo(FENCE.getBlock(), 5, 20);
        fire.TI_SetFireInfo(FENCE_GATE.getBlock(), 5, 20);
    }

    @OnlyIn(Dist.CLIENT)
    public void initRenderLayers() {
        if (!isEnabled()) {
            return;
        }

        RenderTypeLookup.setRenderLayer(SAPLING.getBlock(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(SAPLING.getPotted(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(DOOR.getBlock(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(TRAPDOOR.getBlock(), RenderType.getCutout());
        RenderTypeLookup.setRenderLayer(LEAVES.getBlock(), RenderType.getCutoutMipped());
    }
}
