package com.leo.soulmancy.client.screen;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.menu.SoulManipulatorMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SoulManipulatorScreen extends AbstractContainerScreen<SoulManipulatorMenu> {
    public static ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(Soulmancy.MODID, "textures/gui/soul_manipulator.png");

    public SoulManipulatorScreen(SoulManipulatorMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
        super(pMenu, pPlayerInventory, pTitle);
    }

    @Override
    protected void init() {
        super.init();

        inventoryLabelY = 10000;
        titleLabelY = 10000;
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float pPartialTick, int pMouseX, int pMouseY) {
        int x = (width - imageWidth) / 2;
        int y = (height - imageHeight) / 2;

        guiGraphics.blit(
            BACKGROUND,
            x,
            y,
            0,
            0,
            176,
            178,
            256,
            256
        );

        if(menu.getItemHandler().getStackInSlot(0).isEmpty()) guiGraphics.blit(
            BACKGROUND,
            leftPos + 44,
            topPos + 37 ,
            44,
            178,
            18,
            18,
            256,
            256
        );

        if(menu.getItemHandler().getStackInSlot(1 ).isEmpty()) guiGraphics.blit(
            BACKGROUND,
            leftPos + 116,
            topPos + 37 ,
            116,
            178,
            18,
            18,
            256,
            256
        );

        int soul = menu.getData().get(1), maxSoul = menu.getData().get(2);

        int scaledSoul = soul > 0 && maxSoul > 0 ? (soul * 71 / maxSoul): 0;

        guiGraphics.blit(
            BACKGROUND,
            leftPos + 160,
            topPos + 80 - scaledSoul,
             176,
            9,
             7,
            scaledSoul,
            256,
            256
        );

        int progress = menu.getData().get(0), maxProgress = menu.getData().get(3);
        if(maxProgress <= 0) return;

        int scaledProgress = progress * 32 / maxProgress;

        guiGraphics.blit(
            BACKGROUND,
            leftPos + 72,
            topPos + 40,
             72,
            178,
            scaledProgress,
            10,
            256,
            256
        );
    }


    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        super.render(guiGraphics, mouseX, mouseY, partialTick);
        renderTooltip(guiGraphics, mouseX, mouseY);
    }

    @Override
    protected void renderTooltip(GuiGraphics guiGraphics, int x, int y) {
        super.renderTooltip(guiGraphics, x, y);

        if(!isMouseHovering(leftPos + 159,  topPos + 8, 9, 73, x, y)) return;

        int soul = menu.getData().get(1), maxSoul = menu.getData().get(2);

        guiGraphics.renderTooltip(
            font,
            Component.literal(soul + "/" + maxSoul),
            x,
            y
        );
    }

    public static boolean isMouseHovering(int x, int y, int width, int height, double mouseX, double mouseY) {
        return mouseX >= x && mouseY >= y && mouseX < x + width && mouseY < y + height;
    }
}
