package azmalent.terraincognita.common.integration.theoneprobe;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.entity.butterfly.ButterflyEntity;
import mcjty.theoneprobe.api.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.world.World;

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
    public void addProbeEntityInfo(ProbeMode mode, IProbeInfo probeInfo, PlayerEntity player, World world, Entity entity, IProbeHitEntityData hit) {
        if (entity instanceof ButterflyEntity) {
            ButterflyEntity butterfly = (ButterflyEntity) entity;
            probeInfo.text(butterfly.getTypeDisplayName());
        }
    }
}
