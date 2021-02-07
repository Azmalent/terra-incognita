package azmalent.terraincognita.common.init;

import azmalent.cuneiform.lib.config.options.BooleanOption;
import azmalent.cuneiform.lib.registry.BlockEntry;
import azmalent.cuneiform.lib.registry.BlockRegistryHelper;
import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.TIConfig.Flora;
import azmalent.terraincognita.TIConfig.Misc;
import azmalent.terraincognita.TIConfig.Tools;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.block.*;
import azmalent.terraincognita.common.block.plants.*;
import azmalent.terraincognita.common.block.trees.AppleTree;
import azmalent.terraincognita.common.init.blocksets.AppleWoodType;
import azmalent.terraincognita.common.init.blocksets.PottablePlantEntry;
import azmalent.terraincognita.common.init.blocksets.TIWoodType;
import azmalent.terraincognita.common.item.block.BasketItem;
import azmalent.terraincognita.common.item.block.CaltropsItem;
import azmalent.terraincognita.common.item.block.DandelionPuffItem;
import azmalent.terraincognita.common.item.block.SmallLilypadItem;
import azmalent.terraincognita.mixin.accessor.FireBlockAccessor;
import azmalent.terraincognita.util.CollectionUtil;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import net.minecraft.block.*;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.item.*;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

@SuppressWarnings({"unused", "ConstantConditions"})
public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TerraIncognita.MODID);
    public static final BlockRegistryHelper HELPER = new BlockRegistryHelper(BLOCKS, ModItems.ITEMS, TerraIncognita.TAB);

    public static class WoodTypes {
        public static AppleWoodType APPLE = new AppleWoodType("apple", new AppleTree(), MaterialColor.WOOD, MaterialColor.ORANGE_TERRACOTTA, TIConfig.Trees.apple);

        public static List<TIWoodType> VALUES;
        public static Map<String, TIWoodType> VALUES_BY_NAME;

        private static void init() {
            VALUES = CollectionUtil.filterNotNull(APPLE);
            VALUES_BY_NAME = VALUES.stream().collect(Collectors.toMap(type -> type.name, type -> type));
        }

        public static TIWoodType byName(String name) {
            return VALUES_BY_NAME.get(name);
        }
    }

    public static final List<PottablePlantEntry> FLOWERS;
    public static final PottablePlantEntry DANDELION_PUFF = makePottablePlant("dandelion_puff", () -> new FlowerBlock(Effects.SATURATION, 6, Properties.from(Blocks.AZURE_BLUET)), DandelionPuffItem::new, Flora.dandelionPuff);
    public static final PottablePlantEntry CHICORY        = makeFlower("chicory", Effects.SPEED, 160, Blocks.BLUE_ORCHID, Flora.fieldFlowers);
    public static final PottablePlantEntry YARROW         = makeFlower("yarrow", Effects.NIGHT_VISION, 100, Blocks.WHITE_TULIP, Flora.fieldFlowers);
    public static final PottablePlantEntry MARIGOLD       = makePottablePlant("marigold", MarigoldBlock::new, Flora.savannaFlowers);
    public static final PottablePlantEntry BLUE_LUPINE    = makeFlower("blue_lupin", Effects.RESISTANCE, 120, Blocks.CORNFLOWER, Flora.savannaFlowers);
    public static final PottablePlantEntry RED_SNAPDRAGON     = makeFlower("red_snapdragon", Effects.RESISTANCE, 120, Blocks.POPPY, Flora.savannaFlowers);
    public static final PottablePlantEntry YELLOW_SNAPDRAGON  = makeFlower("yellow_snapdragon", Effects.RESISTANCE, 120, Blocks.DANDELION, Flora.savannaFlowers);
    public static final PottablePlantEntry MAGENTA_SNAPDRAGON = makeFlower("magenta_snapdragon", Effects.RESISTANCE, 120, Blocks.ALLIUM, Flora.savannaFlowers);
    public static final PottablePlantEntry EDELWEISS      = makePottablePlant("edelweiss", () -> new AlpineFlowerBlock(Effects.HASTE, 200, Properties.from(Blocks.AZURE_BLUET)), Flora.alpineFlowers);
    public static final PottablePlantEntry ALPINE_PINK    = makePottablePlant("alpine_pink", () -> new AlpineFlowerBlock(Effects.SLOWNESS, 160, Properties.from(Blocks.PINK_TULIP)), Flora.alpineFlowers);
    public static final PottablePlantEntry GENTIAN        = makePottablePlant("gentian", () -> new AlpineFlowerBlock(Effects.WEAKNESS, 180, Properties.from(Blocks.CORNFLOWER)), Flora.alpineFlowers);
    public static final PottablePlantEntry ROCKFOIL       = makePottablePlant("rockfoil", RockfoilBlock::new, Flora.alpineFlowers);
    public static final PottablePlantEntry FORGET_ME_NOT  = makeFlower("forget_me_not", Effects.SLOWNESS, 160, Blocks.BLUE_ORCHID, Flora.swampFlowers);
    public static final PottablePlantEntry BLUE_IRIS      = makeFlower("blue_iris", Effects.INVISIBILITY, 160, Blocks.CORNFLOWER, Flora.jungleFlowers);
    public static final PottablePlantEntry PURPLE_IRIS    = makeFlower("purple_iris", Effects.INVISIBILITY, 160, Blocks.ALLIUM, Flora.jungleFlowers);
    public static final PottablePlantEntry BLACK_IRIS     = makeFlower("black_iris", Effects.INVISIBILITY, 160, Blocks.ALLIUM, Flora.jungleFlowers);
    public static final PottablePlantEntry DWARF_FIREWEED = makeFlower("fireweed", Effects.SPEED, 160, Blocks.WITHER_ROSE, Flora.arcticFlowers);
    public static final PottablePlantEntry ARCTIC_POPPY   = makeFlower("arctic_poppy", Effects.NIGHT_VISION, 100, Blocks.DANDELION, Flora.arcticFlowers);

    public static final List<BlockEntry> TALL_PLANTS;
    public static final BlockEntry CATTAIL       = HELPER.newBuilder("cattail", WaterloggableTallFlowerBlock::new).withBlockItem(block -> new TallBlockItem(block, new Item.Properties().group(TerraIncognita.TAB))).buildIf(Flora.swampFlowers);
    public static final BlockEntry WATER_FLAG    = HELPER.newBuilder("water_flag", WaterloggableTallFlowerBlock::new).withBlockItem(block -> new TallBlockItem(block, new Item.Properties().group(TerraIncognita.TAB))).buildIf(Flora.swampFlowers);
    public static final BlockEntry TALL_FIREWEED = makeTallFlower("tall_fireweed", Flora.arcticFlowers);
    public static final BlockEntry WHITE_RHODODENDRON = makeTallFlower("white_rhododendron", Flora.arcticFlowers);

    public static final List<BlockEntry> LOTUSES;
    public static final BlockEntry PINK_LOTUS   = makeLotus("pink_lotus");
    public static final BlockEntry WHITE_LOTUS  = makeLotus("white_lotus");
    public static final BlockEntry YELLOW_LOTUS = makeLotus("yellow_lotus");
    public static final BlockEntry SMALL_LILY_PAD = HELPER.newBuilder("small_lilypad", SmallLilypadBlock::new).withBlockItem(SmallLilypadItem::new).buildIf(Flora.smallLilypad);

    public static final PottablePlantEntry REEDS = makePottablePlant("reeds", ReedsBlock::new, Flora.reeds);
    public static final BlockEntry ROOTS = HELPER.newBuilder("roots", RootsBlock::new).buildIf(Flora.roots);
    public static final BlockEntry MOSS = HELPER.newBuilder("hanging_moss", HangingMossBlock::new).buildIf(Flora.hangingMoss);

    public static final BlockEntry PEAT = HELPER.newBuilder("peat", PeatBlock::new).buildIf(Misc.peat);
    public static final BlockEntry TILLED_PEAT = HELPER.newBuilder("tilled_peat", TilledPeatBlock::new).withoutItemForm().buildIf(Misc.peat);

    public static final BlockEntry CALTROPS = HELPER.newBuilder("caltrops", CaltropsBlock::new).withBlockItem(CaltropsItem::new).buildIf(Tools.caltrops);
    public static final BlockEntry BASKET = HELPER.newBuilder("basket", BasketBlock::new).withBlockItem(BasketItem::new).buildIf(Tools.basket);
    public static final BlockEntry WICKER_MAT = HELPER.newBuilder("wicker_mat", WickerMatBlock::new).buildIf(Flora.reeds);

    static {
        WoodTypes.init();

        FLOWERS = CollectionUtil.filterNotNull(
            DANDELION_PUFF, CHICORY, YARROW, MARIGOLD, BLUE_LUPINE, RED_SNAPDRAGON, YELLOW_SNAPDRAGON, MAGENTA_SNAPDRAGON, EDELWEISS,
            ALPINE_PINK, ROCKFOIL, GENTIAN, FORGET_ME_NOT, BLUE_IRIS, PURPLE_IRIS, BLACK_IRIS, DWARF_FIREWEED, ARCTIC_POPPY
        );

        TALL_PLANTS = CollectionUtil.filterNotNull(
            CATTAIL, WATER_FLAG, TALL_FIREWEED, WHITE_RHODODENDRON
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

        BlockEntry.Builder builder = HELPER.newBuilder(id, constructor);
        if (blockItemConstructor != null) {
            builder = builder.withBlockItem(blockItemConstructor);
        }

        return new PottablePlantEntry(id, builder.build());
    }

    private static PottablePlantEntry makeFlower(String id, Effect effect, int duration, Block propertyBase, BooleanOption condition) {
        return makePottablePlant(id, () -> new FlowerBlock(effect, duration, Properties.from(propertyBase)), condition);
    }

    private static BlockEntry makeTallFlower(String id, BooleanOption condition) {
        Function<Block, BlockItem> blockItemConstructor = block -> new TallBlockItem(block, new Item.Properties().group(TerraIncognita.TAB));
        return HELPER.newBuilder(id, () -> new TallFlowerBlock(Properties.from(Blocks.ROSE_BUSH))).withBlockItem(blockItemConstructor).buildIf(condition);
    }

    private static BlockEntry makeLotus(String id) {
        Function<Block, BlockItem> blockItemConstructor = block -> new LilyPadItem(block, new Item.Properties().group(TerraIncognita.TAB));
        return HELPER.newBuilder(id, () -> new LilyPadBlock(Properties.from(Blocks.LILY_PAD))).withBlockItem(blockItemConstructor).buildIf(Flora.lotus);
    }

    public static void initTilling() {
        HoeItem.HOE_LOOKUP = Maps.newHashMap(HoeItem.HOE_LOOKUP);

        if (Misc.peat.get()) {
            HoeItem.HOE_LOOKUP.put(ModBlocks.PEAT.getBlock(), ModBlocks.TILLED_PEAT.getBlock().getDefaultState());
        }
    }

    public static void initStripping() {
        AxeItem.BLOCK_STRIPPING_MAP = Maps.newHashMap(AxeItem.BLOCK_STRIPPING_MAP);

        for (TIWoodType woodType : WoodTypes.VALUES) {
            AxeItem.BLOCK_STRIPPING_MAP.put(woodType.LOG.getBlock(), woodType.STRIPPED_LOG.getBlock());
            AxeItem.BLOCK_STRIPPING_MAP.put(woodType.WOOD.getBlock(), woodType.STRIPPED_WOOD.getBlock());
        }
    }

    public static void initFlammability() {
        FireBlockAccessor fire = (FireBlockAccessor) Blocks.FIRE;
        for (PottablePlantEntry flower : FLOWERS) {
            fire.TI_SetFireInfo(flower.getBlock(), 60, 100);
        }

        if (Flora.reeds.get()) {
            assert WICKER_MAT != null;
            fire.TI_SetFireInfo(WICKER_MAT.getBlock(), 60, 20);
        }

        WoodTypes.APPLE.initFlammability();
    }
}
