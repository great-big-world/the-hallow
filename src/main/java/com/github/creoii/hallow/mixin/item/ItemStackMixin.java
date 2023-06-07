package com.github.creoii.hallow.mixin.item;

import com.github.creoii.hallow.recipe.AnointingRecipe;
import com.google.common.collect.Multimap;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Mixin(ItemStack.class)
public class ItemStackMixin {
    private boolean anointed = false;

    @Inject(method = "getTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/attribute/EntityAttributeModifier;getValue()D"), locals = LocalCapture.CAPTURE_FAILSOFT)
    private void hallow_isAttributeAnointed(@Nullable PlayerEntity player, TooltipContext context, CallbackInfoReturnable<List<Text>> cir, List<Text> list, MutableText mutableText, int i, EquipmentSlot[] var6, int var7, int var8, EquipmentSlot equipmentSlot, Multimap multimap, Iterator var11, Map.Entry entry, EntityAttributeModifier entityAttributeModifier) {
        anointed = entityAttributeModifier.getName().equals(AnointingRecipe.ANOINTMENT_BONUS_KEY);
    }

    private MutableText anointed(MutableText instance, Formatting formatting) {
        if (anointed) {
            return instance.formatted(Formatting.DARK_PURPLE);
        }
        return instance.formatted(formatting);
    }

    @Redirect(method = "getTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/text/MutableText;formatted(Lnet/minecraft/util/Formatting;)Lnet/minecraft/text/MutableText;", ordinal = 5))
    private MutableText hallow_purpleAnointTooltip5(MutableText instance, Formatting formatting) {
        return anointed(instance, formatting);
    }

    @Redirect(method = "getTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/text/MutableText;formatted(Lnet/minecraft/util/Formatting;)Lnet/minecraft/text/MutableText;", ordinal = 6))
    private MutableText hallow_purpleAnointTooltip6(MutableText instance, Formatting formatting) {
        return anointed(instance, formatting);
    }

    @Redirect(method = "getTooltip", at = @At(value = "INVOKE", target = "Lnet/minecraft/text/MutableText;formatted(Lnet/minecraft/util/Formatting;)Lnet/minecraft/text/MutableText;", ordinal = 7))
    private MutableText hallow_purpleAnointTooltip7(MutableText instance, Formatting formatting) {
        return anointed(instance, formatting);
    }
}
