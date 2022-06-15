package azmalent.terraincognita.mixin;

import azmalent.cuneiform.util.ItemUtil;
import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.TIServerConfig;
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
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import javax.annotation.Nonnull;
import java.util.Random;

@Mixin(CactusBlock.class)
public abstract class CactusBlockMixin extends Block {
    public CactusBlockMixin(Properties properties) {
        super(properties);
    }

    private boolean isCactus(BlockState state) {
        return state.is(Blocks.CACTUS) || state.getBlock() == ModBlocks.SMOOTH_CACTUS.get();
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
        if (TIServerConfig.Tweaks.plantHeightVariation.get()) {
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
    private void randomTick(BlockState state, ServerLevel level, BlockPos pos, Random random, CallbackInfo ci) {
        int height = getHeight(level, pos);
        int maxHeight = getMaxHeight(pos);
        int age = state.getValue(CactusBlock.AGE);
        BlockPos up = pos.above();

        if (level.isEmptyBlock(up)) {
            if(ForgeHooks.onCropsGrowPre(level, up, state, true)) {
                if (age == 15) {
                    if (height < maxHeight) {
                        grow(level, pos, up);
                    }
                } else if (canGrowFlower(level, pos, random)) {
                    level.setBlockAndUpdate(up, ModBlocks.CACTUS_FLOWER.getBlock().defaultBlockState());
                } else {
                    level.setBlock(pos, state.setValue(CactusBlock.AGE, age + 1), 4);
                }

                ForgeHooks.onCropsGrowPost(level, pos, state);
            }
        } else if (level.getBlockState(up).getBlock() == ModBlocks.CACTUS_FLOWER.getBlock()) {
            if(ForgeHooks.onCropsGrowPre(level, up, state, true)) {
                BlockPos up2 = up.above();
                if (level.isEmptyBlock(up2) && up2.getY() < 255 && height < maxHeight) {
                    if (age == 15) {
                        grow(level, pos, up);
                        level.setBlockAndUpdate(up2, ModBlocks.CACTUS_FLOWER.getBlock().defaultBlockState());
                    } else {
                        level.setBlock(pos, state.setValue(CactusBlock.AGE, age + 1), 4);
                    }
                }

                ForgeHooks.onCropsGrowPost(level, pos, state);
            }
        }

        ci.cancel();
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(@NotNull BlockState state, @NotNull Level level, @NotNull BlockPos pos, Player player, @NotNull InteractionHand handIn, @NotNull BlockHitResult hit) {
        ItemStack shears = player.getItemInHand(handIn);
        if (this == Blocks.CACTUS && shears.is(Tags.Items.SHEARS)) {
            level.setBlockAndUpdate(pos, ModBlocks.SMOOTH_CACTUS.defaultBlockState());

            level.playSound(player, player.getX(), player.getY(), player.getZ(), SoundEvents.SHEEP_SHEAR, SoundSource.BLOCKS, 0.5F, 1.0F);
            popResource(level, pos, new ItemStack(ModItems.CACTUS_NEEDLE.get(), 1 + level.random.nextInt(2)));
            ItemUtil.damageHeldItem(player, handIn);

            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        return InteractionResult.PASS;
    }
}
