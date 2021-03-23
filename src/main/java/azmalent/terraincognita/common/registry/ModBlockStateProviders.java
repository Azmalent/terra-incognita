package azmalent.terraincognita.common.registry;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.world.blockstateprovider.AlpineFlowerBlockStateProvider;
import azmalent.terraincognita.mixin.accessor.BlockStateProviderTypeAccessor;
import com.mojang.serialization.Codec;
import net.minecraft.world.gen.blockstateprovider.BlockStateProvider;
import net.minecraft.world.gen.blockstateprovider.BlockStateProviderType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModBlockStateProviders {
    public static final DeferredRegister<BlockStateProviderType<?>> PROVIDERS = DeferredRegister.create(ForgeRegistries.BLOCK_STATE_PROVIDER_TYPES, TerraIncognita.MODID);

    public static final RegistryObject<BlockStateProviderType<?>> ALPINE_FLOWERS = register("alpine_flower_provider", AlpineFlowerBlockStateProvider.CODEC);

    private static <P extends BlockStateProvider> RegistryObject<BlockStateProviderType<?>> register(String id, Codec<P> codec) {
        return PROVIDERS.register(id, () -> BlockStateProviderTypeAccessor.ti_constructor(codec));
    }
}
