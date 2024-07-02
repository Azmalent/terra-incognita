package azmalent.terraincognita;

import azmalent.cuneiform.config.Comment;
import azmalent.cuneiform.config.ConfigFile;
import azmalent.cuneiform.config.options.BooleanOption;
import azmalent.cuneiform.config.options.DoubleOption;
import azmalent.cuneiform.config.options.IntOption;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;

@SuppressWarnings("unused")
@Mod.EventBusSubscriber(modid = TerraIncognita.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class TIServerConfig extends ConfigFile {
    public static TIServerConfig INSTANCE = new TIServerConfig();

    private TIServerConfig() {
        super(TerraIncognita.MODID, ModConfig.Type.SERVER);
    }

    public static final class Peat {
        public static final DoubleOption growthRateBonus = DoubleOption.inUnitRange(0.25);
        public static final BooleanOption particlesEnabled = BooleanOption.of(true);
    }

    @Comment("Adds height variation to cacti, sugarcane and swamp reeds.")
    public static final BooleanOption plantHeightVariation = BooleanOption.of(true);

    @Comment("Allows growing lily pads by using bonemeal in shallow water pools.")
    public static final BooleanOption bonemealLilypadGrowing = BooleanOption.of(true);

    public static final IntOption taffyHealingAmount = IntOption.inRange(3, 0, 8);

    public static final DoubleOption fiddleheadDropChance = DoubleOption.inUnitRange(0.2);

    public static final DoubleOption caltropsBreakChance = DoubleOption.inUnitRange(0.125);
}
