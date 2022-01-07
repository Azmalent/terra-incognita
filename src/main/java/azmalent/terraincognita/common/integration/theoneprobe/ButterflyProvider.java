package azmalent.terraincognita.common.integration.theoneprobe;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.entity.butterfly.ButterflyEntity;
import mcjty.theoneprobe.api.*;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.function.Function;

public class ButterflyProvider implements IProbeInfoEntityProvider, Function<ITheOneProbe, Void> {
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
        if (entity instanceof ButterflyEntity) {
            ButterflyEntity butterfly = (ButterflyEntity) entity;
            probeInfo.text(butterfly.getTypeDisplayName());
        }
    }
}
