package azmalent.terraincognita;

import azmalent.cuneiform.config.Comment;
import azmalent.cuneiform.config.ConfigFile;
import azmalent.cuneiform.config.Name;
import azmalent.cuneiform.config.options.BooleanOption;
import azmalent.cuneiform.config.options.DoubleOption;
import azmalent.cuneiform.config.options.IntOption;
import net.minecraftforge.fml.config.ModConfig;

@SuppressWarnings("unused")
public class TIConfig extends ConfigFile {
    public static TIConfig INSTANCE = new TIConfig();

    private TIConfig() {
        super(TerraIncognita.MODID, ModConfig.Type.COMMON);
    }

    public static class Food {
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
        @Comment("How many half hearts are healed when eating a taffy.")
        public static final IntOption taffyHealing = IntOption.inRange(3, 0, 8);

        @Name("Enchanted Golden Carrot Enabled")
        @Comment({"Enchanted golden carrot grants 30 seconds of Regeneration II, 2 minutes of Absorption IV, 5 minutes of Speed II and 10 minutes of Night Vision when eaten.",
            "It can be found in dungeon loot with the same rarity as enchanted golden apples."})
        public static final BooleanOption notchCarrot = BooleanOption.of(true);

        @Name("Extra Vanilla Foods")
        @Comment("Adds kelp soup and sweet berry sorbet.")
        public static final BooleanOption kelpSoup = BooleanOption.of(true, "vanilla_foods");
    }

    public static class Flora {
        @Name("Dandelion Puff Enabled")
        @Comment("Dandelion puffs spawn among vanilla dandelions. You can blow on a dandelion puff by using it.")
        public static final BooleanOption dandelionPuff = BooleanOption.of(true, "dandelion_puff");

        @Name("Dandelion Puff Ratio")
        @Comment("Chance to replace a vanilla dandelion with a dandelion puff during generation.")
        public static final DoubleOption dandelionPuffChance = DoubleOption.inUnitRange(0.5);

        @Name("Field Flowers Enabled")
        @Comment("Adds chicory, yarrow and daffodils to plains. These flowers can also be found in flower forests.")
        public static final BooleanOption fieldFlowers = BooleanOption.of(true, "field_flowers");

		@Name("Forest Flowers Enabled")
        @Comment("Adds wild garlic, primroses and foxgloves to forests. These flowers can also be found in flower forests.")
        public static final BooleanOption forestFlowers = BooleanOption.of(true, "forest_flowers");

        @Name("Swamp Flowers Enabled")
        @Comment("Adds forget-me-not, globeflowers and water flags to temperate and hot swamps.")
        public static final BooleanOption swampFlowers = BooleanOption.of(true, "swamp_flowers");

        @Name("Small Lilypads Enabled")
        @Comment("Small lilypads can be found in swamps. They can be stacked up to 4, like sea pickles.")
        public static final BooleanOption smallLilypad = BooleanOption.of(true);

        @Name("Sedge Enabled")
        @Comment({"Sedge is a sugarcane-like plant that can be found in swamps. It can be grown in the same way as sugar cane, or, alternatively, in one block deep water.",
            "Sedge can be crafted into a variety of decorative wicker blocks and baskets."})
        public static final BooleanOption sedge = BooleanOption.of(true, "sedge");

        @Name("Savanna Flowers Enabled")
        @Comment("Adds marigolds, snapdragons, irises, oleanders and sage to savannas.")
        public static final BooleanOption savannaFlowers = BooleanOption.of(true, "savanna_flowers");

        @Name("Lotus Enabled")
        @Comment("Lotus is a beautiful aquatic flower found in jungle lakes. It comes in yellow, pink and white colors.")
        public static final BooleanOption lotus = BooleanOption.of(true, "lotus");

        @Name("Alpine Flowers Enabled")
        @Comment({"Adds edelweiss, alpine pink, saxifrage, asters and and gentian to temperate and cold mountain biomes.",
                "All of these flowers can grow on gravel, and saxifrages can grow on stone as well.",
                "Edelweiss has a minimum altitude requirement to generate."})
        public static final BooleanOption alpineFlowers = BooleanOption.of(true, "alpine_flowers");

        @Name("Minimum Altitude for Edelweiss")
        @Comment("Minimum Y level where edelweiss can generate in the mountains.")
        public static final IntOption edelweissMinimumY = IntOption.inRange(128, 96, 256);

        @Name("Arctic Flowers Enabled")
        @Comment("Adds heather, white dryads, fireweed and white rhododendrons to taiga and tundra biomes.")
        public static final BooleanOption arcticFlowers = BooleanOption.of(true, "arctic_flowers");

        @Name("Sweet Peas Enabled")
        @Comment("Sweet peas are flowering vines found in flower forests. They come in seven different colors.")
        public static final BooleanOption sweetPeas = BooleanOption.of(true, "sweet_peas");

        @Name("Wreaths Enabled")
        @Comment({"Wreath is a cosmetic headdress crafted with 4 small flowers of any kind in a 2x2 shape.",
                "The color of the wreath depends on the flowers you used to craft it."})
        public static final BooleanOption wreath = BooleanOption.of(true, "wreath");

        @Name("Caribou Moss Enabled")
        @Comment("Caribou moss is a grass-like plant found in tundras.")
        public static final BooleanOption caribouMoss = BooleanOption.of(true);

        @Name("Sour Berries Enabled")
        @Comment("Sour berries can be found growing on water in muskeg biomes.")
        public static final BooleanOption sourBerries = BooleanOption.of(true, "sour_berries");

        @Name("Hanging Moss Enabled")
        public static final BooleanOption hangingMoss = BooleanOption.of(true, "hanging_moss");

        @Name("Cactus Flowers Enabled")
        public static final BooleanOption cactusFlowers = BooleanOption.of(true, "cactus_flowers");
    }

    public static class Fauna {
        public static final IntOption butterflySpawnWeight = IntOption.inRange(10, 0, 100);
    }

    public static class Trees {
        @Name("Apple Trees Enabled")
        public static final BooleanOption apple = BooleanOption.of(true, "apple");

        @Name("Hazel Trees Enabled")
        public static final BooleanOption hazel = BooleanOption.of(true, "hazel");

        @Name("Larch Trees Enabled")
        public static final BooleanOption larch = BooleanOption.of(true, "larch");

        @Name("Ginkgo Trees Enabled")
        public static final BooleanOption ginkgo = BooleanOption.of(true, "ginkgo");
    }

    public static class Biomes {
        public static final IntOption biomeWeight = IntOption.nonNegative(3);

        public static final BooleanOption lushPlains = BooleanOption.of(true);
        public static final BooleanOption tundra = BooleanOption.of(true);
        public static final BooleanOption muskeg = BooleanOption.of(true);
    }

    public static class Tools {
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

    public static class Misc {
        @Name("Peat Enabled")
        @Comment({"Peat is a block found underwater in swamps similar to clay.",
            "It can be tilled and used to plant crops, which will grow faster than on regular farmland.",
            "One peat block can also be used as fuel to smelt 12 items."})
        public static final BooleanOption peat = BooleanOption.of(true);

        public static final DoubleOption peatGrowthRateBonus = DoubleOption.inUnitRange(0.25);

        @Name("Mossy Gravel Enabled")
        @Comment("Mossy Gravel is found in swamps. It can also be crafted using gravel and vines/hanging moss.")
        public static final BooleanOption mossyGravel = BooleanOption.of(true, "mossy_gravel");

        @Comment("This tweak only applies if apple trees are enabled.")
        public static final BooleanOption disableAppleDropFromOaks = BooleanOption.of(true, "remove_oak_apples");

        @Comment("Adds height variation to cacti, sugarcane and sedge.")
        public static final BooleanOption plantHeightVariation = BooleanOption.of(true);

        @Comment("Adds composting recipes for dead bushes, bamboo, poisonous potatoes and chorus fruits/flowers.")
        public static final BooleanOption additionalCompostables = BooleanOption.of(true);

        @Comment("Allows growing lily pads by using bonemeal in shallow water pools.")
        public static final BooleanOption bonemealLilypadGrowing = BooleanOption.of(true);

        @Comment("If enabled, wither roses will generate naturally in Soul Sand Valleys.")
        public static final BooleanOption witherRoseGeneration = BooleanOption.of(true);

        public static final BooleanOption bannerPatterns = BooleanOption.of(true, "banners");
    }

    public static class Integration {
        public static class Quark {
            @Comment("Chance to generate a marigold fairy ring in savanna biome chunk. Set to 0 to disable.")
            public static final DoubleOption savannaFairyRingChance = DoubleOption.inUnitRange(0.0025);
        }
    }
}
