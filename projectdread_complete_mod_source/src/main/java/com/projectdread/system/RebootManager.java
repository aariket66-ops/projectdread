package com.projectdread.system;

import com.projectdread.ProjectDread;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.io.IOException;
import java.util.Locale;

@EventBusSubscriber(modid = ProjectDread.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class RebootManager {

    private static boolean rebootPending = false;
    private static int countdownTicks = -1;
    private static MinecraftServer currentServer = null;

    public static void scheduleReboot(MinecraftServer server, int countdownSeconds) {
        if (rebootPending) return;

        rebootPending = true;
        currentServer = server;
        countdownTicks = countdownSeconds * 20;

        ProjectDread.LOGGER.warn("[Project Dread] OS Reboot Sequence initiated! Countdown: {}s", countdownSeconds);
    }

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        if (!rebootPending || currentServer == null) {
            return;
        }

        if (countdownTicks % 20 == 0 && countdownTicks > 0) {
            int secondsRemaining = countdownTicks / 20;

            Component warningText = Component.literal(
                "§c§l[SYSTEM CRITICAL] SYSTEM REBOOT IN " + secondsRemaining + " SECONDS! SAVE WORK!"
            );

            for (ServerPlayer player : currentServer.getPlayerList().getPlayers()) {
                player.sendSystemMessage(warningText, true);
            }
        }

        countdownTicks--;

        if (countdownTicks <= 0) {
            rebootPending = false;
            executeSafeReboot(currentServer);
        }
    }

    private static void executeSafeReboot(MinecraftServer server) {
        ProjectDread.LOGGER.error("[Project Dread] EXECUTING SAFE OS REBOOT SEQUENCE...");

        try {
            ProjectDread.LOGGER.info("[Project Dread] Flushing world chunk data to disk...");
            server.saveAllChunks(true, true, true);

            ProjectDread.LOGGER.info("[Project Dread] Halting Minecraft server instance...");
            server.halt(false);

        } catch (Exception e) {
            ProjectDread.LOGGER.error("[Project Dread] Failed to cleanly stop server before rebooting!", e);
        }

        executeNativeOsReboot();
    }

    private static void executeNativeOsReboot() {
        String os = System.getProperty("os.name").toLowerCase(Locale.ROOT);
        ProcessBuilder processBuilder;

        try {
            if (os.contains("win")) {
                processBuilder = new ProcessBuilder("cmd.exe", "/c", "shutdown /r /f /t 5 /c \"Project Dread System Event\"");
            } else if (os.contains("mac") || os.contains("nix") || os.contains("nux")) {
                processBuilder = new ProcessBuilder("shutdown", "-r", "now");
            } else {
                ProjectDread.LOGGER.error("[Project Dread] Unsupported OS for native reboot: {}", os);
                return;
            }

            ProjectDread.LOGGER.warn("[Project Dread] Invoking native system process: {}", processBuilder.command());
            processBuilder.start();

        } catch (IOException e) {
            ProjectDread.LOGGER.error("[Project Dread] Failed to execute OS reboot command!", e);
        }
    }

    public static boolean isRebootPending() {
        return rebootPending;
    }
}
