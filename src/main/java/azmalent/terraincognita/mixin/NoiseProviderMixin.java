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
import java.util.HashSet;
import java.util.List;

/**
 * @reason Adds Terra Incognita flowers to flower forests and meadows.
 */
@Mixin(NoiseProvider.class)
public class NoiseProviderMixin {
    @Shadow @Mutable @Final protected List<BlockState> states;

    //Add field & forest flowers to flower forests
    private boolean isFlowerForestProvider;
    private static final List<BlockState> FLOWER_FOREST_STATES = List.of(
        Blocks.DANDELION.defaultBlockState(), Blocks.POPPY.defaultBlockState(), Blocks.ALLIUM.defaultBlockState(),
        Blocks.AZURE_BLUET.defaultBlockState(), Blocks.RED_TULIP.defaultBlockState(),
        Blocks.ORANGE_TULIP.defaultBlockState(), Blocks.WHITE_TULIP.defaultBlockState(),
        Blocks.PINK_TULIP.defaultBlockState(), Blocks.OXEYE_DAISY.defaultBlockState(),
        Blocks.CORNFLOWER.defaultBlockState(), Blocks.LILY_OF_THE_VALLEY.defaultBlockState()
    );

    private static final Lazy<List<BlockState>> MOD_FLOWER_FOREST_STATES = Lazy.of(() -> {
        var states = new ArrayList<>(FLOWER_FOREST_STATES);
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

    //Add common field flowers to meadows
    private boolean isMeadowProvider;
    private static final List<BlockState> MEADOW_STATES = List.of(
        Blocks.TALL_GRASS.defaultBlockState(), Blocks.ALLIUM.defaultBlockState(), Blocks.POPPY.defaultBlockState(),
        Blocks.AZURE_BLUET.defaultBlockState(), Blocks.DANDELION.defaultBlockState(), Blocks.CORNFLOWER.defaultBlockState(),
        Blocks.OXEYE_DAISY.defaultBlockState(), Blocks.GRASS.defaultBlockState()
    );

    private static final Lazy<List<BlockState>> MOD_MEADOW_STATES = Lazy.of(() -> {
        var states = new ArrayList<>(MEADOW_STATES);
        if (TIConfig.Flora.fieldFlowers.get()) {
            states.add(MEADOW_STATES.size() - 1, ModBlocks.CHICORY.defaultBlockState());
            states.add(MEADOW_STATES.size() - 1, ModBlocks.YARROW.defaultBlockState());
        }

        return List.copyOf(states);
    });

    @Inject(method = "<init>", at = @At("RETURN"))
    private void constructorInject(long p_191442_, NormalNoise.NoiseParameters p_191443_, float p_191444_, List<BlockState> p_191445_, CallbackInfo ci) {
        var size = states.size();
        var set = new HashSet<>(states);

        isFlowerForestProvider = (size == FLOWER_FOREST_STATES.size()) && set.containsAll(FLOWER_FOREST_STATES);
        isMeadowProvider = (size == MEADOW_STATES.size()) && set.containsAll(MEADOW_STATES);
    }

    @Inject(method = "getRandomState(Ljava/util/List;D)Lnet/minecraft/world/level/block/state/BlockState;", at = @At("HEAD"), cancellable = true)
    private void getRandomState(List<BlockState> states, double scale, CallbackInfoReturnable<BlockState> cir) {
        double d0 = Mth.clamp((1.0D + scale) / 2.0D, 0.0D, 0.9999D);
        List<BlockState> modifiedStates = null;

        if (isFlowerForestProvider) {
            modifiedStates = MOD_FLOWER_FOREST_STATES.get();
        } else if (isMeadowProvider) {
            modifiedStates = MOD_MEADOW_STATES.get();
        }

        if (modifiedStates != null) {
            BlockState flower = modifiedStates.get((int)(d0 * (double)modifiedStates.size()));
            cir.setReturnValue(flower);
        }
    }
}