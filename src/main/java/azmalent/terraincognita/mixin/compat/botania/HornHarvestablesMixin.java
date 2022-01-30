package azmalent.terraincognita.mixin.compat.botania;

import azmalent.terraincognita.common.block.plants.CaribouMossWallBlock;
import azmalent.terraincognita.common.block.plants.HangingPlantBlock;
import azmalent.terraincognita.common.block.trees.AbstractFruitBlock;
import net.minecraft.block.BlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import vazkii.botania.api.item.IHornHarvestable;

@Mixin({HangingPlantBlock.class, CaribouMossWallBlock.class, AbstractFruitBlock.class})
public class HornHarvestablesMixin implements IHornHarvestable {
    @Override
    public boolean canHornHarvest(World world, BlockPos blockPos, ItemStack itemStack, EnumHornType enumHornType) {
        if (enumHornType == EnumHornType.WILD) {
            BlockState blockState = world.getBlockState(blockPos);
            if (blockState.getBlock() instanceof AbstractFruitBlock) {
                return blockState.get(AbstractFruitBlock.AGE) == 7;
            }

            return true;
        }

        return false;
    }

    @Override
    public boolean hasSpecialHornHarvest(World world, BlockPos blockPos, ItemStack itemStack, EnumHornType enumHornType) {
        return false;
    }

    @Override
    public void harvestByHorn(World world, BlockPos blockPos, ItemStack itemStack, EnumHornType enumHornType) {
        //NO-OP
    }
}
