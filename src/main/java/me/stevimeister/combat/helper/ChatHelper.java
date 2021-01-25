package me.stevimeister.combat.helper;

import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import java.util.Collection;

/**
 * @author stevimeister on 05/01/2021
 **/
public final class ChatHelper {

    public static String colored(final String content) {
        return ChatColor.translateAlternateColorCodes('&', content
                .replace(">>", "\u00BB")
                .replace("<<", "\u00AB"));
    }

    public static void sendMessage(final CommandSender sender, final String text) {
        sender.sendMessage(colored(text));
    }

    public static void sendMessage(final Collection<? extends CommandSender> senders, final String text) {
        for (final CommandSender sender : senders)
            sendMessage(sender, text);
    }

    public static void sendTitle(final Player player, final String title, final String subTitle) {
        final PacketPlayOutTitle packetPlayOutTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE, new ChatComponentText(colored(title)));
        final PacketPlayOutTitle packetPlayOutSubTitle = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE, new ChatComponentText(colored(subTitle)));
        final PacketPlayOutTitle packetPlayOutTitleLength = new PacketPlayOutTitle(25, 50, 25);
        final PlayerConnection playerConnection = ((CraftPlayer) player).getHandle().playerConnection;

        playerConnection.sendPacket(packetPlayOutTitle);
        playerConnection.sendPacket(packetPlayOutSubTitle);
        playerConnection.sendPacket(packetPlayOutTitleLength);
    }

    public static void sendActionBar(final Player player, final String content) {
        final PacketPlayOutChat packet = new PacketPlayOutChat(new ChatComponentText(colored(content)), (byte) 2);
        ((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
    }
}
