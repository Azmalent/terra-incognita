package azmalent.terraincognita.common.integration.quark;

import azmalent.cuneiform.common.event.FuelHandler;
import azmalent.cuneiform.lib.compat.ModProxyImpl;
import azmalent.cuneiform.lib.registry.BlockEntry;
import azmalent.cuneiform.lib.util.DataUtil;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.client.event.ColorHandler;
import azmalent.terraincognita.common.integration.quark.block.TIHedgeBlock;
import azmalent.terraincognita.common.integration.quark.block.TILeafCarpetBlock;
import azmalent.terraincognita.common.integration.quark.block.TIWoodPostBlock;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModRecipes;
import azmalent.terraincognita.common.registry.ModWoodTypes;
import com.google.common.collect.Lists;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.FoliageColors;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.biome.BiomeColors;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import vazkii.quark.base.module.ModuleLoader;
import vazkii.quark.content.building.module.BurnVinesModule;
import vazkii.quark.content.client.module.ChestSearchingModule;
import vazkii.quark.content.tweaks.module.SignEditingModule;

import java.util.List;

@ModProxyImpl("quark")
public class QuarkIntegration implements IQuarkIntegration {
    private static final ResourceLocation BASKET_DROP_IN_CAP = TerraIncognita.prefix("basket_drop_in");

    private final QuarkWoodBlockSet APPLE = new QuarkWoodBlockSet(ModWoodTypes.APPLE);
    private final BlockEntry BLOSSOMING_APPLE_LEAF_CARPET = ModBlocks.HELPER.newBuilder("blossoming_apple_leaf_carpet", TILeafCarpetBlock::new).cutoutMippedRender().build();
    private final BlockEntry BLOSSOMING_APPLE_HEDGE = ModBlocks.HELPER.newBuilder("blossoming_apple_hedge", () -> new TIHedgeBlock(MaterialColor.ORANGE_TERRACOTTA)).cutoutMippedRender().build();

    private final QuarkWoodBlockSet HAZEL = new QuarkWoodBlockSet(ModWoodTypes.HAZEL);

    private final BlockEntry HAZELNUT_SACK = ModBlocks.HELPER.newBuilder("hazelnut_sack", Block.Properties.create(Material.WOOL, MaterialColor.BROWN).hardnessAndResistance(0.5F).sound(SoundType.CLOTH)).build();
    private final BlockEntry SOUR_BERRY_SACK = ModBlocks.HELPER.newBuilder("sour_berry_sack", RotatedPillarBlock::new, Block.Properties.create(Material.WOOL, MaterialColor.ORANGE_TERRACOTTA).hardnessAndResistance(0.5F).sound(SoundType.CLOTH)).build();
    private final BlockEntry REEDS_BUNDLE = ModBlocks.HELPER.newBuilder("reeds_block", RotatedPillarBlock::new, Block.Properties.create(Material.WOOD).hardnessAndResistance(0.5F).sound(SoundType.WOOD)).withItemGroup(ItemGroup.BUILDING_BLOCKS).build();

    private final List<QuarkWoodBlockSet> WOOD_BLOCK_SETS = Lists.newArrayList(APPLE, HAZEL);

    @Override
    public boolean matchesItemSearch(ItemStack stack) {
        return ChestSearchingModule.namesMatch(stack);
    }

    @Override
    public void register(IEventBus bus) {
        TerraIncognita.LOGGER.info("Integrating with Quark...");

        bus.addListener(this::setup);
        MinecraftForge.EVENT_BUS.addGenericListener(ItemStack.class, this::onAttachItemCapabilities);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            bus.addListener(this::registerBlockColorHandlers);
            bus.addListener(this::registerItemColorHandlers);
        });
    }

    public void setup(FMLCommonSetupEvent event) {
        for (QuarkWoodBlockSet set : WOOD_BLOCK_SETS) {
			TIWoodPostBlock post = (TIWoodPostBlock) set.POST.getBlock();
			post.strippedBlock = set.STRIPPED_POST.getBlock();

            DataUtil.registerFlammable(set.VERTICAL_PLANKS, 5, 20);
            DataUtil.registerFlammable(set.BOOKSHELF, 30, 20);
            DataUtil.registerFlammable(set.POST, 5, 20);
            DataUtil.registerFlammable(set.STRIPPED_POST, 5, 20);
            DataUtil.registerFlammable(set.HEDGE, 5, 20);
            DataUtil.registerFlammable(set.LEAF_CARPET, 30, 60);

            FuelHandler.registerFuel(set.VERTICAL_PLANKS, 300);
            FuelHandler.registerFuel(set.BOOKSHELF, 300);
            FuelHandler.registerFuel(set.LADDER, 300);
            FuelHandler.registerFuel(set.POST, 300);
            FuelHandler.registerFuel(set.STRIPPED_POST, 300);

            ModRecipes.registerCompostable(set.LEAF_CARPET, 0.2f);
        }

        DataUtil.registerFlammable(BLOSSOMING_APPLE_HEDGE, 5, 20);
        DataUtil.registerFlammable(BLOSSOMING_APPLE_LEAF_CARPET, 30, 60);
        DataUtil.registerFlammable(HAZELNUT_SACK, 30, 60);
        DataUtil.registerFlammable(SOUR_BERRY_SACK, 30, 60);
        DataUtil.registerFlammable(REEDS_BUNDLE, 30, 60);

        FuelHandler.registerFuel(REEDS_BUNDLE, 900);

        ModRecipes.registerCompostable(BLOSSOMING_APPLE_LEAF_CARPET, 0.2f);
        ModRecipes.registerCompostable(SOUR_BERRY_SACK, 1);
        ModRecipes.registerCompostable(HAZELNUT_SACK, 1);
        ModRecipes.registerCompostable(REEDS_BUNDLE, 1);
    }

    @OnlyIn(Dist.CLIENT)
    public void registerBlockColorHandlers(ColorHandlerEvent.Block event) {
        BlockColors colors = event.getBlockColors();

        colors.register((state, reader, pos, color) -> reader != null && pos != null ? BiomeColors.getFoliageColor(reader, pos) : FoliageColors.get(0.5D, 1.0D),
            APPLE.LEAF_CARPET.getBlock(), BLOSSOMING_APPLE_LEAF_CARPET.getBlock(), APPLE.HEDGE.getBlock(), BLOSSOMING_APPLE_HEDGE.getBlock(),
            HAZEL.LEAF_CARPET.getBlock(), HAZEL.HEDGE.getBlock()
        );
    }

    @OnlyIn(Dist.CLIENT)
    public void registerItemColorHandlers(ColorHandlerEvent.Item event) {
        ItemColors colors = event.getItemColors();

        ColorHandler.registerDefaultItemColors(colors, event.getBlockColors(),
            APPLE.LEAF_CARPET, BLOSSOMING_APPLE_LEAF_CARPET, APPLE.HEDGE, BLOSSOMING_APPLE_HEDGE,
            HAZEL.LEAF_CARPET, HAZEL.HEDGE
        );
    }

    public void onAttachItemCapabilities(AttachCapabilitiesEvent<ItemStack> event) {
        if (event.getObject().getItem() == ModBlocks.BASKET.getItem()) {
            event.addCapability(BASKET_DROP_IN_CAP, new BasketDropIn());
        }
    }

    @Override
    public boolean canEditSign(ItemStack heldStack) {
        return ModuleLoader.INSTANCE.isModuleEnabled(SignEditingModule.class) && (!SignEditingModule.requiresEmptyHand || heldStack.isEmpty());
    }

    @Override
    public boolean canLanternConnect(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return state.get(LanternBlock.HANGING) && (worldIn.getBlockState(pos.up()).getBlock() instanceof TIWoodPostBlock);
    }

    @Override
    public boolean canBurnVineTips() {
        return ModuleLoader.INSTANCE.isModuleEnabled(BurnVinesModule.class);
    }
}
