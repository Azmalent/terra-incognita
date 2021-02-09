package azmalent.terraincognita.common.init;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.client.renderer.tile.ModChestRenderer;
import azmalent.terraincognita.client.renderer.tile.ModSignRenderer;
import azmalent.terraincognita.common.block.blocksets.ModWoodType;
import azmalent.terraincognita.common.tile.BasketTileEntity;
import azmalent.terraincognita.common.tile.ModChestTileEntity;
import azmalent.terraincognita.common.tile.ModSignTileEntity;
import azmalent.terraincognita.common.tile.ModTrappedChestTileEntity;
import com.google.common.collect.Lists;
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
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

@SuppressWarnings({"ConstantConditions", "unchecked"})
public class ModTileEntities {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITIES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, TerraIncognita.MODID);


    public static RegistryObject<TileEntityType<ModChestTileEntity>> CHEST = register("chest", ModChestTileEntity::new,
        getWoodBlockSuppliers((list, wood) -> { list.add(wood.CHEST::getBlock); })
    );

    public static RegistryObject<TileEntityType<ModTrappedChestTileEntity>> TRAPPED_CHEST = register("trapped_chest", ModTrappedChestTileEntity::new,
        getWoodBlockSuppliers((list, wood) -> { list.add(wood.TRAPPED_CHEST::getBlock); })
    );

    public static RegistryObject<TileEntityType<ModSignTileEntity>> SIGN = register("sign", ModSignTileEntity::new,
        getWoodBlockSuppliers((list, wood) -> { list.add(wood.STANDING_SIGN::getBlock); list.add(wood.WALL_SIGN::getBlock); })
    );

    public static RegistryObject<TileEntityType<BasketTileEntity>> BASKET;

    static {
        if (TIConfig.Tools.basket.get()) {
            BASKET = register("basket", BasketTileEntity::new, ModBlocks.BASKET::getBlock);
        }
    }

    private static Supplier<Block>[] getWoodBlockSuppliers(BiConsumer<List<Supplier<Block>>, ModWoodType> func) {
        List<Supplier<Block>> suppliers = Lists.newArrayList();
        ModWoodTypes.VALUES.stream().filter(ModWoodType::isEnabled).forEach(woodType -> func.accept(suppliers, woodType));

        Supplier<Block>[] array = new Supplier[suppliers.size()];
        return suppliers.toArray(array);
    }

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
