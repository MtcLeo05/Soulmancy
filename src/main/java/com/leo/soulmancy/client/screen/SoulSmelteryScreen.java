package com.leo.soulmancy.client.screen;

import com.leo.soulmancy.Soulmancy;
import com.leo.soulmancy.menu.SoulSmelteryMenu;
import com.leo.soulmancy.util.Utils;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class SoulSmelteryScreen extends AbstractContainerScreen<SoulSmelteryMenu> {
    public static ResourceLocation BACKGROUND = ResourceLocation.fromNamespaceAndPath(Soulmancy.MODID, "textures/gui/soul_smeltery.png");

    public SoulSmelteryScreen(SoulSmelteryMenu pMenu, Inventory pPlayerInventory, Component pTitle) {
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
            166,
            256,
            256
        );

        int soul = menu.getData().get(1), maxSoul = menu.getData().get(2);
        int scaledSoul = soul > 0 && maxSoul > 0 ? (soul * 46 / maxSoul): 0;

        guiGraphics.blit(
            BACKGROUND,
            leftPos + 160,
            topPos + 55 - scaledSoul,
            176,
            9,
            7,
            scaledSoul,
            256,
            256
        );

        int progress = menu.getData().get(0), maxProgress = menu.getData().get(3);
        if(maxProgress > 0) {
            int scaledProgress = progress * 22 / maxProgress;
            guiGraphics.blit(
                BACKGROUND,
                leftPos + 80,
                topPos + 30,
                80,
                166,
                scaledProgress,
                15,
                256,
                256
            );
        }

        int burnTime = menu.getData().get(4);
        int scaledFuel = (burnTime * 13) / menu.getData().get(3);

        guiGraphics.blit(
            BACKGROUND,
            leftPos + 157,
            topPos + 70 - scaledFuel,
            157,
            181 - scaledFuel,
            14,
            scaledFuel,
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

        if(!Utils.isMouseHovering(leftPos + 159,  topPos + 8, 9, 48, x, y)) return;

        int soul = menu.getData().get(1), maxSoul = menu.getData().get(2);

        guiGraphics.renderTooltip(
            font,
            Component.literal(soul + "/" + maxSoul),
            x,
            y
        );
    }
}
