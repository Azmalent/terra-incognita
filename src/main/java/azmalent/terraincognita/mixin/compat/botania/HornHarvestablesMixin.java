package azmalent.terraincognita.mixin.compat.botania;

import azmalent.terraincognita.common.block.fruit.AbstractFruitBlock;
import azmalent.terraincognita.common.block.plant.CaribouMossWallBlock;
import azmalent.terraincognita.common.block.plant.HangingMossBlock;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import vazkii.botania.api.block.IHornHarvestable;

/**
 * @reason Adds IHornHarvestable implementation to Terra Incognita blocks.
 * It has to be a mixin because Forge no longer has optional interfaces.
 */
@Mixin({HangingMossBlock.class, CaribouMossWallBlock.class, AbstractFruitBlock.class})
public class HornHarvestablesMixin implements IHornHarvestable {
    @Override
    public boolean canHornHarvest(Level level, BlockPos blockPos, ItemStack itemStack, EnumHornType hornType) {
        if (hornType == EnumHornType.WILD) {
            BlockState blockState = level.getBlockState(blockPos);
            if (blockState.getBlock() instanceof AbstractFruitBlock) {
                return blockState.getValue(AbstractFruitBlock.AGE) == 7;
            }

            return true;
        }

        return false;
    }

    @Override
    public boolean hasSpecialHornHarvest(Level level, BlockPos blockPos, ItemStack itemStack, EnumHornType enumHornType) {
        return false;
    }

    @Override
    public void harvestByHorn(Level level, BlockPos blockPos, ItemStack itemStack, EnumHornType enumHornType) {
        //NO-OP
    }
}
