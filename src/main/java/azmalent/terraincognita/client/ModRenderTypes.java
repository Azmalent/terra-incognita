package azmalent.terraincognita.client;

import azmalent.cuneiform.lib.registry.BlockEntry;
import azmalent.terraincognita.TIConfig;
import net.minecraft.client.renderer.RenderState;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.RenderTypeLookup;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import static azmalent.terraincognita.common.init.ModBlocks.*;
import static net.minecraft.client.renderer.RenderState.*;

@OnlyIn(Dist.CLIENT)
public class ModRenderTypes {
    public static void init() {
        FLOWERS.forEach(ModRenderTypes::setCutoutRender);
        setCutoutRender(REEDS);
        setCutoutRender(PINK_LOTUS, WHITE_LOTUS, YELLOW_LOTUS, SMALL_LILYPAD, CALTROPS, ROOTS);
    }

    private static void setCutoutRender(BlockEntry... blockEntries) {
        for (BlockEntry blockEntry : blockEntries) {
            if (blockEntry != null) {
                RenderTypeLookup.setRenderLayer(blockEntry.getBlock(), RenderType.getCutout());
            }
        }
    }

    private static void setCutoutRender(PottablePlantEntry... plants) {
        for (PottablePlantEntry plant : plants) {
            if (plant != null) {
                RenderTypeLookup.setRenderLayer(plant.getBlock(), RenderType.getCutout());
                RenderTypeLookup.setRenderLayer(plant.getPotted(), RenderType.getCutout());
            }
        }
    }
}
