package azmalent.terraincognita.common.world.placement;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

public class HeightBiomeFilter extends PlacementFilter {
    private final int minHeight;
    private final int maxHeight;

    private HeightBiomeFilter(int minHeight, int maxHeight) {
        this.minHeight = minHeight;
        this.maxHeight = maxHeight;
    }

    public static HeightBiomeFilter above(int minHeight) {
        return new HeightBiomeFilter(minHeight, 256);
    }

    public static HeightBiomeFilter below(int maxHeight) {
        return new HeightBiomeFilter(0, maxHeight);
    }

    public static HeightBiomeFilter between(int minHeight, int maxHeight) {
        return new HeightBiomeFilter(minHeight, maxHeight);
    }

    @Override
    protected boolean shouldPlace(@NotNull PlacementContext context, @NotNull Random random, BlockPos pos) {
        if(minHeight <= pos.getY() && pos.getY() <= maxHeight) {
            var feature = context.topFeature().orElseThrow(() ->
               new IllegalStateException("Tried to biome check an unregistered feature")
            );

            var biome = context.getLevel().getBiome(pos).value();
            return biome.getGenerationSettings().hasFeature(feature);
        }

        return false;
    }

    @Override
    @NotNull
    public PlacementModifierType<?> type() {
        return PlacementModifierType.BIOME_FILTER;
    }
}
