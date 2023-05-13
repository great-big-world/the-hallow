package com.github.creoii.hallow.main.registry;

import com.github.creoii.hallow.effect.HexEffect;
import com.github.creoii.hallow.effect.hex.Hexes;
import com.github.creoii.hallow.main.TheHallow;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class HallowStatusEffects {
    public static final StatusEffect CONFUSED_HEX = new HexEffect(Hexes.CONFUSED);
    public static final StatusEffect SICKENED_HEX = new HexEffect(Hexes.SICKENED);
    public static final StatusEffect CLIMBING_HEX = new HexEffect(Hexes.CLIMBING);
    public static final StatusEffect SHRUNK_HEX = new HexEffect(Hexes.SHRUNK);
    public static final StatusEffect INFLATED_HEX = new HexEffect(Hexes.INFLATED);
    public static final StatusEffect EFFECT_INVERSION_HEX = new HexEffect(Hexes.EFFECT_INVERSION);

    public static void register() {
        Registry.register(Registries.STATUS_EFFECT, new Identifier(TheHallow.NAMESPACE, "confused_hex"), CONFUSED_HEX);
        Registry.register(Registries.STATUS_EFFECT, new Identifier(TheHallow.NAMESPACE, "sickened_hex"), SICKENED_HEX);
        Registry.register(Registries.STATUS_EFFECT, new Identifier(TheHallow.NAMESPACE, "climbing_hex"), CLIMBING_HEX);
        Registry.register(Registries.STATUS_EFFECT, new Identifier(TheHallow.NAMESPACE, "shrunk_hex"), SHRUNK_HEX);
        Registry.register(Registries.STATUS_EFFECT, new Identifier(TheHallow.NAMESPACE, "inflated_hex"), INFLATED_HEX);
        Registry.register(Registries.STATUS_EFFECT, new Identifier(TheHallow.NAMESPACE, "effect_inversion_hex"), EFFECT_INVERSION_HEX);
    }
}
