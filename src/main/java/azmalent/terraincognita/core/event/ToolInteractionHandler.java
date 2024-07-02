package azmalent.terraincognita.core.event;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.core.registry.ModBlocks;
import azmalent.terraincognita.core.registry.ModWoodTypes;
import azmalent.terraincognita.common.woodtype.TIWoodType;
import com.google.common.collect.Maps;
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
    private static final Map<Block, Block> AXE_INTERACTIONS = Maps.newHashMap();
    private static final Map<Block, Block> SHOVEL_INTERACTIONS = Maps.newHashMap();
    private static final Map<Block, Block> HOE_INTERACTIONS = Maps.newHashMap();

    public static void initToolInteractions() {
        for (TIWoodType woodType : ModWoodTypes.VALUES) {
            AXE_INTERACTIONS.put(woodType.LOG.get(), woodType.STRIPPED_LOG.get());
            AXE_INTERACTIONS.put(woodType.WOOD.get(), woodType.STRIPPED_WOOD.get());
            AXE_INTERACTIONS.put(woodType.POST.get(), woodType.STRIPPED_POST.get());
        }

        SHOVEL_INTERACTIONS.put(ModBlocks.FLOWERING_GRASS.get(), Blocks.DIRT_PATH);

        HOE_INTERACTIONS.put(ModBlocks.FLOWERING_GRASS.get(), Blocks.FARMLAND);
        HOE_INTERACTIONS.put(ModBlocks.PEAT.get(), ModBlocks.TILLED_PEAT.get());
    }

    @SubscribeEvent
    public static void onToolInteraction(BlockEvent.BlockToolModificationEvent event) {
        Map<Block, Block> map = null;
        if (event.getToolAction() == ToolActions.AXE_STRIP) {
            map = AXE_INTERACTIONS;
        } else if (event.getToolAction() == ToolActions.SHOVEL_FLATTEN) {
            map = SHOVEL_INTERACTIONS;
        } else if (event.getToolAction() == ToolActions.HOE_TILL) {
            map = HOE_INTERACTIONS;
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
