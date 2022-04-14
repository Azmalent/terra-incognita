package azmalent.terraincognita.common.item;

import azmalent.terraincognita.common.entity.TIBoat;
import azmalent.terraincognita.common.woodtype.TIWoodType;
import azmalent.terraincognita.common.item.dispenser.ModBoatDispenserBehavior;
import net.minecraft.core.BlockPos;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.entity.EntitySelector;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;

import javax.annotation.Nonnull;
import java.util.List;
import java.util.function.Predicate;

public class TIBoatItem extends Item {
    private static final ModBoatDispenserBehavior DISPENSER_BEHAVIOR = new ModBoatDispenserBehavior();
    private static final Predicate<Entity> predicate = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);
    public final TIWoodType woodType;

    public TIBoatItem(TIWoodType woodType) {
        super(new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_TRANSPORTATION));
        this.woodType = woodType;

        DispenserBlock.registerBehavior(this, DISPENSER_BEHAVIOR);
    }

    @Nonnull
    public InteractionResultHolder<ItemStack> use(Level level, Player player, @Nonnull InteractionHand handIn) {
        ItemStack stack = player.getItemInHand(handIn);
        HitResult hit = getPlayerPOVHitResult(level, player, ClipContext.Fluid.ANY);
        if (hit.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(stack);
        } else {
            Vec3 vector3d = player.getViewVector(1.0F);
            double d0 = 5.0D;
            List<Entity> list = level.getEntities(player, player.getBoundingBox().expandTowards(vector3d.scale(5.0D)).inflate(1.0D), predicate);
            if (!list.isEmpty()) {
                Vec3 eyePos = player.getEyePosition(1.0F);

                for(Entity entity : list) {
                    AABB aabb = entity.getBoundingBox().inflate(entity.getPickRadius());
                    if (aabb.contains(eyePos)) {
                        return InteractionResultHolder.pass(stack);
                    }
                }
            }

            if (hit.getType() == HitResult.Type.BLOCK) {
                TIBoat boat = new TIBoat(level, hit.getLocation().x, hit.getLocation().y, hit.getLocation().z);
                boat.setWoodType(this.woodType);
                boat.setYRot(player.getYRot());
                if (!level.noCollision(boat, boat.getBoundingBox())) {
                    return InteractionResultHolder.fail(stack);
                } else {
                    if (!level.isClientSide) {
                        level.addFreshEntity(boat);
                        level.gameEvent(player, GameEvent.ENTITY_PLACE, new BlockPos(hit.getLocation()));
                        if (!player.getAbilities().instabuild) {
                            stack.shrink(1);
                        }
                    }

                    player.awardStat(Stats.ITEM_USED.get(this));
                    return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
                }
            } else {
                return InteractionResultHolder.pass(stack);
            }
        }
    }
}
