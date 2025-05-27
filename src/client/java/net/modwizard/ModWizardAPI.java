package net.modwizard;

import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.SoundEvent;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModWizardAPI {
    public static void playSound(String sound, float volume, float pitch) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        Identifier id = Identifier.tryParse(sound);
        if (id == null) {
            client.player.sendMessage(Text.literal("Invalid sound identifier: " + sound), false);
            return;
        }

        SoundEvent event = SoundEvent.of(id);
        client.player.playSound(event, volume, pitch);
    }


    public static void sendMessage(String message, boolean sendGlobally) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (sendGlobally) {
            String plainMessage = message.replaceAll("§.", "");
            client.player.networkHandler.sendChatMessage(plainMessage);
        } else {
            client.inGameHud.getChatHud().addMessage(Text.literal(message));
        }
    }


    public static String playerClipboard (String text, String method) {
        if (method == "copy") {
            MinecraftClient.getInstance().keyboard.setClipboard(text);
            System.out.println("Copied" + text + "to clipboard");
            return text;
        } else if (method == "get") {
            return MinecraftClient.getInstance().keyboard.getClipboard();
        } else{
            return text;
        }
    }

}