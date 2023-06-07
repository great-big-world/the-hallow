package com.github.creoii.hallow.main.registry;

import com.github.creoii.creolib.api.util.fog.FogModifier;
import com.github.creoii.hallow.main.TheHallow;
import net.minecraft.client.render.FogShape;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.biome.Biome;

public final class HallowBiomes {
    public static final RegistryKey<Biome> ASPHODEL_FIELDS = RegistryKey.of(RegistryKeys.BIOME, new Identifier(TheHallow.NAMESPACE, "asphodel_fields"));
    public static final RegistryKey<Biome> EBONY_WOODS = RegistryKey.of(RegistryKeys.BIOME, new Identifier(TheHallow.NAMESPACE, "ebony_woods"));
    public static final RegistryKey<Biome> HALLOWED_PEAKS = RegistryKey.of(RegistryKeys.BIOME, new Identifier(TheHallow.NAMESPACE, "hallowed_peaks"));

    public static void register() {
        FogModifier.register(FogModifier.create(
                fogFunction -> fogFunction.biomeEntry() != null && fogFunction.biomeEntry().matchesKey(EBONY_WOODS),
                fogFunction -> 24f,
                fogFunction -> 64f,
                .008f,
                FogShape.SPHERE
        ));
        FogModifier.register(FogModifier.create(
                fogFunction -> fogFunction.biomeEntry() != null && (fogFunction.biomeEntry().matchesKey(ASPHODEL_FIELDS) || fogFunction.biomeEntry().matchesKey(HALLOWED_PEAKS)),
                fogFunction -> 32f,
                fogFunction -> 96f,
                .02f,
                FogShape.SPHERE
        ));
    }
}
