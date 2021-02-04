package azmalent.terraincognita.mixin;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.world.ModTrees;
import azmalent.terraincognita.common.world.feature.config.HugeTreeFeatureConfig;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.trees.OakTree;
import net.minecraft.block.trees.Tree;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.feature.BaseTreeFeatureConfig;
import net.minecraft.world.gen.feature.ConfiguredFeature;
import net.minecraft.world.gen.feature.Features;
import net.minecraft.world.server.ServerWorld;
import org.spongepowered.asm.mixin.Mixin;

import java.util.Random;

@Mixin(OakTree.class)
public abstract class OakTreeMixin extends Tree {
    @Override
    public boolean attemptGrowTree(ServerWorld world, ChunkGenerator chunkGenerator, BlockPos pos, BlockState state, Random rand) {
        if (TIConfig.Trees.bigOaks.get()) {
            for (int i = 0; i >= -1; i--) {
                for (int j = 0; j >= -1; j--) {
                    if (canBigTreeSpawnAt(state, world, pos, i, j)) {
                        return this.growBigTree(world, chunkGenerator, pos, state, rand, i, j);
                    }
                }
            }
        }

        return super.attemptGrowTree(world, chunkGenerator, pos, state, rand);
    }

    private boolean canBigTreeSpawnAt(BlockState blockUnder, IBlockReader worldIn, BlockPos pos, int xOffset, int zOffset) {
        Block block = blockUnder.getBlock();
        return block == worldIn.getBlockState(pos.add(xOffset, 0, zOffset)).getBlock() && block == worldIn.getBlockState(pos.add(xOffset + 1, 0, zOffset)).getBlock() && block == worldIn.getBlockState(pos.add(xOffset, 0, zOffset + 1)).getBlock() && block == worldIn.getBlockState(pos.add(xOffset + 1, 0, zOffset + 1)).getBlock();
    }

    private boolean growBigTree(ServerWorld world, ChunkGenerator chunkGenerator, BlockPos pos, BlockState state, Random rand, int branchX, int branchY) {
        ConfiguredFeature<HugeTreeFeatureConfig, ?> feature = ModTrees.HUGE_OAK;
        feature.config.forcePlacement();
        BlockState air = Blocks.AIR.getDefaultState();
        world.setBlockState(pos.add(branchX, 0, branchY), air, 4);
        world.setBlockState(pos.add(branchX + 1, 0, branchY), air, 4);
        world.setBlockState(pos.add(branchX, 0, branchY + 1), air, 4);
        world.setBlockState(pos.add(branchX + 1, 0, branchY + 1), air, 4);
        if (feature.generate(world, chunkGenerator, rand, pos.add(branchX, 0, branchY))) {
            return true;
        }

        world.setBlockState(pos.add(branchX, 0, branchY), state, 4);
        world.setBlockState(pos.add(branchX + 1, 0, branchY), state, 4);
        world.setBlockState(pos.add(branchX, 0, branchY + 1), state, 4);
        world.setBlockState(pos.add(branchX + 1, 0, branchY + 1), state, 4);
        return false;
    }
}
