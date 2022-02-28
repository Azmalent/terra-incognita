package azmalent.terraincognita.integration;

import azmalent.cuneiform.lib.integration.IModProxy;
import azmalent.cuneiform.lib.integration.IntegrationImpl;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.woodtype.ModWoodType;
import azmalent.terraincognita.common.registry.ModWoodTypes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.eventbus.api.IEventBus;
import vectorwing.farmersdelight.common.block.CabinetBlock;

@IntegrationImpl("farmersdelight")
public class FarmersDelightIntegration implements IModProxy {
    @Override
    public void register(IEventBus bus) {
        TerraIncognita.LOGGER.info("Integrating with Farmer's Delight...");

        for (ModWoodType woodType : ModWoodTypes.VALUES) {
            TerraIncognita.REG_HELPER.createBlock(woodType.name + "_pantry", CabinetBlock::new, Block.Properties.copy(Blocks.BARREL));
        }
    }
}
