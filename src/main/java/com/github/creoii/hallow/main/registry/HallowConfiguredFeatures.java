package com.github.creoii.hallow.main.registry;

import com.github.creoii.hallow.main.TheHallow;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;
import net.minecraft.world.gen.feature.ConfiguredFeature;

public final class HallowConfiguredFeatures {
    public static RegistryKey<ConfiguredFeature<?, ?>> PATCH_DEADROOT;

    public static void register() {
        PATCH_DEADROOT = RegistryKey.of(RegistryKeys.CONFIGURED_FEATURE, new Identifier(TheHallow.NAMESPACE, "patch_deadroot"));
    }
}
