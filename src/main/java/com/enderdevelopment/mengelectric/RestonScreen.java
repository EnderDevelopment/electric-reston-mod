package com.enderdevelopment.mengelectric;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class RestonScreen extends HandledScreen<RestonScreenHandler> {
    private static final Identifier TEXTURE = Identifier.of("minecraft", "textures/gui/container/generic_54.png");
    private static final int BG_WIDTH = 176;
    private static final int BG_HEIGHT = 166;

    public RestonScreen(RestonScreenHandler handler, PlayerInventory inventory, Text title) {
        super(handler, inventory, title);
        this.backgroundWidth = BG_WIDTH;
        this.backgroundHeight = BG_HEIGHT;
    }

    @Override
    protected void drawBackground(DrawContext context, float delta, int mouseX, int mouseY) {
        int x = (this.width - this.backgroundWidth) / 2;
        int y = (this.height - this.backgroundHeight) / 2;
        context.drawTexture(TEXTURE, x, y, 0, 0, this.backgroundWidth, this.backgroundHeight);

        int energy = Math.max(0, this.handler.getEnergy());
        int capacity = this.handler.getCapacity();
        int barHeight = (int) (48 * Math.min(1.0F, (float) energy / capacity));

        // Energy bar background.
        context.fill(x + 26, y + 24, x + 34, y + 72, 0xFF333333);
        // Energy bar fill (green when full, otherwise yellow).
        int color = energy >= capacity ? 0xFF00E000 : 0xFFE0E000;
        if (barHeight > 0) {
            context.fill(x + 26, y + 72 - barHeight, x + 34, y + 72, color);
        }
    }

    @Override
    protected void drawForeground(DrawContext context, int mouseX, int mouseY) {
        context.drawText(this.textRenderer, this.title, this.titleX, this.titleY, 0x404040, false);
        context.drawText(this.textRenderer,
            Text.literal("Energy: " + Math.max(0, this.handler.getEnergy()) + " / " + this.handler.getCapacity() + " FE"),
            30, 78, 0x404040, false);
    }
}
