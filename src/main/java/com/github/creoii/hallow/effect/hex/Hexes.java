package com.github.creoii.hallow.effect.hex;

import net.minecraft.entity.effect.StatusEffectCategory;

public final class Hexes {
   public static final Hex CONFUSED = new Hex(StatusEffectCategory.HARMFUL, 1) {};
   public static final Hex SICKENED = new Hex(StatusEffectCategory.HARMFUL, 1) {};
   public static final Hex EFFECT_INVERSION = new EffectInversionHex();
}
