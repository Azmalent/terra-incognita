package azmalent.terraincognita.common.block.plant;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.WaterlilyBlock;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.PlantType;

public class TILilyPadBlock extends WaterlilyBlock implements IPlantable {
    public TILilyPadBlock(Properties props) {
        super(props);
    }

    @Override
    public PlantType getPlantType(BlockGetter level, BlockPos pos) {
        return PlantType.WATER;
    }
}
