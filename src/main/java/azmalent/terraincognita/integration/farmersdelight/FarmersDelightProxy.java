package azmalent.terraincognita.integration.farmersdelight;


import azmalent.cuneiform.integration.IModProxy;
import azmalent.cuneiform.registry.BlockEntry;
import azmalent.terraincognita.common.registry.ModWoodTypes;
import azmalent.terraincognita.common.woodtype.TIWoodType;
import net.minecraft.world.level.block.Block;

public sealed abstract class FarmersDelightProxy implements IModProxy permits FarmersDelightDummy, FarmersDelightIntegration {
    public BlockEntry<? extends Block> APPLE_CABINET;
    public BlockEntry<? extends Block> HAZEL_CABINET;
    public BlockEntry<? extends Block> GINKGO_CABINET;
    public BlockEntry<? extends Block> LARCH_CABINET;

    protected final void initCabinets() {
        APPLE_CABINET = registerCabinet(ModWoodTypes.APPLE);
        HAZEL_CABINET = registerCabinet(ModWoodTypes.HAZEL);
        GINKGO_CABINET = registerCabinet(ModWoodTypes.GINKGO);
        LARCH_CABINET = registerCabinet(ModWoodTypes.LARCH);
    }
    abstract protected BlockEntry<? extends Block> registerCabinet(TIWoodType woodType);
}
