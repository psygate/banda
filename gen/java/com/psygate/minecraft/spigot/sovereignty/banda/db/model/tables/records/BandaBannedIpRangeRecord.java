/**
 * This class is generated by jOOQ
 */
package com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.records;


import com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.BandaBannedIpRange;
import com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.interfaces.IBandaBannedIpRange;

import javax.annotation.Generated;

import org.jooq.Field;
import org.jooq.Record1;
import org.jooq.Record3;
import org.jooq.Row3;
import org.jooq.impl.UpdatableRecordImpl;


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
public class BandaBannedIpRangeRecord extends UpdatableRecordImpl<BandaBannedIpRangeRecord> implements Record3<Long, byte[], byte[]>, IBandaBannedIpRange {

	private static final long serialVersionUID = 1667457677;

	/**
	 * Setter for <code>nucleus.banda_banned_ip_range.ban_id</code>.
	 */
	public void setBanId(Long value) {
		setValue(0, value);
	}

	/**
	 * Getter for <code>nucleus.banda_banned_ip_range.ban_id</code>.
	 */
	@Override
	public Long getBanId() {
		return (Long) getValue(0);
	}

	/**
	 * Setter for <code>nucleus.banda_banned_ip_range.ip</code>.
	 */
	public void setIp(byte[] value) {
		setValue(1, value);
	}

	/**
	 * Getter for <code>nucleus.banda_banned_ip_range.ip</code>.
	 */
	@Override
	public byte[] getIp() {
		return (byte[]) getValue(1);
	}

	/**
	 * Setter for <code>nucleus.banda_banned_ip_range.netmask</code>.
	 */
	public void setNetmask(byte[] value) {
		setValue(2, value);
	}

	/**
	 * Getter for <code>nucleus.banda_banned_ip_range.netmask</code>.
	 */
	@Override
	public byte[] getNetmask() {
		return (byte[]) getValue(2);
	}

	// -------------------------------------------------------------------------
	// Primary key information
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Record1<Long> key() {
		return (Record1) super.key();
	}

	// -------------------------------------------------------------------------
	// Record3 type implementation
	// -------------------------------------------------------------------------

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row3<Long, byte[], byte[]> fieldsRow() {
		return (Row3) super.fieldsRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Row3<Long, byte[], byte[]> valuesRow() {
		return (Row3) super.valuesRow();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<Long> field1() {
		return BandaBannedIpRange.BANDA_BANNED_IP_RANGE.BAN_ID;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<byte[]> field2() {
		return BandaBannedIpRange.BANDA_BANNED_IP_RANGE.IP;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Field<byte[]> field3() {
		return BandaBannedIpRange.BANDA_BANNED_IP_RANGE.NETMASK;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Long value1() {
		return getBanId();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte[] value2() {
		return getIp();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public byte[] value3() {
		return getNetmask();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BandaBannedIpRangeRecord value1(Long value) {
		setBanId(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BandaBannedIpRangeRecord value2(byte[] value) {
		setIp(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BandaBannedIpRangeRecord value3(byte[] value) {
		setNetmask(value);
		return this;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public BandaBannedIpRangeRecord values(Long value1, byte[] value2, byte[] value3) {
		value1(value1);
		value2(value2);
		value3(value3);
		return this;
	}

	// -------------------------------------------------------------------------
	// Constructors
	// -------------------------------------------------------------------------

	/**
	 * Create a detached BandaBannedIpRangeRecord
	 */
	public BandaBannedIpRangeRecord() {
		super(BandaBannedIpRange.BANDA_BANNED_IP_RANGE);
	}

	/**
	 * Create a detached, initialised BandaBannedIpRangeRecord
	 */
	public BandaBannedIpRangeRecord(Long banId, byte[] ip, byte[] netmask) {
		super(BandaBannedIpRange.BANDA_BANNED_IP_RANGE);

		setValue(0, banId);
		setValue(1, ip);
		setValue(2, netmask);
	}
}