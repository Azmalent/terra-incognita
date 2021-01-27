package azmalent.terraincognita.common.init;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.TIConfig;
import azmalent.terraincognita.common.item.WreathItem;
import azmalent.terraincognita.common.item.ModFoods;
import azmalent.terraincognita.common.item.NotchCarrotItem;
import azmalent.terraincognita.common.item.TaffyItem;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

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

    private static Item.Properties props() {
        return new Item.Properties().group(TerraIncognita.TAB);
    }

    static {
        if (TIConfig.Food.taffy.get()) TAFFY = ITEMS.register("taffy", TaffyItem::new);
        if (TIConfig.Food.notchCarrot.get()) NOTCH_CARROT = ITEMS.register("notch_carrot", NotchCarrotItem::new);

        if (TIConfig.Food.fiddlehead.get()) {
            FIDDLEHEAD = ITEMS.register("fiddlehead", () -> new Item(props().food(ModFoods.FIDDLEHEAD)));
        }

        if (TIConfig.Food.bakedRoots.get()) {
            CLAYED_ROOT = ITEMS.register("clayed_root", () -> new Item(props()));
            BAKED_ROOT = ITEMS.register("baked_root", () -> new Item(props().food(ModFoods.BAKED_ROOT)));
        }

        if (TIConfig.Food.berrySorbet.get()) {
            BERRY_SORBET = ITEMS.register("berry_sorbet", () -> new SoupItem(props().food(ModFoods.BERRY_SORBET)));
        }

        if (TIConfig.Flora.wreath.get()) WREATH = ITEMS.register("flower_band", WreathItem::new);
    }

    @OnlyIn(Dist.CLIENT)
    public static void registerPropertyOverrides() {
        ItemModelsProperties.registerProperty(Items.SUSPICIOUS_STEW, new ResourceLocation("fiddlehead"), (stack, world, livingEntity) ->
            stack.hasTag() && stack.getTag().contains("fiddlehead") ? 1 : 0
        );
    }
}
