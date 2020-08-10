package com.skamlic.vanishchat;

import de.myzelyam.api.vanish.PlayerHideEvent;
import de.myzelyam.api.vanish.PlayerShowEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class VanishListener implements Listener {

    private final ChannelSwitchMessage callback;

    public VanishListener(ChannelSwitchMessage callback) {
        this.callback = callback;
    }

    @EventHandler
    public void onVanish(PlayerHideEvent e) {
        this.callback.handleChannelSwitchMessage(e.getPlayer());
    }

    @EventHandler
    public void onReappear(PlayerShowEvent e) {
        this.callback.handleChannelSwitchMessage(e.getPlayer());
    }
}