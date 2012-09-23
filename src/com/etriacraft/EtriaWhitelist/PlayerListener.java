package com.etriacraft.EtriaWhitelist;

import java.sql.ResultSet;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;

public class PlayerListener implements Listener {

	@EventHandler
    public void onPlayerLogin(PlayerLoginEvent e) {
        final String player = e.getPlayer().getName();
        Utils.log.info("[WhitelistSQL] " + player + " is trying to join...");
        try {
        	ResultSet rs = DBConnection.sql.readQuery("SELECT `playername` FROM `whitelist` WHERE `playername` = '" + player + "' LIMIT 1;");
            if (!rs.last()) {
                e.disallow(Result.KICK_WHITELIST, Config.KICK_MESSAGE);
                Utils.log.info("[WhitelistSQL] Kicked " + player +"; not on the whitelist!");
            } else {
                Utils.log.info("[WhitelistSQL] Allowed " + player + "!");
            }
            rs.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
