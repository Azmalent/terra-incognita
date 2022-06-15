package azmalent.terraincognita;

import azmalent.cuneiform.config.Comment;
import azmalent.cuneiform.config.ConfigFile;
import azmalent.cuneiform.config.Name;
import azmalent.cuneiform.config.options.BooleanOption;
import azmalent.cuneiform.config.options.DoubleOption;
import azmalent.cuneiform.config.options.IntOption;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = TerraIncognita.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TIServerConfig extends ConfigFile {
    public static TIServerConfig INSTANCE = new TIServerConfig();

    private TIServerConfig() {
        super(TerraIncognita.MODID, ModConfig.Type.SERVER);
    }

    //TODO: rewrite this stuff properly in cuneiform
    @Override
    public void register() {
        buildSpec();
        ModLoadingContext.get().registerConfig(configType, spec, getConfigFilename());
    }

    @SubscribeEvent
    public static void onServerStart(ServerAboutToStartEvent event) {
        initConfigFlags(TIServerConfig.class, TerraIncognita.MODID);
    }

    public static final class Peat {
        public static final DoubleOption growthRateBonus = DoubleOption.inUnitRange(0.25);
        public static final BooleanOption particlesEnabled = BooleanOption.of(true);
    }

    public static final class Tweaks {
        @Comment("This tweak only applies if apple trees are enabled.")
        public static final BooleanOption disableAppleDropFromOaks = BooleanOption.of(true, "remove_oak_apples");

        @Comment("Adds height variation to cacti, sugarcane and sedge.")
        public static final BooleanOption plantHeightVariation = BooleanOption.of(true);

        @Comment("Allows growing lily pads by using bonemeal in shallow water pools.")
        public static final BooleanOption bonemealLilypadGrowing = BooleanOption.of(true);
    }

    public static final class Food {
        @Name("Fern Fiddlehead Enabled")
        @Comment({"Fern fiddleheads can be found when breaking ferns. You can eat them raw or add them to suspicious stews.",
            "Duration of suspicious stew's effect is doubled when a fiddlehead is added during or after cooking.",
            "If the effect is negative, duration is halved instead."
        })
        public static final BooleanOption fiddlehead = BooleanOption.of(true, "fiddlehead");

        public static final DoubleOption fiddleheadDropChance = DoubleOption.inUnitRange(0.2);

        @Name("Taffy Enabled")
        @Comment("Taffy is a food item that can only be found in dungeons. " +
            "It restores some health, but also makes your mouth sticky, reducing eating speed with every taffy eaten.")
        public static final BooleanOption taffy = BooleanOption.of(true);

        @Name("Taffy Healing Amount")
        public static final IntOption taffyHealing = IntOption.inRange(3, 0, 8);

        @Name("Enchanted Golden Carrot Enabled")
        @Comment({"Enchanted golden carrot grants 30 seconds of Regeneration II, 2 minutes of Absorption IV, 5 minutes of Speed II and 10 minutes of Night Vision when eaten.",
            "It can be found in dungeon loot with the same rarity as enchanted golden apples."})
        public static final BooleanOption notchCarrot = BooleanOption.of(true);

        @Comment("Adds kelp soup and sweet berry sorbet.")
        public static final BooleanOption extraVanillaFoods = BooleanOption.of(true, "vanilla_foods");
    }

    public static final class Tools {
        @Name("Basket Enabled")
        @Comment({"Basket is a portable container crafted from sedge (or sugar cane if sedge is disabled). It can be placed or opened from inventory.",
            "Baskets have 9 slots and can only store forage such as flowers, saplings, mushrooms, seeds and eggs.",
            "When you have a basket in your hand, it will automatically collect compatible items."})
        public static final BooleanOption basket = BooleanOption.of(true, "basket");

        @Name("Caltrops Enabled")
        @Comment({"Caltrops are metal spikes that can be placed on the ground. Dropped caltrops will also place themselves if possible.",
            "Caltrops deal one heart of damage and inflict slowness for 10 seconds when touched.",
            "You can right click caltrops with an empty hand to pick them up. They will also be flushed away by flowing water."})
        public static final BooleanOption caltrops = BooleanOption.of(true, "caltrops");

        @Comment("The chance for caltrops to break when dealing damage.")
        public static final DoubleOption caltropsBreakChance = DoubleOption.inUnitRange(0.125);

        @Name("Cactus Shearing & Blowpipe Enabled")
        public static final BooleanOption blowpipe = BooleanOption.of(true, "blowpipe");
    }
}
