package azmalent.terraincognita.common.registry;

import azmalent.cuneiform.lib.registry.BlockEntityEntry;
import azmalent.terraincognita.common.blockentity.BasketBlockEntity;
import azmalent.terraincognita.common.blockentity.ModChestBlockEntity;
import azmalent.terraincognita.common.blockentity.ModSignBlockEntity;
import azmalent.terraincognita.common.blockentity.ModTrappedChestBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static azmalent.terraincognita.TerraIncognita.REG_HELPER;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = REG_HELPER.getOrCreateRegistry(ForgeRegistries.BLOCK_ENTITIES);

    public static final BlockEntityEntry<ModChestBlockEntity> CHEST = REG_HELPER.createBlockEntity("chest", ModChestBlockEntity::new, ModWoodTypes.getChests());
    public static final BlockEntityEntry<ModTrappedChestBlockEntity> TRAPPED_CHEST = REG_HELPER.createBlockEntity("trapped_chest", ModTrappedChestBlockEntity::new, ModWoodTypes.getTrappedChests());
    public static final BlockEntityEntry<ModSignBlockEntity> SIGN = REG_HELPER.createBlockEntity("sign", ModSignBlockEntity::new, ModWoodTypes.getSigns());

    public static final BlockEntityEntry<BasketBlockEntity> BASKET = REG_HELPER.createBlockEntity("basket", BasketBlockEntity::new, ModBlocks.BASKET);
}
