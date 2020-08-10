package com.skamlic.vanishchat;

import de.myzelyam.api.vanish.VanishAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class VanishChat extends JavaPlugin implements Listener, ChannelSwitchMessage {

    private boolean isVanishLoaded;
    private boolean isUCLoaded;
    private boolean isPAPILoaded;
    private FileConfiguration config;

    @Override
    public void onEnable() {
        Bukkit.getServer().getPluginManager().registerEvents(this, this);
        getServer().getScheduler().scheduleSyncDelayedTask(this, this::registerEvents, 100L);
        config = this.getConfig();
        config.addDefault("switchFromChannel", "Global");
        config.addDefault("switchToChannel", "Mod");
        config.addDefault("switchChannelWhenUnvanish", false);
        config.addDefault("switchChannelMessage", "&4Chat channel automatically changed to &6Mod&4.");
        config.addDefault("switchChannelMessagePAPI", "&4Chat channel automatically changed to &6%uchat_player_channel_name%&4.");
        config.options().copyDefaults(true);
        saveConfig();
    }

    public void registerEvents() {
        isUCLoaded = Bukkit.getPluginManager().isPluginEnabled("UltimateChat");
        if (!isUCLoaded) {
            getServer().getConsoleSender().sendMessage(ChatColor.RED + "VanishChat: UltimateChat plugin not detected. Auto-switching channel won't work.");
        }

        isVanishLoaded = Bukkit.getPluginManager().isPluginEnabled("SuperVanish") || Bukkit.getPluginManager().isPluginEnabled("PremiumVanish");
        if (isVanishLoaded) {
            getServer().getPluginManager().registerEvents(this, this);
            // VanishListener wrapper is necessary because you can't register an event if plugin is not loaded.
            getServer().getPluginManager().registerEvents(new VanishListener(this), this);
        } else {
            getServer().getConsoleSender().sendMessage(ChatColor.RED + "VanishChat: Vanish plugin not detected. Auto-switching channel won't work.");
        }
        isPAPILoaded = Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        if (isVanishLoaded && VanishAPI.isInvisible(p)) {
            handleChannelSwitchMessage(event.getPlayer());
        }
    }

    @Override
    public void handleChannelSwitchMessage(Player p) {
        if (isVanishLoaded && isUCLoaded) {
            boolean visibility = VanishAPI.isInvisible(p);
            boolean wasSwitchSuccessful = UCChannelSwitch.switchChannel(config, p, visibility);
            if (wasSwitchSuccessful) {
                BroadcastChannelSwitch.printSwitchMsg(config, isPAPILoaded, p);
            }
        }
    }
}
