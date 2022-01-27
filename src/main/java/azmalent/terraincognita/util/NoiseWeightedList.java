package azmalent.terraincognita.util;

import com.google.common.collect.Lists;
import net.minecraft.world.level.biome.newbiome.context.Context;

import java.util.List;

public class NoiseWeightedList<T> {
    private record Node<T>(T value, int weight) { }

    private final List<Node<T>> items = Lists.newArrayList();
    private int totalWeight = 0;

    public void add(T value, int weight) {
        if (weight < 0) {
            throw new IllegalArgumentException("Weight must be positive!");
        } else if (weight == 0) {
            return;
        }

        items.add(new Node<>(value, weight));
        totalWeight += weight;
    }

    public boolean isEmpty() {
        return totalWeight == 0;
    }

    public T getRandomItem(Context random) {
        if (items.isEmpty()) return null;

        int i = random.nextRandom(totalWeight);
        for (Node<T> item : items) {
            if (i < item.weight) {
                return item.value;
            }

            i -= item.weight;
        }

        throw new AssertionError("Method didn't return a value!");
    }
}
