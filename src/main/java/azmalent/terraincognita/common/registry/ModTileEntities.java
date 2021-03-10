package azmalent.terraincognita.common.registry;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.client.renderer.tile.ModChestRenderer;
import azmalent.terraincognita.client.renderer.tile.ModSignRenderer;
import azmalent.terraincognita.common.tile.BasketTileEntity;
import azmalent.terraincognita.common.tile.ModChestTileEntity;
import azmalent.terraincognita.common.tile.ModSignTileEntity;
import azmalent.terraincognita.common.tile.ModTrappedChestTileEntity;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Arrays;
import java.util.function.Supplier;

@SuppressWarnings({"ConstantConditions", "unchecked"})
public class ModTileEntities {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, TerraIncognita.MODID);

    public static RegistryObject<TileEntityType<ModChestTileEntity>> CHEST = register("chest", ModChestTileEntity::new, ModWoodTypes.getChests());
    public static RegistryObject<TileEntityType<ModTrappedChestTileEntity>> TRAPPED_CHEST = register("trapped_chest", ModTrappedChestTileEntity::new, ModWoodTypes.getTrappedChests());
    public static RegistryObject<TileEntityType<ModSignTileEntity>> SIGN = register("sign", ModSignTileEntity::new, ModWoodTypes.getSigns());

    public static RegistryObject<TileEntityType<BasketTileEntity>> BASKET = register("basket", BasketTileEntity::new, ModBlocks.BASKET::getBlock);

    @SuppressWarnings("ConstantConditions")
    private static <T extends TileEntity> RegistryObject<TileEntityType<T>> register(String id, Supplier<T> constructor, Supplier<Block>... blockSuppliers) {
        return TILE_ENTITIES.register(id, () -> {
            Block[] blocks = Arrays.stream(blockSuppliers).map(Supplier::get).toArray(Block[]::new);
            return TileEntityType.Builder.create(constructor, blocks).build(null);
        });
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerRenderers() {
        ClientRegistry.bindTileEntityRenderer(CHEST.get(), ModChestRenderer<ModChestTileEntity>::new);
        ClientRegistry.bindTileEntityRenderer(TRAPPED_CHEST.get(), ModChestRenderer<ModTrappedChestTileEntity>::new);
        ClientRegistry.bindTileEntityRenderer(SIGN.get(), ModSignRenderer::new);
    }
}
