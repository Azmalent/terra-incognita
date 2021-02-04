package azmalent.terraincognita.common.data.loot;

import azmalent.terraincognita.common.init.ModItems;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.stream.Collectors;

public class OakLeavesLootModifier extends LootModifier {
    public OakLeavesLootModifier(ILootCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        return generatedLoot.stream().filter(stack -> stack.getItem() != (Items.APPLE)).collect(Collectors.toList());
    }

    public static class Serializer extends GlobalLootModifierSerializer<OakLeavesLootModifier> {
        @Override
        public OakLeavesLootModifier read(ResourceLocation name, JsonObject json, ILootCondition[] conditionsIn) {
            return new OakLeavesLootModifier(conditionsIn);
        }

        @Override
        public JsonObject write(OakLeavesLootModifier instance) {
            return makeConditions(instance.conditions);
        }
    }
}
