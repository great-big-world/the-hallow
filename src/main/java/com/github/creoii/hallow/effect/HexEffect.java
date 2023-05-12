package com.github.creoii.hallow.effect;

import com.github.creoii.hallow.effect.hex.Hex;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import org.jetbrains.annotations.Nullable;

public class HexEffect extends StatusEffect {
    private final Hex hex;
    private final boolean instant;

    public HexEffect(Hex hex) {
        super(hex.getCategory(), hex.getColor());
        this.hex = hex;
        this.instant = hex.isInstant();
    }

    @Override
    public boolean isInstant() {
        return instant;
    }

    @Override
    public void applyInstantEffect(@Nullable Entity source, @Nullable Entity attacker, LivingEntity target, int amplifier, double proximity) {
        hex.applyInstantEffect(source, attacker, target, amplifier, proximity);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        hex.onApplied(entity, attributes, amplifier);
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        hex.onRemoved(entity, attributes, amplifier);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier) {
        return hex.canApplyUpdateEffect(duration, amplifier);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier) {
        hex.applyUpdateEffect(entity, amplifier);
    }

    public Hex getHex() {
        return hex;
    }

    @Override
    public String getTranslationKey() {
        return "effect.hallow.hexed";
    }
}
