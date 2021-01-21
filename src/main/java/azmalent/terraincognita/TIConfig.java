package azmalent.terraincognita;

import azmalent.cuneiform.lib.config.Category;
import azmalent.cuneiform.lib.config.CommonConfigFile;
import azmalent.cuneiform.lib.config.annotations.Comment;
import azmalent.cuneiform.lib.config.annotations.Name;
import azmalent.cuneiform.lib.config.options.BooleanOption;
import azmalent.cuneiform.lib.config.options.DoubleOption;
import azmalent.cuneiform.lib.config.options.IntOption;

import java.util.Random;

public class TIConfig extends CommonConfigFile {
    protected TIConfig() {
        super(TerraIncognita.MODID);
    }

    public static void init() {
        new TIConfig().register();
    }

    public static class Food extends Category {
        @Name("Fern Fiddlehead Enabled")
        @Comment({"Fern fiddleheads can be found when breaking ferns. You can eat them raw or add them to suspicious stews.",
            "Duration of suspicious stew's effect is doubled when a fiddlehead is added during or after cooking.",
            "If the effect is negative, duration is halved instead."
        })
        public static BooleanOption fiddlehead = new BooleanOption(true, "fiddlehead");

        @Name("Baked Root Enabled")
        @Comment({"Baked root can be made by covering a root with clay and cooking it on a campfire.",
            "It gives 15 seconds of Haste when eaten."})
        public static BooleanOption bakedRoots = new BooleanOption(true, "baked_roots");

        @Name("Taffy Enabled")
        @Comment("Taffy is a food item that can only be found in dungeons. " +
                "It restores some health, but also makes your mouth sticky, reducing eating speed with every taffy eaten.")
        public static BooleanOption taffy = new BooleanOption(true);

        @Name("Taffy Healing Amount")
        @Comment("How much half hearts are healed when eating a taffy.")
        public static IntOption taffyHealing = new IntOption(3, 2, 8);

        @Name("Enchanted Golden Carrot Enabled")
        @Comment({"Enchanted golden carrot grants 30 seconds of Regeneration II, 2 minutes of Absorption IV, 5 minutes of Speed II and 10 minutes of Night Vision when eaten.",
            "It can be found in dungeon loot with the same rarity as enchanted golden apples."})
        public static BooleanOption notchCarrot = new BooleanOption(true);

        @Name("Berry Sorbet Enabled")
        @Comment("Berry Sorbet is a new food item made with sweet berries, sugar and a snowball in a bowl.")
        public static BooleanOption berrySorbet = new BooleanOption(true, "sorbet");
    }

    public static class Flora extends Category {
        @Name("Dandelion Puff Enabled")
        @Comment("Dandelion puffs spawn among vanilla dandelions. You can blow on a dandelion puff by using it.")
        public static BooleanOption dandelionPuff = new BooleanOption(true, "dandelion_puff");

        @Name("Dandelion Puff Ratio")
        @Comment("Chance to replace a vanilla dandelion with a dandelion puff during generation.")
        public static DoubleOption dandelionPuffChance = new DoubleOption(0.5, 0, 1);

        @Name("Swamp Flowers Enabled")
        @Comment("Adds forget-me-not to temperate and hot swamps.")
        public static BooleanOption swampFlowers = new BooleanOption(true, "swamp_flowers");

        @Name("Small Lilypads Enabled")
        @Comment("Small lilypads can be found in swamps. They can be stacked up to 4, like sea pickles.")
        public static BooleanOption smallLilypad = new BooleanOption(true);

        @Name("Reeds Enabled")
        @Comment({"Reeds are a sugarcane-like plant that can be found in swamps. They can be grown in the same way as sugar cane, or, alternatively, in one block deep water.",
            "Reeds can be crafted into a variety of decorative wicker blocks and baskets.",
            "Flower baskets can store 9 stacks of flowers and will automatically collect them if you hold it while picking up flowers."})
        public static BooleanOption reeds = new BooleanOption(true, "reeds");

        @Name("Savanna Flowers Enabled")
        @Comment({"Marigold is a large orange flower found in biomes such as savannas and deserts.",
            "It is unique in that it can grow on sand, but only if there is water nearby."})
        public static BooleanOption savannaFlowers = new BooleanOption(true, "savanna_flowers");

        @Name("Jungle Flowers Enabled")
        @Comment("Adds blue and purple irises to jungles.")
        public static BooleanOption jungleFlowers = new BooleanOption(true, "jungle_flowers");

        @Name("Lotus Enabled")
        @Comment("Lotus is a beautiful aquatic flower found in jungles. It comes in yellow, pink and white colors.")
        public static BooleanOption lotus = new BooleanOption(true, "lotus");

        @Name("Alpine Flowers Enabled")
        @Comment({"Adds edelweiss, alpine pink and rockfoil to temperate and cold mountain biomes.",
                "All of these flowers can grow on gravel, and rockfoil can grow on stone as well.",
                "Edelweiss has a minimum altitude requirement to generate."})
        public static BooleanOption alpineFlowers = new BooleanOption(true, "alpine_flowers");

        @Name("Minimum Altitude for Edelweiss")
        @Comment("Minimum Y level where edelweiss can generate in the mountains.")
        public static IntOption edelweissMinimumY = new IntOption(90, 64, 128);

        @Name("Arctic Flowers Enabled")
        @Comment({"Adds fireweed to taiga and tundra biomes.",
        "Also adds arctic poppies that replace vanilla poppies in tundras."})
        public static BooleanOption arcticFlowers = new BooleanOption(true, "arctic_flowers");

        @Name("Wreaths Enabled")
        @Comment({"Wreath is a cosmetic headdress crafted with 4 small flowers of any kind in any shape.",
            "The color of the wreath depends on the flowers you used to craft it."})
        public static BooleanOption flowerBand = new BooleanOption(true, "flower_band");

        @Name("Roots Enabled")
        @Comment({"Roots can be found on dirt cave ceilings and overhangs in most biomes.",
            "They can be collected with shears. You can grow more roots by using bonemeal on one.",
            "Roots can be crafted into brown dye."})
        public static BooleanOption roots = new BooleanOption(true, "roots");
    }

    public static class Tools extends Category {
        @Name("Caltrops Enabled")
        @Comment({"Caltrops are metal spikes that can be placed on the ground. Dropped caltrops will also place themselves if possible.",
            "Caltrops deal one heart of damage and inflict slowness for 10 seconds when touched.",
            "They break with 1 in 5 chance after dealing damage, so they are not suitable as a permanent trap.",
            "Caltrops will be flushed away by flowing water.",
            "You can right click caltrops with an empty hand to pick them up."})
        public static BooleanOption caltrops = new BooleanOption(true, "caltrops");
    }

    public static class Biomes extends Category {
        @Comment("Adds snowless and rocky tundra biomes.")
        public static BooleanOption tundraVariants = new BooleanOption(true);
    }

    public static class Tweaks extends Category {
        @Comment("Adds foxes, sheep, berry bushes, birches and shrubs to tundras to make them less lackluster.")
        public static BooleanOption betterTundras = new BooleanOption(true);

        @Comment("Adds composting recipes for dead bushes, bamboo, poisonous potatoes and chorus fruits/flowers.")
        public static BooleanOption additionalCompostables = new BooleanOption(true);

        @Comment("Allows growing lilypads by using bonemeal in shallow water pools.")
        public static BooleanOption bonemealLilypadGrowing = new BooleanOption(true);

        @Comment("If enabled, wither roses will generate naturally in Soul Sand Valleys.")
        public static BooleanOption witherRoseGeneration = new BooleanOption(true);
    }
}
