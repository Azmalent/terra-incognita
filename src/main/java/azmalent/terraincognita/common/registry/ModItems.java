package azmalent.terraincognita.common.registry;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.entity.butterfly.ButterflyEntity;
import azmalent.terraincognita.common.item.*;
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

    public static RegistryObject<Item> MOD_ICON = ITEMS.register("mod_icon", () -> new Item(new Item.Properties()));

    public static RegistryObject<TaffyItem> TAFFY = ITEMS.register("taffy", TaffyItem::new);
    public static RegistryObject<NotchCarrotItem> NOTCH_CARROT = ITEMS.register("notch_carrot", NotchCarrotItem::new);
    public static RegistryObject<Item> FIDDLEHEAD = ITEMS.register("fiddlehead", () -> new Item(defaultProps().food(ModFoods.FIDDLEHEAD)));
    public static RegistryObject<Item> CLAYED_ROOT = ITEMS.register("clayed_root", () -> new Item(defaultProps()));
    public static RegistryObject<Item> BAKED_ROOT = ITEMS.register("baked_root", () -> new Item(defaultProps().food(ModFoods.BAKED_ROOT)));
    public static RegistryObject<Item> KELP_SOUP = ITEMS.register("kelp_soup", () -> new SoupItem(defaultProps().maxStackSize(1).food(ModFoods.KELP_SOUP)));
    public static RegistryObject<Item> BERRY_SORBET = ITEMS.register("berry_sorbet", () -> new SoupItem(defaultProps().maxStackSize(1).food(ModFoods.BERRY_SORBET)));
    public static RegistryObject<WreathItem> WREATH = ITEMS.register("flower_band", WreathItem::new);
    public static RegistryObject<Item> HAZELNUT = ITEMS.register("hazelnut", () -> new Item(defaultProps().food(ModFoods.HAZELNUT)));
    public static RegistryObject<Item> HONEY_HAZELNUT = ITEMS.register("honey_hazelnut", () -> new Item(defaultProps().food(ModFoods.HONEY_HAZELNUT)));

    public static final RegistryObject<ModSpawnEggItem<ButterflyEntity>> BUTTERFLY_SPAWN_EGG = ModSpawnEggItem.create("butterfly", ModEntities.BUTTERFLY, 0xc02f03, 0x0f1016);

    private static Item.Properties defaultProps() {
        return new Item.Properties().group(TerraIncognita.TAB);
    }

    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("ConstantConditions")
    public static void registerPropertyOverrides() {
        ItemModelsProperties.registerProperty(Items.SUSPICIOUS_STEW, new ResourceLocation("fiddlehead"), (stack, world, livingEntity) ->
            stack.hasTag() && stack.getTag().contains("fiddlehead") ? 1 : 0
        );
    }
}
