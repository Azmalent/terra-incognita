package azmalent.terraincognita.mixin;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CactusBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.Tags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nonnull;
import java.util.Random;

import static net.minecraft.block.CactusBlock.AGE;

@Mixin(CactusBlock.class)
public abstract class CactusBlockMixin extends Block {
    public CactusBlockMixin(Properties properties) {
        super(properties);
    }

    private boolean isCactus(BlockState state) {
        return state.isIn(Blocks.CACTUS) || state.getBlock() == ModBlocks.SMOOTH_CACTUS.getBlock();
    }

    private int getHeight(ServerWorld world, BlockPos pos) {
        int height = 1;
        while (isCactus(world.getBlockState(pos.down(height)))) {
            height++;
        }

        return height;
    }

    private int getMaxHeight(BlockPos pos) {
        //TODO: optimize
        if (TIConfig.Misc.plantHeightVariation.get()) {
            return new Random(pos.toLong()).nextInt(3) + 2;
        }

        return 3;
    }

    private void grow(ServerWorld world, BlockPos pos, BlockPos up) {
        BlockState cactus = Blocks.CACTUS.getDefaultState();

        world.setBlockState(pos, cactus, 4);
        world.setBlockState(up, cactus);
        cactus.neighborChanged(world, up, Blocks.CACTUS, pos, false);
    }

    private boolean canGrowFlower(ServerWorld world, BlockPos pos, Random random) {
        if (!TIConfig.Flora.cactusFlowers.get()) return false;
        return random.nextInt(300) == 0 && world.canBlockSeeSky(pos);
    }

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    private void randomTick(BlockState state, ServerWorld worldIn, BlockPos pos, Random random, CallbackInfo ci) {
        int height = getHeight(worldIn, pos);
        int maxHeight = getMaxHeight(pos);
        int age = state.get(AGE);
        BlockPos up = pos.up();

        if (worldIn.isAirBlock(up)) {
            if(ForgeHooks.onCropsGrowPre(worldIn, up, state, true)) {
                if (age == 15) {
                    if (height < maxHeight) {
                        grow(worldIn, pos, up);
                    }
                } else if (canGrowFlower(worldIn, pos, random)) {
                    worldIn.setBlockState(up, ModBlocks.CACTUS_FLOWER.getBlock().getDefaultState());
                } else {
                    worldIn.setBlockState(pos, state.with(AGE, age + 1), 4);
                }

                ForgeHooks.onCropsGrowPost(worldIn, pos, state);
            }
        } else if (worldIn.getBlockState(up).getBlock() == ModBlocks.CACTUS_FLOWER.getBlock()) {
            if(ForgeHooks.onCropsGrowPre(worldIn, up, state, true)) {
                BlockPos up2 = up.up();
                if (worldIn.isAirBlock(up2) && up2.getY() < 255 && height < maxHeight) {
                    if (age == 15) {
                        grow(worldIn, pos, up);
                        worldIn.setBlockState(up2, ModBlocks.CACTUS_FLOWER.getBlock().getDefaultState());
                    } else {
                        worldIn.setBlockState(pos, state.with(AGE, age + 1), 4);
                    }
                }

                ForgeHooks.onCropsGrowPost(worldIn, pos, state);
            }
        }

        ci.cancel();
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public ActionResultType onBlockActivated(BlockState state, World worldIn, BlockPos pos, PlayerEntity player, Hand handIn, BlockRayTraceResult hit) {
        ItemStack shears = player.getHeldItem(handIn);
        if (this == Blocks.CACTUS && shears.getItem().isIn(Tags.Items.SHEARS)) {
            worldIn.setBlockState(pos, ModBlocks.SMOOTH_CACTUS.getDefaultState());

            worldIn.playSound(player, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_SHEEP_SHEAR, SoundCategory.BLOCKS, 1.0F, 1.0F);
            spawnAsEntity(worldIn, pos, new ItemStack(ModItems.CACTUS_NEEDLE.get(), 1 + worldIn.rand.nextInt(2)));
            shears.damageItem(1, player, (playerEntity) -> {
                playerEntity.sendBreakAnimation(handIn);
            });

            return ActionResultType.func_233537_a_(worldIn.isRemote);
        }

        return ActionResultType.PASS;
    }
}
