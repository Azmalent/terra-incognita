package azmalent.terraincognita.common.integration;

import azmalent.cuneiform.lib.compat.IModIntegration;
import azmalent.cuneiform.lib.compat.ModProxyImpl;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.block.woodtypes.ModWoodType;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModWoodTypes;
import com.minecraftabnormals.abnormals_core.common.blocks.AbnormalsBeehiveBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.eventbus.api.IEventBus;

@ModProxyImpl("buzzier_bees")
public class BuzzierBeesIntegration implements IModIntegration {
    private void createBeehive(String id) {
        ModBlocks.HELPER.newBuilder(id, AbnormalsBeehiveBlock::new, Block.Properties.copy(Blocks.BEEHIVE)).build();
    }

    @Override
    public void register(IEventBus bus) {
        TerraIncognita.LOGGER.info("Integrating with Buzzier Bees...");

        for (ModWoodType woodType : ModWoodTypes.VALUES) {
            createBeehive(woodType.name + "_beehive");
        }
    }
}
