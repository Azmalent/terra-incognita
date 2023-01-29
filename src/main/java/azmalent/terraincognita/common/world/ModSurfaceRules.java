package azmalent.terraincognita.common.world;

import azmalent.terraincognita.common.registry.ModBiomes;
import azmalent.terraincognita.common.registry.ModBlocks;
import azmalent.terraincognita.common.world.biome.TIBiomeEntry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.levelgen.SurfaceRules;
import terrablender.api.SurfaceRuleManager;

import static azmalent.terraincognita.TerraIncognita.MODID;

public class ModSurfaceRules {
    private static final SurfaceRules.RuleSource FLOWERING_GRASS = makeStateRule(ModBlocks.FLOWERING_GRASS.get());

    private static SurfaceRules.RuleSource makeStateRule(Block pBlock) {
        return SurfaceRules.state(pBlock.defaultBlockState());
    }

    private static SurfaceRules.ConditionSource isAboveWaterLevel() {
        return SurfaceRules.waterBlockCheck(0, 0);
    }

    public static void initRules() {
        SurfaceRuleManager.addSurfaceRules(SurfaceRuleManager.RuleCategory.OVERWORLD, MODID, SurfaceRules.sequence(
            biomeRule(ModBiomes.LUSH_PLAINS, lushPlainsRules())
        ));
    }

    @SuppressWarnings("SameParameterValue")
    private static SurfaceRules.RuleSource biomeRule(TIBiomeEntry biomeEntry, SurfaceRules.RuleSource rules) {
        return SurfaceRules.ifTrue(SurfaceRules.isBiome(biomeEntry.biome.getKey()), rules);
    }

    private static SurfaceRules.RuleSource lushPlainsRules() {
        var groundRules = SurfaceRules.ifTrue(
            SurfaceRules.ON_FLOOR,
            SurfaceRules.ifTrue(
                isAboveWaterLevel(),
                FLOWERING_GRASS
            )
        );

        return SurfaceRules.ifTrue(SurfaceRules.abovePreliminarySurface(), groundRules);
    }
}
