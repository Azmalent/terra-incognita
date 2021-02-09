package azmalent.terraincognita.client.particle;

import azmalent.terraincognita.TerraIncognita;
import net.minecraft.client.particle.IAnimatedSprite;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.IParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.SpriteTexturedParticle;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particles.BasicParticleType;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@OnlyIn(Dist.CLIENT)
public class DandelionFluffParticle extends SpriteTexturedParticle {

    private final float PARTICLE_SCALE_FOR_ONE_BLOCK = 0.5f;
    private final float BASE_SIZE = 0.2f;

    public DandelionFluffParticle(ClientWorld world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(world, x, y, z, xSpeed, ySpeed, zSpeed);

        particleScale = PARTICLE_SCALE_FOR_ONE_BLOCK * BASE_SIZE;
        setSize(BASE_SIZE, BASE_SIZE);

        maxAge = (int)(20.0F / (rand.nextFloat() * 0.9F + 0.1F));
        particleGravity = 0.05f;
    }

    @Nonnull
    @Override
    public IParticleRenderType getRenderType() {
        return IParticleRenderType.PARTICLE_SHEET_LIT;
    }

    @Override
    protected int getBrightnessForRender(float partialTick) {
        BlockPos blockPos = new BlockPos(this.posX, this.posY, this.posZ);
        return this.world.isBlockLoaded(blockPos) ? WorldRenderer.getCombinedLight(this.world, blockPos) : 0;
    }

    @OnlyIn(Dist.CLIENT)
    public static class Factory implements IParticleFactory<BasicParticleType> {
        private final IAnimatedSprite sprite;

        public Factory(IAnimatedSprite sprite) {
            this.sprite = sprite;
        }

        @Nullable
        @Override
        public Particle makeParticle(@Nonnull BasicParticleType data, @Nonnull ClientWorld worldIn, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            DandelionFluffParticle particle = new DandelionFluffParticle(worldIn, x, y, z, xSpeed, ySpeed, zSpeed);
            particle.selectSpriteRandomly(sprite);
            return particle;
        }
    }
}
