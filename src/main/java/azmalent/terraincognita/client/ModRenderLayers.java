package azmalent.terraincognita.client;

import azmalent.cuneiform.lib.registry.BlockEntry;
import azmalent.terraincognita.common.init.blocksets.PottablePlantEntry;
import azmalent.terraincognita.common.init.blocksets.TIWoodType;
import azmalent.terraincognita.common.integration.ModIntegration;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static azmalent.terraincognita.common.init.ModBlocks.*;

@OnlyIn(Dist.CLIENT)
public class ModRenderLayers {
    public static void init() {
        FLOWERS.forEach(ModRenderLayers::setCutoutRender);
        TALL_PLANTS.forEach(ModRenderLayers::setCutoutRender);
        LOTUSES.forEach(ModRenderLayers::setCutoutRender);
        setCutoutRender(REEDS);
        setCutoutRender(SMALL_LILY_PAD, CALTROPS, ROOTS, MOSS);

        for (TIWoodType woodType : WoodTypes.VALUES) {
            woodType.initRenderLayers();
        }

        setCutoutRender(WoodTypes.APPLE.FRUIT);

        ModIntegration.QUARK.initRenderLayers();
    }

    public static void setCutoutRender(BlockEntry... blockEntries) {
        for (BlockEntry blockEntry : blockEntries) {
            if (blockEntry != null) {
                RenderTypeLookup.setRenderLayer(blockEntry.getBlock(), RenderType.getCutout());
            }
        }
    }

    public static void setCutoutRender(PottablePlantEntry... plants) {
        for (PottablePlantEntry plant : plants) {
            if (plant != null) {
                RenderTypeLookup.setRenderLayer(plant.getBlock(), RenderType.getCutout());
                RenderTypeLookup.setRenderLayer(plant.getPotted(), RenderType.getCutout());
            }
        }
    }
}
