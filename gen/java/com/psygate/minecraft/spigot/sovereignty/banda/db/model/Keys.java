/**
 * This class is generated by jOOQ
 */
package com.psygate.minecraft.spigot.sovereignty.banda.db.model;


import com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.BandaBannedIp;
import com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.BandaBannedIpRange;
import com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.BandaBannedPlayer;
import com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.BandaBans;
import com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.BandaProtection;
import com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.records.BandaBannedIpRangeRecord;
import com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.records.BandaBannedIpRecord;
import com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.records.BandaBannedPlayerRecord;
import com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.records.BandaBansRecord;
import com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.records.BandaProtectionRecord;

import javax.annotation.Generated;

import org.jooq.ForeignKey;
import org.jooq.Identity;
import org.jooq.UniqueKey;
import org.jooq.impl.AbstractKeys;


/**
 * A class modelling foreign key relationships between tables of the <code>nucleus</code> 
 * schema
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.2"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class Keys {

	// -------------------------------------------------------------------------
	// IDENTITY definitions
	// -------------------------------------------------------------------------

	public static final Identity<BandaBansRecord, Long> IDENTITY_BANDA_BANS = Identities0.IDENTITY_BANDA_BANS;

	// -------------------------------------------------------------------------
	// UNIQUE and PRIMARY KEY definitions
	// -------------------------------------------------------------------------

	public static final UniqueKey<BandaBannedIpRecord> KEY_BANDA_BANNED_IP_PRIMARY = UniqueKeys0.KEY_BANDA_BANNED_IP_PRIMARY;
	public static final UniqueKey<BandaBannedIpRecord> KEY_BANDA_BANNED_IP_IP = UniqueKeys0.KEY_BANDA_BANNED_IP_IP;
	public static final UniqueKey<BandaBannedIpRangeRecord> KEY_BANDA_BANNED_IP_RANGE_PRIMARY = UniqueKeys0.KEY_BANDA_BANNED_IP_RANGE_PRIMARY;
	public static final UniqueKey<BandaBannedIpRangeRecord> KEY_BANDA_BANNED_IP_RANGE_IP = UniqueKeys0.KEY_BANDA_BANNED_IP_RANGE_IP;
	public static final UniqueKey<BandaBannedPlayerRecord> KEY_BANDA_BANNED_PLAYER_PRIMARY = UniqueKeys0.KEY_BANDA_BANNED_PLAYER_PRIMARY;
	public static final UniqueKey<BandaBansRecord> KEY_BANDA_BANS_PRIMARY = UniqueKeys0.KEY_BANDA_BANS_PRIMARY;
	public static final UniqueKey<BandaProtectionRecord> KEY_BANDA_PROTECTION_PRIMARY = UniqueKeys0.KEY_BANDA_PROTECTION_PRIMARY;

	// -------------------------------------------------------------------------
	// FOREIGN KEY definitions
	// -------------------------------------------------------------------------

	public static final ForeignKey<BandaBannedIpRecord, BandaBansRecord> BANDA_BANNED_IP_IBFK_1 = ForeignKeys0.BANDA_BANNED_IP_IBFK_1;
	public static final ForeignKey<BandaBannedIpRangeRecord, BandaBansRecord> BANDA_BANNED_IP_RANGE_IBFK_1 = ForeignKeys0.BANDA_BANNED_IP_RANGE_IBFK_1;
	public static final ForeignKey<BandaBannedPlayerRecord, BandaBansRecord> BANDA_BANNED_PLAYER_IBFK_1 = ForeignKeys0.BANDA_BANNED_PLAYER_IBFK_1;

	// -------------------------------------------------------------------------
	// [#1459] distribute members to avoid static initialisers > 64kb
	// -------------------------------------------------------------------------

	private static class Identities0 extends AbstractKeys {
		public static Identity<BandaBansRecord, Long> IDENTITY_BANDA_BANS = createIdentity(BandaBans.BANDA_BANS, BandaBans.BANDA_BANS.BAN_ID);
	}

	private static class UniqueKeys0 extends AbstractKeys {
		public static final UniqueKey<BandaBannedIpRecord> KEY_BANDA_BANNED_IP_PRIMARY = createUniqueKey(BandaBannedIp.BANDA_BANNED_IP, BandaBannedIp.BANDA_BANNED_IP.BAN_ID);
		public static final UniqueKey<BandaBannedIpRecord> KEY_BANDA_BANNED_IP_IP = createUniqueKey(BandaBannedIp.BANDA_BANNED_IP, BandaBannedIp.BANDA_BANNED_IP.IP);
		public static final UniqueKey<BandaBannedIpRangeRecord> KEY_BANDA_BANNED_IP_RANGE_PRIMARY = createUniqueKey(BandaBannedIpRange.BANDA_BANNED_IP_RANGE, BandaBannedIpRange.BANDA_BANNED_IP_RANGE.BAN_ID);
		public static final UniqueKey<BandaBannedIpRangeRecord> KEY_BANDA_BANNED_IP_RANGE_IP = createUniqueKey(BandaBannedIpRange.BANDA_BANNED_IP_RANGE, BandaBannedIpRange.BANDA_BANNED_IP_RANGE.IP, BandaBannedIpRange.BANDA_BANNED_IP_RANGE.NETMASK);
		public static final UniqueKey<BandaBannedPlayerRecord> KEY_BANDA_BANNED_PLAYER_PRIMARY = createUniqueKey(BandaBannedPlayer.BANDA_BANNED_PLAYER, BandaBannedPlayer.BANDA_BANNED_PLAYER.PLAYER_UUID);
		public static final UniqueKey<BandaBansRecord> KEY_BANDA_BANS_PRIMARY = createUniqueKey(BandaBans.BANDA_BANS, BandaBans.BANDA_BANS.BAN_ID);
		public static final UniqueKey<BandaProtectionRecord> KEY_BANDA_PROTECTION_PRIMARY = createUniqueKey(BandaProtection.BANDA_PROTECTION, BandaProtection.BANDA_PROTECTION.PLAYER_UUID);
	}

	private static class ForeignKeys0 extends AbstractKeys {
		public static final ForeignKey<BandaBannedIpRecord, BandaBansRecord> BANDA_BANNED_IP_IBFK_1 = createForeignKey(com.psygate.minecraft.spigot.sovereignty.banda.db.model.Keys.KEY_BANDA_BANS_PRIMARY, BandaBannedIp.BANDA_BANNED_IP, BandaBannedIp.BANDA_BANNED_IP.BAN_ID);
		public static final ForeignKey<BandaBannedIpRangeRecord, BandaBansRecord> BANDA_BANNED_IP_RANGE_IBFK_1 = createForeignKey(com.psygate.minecraft.spigot.sovereignty.banda.db.model.Keys.KEY_BANDA_BANS_PRIMARY, BandaBannedIpRange.BANDA_BANNED_IP_RANGE, BandaBannedIpRange.BANDA_BANNED_IP_RANGE.BAN_ID);
		public static final ForeignKey<BandaBannedPlayerRecord, BandaBansRecord> BANDA_BANNED_PLAYER_IBFK_1 = createForeignKey(com.psygate.minecraft.spigot.sovereignty.banda.db.model.Keys.KEY_BANDA_BANS_PRIMARY, BandaBannedPlayer.BANDA_BANNED_PLAYER, BandaBannedPlayer.BANDA_BANNED_PLAYER.BAN_ID);
	}
}