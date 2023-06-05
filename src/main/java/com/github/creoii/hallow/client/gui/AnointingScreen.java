package com.github.creoii.hallow.client.gui;

import com.github.creoii.hallow.main.TheHallow;
import com.github.creoii.hallow.screen.AnointingScreenHandler;
import com.mojang.blaze3d.systems.RenderSystem;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.render.GameRenderer;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerListener;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public class AnointingScreen extends HandledScreen<AnointingScreenHandler> implements ScreenHandlerListener {
    private static final Identifier TEXTURE = new Identifier(TheHallow.NAMESPACE,"textures/gui/container/anointing.png");

    public AnointingScreen(AnointingScreenHandler handler, PlayerInventory playerInventory, Text title) {
        super(handler, playerInventory, title);
        titleX = 70;
        titleY = 10;
    }

    protected void init() {
        super.init();
        handler.addListener(this);
    }

    public void removed() {
        super.removed();
        handler.removeListener(this);
    }

    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        renderBackground(context);
        super.render(context, mouseX, mouseY, delta);
        RenderSystem.disableBlend();
        drawMouseoverTooltip(context, mouseX, mouseY);
    }

    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        RenderSystem.setShader(GameRenderer::getPositionTexProgram);
        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.setShaderTexture(0, TEXTURE);
        int i = (width - backgroundWidth) / 2;
        int j = (height - backgroundHeight) / 2;
        context.drawTexture(TEXTURE, i, j, 0, 0, backgroundWidth, backgroundHeight);
        context.drawTexture(TEXTURE, i + 59, j + 20, 0, backgroundHeight + (handler.getSlot(0).hasStack() ? 0 : 16), 110, 16);
        if (handler.getSlot(0).hasStack() || (handler.getSlot(1).hasStack()) && !handler.getSlot(2).hasStack()) {
            context.drawTexture(TEXTURE, i + 99, j + 28, backgroundWidth, 0, 28, 21);
        }
    }

    public void onPropertyUpdate(ScreenHandler handler, int property, int value) {}

    public void onSlotUpdate(ScreenHandler handler, int slotId, ItemStack stack) {}

    protected void drawForeground(DrawContext context, int x, int y) {
        RenderSystem.disableBlend();
        super.drawForeground(context, x, y);
    }
}