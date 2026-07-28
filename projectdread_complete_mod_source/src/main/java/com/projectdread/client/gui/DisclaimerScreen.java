package com.projectdread.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class DisclaimerScreen extends Screen {

    private final Screen parentScreen;

    public DisclaimerScreen(Screen parentScreen) {
        super(Component.literal("PROJECT DREAD - WARNING"));
        this.parentScreen = parentScreen;
    }

    @Override
    protected void init() {
        super.init();

        int buttonWidth = 200;
        int buttonHeight = 20;
        int centerX = this.width / 2 - buttonWidth / 2;
        int centerY = this.height / 2 + 60;

        this.addRenderableWidget(
            Button.builder(Component.literal("I ACCEPT THE RISKS"), button -> {
                saveDisclaimerAccepted();
                if (this.minecraft != null) {
                    this.minecraft.setScreen(this.parentScreen);
                }
            })
            .bounds(centerX, centerY, buttonWidth, buttonHeight)
            .build()
        );
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);

        guiGraphics.drawCenteredString(
            this.font, 
            Component.literal("§c§lCRITICAL SAFETY WARNING"), 
            this.width / 2, 
            this.height / 2 - 80, 
            0xFF5555
        );

        Component warningText = Component.literal(
            "§fProject Dread contains real system disruptions including §cforced OS reboots§f, " +
            "intense visual screen glitches, fake system error alerts, and loud audio jumpscares.\n\n" +
            "§e§lPLEASE SAVE ALL OPEN WORK ON YOUR COMPUTER BEFORE PLAYING.\n\n" +
            "§7By clicking below, you acknowledge that you understand the mechanics and accept all risks associated with gameplay."
        );

        int textMargin = 40;
        int maxTextWidth = this.width - (textMargin * 2);
        guiGraphics.drawWordWrap(
            this.font, 
            warningText, 
            textMargin, 
            this.height / 2 - 50, 
            maxTextWidth, 
            0xFFFFFF
        );

        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    @Override
    public boolean shouldCloseOnEsc() {
        return false;
    }

    private static File getConfigFile() {
        return new File(Minecraft.getInstance().gameDirectory, "config/projectdread_disclaimer.flag");
    }

    public static boolean hasAcceptedDisclaimer() {
        return getConfigFile().exists();
    }

    public static void saveDisclaimerAccepted() {
        try {
            File configFile = getConfigFile();
            if (configFile.getParentFile() != null && !configFile.getParentFile().exists()) {
                configFile.getParentFile().mkdirs();
            }
            Files.writeString(configFile.toPath(), "DISCLAIMER_ACCEPTED=TRUE");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
