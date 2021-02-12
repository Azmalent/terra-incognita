package azmalent.terraincognita.common.integration.quark;

import azmalent.cuneiform.lib.compat.ModProxyImpl;
import azmalent.cuneiform.lib.registry.BlockEntry;
import azmalent.cuneiform.lib.registry.BlockRenderType;
import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.client.event.ColorHandler;
import azmalent.terraincognita.common.event.FuelHandler;
import azmalent.terraincognita.common.init.ModBlocks;
import azmalent.terraincognita.common.init.ModRecipes;
import azmalent.terraincognita.mixin.accessor.FireBlockAccessor;
import com.google.common.collect.Sets;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.renderer.color.BlockColors;
import net.minecraft.client.renderer.color.ItemColors;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorldReader;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import vazkii.quark.base.module.ModuleLoader;
import vazkii.quark.content.client.module.ChestSearchingModule;
import vazkii.quark.content.tweaks.module.SignEditingModule;

import java.util.Set;

@ModProxyImpl("quark")
public class QuarkIntegration implements IQuarkIntegration {
    private QuarkWoodBlockSet APPLE;
    private BlockEntry BLOSSOMING_APPLE_LEAF_CARPET;
    private BlockEntry BLOSSOMING_APPLE_HEDGE;

    private QuarkWoodBlockSet HAZEL;
    private BlockEntry HAZELNUT_SACK;
    
    private Set<QuarkWoodBlockSet> WOOD_BLOCK_SETS = Sets.newHashSet();

    @Override
    public boolean matchesItemSearch(ItemStack stack) {
        return ChestSearchingModule.namesMatch(stack);
    }

    @SuppressWarnings("ConstantConditions")
    @Override
    public void register(IEventBus bus) {
        if (TIConfig.Trees.apple.get()) {
            APPLE = new QuarkWoodBlockSet(ModBlocks.HELPER, "apple", MaterialColor.WOOD, MaterialColor.ORANGE_TERRACOTTA);
            BLOSSOMING_APPLE_LEAF_CARPET = ModBlocks.HELPER.newBuilder("blossoming_apple_leaf_carpet", LeafCarpetBlock::new).withRenderType(BlockRenderType.CUTOUT_MIPPED).build();
            BLOSSOMING_APPLE_HEDGE = ModBlocks.HELPER.newBuilder("blossoming_apple_hedge", () -> new HedgeBlock(MaterialColor.ORANGE_TERRACOTTA)).withRenderType(BlockRenderType.CUTOUT_MIPPED).build();

            WOOD_BLOCK_SETS.add(APPLE);
        }

        if (TIConfig.Trees.hazel.get()) {
            HAZEL = new QuarkWoodBlockSet(ModBlocks.HELPER, "hazel", MaterialColor.WOOD, MaterialColor.BROWN);
            HAZELNUT_SACK = ModBlocks.HELPER.newBuilder("hazelnut_sack", Block.Properties.create(Material.WOOL, MaterialColor.BROWN).hardnessAndResistance(0.5F).sound(SoundType.CLOTH)).build();

            WOOD_BLOCK_SETS.add(HAZEL);
        }

        bus.addListener(this::setup);

        DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> {
            bus.addListener(this::registerBlockColorHandlers);
            bus.addListener(this::registerItemColorHandlers);
        });
    }

    public void setup(FMLCommonSetupEvent event) {
        FireBlockAccessor fire = (FireBlockAccessor) Blocks.FIRE;

        for (QuarkWoodBlockSet set : WOOD_BLOCK_SETS) {
			WoodenPostBlock post = (WoodenPostBlock) set.POST.getBlock();
			post.strippedBlock = set.STRIPPED_POST.getBlock();
        
            set.initFuelValues(FuelHandler.fuelValues);
            set.initFlammability();

            ModRecipes.registerCompostable(set.LEAF_CARPET, 0.2f);
        }

        if (TIConfig.Trees.apple.get()) {
            fire.TI_SetFireInfo(BLOSSOMING_APPLE_HEDGE.getBlock(), 5, 20);
        }

        if (TIConfig.Trees.hazel.get()) {
            fire.TI_SetFireInfo(HAZELNUT_SACK.getBlock(), 5, 20);
            ModRecipes.registerCompostable(HAZELNUT_SACK, 1);
        }

        ModRecipes.registerCompostable(BLOSSOMING_APPLE_LEAF_CARPET, 0.2f);
    }

    @OnlyIn(Dist.CLIENT)
    public void registerBlockColorHandlers(ColorHandlerEvent.Block event) {
        BlockColors colors = event.getBlockColors();

        if(APPLE != null) {
            colors.register((state, reader, pos, color) -> ColorHandler.APPLE_LEAVES_COLOR, APPLE.LEAF_CARPET.getBlock(), BLOSSOMING_APPLE_LEAF_CARPET.getBlock(), APPLE.HEDGE.getBlock(), BLOSSOMING_APPLE_HEDGE.getBlock());
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void registerItemColorHandlers(ColorHandlerEvent.Item event) {
        ItemColors colors = event.getItemColors();

        if (APPLE != null) {
            colors.register((stack, index) -> index > 0 ? -1 : ColorHandler.APPLE_LEAVES_COLOR, APPLE.LEAF_CARPET.getItem(), BLOSSOMING_APPLE_LEAF_CARPET.getItem(), APPLE.HEDGE.getItem(), BLOSSOMING_APPLE_HEDGE.getItem());
        }
    }

    @Override
    public boolean canEditSign(ItemStack heldStack) {
        return ModuleLoader.INSTANCE.isModuleEnabled(SignEditingModule.class) && (!SignEditingModule.requiresEmptyHand || heldStack.isEmpty());
    }

    @Override
    public boolean canLanternConnect(BlockState state, IWorldReader worldIn, BlockPos pos) {
        return state.get(LanternBlock.HANGING) && worldIn.getBlockState(pos.up()).getBlock() instanceof WoodenPostBlock;
    }
}
