package com.github.creoii.hallow.effect.hex;

import com.google.common.collect.ImmutableMap;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;

import java.util.Map;

public class EffectInversionHex extends Hex {
    public static final Map<StatusEffect, StatusEffect> INVERSION_MAP = new ImmutableMap.Builder<StatusEffect, StatusEffect>()
            .put(StatusEffects.SPEED, StatusEffects.SLOWNESS)
            .put(StatusEffects.SLOWNESS, StatusEffects.SPEED)
            .put(StatusEffects.STRENGTH, StatusEffects.WEAKNESS)
            .put(StatusEffects.WEAKNESS, StatusEffects.STRENGTH)
            .put(StatusEffects.HASTE, StatusEffects.MINING_FATIGUE)
            .put(StatusEffects.MINING_FATIGUE, StatusEffects.HASTE)
            .put(StatusEffects.ABSORPTION, StatusEffects.HUNGER)
            .put(StatusEffects.HUNGER, StatusEffects.ABSORPTION)
            .put(StatusEffects.NIGHT_VISION, StatusEffects.BLINDNESS)
            .put(StatusEffects.BLINDNESS, StatusEffects.NIGHT_VISION)
            .put(StatusEffects.REGENERATION, StatusEffects.POISON)
            .put(StatusEffects.POISON, StatusEffects.REGENERATION)
            .put(StatusEffects.INSTANT_HEALTH, StatusEffects.INSTANT_DAMAGE)
            .put(StatusEffects.INSTANT_DAMAGE, StatusEffects.INSTANT_HEALTH)
            .put(StatusEffects.LUCK, StatusEffects.UNLUCK)
            .put(StatusEffects.UNLUCK, StatusEffects.LUCK)
            .build();

    public EffectInversionHex() {
        super(StatusEffectCategory.NEUTRAL, 1);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        Map<StatusEffect, StatusEffectInstance> activeEffects = entity.getActiveStatusEffects();
        activeEffects.forEach((effect, instance) -> {
            if (INVERSION_MAP.containsKey(effect)) {
                entity.removeStatusEffect(effect);
                entity.addStatusEffect(new StatusEffectInstance(INVERSION_MAP.get(effect), instance.getDuration(), instance.getAmplifier(), instance.isAmbient(), instance.shouldShowParticles(), instance.shouldShowIcon()));
            }
        });
    }
}
