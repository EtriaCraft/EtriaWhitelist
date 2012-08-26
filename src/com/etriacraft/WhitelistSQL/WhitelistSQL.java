package com.etriacraft.WhitelistSQL;

import java.sql.ResultSet;
import java.util.logging.Logger;

import lib.MySQL;

import org.bukkit.configuration.Configuration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerLoginEvent.Result;
import org.bukkit.plugin.java.JavaPlugin;

public class WhitelistSQL extends JavaPlugin implements Listener {

	private static MySQL sql;
	private static final Logger log = Logger.getLogger("Minecraft");
	
	// Configuration Stuffs
	public static String MYSQL_HOST, MYSQL_USER, MYSQL_PASS, MYSQL_DB;
	public static int MYSQL_PORT;
	
	public static String KICK_MESSAGE;
	
	@Override
	public void onEnable() {
		getServer().getPluginManager().registerEvents(this, this);
		final Configuration config = getConfig();
		
		config.set("MYSQL.HOST", MYSQL_HOST = config.getString("MYSQL.HOST", "localhost"));
        config.set("MYSQL.PORT", MYSQL_PORT = config.getInt("MYSQL.PORT", 3306));
        config.set("MYSQL.USER", MYSQL_USER = config.getString("MYSQL.USER", "user"));
        config.set("MYSQL.PASS", MYSQL_PASS = config.getString("MYSQL.PASS", "pass"));
        config.set("MYSQL.DB", MYSQL_DB = config.getString("MYSQL.DB", "database"));
		config.set("KICK_MESSAGE", KICK_MESSAGE = config.getString("KICK_MESSAGE", "Sorry, you need to be whitelisted!"));
		
		saveConfig();
		
		sql = new MySQL(log, "[WhitelistSQL] ", MYSQL_HOST, MYSQL_PORT, MYSQL_USER, MYSQL_PASS, MYSQL_DB);
		sql.open();
	}
	
	@Override
	public void onDisable() {
		if (sql.getConnection() != null) {
			sql.close();
		}
	}
	
	@EventHandler
    public void onPlayerLogin(PlayerLoginEvent e) {
        final String player = e.getPlayer().getName();
        log.info("[WhitelistSQL] " + player + " is trying to join...");
        try {
        	ResultSet rs = sql.readQuery("SELECT `playername` FROM `whitelist` WHERE `playername` = '" + player + "' LIMIT 1;");
            if (!rs.last()) {
                e.disallow(Result.KICK_WHITELIST, KICK_MESSAGE);
                log.info("[WhitelistSQL] Kicked " + player +"; not on the whitelist!");
            } else {
                log.info("[WhitelistSQL] Allowed " + player + "!");
            }
            rs.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
    
}