package azmalent.terraincognita.mixin;

import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.registry.ModItems;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.InteractionHand;
import net.minecraft.sounds.SoundSource;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.level.Level;
import net.minecraft.server.level.ServerLevel;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.Tags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nonnull;
import java.util.Random;

import static net.minecraft.block.CactusBlock.AGE;

import net.mimport net.minecraft.world.level.block.state.BlockBehaviour.Properties;

net.minecraft.world.level.block.CactusBlockoperties;

@Mixin(CactusBlock.class)
public abstract class CactusBlockMixin extends Block {
    public CactusBlockMixin(Properties properties) {
        super(properties);
    }

    private boolean isCactus(BlockState state) {
        return state.is(Blocks.CACTUS) || state.getBlock() == ModBlocks.SMOOTH_CACTUS.getBlock();
    }

    private int getHeight(ServerLevel world, BlockPos pos) {
        int height = 1;
        while (isCactus(world.getBlockState(pos.below(height)))) {
            height++;
        }

        return height;
    }

    private int getMaxHeight(BlockPos pos) {
        //TODO: optimize
        if (TIConfig.Misc.plantHeightVariation.get()) {
            return new Random(pos.asLong()).nextInt(3) + 2;
        }

        return 3;
    }

    private void grow(ServerLevel world, BlockPos pos, BlockPos up) {
        BlockState cactus = Blocks.CACTUS.defaultBlockState();

        world.setBlock(pos, cactus, 4);
        world.setBlockAndUpdate(up, cactus);
        cactus.neighborChanged(world, up, Blocks.CACTUS, pos, false);
    }

    private boolean canGrowFlower(ServerLevel world, BlockPos pos, Random random) {
        if (!TIConfig.Flora.cactusFlowers.get()) return false;
        return random.nextInt(300) == 0 && world.canSeeSkyFromBelowWater(pos);
    }

    @Inject(method = "randomTick", at = @At("HEAD"), cancellable = true)
    private void randomTick(BlockState state, ServerLevel worldIn, BlockPos pos, Random random, CallbackInfo ci) {
        int height = getHeight(worldIn, pos);
        int maxHeight = getMaxHeight(pos);
        int age = state.getValue(AGE);
        BlockPos up = pos.above();

        if (worldIn.isEmptyBlock(up)) {
            if(ForgeHooks.onCropsGrowPre(worldIn, up, state, true)) {
                if (age == 15) {
                    if (height < maxHeight) {
                        grow(worldIn, pos, up);
                    }
                } else if (canGrowFlower(worldIn, pos, random)) {
                    worldIn.setBlockAndUpdate(up, ModBlocks.CACTUS_FLOWER.getBlock().defaultBlockState());
                } else {
                    worldIn.setBlock(pos, state.setValue(AGE, age + 1), 4);
                }

                ForgeHooks.onCropsGrowPost(worldIn, pos, state);
            }
        } else if (worldIn.getBlockState(up).getBlock() == ModBlocks.CACTUS_FLOWER.getBlock()) {
            if(ForgeHooks.onCropsGrowPre(worldIn, up, state, true)) {
                BlockPos up2 = up.above();
                if (worldIn.isEmptyBlock(up2) && up2.getY() < 255 && height < maxHeight) {
                    if (age == 15) {
                        grow(worldIn, pos, up);
                        worldIn.setBlockAndUpdate(up2, ModBlocks.CACTUS_FLOWER.getBlock().defaultBlockState());
                    } else {
                        worldIn.setBlock(pos, state.setValue(AGE, age + 1), 4);
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
    public InteractionResult use(BlockState state, Level worldIn, BlockPos pos, Player player, InteractionHand handIn, BlockHitResult hit) {
        ItemStack shears = player.getItemInHand(handIn);
        if (this == Blocks.CACTUS && shears.getItem().is(Tags.Items.SHEARS)) {
            worldIn.setBlockAndUpdate(pos, ModBlocks.SMOOTH_CACTUS.getDefaultState());

            worldIn.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 1.0F, 1.0F);
            popResource(worldIn, pos, new ItemStack(ModItems.CACTUS_NEEDLE.get(), 1 + worldIn.random.nextInt(2)));
            shears.hurtAndBreak(1, player, (playerEntity) -> {
                playerEntity.broadcastBreakEvent(handIn);
            });

            return InteractionResult.sidedSuccess(worldIn.isClientSide);
        }

        return InteractionResult.PASS;
    }
}
