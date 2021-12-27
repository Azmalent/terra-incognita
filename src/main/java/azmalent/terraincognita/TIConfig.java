package azmalent.terraincognita;

import azmalent.cuneiform.lib.config.Category;
import azmalent.cuneiform.lib.config.CommonConfigFile;
import azmalent.cuneiform.lib.config.annotations.Comment;
import azmalent.cuneiform.lib.config.annotations.Name;
import azmalent.cuneiform.lib.config.options.BooleanOption;
import azmalent.cuneiform.lib.config.options.DoubleOption;
import azmalent.cuneiform.lib.config.options.IntOption;
import azmalent.cuneiform.lib.config.options.lazy.RegistryListOption;
import azmalent.terraincognita.common.world.biome.BiomeEntry;
import com.google.common.collect.Lists;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

@SuppressWarnings("unused")
public class TIConfig extends CommonConfigFile {
    protected TIConfig() {
        super(TerraIncognita.MODID);
    }

    public static void init() {
        new TIConfig().register();
    }

    @Comment("Worldgen features will not be added to the biomes listed here.")
    public static final RegistryListOption<Biome> biomeBlacklist = new RegistryListOption<>(ForgeRegistries.BIOMES, Lists.newArrayList());

    public static class Food extends Category {
        @Name("Fern Fiddlehead Enabled")
        @Comment({"Fern fiddleheads can be found when breaking ferns. You can eat them raw or add them to suspicious stews.",
            "Duration of suspicious stew's effect is doubled when a fiddlehead is added during or after cooking.",
            "If the effect is negative, duration is halved instead."
        })
        public static final BooleanOption fiddlehead = new BooleanOption(true).withFlag("fiddlehead");

        public static final DoubleOption fiddleheadDropChance = new DoubleOption(0.2).inUnitRange();

        @Name("Taffy Enabled")
        @Comment("Taffy is a food item that can only be found in dungeons. " +
                "It restores some health, but also makes your mouth sticky, reducing eating speed with every taffy eaten.")
        public static final BooleanOption taffy = new BooleanOption(true);

        @Name("Taffy Healing Amount")
        @Comment("How many half hearts are healed when eating a taffy.")
        public static final IntOption taffyHealing = new IntOption(3).inRange(0, 8);

        @Name("Enchanted Golden Carrot Enabled")
        @Comment({"Enchanted golden carrot grants 30 seconds of Regeneration II, 2 minutes of Absorption IV, 5 minutes of Speed II and 10 minutes of Night Vision when eaten.",
            "It can be found in dungeon loot with the same rarity as enchanted golden apples."})
        public static final BooleanOption notchCarrot = new BooleanOption(true);

        @Name("Extra Vanilla Foods")
        @Comment("Adds kelp soup and sweet berry sorbet.")
        public static final BooleanOption kelpSoup = new BooleanOption(true).withFlag("vanilla_foods");
    }

    public static class Flora extends Category {
        @Name("Dandelion Puff Enabled")
        @Comment("Dandelion puffs spawn among vanilla dandelions. You can blow on a dandelion puff by using it.")
        public static final BooleanOption dandelionPuff = new BooleanOption(true).withFlag("dandelion_puff");

        @Name("Dandelion Puff Ratio")
        @Comment("Chance to replace a vanilla dandelion with a dandelion puff during generation.")
        public static final DoubleOption dandelionPuffChance = new DoubleOption(0.5).inUnitRange();

        @Name("Field Flowers Enabled")
        @Comment("Adds chicory, yarrow and daffodils to plains. Daffodils are rare, but they can also be found in flower forests.")
        public static final BooleanOption fieldFlowers = new BooleanOption(true).withFlag("field_flowers");

		@Name("Forest Flowers Enabled")
        @Comment("Adds wild garlic, primroses and foxgloves to forests. These flowers can also be found in flower forests.")
        public static final BooleanOption forestFlowers = new BooleanOption(true).withFlag("forest_flowers");

        @Name("Swamp Flowers Enabled")
        @Comment("Adds forget-me-not, globeflowers and water flags to temperate and hot swamps.")
        public static final BooleanOption swampFlowers = new BooleanOption(true).withFlag("swamp_flowers");

        @Name("Cattails Enabled")
        @Comment("Adds cattails to swamps. Cattails can be duplicated using bonemeal and crafted into brown dye.")
        public static final BooleanOption cattails = new BooleanOption(true).withFlag("cattails");

        @Name("Small Lilypads Enabled")
        @Comment("Small lilypads can be found in swamps. They can be stacked up to 4, like sea pickles.")
        public static final BooleanOption smallLilypad = new BooleanOption(true);

        @Name("Reeds Enabled")
        @Comment({"Reeds are a sugarcane-like plant that can be found in swamps. They can be grown in the same way as sugar cane, or, alternatively, in one block deep water.",
            "Reeds can be crafted into a variety of decorative wicker blocks and baskets."})
        public static final BooleanOption reeds = new BooleanOption(true).withFlag("reeds");

        @Name("Savanna Flowers Enabled")
        @Comment("Adds marigolds, blue lupines, snapdragons, gladioluses, geraniums, oleanders and sage to savannas.")
        public static final BooleanOption savannaFlowers = new BooleanOption(true).withFlag("savanna_flowers");

        @Name("Jungle Flowers Enabled")
        @Comment("Adds blue, black and purple irises to jungles.")
        public static final BooleanOption jungleFlowers = new BooleanOption(true).withFlag("jungle_flowers");

        @Name("Lotus Enabled")
        @Comment("Lotus is a beautiful aquatic flower found in jungle lakes. It comes in yellow, pink and white colors.")
        public static final BooleanOption lotus = new BooleanOption(true).withFlag("lotus");

        @Name("Alpine Flowers Enabled")
        @Comment({"Adds edelweiss, alpine pink, saxifrage and gentian to temperate and cold mountain biomes.",
                "All of these flowers can grow on gravel, and saxifrage can grow on stone as well.",
                "Edelweiss has a minimum altitude requirement to generate."})
        public static final BooleanOption alpineFlowers = new BooleanOption(true).withFlag("alpine_flowers");

        @Name("Minimum Altitude for Edelweiss")
        @Comment("Minimum Y level where edelweiss can generate in the mountains.")
        public static final IntOption edelweissMinimumY = new IntOption(90).inRange(64, 128);

        @Name("Arctic Flowers Enabled")
        @Comment("Adds white dryads, fireweed, arctic poppies and white rhododendrons to taiga and tundra biomes.")
        public static final BooleanOption arcticFlowers = new BooleanOption(true).withFlag("arctic_flowers");

        @Name("Arctic Poppy Ratio")
        @Comment("Chance to replace a vanilla poppy with an arctic poppy in tundra biomes.")
        public static final DoubleOption arcticPoppyChance = new DoubleOption(0.5).inUnitRange();

        @Name("Sweet Peas Enabled")
        @Comment("Sweet peas are flowering vines found in flower forests. They come in seven different colors.")
        public static final BooleanOption sweetPeas = new BooleanOption(true).withFlag("sweet_peas");

        @Name("Wreaths Enabled")
        @Comment({"Wreath is a cosmetic headdress crafted with 4 small flowers of any kind in a 2x2 shape.",
                "The color of the wreath depends on the flowers you used to craft it."})
        public static final BooleanOption wreath = new BooleanOption(true).withFlag("wreath");

        @Name("Caribou Moss Enabled")
        @Comment("Caribou moss is a grass-like plant found in tundras.")
        public static final BooleanOption caribouMoss = new BooleanOption(true);

        @Name("Sour Berries Enabled")
        @Comment("Sour berries can be found growing on water in boreal swamps.")
        public static final BooleanOption sourBerries = new BooleanOption(true).withFlag("sour_berries");

        @Name("Roots Enabled")
        @Comment({"Roots can be found on dirt cave ceilings and overhangs in most biomes.",
            "They can be collected with shears. You can spread roots by using bonemeal on one.",
            "Roots can be crafted into brown dye or covered in clay and baked on a campfire. Baked roots grant 15 seconds of Haste when eaten."})
        public static final BooleanOption roots = new BooleanOption(true).withFlag("roots");

        @Name("Hanging Moss Enabled")
        public static final BooleanOption hangingMoss = new BooleanOption(true).withFlag("hanging_moss");

        @Name("Cactus Flowers Enabled")
        public static final BooleanOption cactusFlowers = new BooleanOption(true).withFlag("cactus_flowers");

        @Name("Small Cacti Enabled")
        public static final BooleanOption smallCacti = new BooleanOption(true).withFlag("small_cacti");
    }

    public static class Fauna extends Category {
        public static final IntOption butterflySpawnWeight = new IntOption(10).inRange(0, 100);
    }

    public static class Trees extends Category {
        @Name("Apple Trees Enabled")
        public static final BooleanOption apple = new BooleanOption(true).withFlag("apple");

        @Name("Hazel Trees Enabled")
        public static final BooleanOption hazel = new BooleanOption(true).withFlag("hazel");
    }

    public static class Biomes extends Category {
        public static final IntOption lushPlainsWeight = new IntOption(1).nonNegative();
        public static final IntOption tundraWeight = new IntOption(5).nonNegative();
        public static final IntOption muskegWeight = new IntOption(3).nonNegative();
    }

    public static class Tools extends Category {
        @Name("Basket Enabled")
        @Comment({"Basket is a portable container crafted from swamp reeds (or sugar cane if reeds are disabled). It can be placed or opened from inventory.",
                "Baskets have 9 slots and can only store forage such as flowers, saplings, mushrooms, seeds and eggs.",
                "When you have a basket in your hand, it will automatically collect compatible items."})
        public static final BooleanOption basket = new BooleanOption(true).withFlag("basket");

        @Name("Caltrops Enabled")
        @Comment({"Caltrops are metal spikes that can be placed on the ground. Dropped caltrops will also place themselves if possible.",
            "Caltrops deal one heart of damage and inflict slowness for 10 seconds when touched.",
            "You can right click caltrops with an empty hand to pick them up. They will also be flushed away by flowing water."})
        public static final BooleanOption caltrops = new BooleanOption(true).withFlag("caltrops");

        @Comment("The chance for caltrops to break when dealing damage.")
        public static final DoubleOption caltropsBreakChance = new DoubleOption(0.125).inUnitRange();

        @Name("Blowpipe Enabled")
        public static final BooleanOption blowpipe = new BooleanOption(true).withFlag("blowpipe");
    }

    @SuppressWarnings("GrazieInspection")
    public static class Misc extends Category {
        @Name("Peat Enabled")
        @Comment({"Peat is a block found underwater in swamps similar to clay.",
            "It can be tilled and used to plant crops, which will grow faster than on regular farmland.",
            "One peat block can also be used as fuel to smelt 12 items."})
        public static final BooleanOption peat = new BooleanOption(true);

        public static final DoubleOption peatGrowthRateBonus = new DoubleOption(0.25).inUnitRange();

        @Name("Mossy Gravel Enabled")
        @Comment("Mossy Gravel is found in swamps. It can also be crafted using gravel and vines/hanging moss.")
        public static final BooleanOption mossyGravel = new BooleanOption(true).withFlag("mossy_gravel");

        @Comment("This tweak only applies if apple trees are enabled.")
        public static final BooleanOption disableAppleDropFromOaks = new BooleanOption(true).withFlag("remove_oak_apples");

        @Comment("Adds height variation to cacti, sugarcane and reeds.")
        public static final BooleanOption plantHeightVariation = new BooleanOption(true);

        @Comment("Adds composting recipes for dead bushes, bamboo, poisonous potatoes and chorus fruits/flowers.")
        public static final BooleanOption additionalCompostables = new BooleanOption(true);

        @Comment("Allows growing lily pads by using bonemeal in shallow water pools.")
        public static final BooleanOption bonemealLilypadGrowing = new BooleanOption(true);

        @Comment("Adds foxes, sheep, berry bushes, birches and shrubs to tundras to make them less lackluster.")
        public static final BooleanOption betterTundras = new BooleanOption(true);

        @Comment("If enabled, wither roses will generate naturally in Soul Sand Valleys.")
        public static final BooleanOption witherRoseGeneration = new BooleanOption(true);

        public static final BooleanOption bannerPatterns = new BooleanOption(true).withFlag("banners");
    }

    public static class Integration extends Category {
        public static class Quark extends Category {
            @Comment("Chance to generate a marigold fairy ring in savanna biome chunk. Set to 0 to disable.")
            public static final DoubleOption savannaFairyRingChance = new DoubleOption(0.0025).inUnitRange();
        }

        public static class Environmental extends Category {
            public static final BooleanOption delphiniumsInLushPlains = new BooleanOption(true);
        }
    }
}
