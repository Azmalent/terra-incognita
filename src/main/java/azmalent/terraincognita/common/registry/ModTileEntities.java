package azmalent.terraincognita.common.registry;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.client.renderer.tile.ModChestRenderer;
import azmalent.terraincognita.client.renderer.tile.ModSignRenderer;
import azmalent.terraincognita.common.tile.BasketTileEntity;
import azmalent.terraincognita.common.tile.ModChestTileEntity;
import azmalent.terraincognita.common.tile.ModSignTileEntity;
import azmalent.terraincognita.common.tile.ModTrappedChestTileEntity;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
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
    public static final DeferredRegister<BlockEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, TerraIncognita.MODID);

    public static final RegistryObject<BlockEntityType<ModChestTileEntity>> CHEST = register("chest", ModChestTileEntity::new, ModWoodTypes.getChests());
    public static final RegistryObject<BlockEntityType<ModTrappedChestTileEntity>> TRAPPED_CHEST = register("trapped_chest", ModTrappedChestTileEntity::new, ModWoodTypes.getTrappedChests());
    public static final RegistryObject<BlockEntityType<ModSignTileEntity>> SIGN = register("sign", ModSignTileEntity::new, ModWoodTypes.getSigns());

    public static final RegistryObject<BlockEntityType<BasketTileEntity>> BASKET = register("basket", BasketTileEntity::new, ModBlocks.BASKET::getBlock);

    @SuppressWarnings("ConstantConditions")
    private static <T extends BlockEntity> RegistryObject<BlockEntityType<T>> register(String id, Supplier<T> constructor, Supplier<Block>... blockSuppliers) {
        return TILE_ENTITIES.register(id, () -> {
            Block[] blocks = Arrays.stream(blockSuppliers).map(Supplier::get).toArray(Block[]::new);
            return BlockEntityType.Builder.of(constructor, blocks).build(null);
        });
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerRenderers() {
        ClientRegistry.bindTileEntityRenderer(CHEST.get(), ModChestRenderer<ModChestTileEntity>::new);
        ClientRegistry.bindTileEntityRenderer(TRAPPED_CHEST.get(), ModChestRenderer<ModTrappedChestTileEntity>::new);
        ClientRegistry.bindTileEntityRenderer(SIGN.get(), ModSignRenderer::new);
    }
}
