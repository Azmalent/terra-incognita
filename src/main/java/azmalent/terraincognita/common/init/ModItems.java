package azmalent.terraincognita.common.init;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.entity.ButterflyEntity;
import azmalent.terraincognita.common.entity.MothEntity;
import azmalent.terraincognita.common.item.ModSpawnEggItem;
import azmalent.terraincognita.common.item.WreathItem;
import azmalent.terraincognita.common.item.NotchCarrotItem;
import azmalent.terraincognita.common.item.TaffyItem;
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

    public static RegistryObject<TaffyItem> TAFFY;
    public static RegistryObject<NotchCarrotItem> NOTCH_CARROT;
    public static RegistryObject<Item> FIDDLEHEAD;
    public static RegistryObject<Item> CLAYED_ROOT;
    public static RegistryObject<Item> BAKED_ROOT;
    public static RegistryObject<Item> BERRY_SORBET;
    public static RegistryObject<WreathItem> WREATH;
    public static RegistryObject<Item> HAZELNUT;

    public static final RegistryObject<ModSpawnEggItem<ButterflyEntity>> BUTTERFLY_SPAWN_EGG = ModSpawnEggItem.create("butterfly", ModEntities.BUTTERFLY, 0xc02f03, 0x0f1016);
    public static final RegistryObject<ModSpawnEggItem<MothEntity>> MOTH_SPAWN_EGG = ModSpawnEggItem.create("moth", ModEntities.MOTH, 0xbe9c7e, 0x5e4736);

    private static Item.Properties defaultProps() {
        return new Item.Properties().group(TerraIncognita.TAB);
    }

    static {
        if (TIConfig.Food.taffy.get()) {
        	TAFFY = ITEMS.register("taffy", TaffyItem::new);
       	}

        if (TIConfig.Food.notchCarrot.get()) {
        	NOTCH_CARROT = ITEMS.register("notch_carrot", NotchCarrotItem::new);
       	}

        if (TIConfig.Food.fiddlehead.get()) {
            FIDDLEHEAD = ITEMS.register("fiddlehead", () -> new Item(defaultProps().food(ModFoods.FIDDLEHEAD)));
        }

        if (TIConfig.Flora.roots.get()) {
            CLAYED_ROOT = ITEMS.register("clayed_root", () -> new Item(defaultProps()));
            BAKED_ROOT = ITEMS.register("baked_root", () -> new Item(defaultProps().food(ModFoods.BAKED_ROOT)));
        }

        if (TIConfig.Food.berrySorbet.get()) {
            BERRY_SORBET = ITEMS.register("berry_sorbet", () -> new SoupItem(defaultProps().food(ModFoods.BERRY_SORBET)));
        }

        if (TIConfig.Flora.wreath.get()) {
            WREATH = ITEMS.register("flower_band", WreathItem::new);
        }

        if (TIConfig.Trees.hazel.get()) {
            HAZELNUT = ITEMS.register("hazelnut", () -> new Item(defaultProps().food(ModFoods.HAZELNUT)));
        }

        ModBanners.init();
    }

    @OnlyIn(Dist.CLIENT)
    @SuppressWarnings("ConstantConditions")
    public static void registerPropertyOverrides() {
        ItemModelsProperties.registerProperty(Items.SUSPICIOUS_STEW, new ResourceLocation("fiddlehead"), (stack, world, livingEntity) ->
            stack.hasTag() && stack.getTag().contains("fiddlehead") ? 1 : 0
        );
    }
}
