package azmalent.terraincognita.common.world.blockstateprovider;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.registry.ModBlockStateProviders;
import azmalent.terraincognita.common.registry.ModBlocks;
import com.google.common.collect.Lists;
import com.mojang.serialization.Codec;
import net.minecraft.block.BlockState;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.BlockStateProviderType;
import net.minecraftforge.common.util.Lazy;

import javax.annotation.Nonnull;
import java.util.Random;

public class AlpineFlowerBlockStateProvider extends BlockStateProvider {
    public static final AlpineFlowerBlockStateProvider INSTANCE = new AlpineFlowerBlockStateProvider();
    public static final Codec<AlpineFlowerBlockStateProvider> CODEC = Codec.unit(() -> INSTANCE);

    private static final Lazy<BlockState[]> FLOWERS = Lazy.of(
        () -> Lists.newArrayList(ModBlocks.SAXIFRAGE, ModBlocks.ALPINE_PINK, ModBlocks.GENTIAN).stream().map(flower -> flower.getBlock().getDefaultState()).toArray(BlockState[]::new)
    );

    private static final Lazy<BlockState> EDELWEISS = Lazy.of(() -> ModBlocks.EDELWEISS.getBlock().getDefaultState());

    @Nonnull
    @Override
    protected BlockStateProviderType<?> getProviderType() {
        return ModBlockStateProviders.ALPINE_FLOWERS.get();
    }

    @Nonnull
    @Override
    public BlockState getBlockState(@Nonnull Random randomIn, @Nonnull BlockPos blockPosIn) {
        if (blockPosIn.getY() >= TIConfig.Flora.edelweissMinimumY.get() && randomIn.nextBoolean()) {
            return EDELWEISS.get();
        }

        return Util.getRandomObject(FLOWERS.get(), randomIn);
    }
}
