package azmalent.terraincognita.common.world.biome;

import azmalent.terraincognita.common.world.ModDefaultFeatures;
import net.minecraft.data.worldgen.BiomeDefaultFeatures;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.BiomeGenerationSettings;
import net.minecraft.world.level.biome.BiomeSpecialEffects;
import net.minecraft.world.level.biome.BiomeSpecialEffects.GrassColorModifier;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraftforge.common.BiomeDictionary;

import java.util.List;

@SuppressWarnings({"deprecation", "removal"})
public class TundraBiome extends TIBiomeEntry {
    //todo
    private static final GrassColorModifier GRASS_COLOR = GrassColorModifier.create("tundra", "TUNDRA", (x, z, color) -> {
        double d0 = Biome.BIOME_INFO_NOISE.getValue(x * 0.0225D, z * 0.0225D, false);
        return d0 < -0.1D ? 5011004 : 6975545;
    });

    public TundraBiome(String name) {
        super(name);
    }

    @Override
    protected Biome.BiomeCategory getCategory() {
        return Biome.BiomeCategory.PLAINS;
    }

    @Override
    public List<BiomeDictionary.Type> getBiomeDictionaryTypes() {
        return List.of(BiomeDictionary.Type.OVERWORLD, BiomeDictionary.Type.PLAINS, BiomeDictionary.Type.COLD);
    }

    @Override
    protected float getTemperature() {
        return 0.2f;
    }

    @Override
    protected float getRainfall() {
        return 0.6f;
    }

    @Override
    protected BiomeSpecialEffects getSpecialEffects() {
        return defaultSpecialEffects().foliageColorOverride(0xDAA520).grassColorModifier(GRASS_COLOR).build();
    }

    @Override
    public void initFeatures(BiomeGenerationSettings.Builder builder) {
        defaultFeatures(builder);

        BiomeDefaultFeatures.addDefaultGrass(builder);
        BiomeDefaultFeatures.addDefaultExtraVegetation(builder);
        BiomeDefaultFeatures.addDefaultMushrooms(builder);

        BiomeDefaultFeatures.addDefaultOres(builder);
        BiomeDefaultFeatures.addDefaultSoftDisks(builder);

        ModDefaultFeatures.caribouMoss(builder);
        ModDefaultFeatures.arcticFlowers(builder);
        ModDefaultFeatures.tundraBoulders(builder);
        ModDefaultFeatures.tundraVegetation(builder);
    }

    @Override
    public void initSpawns(MobSpawnSettings.Builder builder) {
        BiomeDefaultFeatures.commonSpawns(builder);
        builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.SHEEP, 12, 4, 4));
        builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.CHICKEN, 10, 4, 4));
        builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.RABBIT, 10, 2, 3));
        builder.addSpawn(MobCategory.CREATURE, new MobSpawnSettings.SpawnerData(EntityType.POLAR_BEAR, 3, 1, 2));
    }
}
