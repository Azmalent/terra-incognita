package azmalent.terraincognita.common.registry;

import azmalent.terraincognita.common.lootmodifier.FernLootModifier;
import azmalent.terraincognita.common.lootmodifier.OakLeavesLootModifier;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static azmalent.terraincognita.TerraIncognita.REG_HELPER;

@SuppressWarnings("unused")
public class ModLootModifiers {
    public static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODIFIERS = REG_HELPER.getOrCreateRegistry(ForgeRegistries.Keys.LOOT_MODIFIER_SERIALIZERS);

    public static final RegistryObject<GlobalLootModifierSerializer<?>> FIDDLEHEADS = LOOT_MODIFIERS.register("fern_fiddlehead", FernLootModifier.Serializer::new);
    public static final RegistryObject<GlobalLootModifierSerializer<?>> REMOVE_OAK_APPLES = LOOT_MODIFIERS.register("remove_oak_apples", OakLeavesLootModifier.Serializer::new);
}
