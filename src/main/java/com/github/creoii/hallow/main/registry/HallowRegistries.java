package com.github.creoii.hallow.main.registry;

import com.github.creoii.hallow.effect.hex.Hex;
import com.github.creoii.hallow.main.TheHallow;
import net.fabricmc.fabric.api.event.registry.FabricRegistryBuilder;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class HallowRegistries {
    public static final Registry<Hex> HEX = FabricRegistryBuilder.createDefaulted(HallowRegistryKeys.HEX, new Identifier(TheHallow.NAMESPACE, "nothing")).buildAndRegister();
}
