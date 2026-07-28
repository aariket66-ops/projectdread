package com.projectdread.escalation;

import com.projectdread.ProjectDread;
import com.projectdread.system.RebootManager;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.tick.ServerTickEvent;

import java.util.Random;

@EventBusSubscriber(modid = ProjectDread.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class EscalationTracker {

    private static final Random RANDOM = new Random();
    private static int forcedDayOverride = -1;
    private static int tickCounter = 0;

    public enum HorrorPhase {
        PSYCHOLOGICAL(1, 5),
        DISRUPTION(6, 15),
        EXTREME(16, 30);

        private final int startDay;
        private final int endDay;

        HorrorPhase(int startDay, int endDay) {
            this.startDay = startDay;
            this.endDay = endDay;
        }

        public static HorrorPhase getForDay(int day) {
            if (day <= 5) return PSYCHOLOGICAL;
            if (day <= 15) return DISRUPTION;
            return EXTREME;
        }
    }

    public static int getCurrentDay(ServerLevel level) {
        if (forcedDayOverride >= 0) {
            return forcedDayOverride;
        }
        return (int) (level.getDayTime() / 24000L) + 1;
    }

    public static void setForcedDayOverride(int day) {
        forcedDayOverride = day;
        ProjectDread.LOGGER.info("[Project Dread] Escalation day manually overridden to Day {}", day);
    }

    public static void resetDayOverride() {
        forcedDayOverride = -1;
    }

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        tickCounter++;
        
        if (tickCounter % 200 != 0) return;

        ServerLevel overworld = event.getServer().overworld();
        if (overworld == null || overworld.players().isEmpty()) return;

        int currentDay = getCurrentDay(overworld);
        HorrorPhase phase = HorrorPhase.getForDay(currentDay);

        for (ServerPlayer player : overworld.players()) {
            evaluatePlayerHorrorEvents(player, overworld, currentDay, phase);
        }
    }

    private static void evaluatePlayerHorrorEvents(ServerPlayer player, ServerLevel level, int day, HorrorPhase phase) {
        double roll = RANDOM.nextDouble();

        switch (phase) {
            case PSYCHOLOGICAL -> {
                if (roll < 0.15) {
                    level.playSound(null, player.blockPosition(), SoundEvents.AMBIENT_CAVE.value(), 
                            SoundSource.AMBIENT, 1.0f, 0.5f);
                }
            }
            case DISRUPTION -> {
                if (roll < 0.25) {
                    level.playSound(null, player.blockPosition(), SoundEvents.ENDERMAN_SCREAM, 
                            SoundSource.HOSTILE, 0.8f, 0.3f);
                }
            }
            case EXTREME -> {
                if (roll < 0.05 && !RebootManager.isRebootPending()) {
                    RebootManager.scheduleReboot(player.getServer(), 5);
                } else if (roll < 0.15) {
                    player.sendSystemMessage(Component.literal("§c[FATAL ERROR] Memory Allocation Corruption. Closing Client..."));
                    Minecraft.getInstance().execute(() -> Minecraft.getInstance().stop());
                }
            }
        }
    }
}
