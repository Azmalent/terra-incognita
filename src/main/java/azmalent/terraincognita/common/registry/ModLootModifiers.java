package azmalent.terraincognita.common.registry;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.data.loot.FernLootModifier;
import azmalent.terraincognita.common.data.loot.OakLeavesLootModifier;
import net.minecraftforge.common.loot.GlobalLootModifierSerializer;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings("unused")
public class ModLootModifiers {
    public static final DeferredRegister<GlobalLootModifierSerializer<?>> LOOT_MODIFIERS = DeferredRegister.create(ForgeRegistries.LOOT_MODIFIER_SERIALIZERS, TerraIncognita.MODID);

    public static final RegistryObject<GlobalLootModifierSerializer<?>> FIDDLEHEADS = LOOT_MODIFIERS.register("fern_fiddlehead", FernLootModifier.Serializer::new);
    public static final RegistryObject<GlobalLootModifierSerializer<?>> REMOVE_OAK_APPLES = LOOT_MODIFIERS.register("remove_oak_apples", OakLeavesLootModifier.Serializer::new);
}
