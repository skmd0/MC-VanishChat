package com.skamlic.vanishchat;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class BroadcastChannelSwitch {

    public static void printSwitchMsg(FileConfiguration config, boolean isPAPILoaded, Player player) {
        String rawTxt = config.getString("switchChannelMessage");
        if (isPAPILoaded) {
            rawTxt = PlaceholderAPI.setPlaceholders(player, config.getString("switchChannelMessagePAPI"));
        }
        if (rawTxt == null) {
            rawTxt = "&4Chat channel automatically changed.";
        }
        String msg = ChatColor.translateAlternateColorCodes('&', rawTxt);
        player.sendMessage(msg);
    }
}
