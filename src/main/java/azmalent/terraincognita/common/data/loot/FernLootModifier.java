package azmalent.terraincognita.common.data.loot;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.registry.ModItems;
import com.google.gson.JsonObject;
import net.minecraft.item.ItemStack;
import net.minecraft.loot.LootContext;
import net.minecraft.loot.conditions.ILootCondition;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.List;

public class FernLootModifier extends LootModifier {
    public FernLootModifier(ILootCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        if (generatedLoot.isEmpty() && context.getRandom().nextFloat() < TIConfig.Food.fiddleheadDropChance.get()) {
            ItemStack fiddlehead = new ItemStack(ModItems.FIDDLEHEAD.get());
            generatedLoot.add(fiddlehead);
        }

        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<FernLootModifier> {
        @Override
        public FernLootModifier read(ResourceLocation name, JsonObject json, ILootCondition[] conditionsIn) {
            return new FernLootModifier(conditionsIn);
        }

        @Override
        public JsonObject write(FernLootModifier instance) {
            return makeConditions(instance.conditions);
        }
    }
}
