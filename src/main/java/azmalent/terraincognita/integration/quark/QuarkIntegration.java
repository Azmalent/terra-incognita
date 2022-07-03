package azmalent.terraincognita.integration.quark;

import azmalent.cuneiform.common.data.FuelHandler;
import azmalent.cuneiform.integration.IntegrationImpl;
import azmalent.cuneiform.registry.BlockEntry;
import azmalent.cuneiform.util.DataUtil;
import azmalent.cuneiform.util.ItemUtil;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.event.ToolInteractionHandler;
import azmalent.terraincognita.common.registry.ModWoodTypes;
import azmalent.terraincognita.common.block.woodset.TIHedgeBlock;
import azmalent.terraincognita.common.block.woodset.TILeafCarpetBlock;
import azmalent.terraincognita.common.block.woodset.TIWoodPostBlock;
import com.google.common.collect.Lists;
import net.minecraft.client.color.block.BlockColors;
import net.minecraft.client.color.item.ItemColors;
import net.minecraft.client.renderer.BiomeColors;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.FoliageColor;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.LanternBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import vazkii.quark.base.module.ModuleLoader;
import vazkii.quark.content.building.module.ShearVinesModule;
import vazkii.quark.content.client.module.ChestSearchingModule;

import java.util.List;

@IntegrationImpl("quark")
public final class QuarkIntegration implements IQuarkProxy {
    @Override
    public void register(IEventBus bus) {
        TerraIncognita.LOGGER.info("Integrating with Quark...");
    }

    @Override
    public boolean namesMatch(ItemStack stack) {
        return ChestSearchingModule.namesMatch(stack);
    }

    @Override
    public boolean canCutVines() {
        return ModuleLoader.INSTANCE.isModuleEnabled(ShearVinesModule.class);
    }
}
