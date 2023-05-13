package com.github.creoii.hallow.effect.hex;

import com.github.creoii.hallow.main.TheHallow;
import com.github.creoii.hallow.main.registry.HallowRegistries;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public final class Hexes {
   public static final Hex NOTHING = new Hex(StatusEffectCategory.NEUTRAL, 1) {};
   public static final Hex CONFUSED = new Hex(StatusEffectCategory.HARMFUL, 1) {};
   public static final Hex SICKENED = new Hex(StatusEffectCategory.HARMFUL, 1) {};
   public static final Hex CLIMBING = new Hex(StatusEffectCategory.BENEFICIAL, 1) {};
   public static final Hex SHRUNK = new SizedHex();
   public static final Hex INFLATED = new SizedHex();
   public static final Hex EFFECT_INVERSION = new EffectInversionHex();

   public static void register() {
      register(new Identifier(TheHallow.NAMESPACE, "nothing"), NOTHING);
      register(new Identifier(TheHallow.NAMESPACE, "confused"), CONFUSED);
      register(new Identifier(TheHallow.NAMESPACE, "sickened"), SICKENED);
      register(new Identifier(TheHallow.NAMESPACE, "climbing"), CLIMBING);
      register(new Identifier(TheHallow.NAMESPACE, "shrunk"), SHRUNK);
      register(new Identifier(TheHallow.NAMESPACE, "inflated"), INFLATED);
      register(new Identifier(TheHallow.NAMESPACE, "effect_inversion"), EFFECT_INVERSION);
   }

   public static void register(Identifier id, Hex hex) {
      Registry.register(HallowRegistries.HEX, id, hex);
   }
}
