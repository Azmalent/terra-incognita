package azmalent.terraincognita.common.integration.quark;

import azmalent.cuneiform.lib.compat.ModProxyImpl;
import azmalent.cuneiform.lib.registry.BlockEntry;
import azmalent.cuneiform.lib.registry.BlockRenderType;
import azmalent.cuneiform.lib.util.DataUtil;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.client.event.ColorHandler;
import azmalent.terraincognita.common.event.FuelHandler;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModRecipes;
import azmalent.terraincognita.common.integration.quark.block.TIHedgeBlock;
import azmalent.terraincognita.common.integration.quark.block.TILeafCarpetBlock;
import azmalent.terraincognita.common.integration.quark.block.TIWoodPostBlock;
import com.google.common.collect.Lists;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import vazkii.quark.base.module.ModuleLoader;
import vazkii.quark.content.client.module.ChestSearchingModule;
import vazkii.quark.content.tweaks.module.SignEditingModule;

import java.util.List;

@ModProxyImpl("quark")
public class QuarkIntegration implements IQuarkIntegration {
    private static final ResourceLocation BASKET_DROP_IN_CAP = TerraIncognita.prefix("basket_drop_in");

    private QuarkWoodBlockSet APPLE = new QuarkWoodBlockSet("apple", MaterialColor.WOOD, MaterialColor.ORANGE_TERRACOTTA);
    private BlockEntry BLOSSOMING_APPLE_LEAF_CARPET = ModBlocks.HELPER.newBuilder("blossoming_apple_leaf_carpet", TILeafCarpetBlock::new).cutoutMippedRender().build();
    private BlockEntry BLOSSOMING_APPLE_HEDGE = ModBlocks.HELPER.newBuilder("blossoming_apple_hedge", () -> new TIHedgeBlock(MaterialColor.ORANGE_TERRACOTTA)).cutoutMippedRender().build();;

    private QuarkWoodBlockSet HAZEL = new QuarkWoodBlockSet("hazel", MaterialColor.WOOD, MaterialColor.BROWN);
    private BlockEntry HAZELNUT_SACK = ModBlocks.HELPER.newBuilder("hazelnut_sack", Block.Properties.create(Material.WOOL, MaterialColor.BROWN).hardnessAndResistance(0.5F).sound(SoundType.CLOTH)).build();
    
    private List<QuarkWoodBlockSet> WOOD_BLOCK_SETS = Lists.newArrayList(APPLE, HAZEL);

    @Override
    public boolean matchesItemSearch(ItemStack stack) {
        return ChestSearchingModule.namesMatch(stack);
    }

    @SuppressWarnings("ConstantConditions")
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
        
            set.initFuelValues(FuelHandler.fuelValues);
            set.initFlammability();

            ModRecipes.registerCompostable(set.LEAF_CARPET, 0.2f);
        }

        DataUtil.registerFlammable(BLOSSOMING_APPLE_HEDGE, 5, 20);
        ModRecipes.registerCompostable(BLOSSOMING_APPLE_LEAF_CARPET, 0.2f);

        DataUtil.registerFlammable(HAZELNUT_SACK, 5, 20);
        ModRecipes.registerCompostable(HAZELNUT_SACK, 1);
    }

    @OnlyIn(Dist.CLIENT)
    public void registerBlockColorHandlers(ColorHandlerEvent.Block event) {
        BlockColors colors = event.getBlockColors();

        colors.register((state, reader, pos, color) -> ColorHandler.APPLE_LEAVES_COLOR, APPLE.LEAF_CARPET.getBlock(), BLOSSOMING_APPLE_LEAF_CARPET.getBlock(), APPLE.HEDGE.getBlock(), BLOSSOMING_APPLE_HEDGE.getBlock());
        colors.register((state, reader, pos, color) -> ColorHandler.HAZEL_LEAVES_COLOR, HAZEL.LEAF_CARPET.getBlock(), HAZEL.HEDGE.getBlock());
    }

    @OnlyIn(Dist.CLIENT)
    public void registerItemColorHandlers(ColorHandlerEvent.Item event) {
        ItemColors colors = event.getItemColors();

        colors.register((stack, index) -> index > 0 ? -1 : ColorHandler.APPLE_LEAVES_COLOR, APPLE.LEAF_CARPET, BLOSSOMING_APPLE_LEAF_CARPET, APPLE.HEDGE, BLOSSOMING_APPLE_HEDGE);
        colors.register((stack, index) -> index > 0 ? -1 : ColorHandler.HAZEL_LEAVES_COLOR, HAZEL.LEAF_CARPET, HAZEL.HEDGE);
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
}
