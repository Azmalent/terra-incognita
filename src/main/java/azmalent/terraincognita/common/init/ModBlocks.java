package azmalent.terraincognita.common.init;

import azmalent.cuneiform.lib.config.options.BooleanOption;
import azmalent.cuneiform.lib.registry.BlockEntry;
import azmalent.cuneiform.lib.registry.BlockRegistryHelper;
import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.TIConfig.Flora;
import azmalent.terraincognita.TIConfig.Tools;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.block.BasketBlock;
import azmalent.terraincognita.common.block.CaltropsBlock;
import azmalent.terraincognita.common.block.WickerMatBlock;
import azmalent.terraincognita.common.block.plants.EdelweissBlock;
import azmalent.terraincognita.common.block.plants.MarigoldBlock;
import azmalent.terraincognita.common.block.plants.ReedsBlock;
import azmalent.terraincognita.common.block.plants.SmallLilypadBlock;
import azmalent.terraincognita.common.item.block.BasketItem;
import azmalent.terraincognita.common.item.block.CaltropsItem;
import azmalent.terraincognita.common.item.block.DandelionPuffItem;
import azmalent.terraincognita.common.item.block.SmallLilypadItem;
import com.google.common.collect.Lists;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.LilyPadItem;
import net.minecraft.potion.Effect;
import net.minecraft.potion.Effects;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, TerraIncognita.MODID);
    public static final BlockRegistryHelper HELPER = new BlockRegistryHelper(BLOCKS, ModItems.ITEMS, TerraIncognita.TAB);

    public static final class PottablePlantEntry {
        public final BlockEntry plant;
        public final BlockEntry potted;

        private static final Properties FLOWER_POT_PROPS = Properties.create(Material.MISCELLANEOUS).zeroHardnessAndResistance().notSolid();

        private PottablePlantEntry(String id, BlockEntry plant) {
            this.plant = plant;
            potted = HELPER.newBuilder("potted_" + id, () -> new FlowerPotBlock(plant.getBlock(), FLOWER_POT_PROPS)).withoutItemForm().build();
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

    //Flowers
    public static final List<PottablePlantEntry> FLOWERS;
    public static PottablePlantEntry DANDELION_PUFF = makeFlower("dandelion_puff", Effects.SATURATION, 6, Blocks.AZURE_BLUET, DandelionPuffItem::new, Flora.dandelionPuff);
    public static PottablePlantEntry FORGET_ME_NOT  = makeFlower("forget_me_not", Effects.SLOWNESS, 160, Blocks.AZURE_BLUET, Flora.forgetMeNot);
    public static PottablePlantEntry MARIGOLD       = makePlant("marigold", MarigoldBlock::new, Flora.marigold);
    public static PottablePlantEntry EDELWEISS      = makePlant( "edelweiss", EdelweissBlock::new, Flora.edelweiss);
    public static PottablePlantEntry BLUE_IRIS      = makeFlower( "blue_iris", Effects.INVISIBILITY, 160, Blocks.CORNFLOWER, Flora.iris);
    public static PottablePlantEntry PURPLE_IRIS    = makeFlower( "purple_iris", Effects.INVISIBILITY, 160, Blocks.ALLIUM, Flora.iris);
    public static PottablePlantEntry FIREWEED       = makeFlower( "fireweed", Effects.SPEED, 160, Blocks.ALLIUM, Flora.fireweed);
    public static PottablePlantEntry ARCTIC_POPPY   = makeFlower( "arctic_poppy", Effects.RESISTANCE, 140, Blocks.DANDELION, Flora.arcticPoppy);

    //Lilypads
    public static List<BlockEntry> LOTUSES;
    public static BlockEntry WHITE_LOTUS  = makeLotus("white_lotus");
    public static BlockEntry PINK_LOTUS   = makeLotus("pink_lotus");
    public static BlockEntry YELLOW_LOTUS = makeLotus("yellow_lotus");

    public static BlockEntry SMALL_LILYPAD = HELPER.newBuilder("small_lilypad", SmallLilypadBlock::new).withBlockItem(SmallLilypadItem::new).buildIf(Flora.smallLilypad);

    //Other plants
    public static PottablePlantEntry REEDS = makePlant("reeds", ReedsBlock::new, Flora.reeds);

    //Tools
    public static BlockEntry CALTROPS = HELPER.newBuilder("caltrops", CaltropsBlock::new).withBlockItem(CaltropsItem::new).buildIf(Tools.caltrops);

    //Decor
    public static BlockEntry BASKET = HELPER.newBuilder("basket", BasketBlock::new).withBlockItem(BasketItem::new).buildIf(Flora.reeds);
    public static BlockEntry WICKER_MAT = HELPER.newBuilder("wicker_mat", WickerMatBlock::new).buildIf(Flora.reeds);

    static {
        FLOWERS = initBlockSet(DANDELION_PUFF, FORGET_ME_NOT, MARIGOLD, EDELWEISS, BLUE_IRIS, PURPLE_IRIS, FIREWEED, ARCTIC_POPPY);
        LOTUSES = initBlockSet(WHITE_LOTUS, PINK_LOTUS, YELLOW_LOTUS);
    }

    @SafeVarargs
    private static <T> List<T> initBlockSet(T... values) {
        List<T> list = new ArrayList<>();
        for (T t : Lists.newArrayList(values)) {
            if (t != null) {
                list.add(t);
            }
        }
        return list;
    }

    private static PottablePlantEntry makeFlower(String id, Effect effect, int duration, Block propertyBase, Function<Block, ? extends BlockItem> blockItemConstructor, BooleanOption condition) {
        if (!condition.get()) {
            return null;
        }

        BlockEntry plant = HELPER.newBuilder(id, () -> new FlowerBlock(effect, duration, Properties.from(propertyBase))).withBlockItem(blockItemConstructor).build();
        return new PottablePlantEntry(id, plant);
    }

    private static PottablePlantEntry makeFlower(String id, Effect effect, int duration, Block propertyBase, BooleanOption condition) {
        return makePlant(id, () -> new FlowerBlock(effect, duration, Properties.from(propertyBase)), condition);
    }

    private static BlockEntry makeLotus(String id) {
        return HELPER.newBuilder(id, () -> new LilyPadBlock(Properties.from(Blocks.LILY_PAD)))
                .withBlockItem(block -> new LilyPadItem(block, new Item.Properties().group(TerraIncognita.TAB)))
                .buildIf(Flora.lotus);
    }

    @OnlyIn(Dist.CLIENT)
    private static void setCutoutRender(BlockEntry... blockEntries) {
        for (BlockEntry blockEntry : blockEntries) {
            if (blockEntry != null) {
                RenderTypeLookup.setRenderLayer(blockEntry.getBlock(), RenderType.getCutout());
            }
        }
    }

    @OnlyIn(Dist.CLIENT)
    private static void setCutoutRender(PottablePlantEntry plant) {
        if (plant != null) {
            RenderTypeLookup.setRenderLayer(plant.getBlock(), RenderType.getCutout());
            RenderTypeLookup.setRenderLayer(plant.getPotted(), RenderType.getCutout());
        }
    }

    @OnlyIn(Dist.CLIENT)
    public static void initRenderLayers() {
        FLOWERS.forEach(ModBlocks::setCutoutRender);
        setCutoutRender(REEDS);
        setCutoutRender(PINK_LOTUS, WHITE_LOTUS, YELLOW_LOTUS, SMALL_LILYPAD, CALTROPS);
    }
}
