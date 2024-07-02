package azmalent.terraincognita.core.event;

import azmalent.cuneiform.util.ItemUtil;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.core.ModItemTags;
import azmalent.terraincognita.common.block.fruit.AbstractFruitBlock;
import azmalent.terraincognita.common.entity.IBottleableEntity;
import azmalent.terraincognita.common.menu.BasketStackHandler;
import azmalent.terraincognita.common.item.BottledEntityItem;
import azmalent.terraincognita.common.item.block.BasketItem;
import azmalent.terraincognita.common.recipe.WreathRecipe;
import azmalent.terraincognita.core.registry.ModBlocks;
import azmalent.terraincognita.core.registry.ModEffects;
import azmalent.terraincognita.core.registry.ModItems;
import azmalent.terraincognita.core.registry.ModWoodTypes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.DyeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.CraftingRecipe;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.event.RecipesUpdatedEvent;
import net.minecraftforge.common.Tags;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent;
import net.minecraftforge.event.entity.player.EntityItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.items.ItemHandlerHelper;

import java.util.List;


@Mod.EventBusSubscriber(modid = TerraIncognita.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class EventHandler {
    //Build flower to dye map for the wreath recipe
    @SubscribeEvent
    public static void onUpdateRecipes(RecipesUpdatedEvent event) {
        for (CraftingRecipe recipe : event.getRecipeManager().getAllRecipesFor(RecipeType.CRAFTING)) {
            List<Ingredient> ingredients = recipe.getIngredients();
            if (ingredients.size() != 1) continue;

            for (ItemStack input : ingredients.get(0).getItems()) {
                ItemStack output = recipe.getResultItem();

                if (input.is(ItemTags.SMALL_FLOWERS) && output.is(Tags.Items.DYES)) {
                    if (output.getItem() instanceof DyeItem dyeItem) {
                        WreathRecipe.FLOWER_TO_DYE_MAP.put(input.getItem(), dyeItem);
                    }
                }
            }
        }
    }

    //Eating speed adjustment
    @SubscribeEvent
    public static void onPlayerUseItem(LivingEntityUseItemEvent.Start event) {
        if (!(event.getEntity() instanceof Player)) return;

        Player player = (Player) event.getEntityLiving();
        MobEffectInstance effect = player.getEffect(ModEffects.STICKY_MOUTH.get());
        if (effect != null && event.getItem().isEdible()) {
            int multiplier = 2 << effect.getAmplifier();
            event.setDuration(event.getDuration() * multiplier);
        }
    }

    //Placing fruits
    @SubscribeEvent
    public static void onPlayerInteractWithBlock(PlayerInteractEvent.RightClickBlock event) {
        if (event.getFace() != Direction.DOWN) {
            return;
        }

        Player player = event.getPlayer();
        InteractionHand hand = event.getHand();
        ItemStack heldStack = player.getItemInHand(hand);

        BlockPos pos = event.getPos();
        Level level = player.level;
        Block block = level.getBlockState(pos).getBlock();

        BlockState fruit = null;
        if (heldStack.getItem() == Items.APPLE && (block == ModWoodTypes.APPLE.LEAVES.get() || block == ModWoodTypes.APPLE.BLOSSOMING_LEAVES.get())) {
            fruit = ModBlocks.APPLE.defaultBlockState();
        } else if (heldStack.getItem() == ModItems.HAZELNUT.get() && block == ModWoodTypes.HAZEL.LEAVES.get()) {
            fruit = ModBlocks.HAZELNUT.defaultBlockState();
        }

        if (fruit != null && level.getBlockState(pos.below()).isAir()) {
            level.setBlock(pos.below(), fruit.setValue(AbstractFruitBlock.AGE, 7), 2);

            if (!player.isCreative()) {
                heldStack.shrink(1);
            }

            player.swing(hand);
            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.CONSUME);
        }
    }

    //Entity bottling
    @SubscribeEvent
    public static void onPlayerInteractWithEntity(PlayerInteractEvent.EntityInteractSpecific event) {
        if (!(event.getTarget() instanceof LivingEntity living) || event.getWorld().isClientSide) {
            return;
        }

        Player player = event.getPlayer();
        InteractionHand hand = event.getHand();
        ItemStack heldStack = player.getItemInHand(hand);

        if (heldStack.is(Items.GLASS_BOTTLE) && living instanceof IBottleableEntity && living.isAlive()) {
            Level world = event.getWorld();

            heldStack.shrink(1);

            ItemStack bottle = new ItemStack(((IBottleableEntity) living).getBottledItem());
            if (living.hasCustomName()) {
                bottle.setHoverName(living.getCustomName());
            }

            BottledEntityItem.setBottledEntity(bottle, living);
            living.discard();
            world.playSound(player, event.getPos(), SoundEvents.BOTTLE_FILL_DRAGONBREATH, SoundSource.NEUTRAL, 1.0f, 1.0f);
            player.awardStat(Stats.ITEM_USED.get(Items.GLASS_BOTTLE));

            ItemUtil.giveStackToPlayer(player, bottle, hand);
            player.swing(hand);

            event.setCanceled(true);
            event.setCancellationResult(InteractionResult.CONSUME);
        }
    }

    //Basket auto-pickup
    @SubscribeEvent
    public static void onItemPickup(EntityItemPickupEvent event) {
        if (event.isCanceled() || event.getResult() == Event.Result.ALLOW) {
            return;
        }

        ItemEntity itemEntity = event.getItem();
        ItemStack stack = itemEntity.getItem().copy();
        Player player = event.getPlayer();
        ItemStack basket = BasketItem.getBasketInHand(player);

        if (basket != null && !stack.isEmpty() && stack.getItem().canFitInsideContainerItems() && stack.is(ModItemTags.BASKET_STORABLE)) {
            BasketStackHandler stackHandler = BasketItem.getStackHandler(basket);
            ItemStack remainingStack = ItemHandlerHelper.insertItemStacked(stackHandler, stack, false);

            int numPickedUp = stack.getCount() - remainingStack.getCount();
            if (numPickedUp > 0) {
                event.setCanceled(true);
                itemEntity.getItem().shrink(numPickedUp);

                if (!itemEntity.isSilent()) {
                    itemEntity.level.playSound(player, player.getX(), player.getY(), player.getZ(),
                        SoundEvents.ITEM_PICKUP, SoundSource.PLAYERS, 0.2F,
                        ((itemEntity.level.random.nextFloat() - itemEntity.level.random.nextFloat()) * 0.7F + 1.0F) * 2.0F
                    );
                }

                player.take(itemEntity, numPickedUp);
                player.containerMenu.broadcastChanges();
            }
        }
    }
}
