package azmalent.terraincognita.core.event;

import azmalent.terraincognita.TIConfig.Flora;
import azmalent.terraincognita.TIServerConfig;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.block.plant.SmallLilyPadBlock;
import azmalent.terraincognita.core.registry.ModBlocks;
import azmalent.terraincognita.util.WorldGenUtil;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.BonemealableBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Fluids;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.Random;

@Mod.EventBusSubscriber(modid = TerraIncognita.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BonemealHandler {
    private static final int MAX_LILYPAD_GROWING_DEPTH = 4;

    private static boolean isInShallowWater(Level world, BlockPos pos) {
        if (world.getFluidState(pos.above()).getType() != Fluids.WATER) return false;

        for (int i = 2; i < MAX_LILYPAD_GROWING_DEPTH + 1; i++) {
            if (world.getFluidState(pos.above(i)).getType() != Fluids.WATER) {
                return world.isEmptyBlock(pos.above(i));
            }
        }

        return false;
    }

    private static void placeRandomLilyPad(Level world, BlockPos pos, boolean isJungle) {
        BlockState blockState;
        Random rand = world.getRandom();

        if (isJungle && Flora.lotus.get() && rand.nextFloat() < 0.5) {
            blockState = Util.getRandom(ModBlocks.LOTUSES, rand).defaultBlockState();
        } else if (Flora.smallLilyPads.get() && rand.nextFloat() < Flora.smallLilyPadChance.get()) {
            blockState = ModBlocks.SMALL_LILY_PAD.defaultBlockState().setValue(SmallLilyPadBlock.LILY_PADS, 1 + rand.nextInt(4));
        } else {
            blockState = Blocks.LILY_PAD.defaultBlockState();
        }

        world.setBlockAndUpdate(pos, blockState);
    }

    @SubscribeEvent
    public static void onBonemealUnderwater(BonemealEvent event) {
        if (!TIServerConfig.bonemealLilypadGrowing.get()) {
            return;
        }

        Level world = event.getWorld();
        BlockPos pos = event.getPos();

        //Don't grow lilypads when bonemealing growable plants
        if (world.getBlockState(pos).getBlock() instanceof BonemealableBlock) return;

        Biome.BiomeCategory category = WorldGenUtil.getProperBiomeCategory(world.getBiome(pos));
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
                    placeRandomLilyPad(world, lilypadPos, category == Biome.BiomeCategory.JUNGLE);
                }
            }
        }
    }
}
