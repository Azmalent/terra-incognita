package azmalent.terraincognita.common.item;

import azmalent.terraincognita.common.entity.IBottleableEntity;
import azmalent.terraincognita.common.item.dispenser.BottledEntityDispenserBehavior;
import net.minecraft.block.DispenserBlock;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.stats.Stats;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.math.RayTraceContext;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.common.util.Constants;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

public class BottledEntityItem<T extends LivingEntity & IBottleableEntity> extends Item {
    private static final BottledEntityDispenserBehavior DISPENSER_BEHAVIOR = new BottledEntityDispenserBehavior();
    private static final String ENTITY_KEY = "bottled_entity";

    public final Supplier<EntityType<T>> type;
    private final BiConsumer<CompoundNBT, List<ITextComponent>> tooltipHandler;

    public BottledEntityItem(Supplier<EntityType<T>> type, BiConsumer<CompoundNBT, List<ITextComponent>> tooltipHandler) {
        super(new Properties().group(ItemGroup.MISC).maxStackSize(1));
        this.type = type;
        this.tooltipHandler = tooltipHandler;

        DispenserBlock.registerDispenseBehavior(this, DISPENSER_BEHAVIOR);
    }

    public static <T extends LivingEntity> void setBottledEntity(ItemStack stack, T entity) {
        CompoundNBT nbt = new CompoundNBT();
        entity.writeAdditional(nbt);

        stack.getOrCreateTag().put(ENTITY_KEY, nbt);
    }

    public static CompoundNBT getBottledEntity(ItemStack bottle) {
        if (!bottle.getOrCreateTag().contains(ENTITY_KEY, Constants.NBT.TAG_COMPOUND)) {
            return new CompoundNBT();
        }

        return bottle.getOrCreateTag().getCompound(ENTITY_KEY);
    }

    public static void initReleasedEntity(LivingEntity entity, ItemStack bottle) {
        CompoundNBT nbt = getBottledEntity(bottle);
        entity.readAdditional(nbt);

        if (bottle.hasDisplayName()) {
            entity.setCustomName(bottle.getDisplayName());
        }

        ((IBottleableEntity) entity).onUnbottled();
    }

    @Nonnull
    @Override
    @SuppressWarnings("ConstantConditions")
    public ActionResultType onItemUse(ItemUseContext context) {
        World world = context.getWorld();
        if (world.isRemote) {
            return ActionResultType.SUCCESS;
        }

        PlayerEntity player = context.getPlayer();
        ItemStack bottle = context.getItem();
        BlockPos pos = context.getPos();
        BlockPos pos1 = pos;
        Direction direction = context.getFace();

        if (!world.getBlockState(pos).getCollisionShape(world, pos).isEmpty()) {
            pos1 = pos.offset(direction);
        }

        world.playSound(player, pos1, SoundEvents.ITEM_BOTTLE_EMPTY, SoundCategory.BLOCKS, 1.0F, 1.0F);
        LivingEntity entity = (LivingEntity) type.get().spawn((ServerWorld)world, bottle, context.getPlayer(), pos, SpawnReason.BUCKET, true, !Objects.equals(pos, pos1) && direction == Direction.UP);
        if (entity != null) {
            initReleasedEntity(entity, bottle);
            player.addStat(Stats.ITEM_USED.get(this));
            player.setHeldItem(context.getHand(), new ItemStack(Items.GLASS_BOTTLE));
            player.swingArm(context.getHand());
        }

        return ActionResultType.CONSUME;
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
        tooltipHandler.accept(getBottledEntity(stack), tooltip);
    }
}
