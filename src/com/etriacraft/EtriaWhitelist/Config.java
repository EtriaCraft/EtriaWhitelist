package com.etriacraft.EtriaWhitelist;

import org.bukkit.configuration.Configuration;

public final class Config {
	
	public static Configuration config;
	
	// MySQL Information
	public static String SQL_ENGINE;
	public static String SQLITE_DB;
	public static String MYSQL_HOST;
	public static int MYSQL_PORT;
	public static String MYSQL_USER;
	public static String MYSQL_PASS;
	public static String MYSQL_DB;
	
	// Messages
	public static String KICK_MESSAGE;
	
	public Config (EtriaWhitelist instance) {
		config = instance.getConfig();
		config.set("SQL.ENGINE", SQL_ENGINE = config.getString("SQL.ENGINE", "sqlite"));
		config.set("SQL.SQLITE.DB", SQLITE_DB = config.getString("SQL.SQLITE.DB", "etriawhitelist.db"));
		config.set("SQL.MYSQL.HOST", MYSQL_HOST = config.getString("SQL.MYSQL.HOST", "localhost"));
	    config.set("SQL.MYSQL.PORT", MYSQL_PORT = config.getInt("SQL.MYSQL.PORT", 3306));
	    config.set("SQL.MYSQL.USER", MYSQL_USER = config.getString("SQL.MYSQL.USER", "user"));
	    config.set("SQL.MYSQL.PASS", MYSQL_PASS = config.getString("SQL.MYSQL.PASS", "pass"));
	    config.set("SQL.MYSQL.DB", MYSQL_DB = config.getString("SQL.MYSQL.DB", "database"));
		config.set("MESSAGES.KICK_MESSAGE", KICK_MESSAGE = config.getString("MESSAGES.KICK_MESSAGE", "Sorry, you need to be whitelisted!"));
		instance.saveConfig();
	}

}