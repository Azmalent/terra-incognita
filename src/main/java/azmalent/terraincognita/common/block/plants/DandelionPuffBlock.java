package azmalent.terraincognita.common.block.plants;

import azmalent.terraincognita.TerraIncognita;
import azmalent.terraincognita.common.init.ModParticles;
import azmalent.terraincognita.common.init.ModSounds;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.FlowerBlock;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.ItemStack;
import net.minecraft.item.UseAction;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.potion.Effects;
import net.minecraft.stats.Stats;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.world.World;

import java.util.Random;

public class DandelionPuffBlock extends FlowerBlock {
    public DandelionPuffBlock() {
        super(Effects.SATURATION, 6, Properties.from(Blocks.AZURE_BLUET));
    }


}
