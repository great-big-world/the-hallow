package com.github.creoii.hallow.mixin.client;

import com.github.creoii.hallow.main.registry.HallowStatusEffects;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.Mouse;
import net.minecraft.client.option.GameOptions;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Mouse.class)
public class MouseMixin {
    @Shadow @Final private MinecraftClient client;

    @Redirect(method = "updateMouse", at = @At(value = "FIELD", target = "Lnet/minecraft/client/option/GameOptions;smoothCameraEnabled:Z"))
    private boolean hallow_applySmoothCameraHex(GameOptions instance) {
        if (client.player != null && client.player.hasStatusEffect(HallowStatusEffects.SMOOTH_CAMERA_HEX)) {
            return true;
        }
        return instance.smoothCameraEnabled;
    }
}
