package azmalent.terraincognita.common.registry;

import azmalent.cuneiform.lib.registry.BlockEntityEntry;
import azmalent.terraincognita.client.renderer.blockentity.ModChestRenderer;
import azmalent.terraincognita.client.renderer.blockentity.ModSignRenderer;
import azmalent.terraincognita.common.tile.BasketBlockEntity;
import azmalent.terraincognita.common.tile.ModChestBlockEntity;
import azmalent.terraincognita.common.tile.ModSignBlockEntity;
import azmalent.terraincognita.common.tile.ModTrappedChestBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import static azmalent.terraincognita.TerraIncognita.REG_HELPER;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = REG_HELPER.getOrCreateRegistry(ForgeRegistries.BLOCK_ENTITIES);

    public static final BlockEntityEntry<ModChestBlockEntity> CHEST = REG_HELPER.createBlockEntity("chest", ModChestBlockEntity::new, ModWoodTypes.getChests());
    public static final BlockEntityEntry<ModTrappedChestBlockEntity> TRAPPED_CHEST = REG_HELPER.createBlockEntity("trapped_chest", ModTrappedChestBlockEntity::new, ModWoodTypes.getTrappedChests());
    public static final BlockEntityEntry<ModSignBlockEntity> SIGN = REG_HELPER.createBlockEntity("sign", ModSignBlockEntity::new, ModWoodTypes.getSigns());

    public static final BlockEntityEntry<BasketBlockEntity> BASKET = REG_HELPER.createBlockEntity("basket", BasketBlockEntity::new, ModBlocks.BASKET);

    @OnlyIn(Dist.CLIENT)
    public static void registerRenderers(EntityRenderersEvent.RegisterRenderers event) {
        event.registerBlockEntityRenderer(CHEST.get(), ModChestRenderer::new);
        event.registerBlockEntityRenderer(TRAPPED_CHEST.get(), ModChestRenderer::new);
        event.registerBlockEntityRenderer(SIGN.get(), ModSignRenderer::new);
    }
}
