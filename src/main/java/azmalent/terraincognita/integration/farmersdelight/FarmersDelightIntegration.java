package azmalent.terraincognita.integration.farmersdelight;

import azmalent.cuneiform.integration.IntegrationImpl;
import azmalent.cuneiform.registry.BlockEntry;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.woodtype.TIWoodType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.eventbus.api.IEventBus;
import vectorwing.farmersdelight.common.block.CabinetBlock;

@IntegrationImpl("farmersdelight")
public final class FarmersDelightIntegration extends FarmersDelightProxy {
    @Override
    public void register(IEventBus bus) {
        TerraIncognita.LOGGER.info("Integrating with Farmer's Delight...");
        initCabinets();
    }

    @Override
    public BlockEntry<? extends Block> registerCabinet(TIWoodType woodType) {
        return TerraIncognita.REGISTRY_HELPER.createBlock(woodType.name + "_cabinet", CabinetBlock::new, Block.Properties.copy(Blocks.BARREL)).build();
    }
}
