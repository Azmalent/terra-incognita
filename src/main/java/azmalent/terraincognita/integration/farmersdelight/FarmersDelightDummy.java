package azmalent.terraincognita.integration.farmersdelight;

import azmalent.cuneiform.integration.IntegrationDummy;
import azmalent.cuneiform.registry.BlockEntry;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.block.woodset.TIDummyCabinetBlock;
import azmalent.terraincognita.common.woodtype.TIWoodType;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;

@IntegrationDummy("farmersdelight")
public final class FarmersDelightDummy implements IFarmersDelightProxy {
    @Override
    public void register(IEventBus bus) {
        TerraIncognita.LOGGER.info("Farmer's Delight is not installed. Terra Incognita will register dummy cabinet blocks.");
    }

    @Override
    public BlockEntry<? extends Block> registerCabinet(TIWoodType woodType) {
        return TerraIncognita.REGISTRY_HELPER.createBlock(woodType.name + "_cabinet", TIDummyCabinetBlock::new).build();
    }
}
