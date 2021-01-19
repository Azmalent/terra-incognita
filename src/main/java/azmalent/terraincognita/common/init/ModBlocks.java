package azmalent.terraincognita.common.init;

import azmalent.cuneiform.lib.config.options.BooleanOption;
import azmalent.cuneiform.lib.registry.BlockEntry;
import azmalent.cuneiform.lib.registry.BlockRegistryHelper;
import azmalent.terraincognita.TIConfig.Flora;
import azmalent.terraincognita.TIConfig.Tools;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.block.BasketBlock;
import azmalent.terraincognita.common.block.CaltropsBlock;
import azmalent.terraincognita.common.block.WickerMatBlock;
import azmalent.terraincognita.common.block.plants.*;
import azmalent.terraincognita.common.item.block.BasketItem;
import azmalent.terraincognita.common.item.block.CaltropsItem;
import azmalent.terraincognita.common.item.block.DandelionPuffItem;
import azmalent.terraincognita.common.item.block.SmallLilypadItem;
import azmalent.terraincognita.util.CollectionUtil;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.LilyPadItem;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

@SuppressWarnings("unused")
public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TerraIncognita.MODID);
    public static final BlockRegistryHelper HELPER = new BlockRegistryHelper(BLOCKS, ModItems.ITEMS, TerraIncognita.TAB);

    public static final class PottablePlantEntry {
        public final BlockEntry plant;
        public final BlockEntry potted;

        private PottablePlantEntry(String id, BlockEntry plant) {
            this.plant = plant;
            potted = HELPER.newBuilder("potted_" + id, () -> new FlowerPotBlock(plant.getBlock(), Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid())).withoutItemForm().build();
        }

        public Block getBlock() {
            return plant.getBlock();
        }

        public Item getItem() {
            return plant.getItem();
        }

        public Block getPotted() {
            return potted.getBlock();
        }
    }

    private static PottablePlantEntry makePlant(String id, Supplier<Block> constructor, BooleanOption condition) {
        if (!condition.get()) {
            return null;
        }

        BlockEntry plant = HELPER.newBuilder(id, constructor).build();
        return new PottablePlantEntry(id, plant);
    }

    private static PottablePlantEntry makePlant(String id, Supplier<Block> constructor, Function<Block, BlockItem> blockItemConstructor, BooleanOption condition) {
        if (!condition.get()) {
            return null;
        }

        BlockEntry plant = HELPER.newBuilder(id, constructor).withBlockItem(blockItemConstructor).build();
        return new PottablePlantEntry(id, plant);
    }

    public static final List<PottablePlantEntry> FLOWERS;
    public static PottablePlantEntry DANDELION_PUFF = makePlant("dandelion_puff", () -> new FlowerBlock(Effects.SATURATION, 6, Properties.from(Blocks.AZURE_BLUET)), DandelionPuffItem::new, Flora.dandelionPuff);
    public static PottablePlantEntry MARIGOLD       = makePlant("marigold", MarigoldBlock::new, Flora.savannaFlowers);
    public static PottablePlantEntry BLUE_LUPIN     = makeFlower("blue_lupin", Effects.RESISTANCE, 120, Blocks.CORNFLOWER, Flora.savannaFlowers);
    public static PottablePlantEntry EDELWEISS      = makePlant("edelweiss", () -> new AlpineFlowerBlock(Effects.HASTE, 200, Properties.from(Blocks.AZURE_BLUET)), Flora.alpineFlowers);
    public static PottablePlantEntry ALPINE_PINK    = makePlant("alpine_pink", () -> new AlpineFlowerBlock(Effects.SLOWNESS, 160, Properties.from(Blocks.PINK_TULIP)), Flora.alpineFlowers);
    public static PottablePlantEntry ROCKFOIL       = makePlant("rockfoil", RockfoilBlock::new, Flora.alpineFlowers);
    public static PottablePlantEntry FORGET_ME_NOT  = makeFlower("forget_me_not", Effects.SLOWNESS, 160, Blocks.AZURE_BLUET, Flora.swampFlowers);
    public static PottablePlantEntry BLUE_IRIS      = makeFlower("blue_iris", Effects.INVISIBILITY, 160, Blocks.CORNFLOWER, Flora.jungleFlowers);
    public static PottablePlantEntry PURPLE_IRIS    = makeFlower("purple_iris", Effects.INVISIBILITY, 160, Blocks.ALLIUM, Flora.jungleFlowers);
    public static PottablePlantEntry FIREWEED       = makeFlower("fireweed", Effects.SPEED, 160, Blocks.ALLIUM, Flora.arcticFlowers);
    public static PottablePlantEntry ARCTIC_POPPY   = makeFlower("arctic_poppy", Effects.NIGHT_VISION, 100, Blocks.DANDELION, Flora.arcticFlowers);

    public static List<BlockEntry> LOTUSES;
    public static BlockEntry PINK_LOTUS   = makeLotus("pink_lotus");
    public static BlockEntry WHITE_LOTUS  = makeLotus("white_lotus");
    public static BlockEntry YELLOW_LOTUS = makeLotus("yellow_lotus");
    public static BlockEntry SMALL_LILYPAD = HELPER.newBuilder("small_lilypad", SmallLilypadBlock::new).withBlockItem(SmallLilypadItem::new).buildIf(Flora.smallLilypad);

    public static PottablePlantEntry REEDS = makePlant("reeds", ReedsBlock::new, Flora.reeds);

    public static BlockEntry CALTROPS = HELPER.newBuilder("caltrops", CaltropsBlock::new).withBlockItem(CaltropsItem::new).buildIf(Tools.caltrops);
    public static BlockEntry BASKET = HELPER.newBuilder("basket", BasketBlock::new).withBlockItem(BasketItem::new).buildIf(Flora.reeds);
    public static BlockEntry WICKER_MAT = HELPER.newBuilder("wicker_mat", WickerMatBlock::new).buildIf(Flora.reeds);

    static {
        FLOWERS = CollectionUtil.nonNullList(DANDELION_PUFF, FORGET_ME_NOT, MARIGOLD, BLUE_LUPIN, EDELWEISS, ALPINE_PINK, ROCKFOIL, BLUE_IRIS, PURPLE_IRIS, FIREWEED, ARCTIC_POPPY);
        LOTUSES = CollectionUtil.nonNullList(WHITE_LOTUS, PINK_LOTUS, YELLOW_LOTUS);
    }

    private static PottablePlantEntry makeFlower(String id, Effect effect, int duration, Block propertyBase, BooleanOption condition) {
        return makePlant(id, () -> new FlowerBlock(effect, duration, Properties.from(propertyBase)), condition);
    }

    private static BlockEntry makeLotus(String id) {
        return HELPER.newBuilder(id, () -> new LilyPadBlock(Properties.from(Blocks.LILY_PAD)))
                .withBlockItem(block -> new LilyPadItem(block, new Item.Properties().group(TerraIncognita.TAB)))
                .buildIf(Flora.lotus);
    }
}
