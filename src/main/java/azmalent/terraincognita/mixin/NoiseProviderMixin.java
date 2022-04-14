package azmalent.terraincognita.mixin;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.registry.ModBlocks;
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseProvider;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraftforge.common.util.Lazy;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.ArrayList;
import java.util.List;

@Mixin(NoiseProvider.class)
public class NoiseProviderMixin {
    private static final List<BlockState> FLOWER_FOREST_BLOCKSTATES = List.of(
        Blocks.DANDELION.defaultBlockState(), Blocks.POPPY.defaultBlockState(), Blocks.ALLIUM.defaultBlockState(),
        Blocks.AZURE_BLUET.defaultBlockState(), Blocks.RED_TULIP.defaultBlockState(),
        Blocks.ORANGE_TULIP.defaultBlockState(), Blocks.WHITE_TULIP.defaultBlockState(),
        Blocks.PINK_TULIP.defaultBlockState(), Blocks.OXEYE_DAISY.defaultBlockState(),
        Blocks.CORNFLOWER.defaultBlockState(), Blocks.LILY_OF_THE_VALLEY.defaultBlockState()
    );

    @Shadow @Mutable @Final protected List<BlockState> states;

    private boolean isFlowerForestProvider;
    private static final Lazy<List<BlockState>> flowerForestBlockStates = Lazy.of(() -> {
        var states = new ArrayList<>(FLOWER_FOREST_BLOCKSTATES);
        if (TIConfig.Flora.fieldFlowers.get()) {
            states.add(ModBlocks.CHICORY.defaultBlockState());
            states.add(ModBlocks.YARROW.defaultBlockState());
            states.add(ModBlocks.DAFFODIL.defaultBlockState());
        }

        if (TIConfig.Flora.forestFlowers.get()) {
            states.add(ModBlocks.WILD_GARLIC.defaultBlockState());
            states.add(ModBlocks.FOXGLOVE.defaultBlockState());
            states.add(ModBlocks.PINK_PRIMROSE.defaultBlockState());
            states.add(ModBlocks.PURPLE_PRIMROSE.defaultBlockState());
            states.add(ModBlocks.YELLOW_PRIMROSE.defaultBlockState());
        }

        return List.copyOf(states);
    });

    @Inject(method = "<init>", at = @At("RETURN"))
    private void constructorInject(long p_191442_, NormalNoise.NoiseParameters p_191443_, float p_191444_, List<BlockState> p_191445_, CallbackInfo ci) {
        isFlowerForestProvider = states.containsAll(FLOWER_FOREST_BLOCKSTATES);
    }

    @Inject(method = "getRandomState(Ljava/util/List;D)Lnet/minecraft/world/level/block/state/BlockState;", at = @At("HEAD"), cancellable = true)
    void getRandomState(List<BlockState> states, double scale, CallbackInfoReturnable<BlockState> cir) {
        if (isFlowerForestProvider) {
            double d0 = Mth.clamp((1.0D + scale) / 2.0D, 0.0D, 0.9999D);
            BlockState flower = flowerForestBlockStates.get().get((int)(d0 * (double)flowerForestBlockStates.get().size()));
            cir.setReturnValue(flower);
        }
    }
}