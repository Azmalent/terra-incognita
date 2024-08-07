package azmalent.terraincognita.common.lootmodifier;

import azmalent.terraincognita.TIServerConfig;
import azmalent.terraincognita.core.registry.ModItems;
import com.google.gson.JsonObject;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.common.loot.LootModifier;

import javax.annotation.Nonnull;
import java.util.List;

public class FernLootModifier extends LootModifier {
    public FernLootModifier(LootItemCondition[] conditionsIn) {
        super(conditionsIn);
    }

    @Nonnull
    @Override
    protected List<ItemStack> doApply(List<ItemStack> generatedLoot, LootContext context) {
        if (generatedLoot.isEmpty() && context.getRandom().nextFloat() < TIServerConfig.fiddleheadDropChance.get()) {
            ItemStack fiddlehead = new ItemStack(ModItems.FERN_FIDDLEHEAD.get());
            generatedLoot.add(fiddlehead);
        }

        return generatedLoot;
    }

    public static class Serializer extends GlobalLootModifierSerializer<FernLootModifier> {
        @Override
        public FernLootModifier read(ResourceLocation name, JsonObject json, LootItemCondition[] conditionsIn) {
            return new FernLootModifier(conditionsIn);
        }

        @Override
        public JsonObject write(FernLootModifier instance) {
            return makeConditions(instance.conditions);
        }
    }
}
