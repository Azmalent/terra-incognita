package azmalent.terraincognita.common.block;

import azmalent.terraincognita.TIServerConfig;
import azmalent.terraincognita.core.ModDamageSources;
import azmalent.terraincognita.core.registry.ModBlocks;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.Level;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import org.jetbrains.annotations.NotNull;

@SuppressWarnings({"ConstantConditions", "deprecation"})
public class CaltropsBlock extends Block {
    private static final VoxelShape SHAPE = box(0, 0, 0, 16, 4, 16);

    private static final int STEP_DAMAGE = 2;

    public CaltropsBlock() {
        super(Block.Properties.of(Material.DECORATION, MaterialColor.METAL).noCollission().instabreak());
    }

    @Override
    public SoundType getSoundType(BlockState state, LevelReader world, BlockPos pos, @Nullable Entity entity) {
        return SoundType.CHAIN;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(@NotNull BlockState state, @NotNull Level world, @NotNull BlockPos pos, @NotNull Block block, @NotNull BlockPos neighborPos, boolean isMoving) {
        if (!canSurvive(state, world, pos)) {
            world.destroyBlock(pos, true);
        }
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public InteractionResult use(@NotNull BlockState state, Level world, @NotNull BlockPos pos, @NotNull Player player, @NotNull InteractionHand hand, @NotNull BlockHitResult hit) {
        if (!world.isClientSide && player.getItemInHand(hand).isEmpty()) {
            ItemStack stack = ModBlocks.CALTROPS.makeStack();

            if (player.addItem(stack)) {
                float pitch = ((world.random.nextFloat() - world.random.nextFloat()) * 0.7F + 1.0F) * 2.0F;
                world.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F, pitch);

                if (hand != null) player.swing(hand);
                world.setBlockAndUpdate(pos, Blocks.AIR.defaultBlockState());
            }
        }

        return super.use(state, world, pos, player, hand, hit);
    }

    @Override
    public @NotNull VoxelShape getShape(@NotNull BlockState state, @NotNull BlockGetter level, @NotNull BlockPos pos, @NotNull CollisionContext context) {
        return SHAPE;
    }

    @Override
    public boolean canSurvive(@NotNull BlockState state, LevelReader level, BlockPos pos) {
        BlockPos down = pos.below();
        return level.getBlockState(down).isFaceSturdy(level, down, Direction.UP);
    }

    @Override
    public void entityInside(@NotNull BlockState state, Level world, @NotNull BlockPos pos, @NotNull Entity entity) {
        if (!world.isClientSide && entity instanceof LivingEntity living && living.isOnGround()) {
            if (living.hurt(ModDamageSources.CALTROPS, STEP_DAMAGE)) {
                living.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, STEP_DAMAGE * 100, 0, false, false));
                if (world.random.nextFloat() < TIServerConfig.caltropsBreakChance.get()) {
                    world.destroyBlock(pos, false);
                }
            }
        }
    }
}
