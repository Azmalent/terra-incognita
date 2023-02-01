package azmalent.terraincognita.integration.farmersdelight;


import azmalent.cuneiform.integration.IModProxy;
import azmalent.cuneiform.registry.BlockEntry;
import azmalent.terraincognita.common.woodtype.TIWoodType;
import net.minecraft.world.level.block.Block;

public sealed abstract class FarmersDelightProxy implements IModProxy permits FarmersDelightDummy, FarmersDelightIntegration {
    public abstract BlockEntry<? extends Block> createCabinet(TIWoodType woodType);
}
