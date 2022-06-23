package azmalent.terraincognita.common.world.stateprovider;

import azmalent.cuneiform.registry.BlockEntry;
import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.registry.ModBlockStateProviders;
import azmalent.terraincognita.common.registry.ModBlocks;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.minecraftforge.common.util.Lazy;

import javax.annotation.Nonnull;
import java.util.Random;
import java.util.stream.Stream;

public class AlpineFlowerStateProvider extends BlockStateProvider {
    public static final AlpineFlowerStateProvider INSTANCE = new AlpineFlowerStateProvider();
    public static final Codec<AlpineFlowerStateProvider> CODEC = Codec.unit(() -> INSTANCE);

    private static final Lazy<BlockState[]> FLOWERS = Lazy.of(
        () -> Stream.of(ModBlocks.ALPINE_PINK, ModBlocks.ASTER, ModBlocks.GENTIAN)
            .map(BlockEntry::defaultBlockState)
            .toArray(BlockState[]::new)
    );

    private static final Lazy<BlockState> EDELWEISS = Lazy.of(ModBlocks.EDELWEISS::defaultBlockState);

    @Nonnull
    @Override
    protected BlockStateProviderType<?> type() {
        return ModBlockStateProviders.ALPINE_FLOWERS.get();
    }

    @Nonnull
    @Override
    public BlockState getState(@Nonnull Random randomIn, @Nonnull BlockPos blockPosIn) {
        if (blockPosIn.getY() >= TIConfig.Flora.edelweissMinimumY.get() && randomIn.nextBoolean()) {
            return EDELWEISS.get();
        }

        return Util.getRandom(FLOWERS.get(), randomIn);
    }
}
