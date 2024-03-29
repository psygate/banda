/**
 * This class is generated by jOOQ
 */
package com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables;


import com.psygate.minecraft.spigot.sovereignty.banda.db.model.Keys;
import com.psygate.minecraft.spigot.sovereignty.banda.db.model.Nucleus;
import com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.records.BandaProtectionRecord;
import com.psygate.minecraft.spigot.sovereignty.banda.sql.util.BooleanIntConverter;
import com.psygate.minecraft.spigot.sovereignty.nucleus.sql.util.UUIDByteConverter;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Table;
import org.jooq.TableField;
import org.jooq.UniqueKey;
import org.jooq.impl.TableImpl;


/**
 * This class is generated by jOOQ.
 */
@Generated(
	value = {
		"http://www.jooq.org",
		"jOOQ version:3.7.2"
	},
	comments = "This class is generated by jOOQ"
)
@SuppressWarnings({ "all", "unchecked", "rawtypes" })
public class BandaProtection extends TableImpl<BandaProtectionRecord> {

	private static final long serialVersionUID = -1526735888;

	/**
	 * The reference instance of <code>nucleus.banda_protection</code>
	 */
	public static final BandaProtection BANDA_PROTECTION = new BandaProtection();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<BandaProtectionRecord> getRecordType() {
		return BandaProtectionRecord.class;
	}

	/**
	 * The column <code>nucleus.banda_protection.player_uuid</code>.
	 */
	public final TableField<BandaProtectionRecord, UUID> PLAYER_UUID = createField("player_uuid", org.jooq.impl.SQLDataType.BINARY.length(16).nullable(false), this, "", new UUIDByteConverter());

	/**
	 * The column <code>nucleus.banda_protection.protect_ip_range_ban</code>.
	 */
	public final TableField<BandaProtectionRecord, Boolean> PROTECT_IP_RANGE_BAN = createField("protect_ip_range_ban", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "", new BooleanIntConverter());

	/**
	 * The column <code>nucleus.banda_protection.protect_ip_ban</code>.
	 */
	public final TableField<BandaProtectionRecord, Boolean> PROTECT_IP_BAN = createField("protect_ip_ban", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "", new BooleanIntConverter());

	/**
	 * The column <code>nucleus.banda_protection.protect_multiaccount_ban</code>.
	 */
	public final TableField<BandaProtectionRecord, Boolean> PROTECT_MULTIACCOUNT_BAN = createField("protect_multiaccount_ban", org.jooq.impl.SQLDataType.INTEGER.nullable(false), this, "", new BooleanIntConverter());

	/**
	 * Create a <code>nucleus.banda_protection</code> table reference
	 */
	public BandaProtection() {
		this("banda_protection", null);
	}

	/**
	 * Create an aliased <code>nucleus.banda_protection</code> table reference
	 */
	public BandaProtection(String alias) {
		this(alias, BANDA_PROTECTION);
	}

	private BandaProtection(String alias, Table<BandaProtectionRecord> aliased) {
		this(alias, aliased, null);
	}

	private BandaProtection(String alias, Table<BandaProtectionRecord> aliased, Field<?>[] parameters) {
		super(alias, Nucleus.NUCLEUS, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<BandaProtectionRecord> getPrimaryKey() {
		return Keys.KEY_BANDA_PROTECTION_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<BandaProtectionRecord>> getKeys() {
		return Arrays.<UniqueKey<BandaProtectionRecord>>asList(Keys.KEY_BANDA_PROTECTION_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BandaProtection as(String alias) {
		return new BandaProtection(alias, this);
	}

	/**
	 * Rename this table
	 */
	public BandaProtection rename(String name) {
		return new BandaProtection(name, null);
	}
}
