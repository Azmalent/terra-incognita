package azmalent.terraincognita.common.registry;

import azmalent.cuneiform.common.event.FuelHandler;
import azmalent.cuneiform.common.item.ModSpawnEggItem;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.block.woodtypes.ModWoodType;
import azmalent.terraincognita.common.entity.IBottleableEntity;
import azmalent.terraincognita.common.entity.butterfly.ButterflyEntity;
import azmalent.terraincognita.common.item.*;
import azmalent.terraincognita.common.item.BottledEntityItem;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.item.*;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Supplier;

import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BowlFoodItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;

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

        public static final FoodProperties FIDDLEHEAD = new FoodProperties.Builder().nutrition(3).saturationMod(0.5f).fast().build();

        public static final FoodProperties BAKED_ROOT = new FoodProperties.Builder().nutrition(5).saturationMod(0.6f).effect(() -> new MobEffectInstance(MobEffects.DIG_SPEED, 15 * 20), 1.0f).build();

        public static final FoodProperties KELP_SOUP = new FoodProperties.Builder().nutrition(6).saturationMod(0.6f).build();

        public static final FoodProperties BERRY_SORBET = new FoodProperties.Builder().nutrition(5).saturationMod(0.5f).build();

        public static final FoodProperties SOUR_BERRIES = new FoodProperties.Builder().nutrition(2).saturationMod(0.1f).build();
        public static final FoodProperties SOUR_BERRY_PIE = new FoodProperties.Builder().nutrition(6).saturationMod(0.3f).build();
        public static final FoodProperties SOUR_BERRY_JAM = new FoodProperties.Builder().nutrition(4).saturationMod(0.2f).build();

        public static final FoodProperties HAZELNUT = new FoodProperties.Builder().nutrition(2).saturationMod(0.1f).fast().build();
        public static final FoodProperties CANDIED_HAZELNUT = new FoodProperties.Builder().nutrition(4).saturationMod(0.4f).fast().build();
        public static final FoodProperties HAZELNUT_COOKIE = new FoodProperties.Builder().nutrition(2).saturationMod(0.2f).build();
    }

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TerraIncognita.MODID);

    public static final RegistryObject<TaffyItem> TAFFY = ITEMS.register("taffy", TaffyItem::new);
    public static final RegistryObject<NotchCarrotItem> NOTCH_CARROT = ITEMS.register("notch_carrot", NotchCarrotItem::new);
    public static final RegistryObject<Item> FIDDLEHEAD = ITEMS.register("fiddlehead", () -> new Item(foodProps(Foods.FIDDLEHEAD)));
    public static final RegistryObject<Item> CLAYED_ROOT = ITEMS.register("clayed_root", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_FOOD)));
    public static final RegistryObject<Item> BAKED_ROOT = ITEMS.register("baked_root", () -> new Item(foodProps(Foods.BAKED_ROOT)));
    public static final RegistryObject<Item> KELP_SOUP = ITEMS.register("kelp_soup", () -> new BowlFoodItem(foodProps(Foods.KELP_SOUP).stacksTo(1)));
    public static final RegistryObject<Item> BERRY_SORBET = ITEMS.register("berry_sorbet", () -> new BowlFoodItem(foodProps(Foods.BERRY_SORBET).stacksTo(1)));

    public static final RegistryObject<Item> SOUR_BERRIES = ITEMS.register("sour_berries", () -> new Item(foodProps(Foods.SOUR_BERRIES)));
    public static final RegistryObject<Item> SOUR_BERRY_PIE = ITEMS.register("sour_berry_pie", () -> new Item(foodProps(Foods.SOUR_BERRY_PIE)));
    public static final RegistryObject<Item> SOUR_BERRY_JAM = ITEMS.register("sour_berry_jam", () -> new JamItem(foodProps(Foods.SOUR_BERRY_JAM).stacksTo(16)));

    public static final RegistryObject<Item> HAZELNUT = ITEMS.register("hazelnut", () -> new Item(foodProps(Foods.HAZELNUT)));
    public static final RegistryObject<Item> CANDIED_HAZELNUT = ITEMS.register("candied_hazelnut", () -> new Item(foodProps(Foods.CANDIED_HAZELNUT)));
    public static final RegistryObject<Item> HAZELNUT_COOKIE = ITEMS.register("hazelnut_cookie", () -> new Item(foodProps(Foods.HAZELNUT_COOKIE)));

    public static final RegistryObject<Item> CACTUS_NEEDLE = ITEMS.register("cactus_needle", () -> new Item(new Item.Properties().tab(CreativeModeTab.TAB_MISC)));

    public static final RegistryObject<WreathItem> WREATH = ITEMS.register("wreath", WreathItem::new);

    public static final RegistryObject<BottledEntityItem<ButterflyEntity>> BOTTLED_BUTTERFLY = bottledEntity("butterfly", ModEntities.BUTTERFLY, ButterflyEntity::addBottleTooltip);
    public static final RegistryObject<ModSpawnEggItem<ButterflyEntity>> BUTTERFLY_SPAWN_EGG = spawnEgg("butterfly", ModEntities.BUTTERFLY, 0xc02f03, 0x0f1016);

    private static Item.Properties foodProps(FoodProperties food) {
        return new Item.Properties().tab(CreativeModeTab.TAB_FOOD).food(food);
    }

    private static <T extends LivingEntity & IBottleableEntity> RegistryObject<BottledEntityItem<T>> bottledEntity(String entityId, Supplier<EntityType<T>> type, BiConsumer<CompoundTag, List<Component>> tooltipHandler) {
        return ITEMS.register("bottled_" + entityId, () -> new BottledEntityItem<T>(type, tooltipHandler));
    }

    private static <T extends Entity> RegistryObject<ModSpawnEggItem<T>> spawnEgg(String entityId, RegistryObject<EntityType<T>> type, int primaryColor, int secondaryColor) {
        return ITEMS.register(entityId + "_spawn_egg", () -> new ModSpawnEggItem<T>(type, primaryColor, secondaryColor));
    }

    public static void initFuelValues() {
        for (ModWoodType woodType : ModWoodTypes.VALUES) {
            FuelHandler.registerFuel(woodType.LOG, 300);
            FuelHandler.registerFuel(woodType.STRIPPED_LOG, 300);
            FuelHandler.registerFuel(woodType.WOOD, 300);
            FuelHandler.registerFuel(woodType.STRIPPED_WOOD, 300);
            FuelHandler.registerFuel(woodType.PLANKS, 300);
            FuelHandler.registerFuel(woodType.SLAB, 150);
            FuelHandler.registerFuel(woodType.STAIRS, 300);
            FuelHandler.registerFuel(woodType.FENCE, 300);
            FuelHandler.registerFuel(woodType.FENCE_GATE, 300);
            FuelHandler.registerFuel(woodType.SIGN, 200);
            FuelHandler.registerFuel(woodType.DOOR, 200);
            FuelHandler.registerFuel(woodType.TRAPDOOR, 300);
        }

        FuelHandler.registerFuel(ModBlocks.REEDS, 100);
        FuelHandler.registerFuel(ModBlocks.PEAT, 2400);
    }

    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("ConstantConditions")
    public static void registerPropertyOverrides() {
        ItemProperties.register(Items.SUSPICIOUS_STEW, new ResourceLocation("fiddlehead"), (stack, world, livingEntity) ->
            stack.hasTag() && stack.getTag().contains("fiddlehead") ? 1 : 0
        );
    }
}
