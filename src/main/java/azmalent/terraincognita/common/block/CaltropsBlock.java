package azmalent.terraincognita.common.block;

import azmalent.terraincognita.client.ModSoundTypes;
import azmalent.terraincognita.common.init.ModBlocks;
import azmalent.terraincognita.common.init.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.shapes.ISelectionContext;
import net.minecraft.util.math.shapes.VoxelShape;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.IWorldReader;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings("ConstantConditions")
public class CaltropsBlock extends Block {
    private static final VoxelShape SHAPE = makeCuboidShape(0, 0, 0, 15, 4, 15);
    private static final float BREAK_CHANCE = 0.2f;

    public CaltropsBlock() {
        super(Block.Properties.create(Material.MISCELLANEOUS, MaterialColor.IRON).doesNotBlockMovement().zeroHardnessAndResistance());
    }

    @Override
    public SoundType getSoundType(BlockState state, IWorldReader world, BlockPos pos, @Nullable Entity entity) {
        return ModSoundTypes.CALTROPS;
    }

    @Override
    @SuppressWarnings("deprecation")
    public void neighborChanged(BlockState state, World world, BlockPos pos, Block block, BlockPos neighborPos, boolean isMoving) {
        if (!isValidPosition(state, world, pos)) {
            world.destroyBlock(pos, true);
        }
    }

    @Nonnull
    @Override
    @SuppressWarnings("deprecation")
    public ActionResultType onBlockActivated(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockRayTraceResult hit) {
        if (!world.isRemote && player.getHeldItem(hand).isEmpty()) {
            ItemStack stack = ModBlocks.CALTROPS.makeStack();

            if (player.addItemStackToInventory(stack)) {
                float pitch = ((world.rand.nextFloat() - world.rand.nextFloat()) * 0.7F + 1.0F) * 2.0F;
                world.playSound(player, player.getPosX(), player.getPosY(), player.getPosZ(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2F, pitch);

                if (hand != null) player.swingArm(hand);
                world.setBlockState(pos, Blocks.AIR.getDefaultState());
            }
        }

        return super.onBlockActivated(state, world, pos, player, hand, hit);
    }

    @Nonnull
    @Override
    public VoxelShape getShape(BlockState state, IBlockReader worldIn, BlockPos pos, ISelectionContext context) {
        return SHAPE;
    }

    @Override
    public boolean isValidPosition(BlockState state, IWorldReader worldIn, BlockPos pos) {
        BlockPos down = pos.down();
        return worldIn.getBlockState(down).isSolidSide(worldIn, down, Direction.UP);
    }

    @Override
    public void onEntityCollision(BlockState state, World world, BlockPos pos, Entity entity) {
        if (!world.isRemote && entity instanceof LivingEntity) {
            LivingEntity living = (LivingEntity) entity;
            if (living.attackEntityFrom(DamageSource.GENERIC, 2)) {
                living.addPotionEffect(new EffectInstance(Effects.SLOWNESS, 10 * 20, 0, false, false));
                if (world.rand.nextFloat() < BREAK_CHANCE) {
                    world.destroyBlock(pos, false);
                }
            }
        }
    }
}
