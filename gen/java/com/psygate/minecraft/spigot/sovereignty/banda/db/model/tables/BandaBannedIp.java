/**
 * This class is generated by jOOQ
 */
package com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables;


import com.psygate.minecraft.spigot.sovereignty.banda.db.model.Keys;
import com.psygate.minecraft.spigot.sovereignty.banda.db.model.Nucleus;
import com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.records.BandaBannedIpRecord;

import java.util.Arrays;
import java.util.List;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.ForeignKey;
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
public class BandaBannedIp extends TableImpl<BandaBannedIpRecord> {

	private static final long serialVersionUID = -8528478;

	/**
	 * The reference instance of <code>nucleus.banda_banned_ip</code>
	 */
	public static final BandaBannedIp BANDA_BANNED_IP = new BandaBannedIp();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<BandaBannedIpRecord> getRecordType() {
		return BandaBannedIpRecord.class;
	}

	/**
	 * The column <code>nucleus.banda_banned_ip.ban_id</code>.
	 */
	public final TableField<BandaBannedIpRecord, Long> BAN_ID = createField("ban_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

	/**
	 * The column <code>nucleus.banda_banned_ip.ip</code>.
	 */
	public final TableField<BandaBannedIpRecord, byte[]> IP = createField("ip", org.jooq.impl.SQLDataType.VARBINARY.length(16).nullable(false), this, "");

	/**
	 * Create a <code>nucleus.banda_banned_ip</code> table reference
	 */
	public BandaBannedIp() {
		this("banda_banned_ip", null);
	}

	/**
	 * Create an aliased <code>nucleus.banda_banned_ip</code> table reference
	 */
	public BandaBannedIp(String alias) {
		this(alias, BANDA_BANNED_IP);
	}

	private BandaBannedIp(String alias, Table<BandaBannedIpRecord> aliased) {
		this(alias, aliased, null);
	}

	private BandaBannedIp(String alias, Table<BandaBannedIpRecord> aliased, Field<?>[] parameters) {
		super(alias, Nucleus.NUCLEUS, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<BandaBannedIpRecord> getPrimaryKey() {
		return Keys.KEY_BANDA_BANNED_IP_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<BandaBannedIpRecord>> getKeys() {
		return Arrays.<UniqueKey<BandaBannedIpRecord>>asList(Keys.KEY_BANDA_BANNED_IP_PRIMARY, Keys.KEY_BANDA_BANNED_IP_IP);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForeignKey<BandaBannedIpRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<BandaBannedIpRecord, ?>>asList(Keys.BANDA_BANNED_IP_IBFK_1);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BandaBannedIp as(String alias) {
		return new BandaBannedIp(alias, this);
	}

	/**
	 * Rename this table
	 */
	public BandaBannedIp rename(String name) {
		return new BandaBannedIp(name, null);
	}
}
