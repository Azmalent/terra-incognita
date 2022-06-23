package azmalent.terraincognita.common.registry;

import azmalent.cuneiform.common.data.FuelHandler;
import azmalent.cuneiform.registry.ItemEntry;
import azmalent.terraincognita.common.woodtype.TIWoodType;
import azmalent.terraincognita.common.entity.IBottleableEntity;
import azmalent.terraincognita.common.entity.butterfly.Butterfly;
import azmalent.terraincognita.common.item.*;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import static azmalent.terraincognita.TerraIncognita.REGISTRY_HELPER;

@SuppressWarnings({"ConstantConditions", "unused"})
public class ModItems {
    public static class Foods {
        public static final FoodProperties TAFFY = new FoodProperties.Builder().nutrition(3).saturationMod(0.3f).alwaysEat().build();

        public static final FoodProperties NOTCH_CARROT = new FoodProperties.Builder().nutrition(6).saturationMod(1.2f)
                .effect(() -> new MobEffectInstance(MobEffects.ABSORPTION, 120 * 20, 3), 1.0f)
                .effect(() -> new MobEffectInstance(MobEffects.REGENERATION, 20 * 20, 1), 1.0f)
                .effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 600 * 20), 1.0f)
                .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 300 * 20, 1), 1.0f)
                .alwaysEat()
                .build();

        public static final FoodProperties FIDDLEHEAD = new FoodProperties.Builder().nutrition(3).saturationMod(0.3f).fast().build();

        public static final FoodProperties KELP_SOUP = new FoodProperties.Builder().nutrition(6).saturationMod(0.6f).build();

        public static final FoodProperties BERRY_SORBET = new FoodProperties.Builder().nutrition(5).saturationMod(0.5f).build();

        public static final FoodProperties SOUR_BERRIES = new FoodProperties.Builder().nutrition(2).saturationMod(0.1f).build();
        public static final FoodProperties SOUR_BERRY_PIE = new FoodProperties.Builder().nutrition(6).saturationMod(0.3f).build();
        public static final FoodProperties SOUR_BERRY_JAM = new FoodProperties.Builder().nutrition(4).saturationMod(0.2f).build();

        public static final FoodProperties HAZELNUT = new FoodProperties.Builder().nutrition(2).saturationMod(0.1f).fast().build();
        public static final FoodProperties CANDIED_HAZELNUT = new FoodProperties.Builder().nutrition(4).saturationMod(0.4f).fast().build();
        public static final FoodProperties HAZELNUT_COOKIE = new FoodProperties.Builder().nutrition(2).saturationMod(0.3f).build();
    }

    public static final DeferredRegister<Item> ITEMS = REGISTRY_HELPER.getOrCreateRegistry(ForgeRegistries.ITEMS);

    public static final ItemEntry<TaffyItem> TAFFY = REGISTRY_HELPER.createItem("taffy", TaffyItem::new);
    public static final ItemEntry<NotchCarrotItem> NOTCH_CARROT  = REGISTRY_HELPER.createItem("notch_carrot", NotchCarrotItem::new);
    public static final ItemEntry<Item> FIDDLEHEAD    = REGISTRY_HELPER.createFood("fiddlehead", Foods.FIDDLEHEAD);
    public static final ItemEntry<BowlFoodItem> KELP_SOUP     = REGISTRY_HELPER.createItem("kelp_soup", BowlFoodItem::new, foodProps(Foods.KELP_SOUP).stacksTo(1));
    public static final ItemEntry<BowlFoodItem> BERRY_SORBET  = REGISTRY_HELPER.createItem("berry_sorbet", BowlFoodItem::new, foodProps(Foods.BERRY_SORBET).stacksTo(1));

    public static final ItemEntry<Item> SOUR_BERRIES   = REGISTRY_HELPER.createFood("sour_berries", Foods.SOUR_BERRIES);
    public static final ItemEntry<Item> SOUR_BERRY_PIE = REGISTRY_HELPER.createFood("sour_berry_pie", Foods.SOUR_BERRY_PIE);
    public static final ItemEntry<JamItem> SOUR_BERRY_JAM = REGISTRY_HELPER.createItem("sour_berry_jam", JamItem::new, foodProps(Foods.SOUR_BERRY_JAM).stacksTo(16));

    public static final ItemEntry<Item> HAZELNUT = REGISTRY_HELPER.createFood("hazelnut", Foods.HAZELNUT);
    public static final ItemEntry<Item> CANDIED_HAZELNUT = REGISTRY_HELPER.createFood("candied_hazelnut", Foods.CANDIED_HAZELNUT);
    public static final ItemEntry<Item> HAZELNUT_COOKIE = REGISTRY_HELPER.createFood("hazelnut_cookie", Foods.HAZELNUT_COOKIE);

    public static final ItemEntry<Item> CACTUS_NEEDLE = REGISTRY_HELPER.createItem("cactus_needle", CreativeModeTab.TAB_MISC);

    public static final ItemEntry<WreathItem> WREATH = REGISTRY_HELPER.createItem("wreath", WreathItem::new);

    public static final ItemEntry<BottledEntityItem<Butterfly>> BOTTLED_BUTTERFLY = bottledEntity("butterfly", ModEntities.BUTTERFLY, Butterfly::addBottleTooltip);

    private static Item.Properties foodProps(FoodProperties food) {
        return new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(food);
    }

    @SuppressWarnings("SameParameterValue")
    private static <T extends LivingEntity & IBottleableEntity> ItemEntry<BottledEntityItem<T>> bottledEntity(String entityId, Supplier<EntityType<T>> type, BiConsumer<CompoundTag, List<Component>> tooltipHandler) {
        return REGISTRY_HELPER.createItem("bottled_" + entityId, () -> new BottledEntityItem<>(type, tooltipHandler));
    }

    public static void initFuelValues() {
        for (TIWoodType woodType : ModWoodTypes.VALUES) {
            FuelHandler.registerFuel(1.5f,woodType.LOG, woodType.STRIPPED_LOG, woodType.WOOD, woodType.STRIPPED_WOOD);
            FuelHandler.registerFuel(1.5f, woodType.PLANKS);
            FuelHandler.registerFuel(0.75f, woodType.SLAB);
            FuelHandler.registerFuel(1.5f, woodType.STAIRS);
            FuelHandler.registerFuel(1.5f, woodType.FENCE, woodType.FENCE_GATE);
            FuelHandler.registerFuel(1, woodType.SIGN);
            FuelHandler.registerFuel(1, woodType.DOOR);
            FuelHandler.registerFuel(1.5f, woodType.TRAPDOOR);
        }

        FuelHandler.registerFuel(0.5f, ModBlocks.SEDGE);
        FuelHandler.registerFuel(12, ModBlocks.PEAT);
    }

    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("ConstantConditions")
    public static void registerPropertyOverrides() {
        ItemProperties.register(Items.SUSPICIOUS_STEW, new ResourceLocation("fiddlehead"), (stack, level, entity, seed) ->
            stack.hasTag() && stack.getTag().contains("fiddlehead") ? 1 : 0
        );
    }
}
