package azmalent.terraincognita.common.item;

import azmalent.terraincognita.common.entity.IBottleableEntity;
import azmalent.terraincognita.common.item.dispenser.BottledEntityDispenserBehavior;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import org.jetbrains.annotations.NotNull;

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
    private final BiConsumer<CompoundTag, List<Component>> tooltipHandler;

    public BottledEntityItem(Supplier<EntityType<T>> type, BiConsumer<CompoundTag, List<Component>> tooltipHandler) {
        super(new Properties().tab(CreativeModeTab.TAB_MISC).stacksTo(1));
        this.type = type;
        this.tooltipHandler = tooltipHandler;

        DispenserBlock.registerBehavior(this, DISPENSER_BEHAVIOR);
    }

    public static <T extends LivingEntity> void setBottledEntity(ItemStack stack, T entity) {
        CompoundTag nbt = new CompoundTag();
        entity.addAdditionalSaveData(nbt);

        stack.getOrCreateTag().put(ENTITY_KEY, nbt);
    }

    public static CompoundTag getBottledEntity(ItemStack bottle) {
        if (!bottle.getOrCreateTag().contains(ENTITY_KEY, Tag.TAG_COMPOUND)) {
            return new CompoundTag();
        }

        return bottle.getOrCreateTag().getCompound(ENTITY_KEY);
    }

    public static void initReleasedEntity(LivingEntity entity, ItemStack bottle) {
        CompoundTag nbt = getBottledEntity(bottle);
        entity.readAdditionalSaveData(nbt);

        if (bottle.hasCustomHoverName()) {
            entity.setCustomName(bottle.getHoverName());
        }

        ((IBottleableEntity) entity).onRelease();
    }

    @Nonnull
    @Override
    @SuppressWarnings("ConstantConditions")
    public InteractionResult useOn(UseOnContext context) {
        Level world = context.getLevel();
        if (world.isClientSide) {
            return InteractionResult.SUCCESS;
        }

        Player player = context.getPlayer();
        ItemStack bottle = context.getItemInHand();
        BlockPos pos = context.getClickedPos();
        BlockPos pos1 = pos;
        Direction direction = context.getClickedFace();

        if (!world.getBlockState(pos).getCollisionShape(world, pos).isEmpty()) {
            pos1 = pos.relative(direction);
        }

        world.playSound(player, pos1, SoundEvents.BOTTLE_EMPTY, SoundSource.BLOCKS, 1.0F, 1.0F);
        LivingEntity entity = (LivingEntity) type.get().spawn((ServerLevel)world, bottle, context.getPlayer(), pos, MobSpawnType.BUCKET, true, !Objects.equals(pos, pos1) && direction == Direction.UP);
        if (entity != null) {
            initReleasedEntity(entity, bottle);
            player.awardStat(Stats.ITEM_USED.get(this));
            player.setItemInHand(context.getHand(), new ItemStack(Items.GLASS_BOTTLE));
            player.swing(context.getHand());
        }

        return InteractionResult.CONSUME;
    }

    @Override
    public void appendHoverText(@NotNull ItemStack stack, @Nullable Level level, @NotNull List<Component> tooltip, @NotNull TooltipFlag flagIn) {
        super.appendHoverText(stack, level, tooltip, flagIn);
        tooltipHandler.accept(getBottledEntity(stack), tooltip);
    }
}
