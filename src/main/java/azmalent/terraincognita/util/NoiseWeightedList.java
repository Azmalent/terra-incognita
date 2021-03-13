package azmalent.terraincognita.util;

import com.google.common.collect.Lists;
import net.minecraft.world.gen.INoiseRandom;

import java.util.List;
import java.util.Random;

public final class NoiseWeightedList<T> {
    private final List<Node<T>> items = Lists.newArrayList();
    private int totalWeight = 0;

    public void add(T value, int weight) {
        if (weight < 1) {
            throw new IllegalArgumentException("Weight must be positive!");
        }

        items.add(new Node<>(value, weight));
        totalWeight += weight;
    }

    public boolean isEmpty() {
        return totalWeight == 0;
    }

    public T getRandomItem(INoiseRandom random) {
        if (items.isEmpty()) return null;

        int i = random.random(totalWeight);
        for (Node<T> item : items) {
            if (i < item.weight) {
                return item.value;
            }

            i -= item.weight;
        }

        throw new AssertionError("Method didn't return a value!");
    }

    private static class Node<T> {
        private final T value;
        private final int weight;

        private Node(T value, int weight) {
            this.value = value;
            this.weight = weight;
        }
    }
}
