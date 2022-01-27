package azmalent.terraincognita.client.particle;

import net.minecraft.client.particle.SpriteSet;
import net.minecraft.client.particle.ParticleProvider;
import net.minecraft.client.particle.ParticleRenderType;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.particle.TextureSheetParticle;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.core.BlockPos;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

@SuppressWarnings("FieldCanBeLocal")
@OnlyIn(Dist.CLIENT)
public class DandelionFluffParticle extends TextureSheetParticle {
    private final float PARTICLE_SCALE_FOR_ONE_BLOCK = 0.5f;
    private final float BASE_SIZE = 0.2f;

    public DandelionFluffParticle(ClientLevel world, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
        super(world, x, y, z, xSpeed, ySpeed, zSpeed);

        quadSize = PARTICLE_SCALE_FOR_ONE_BLOCK * BASE_SIZE;
        setSize(BASE_SIZE, BASE_SIZE);

        lifetime = (int)(20.0F / (random.nextFloat() * 0.9F + 0.1F));
        gravity = 0.05f;
    }

    @Nonnull
    @Override
    public ParticleRenderType getRenderType() {
        return ParticleRenderType.PARTICLE_SHEET_LIT;
    }

    @Override
    protected int getLightColor(float partialTick) {
        BlockPos blockPos = new BlockPos(this.x, this.y, this.z);
        return this.level.hasChunkAt(blockPos) ? LevelRenderer.getLightColor(this.level, blockPos) : 0;
    }

    @OnlyIn(Dist.CLIENT)
    public record Factory(SpriteSet sprite) implements ParticleProvider<SimpleParticleType> {
        @Override
        public @NotNull Particle createParticle(@Nonnull SimpleParticleType data, @Nonnull ClientLevel level, double x, double y, double z, double xSpeed, double ySpeed, double zSpeed) {
            DandelionFluffParticle particle = new DandelionFluffParticle(level, x, y, z, xSpeed, ySpeed, zSpeed);
            particle.pickSprite(sprite);
            return particle;
        }
    }
}
