package azmalent.terraincognita.common.registry;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.world.surfacebuilder.LushPlainsSurfaceBuilder;
import azmalent.terraincognita.common.world.surfacebuilder.MuskegSurfaceBuilder;
import net.minecraft.world.level.levelgen.surfacebuilders.SurfaceBuilder;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class ModSurfaceBuilders {
    public static final DeferredRegister<SurfaceBuilder<?>> BUILDERS = DeferredRegister.create(ForgeRegistries.SURFACE_BUILDERS, TerraIncognita.MODID);

    public static final RegistryObject<LushPlainsSurfaceBuilder> LUSH_PLAINS = BUILDERS.register("lush_plains", LushPlainsSurfaceBuilder::new);
    public static final RegistryObject<MuskegSurfaceBuilder> MUSKEG = BUILDERS.register("muskeg", MuskegSurfaceBuilder::new);
}
