package com.github.creoii.hallow.effect.hex;

import com.github.creoii.creolib.api.registry.CEntityAttributes;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.util.math.MathHelper;

public class SizedHex extends Hex {
    private EntityAttributeModifier modifier;

    public SizedHex() {
        super(StatusEffectCategory.NEUTRAL, 1);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        EntityAttributeInstance instance = entity.getAttributeInstance(CEntityAttributes.GENERIC_SCALE);
        double value = (this == Hexes.SHRUNK ? -.1d : .1d) * (amplifier + 1);
        modifier = new EntityAttributeModifier("Size Hex modifier", MathHelper.clamp(value, -1d, 16d), EntityAttributeModifier.Operation.ADDITION);
        if (instance != null)
            instance.addTemporaryModifier(modifier);
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier) {
        EntityAttributeInstance instance = entity.getAttributeInstance(CEntityAttributes.GENERIC_SCALE);
        if (instance != null && modifier != null) {
            if (this == Hexes.SHRUNK)
                instance.removeModifier(modifier);
            else if (this == Hexes.INFLATED)
                instance.removeModifier(modifier);
        }
    }
}
