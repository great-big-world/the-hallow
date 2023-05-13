package com.github.creoii.hallow.main.registry;

import com.github.creoii.hallow.effect.hex.Hex;
import com.github.creoii.hallow.main.TheHallow;
import net.minecraft.registry.Registry;
import net.minecraft.registry.RegistryKey;
import net.minecraft.util.Identifier;

public final class HallowRegistryKeys {
    public static final RegistryKey<Registry<Hex>> HEX = RegistryKey.ofRegistry(new Identifier(TheHallow.NAMESPACE, "hex"));
}
