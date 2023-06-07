package com.github.creoii.hallow.main.registry;

import com.github.creoii.hallow.client.particle.AnointAnimaParticle;
import com.github.creoii.hallow.main.TheHallow;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.fabricmc.fabric.api.particle.v1.FabricParticleTypes;
import net.minecraft.particle.DefaultParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class HallowParticleTypes {
    public static final DefaultParticleType ANOINT_SWORD = FabricParticleTypes.simple();
    public static final DefaultParticleType ANOINT_BOW = FabricParticleTypes.simple();
    public static final DefaultParticleType ANOINT_PICKAXE = FabricParticleTypes.simple();
    public static final DefaultParticleType ANOINT_SHOVEL = FabricParticleTypes.simple();
    public static final DefaultParticleType ANOINT_HOE = FabricParticleTypes.simple();
    public static final DefaultParticleType ANOINT_HELMET = FabricParticleTypes.simple();
    public static final DefaultParticleType ANOINT_CHESTPLATE = FabricParticleTypes.simple();
    public static final DefaultParticleType ANOINT_LEGGINGS = FabricParticleTypes.simple();
    public static final DefaultParticleType ANOINT_ELYTRA = FabricParticleTypes.simple();

    public static void register() {
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(TheHallow.NAMESPACE, "anoint_sword"), ANOINT_SWORD);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(TheHallow.NAMESPACE, "anoint_bow"), ANOINT_BOW);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(TheHallow.NAMESPACE, "anoint_pickaxe"), ANOINT_PICKAXE);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(TheHallow.NAMESPACE, "anoint_shovel"), ANOINT_SHOVEL);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(TheHallow.NAMESPACE, "anoint_hoe"), ANOINT_HOE);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(TheHallow.NAMESPACE, "anoint_helmet"), ANOINT_HELMET);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(TheHallow.NAMESPACE, "anoint_chestplate"), ANOINT_CHESTPLATE);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(TheHallow.NAMESPACE, "anoint_leggings"), ANOINT_LEGGINGS);
        Registry.register(Registries.PARTICLE_TYPE, new Identifier(TheHallow.NAMESPACE, "anoint_elytra"), ANOINT_ELYTRA);
    }

    @Environment(EnvType.CLIENT)
    public static void registerClient() {
        ParticleFactoryRegistry particleFactoryRegistry = ParticleFactoryRegistry.getInstance();
        particleFactoryRegistry.register(ANOINT_SWORD, AnointAnimaParticle.Factory::new);
        particleFactoryRegistry.register(ANOINT_BOW, AnointAnimaParticle.Factory::new);
        particleFactoryRegistry.register(ANOINT_PICKAXE, AnointAnimaParticle.Factory::new);
        particleFactoryRegistry.register(ANOINT_SHOVEL, AnointAnimaParticle.Factory::new);
        particleFactoryRegistry.register(ANOINT_HOE, AnointAnimaParticle.Factory::new);
        particleFactoryRegistry.register(ANOINT_HELMET, AnointAnimaParticle.Factory::new);
        particleFactoryRegistry.register(ANOINT_CHESTPLATE, AnointAnimaParticle.Factory::new);
        particleFactoryRegistry.register(ANOINT_LEGGINGS, AnointAnimaParticle.Factory::new);
        particleFactoryRegistry.register(ANOINT_ELYTRA, AnointAnimaParticle.Factory::new);
    }
}
