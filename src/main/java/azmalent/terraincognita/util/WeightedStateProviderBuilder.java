package azmalent.terraincognita.util;

import net.minecraft.util.random.SimpleWeightedRandomList;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.levelgen.feature.stateproviders.WeightedStateProvider;

import java.util.function.Supplier;

public final class WeightedStateProviderBuilder {
    private final SimpleWeightedRandomList.Builder<BlockState> listBuilder;

    public WeightedStateProviderBuilder() {
        this.listBuilder = new SimpleWeightedRandomList.Builder<BlockState>();
    }

    public WeightedStateProviderBuilder add(BlockState state, int weight) {
        listBuilder.add(state, weight);
        return this;
    }

    public WeightedStateProviderBuilder add(Block block, int weight) {
        listBuilder.add(block.defaultBlockState(), weight);
        return this;
    }

    public <T extends Block> WeightedStateProviderBuilder add(Supplier<T> block, int weight) {
        listBuilder.add(block.get().defaultBlockState(), weight);
        return this;
    }

    public WeightedStateProvider build() {
        return new WeightedStateProvider(listBuilder.build());
    }
}
