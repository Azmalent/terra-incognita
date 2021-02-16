package azmalent.terraincognita.common.init;

import azmalent.cuneiform.lib.registry.BlockEntry;
import azmalent.cuneiform.lib.registry.BlockRegistryHelper;
import azmalent.cuneiform.lib.registry.BlockRenderType;
import azmalent.terraincognita.TIConfig.Flora;
import azmalent.terraincognita.TIConfig.Misc;
import azmalent.terraincognita.TIConfig.Tools;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.block.*;
import azmalent.terraincognita.common.block.blocksets.ModWoodType;
import azmalent.terraincognita.common.block.blocksets.PottablePlantEntry;
import azmalent.terraincognita.common.block.plants.*;
import azmalent.terraincognita.common.block.trees.AppleBlock;
import azmalent.terraincognita.common.block.trees.HazelnutBlock;
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
import net.minecraft.potion.Effects;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.function.Function;

import static azmalent.terraincognita.TIConfig.Trees;
import static azmalent.terraincognita.common.block.blocksets.PottablePlantEntry.*;

@SuppressWarnings({"unused", "ConstantConditions"})
public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TerraIncognita.MODID);
    public static final BlockRegistryHelper HELPER = new BlockRegistryHelper(BLOCKS, ModItems.ITEMS, TerraIncognita.TAB);

    public static final List<PottablePlantEntry> FLOWERS;
    public static final PottablePlantEntry DANDELION_PUFF = create("dandelion_puff", () -> new FlowerBlock(Effects.SATURATION, 6, Properties.from(Blocks.AZURE_BLUET)), DandelionPuffItem::new, Flora.dandelionPuff);
    public static final PottablePlantEntry CHICORY        = createFlower("chicory", Effects.SPEED, 8, Blocks.BLUE_ORCHID, Flora.fieldFlowers);
    public static final PottablePlantEntry YARROW         = createFlower("yarrow", Effects.NIGHT_VISION, 5, Blocks.WHITE_TULIP, Flora.fieldFlowers);
    public static final PottablePlantEntry DAFFODIL       = createFlower("daffodil", Effects.BLINDNESS, 8, Blocks.WHITE_TULIP, Flora.fieldFlowers);
    public static final PottablePlantEntry BLUEBELL       = createFlower("bluebell", Effects.REGENERATION, 6, Blocks.CORNFLOWER, Flora.forestFlowers);
    public static final PottablePlantEntry YELLOW_PRIMROSE = createFlower("yellow_primrose", Effects.STRENGTH, 6, Blocks.DANDELION, Flora.forestFlowers);
    public static final PottablePlantEntry PINK_PRIMROSE   = createFlower("pink_primrose", Effects.STRENGTH, 6, Blocks.PINK_TULIP, Flora.forestFlowers);
    public static final PottablePlantEntry PURPLE_PRIMROSE = createFlower("purple_primrose", Effects.STRENGTH, 6, Blocks.ALLIUM, Flora.forestFlowers);
    public static final PottablePlantEntry FOXGLOVE       = createFlower("foxglove", Effects.POISON, 12, Blocks.PINK_TULIP, Flora.forestFlowers);
    public static final PottablePlantEntry MARIGOLD       = create("marigold", MarigoldBlock::new, Flora.savannaFlowers);
    public static final PottablePlantEntry BLUE_LUPINE    = createFlower("blue_lupin", Effects.RESISTANCE, 6, Blocks.CORNFLOWER, Flora.savannaFlowers);
    public static final PottablePlantEntry RED_SNAPDRAGON     = createFlower("red_snapdragon", Effects.RESISTANCE, 6, Blocks.POPPY, Flora.savannaFlowers);
    public static final PottablePlantEntry YELLOW_SNAPDRAGON  = createFlower("yellow_snapdragon", Effects.RESISTANCE, 6, Blocks.DANDELION, Flora.savannaFlowers);
    public static final PottablePlantEntry MAGENTA_SNAPDRAGON = createFlower("magenta_snapdragon", Effects.RESISTANCE, 6, Blocks.ALLIUM, Flora.savannaFlowers);
    public static final PottablePlantEntry EDELWEISS      = create("edelweiss", () -> new AlpineFlowerBlock(Effects.HASTE, 10, Properties.from(Blocks.AZURE_BLUET)), Flora.alpineFlowers);
    public static final PottablePlantEntry SAXIFRAGE      = create("rockfoil", SaxifrageBlock::new, Flora.alpineFlowers);
    public static final PottablePlantEntry ALPINE_PINK    = create("alpine_pink", () -> new AlpineFlowerBlock(Effects.SLOWNESS, 8, Properties.from(Blocks.PINK_TULIP)), Flora.alpineFlowers);
    public static final PottablePlantEntry GENTIAN        = create("gentian", () -> new AlpineFlowerBlock(Effects.WEAKNESS, 9, Properties.from(Blocks.CORNFLOWER)), Flora.alpineFlowers);
    public static final PottablePlantEntry FORGET_ME_NOT  = createFlower("forget_me_not", Effects.SLOWNESS, 8, Blocks.BLUE_ORCHID, Flora.swampFlowers);
    public static final PottablePlantEntry GLOBEFLOWER    = createFlower("globeflower", Effects.RESISTANCE, 6, Blocks.ORANGE_TULIP, Flora.swampFlowers);
    public static final PottablePlantEntry BLUE_IRIS      = createFlower("blue_iris", Effects.INVISIBILITY, 8, Blocks.CORNFLOWER, Flora.jungleFlowers);
    public static final PottablePlantEntry PURPLE_IRIS    = createFlower("purple_iris", Effects.INVISIBILITY, 8, Blocks.ALLIUM, Flora.jungleFlowers);
    public static final PottablePlantEntry BLACK_IRIS     = createFlower("black_iris", Effects.INVISIBILITY, 8, Blocks.ALLIUM, Flora.jungleFlowers);
    public static final PottablePlantEntry DWARF_FIREWEED = createFlower("fireweed", Effects.SPEED, 8, Blocks.WITHER_ROSE, Flora.arcticFlowers);
    public static final PottablePlantEntry ARCTIC_POPPY   = createFlower("arctic_poppy", Effects.NIGHT_VISION, 5, Blocks.DANDELION, Flora.arcticFlowers);

    public static final PottablePlantEntry CATTAIL            = createTallWaterloggable("cattail", Flora.swampFlowers);
    public static final PottablePlantEntry WATER_FLAG         = createTallWaterloggable("water_flag", Flora.swampFlowers);
    public static final PottablePlantEntry TALL_FIREWEED      = createTall("tall_fireweed", Flora.arcticFlowers);
    public static final PottablePlantEntry WHITE_RHODODENDRON = createTall("white_rhododendron", Flora.arcticFlowers);

    public static final List<BlockEntry> LOTUSES;
    public static final BlockEntry PINK_LOTUS   = makeLotus("pink_lotus");
    public static final BlockEntry WHITE_LOTUS  = makeLotus("white_lotus");
    public static final BlockEntry YELLOW_LOTUS = makeLotus("yellow_lotus");
    public static final BlockEntry SMALL_LILY_PAD = HELPER.newBuilder("small_lilypad", SmallLilypadBlock::new).withBlockItem(SmallLilypadItem::new).withRenderType(BlockRenderType.CUTOUT).buildIf(Flora.smallLilypad);

    public static final PottablePlantEntry REEDS = create("reeds", ReedsBlock::new, Flora.reeds);
    public static final BlockEntry ROOTS = HELPER.newBuilder("roots", RootsBlock::new).withRenderType(BlockRenderType.CUTOUT).buildIf(Flora.roots);
    public static final BlockEntry HANGING_MOSS = HELPER.newBuilder("hanging_moss", HangingMossBlock::new).withRenderType(BlockRenderType.CUTOUT).buildIf(Flora.hangingMoss);

    public static final BlockEntry APPLE = HELPER.newBuilder("apple", AppleBlock::new).withRenderType(BlockRenderType.CUTOUT).withoutItemForm().buildIf(Trees.apple);
    public static final BlockEntry HAZELNUT = HELPER.newBuilder("hazelnut", HazelnutBlock::new).withoutItemForm().withRenderType(BlockRenderType.CUTOUT).buildIf(Trees.hazel);

    public static final BlockEntry PEAT = HELPER.newBuilder("peat", PeatBlock::new).buildIf(Misc.peat);
    public static final BlockEntry TILLED_PEAT = HELPER.newBuilder("tilled_peat", TilledPeatBlock::new).buildIf(Misc.peat);
    public static final BlockEntry MOSSY_GRAVEL = HELPER.newBuilder("mossy_gravel", () -> new GravelBlock(Properties.from(Blocks.GRAVEL))).build();

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
            fire.TI_setFireInfo(flower.getBlock(), 60, 100);
        }

        for (ModWoodType woodType : ModWoodTypes.VALUES) {
            woodType.initFlammability();
        }

        if (Flora.reeds.get()) {
            assert WICKER_MAT != null;
            fire.TI_setFireInfo(WICKER_MAT.getBlock(), 60, 20);
        }
    }
}
