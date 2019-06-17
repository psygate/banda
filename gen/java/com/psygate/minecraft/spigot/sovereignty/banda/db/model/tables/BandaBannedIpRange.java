/**
 * This class is generated by jOOQ
 */
package com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables;


import com.psygate.minecraft.spigot.sovereignty.banda.db.model.Keys;
import com.psygate.minecraft.spigot.sovereignty.banda.db.model.Nucleus;
import com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.records.BandaBannedIpRangeRecord;

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
public class BandaBannedIpRange extends TableImpl<BandaBannedIpRangeRecord> {

	private static final long serialVersionUID = -399248372;

	/**
	 * The reference instance of <code>nucleus.banda_banned_ip_range</code>
	 */
	public static final BandaBannedIpRange BANDA_BANNED_IP_RANGE = new BandaBannedIpRange();

	/**
	 * The class holding records for this type
	 */
	@Override
	public Class<BandaBannedIpRangeRecord> getRecordType() {
		return BandaBannedIpRangeRecord.class;
	}

	/**
	 * The column <code>nucleus.banda_banned_ip_range.ban_id</code>.
	 */
	public final TableField<BandaBannedIpRangeRecord, Long> BAN_ID = createField("ban_id", org.jooq.impl.SQLDataType.BIGINT.nullable(false), this, "");

	/**
	 * The column <code>nucleus.banda_banned_ip_range.ip</code>.
	 */
	public final TableField<BandaBannedIpRangeRecord, byte[]> IP = createField("ip", org.jooq.impl.SQLDataType.VARBINARY.length(16).nullable(false), this, "");

	/**
	 * The column <code>nucleus.banda_banned_ip_range.netmask</code>.
	 */
	public final TableField<BandaBannedIpRangeRecord, byte[]> NETMASK = createField("netmask", org.jooq.impl.SQLDataType.VARBINARY.length(16).nullable(false), this, "");

	/**
	 * Create a <code>nucleus.banda_banned_ip_range</code> table reference
	 */
	public BandaBannedIpRange() {
		this("banda_banned_ip_range", null);
	}

	/**
	 * Create an aliased <code>nucleus.banda_banned_ip_range</code> table reference
	 */
	public BandaBannedIpRange(String alias) {
		this(alias, BANDA_BANNED_IP_RANGE);
	}

	private BandaBannedIpRange(String alias, Table<BandaBannedIpRangeRecord> aliased) {
		this(alias, aliased, null);
	}

	private BandaBannedIpRange(String alias, Table<BandaBannedIpRangeRecord> aliased, Field<?>[] parameters) {
		super(alias, Nucleus.NUCLEUS, aliased, parameters, "");
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public UniqueKey<BandaBannedIpRangeRecord> getPrimaryKey() {
		return Keys.KEY_BANDA_BANNED_IP_RANGE_PRIMARY;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<UniqueKey<BandaBannedIpRangeRecord>> getKeys() {
		return Arrays.<UniqueKey<BandaBannedIpRangeRecord>>asList(Keys.KEY_BANDA_BANNED_IP_RANGE_PRIMARY, Keys.KEY_BANDA_BANNED_IP_RANGE_IP);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public List<ForeignKey<BandaBannedIpRangeRecord, ?>> getReferences() {
		return Arrays.<ForeignKey<BandaBannedIpRangeRecord, ?>>asList(Keys.BANDA_BANNED_IP_RANGE_IBFK_1);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BandaBannedIpRange as(String alias) {
		return new BandaBannedIpRange(alias, this);
	}

	/**
	 * Rename this table
	 */
	public BandaBannedIpRange rename(String name) {
		return new BandaBannedIpRange(name, null);
	}
}
