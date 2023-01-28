package azmalent.terraincognita.mixin;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.registry.ModBlocks;
import net.minecraft.Util;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.NoiseThresholdProvider;
import net.minecraft.world.level.levelgen.synth.NormalNoise;
import net.minecraftforge.common.util.Lazy;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Random;

/**
 * @reason Adds Terra Incognita flowers to plains.
 */
@Mixin(NoiseThresholdProvider.class)
public class NoiseThresholdProviderMixin {
    private static final List<BlockState> LOW_STATES = List.of(
        Blocks.ORANGE_TULIP.defaultBlockState(), Blocks.RED_TULIP.defaultBlockState(),
        Blocks.PINK_TULIP.defaultBlockState(), Blocks.WHITE_TULIP.defaultBlockState()
    );

    private static final List<BlockState> HIGH_STATES = List.of(
        Blocks.POPPY.defaultBlockState(), Blocks.AZURE_BLUET.defaultBlockState(),
        Blocks.OXEYE_DAISY.defaultBlockState(), Blocks.CORNFLOWER.defaultBlockState()
    );

    @Shadow @Mutable @Final private List<BlockState> lowStates;
    @Shadow @Mutable @Final private List<BlockState> highStates;

    private boolean isPlainsProvider;
    private static final Lazy<List<BlockState>> plainsLowStates = Lazy.of(() -> {
        var states = new ArrayList<>(LOW_STATES);
        if (TIConfig.Flora.fieldFlowers.get()) {
            states.add(ModBlocks.DAFFODIL.defaultBlockState());
        }

        return List.copyOf(states);
    });

    private static final Lazy<List<BlockState>> plainsHighStates = Lazy.of(() -> {
        var states = new ArrayList<>(HIGH_STATES);
        if (TIConfig.Flora.fieldFlowers.get()) {
            states.add(ModBlocks.CHICORY.defaultBlockState());
            states.add(ModBlocks.YARROW.defaultBlockState());
        }

        return List.copyOf(states);
    });

    @Inject(method = "<init>", at = @At("RETURN"))
    private void constructorInject(long p_191471_, NormalNoise.NoiseParameters p_191472_, float p_191473_, float p_191474_, float p_191475_, BlockState defaultState, List<BlockState> p_191477_, List<BlockState> p_191478_, CallbackInfo ci) {
        var lowSet = new HashSet<>(lowStates);
        var highSet = new HashSet<>(highStates);
        isPlainsProvider = defaultState.is(Blocks.DANDELION) && lowSet.containsAll(LOW_STATES) && highSet.containsAll(HIGH_STATES);
    }

    @SuppressWarnings("unchecked")
    @Redirect(method = "getState", at = @At(value = "INVOKE", target = "Lnet/minecraft/Util;getRandom(Ljava/util/List;Ljava/util/Random;)Ljava/lang/Object;"))
    private <T> T getRandomBlockstate(List<T> selections, Random random) {
        if (isPlainsProvider) {
            if (selections == lowStates) return (T) Util.getRandom(plainsLowStates.get(), random);
            else if (selections == highStates) return (T) Util.getRandom(plainsHighStates.get(), random);
        }

        return Util.getRandom(selections, random);
    }
}
