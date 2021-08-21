package azmalent.terraincognita.common.registry;

import azmalent.cuneiform.common.event.FuelHandler;
import azmalent.cuneiform.common.item.ModSpawnEggItem;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.block.woodtypes.ModWoodType;
import azmalent.terraincognita.common.entity.butterfly.ButterflyEntity;
import azmalent.terraincognita.common.item.JamItem;
import azmalent.terraincognita.common.item.NotchCarrotItem;
import azmalent.terraincognita.common.item.TaffyItem;
import azmalent.terraincognita.common.item.WreathItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings({"ConstantConditions", "unused"})
public class ModItems {
    public static class Foods {
        public static final Food TAFFY = new Food.Builder().hunger(3).saturation(0.3f).setAlwaysEdible().build();

        public static final Food NOTCH_CARROT = new Food.Builder().hunger(6).saturation(1.2f)
                .effect(() -> new EffectInstance(Effects.ABSORPTION, 120 * 20, 3), 1.0f)
                .effect(() -> new EffectInstance(Effects.REGENERATION, 20 * 20, 1), 1.0f)
                .effect(() -> new EffectInstance(Effects.NIGHT_VISION, 600 * 20), 1.0f)
                .effect(() -> new EffectInstance(Effects.SPEED, 300 * 20, 1), 1.0f)
                .setAlwaysEdible()
                .build();

        public static final Food FIDDLEHEAD = new Food.Builder().hunger(3).saturation(0.5f).fastToEat().build();

        public static final Food BAKED_ROOT = new Food.Builder().hunger(5).saturation(0.6f).effect(() -> new EffectInstance(Effects.HASTE, 15 * 20), 1.0f).build();

        public static final Food KELP_SOUP = new Food.Builder().hunger(6).saturation(0.6f).build();

        public static final Food BERRY_SORBET = new Food.Builder().hunger(5).saturation(0.5f).build();

        public static final Food SOUR_BERRIES = new Food.Builder().hunger(2).saturation(0.1f).build();
        public static final Food SOUR_BERRY_PIE = new Food.Builder().hunger(6).saturation(0.3f).build();
        public static final Food SOUR_BERRY_JAM = new Food.Builder().hunger(4).saturation(0.2f).build();

        public static final Food HAZELNUT = new Food.Builder().hunger(2).saturation(0.1f).fastToEat().build();
        public static final Food CANDIED_HAZELNUT = new Food.Builder().hunger(4).saturation(0.4f).fastToEat().build();
        public static final Food HAZELNUT_COOKIE = new Food.Builder().hunger(2).saturation(0.2f).build();
    }

    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TerraIncognita.MODID);

    public static final RegistryObject<TaffyItem> TAFFY = ITEMS.register("taffy", TaffyItem::new);
    public static final RegistryObject<NotchCarrotItem> NOTCH_CARROT = ITEMS.register("notch_carrot", NotchCarrotItem::new);
    public static final RegistryObject<Item> FIDDLEHEAD = ITEMS.register("fiddlehead", () -> new Item(foodProps(Foods.FIDDLEHEAD)));
    public static final RegistryObject<Item> CLAYED_ROOT = ITEMS.register("clayed_root", () -> new Item(new Item.Properties().group(ItemGroup.FOOD)));
    public static final RegistryObject<Item> BAKED_ROOT = ITEMS.register("baked_root", () -> new Item(foodProps(Foods.BAKED_ROOT)));
    public static final RegistryObject<Item> KELP_SOUP = ITEMS.register("kelp_soup", () -> new SoupItem(foodProps(Foods.KELP_SOUP).maxStackSize(1)));
    public static final RegistryObject<Item> BERRY_SORBET = ITEMS.register("berry_sorbet", () -> new SoupItem(foodProps(Foods.BERRY_SORBET).maxStackSize(1)));

    public static final RegistryObject<Item> SOUR_BERRIES = ITEMS.register("sour_berries", () -> new Item(foodProps(Foods.SOUR_BERRIES)));
    public static final RegistryObject<Item> SOUR_BERRY_PIE = ITEMS.register("sour_berry_pie", () -> new Item(foodProps(Foods.SOUR_BERRY_PIE)));
    public static final RegistryObject<Item> SOUR_BERRY_JAM = ITEMS.register("sour_berry_jam", () -> new JamItem(foodProps(Foods.SOUR_BERRY_JAM).maxStackSize(16)));

    public static final RegistryObject<Item> HAZELNUT = ITEMS.register("hazelnut", () -> new Item(foodProps(Foods.HAZELNUT)));
    public static final RegistryObject<Item> CANDIED_HAZELNUT = ITEMS.register("candied_hazelnut", () -> new Item(foodProps(Foods.CANDIED_HAZELNUT)));
    public static final RegistryObject<Item> HAZELNUT_COOKIE = ITEMS.register("hazelnut_cookie", () -> new Item(foodProps(Foods.HAZELNUT_COOKIE)));

    public static final RegistryObject<Item> CACTUS_NEEDLE = ITEMS.register("cactus_needle", () -> new Item(new Item.Properties().group(ItemGroup.MISC)));
    //public static final RegistryObject<BlowpipeItem> BLOWPIPE = ITEMS.register("blowpipe", BlowpipeItem::new);

    public static final RegistryObject<WreathItem> WREATH = ITEMS.register("wreath", WreathItem::new);

    public static final RegistryObject<ModSpawnEggItem<ButterflyEntity>> BUTTERFLY_SPAWN_EGG = spawnEgg("butterfly", ModEntities.BUTTERFLY, 0xc02f03, 0x0f1016);

    private static Item.Properties foodProps(Food food) {
        return new Item.Properties().group(ItemGroup.FOOD).food(food);
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
        ItemModelsProperties.registerProperty(Items.SUSPICIOUS_STEW, new ResourceLocation("fiddlehead"), (stack, world, livingEntity) ->
            stack.hasTag() && stack.getTag().contains("fiddlehead") ? 1 : 0
        );
    }
}
