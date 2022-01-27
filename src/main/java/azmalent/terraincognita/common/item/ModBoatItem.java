package azmalent.terraincognita.common.item;

import azmalent.terraincognita.common.block.woodtypes.ModWoodType;
import azmalent.terraincognita.common.item.dispenser.ModBoatDispenserBehavior;
import net.minecraft.world.level.block.DispenserBlock;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.entity.EntitySelector;

import java.util.function.Predicate;

public class ModBoatItem extends Item {
    private static final ModBoatDispenserBehavior DISPENSER_BEHAVIOR = new ModBoatDispenserBehavior();
    private static final Predicate<Entity> predicate = EntitySelector.NO_SPECTATORS.and(Entity::isPickable);
    public final ModWoodType woodType;

    public ModBoatItem(ModWoodType woodType) {
        super(new Item.Properties().stacksTo(1).tab(CreativeModeTab.TAB_TRANSPORTATION));
        this.woodType = woodType;

        DispenserBlock.registerBehavior(this, DISPENSER_BEHAVIOR);
    }

    //TODO: boat item
    /**
     * Called to trigger the item's "innate" right click behavior. To handle when this item is used on a Block, see
     * {@link #onItemUse}.
     */
/*    @Nonnull
    public InteractionResultHolder<ItemStack> use(Level level, Player playerIn, @Nonnull InteractionHand handIn) {
        ItemStack itemstack = playerIn.getItemInHand(handIn);
        HitResult raytraceresult = getPlayerPOVHitResult(level, playerIn, ClipContext.Fluid.ANY);
        if (raytraceresult.getType() == HitResult.Type.MISS) {
            return InteractionResultHolder.pass(itemstack);
        } else {
            Vec3 vector3d = playerIn.getViewVector(1.0F);
            double d0 = 5.0D;
            List<Entity> list = level.getEntities(playerIn, playerIn.getBoundingBox().expandTowards(vector3d.scale(5.0D)).inflate(1.0D), predicate);
            if (!list.isEmpty()) {
                Vec3 vector3d1 = playerIn.getEyePosition(1.0F);

                for(Entity entity : list) {
                    AABB axisalignedbb = entity.getBoundingBox().inflate(entity.getPickRadius());
                    if (axisalignedbb.contains(vector3d1)) {
                        return InteractionResultHolder.pass(itemstack);
                    }
                }
            }

            if (raytraceresult.getType() == HitResult.Type.BLOCK) {
                ModBoatEntity boat = new ModBoatEntity(level, raytraceresult.getLocation().x, raytraceresult.getLocation().y, raytraceresult.getLocation().z);
                boat.setWoodType(this.woodType);
                boat.yRot = playerIn.yRot;
                if (!level.noCollision(boat, boat.getBoundingBox().inflate(-0.1D))) {
                    return InteractionResultHolder.fail(itemstack);
                } else {
                    if (!level.isClientSide) {
                        level.addFreshEntity(boat);
                        if (!playerIn.abilities.instabuild) {
                            itemstack.shrink(1);
                        }
                    }

                    playerIn.awardStat(Stats.ITEM_USED.get(this));
                    return InteractionResultHolder.sidedSuccess(itemstack, level.isClientSide());
                }
            } else {
                return InteractionResultHolder.pass(itemstack);
            }
        }
    }*/
}
