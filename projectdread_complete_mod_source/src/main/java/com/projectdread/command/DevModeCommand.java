package com.projectdread.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.projectdread.client.gui.AdminControlScreen;
import net.minecraft.client.Minecraft;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;

public class DevModeCommand {

    private static final String SECRET_CODE = "LETSHAVEFUN";

    public static void register(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(
            Commands.literal("devmode")
                .then(Commands.argument("code", StringArgumentType.string())
                    .executes(context -> {
                        String inputCode = StringArgumentType.getString(context, "code");
                        CommandSourceStack source = context.getSource();

                        if (SECRET_CODE.equals(inputCode)) {
                            source.sendSuccess(() -> Component.literal("§a[Project Dread] Access Granted. Opening Admin Panel..."), false);
                            
                            if (source.getEntity() instanceof ServerPlayer) {
                                Minecraft.getInstance().execute(() -> 
                                    Minecraft.getInstance().setScreen(new AdminControlScreen())
                                );
                            }
                            return 1;
                        } else {
                            source.sendFailure(Component.literal("§c[Project Dread] Access Denied: Invalid Security Code."));
                            return 0;
                        }
                    })
                )
        );
    }
}
