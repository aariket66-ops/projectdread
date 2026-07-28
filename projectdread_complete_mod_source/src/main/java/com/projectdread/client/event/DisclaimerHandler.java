package com.projectdread.client.event;

import com.projectdread.ProjectDread;
import com.projectdread.client.gui.DisclaimerScreen;
import net.minecraft.client.gui.screens.TitleScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.ScreenEvent;

@EventBusSubscriber(modid = ProjectDread.MOD_ID, bus = EventBusSubscriber.Bus.GAME, value = Dist.CLIENT)
public class DisclaimerHandler {

    @SubscribeEvent
    public static void onScreenOpen(ScreenEvent.Opening event) {
        if (event.getNewScreen() instanceof TitleScreen titleScreen) {
            if (!DisclaimerScreen.hasAcceptedDisclaimer()) {
                event.setNewScreen(new DisclaimerScreen(titleScreen));
            }
        }
    }
}
