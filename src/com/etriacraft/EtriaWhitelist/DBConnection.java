package com.etriacraft.EtriaWhitelist;

import java.util.logging.Level;

import lib.Database;
import lib.MySQL;
import lib.SQLite;

public final class DBConnection {
	
	public static Database sql;
	
	public DBConnection() {
		initialize();
	}
	
	public void initialize() {
		if (Config.SQL_ENGINE.equalsIgnoreCase("mysql")) {
			sql = new MySQL(Utils.log, Strings.PREFIX.toString(), Config.MYSQL_HOST, Config.MYSQL_PORT, Config.MYSQL_USER, Config.MYSQL_PASS, Config.MYSQL_DB);
			((MySQL) sql).open();
			
			if (!sql.tableExists("whitelist")) {
				String query = "CREATE TABLE IF NOT EXISTS `whitelist` ("
						+ "`id` int(32) NOT NULL AUTO_INCREMENT,"
						+ "`playername` varchar(32) NOT NULL,"
						+ "PRIMARY KEY (`id`));";
				sql.modifyQuery(query);
			}
		} else if (Config.SQL_ENGINE.equalsIgnoreCase("sqlite")) {
			sql = new SQLite(Utils.log, Strings.PREFIX.toString(), Config.SQLITE_DB, EtriaWhitelist.getInstance().getDataFolder().getAbsolutePath());
			((SQLite) sql).open();
			
			if (!sql.tableExists("whitelist")) {
				String query = "CREATE TABLE `whitelist` ("
						+ "`playername` TEXT NOT NULL;";
				sql.modifyQuery(query);
			}
		} else {
			Utils.log.log(Level.SEVERE, Strings.PREFIX + "Unknown value for SQL Engine, expected sqlite or mysql");
		}
	}

}