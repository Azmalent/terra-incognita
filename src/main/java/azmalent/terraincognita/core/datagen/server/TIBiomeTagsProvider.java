package azmalent.terraincognita.core.datagen.server;

import azmalent.terraincognita.TerraIncognita;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BiomeTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

public class TIBiomeTagsProvider extends BiomeTagsProvider {
    public TIBiomeTagsProvider(DataGenerator gen, ExistingFileHelper exFileHelper) {
        super(gen, TerraIncognita.MODID, exFileHelper);
    }

    @Override
    protected void addTags() {
        //TODO
    }
}
