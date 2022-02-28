package azmalent.terraincognita.common.lootmodifier;

import com.google.gson.JsonObject;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

public class OakLeavesLootModifier extends LootModifier {
    public OakLeavesLootModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        return generatedLoot.stream().filter(stack -> stack.getItem() != (Items.APPLE)).collect(Collectors.toList());
    }

    public static class Serializer extends GlobalLootModifierSerializer<OakLeavesLootModifier> {
        @Override
        public OakLeavesLootModifier read(ResourceLocation name, JsonObject json, LootItemCondition[] conditionsIn) {
            return new OakLeavesLootModifier(conditionsIn);
        }

        @Override
        public JsonObject write(OakLeavesLootModifier instance) {
            return makeConditions(instance.conditions);
        }
    }
}
