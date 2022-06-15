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

    public static final class Flora {
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

    public static final class Fauna {
        public static final IntOption butterflySpawnWeight = IntOption.inRange(10, 0, 100);
    }

    public static final class Trees {
        @Name("Apple Trees Enabled")
        public static final BooleanOption apple = BooleanOption.of(true, "apple");

        @Name("Hazel Trees Enabled")
        public static final BooleanOption hazel = BooleanOption.of(true, "hazel");

        @Name("Larch Trees Enabled")
        public static final BooleanOption larch = BooleanOption.of(true, "larch");

        @Name("Ginkgo Trees Enabled")
        public static final BooleanOption ginkgo = BooleanOption.of(true, "ginkgo");
    }

    public static final class Biomes {
        public static final IntOption biomeWeight = IntOption.nonNegative(3);

        public static final BooleanOption lushPlains = BooleanOption.of(true);
        public static final BooleanOption tundra = BooleanOption.of(true);
        public static final BooleanOption muskeg = BooleanOption.of(true);
    }

    public static final class Misc {
        @Name("Peat Enabled")
        @Comment({"Peat is a block found underwater in swamps similar to clay.",
            "It can be tilled and used to plant crops, which will grow faster than on regular farmland.",
            "One peat block can also be used as fuel to smelt 12 items."})
        public static final BooleanOption peat = BooleanOption.of(true);

        @Name("Mossy Gravel Enabled")
        @Comment("Mossy Gravel is found in swamps. It can also be crafted using gravel and vines/hanging moss.")
        public static final BooleanOption mossyGravel = BooleanOption.of(true, "mossy_gravel");

        @Comment("Adds composting recipes for dead bushes, bamboo, poisonous potatoes and chorus fruits/flowers.")
        public static final BooleanOption additionalCompostables = BooleanOption.of(true);

        @Comment("If enabled, wither roses will generate naturally in Soul Sand Valleys.")
        public static final BooleanOption witherRoseGeneration = BooleanOption.of(true);

        public static final BooleanOption bannerPatterns = BooleanOption.of(true, "banners");
    }

    public static final class Integration {
        public static final class Quark {
            @Comment("Chance to generate a marigold fairy ring in savanna biome chunk. Set to 0 to disable.")
            public static final DoubleOption savannaFairyRingChance = DoubleOption.inUnitRange(0.0025);
        }
    }
}
