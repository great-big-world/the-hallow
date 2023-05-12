package com.github.creoii.hallow.effect.hex;

import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffectCategory;
import org.jetbrains.annotations.Nullable;

public abstract class Hex {
    private final StatusEffectCategory category;
    private final int color;
    private final boolean instant;

    public Hex(StatusEffectCategory category, int color, boolean instant) {
        this.category = category;
        this.color = color;
        this.instant = instant;
    }

    public Hex(StatusEffectCategory category, int color) {
        this.category = category;
        this.color = color;
        this.instant = false;
    }

    public StatusEffectCategory getCategory() {
        return category;
    }

    public int getColor() {
        return color;
    }

    public boolean isInstant() {
        return instant;
    }

    public void applyInstantEffect(@Nullable Entity source, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity) {
    }

    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
    }

    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
    }

    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return true;
    }

    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
    }
}
