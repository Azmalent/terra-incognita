package azmalent.terraincognita.common.init;

import azmalent.cuneiform.lib.config.options.BooleanOption;
import azmalent.cuneiform.lib.registry.BlockEntry;
import azmalent.cuneiform.lib.registry.BlockRegistryHelper;
import azmalent.cuneiform.lib.registry.BlockRenderType;
import azmalent.terraincognita.TIConfig.Flora;
import azmalent.terraincognita.TIConfig.Misc;
import azmalent.terraincognita.TIConfig.Tools;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.block.*;
import azmalent.terraincognita.common.block.plants.*;
import azmalent.terraincognita.common.block.blocksets.PottablePlantEntry;
import azmalent.terraincognita.common.block.blocksets.ModWoodType;
import azmalent.terraincognita.common.item.block.BasketItem;
import azmalent.terraincognita.common.item.block.CaltropsItem;
import azmalent.terraincognita.common.item.block.DandelionPuffItem;
import azmalent.terraincognita.common.item.block.SmallLilypadItem;
import azmalent.terraincognita.mixin.accessor.FireBlockAccessor;
import azmalent.terraincognita.util.CollectionUtil;
import com.google.common.collect.Maps;
import net.minecraft.block.*;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.item.*;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings({"unused", "ConstantConditions"})
public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TerraIncognita.MODID);
    public static final BlockRegistryHelper HELPER = new BlockRegistryHelper(BLOCKS, ModItems.ITEMS, TerraIncognita.TAB);

        public static final List<PottablePlantEntry> FLOWERS;
    public static final PottablePlantEntry DANDELION_PUFF = makePottablePlant("dandelion_puff", () -> new FlowerBlock(Effects.SATURATION, 6, Properties.from(Blocks.AZURE_BLUET)), DandelionPuffItem::new, Flora.dandelionPuff);
    public static final PottablePlantEntry CHICORY        = makeFlower("chicory", Effects.SPEED, 160, Blocks.BLUE_ORCHID, Flora.fieldFlowers);
    public static final PottablePlantEntry YARROW         = makeFlower("yarrow", Effects.NIGHT_VISION, 100, Blocks.WHITE_TULIP, Flora.fieldFlowers);
    public static final PottablePlantEntry DAFFODIL       = makeFlower("daffodil", Effects.BLINDNESS, 160, Blocks.WHITE_TULIP, Flora.fieldFlowers);
    public static final PottablePlantEntry BLUEBELL       = makeFlower("bluebell", Effects.REGENERATION, 120, Blocks.CORNFLOWER, Flora.forestFlowers);
    public static final PottablePlantEntry YELLOW_PRIMROSE = makeFlower("yellow_primrose", Effects.STRENGTH, 120, Blocks.DANDELION, Flora.forestFlowers);
    public static final PottablePlantEntry PINK_PRIMROSE   = makeFlower("pink_primrose", Effects.STRENGTH, 120, Blocks.PINK_TULIP, Flora.forestFlowers);
    public static final PottablePlantEntry PURPLE_PRIMROSE = makeFlower("purple_primrose", Effects.STRENGTH, 120, Blocks.ALLIUM, Flora.forestFlowers);
    public static final PottablePlantEntry FOXGLOVE       = makeFlower("foxglove", Effects.POISON, 240, Blocks.PINK_TULIP, Flora.forestFlowers);
    public static final PottablePlantEntry MARIGOLD       = makePottablePlant("marigold", MarigoldBlock::new, Flora.savannaFlowers);
    public static final PottablePlantEntry BLUE_LUPINE    = makeFlower("blue_lupin", Effects.RESISTANCE, 120, Blocks.CORNFLOWER, Flora.savannaFlowers);
    public static final PottablePlantEntry RED_SNAPDRAGON     = makeFlower("red_snapdragon", Effects.RESISTANCE, 120, Blocks.POPPY, Flora.savannaFlowers);
    public static final PottablePlantEntry YELLOW_SNAPDRAGON  = makeFlower("yellow_snapdragon", Effects.RESISTANCE, 120, Blocks.DANDELION, Flora.savannaFlowers);
    public static final PottablePlantEntry MAGENTA_SNAPDRAGON = makeFlower("magenta_snapdragon", Effects.RESISTANCE, 120, Blocks.ALLIUM, Flora.savannaFlowers);
    public static final PottablePlantEntry EDELWEISS      = makePottablePlant("edelweiss", () -> new AlpineFlowerBlock(Effects.HASTE, 200, Properties.from(Blocks.AZURE_BLUET)), Flora.alpineFlowers);
    public static final PottablePlantEntry SAXIFRAGE      = makePottablePlant("rockfoil", SaxifrageBlock::new, Flora.alpineFlowers);
    public static final PottablePlantEntry ALPINE_PINK    = makePottablePlant("alpine_pink", () -> new AlpineFlowerBlock(Effects.SLOWNESS, 160, Properties.from(Blocks.PINK_TULIP)), Flora.alpineFlowers);
    public static final PottablePlantEntry GENTIAN        = makePottablePlant("gentian", () -> new AlpineFlowerBlock(Effects.WEAKNESS, 180, Properties.from(Blocks.CORNFLOWER)), Flora.alpineFlowers);
    public static final PottablePlantEntry FORGET_ME_NOT  = makeFlower("forget_me_not", Effects.SLOWNESS, 160, Blocks.BLUE_ORCHID, Flora.swampFlowers);
    public static final PottablePlantEntry GLOBEFLOWER    = makeFlower("globeflower", Effects.RESISTANCE, 120, Blocks.ORANGE_TULIP, Flora.swampFlowers);
    public static final PottablePlantEntry BLUE_IRIS      = makeFlower("blue_iris", Effects.INVISIBILITY, 160, Blocks.CORNFLOWER, Flora.jungleFlowers);
    public static final PottablePlantEntry PURPLE_IRIS    = makeFlower("purple_iris", Effects.INVISIBILITY, 160, Blocks.ALLIUM, Flora.jungleFlowers);
    public static final PottablePlantEntry BLACK_IRIS     = makeFlower("black_iris", Effects.INVISIBILITY, 160, Blocks.ALLIUM, Flora.jungleFlowers);
    public static final PottablePlantEntry DWARF_FIREWEED = makeFlower("fireweed", Effects.SPEED, 160, Blocks.WITHER_ROSE, Flora.arcticFlowers);
    public static final PottablePlantEntry ARCTIC_POPPY   = makeFlower("arctic_poppy", Effects.NIGHT_VISION, 100, Blocks.DANDELION, Flora.arcticFlowers);

    public static final PottablePlantEntry CATTAIL            = makeWaterloggableTallPlant("cattail", Flora.swampFlowers);
    public static final PottablePlantEntry WATER_FLAG         = makeWaterloggableTallPlant("water_flag", Flora.swampFlowers);
    public static final PottablePlantEntry TALL_FIREWEED      = makeTallPlant("tall_fireweed", Flora.arcticFlowers);
    public static final PottablePlantEntry WHITE_RHODODENDRON = makeTallPlant("white_rhododendron", Flora.arcticFlowers);

    public static final List<BlockEntry> LOTUSES;
    public static final BlockEntry PINK_LOTUS   = makeLotus("pink_lotus");
    public static final BlockEntry WHITE_LOTUS  = makeLotus("white_lotus");
    public static final BlockEntry YELLOW_LOTUS = makeLotus("yellow_lotus");
    public static final BlockEntry SMALL_LILY_PAD = HELPER.newBuilder("small_lilypad", SmallLilypadBlock::new).withBlockItem(SmallLilypadItem::new).withRenderType(BlockRenderType.CUTOUT).buildIf(Flora.smallLilypad);

    public static final PottablePlantEntry REEDS = makePottablePlant("reeds", ReedsBlock::new, Flora.reeds);
    public static final BlockEntry ROOTS = HELPER.newBuilder("roots", RootsBlock::new).withRenderType(BlockRenderType.CUTOUT).buildIf(Flora.roots);
    public static final BlockEntry HANGING_MOSS = HELPER.newBuilder("hanging_moss", HangingMossBlock::new).withRenderType(BlockRenderType.CUTOUT).buildIf(Flora.hangingMoss);

    public static final BlockEntry PEAT = HELPER.newBuilder("peat", PeatBlock::new).buildIf(Misc.peat);
    public static final BlockEntry TILLED_PEAT = HELPER.newBuilder("tilled_peat", TilledPeatBlock::new).buildIf(Misc.peat);

    public static final BlockEntry CALTROPS = HELPER.newBuilder("caltrops", CaltropsBlock::new).withBlockItem(CaltropsItem::new).withRenderType(BlockRenderType.CUTOUT).buildIf(Tools.caltrops);
    public static final BlockEntry BASKET = HELPER.newBuilder("basket", BasketBlock::new).withBlockItem(BasketItem::new).buildIf(Tools.basket);
    public static final BlockEntry WICKER_MAT = HELPER.newBuilder("wicker_mat", WickerMatBlock::new).buildIf(Flora.reeds);

    static {
        ModWoodTypes.init();

        FLOWERS = CollectionUtil.filterNotNull(
            DANDELION_PUFF, CHICORY, YARROW, DAFFODIL, BLUEBELL, YELLOW_PRIMROSE, PINK_PRIMROSE, PURPLE_PRIMROSE, FOXGLOVE,
            MARIGOLD, BLUE_LUPINE, RED_SNAPDRAGON, YELLOW_SNAPDRAGON, MAGENTA_SNAPDRAGON, EDELWEISS, ALPINE_PINK, SAXIFRAGE, 
            GENTIAN, FORGET_ME_NOT, BLUE_IRIS, PURPLE_IRIS, BLACK_IRIS, DWARF_FIREWEED, ARCTIC_POPPY
        );

        LOTUSES = CollectionUtil.filterNotNull(WHITE_LOTUS, PINK_LOTUS, YELLOW_LOTUS);
    }

    public static PottablePlantEntry makePottablePlant(String id, Supplier<Block> constructor, BooleanOption condition) {
        return makePottablePlant(id, constructor, null, condition);
    }

    public static PottablePlantEntry makePottablePlant(String id, Supplier<Block> constructor, Function<Block, BlockItem> blockItemConstructor, BooleanOption condition) {
        if (!condition.get()) {
            return null;
        }

        BlockEntry.Builder builder = HELPER.newBuilder(id, constructor).withBlockItem(blockItemConstructor).withRenderType(BlockRenderType.CUTOUT);
        return new PottablePlantEntry(id, builder.build());
    }

    private static PottablePlantEntry makeFlower(String id, Effect effect, int duration, Block propertyBase, BooleanOption condition) {
        return makePottablePlant(id, () -> new FlowerBlock(effect, duration, Properties.from(propertyBase)), condition);
    }

    private static PottablePlantEntry makeTallPlant(String id, BooleanOption condition) {
        Function<Block, BlockItem> blockItemConstructor = block -> new TallBlockItem(block, new Item.Properties().group(TerraIncognita.TAB));
        BlockEntry plant = HELPER.newBuilder(id, () -> new TallFlowerBlock(Properties.from(Blocks.ROSE_BUSH))).withBlockItem(blockItemConstructor).withRenderType(BlockRenderType.CUTOUT).buildIf(condition);
        return new PottablePlantEntry(id, plant);
    }

    private static PottablePlantEntry makeWaterloggableTallPlant(String id, BooleanOption condition) {
        BlockEntry plant = HELPER.newBuilder(id, WaterloggableTallFlowerBlock::new).withBlockItem(block -> new TallBlockItem(block, new Item.Properties().group(TerraIncognita.TAB))).withRenderType(BlockRenderType.CUTOUT).buildIf(condition);
        return new PottablePlantEntry(id, plant);
    }

    private static BlockEntry makeLotus(String id) {
        Function<Block, BlockItem> blockItemConstructor = block -> new LilyPadItem(block, new Item.Properties().group(TerraIncognita.TAB));
        return HELPER.newBuilder(id, () -> new LilyPadBlock(Properties.from(Blocks.LILY_PAD))).withBlockItem(blockItemConstructor).withRenderType(BlockRenderType.CUTOUT).buildIf(Flora.lotus);
    }

    public static void initToolInteractions() {
        AxeItem.BLOCK_STRIPPING_MAP = Maps.newHashMap(AxeItem.BLOCK_STRIPPING_MAP);
        for (ModWoodType woodType : ModWoodTypes.VALUES) {
            AxeItem.BLOCK_STRIPPING_MAP.put(woodType.LOG.getBlock(), woodType.STRIPPED_LOG.getBlock());
            AxeItem.BLOCK_STRIPPING_MAP.put(woodType.WOOD.getBlock(), woodType.STRIPPED_WOOD.getBlock());
        }

        HoeItem.HOE_LOOKUP = Maps.newHashMap(HoeItem.HOE_LOOKUP);
        if (Misc.peat.get()) {
            HoeItem.HOE_LOOKUP.put(ModBlocks.PEAT.getBlock(), ModBlocks.TILLED_PEAT.getBlock().getDefaultState());
        }
    }

    public static void initFlammability() {
        FireBlockAccessor fire = (FireBlockAccessor) Blocks.FIRE;
        for (PottablePlantEntry flower : FLOWERS) {
            fire.TI_SetFireInfo(flower.getBlock(), 60, 100);
        }

        for (ModWoodType woodType : ModWoodTypes.VALUES) {
            woodType.initFlammability();
        }

        if (Flora.reeds.get()) {
            assert WICKER_MAT != null;
            fire.TI_SetFireInfo(WICKER_MAT.getBlock(), 60, 20);
        }
    }
}
