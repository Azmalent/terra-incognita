package azmalent.terraincognita.common.registry;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.world.stateprovider.AlpineFlowerStateProvider;
import azmalent.terraincognita.mixin.accessor.BlockStateProviderTypeAccessor;
import com.mojang.serialization.Codec;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProviderType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockStateProviders {
    public static final DeferredRegister<BlockStateProviderType<?>> PROVIDERS = TerraIncognita.REG_HELPER.getOrCreateRegistry(ForgeRegistries.BLOCK_STATE_PROVIDER_TYPES);

    public static final RegistryObject<BlockStateProviderType<?>> ALPINE_FLOWERS = register("alpine_flower_provider", AlpineFlowerStateProvider.CODEC);

    @SuppressWarnings("SameParameterValue")
    private static <P extends BlockStateProvider> RegistryObject<BlockStateProviderType<?>> register(String id, Codec<P> codec) {
        return PROVIDERS.register(id, () -> BlockStateProviderTypeAccessor.ti_constructor(codec));
    }
}
