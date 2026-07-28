package com.projectdread.client.gui;

import com.projectdread.escalation.EscalationTracker;
import com.projectdread.system.RebootManager;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;

public class AdminControlScreen extends Screen {

    private static boolean horrorMechanicsEnabled = true;

    public AdminControlScreen() {
        super(Component.literal("PROJECT DREAD - ADMIN CONTROL PANEL"));
    }

    @Override
    protected void init() {
        super.init();

        int buttonWidth = 220;
        int buttonHeight = 20;
        int centerX = this.width / 2 - buttonWidth / 2;
        int startY = this.height / 2 - 80;

        this.addRenderableWidget(
            Button.builder(
                Component.literal("Horror Mechanics: " + (horrorMechanicsEnabled ? "§aENABLED" : "§cDISABLED")), 
                button -> {
                    horrorMechanicsEnabled = !horrorMechanicsEnabled;
                    button.setMessage(Component.literal("Horror Mechanics: " + (horrorMechanicsEnabled ? "§aENABLED" : "§cDISABLED")));
                }
            )
            .bounds(centerX, startY, buttonWidth, buttonHeight)
            .build()
        );

        this.addRenderableWidget(
            Button.builder(Component.literal("Override Escalation: §cDay 30 (Extreme)"), button -> {
                EscalationTracker.setForcedDayOverride(30);
                if (this.minecraft != null && this.minecraft.player != null) {
                    this.minecraft.player.sendSystemMessage(Component.literal("§e[Admin] Escalation set to Day 30."));
                }
            })
            .bounds(centerX, startY + 25, buttonWidth, buttonHeight)
            .build()
        );

        this.addRenderableWidget(
            Button.builder(Component.literal("Reset Escalation Override"), button -> {
                EscalationTracker.resetDayOverride();
                if (this.minecraft != null && this.minecraft.player != null) {
                    this.minecraft.player.sendSystemMessage(Component.literal("§e[Admin] Escalation reset to real world day count."));
                }
            })
            .bounds(centerX, startY + 50, buttonWidth, buttonHeight)
            .build()
        );

        this.addRenderableWidget(
            Button.builder(Component.literal("Trigger Fake OS Error HUD Popup"), button -> {
                GlitchHudOverlay.triggerFakeErrorAlert(
                    "ADMIN TEST EXCEPTION", 
                    "System integrity breach simulated via Admin Control Panel.", 
                    100
                );
                this.onClose();
            })
            .bounds(centerX, startY + 75, buttonWidth, buttonHeight)
            .build()
        );

        this.addRenderableWidget(
            Button.builder(Component.literal("Trigger Screen Static Glitch"), button -> {
                GlitchHudOverlay.triggerStaticGlitch(80);
                this.onClose();
            })
            .bounds(centerX, startY + 100, buttonWidth, buttonHeight)
            .build()
        );

        this.addRenderableWidget(
            Button.builder(Component.literal("§c§l[DANGER] TEST OS REBOOT (5s)"), button -> {
                if (this.minecraft != null && this.minecraft.getSingleplayerServer() != null) {
                    RebootManager.scheduleReboot(this.minecraft.getSingleplayerServer(), 5);
                    this.onClose();
                }
            })
            .bounds(centerX, startY + 125, buttonWidth, buttonHeight)
            .build()
        );

        this.addRenderableWidget(
            Button.builder(Component.literal("Close Panel"), button -> this.onClose())
            .bounds(centerX, startY + 155, buttonWidth, buttonHeight)
            .build()
        );
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(guiGraphics, mouseX, mouseY, partialTick);

        guiGraphics.drawCenteredString(
            this.font, 
            Component.literal("§4§lPROJECT DREAD - DEV / ADMIN CONTROL PANEL"), 
            this.width / 2, 
            this.height / 2 - 105, 
            0xFFFFFF
        );

        super.render(guiGraphics, mouseX, mouseY, partialTick);
    }

    public static boolean isHorrorMechanicsEnabled() {
        return horrorMechanicsEnabled;
    }
}
