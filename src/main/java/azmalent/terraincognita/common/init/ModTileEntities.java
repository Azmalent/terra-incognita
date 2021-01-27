package azmalent.terraincognita.common.init;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.tile.BasketTileEntity;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class ModTileEntities {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, TerraIncognita.MODID);

    public static RegistryObject<TileEntityType<BasketTileEntity>> BASKET;

    static {
        if (TIConfig.Tools.basket.get()) {
            assert ModBlocks.BASKET != null;
            BASKET = registerTileEntity("basket", BasketTileEntity::new, ModBlocks.BASKET::getBlock);
        }
    }

    @SuppressWarnings("ConstantConditions")
    private static <T extends TileEntity> RegistryObject<TileEntityType<T>> registerTileEntity(String id, Supplier<T> constructor, Supplier<Block> blockSupplier) {
        return TILE_ENTITIES.register(id, () -> TileEntityType.Builder.create(constructor, blockSupplier.get()).build(null));
    }
}
