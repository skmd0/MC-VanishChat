package com.skamlic.vanishchat;

import br.net.fabiozumbi12.UltimateChat.Bukkit.UCChannel;
import br.net.fabiozumbi12.UltimateChat.Bukkit.UChat;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class UCChannelSwitch {

    // returns true if the channel was switched from Global to Mod
    public static boolean switchChannel(FileConfiguration config, Player player, boolean isVisible) {
        UCChannel fromChannel = UChat.get().getChannel(config.getString("switchFromChannel"));
        UCChannel toChannel = UChat.get().getChannel(config.getString("switchToChannel"));
        UCChannel playerChannel = UChat.get().getPlayerChannel(player);

        if (fromChannel == null || toChannel == null || playerChannel == null) return false;

        String plrCh = playerChannel.getName();
        if (isVisible) {
            if (config.getBoolean("switchChannelWhenUnvanish")) {
                playerChannel.removeMember(player);
                fromChannel.addMember(player);
                return true;
            }
        } else if (plrCh.equals(fromChannel.getName())) {
            playerChannel.removeMember(player);
            toChannel.addMember(player);
            return true;
        }
        return false;
    }

    public static boolean joinInvisible(FileConfiguration config, Player player) {
        UCChannel fromChannel = UChat.get().getChannel(config.getString("switchFromChannel"));
        UCChannel toChannel = UChat.get().getChannel(config.getString("switchToChannel"));
        UCChannel playerChannel = UChat.get().getPlayerChannel(player);

        String plrCh = playerChannel.getName();
        if (plrCh.equals(fromChannel.getName())) {
            playerChannel.removeMember(player);
            toChannel.addMember(player);
            return true;
        }
        return false;
    }
}
