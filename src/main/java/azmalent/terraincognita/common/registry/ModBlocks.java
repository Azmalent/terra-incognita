package azmalent.terraincognita.common.registry;

import azmalent.cuneiform.lib.registry.BlockEntry;
import azmalent.cuneiform.lib.registry.BlockRegistryHelper;
import azmalent.cuneiform.lib.util.DataUtil;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.block.*;
import azmalent.terraincognita.common.block.plants.*;
import azmalent.terraincognita.common.block.plants.ModFlowerBlock.StewEffect;
import azmalent.terraincognita.common.block.trees.AppleBlock;
import azmalent.terraincognita.common.block.trees.HazelnutBlock;
import azmalent.terraincognita.common.block.woodtypes.ModWoodType;
import azmalent.terraincognita.common.item.block.BasketItem;
import azmalent.terraincognita.common.item.block.CaltropsItem;
import azmalent.terraincognita.common.item.block.DandelionPuffItem;
import azmalent.terraincognita.common.item.block.SmallLilypadItem;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings({"unused"})
public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TerraIncognita.MODID);
    public static final BlockRegistryHelper HELPER = new BlockRegistryHelper(BLOCKS, ModItems.ITEMS, ItemGroup.DECORATIONS);

    //Small flowers
    public static final List<PottablePlantEntry> FLOWERS;
    public static final PottablePlantEntry DANDELION_PUFF = createPlant("dandelion_puff", () -> new ModFlowerBlock(StewEffect.SATURATION), DandelionPuffItem::new);
    public static final PottablePlantEntry CHICORY        = createPlant("chicory", () -> new ModFlowerBlock(StewEffect.SPEED));
    public static final PottablePlantEntry YARROW         = createPlant("yarrow", () -> new ModFlowerBlock(StewEffect.NIGHT_VISION));
    public static final PottablePlantEntry DAFFODIL       = createPlant("daffodil", () -> new ModFlowerBlock(StewEffect.BLINDNESS));
    public static final PottablePlantEntry YELLOW_PRIMROSE = createPlant("yellow_primrose", () -> new ModFlowerBlock(StewEffect.STRENGTH));
    public static final PottablePlantEntry PINK_PRIMROSE   = createPlant("pink_primrose", () -> new ModFlowerBlock(StewEffect.STRENGTH));
    public static final PottablePlantEntry PURPLE_PRIMROSE = createPlant("purple_primrose", () -> new ModFlowerBlock(StewEffect.STRENGTH));
    public static final PottablePlantEntry FOXGLOVE       = createPlant("foxglove", () -> new ModFlowerBlock(StewEffect.POISON));
    public static final PottablePlantEntry WILD_GARLIC    = createPlant("wild_garlic", () -> new ModFlowerBlock(StewEffect.SATURATION));
    public static final PottablePlantEntry MARIGOLD       = createPlant("marigold", MarigoldBlock::new);
    public static final PottablePlantEntry BLUE_LUPINE    = createPlant("blue_lupine", () -> new ModFlowerBlock(StewEffect.HUNGER));
    public static final PottablePlantEntry SNAPDRAGON     = createPlant("snapdragon", () -> new ModFlowerBlock(StewEffect.HASTE));
    public static final PottablePlantEntry GLADIOLUS      = createPlant("gladiolus", () -> new ModFlowerBlock(StewEffect.STRENGTH));
    public static final PottablePlantEntry GERANIUM       = createPlant("geranium", () -> new ModFlowerBlock(StewEffect.RESISTANCE));
    public static final PottablePlantEntry SAXIFRAGE      = createPlant("saxifrage", SaxifrageBlock::new);
    public static final PottablePlantEntry ALPINE_PINK    = createPlant("alpine_pink", () -> new AlpineFlowerBlock(StewEffect.SLOWNESS));
    public static final PottablePlantEntry GENTIAN        = createPlant("gentian", () -> new AlpineFlowerBlock(StewEffect.WEAKNESS));
    public static final PottablePlantEntry EDELWEISS      = createPlant("edelweiss", () -> new AlpineFlowerBlock(StewEffect.HASTE));
    public static final PottablePlantEntry FORGET_ME_NOT  = createPlant("forget_me_not", () -> new ModFlowerBlock(StewEffect.SLOWNESS));
    public static final PottablePlantEntry GLOBEFLOWER    = createPlant("globeflower", () -> new ModFlowerBlock(StewEffect.RESISTANCE));
    public static final PottablePlantEntry BLUE_IRIS      = createPlant("blue_iris", () -> new ModFlowerBlock(StewEffect.INVISIBILITY));
    public static final PottablePlantEntry PURPLE_IRIS    = createPlant("purple_iris", () -> new ModFlowerBlock(StewEffect.INVISIBILITY));
    public static final PottablePlantEntry BLACK_IRIS     = createPlant("black_iris", () -> new ModFlowerBlock(StewEffect.INVISIBILITY));
    public static final PottablePlantEntry DWARF_FIREWEED = createPlant("dwarf_fireweed", () -> new ModFlowerBlock(StewEffect.SPEED));
    public static final PottablePlantEntry ARCTIC_POPPY   = createPlant("arctic_poppy", () -> new ModFlowerBlock(StewEffect.NIGHT_VISION));
    public static final PottablePlantEntry WHITE_DRYAD    = createPlant("white_dryad", () -> new ModFlowerBlock(StewEffect.SLOWNESS));

    //Tall flowers
    public static final PottablePlantEntry WATER_FLAG         = createTallWaterloggablePlant("water_flag");
    public static final PottablePlantEntry FIREWEED = createTallPlant("tall_fireweed");
    public static final PottablePlantEntry WHITE_RHODODENDRON = createTallPlant("white_rhododendron");
    public static final PottablePlantEntry OLEANDER = createTallPlant("oleander");
    public static final PottablePlantEntry SAGE     = createTallPlant("sage");

    //Sweet peas
    public static final List<BlockEntry> SWEET_PEAS;
    public static final BlockEntry WHITE_SWEET_PEAS   = createSweetPeas("white");
    public static final BlockEntry PINK_SWEET_PEAS    = createSweetPeas("pink");
    public static final BlockEntry RED_SWEET_PEAS     = createSweetPeas("red");
    public static final BlockEntry MAGENTA_SWEET_PEAS = createSweetPeas("magenta");
    public static final BlockEntry PURPLE_SWEET_PEAS  = createSweetPeas("purple");
    public static final BlockEntry BLUE_SWEET_PEAS    = createSweetPeas("blue");
    public static final BlockEntry LIGHT_BLUE_SWEET_PEAS  = createSweetPeas("light_blue");

    //Lily pads
    public static final List<BlockEntry> LOTUSES;
    public static final BlockEntry PINK_LOTUS   = createLotus("pink");
    public static final BlockEntry WHITE_LOTUS  = createLotus("white");
    public static final BlockEntry YELLOW_LOTUS = createLotus("yellow");
    public static final BlockEntry SMALL_LILY_PAD = HELPER.newBuilder("small_lilypad", SmallLilypadBlock::new).withBlockItem(SmallLilypadItem::new).cutoutRender().build();

    //Fruits
    public static final BlockEntry APPLE = HELPER.newBuilder("apple", AppleBlock::new).withoutItemForm().cutoutRender().build();
    public static final BlockEntry HAZELNUT = HELPER.newBuilder("hazelnut", HazelnutBlock::new).withoutItemForm().cutoutRender().build();

    //Other vegetation
    public static final PottablePlantEntry CATTAIL = createTallWaterloggablePlant("cattail");
    public static final PottablePlantEntry REEDS = createPlant("reeds", ReedsBlock::new);

    public static final BlockEntry SOUR_BERRY_SPROUTS = HELPER.newBuilder("sour_berry_sprouts", SourBerrySproutBlock::new).withBlockItem(LilyPadItem::new, ItemGroup.MISC).cutoutRender().build();
    public static final BlockEntry SOUR_BERRY_BUSH = HELPER.newBuilder("sour_berry_bush", SourBerryBushBlock::new).withoutItemForm().cutoutRender().build();

    public static final BlockEntry CARIBOU_MOSS_WALL = HELPER.newBuilder("caribou_moss_wall", CaribouMossWallBlock::new).withoutItemForm().cutoutRender().build();
    public static final PottablePlantEntry CARIBOU_MOSS = createPlant("caribou_moss", CaribouMossBlock::new, block -> new WallOrFloorItem(block, CARIBOU_MOSS_WALL.getBlock(), new Item.Properties().group(ItemGroup.DECORATIONS)));

    public static final BlockEntry ROOTS = HELPER.newBuilder("roots", RootsBlock::new).cutoutRender().build();
    public static final BlockEntry HANGING_MOSS = HELPER.newBuilder("hanging_moss", HangingMossBlock::new).cutoutRender().build();

    public static final BlockEntry SMOOTH_CACTUS = HELPER.newBuilder("smooth_cactus", SmoothCactusBlock::new).cutoutRender().build();
    public static final PottablePlantEntry CACTUS_FLOWER = createPlant("cactus_flower", CactusFlowerBlock::new);
    public static final PottablePlantEntry SMALL_CACTUS = createPlant("small_cactus", () -> new DeadBushBlock(Properties.from(Blocks.TALL_GRASS)));

    //Other blocks
    public static final BlockEntry PEAT = HELPER.newBuilder("peat", PeatBlock::new).withItemGroup(ItemGroup.BUILDING_BLOCKS).build();
    public static final BlockEntry TILLED_PEAT = HELPER.newBuilder("tilled_peat", PeatFarmlandBlock::new).build();
    public static final BlockEntry FLOWERING_GRASS = HELPER.newBuilder("flowering_grass", GrassBlock::new, Properties.from(Blocks.GRASS_BLOCK)).cutoutMippedRender().build();
    public static final BlockEntry MOSSY_GRAVEL = HELPER.newBuilder("mossy_gravel", GravelBlock::new, Properties.from(Blocks.GRAVEL)).build();
    public static final BlockEntry CALTROPS = HELPER.newBuilder("caltrops", CaltropsBlock::new).withBlockItem(CaltropsItem::new).cutoutRender().build();
    public static final BlockEntry BASKET = HELPER.newBuilder("basket", BasketBlock::new).withBlockItem(BasketItem::new).build();
    public static final BlockEntry WICKER_MAT = HELPER.newBuilder("wicker_mat", WickerMatBlock::new).build();
    public static final BlockEntry WICKER_LANTERN = HELPER.newBuilder("wicker_lantern", WickerLanternBlock::new).cutoutRender().build();

    static {
        ModWoodTypes.init();

        FLOWERS = Lists.newArrayList(
            DANDELION_PUFF, CHICORY, YARROW, DAFFODIL, YELLOW_PRIMROSE, PINK_PRIMROSE, PURPLE_PRIMROSE, FOXGLOVE, WILD_GARLIC,
            MARIGOLD, BLUE_LUPINE, GLADIOLUS, SNAPDRAGON, GERANIUM, EDELWEISS, ALPINE_PINK, SAXIFRAGE,
            GENTIAN, FORGET_ME_NOT, BLUE_IRIS, PURPLE_IRIS, BLACK_IRIS, DWARF_FIREWEED, ARCTIC_POPPY, WHITE_DRYAD
        );

        LOTUSES = Lists.newArrayList(WHITE_LOTUS, PINK_LOTUS, YELLOW_LOTUS);

        SWEET_PEAS = Lists.newArrayList(
            WHITE_SWEET_PEAS, PINK_SWEET_PEAS, RED_SWEET_PEAS, MAGENTA_SWEET_PEAS, PURPLE_SWEET_PEAS,
            BLUE_SWEET_PEAS, LIGHT_BLUE_SWEET_PEAS
        );
    }

    public static PottablePlantEntry createPlant(String id, Supplier<Block> constructor) {
        return createPlant(id, constructor, null);
    }

    public static PottablePlantEntry createPlant(String id, Supplier<Block> constructor, Function<Block, BlockItem> blockItemConstructor) {
        BlockEntry.Builder builder = ModBlocks.HELPER.newBuilder(id, constructor).withBlockItem(blockItemConstructor).cutoutRender();
        return new PottablePlantEntry(id, builder.build());
    }

    public static PottablePlantEntry createTallPlant(String id) {
        return createPlant(id, () -> new TallFlowerBlock(Block.Properties.from(Blocks.ROSE_BUSH)), block -> new TallBlockItem(block, new Item.Properties().group(ItemGroup.DECORATIONS)));
    }

    public static PottablePlantEntry createTallWaterloggablePlant(String id) {
        return createPlant(id, WaterloggableTallFlowerBlock::new, block -> new TallBlockItem(block, new Item.Properties().group(ItemGroup.DECORATIONS)));
    }

    private static BlockEntry createLotus(String color) {
        return HELPER.newBuilder(color + "_lotus", LilyPadBlock::new, Properties.from(Blocks.LILY_PAD)).withBlockItem(LilyPadItem::new, ItemGroup.DECORATIONS).cutoutRender().build();
    }

    private static BlockEntry createSweetPeas(String color) {
        return HELPER.newBuilder(color + "_sweet_peas", SweetPeasBlock::new).cutoutRender().build();
    }

    public static void initToolInteractions() {
        AxeItem.BLOCK_STRIPPING_MAP = Maps.newHashMap(AxeItem.BLOCK_STRIPPING_MAP);
        for (ModWoodType woodType : ModWoodTypes.VALUES) {
            AxeItem.BLOCK_STRIPPING_MAP.put(woodType.LOG.getBlock(), woodType.STRIPPED_LOG.getBlock());
            AxeItem.BLOCK_STRIPPING_MAP.put(woodType.WOOD.getBlock(), woodType.STRIPPED_WOOD.getBlock());
        }

        HoeItem.HOE_LOOKUP = Maps.newHashMap(HoeItem.HOE_LOOKUP);
        HoeItem.HOE_LOOKUP.put(PEAT.getBlock(), TILLED_PEAT.getBlock().getDefaultState());
        HoeItem.HOE_LOOKUP.put(FLOWERING_GRASS.getBlock(), Blocks.FARMLAND.getDefaultState());

        ShovelItem.SHOVEL_LOOKUP = Maps.newHashMap(ShovelItem.SHOVEL_LOOKUP);
        ShovelItem.SHOVEL_LOOKUP.put(FLOWERING_GRASS.getBlock(), Blocks.GRASS_PATH.getDefaultState());
    }

    public static void initFlammability() {
        for (PottablePlantEntry flower : FLOWERS) {
            DataUtil.registerFlammable(flower.getBlock(), 60, 100);
        }

        for (BlockEntry sweetPea : SWEET_PEAS) {
            DataUtil.registerFlammable(sweetPea.getBlock(), 15, 100);
        }

        for (ModWoodType woodType : ModWoodTypes.VALUES) {
            woodType.initFlammability();
        }

        DataUtil.registerFlammable(WICKER_MAT.getBlock(), 60, 20);
    }
}
