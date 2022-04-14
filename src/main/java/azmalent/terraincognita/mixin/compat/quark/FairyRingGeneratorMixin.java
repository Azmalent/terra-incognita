package azmalent.terraincognita.mixin.compat.quark;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.registry.ModBlocks;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.server.level.WorldGenRegion;
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
    @SuppressWarnings("deprecation")
    @Inject(method = "generateChunk", at = @At(value = "HEAD"), cancellable = true, remap = false)
    private void generateChunk(WorldGenRegion level, ChunkGenerator generator, Random rand, BlockPos corner, CallbackInfo ci) {
        int x = corner.getX() + rand.nextInt(16);
        int z = corner.getZ() + rand.nextInt(16);
        BlockPos center = new BlockPos(x, 128, z);

        Holder<Biome> biome = level.getBiomeManager().getBiome(center);

        Biome.BiomeCategory category = Biome.getBiomeCategory(biome);
        double chance = 0;
        if(category == Biome.BiomeCategory.FOREST) {
            chance = FairyRingsModule.forestChance;
        } else if(category == Biome.BiomeCategory.PLAINS) {
            chance = FairyRingsModule.plainsChance;
        } else if (category == Biome.BiomeCategory.SAVANNA) {
            chance = TIConfig.Integration.Quark.savannaFairyRingChance.get();
        }

        if(rand.nextDouble() < chance) {
            BlockPos pos = center;
            BlockState state = level.getBlockState(pos);

            while(state.getMaterial() != Material.GRASS && pos.getY() > 30) {
                pos = pos.below();
                state = level.getBlockState(pos);
            }

            if(state.getMaterial() == Material.GRASS)
                FairyRingGenerator.spawnFairyRing(level, pos.below(), rand);
        }

        ci.cancel();
    }

    @SuppressWarnings("deprecation")
    @Redirect(method = "spawnFairyRing", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/Block;defaultBlockState()Lnet/minecraft/world/level/block/state/BlockState;"))
    private static BlockState selectFlower(Block self, LevelAccessor world, BlockPos pos, Random rand) {
        if (Biome.getBiomeCategory(world.getBiome(pos)) == Biome.BiomeCategory.SAVANNA && TIConfig.Flora.savannaFlowers.get()) {
            return ModBlocks.MARIGOLD.getBlock().defaultBlockState();
        }

        return self.defaultBlockState();
    }
}
