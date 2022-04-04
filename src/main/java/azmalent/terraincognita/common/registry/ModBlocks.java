package azmalent.terraincognita.common.registry;

import azmalent.cuneiform.lib.registry.BlockEntry;
import azmalent.cuneiform.lib.util.DataUtil;
import azmalent.terraincognita.common.block.*;
import azmalent.terraincognita.common.block.plant.*;
import azmalent.terraincognita.common.block.plant.ModFlowerBlock.StewEffect;
import azmalent.terraincognita.common.block.fruit.AppleBlock;
import azmalent.terraincognita.common.block.fruit.HazelnutBlock;
import azmalent.terraincognita.common.woodtype.ModWoodType;
import azmalent.terraincognita.common.item.block.BasketItem;
import azmalent.terraincognita.common.item.block.CaltropsItem;
import azmalent.terraincognita.common.item.block.DandelionPuffItem;
import azmalent.terraincognita.common.item.block.SmallLilypadItem;
import azmalent.terraincognita.util.PottablePlantEntry;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Function;
import java.util.function.Supplier;

import static azmalent.terraincognita.TerraIncognita.REG_HELPER;
import static net.minecraft.world.item.CreativeModeTab.TAB_DECORATIONS;

@SuppressWarnings({"unused", "unchecked"})
public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = REG_HELPER.getOrCreateRegistry(ForgeRegistries.BLOCKS);

    //Small flowers
    public static final PottablePlantEntry[] FLOWERS;
    public static final PottablePlantEntry DANDELION_PUFF = createPlant("dandelion_puff", () -> new ModFlowerBlock(StewEffect.SATURATION), DandelionPuffItem::new);

    //Plain flowers
    public static final PottablePlantEntry CHICORY        = createPlant("chicory", () -> new ModFlowerBlock(StewEffect.SPEED));
    public static final PottablePlantEntry YARROW         = createPlant("yarrow", () -> new ModFlowerBlock(StewEffect.NIGHT_VISION));
    public static final PottablePlantEntry DAFFODIL       = createPlant("daffodil", () -> new ModFlowerBlock(StewEffect.BLINDNESS));

    //Forest flowers
    public static final PottablePlantEntry YELLOW_PRIMROSE = createPlant("yellow_primrose", () -> new ModFlowerBlock(StewEffect.STRENGTH));
    public static final PottablePlantEntry PINK_PRIMROSE   = createPlant("pink_primrose", () -> new ModFlowerBlock(StewEffect.STRENGTH));
    public static final PottablePlantEntry PURPLE_PRIMROSE = createPlant("purple_primrose", () -> new ModFlowerBlock(StewEffect.STRENGTH));
    public static final PottablePlantEntry FOXGLOVE       = createPlant("foxglove", () -> new ModFlowerBlock(StewEffect.POISON));
    public static final PottablePlantEntry WILD_GARLIC    = createPlant("wild_garlic", () -> new ModFlowerBlock(StewEffect.SATURATION));

    //Savanna flowers
    public static final PottablePlantEntry MARIGOLD       = createPlant("marigold", () -> new ModFlowerBlock(StewEffect.REGENERATION));
    public static final PottablePlantEntry SNAPDRAGON     = createPlant("snapdragon", () -> new ModFlowerBlock(StewEffect.HASTE));
    public static final PottablePlantEntry BLUE_IRIS      = createPlant("blue_iris", () -> new ModFlowerBlock(StewEffect.INVISIBILITY));
    public static final PottablePlantEntry PURPLE_IRIS    = createPlant("purple_iris", () -> new ModFlowerBlock(StewEffect.INVISIBILITY));
    public static final PottablePlantEntry BLACK_IRIS     = createPlant("black_iris", () -> new ModFlowerBlock(StewEffect.INVISIBILITY));
    public static final PottablePlantEntry OLEANDER = createTallPlant("oleander");
    public static final PottablePlantEntry SAGE     = createTallPlant("sage");

    //Alpine flowers
    public static final PottablePlantEntry ALPINE_PINK    = createPlant("alpine_pink", () -> new AlpineFlowerBlock(StewEffect.SLOWNESS));
    public static final PottablePlantEntry ASTER          = createPlant("aster", () -> new AlpineFlowerBlock(StewEffect.NIGHT_VISION));
    public static final PottablePlantEntry GENTIAN        = createPlant("gentian", () -> new AlpineFlowerBlock(StewEffect.WEAKNESS));
    public static final PottablePlantEntry EDELWEISS      = createPlant("edelweiss", () -> new AlpineFlowerBlock(StewEffect.HASTE));
    public static final PottablePlantEntry YELLOW_SAXIFRAGE = createPlant("yellow_saxifrage", SaxifrageBlock::new);

    //Swamp flowers
    public static final PottablePlantEntry FORGET_ME_NOT  = createPlant("forget_me_not", () -> new ModFlowerBlock(StewEffect.SLOWNESS));
    public static final PottablePlantEntry GLOBEFLOWER    = createPlant("globeflower", () -> new ModFlowerBlock(StewEffect.RESISTANCE));
    public static final PottablePlantEntry WATER_FLAG         = createTallWaterloggablePlant("water_flag");

    //Arctic flowers
    public static final PottablePlantEntry HEATHER        = createPlant("heather", () -> new ModFlowerBlock(StewEffect.SPEED));
    public static final PottablePlantEntry WHITE_DRYAD    = createPlant("white_dryad", () -> new ModFlowerBlock(StewEffect.SLOWNESS));
    public static final PottablePlantEntry FIREWEED           = createTallPlant("fireweed");
    public static final PottablePlantEntry WHITE_RHODODENDRON = createTallPlant("white_rhododendron");

    //Sweet peas
    public static final BlockEntry<SweetPeasBlock>[] SWEET_PEAS;
    public static final BlockEntry<SweetPeasBlock> WHITE_SWEET_PEAS   = createSweetPeas("white");
    public static final BlockEntry<SweetPeasBlock> PINK_SWEET_PEAS    = createSweetPeas("pink");
    public static final BlockEntry<SweetPeasBlock> RED_SWEET_PEAS     = createSweetPeas("red");
    public static final BlockEntry<SweetPeasBlock> MAGENTA_SWEET_PEAS = createSweetPeas("magenta");
    public static final BlockEntry<SweetPeasBlock> PURPLE_SWEET_PEAS  = createSweetPeas("purple");
    public static final BlockEntry<SweetPeasBlock> BLUE_SWEET_PEAS    = createSweetPeas("blue");
    public static final BlockEntry<SweetPeasBlock> LIGHT_BLUE_SWEET_PEAS  = createSweetPeas("light_blue");

    //Lily pads
    public static final BlockEntry<WaterlilyBlock>[] LOTUSES;
    public static final BlockEntry<WaterlilyBlock> PINK_LOTUS   = createLotus("pink");
    public static final BlockEntry<WaterlilyBlock> WHITE_LOTUS  = createLotus("white");
    public static final BlockEntry<WaterlilyBlock> YELLOW_LOTUS = createLotus("yellow");
    public static final BlockEntry<SmallLilyPadBlock> SMALL_LILY_PAD = REG_HELPER.createBlock("small_lilypad", SmallLilyPadBlock::new).blockItem(SmallLilypadItem::new).cutoutRender().build();

    //Fruits
    public static final BlockEntry<AppleBlock> APPLE = REG_HELPER.createBlock("apple", AppleBlock::new).noItemForm().cutoutRender().build();
    public static final BlockEntry<HazelnutBlock> HAZELNUT = REG_HELPER.createBlock("hazelnut", HazelnutBlock::new).noItemForm().cutoutRender().build();

    //Other vegetation
    public static final PottablePlantEntry SEDGE = createPlant("reeds", SedgeBlock::new);

    public static final BlockEntry<SourBerrySproutBlock> SOUR_BERRY_SPROUTS = REG_HELPER.createBlock("sour_berry_sprouts", SourBerrySproutBlock::new).blockItem(WaterLilyBlockItem::new, CreativeModeTab.TAB_MISC).cutoutRender().build();
    public static final BlockEntry<SourBerryBushBlock> SOUR_BERRY_BUSH = REG_HELPER.createBlock("sour_berry_bush", SourBerryBushBlock::new).noItemForm().cutoutRender().build();

    public static final BlockEntry<CaribouMossWallBlock> CARIBOU_MOSS_WALL = REG_HELPER.createBlock("caribou_moss_wall", CaribouMossWallBlock::new).noItemForm().cutoutRender().build();
    public static final PottablePlantEntry CARIBOU_MOSS = createPlant("caribou_moss", CaribouMossBlock::new, block -> new StandingAndWallBlockItem(block, CARIBOU_MOSS_WALL.get(), new Item.Properties().tab(TAB_DECORATIONS)));

    public static final BlockEntry<HangingMossBlock> HANGING_MOSS = REG_HELPER.createBlock("hanging_moss", HangingMossBlock::new).cutoutRender().build();

    public static final BlockEntry<SmoothCactusBlock> SMOOTH_CACTUS = REG_HELPER.createBlock("smooth_cactus", SmoothCactusBlock::new).cutoutRender().build();
    public static final PottablePlantEntry CACTUS_FLOWER = createPlant("cactus_flower", CactusFlowerBlock::new);

    //Other blocks
    public static final BlockEntry<PeatBlock> PEAT = REG_HELPER.createBlock("peat", PeatBlock::new).blockItem(CreativeModeTab.TAB_BUILDING_BLOCKS).build();
    public static final BlockEntry<PeatFarmBlock> TILLED_PEAT = REG_HELPER.createBlock("tilled_peat", PeatFarmBlock::new).build();
    public static final BlockEntry<GrassBlock> FLOWERING_GRASS = REG_HELPER.createBlock("flowering_grass", GrassBlock::new, Properties.copy(Blocks.GRASS_BLOCK)).cutoutMippedRender().build();
    public static final BlockEntry<GravelBlock> MOSSY_GRAVEL = REG_HELPER.createBlock("mossy_gravel", GravelBlock::new, Properties.copy(Blocks.GRAVEL)).build();
    public static final BlockEntry<CaltropsBlock> CALTROPS = REG_HELPER.createBlock("caltrops", CaltropsBlock::new).blockItem(CaltropsItem::new).cutoutRender().build();
    public static final BlockEntry<BasketBlock> BASKET = REG_HELPER.createBlock("basket", BasketBlock::new).blockItem(BasketItem::new).build();
    public static final BlockEntry<CarpetBlock> WICKER_MAT = REG_HELPER.createBlock("wicker_mat", CarpetBlock::new, BlockBehaviour.Properties.of(Material.CLOTH_DECORATION, MaterialColor.COLOR_BROWN).strength(0.1F).sound(SoundType.WOOL)).build();
    public static final BlockEntry<WickerLanternBlock> WICKER_LANTERN = REG_HELPER.createBlock("wicker_lantern", WickerLanternBlock::new).cutoutRender().build();

    static {
        ModWoodTypes.init();

        FLOWERS = new PottablePlantEntry[]{
            DANDELION_PUFF, CHICORY, YARROW, DAFFODIL, YELLOW_PRIMROSE, PINK_PRIMROSE, PURPLE_PRIMROSE, FOXGLOVE, WILD_GARLIC,
            MARIGOLD, SNAPDRAGON, EDELWEISS, ALPINE_PINK, ASTER, YELLOW_SAXIFRAGE,
        	GENTIAN, FORGET_ME_NOT, BLUE_IRIS, PURPLE_IRIS, BLACK_IRIS, HEATHER, WHITE_DRYAD,
            WATER_FLAG, FIREWEED, WHITE_RHODODENDRON, OLEANDER, SAGE, CACTUS_FLOWER
        };

        LOTUSES = new BlockEntry[] {
            WHITE_LOTUS, PINK_LOTUS, YELLOW_LOTUS
        };

        SWEET_PEAS = new BlockEntry[] {
            WHITE_SWEET_PEAS, PINK_SWEET_PEAS, RED_SWEET_PEAS, MAGENTA_SWEET_PEAS, PURPLE_SWEET_PEAS,
            BLUE_SWEET_PEAS, LIGHT_BLUE_SWEET_PEAS
        };
    }

    public static PottablePlantEntry createPlant(String id, Supplier<Block> constructor) {
        return createPlant(id, constructor, null);
    }

    public static PottablePlantEntry createPlant(String id, Supplier<Block> constructor, Function<Block, BlockItem> blockItemConstructor) {
        BlockEntry.Builder<Block> builder = REG_HELPER.createBlock(id, constructor).blockItem(blockItemConstructor).cutoutRender();
        return new PottablePlantEntry(id, builder.build());
    }

    public static PottablePlantEntry createTallPlant(String id) {
        BlockEntry.Builder<TallFlowerBlock> builder = REG_HELPER.createBlock(id, TallFlowerBlock::new, Properties.copy(Blocks.ROSE_BUSH)).tallBlockItem(TAB_DECORATIONS).cutoutRender();
        return new PottablePlantEntry(id, builder.build());
    }

    public static PottablePlantEntry createTallWaterloggablePlant(String id) {
        BlockEntry.Builder<WaterloggableTallFlowerBlock> builder = REG_HELPER.createBlock(id, WaterloggableTallFlowerBlock::new).tallBlockItem(TAB_DECORATIONS).cutoutRender();
        return new PottablePlantEntry(id, builder.build());
    }

    private static BlockEntry<WaterlilyBlock> createLotus(String color) {
        return REG_HELPER.createBlock(color + "_lotus", WaterlilyBlock::new, Properties.copy(Blocks.LILY_PAD)).blockItem(WaterLilyBlockItem::new, TAB_DECORATIONS).cutoutRender().build();
    }

    private static BlockEntry<SweetPeasBlock> createSweetPeas(String color) {
        return REG_HELPER.createBlock(color + "_sweet_peas", SweetPeasBlock::new).cutoutRender().build();
    }

    //TODO: fix tool interactions
    public static void initToolInteractions() {
/*        AxeItem.getAxeStrippingState()
        AxeItem.STRIPABLES = Maps.newHashMap(AxeItem.STRIPABLES);
        for (ModWoodType woodType : ModWoodTypes.VALUES) {
            AxeItem.STRIPABLES.put(woodType.LOG.getBlock(), woodType.STRIPPED_LOG.getBlock());
            AxeItem.STRIPABLES.put(woodType.WOOD.getBlock(), woodType.STRIPPED_WOOD.getBlock());
        }

        HoeItem.TILLABLES = Maps.newHashMap(HoeItem.TILLABLES);
        HoeItem.TILLABLES.put(PEAT.getBlock(), TILLED_PEAT.getBlock().defaultBlockState());
        HoeItem.TILLABLES.put(FLOWERING_GRASS.getBlock(), Blocks.FARMLAND.defaultBlockState());

        ShovelItem.FLATTENABLES = Maps.newHashMap(ShovelItem.FLATTENABLES);
        ShovelItem.FLATTENABLES.put(FLOWERING_GRASS.getBlock(), Blocks.GRASS_PATH.defaultBlockState());*/
    }

    public static void initFlammability() {
        for (PottablePlantEntry flower : FLOWERS) {
            DataUtil.registerFlammable(flower::getBlock, 60, 100);
        }

        for (BlockEntry<SweetPeasBlock> sweetPea : SWEET_PEAS) {
            DataUtil.registerFlammable(sweetPea, 15, 100);
        }

        for (ModWoodType woodType : ModWoodTypes.VALUES) {
            woodType.initFlammability();
        }

        DataUtil.registerFlammable(WICKER_MAT, 60, 20);
    }
}
