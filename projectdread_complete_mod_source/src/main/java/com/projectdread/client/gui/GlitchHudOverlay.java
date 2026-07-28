package com.projectdread.client.gui;

import com.projectdread.ProjectDread;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.RenderGuiEvent;

import java.util.Random;

@EventBusSubscriber(modid = ProjectDread.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class GlitchHudOverlay {

    private static final Random RANDOM = new Random();
    
    private static boolean activeErrorAlert = false;
    private static boolean activeStaticShader = false;
    private static String alertTitle = "CRITICAL SYSTEM ERROR";
    private static String alertMessage = "Exception in thread \"main\" java.lang.NullPointerException: Memory region breached.";
    private static int glitchTicksRemaining = 0;

    public static void triggerFakeErrorAlert(String title, String message, int durationTicks) {
        alertTitle = title;
        alertMessage = message;
        activeErrorAlert = true;
        glitchTicksRemaining = durationTicks;
    }

    public static void triggerStaticGlitch(int durationTicks) {
        activeStaticShader = true;
        glitchTicksRemaining = durationTicks;
    }

    public static void clearGlitches() {
        activeErrorAlert = false;
        activeStaticShader = false;
        glitchTicksRemaining = 0;
    }

    @SubscribeEvent
    public static void onRenderGuiPost(RenderGuiEvent.Post event) {
        if (glitchTicksRemaining > 0) {
            glitchTicksRemaining--;
            if (glitchTicksRemaining <= 0) {
                clearGlitches();
            }
        }

        GuiGraphics guiGraphics = event.getGuiGraphics();
        int width = event.getPartialTick().getGameTimeDeltaPartialTick() > 0 ? 
                Minecraft.getInstance().getWindow().getGuiScaledWidth() : event.getGuiGraphics().guiWidth();
        int height = Minecraft.getInstance().getWindow().getGuiScaledHeight();

        if (activeStaticShader) {
            renderScreenStatic(guiGraphics, width, height);
        }

        if (activeErrorAlert) {
            renderFakeErrorDialog(guiGraphics, width, height);
        }
    }

    private static void renderScreenStatic(GuiGraphics guiGraphics, int width, int height) {
        for (int i = 0; i < 80; i++) {
            int x = RANDOM.nextInt(width);
            int y = RANDOM.nextInt(height);
            int w = RANDOM.nextInt(60) + 10;
            int h = RANDOM.nextInt(4) + 1;
            int alpha = RANDOM.nextInt(150) + 50;
            int color = (alpha << 24) | (0xFF << 16) | (0xFF << 8) | 0xFF;
            
            guiGraphics.fill(x, y, x + w, y + h, color);
        }
    }

    private static void renderFakeErrorDialog(GuiGraphics guiGraphics, int screenWidth, int screenHeight) {
        int dialogWidth = 320;
        int dialogHeight = 130;
        int x = (screenWidth - dialogWidth) / 2;
        int y = (screenHeight - dialogHeight) / 2;

        guiGraphics.fill(x - 2, y - 2, x + dialogWidth + 2, y + dialogHeight + 2, 0xFF000000);
        guiGraphics.fill(x, y, x + dialogWidth, y + dialogHeight, 0xFFC0C0C0);

        guiGraphics.fill(x + 2, y + 2, x + dialogWidth - 2, y + 22, 0xFF800000);
        guiGraphics.drawString(Minecraft.getInstance().font, alertTitle, x + 8, y + 7, 0xFFFFFFFF, false);

        guiGraphics.fill(x + dialogWidth - 18, y + 4, x + dialogWidth - 4, y + 18, 0xFFC0C0C0);
        guiGraphics.drawString(Minecraft.getInstance().font, "X", x + dialogWidth - 14, y + 6, 0xFF000000, false);

        guiGraphics.fill(x + 15, y + 35, x + 45, y + 65, 0xFFFF0000);
        guiGraphics.drawString(Minecraft.getInstance().font, "!", x + 27, y + 43, 0xFFFFFFFF, false);

        guiGraphics.drawWordWrap(
                Minecraft.getInstance().font, 
                Component.literal(alertMessage), 
                x + 55, 
                y + 35, 
                dialogWidth - 65, 
                0xFF000000
        );

        int btnX = x + (dialogWidth / 2) - 30;
        int btnY = y + dialogHeight - 28;
        guiGraphics.fill(btnX, btnY, btnX + 60, btnY + 20, 0xFF808080);
        guiGraphics.fill(btnX + 1, btnY + 1, btnX + 59, btnY + 19, 0xFFE0E0E0);
        guiGraphics.drawCenteredString(Minecraft.getInstance().font, "OK", btnX + 30, btnY + 6, 0xFF000000);
    }
}
