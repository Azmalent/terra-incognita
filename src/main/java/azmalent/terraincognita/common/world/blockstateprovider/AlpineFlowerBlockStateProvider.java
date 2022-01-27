package azmalent.terraincognita.common.world.blockstateprovider;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.registry.ModBlockStateProviders;
import azmalent.terraincognita.common.registry.ModBlocks;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.minecraftforge.common.util.Lazy;

import javax.annotation.Nonnull;
import java.util.Random;

public class AlpineFlowerBlockStateProvider extends BlockStateProvider {
    public static final AlpineFlowerBlockStateProvider INSTANCE = new AlpineFlowerBlockStateProvider();
    public static final Codec<AlpineFlowerBlockStateProvider> CODEC = Codec.unit(() -> INSTANCE);

    private static final Lazy<BlockState[]> FLOWERS = Lazy.of(
        () -> Lists.newArrayList(ModBlocks.YELLOW_SAXIFRAGE, ModBlocks.ALPINE_PINK, ModBlocks.GENTIAN).stream().map(flower -> flower.getBlock().defaultBlockState()).toArray(BlockState[]::new)
    );

    private static final Lazy<BlockState> EDELWEISS = Lazy.of(() -> ModBlocks.EDELWEISS.getBlock().defaultBlockState());

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
