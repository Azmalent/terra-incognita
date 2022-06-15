package azmalent.terraincognita.common.event;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModWoodTypes;
import azmalent.terraincognita.common.woodtype.TIWoodType;
import azmalent.terraincognita.mixin.accessor.HoeItemAccessor;
import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import net.minecraft.world.item.HoeItem;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Map;

//Based on Quark ToolInteractionHandler
@Mod.EventBusSubscriber(modid = TerraIncognita.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ToolInteractionHandler {
    public static final Map<Block, Block> AXE_STRIPPABLES = Maps.newHashMap();
    public static final Map<Block, Block> SHOVEL_FLATTENABLES = Maps.newHashMap();

    public static void initToolInteractions() {
        for (TIWoodType woodType : ModWoodTypes.VALUES) {
            AXE_STRIPPABLES.put(woodType.LOG.get(), woodType.STRIPPED_LOG.get());
            AXE_STRIPPABLES.put(woodType.WOOD.get(), woodType.STRIPPED_WOOD.get());
        }

        SHOVEL_FLATTENABLES.put(ModBlocks.FLOWERING_GRASS.get(), Blocks.DIRT_PATH);

        var tillables = Maps.newHashMap(HoeItemAccessor.ti_getTillables());
        tillables.put(ModBlocks.PEAT.get(), Pair.of(
            HoeItem::onlyIfAirAbove, HoeItem.changeIntoState(ModBlocks.TILLED_PEAT.defaultBlockState()))
        );

        tillables.put(ModBlocks.FLOWERING_GRASS.get(), Pair.of(
            HoeItem::onlyIfAirAbove, HoeItem.changeIntoState(Blocks.FARMLAND.defaultBlockState()))
        );
        HoeItemAccessor.ti_setTillables(tillables);
    }

    @SubscribeEvent
    public static void onToolInteraction(BlockEvent.BlockToolModificationEvent event) {
        if (event.isSimulated()) {
            return;
        }

        Map<Block, Block> map = null;
        if (event.getToolAction() == ToolActions.AXE_STRIP) {
            map = AXE_STRIPPABLES;
        } else if (event.getToolAction() == ToolActions.SHOVEL_FLATTEN) {
            map = SHOVEL_FLATTENABLES;
        }

        BlockState state = event.getState();
        Block block = state.getBlock();
        if (map != null && map.containsKey(block)) {
            Block finalBlock = map.get(block);
            BlockState finalState = copyBlockState(state, finalBlock);
            event.setFinalState(finalState);
        }
    }
    @SuppressWarnings({ "unchecked", "rawtypes" })
    private static BlockState copyBlockState(BlockState source, Block target) {
        BlockState outState = target.defaultBlockState();
        for (Property property : source.getProperties()) {
            if (outState.hasProperty(property)) {
                outState = outState.setValue(property, source.getValue(property));
            }
        }

        return outState;
    }
}
