package com.github.creoii.hallow.mixin.entity;

import com.github.creoii.hallow.main.registry.HallowStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
    @Shadow public abstract boolean hasStatusEffect(StatusEffect effect);
    @Shadow public float sidewaysSpeed;
    @Shadow public float forwardSpeed;

    @Inject(method = "tickMovement", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/LivingEntity;tickFallFlying()V"))
    private void hallow_applyConfusedHex(CallbackInfo ci) {
        if (hasStatusEffect(HallowStatusEffects.CONFUSED_HEX)) {
            sidewaysSpeed *= -1d;
            forwardSpeed *= -1d;
        }
    }

    @Inject(method = "setHealth", at = @At("HEAD"), cancellable = true)
    private void hallow_applySickenedHex(float health, CallbackInfo ci) {
        if (hasStatusEffect(HallowStatusEffects.SICKENED_HEX)) ci.cancel();
    }
}
