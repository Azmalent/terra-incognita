package azmalent.terraincognita.common.registry;

import azmalent.cuneiform.lib.registry.BlockEntry;
import azmalent.cuneiform.lib.registry.BlockRegistryHelper;
import azmalent.cuneiform.lib.registry.BlockRenderType;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.block.*;
import azmalent.terraincognita.common.block.blocksets.ModWoodType;
import azmalent.terraincognita.common.block.blocksets.PottablePlantEntry;
import azmalent.terraincognita.common.block.plants.*;
import azmalent.terraincognita.common.block.trees.AppleBlock;
import azmalent.terraincognita.common.block.trees.HazelnutBlock;
import azmalent.terraincognita.common.effect.ModStewEffect;
import azmalent.terraincognita.common.item.block.BasketItem;
import azmalent.terraincognita.common.item.block.CaltropsItem;
import azmalent.terraincognita.common.item.block.DandelionPuffItem;
import azmalent.terraincognita.common.item.block.SmallLilypadItem;
import azmalent.terraincognita.mixin.accessor.FireBlockAccessor;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.*;
import net.minecraft.item.*;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.function.Function;

import static azmalent.terraincognita.common.block.blocksets.PottablePlantEntry.*;

@SuppressWarnings({"unused", "ConstantConditions"})
public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TerraIncognita.MODID);
    public static final BlockRegistryHelper HELPER = new BlockRegistryHelper(BLOCKS, ModItems.ITEMS, TerraIncognita.TAB);

    //Small flowers
    public static final List<PottablePlantEntry> FLOWERS;
    public static final PottablePlantEntry DANDELION_PUFF = createPlant("dandelion_puff", () -> new FlowerBlock(ModStewEffect.SATURATION.effect, ModStewEffect.SATURATION.duration, Properties.from(Blocks.POPPY)), DandelionPuffItem::new);
    public static final PottablePlantEntry CHICORY        = createFlower("chicory", ModStewEffect.SPEED);
    public static final PottablePlantEntry YARROW         = createFlower("yarrow", ModStewEffect.NIGHT_VISION);
    public static final PottablePlantEntry DAFFODIL       = createFlower("daffodil", ModStewEffect.BLINDNESS);
    public static final PottablePlantEntry YELLOW_PRIMROSE = createFlower("yellow_primrose", ModStewEffect.STRENGTH);
    public static final PottablePlantEntry PINK_PRIMROSE   = createFlower("pink_primrose", ModStewEffect.STRENGTH);
    public static final PottablePlantEntry PURPLE_PRIMROSE = createFlower("purple_primrose", ModStewEffect.STRENGTH);
    public static final PottablePlantEntry FOXGLOVE       = createFlower("foxglove", ModStewEffect.POISON);
    public static final PottablePlantEntry WILD_GARLIC    = createFlower("wild_garlic", ModStewEffect.SATURATION);
    public static final PottablePlantEntry MARIGOLD       = createPlant("marigold", MarigoldBlock::new);
    public static final PottablePlantEntry BLUE_LUPINE    = createFlower("blue_lupin", ModStewEffect.RESISTANCE);
    public static final PottablePlantEntry RED_SNAPDRAGON     = createFlower("red_snapdragon", ModStewEffect.RESISTANCE);
    public static final PottablePlantEntry YELLOW_SNAPDRAGON  = createFlower("yellow_snapdragon", ModStewEffect.RESISTANCE);
    public static final PottablePlantEntry MAGENTA_SNAPDRAGON = createFlower("magenta_snapdragon", ModStewEffect.RESISTANCE);
    public static final PottablePlantEntry EDELWEISS      = createPlant("edelweiss", () -> new AlpineFlowerBlock(ModStewEffect.HASTE));
    public static final PottablePlantEntry SAXIFRAGE      = createPlant("rockfoil", SaxifrageBlock::new);
    public static final PottablePlantEntry ALPINE_PINK    = createPlant("alpine_pink", () -> new AlpineFlowerBlock(ModStewEffect.SLOWNESS));
    public static final PottablePlantEntry GENTIAN        = createPlant("gentian", () -> new AlpineFlowerBlock(ModStewEffect.WEAKNESS));
    public static final PottablePlantEntry FORGET_ME_NOT  = createFlower("forget_me_not", ModStewEffect.SLOWNESS);
    public static final PottablePlantEntry GLOBEFLOWER    = createFlower("globeflower", ModStewEffect.RESISTANCE);
    public static final PottablePlantEntry BLUE_IRIS      = createFlower("blue_iris", ModStewEffect.INVISIBILITY);
    public static final PottablePlantEntry PURPLE_IRIS    = createFlower("purple_iris", ModStewEffect.INVISIBILITY);
    public static final PottablePlantEntry BLACK_IRIS     = createFlower("black_iris", ModStewEffect.INVISIBILITY);
    public static final PottablePlantEntry DWARF_FIREWEED = createFlower("fireweed", ModStewEffect.SPEED);
    public static final PottablePlantEntry ARCTIC_POPPY   = createFlower("arctic_poppy", ModStewEffect.NIGHT_VISION);
    public static final PottablePlantEntry WHITE_DRYAD    = createFlower("white_dryad", ModStewEffect.SLOWNESS);

    //Tall flowers
    public static final PottablePlantEntry CATTAIL            = createTallWaterloggablePlant("cattail");
    public static final PottablePlantEntry WATER_FLAG         = createTallWaterloggablePlant("water_flag");
    public static final PottablePlantEntry TALL_FIREWEED      = createTallPlant("tall_fireweed");
    public static final PottablePlantEntry WHITE_RHODODENDRON = createTallPlant("white_rhododendron");

    //Lily pads
    public static final List<BlockEntry> LOTUSES;
    public static final BlockEntry PINK_LOTUS   = createLotus("pink_lotus");
    public static final BlockEntry WHITE_LOTUS  = createLotus("white_lotus");
    public static final BlockEntry YELLOW_LOTUS = createLotus("yellow_lotus");
    public static final BlockEntry SMALL_LILY_PAD = HELPER.newBuilder("small_lilypad", SmallLilypadBlock::new).withBlockItem(SmallLilypadItem::new).withRenderType(BlockRenderType.CUTOUT).build();

    //Other vegetation
    public static final PottablePlantEntry REEDS = createPlant("reeds", ReedsBlock::new);
    public static final BlockEntry ROOTS = HELPER.newBuilder("roots", RootsBlock::new).withRenderType(BlockRenderType.CUTOUT).build();
    public static final BlockEntry HANGING_MOSS = HELPER.newBuilder("hanging_moss", HangingMossBlock::new).withRenderType(BlockRenderType.CUTOUT).build();

    //Fruits
    public static final BlockEntry APPLE = HELPER.newBuilder("apple", AppleBlock::new).withRenderType(BlockRenderType.CUTOUT).withoutItemForm().build();
    public static final BlockEntry HAZELNUT = HELPER.newBuilder("hazelnut", HazelnutBlock::new).withoutItemForm().withRenderType(BlockRenderType.CUTOUT).build();

    //Ores and such
    public static final BlockEntry PEAT = HELPER.newBuilder("peat", PeatBlock::new).build();
    public static final BlockEntry TILLED_PEAT = HELPER.newBuilder("tilled_peat", PeatFarmlandBlock::new).build();
    public static final BlockEntry MOSSY_GRAVEL = HELPER.newBuilder("mossy_gravel", () -> new GravelBlock(Properties.from(Blocks.GRAVEL))).build();

    //Other blocks
    public static final BlockEntry CALTROPS = HELPER.newBuilder("caltrops", CaltropsBlock::new).withBlockItem(CaltropsItem::new).withRenderType(BlockRenderType.CUTOUT).build();
    public static final BlockEntry BASKET = HELPER.newBuilder("basket", BasketBlock::new).withBlockItem(BasketItem::new).build();
    public static final BlockEntry WICKER_MAT = HELPER.newBuilder("wicker_mat", WickerMatBlock::new).build();
    public static final BlockEntry WICKER_LANTERN = HELPER.newBuilder("wicker_lantern", WickerLanternBlock::new).withRenderType(BlockRenderType.CUTOUT).build();

    static {
        ModWoodTypes.init();

        FLOWERS = Lists.newArrayList(
            DANDELION_PUFF, CHICORY, YARROW, DAFFODIL, YELLOW_PRIMROSE, PINK_PRIMROSE, PURPLE_PRIMROSE, FOXGLOVE, WILD_GARLIC,
            MARIGOLD, BLUE_LUPINE, RED_SNAPDRAGON, YELLOW_SNAPDRAGON, MAGENTA_SNAPDRAGON, EDELWEISS, ALPINE_PINK, SAXIFRAGE,
            GENTIAN, FORGET_ME_NOT, BLUE_IRIS, PURPLE_IRIS, BLACK_IRIS, DWARF_FIREWEED, ARCTIC_POPPY, WHITE_DRYAD
        );

        LOTUSES = Lists.newArrayList(WHITE_LOTUS, PINK_LOTUS, YELLOW_LOTUS);
    }

    private static BlockEntry createLotus(String id) {
        Function<Block, BlockItem> blockItemConstructor = block -> new LilyPadItem(block, new Item.Properties().group(TerraIncognita.TAB));
        return HELPER.newBuilder(id, () -> new LilyPadBlock(Properties.from(Blocks.LILY_PAD))).withBlockItem(blockItemConstructor).withRenderType(BlockRenderType.CUTOUT).build();
    }

    public static void initToolInteractions() {
        AxeItem.BLOCK_STRIPPING_MAP = Maps.newHashMap(AxeItem.BLOCK_STRIPPING_MAP);
        for (ModWoodType woodType : ModWoodTypes.VALUES) {
            AxeItem.BLOCK_STRIPPING_MAP.put(woodType.LOG.getBlock(), woodType.STRIPPED_LOG.getBlock());
            AxeItem.BLOCK_STRIPPING_MAP.put(woodType.WOOD.getBlock(), woodType.STRIPPED_WOOD.getBlock());
        }

        HoeItem.HOE_LOOKUP = Maps.newHashMap(HoeItem.HOE_LOOKUP);
        HoeItem.HOE_LOOKUP.put(ModBlocks.PEAT.getBlock(), ModBlocks.TILLED_PEAT.getBlock().getDefaultState());
    }

    public static void initFlammability() {
        FireBlockAccessor fire = (FireBlockAccessor) Blocks.FIRE;
        for (PottablePlantEntry flower : FLOWERS) {
            fire.TI_setFireInfo(flower.getBlock(), 60, 100);
        }

        for (ModWoodType woodType : ModWoodTypes.VALUES) {
            woodType.initFlammability();
        }

        fire.TI_setFireInfo(WICKER_MAT.getBlock(), 60, 20);
    }
}
