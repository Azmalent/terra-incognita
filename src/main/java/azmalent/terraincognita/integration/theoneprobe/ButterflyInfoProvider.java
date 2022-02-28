package azmalent.terraincognita.integration.theoneprobe;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.entity.butterfly.Butterfly;
import mcjty.theoneprobe.api.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.function.Function;

public class ButterflyInfoProvider implements IProbeInfoEntityProvider, Function<ITheOneProbe, Void> {
    @Override
    public String getID() {
        return TerraIncognita.prefix("entity.butterfly").toString();
    }

    @Override
    public Void apply(ITheOneProbe probe) {
        probe.registerEntityProvider(this);
        return null;
    }

    @Override
    public void addProbeEntityInfo(ProbeMode mode, IProbeInfo probeInfo, Player player, Level world, Entity entity, IProbeHitEntityData hit) {
        if (entity instanceof Butterfly butterfly) {
            probeInfo.text(butterfly.getTypeDisplayName());
        }
    }
}
