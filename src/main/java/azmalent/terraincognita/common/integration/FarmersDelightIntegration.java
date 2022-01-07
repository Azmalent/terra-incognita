package azmalent.terraincognita.common.integration;

import azmalent.cuneiform.lib.compat.IModIntegration;
import azmalent.cuneiform.lib.compat.ModProxyImpl;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.block.woodtypes.ModWoodType;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModWoodTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.eventbus.api.IEventBus;
import vectorwing.farmersdelight.blocks.PantryBlock;

@ModProxyImpl("farmersdelight")
public class FarmersDelightIntegration implements IModIntegration {
    private void createPantry(String id) {
        ModBlocks.HELPER.newBuilder(id, PantryBlock::new, Block.Properties.copy(Blocks.BARREL)).build();
    }

    @Override
    public void register(IEventBus bus) {
        TerraIncognita.LOGGER.info("Integrating with Farmer's Delight...");

        for (ModWoodType woodType : ModWoodTypes.VALUES) {
            createPantry(woodType.name + "_pantry");
        }
    }
}
