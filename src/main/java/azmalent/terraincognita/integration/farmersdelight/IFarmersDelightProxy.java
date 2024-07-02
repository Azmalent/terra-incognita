package azmalent.terraincognita.integration.farmersdelight;


import azmalent.cuneiform.integration.IModProxy;
import azmalent.cuneiform.registry.BlockEntry;
import azmalent.terraincognita.common.woodtype.TIWoodType;
import net.minecraft.world.level.block.Block;

public sealed interface IFarmersDelightProxy extends IModProxy permits FarmersDelightDummy, FarmersDelightIntegration {
    BlockEntry<? extends Block> registerCabinet(TIWoodType woodType);
}
