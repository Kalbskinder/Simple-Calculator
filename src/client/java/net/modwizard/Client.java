package net.modwizard;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.api.ClientModInitializer;
import net.modwizard.utils.*;
import net.modwizard.ModWizardAPI;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import org.slf4j.LoggerFactory;

public class Client implements ClientModInitializer {
    public static final String MOD_ID = "ModWizardLogger";
    public static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(
                ClientCommandManager.literal("calc")

                .then(ClientCommandManager.argument("calculation", StringArgumentType.string())
                    .executes(context -> {
                        String calculation = context.getArgument("calculation", String.class);
                        String result = String.valueOf(Eval.eval(calculation));
                        ModWizardAPI.playSound("ui.button.click", 1f, 1f);
                        ModWizardAPI.sendMessage(result, false);
                        ModWizardAPI.sendMessage("Copied result to clipboard", false);
                        ModWizardAPI.playerClipboard(result, "copy");

                        return 1;
                    })
                )

            );
        });
    }
}