package azmalent.terraincognita.common.registry;

import azmalent.cuneiform.common.event.FuelHandler;
import azmalent.cuneiform.common.item.ModSpawnEggItem;
import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.block.woodtypes.ModWoodType;
import azmalent.terraincognita.common.entity.butterfly.ButterflyEntity;
import azmalent.terraincognita.common.item.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

@SuppressWarnings({"ConstantConditions", "unused"})
public class ModItems {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, TerraIncognita.MODID);

    public static RegistryObject<TaffyItem> TAFFY = ITEMS.register("taffy", TaffyItem::new);
    public static RegistryObject<NotchCarrotItem> NOTCH_CARROT = ITEMS.register("notch_carrot", NotchCarrotItem::new);
    public static RegistryObject<Item> FIDDLEHEAD = ITEMS.register("fiddlehead", () -> new Item(foodProps(ModFoods.FIDDLEHEAD)));
    public static RegistryObject<Item> CLAYED_ROOT = ITEMS.register("clayed_root", () -> new Item(new Item.Properties().group(ItemGroup.FOOD)));
    public static RegistryObject<Item> BAKED_ROOT = ITEMS.register("baked_root", () -> new Item(foodProps(ModFoods.BAKED_ROOT)));
    public static RegistryObject<Item> KELP_SOUP = ITEMS.register("kelp_soup", () -> new SoupItem(foodProps(ModFoods.KELP_SOUP).maxStackSize(1)));
    public static RegistryObject<Item> BERRY_SORBET = ITEMS.register("berry_sorbet", () -> new SoupItem(foodProps(ModFoods.BERRY_SORBET).maxStackSize(1)));
    public static RegistryObject<WreathItem> WREATH = ITEMS.register("flower_band", WreathItem::new);
    public static RegistryObject<Item> HAZELNUT = ITEMS.register("hazelnut", () -> new Item(foodProps(ModFoods.HAZELNUT)));
    public static RegistryObject<Item> HONEY_HAZELNUT = ITEMS.register("honey_hazelnut", () -> new Item(foodProps(ModFoods.HONEY_HAZELNUT)));

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
