package azmalent.terraincognita.integration;

import azmalent.cuneiform.integration.IModProxy;
import azmalent.cuneiform.integration.IntegrationImpl;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.registry.ModWoodTypes;
import azmalent.terraincognita.common.woodtype.TIWoodType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.eventbus.api.IEventBus;
import vectorwing.farmersdelight.common.block.CabinetBlock;

@IntegrationImpl("farmersdelight")
public final class FarmersDelightIntegration implements IModProxy {
    @Override
    public void register(IEventBus bus) {
        TerraIncognita.LOGGER.info("Integrating with Farmer's Delight...");

        for (TIWoodType woodType : ModWoodTypes.VALUES) {
            TerraIncognita.REGISTRY_HELPER.createBlock(woodType.name + "_cabinet",
                CabinetBlock::new, Block.Properties.copy(Blocks.BARREL)
            ).build();
        }
    }
}
