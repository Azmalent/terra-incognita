package azmalent.terraincognita.common.registry;

import azmalent.cuneiform.registry.BlockEntry;
import azmalent.cuneiform.util.DataUtil;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.block.*;
import azmalent.terraincognita.common.block.plant.*;
import azmalent.terraincognita.common.block.plant.ModFlowerBlock.StewEffect;
import azmalent.terraincognita.common.block.fruit.AppleBlock;
import azmalent.terraincognita.common.block.fruit.HazelnutBlock;
import azmalent.terraincognita.common.woodtype.TIWoodType;
import azmalent.terraincognita.common.item.block.BasketItem;
import azmalent.terraincognita.common.item.block.CaltropsItem;
import azmalent.terraincognita.common.item.block.DandelionPuffItem;
import azmalent.terraincognita.common.item.block.SmallLilypadItem;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static azmalent.terraincognita.TerraIncognita.REGISTRY_HELPER;
import static net.minecraft.world.item.CreativeModeTab.TAB_DECORATIONS;

@SuppressWarnings({"unused"})
public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = REGISTRY_HELPER.getOrCreateRegistry(ForgeRegistries.BLOCKS);

    //Small flowers
    public static final BlockEntry<?> DANDELION_PUFF = createPlant("dandelion_puff", () -> new ModFlowerBlock(StewEffect.SATURATION), DandelionPuffItem::new);

    //Plain flowers
    public static final BlockEntry<?> CHICORY  = createPlant("chicory", () -> new ModFlowerBlock(StewEffect.SPEED));
    public static final BlockEntry<?> YARROW   = createPlant("yarrow", () -> new ModFlowerBlock(StewEffect.NIGHT_VISION));
    public static final BlockEntry<?> DAFFODIL = createPlant("daffodil", () -> new ModFlowerBlock(StewEffect.BLINDNESS));

    //Forest flowers
    public static final BlockEntry<?> YELLOW_PRIMROSE = createPlant("yellow_primrose", () -> new ModFlowerBlock(StewEffect.STRENGTH));
    public static final BlockEntry<?> PINK_PRIMROSE   = createPlant("pink_primrose", () -> new ModFlowerBlock(StewEffect.STRENGTH));
    public static final BlockEntry<?> PURPLE_PRIMROSE = createPlant("purple_primrose", () -> new ModFlowerBlock(StewEffect.STRENGTH));
    public static final BlockEntry<?> FOXGLOVE        = createPlant("foxglove", () -> new ModFlowerBlock(StewEffect.POISON));
    public static final BlockEntry<?> WILD_GARLIC     = createPlant("wild_garlic", () -> new ModFlowerBlock(StewEffect.SATURATION));

    //Savanna flowers
    public static final BlockEntry<?> MARIGOLD    = createPlant("marigold", () -> new ModFlowerBlock(StewEffect.REGENERATION));
    public static final BlockEntry<?> SNAPDRAGON  = createPlant("snapdragon", () -> new ModFlowerBlock(StewEffect.HASTE));
    public static final BlockEntry<?> BLUE_IRIS   = createPlant("blue_iris", () -> new ModFlowerBlock(StewEffect.INVISIBILITY));
    public static final BlockEntry<?> PURPLE_IRIS = createPlant("purple_iris", () -> new ModFlowerBlock(StewEffect.INVISIBILITY));
    public static final BlockEntry<?> BLACK_IRIS  = createPlant("black_iris", () -> new ModFlowerBlock(StewEffect.INVISIBILITY));
    public static final BlockEntry<?> OLEANDER    = createTallPlant("oleander");
    public static final BlockEntry<?> SAGE        = createTallPlant("sage");

    //Alpine flowers
    public static final BlockEntry<?> ALPINE_PINK = createPlant("alpine_pink", () -> new AlpineFlowerBlock(StewEffect.SLOWNESS));
    public static final BlockEntry<?> ASTER       = createPlant("aster", () -> new AlpineFlowerBlock(StewEffect.NIGHT_VISION));
    public static final BlockEntry<?> GENTIAN     = createPlant("gentian", () -> new AlpineFlowerBlock(StewEffect.WEAKNESS));
    public static final BlockEntry<?> EDELWEISS   = createPlant("edelweiss", () -> new AlpineFlowerBlock(StewEffect.HASTE));
    public static final BlockEntry<?> YELLOW_SAXIFRAGE  = createPlant("yellow_saxifrage", SaxifrageBlock::new);
    public static final BlockEntry<?> MAGENTA_SAXIFRAGE = createPlant("magenta_saxifrage", SaxifrageBlock::new);

    //Swamp flowers
    public static final BlockEntry<?> FORGET_ME_NOT = createPlant("forget_me_not", () -> new ModFlowerBlock(StewEffect.SLOWNESS));
    public static final BlockEntry<?> GLOBEFLOWER   = createPlant("globeflower", () -> new ModFlowerBlock(StewEffect.RESISTANCE));
    public static final BlockEntry<?> WATER_FLAG    = createTallWaterloggablePlant("water_flag");

    //Arctic flowers
    public static final BlockEntry<?> HEATHER     = createPlant("heather", () -> new ModFlowerBlock(StewEffect.SPEED));
    public static final BlockEntry<?> WHITE_DRYAD = createPlant("white_dryad", () -> new ModFlowerBlock(StewEffect.SLOWNESS));
    public static final BlockEntry<?> FIREWEED    = createTallPlant("fireweed");
    public static final BlockEntry<?> WHITE_RHODODENDRON = createTallPlant("white_rhododendron");

    //Cacti
    public static final BlockEntry<CactusFlowerBlock> CACTUS_FLOWER = createPlant("cactus_flower", CactusFlowerBlock::new);
    public static final BlockEntry<SmoothCactusBlock> SMOOTH_CACTUS = REGISTRY_HELPER.createBlock("smooth_cactus", SmoothCactusBlock::new).cutoutRender().build();

    public static final List<BlockEntry<?>> FLOWERS = List.of(
        DANDELION_PUFF, CHICORY, YARROW, DAFFODIL, YELLOW_PRIMROSE, PINK_PRIMROSE, PURPLE_PRIMROSE, FOXGLOVE, WILD_GARLIC,
        MARIGOLD, SNAPDRAGON, EDELWEISS, ALPINE_PINK, ASTER, YELLOW_SAXIFRAGE,
        GENTIAN, FORGET_ME_NOT, BLUE_IRIS, PURPLE_IRIS, BLACK_IRIS, HEATHER, WHITE_DRYAD,
        WATER_FLAG, FIREWEED, WHITE_RHODODENDRON, OLEANDER, SAGE, CACTUS_FLOWER
    );

    //Sweet peas
    public static final List<BlockEntry<SweetPeasBlock>> SWEET_PEAS =
        Stream.of("white", "pink", "red", "magenta", "purple", "blue", "light_blue")
            .map(ModBlocks::createSweetPeas)
            .toList();

    //Lily pads
    public static final BlockEntry<SmallLilyPadBlock> SMALL_LILY_PAD = REGISTRY_HELPER.createBlock("small_lilypad", SmallLilyPadBlock::new).blockItem(SmallLilypadItem::new).cutoutRender().build();

    public static final BlockEntry<TILilyPadBlock> PINK_LOTUS   = createLotus("pink");
    public static final BlockEntry<TILilyPadBlock> WHITE_LOTUS  = createLotus("white");
    public static final BlockEntry<TILilyPadBlock> YELLOW_LOTUS = createLotus("yellow");
    public static final List<BlockEntry<TILilyPadBlock>> LOTUSES = List.of(PINK_LOTUS, WHITE_LOTUS, YELLOW_LOTUS);

    //Fruits
    public static final BlockEntry<AppleBlock> APPLE = REGISTRY_HELPER.createBlock("apple", AppleBlock::new).noItemForm().cutoutRender().build();
    public static final BlockEntry<HazelnutBlock> HAZELNUT = REGISTRY_HELPER.createBlock("hazelnut", HazelnutBlock::new).noItemForm().cutoutRender().build();

    //Other vegetation
    public static final BlockEntry<SedgeBlock> SEDGE = createPlant("sedge", SedgeBlock::new);

    public static final BlockEntry<SourBerrySproutBlock> SOUR_BERRY_SPROUTS = REGISTRY_HELPER.createBlock("sour_berry_sprouts", SourBerrySproutBlock::new).blockItem(WaterLilyBlockItem::new, CreativeModeTab.TAB_MISC).cutoutRender().build();
    public static final BlockEntry<SourBerryBushBlock> SOUR_BERRY_BUSH = REGISTRY_HELPER.createBlock("sour_berry_bush", SourBerryBushBlock::new).noItemForm().cutoutRender().build();

    public static final BlockEntry<CaribouMossWallBlock> CARIBOU_MOSS_WALL = REGISTRY_HELPER.createBlock("caribou_moss_wall", CaribouMossWallBlock::new).noItemForm().cutoutRender().build();
    public static final BlockEntry<CaribouMossBlock> CARIBOU_MOSS = createPlant("caribou_moss", CaribouMossBlock::new, block -> new StandingAndWallBlockItem(block, CARIBOU_MOSS_WALL.get(), new Item.Properties().tab(TAB_DECORATIONS)));

    public static final BlockEntry<HangingMossBlock> HANGING_MOSS = REGISTRY_HELPER.createBlock("hanging_moss", HangingMossBlock::new).cutoutRender().build();

    //Other blocks
    public static final BlockEntry<PeatBlock> PEAT = REGISTRY_HELPER.createBlock("peat", PeatBlock::new).blockItem(CreativeModeTab.TAB_BUILDING_BLOCKS).build();
    public static final BlockEntry<PeatFarmBlock> TILLED_PEAT = REGISTRY_HELPER.createBlock("tilled_peat", PeatFarmBlock::new).build();
    public static final BlockEntry<GrassBlock> FLOWERING_GRASS = REGISTRY_HELPER.createBlock("flowering_grass", GrassBlock::new, Properties.copy(Blocks.GRASS_BLOCK)).cutoutMippedRender().build();
    public static final BlockEntry<GravelBlock> MOSSY_GRAVEL = REGISTRY_HELPER.createBlock("mossy_gravel", GravelBlock::new, Properties.copy(Blocks.GRAVEL)).build();
    public static final BlockEntry<CaltropsBlock> CALTROPS = REGISTRY_HELPER.createBlock("caltrops", CaltropsBlock::new).blockItem(CaltropsItem::new).cutoutRender().build();
    public static final BlockEntry<BasketBlock> BASKET = REGISTRY_HELPER.createBlock("basket", BasketBlock::new).blockItem(BasketItem::new).build();
    public static final BlockEntry<CarpetBlock> WICKER_MAT = REGISTRY_HELPER.createBlock("wicker_mat", CarpetBlock::new, BlockBehaviour.Properties.of(Material.CLOTH_DECORATION, MaterialColor.COLOR_BROWN).strength(0.1F).sound(SoundType.WOOL)).build();
    public static final BlockEntry<WickerLanternBlock> WICKER_LANTERN = REGISTRY_HELPER.createBlock("wicker_lantern", WickerLanternBlock::new).cutoutRender().build();

    public static final BlockEntry<Block> HAZELNUT_SACK = TerraIncognita.REGISTRY_HELPER.createBlock("hazelnut_sack", Block.Properties.of(Material.WOOL, MaterialColor.COLOR_BROWN).strength(0.5F).sound(SoundType.WOOL)).build();
    public static final BlockEntry<Block> SOUR_BERRY_SACK = TerraIncognita.REGISTRY_HELPER.createBlock("sour_berry_sack", Block.Properties.of(Material.WOOL, MaterialColor.TERRACOTTA_ORANGE).strength(0.5F).sound(SoundType.WOOL)).build();

    static {
        ModWoodTypes.init();
    }

    public static <T extends Block> BlockEntry<T> createPlant(String id, Supplier<T> constructor) {
        return createPlant(id, constructor, null);
    }

    public static <T extends Block> BlockEntry<T> createPlant(String id, Supplier<T> constructor, Function<Block, BlockItem> blockItemConstructor) {
        var plant = REGISTRY_HELPER.createBlock(id, constructor).blockItem(blockItemConstructor).cutoutRender().build();
        createFlowerPot(id, plant);
        return plant;
    }

    public static BlockEntry<TallFlowerBlock> createTallPlant(String id) {
        var plant  = REGISTRY_HELPER.createBlock(id, TallFlowerBlock::new, Properties.copy(Blocks.ROSE_BUSH)).tallBlockItem(TAB_DECORATIONS).cutoutRender().build();
        createFlowerPot(id, plant);
        return plant;
    }

    public static BlockEntry<WaterloggableTallFlowerBlock> createTallWaterloggablePlant(String id) {
        var plant = REGISTRY_HELPER.createBlock(id, WaterloggableTallFlowerBlock::new).tallBlockItem(TAB_DECORATIONS).cutoutRender().build();
        createFlowerPot(id, plant);
        return plant;
    }

    private static void createFlowerPot(String id, Supplier<? extends Block> plantSupplier) {
        REGISTRY_HELPER.createBlock("potted_" + id,
            () -> new FlowerPotBlock(() -> (FlowerPotBlock) Blocks.FLOWER_POT.delegate.get(),
                plantSupplier, Block.Properties.copy(Blocks.FLOWER_POT)))
            .noItemForm().cutoutRender().build();
    }

    private static BlockEntry<TILilyPadBlock> createLotus(String color) {
        return REGISTRY_HELPER.createBlock(color + "_lotus", TILilyPadBlock::new, Properties.copy(Blocks.LILY_PAD)).blockItem(WaterLilyBlockItem::new, TAB_DECORATIONS).cutoutRender().build();
    }

    private static BlockEntry<SweetPeasBlock> createSweetPeas(String color) {
        return REGISTRY_HELPER.createBlock(color + "_sweet_peas", SweetPeasBlock::new).cutoutRender().build();
    }

    public static void initFlammability() {
        for (BlockEntry<?> flower : FLOWERS) {
            DataUtil.registerFlammable(flower, 60, 100);
        }

        for (BlockEntry<SweetPeasBlock> sweetPea : SWEET_PEAS) {
            DataUtil.registerFlammable(sweetPea, 15, 100);
        }

        for (TIWoodType woodType : ModWoodTypes.VALUES) {
            woodType.initFlammability();
        }

        DataUtil.registerFlammable(WICKER_MAT, 60, 20);
    }
}
