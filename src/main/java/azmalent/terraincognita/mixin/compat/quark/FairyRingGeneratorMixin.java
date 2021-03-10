package azmalent.terraincognita.mixin.compat.quark;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.registry.ModBlocks;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.ChunkGenerator;
import net.minecraft.world.gen.WorldGenRegion;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import vazkii.quark.content.world.gen.FairyRingGenerator;
import vazkii.quark.content.world.module.FairyRingsModule;

import java.util.Random;

@Mixin(FairyRingGenerator.class)
public class FairyRingGeneratorMixin {
    //TODO: rewrite this mixin properly
    @Inject(method = "generateChunk", at = @At(value = "HEAD"), cancellable = true, remap = false)
    private void generateChunk(WorldGenRegion worldIn, ChunkGenerator generator, Random rand, BlockPos corner, CallbackInfo ci) {
        int x = corner.getX() + rand.nextInt(16);
        int z = corner.getZ() + rand.nextInt(16);
        BlockPos center = new BlockPos(x, 128, z);

        Biome biome = worldIn.getBiomeManager().getBiome(center);

        Biome.Category category = biome.getCategory();
        double chance = 0;
        if(category == Biome.Category.FOREST) {
            chance = FairyRingsModule.forestChance;
        } else if(category == Biome.Category.PLAINS) {
            chance = FairyRingsModule.plainsChance;
        } else if (category == Biome.Category.SAVANNA) {
            chance = TIConfig.Integration.Quark.savannaFairyRingChance.get();
        }

        if(rand.nextDouble() < chance) {
            BlockPos pos = center;
            BlockState state = worldIn.getBlockState(pos);

            while(state.getMaterial() != Material.ORGANIC && pos.getY() > 30) {
                pos = pos.down();
                state = worldIn.getBlockState(pos);
            }

            if(state.getMaterial() == Material.ORGANIC)
                FairyRingGenerator.spawnFairyRing(worldIn, pos.down(), rand);
        }

        ci.cancel();
    }

    @Redirect(method = "spawnFairyRing", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/Block;getDefaultState()Lnet/minecraft/block/BlockState;"))
    private static BlockState selectFlower(Block self, IWorld world, BlockPos pos, Random rand) {
        if (world.getBiome(pos).getCategory() == Biome.Category.SAVANNA && TIConfig.Flora.savannaFlowers.get()) {
            return ModBlocks.MARIGOLD.getBlock().getDefaultState();
        }

        return self.getDefaultState();
    }
}
