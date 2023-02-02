package azmalent.terraincognita.common.registry;

import azmalent.cuneiform.registry.BlockEntityEntry;
import azmalent.terraincognita.common.blockentity.BasketBlockEntity;
import azmalent.terraincognita.common.blockentity.TIChestBlockEntity;
import azmalent.terraincognita.common.blockentity.TITrappedChestBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static azmalent.terraincognita.TerraIncognita.REG_HELPER;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = REG_HELPER.getOrCreateRegistry(ForgeRegistries.BLOCK_ENTITIES);

    public static final BlockEntityEntry<TIChestBlockEntity> CHEST = REG_HELPER.createBlockEntity("chest", TIChestBlockEntity::new, ModWoodTypes.getBlocks(type -> type.CHEST));
    public static final BlockEntityEntry<TITrappedChestBlockEntity> TRAPPED_CHEST = REG_HELPER.createBlockEntity("trapped_chest", TITrappedChestBlockEntity::new, ModWoodTypes.getBlocks(type -> type.TRAPPED_CHEST));

    public static final BlockEntityEntry<BasketBlockEntity> BASKET = REG_HELPER.createBlockEntity("basket", BasketBlockEntity::new, ModBlocks.BASKET);
}
