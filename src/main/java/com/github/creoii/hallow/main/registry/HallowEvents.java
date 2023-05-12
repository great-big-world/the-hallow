package com.github.creoii.hallow.main.registry;

import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.minecraft.item.Equipment;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;

public final class HallowEvents {
    public static void register() {
        ItemTooltipCallback.EVENT.register((stack, context, lines) -> {
            if (stack.getItem() instanceof Equipment && stack.hasNbt() && stack.getNbt().contains("Anointed")) {
                lines.add(1, Text.translatable("tooltip.anointed").formatted(Formatting.DARK_PURPLE, Formatting.ITALIC));
            }
        });
    }
}
