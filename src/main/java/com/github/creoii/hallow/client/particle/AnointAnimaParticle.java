package com.github.creoii.hallow.client.particle;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.particle.*;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.util.math.MathHelper;

@Environment(EnvType.CLIENT)
public class AnointAnimaParticle extends AscendingParticle {
    public AnointAnimaParticle(ClientWorld clientWorld, double d, double e, double f, SpriteProvider spriteProvider) {
        super(clientWorld, d, e, f, 1f, 0f, 1f, 0f, clientWorld.random.nextFloat(), 0f, 1f, spriteProvider, 1f, 30, .2f, false);
        scale(4f);
        collidesWithWorld = false;
        maxAge = 30;
    }

    @Override
    public ParticleTextureSheet getType() {
        return ParticleTextureSheet.PARTICLE_SHEET_OPAQUE;
    }

    @Override
    protected int getBrightness(float tint) {
        return 255;
    }

    @Override
    public void tick() {
        super.tick();
        alpha = 1f - ((float) age / (float) maxAge);
    }

    @Override
    public float getSize(float tickDelta) {
        return scale * MathHelper.clamp(((float) age + tickDelta) / ((float) maxAge / 5f), 0f, 1f);
    }

    @Environment(value=EnvType.CLIENT)
    public static class Factory implements ParticleFactory<DefaultParticleType> {
        private final SpriteProvider spriteProvider;

        public Factory(SpriteProvider spriteProvider) {
            this.spriteProvider = spriteProvider;
        }

        @Override
        public Particle createParticle(DefaultParticleType defaultParticleType, ClientWorld clientWorld, double d, double e, double f, double g, double h, double i) {
            AnointAnimaParticle anointAnimaParticle = new AnointAnimaParticle(clientWorld, d, e, f, spriteProvider);
            anointAnimaParticle.setSprite(spriteProvider);
            return anointAnimaParticle;
        }
    }
}
