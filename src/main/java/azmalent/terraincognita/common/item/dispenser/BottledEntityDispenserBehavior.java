package azmalent.terraincognita.common.item.dispenser;

import azmalent.terraincognita.common.item.BottledEntityItem;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.core.dispenser.DefaultDispenseItemBehavior;
import net.minecraft.core.BlockSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;

import javax.annotation.Nonnull;

public final class BottledEntityDispenserBehavior extends DefaultDispenseItemBehavior {
    @Nonnull
    @Override
    protected ItemStack execute(BlockSource source, ItemStack bottle) {
        Item item = bottle.getItem();
        if (!(item instanceof BottledEntityItem)) {
            return bottle;
        }

        ServerLevel world = source.getLevel();
        Direction facing = source.getBlockState().getValue(DispenserBlock.FACING);
        BlockPos spawnPos = source.getPos().relative(facing);

        Entity entity = ((BottledEntityItem<?>) item).type.get().spawn(world, bottle, null, spawnPos, MobSpawnType.DISPENSER, facing != Direction.UP, false);
        if (entity != null) {
            BottledEntityItem.initReleasedEntity((LivingEntity) entity, bottle);
        }

        return new ItemStack(Items.GLASS_BOTTLE);
    }
}
