/**
 * This class is generated by jOOQ
 */
package com.psygate.minecraft.spigot.sovereignty.banda.db.model;


import com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.BandaBannedIp;
import com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.BandaBannedIpRange;
import com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.BandaBannedPlayer;
import com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.BandaBans;
import com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.BandaPlayerLog;
import com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.BandaProtection;

import javax.annotation.Generated;


/**
 * Convenience access to all tables in nucleus
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.2"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Tables {

	/**
	 * The table nucleus.banda_banned_ip
	 */
	public static final BandaBannedIp BANDA_BANNED_IP = com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.BandaBannedIp.BANDA_BANNED_IP;

	/**
	 * The table nucleus.banda_banned_ip_range
	 */
	public static final BandaBannedIpRange BANDA_BANNED_IP_RANGE = com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.BandaBannedIpRange.BANDA_BANNED_IP_RANGE;

	/**
	 * The table nucleus.banda_banned_player
	 */
	public static final BandaBannedPlayer BANDA_BANNED_PLAYER = com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.BandaBannedPlayer.BANDA_BANNED_PLAYER;

	/**
	 * The table nucleus.banda_bans
	 */
	public static final BandaBans BANDA_BANS = com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.BandaBans.BANDA_BANS;

	/**
	 * The table nucleus.banda_player_log
	 */
	public static final BandaPlayerLog BANDA_PLAYER_LOG = com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.BandaPlayerLog.BANDA_PLAYER_LOG;

	/**
	 * The table nucleus.banda_protection
	 */
	public static final BandaProtection BANDA_PROTECTION = com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.BandaProtection.BANDA_PROTECTION;
}