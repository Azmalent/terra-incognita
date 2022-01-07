package azmalent.terraincognita.mixin.compat.botania;

import azmalent.terraincognita.common.block.plants.CaribouMossWallBlock;
import azmalent.terraincognita.common.block.plants.HangingPlantBlock;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import vazkii.botania.api.item.IHornHarvestable;

import vazkii.botania.api.item.IHornHarvestable.EnumHornType;

@Mixin({HangingPlantBlock.class, CaribouMossWallBlock.class})
public class HornHarvestablesMixin implements IHornHarvestable {
    @Override
    public boolean canHornHarvest(Level world, BlockPos blockPos, ItemStack itemStack, EnumHornType enumHornType) {
        return enumHornType == EnumHornType.WILD;
    }

    @Override
    public boolean hasSpecialHornHarvest(Level world, BlockPos blockPos, ItemStack itemStack, EnumHornType enumHornType) {
        return false;
    }

    @Override
    public void harvestByHorn(Level world, BlockPos blockPos, ItemStack itemStack, EnumHornType enumHornType) {
        //NO-OP
    }
}
