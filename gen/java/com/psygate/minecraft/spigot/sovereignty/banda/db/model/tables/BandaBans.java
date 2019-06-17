/**
 * This class is generated by jOOQ
 */
package com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables;


import com.psygate.minecraft.spigot.sovereignty.banda.db.model.Keys;
import com.psygate.minecraft.spigot.sovereignty.banda.db.model.Nucleus;
import com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.records.BandaBansRecord;
import com.psygate.minecraft.spigot.sovereignty.nucleus.sql.util.UUIDByteConverter;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Identity;
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
public class BandaBans extends TableImpl<BandaBansRecord> {

	private static final long serialVersionUID = 451702964;

	/**
	 * The reference instance of <code>nucleus.banda_bans</code>
	 */
	public static final BandaBans BANDA_BANS = new BandaBans();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<BandaBansRecord> getRecordType() {
		return BandaBansRecord.class;
	}

	/**
	 * The column <code>nucleus.banda_bans.ban_id</code>.
	 */
	public final TableField<BandaBansRecord, Long> BAN_ID = createField("ban_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

	/**
	 * The column <code>nucleus.banda_bans.issuer_uuid</code>.
	 */
	public final TableField<BandaBansRecord, UUID> ISSUER_UUID = createField("issuer_uuid", org.jooq.impl.SQLDataType.BINARY.length(16).nullable(false), this, "", new UUIDByteConverter());

	/**
	 * The column <code>nucleus.banda_bans.reason</code>.
	 */
	public final TableField<BandaBansRecord, String> REASON = createField("reason", org.jooq.impl.SQLDataType.VARCHAR.length(255).nullable(false), this, "");

	/**
	 * The column <code>nucleus.banda_bans.issued</code>.
	 */
	public final TableField<BandaBansRecord, Timestamp> ISSUED = createField("issued", org.jooq.impl.SQLDataType.TIMESTAMP.nullable(false).defaulted(true), this, "");

	/**
	 * Create a <code>nucleus.banda_bans</code> table reference
	 */
	public BandaBans() {
		this("banda_bans", null);
	}

	/**
	 * Create an aliased <code>nucleus.banda_bans</code> table reference
	 */
	public BandaBans(String alias) {
		this(alias, BANDA_BANS);
	}

	private BandaBans(String alias, Table<BandaBansRecord> aliased) {
		this(alias, aliased, null);
	}

	private BandaBans(String alias, Table<BandaBansRecord> aliased, Field<?>[] parameters) {
		super(alias, Nucleus.NUCLEUS, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Identity<BandaBansRecord, Long> getIdentity() {
		return Keys.IDENTITY_BANDA_BANS;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<BandaBansRecord> getPrimaryKey() {
		return Keys.KEY_BANDA_BANS_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<BandaBansRecord>> getKeys() {
		return Arrays.<UniqueKey<BandaBansRecord>>asList(Keys.KEY_BANDA_BANS_PRIMARY);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BandaBans as(String alias) {
		return new BandaBans(alias, this);
	}

	/**
	 * Rename this table
	 */
	public BandaBans rename(String name) {
		return new BandaBans(name, null);
	}
}
