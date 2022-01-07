package azmalent.terraincognita.common.event;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.block.plants.SmallLilypadBlock;
import azmalent.terraincognita.common.registry.ModBlocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.material.Fluids;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.BonemealEvent;

import java.util.Random;

public class BonemealHandler {
    private static final int MAX_LILYPAD_GROWING_DEPTH = 4;

    public static void registerListeners() {
        if (TIConfig.Misc.bonemealLilypadGrowing.get()) {
            MinecraftForge.EVENT_BUS.addListener(BonemealHandler::onBonemealUnderwater);
        }
    }

    private static boolean isInShallowWater(Level world, BlockPos pos) {
        if (world.getFluidState(pos.above()).getType() != Fluids.WATER) return false;

        for (int i = 2; i < MAX_LILYPAD_GROWING_DEPTH + 1; i++) {
            if (world.getFluidState(pos.above(i)).getType() != Fluids.WATER) {
                return world.isEmptyBlock(pos.above(i));
            }
        }

        return false;
    }

    private static void placeRandomLilypad(Level world, BlockPos pos, boolean isJungle) {
        BlockState blockState;
        Random rand = world.getRandom();
        float f = rand.nextFloat();

        if (f < 0.5 && isJungle && TIConfig.Flora.lotus.get()) {
            blockState = ModBlocks.LOTUSES.get(rand.nextInt(3)).getBlock().defaultBlockState();
        } else if (f < 0.5 && !isJungle && TIConfig.Flora.smallLilypad.get()) {
            blockState = ModBlocks.SMALL_LILY_PAD.getBlock().defaultBlockState().setValue(SmallLilypadBlock.LILYPADS, 1 + rand.nextInt(4));
        }
        else blockState = Blocks.LILY_PAD.defaultBlockState();

        world.setBlockAndUpdate(pos, blockState);
    }

    public static void onBonemealUnderwater(BonemealEvent event) {
        Level world = event.getWorld();
        BlockPos pos = event.getPos();

        //Don't grow lilypads when bonemealing growable plants
        if (world.getBlockState(pos).getBlock() instanceof BonemealableBlock) return;

        Biome.BiomeCategory category = world.getBiome(pos).getBiomeCategory();
        if(category != Biome.BiomeCategory.SWAMP && category != Biome.BiomeCategory.JUNGLE) {
            return;
        }

        if (isInShallowWater(world, pos)) {
            Random rand = world.getRandom();
            for (int i = 0; i < 4; i++) {
                int x = rand.nextInt(4) - rand.nextInt(4);
                int y = rand.nextInt(MAX_LILYPAD_GROWING_DEPTH) + 2;
                int z = rand.nextInt(4) - rand.nextInt(4);

                BlockPos lilypadPos = pos.offset(x, y, z);
                if (world.isEmptyBlock(lilypadPos) && world.getFluidState(lilypadPos.below()).getType() == Fluids.WATER) {
                    placeRandomLilypad(world, lilypadPos, category == Biome.BiomeCategory.JUNGLE);
                }
            }
        }
    }
}
