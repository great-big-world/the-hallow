package com.github.creoii.hallow.mixin.entity;

import com.github.creoii.hallow.main.registry.HallowStatusEffects;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Attackable;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Optional;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin extends Entity implements Attackable {
    @Shadow public abstract boolean hasStatusEffect(StatusEffect effect);
    @Shadow public float sidewaysSpeed;
    @Shadow public float forwardSpeed;
    @Shadow private Optional<BlockPos> climbingPos;

    public LivingEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

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

    @Inject(method = "isClimbing", at = @At(value = "INVOKE", target = "Lnet/minecraft/block/BlockState;isIn(Lnet/minecraft/registry/tag/TagKey;)Z"), cancellable = true, locals = LocalCapture.CAPTURE_FAILSOFT)
    private void hallow_applyClimbingHex(CallbackInfoReturnable<Boolean> cir, BlockPos blockPos, BlockState blockState) {
        if (hasStatusEffect(HallowStatusEffects.CLIMBING_HEX) && horizontalCollision) {
            climbingPos = Optional.of(blockPos);
            cir.setReturnValue(true);
        }
    }
}
