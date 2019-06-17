/**
 * This class is generated by jOOQ
 */
package com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.daos;


import com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.BandaBannedIp;
import com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.records.BandaBannedIpRecord;

import java.util.List;

import javax.annotation.Generated;

import org.jooq.Configuration;
import org.jooq.impl.DAOImpl;


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
public class BandaBannedIpDao extends DAOImpl<BandaBannedIpRecord, com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.pojos.BandaBannedIp, Long> {

	/**
	 * Create a new BandaBannedIpDao without any configuration
	 */
	public BandaBannedIpDao() {
		super(BandaBannedIp.BANDA_BANNED_IP, com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.pojos.BandaBannedIp.class);
	}

	/**
	 * Create a new BandaBannedIpDao with an attached configuration
	 */
	public BandaBannedIpDao(Configuration configuration) {
		super(BandaBannedIp.BANDA_BANNED_IP, com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.pojos.BandaBannedIp.class, configuration);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Long getId(com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.pojos.BandaBannedIp object) {
		return object.getBanId();
	}

	/**
	 * Fetch records that have <code>ban_id IN (values)</code>
	 */
	public List<com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.pojos.BandaBannedIp> fetchByBanId(Long... values) {
		return fetch(BandaBannedIp.BANDA_BANNED_IP.BAN_ID, values);
	}

	/**
	 * Fetch a unique record that has <code>ban_id = value</code>
	 */
	public com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.pojos.BandaBannedIp fetchOneByBanId(Long value) {
		return fetchOne(BandaBannedIp.BANDA_BANNED_IP.BAN_ID, value);
	}

	/**
	 * Fetch records that have <code>ip IN (values)</code>
	 */
	public List<com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.pojos.BandaBannedIp> fetchByIp(byte[]... values) {
		return fetch(BandaBannedIp.BANDA_BANNED_IP.IP, values);
	}

	/**
	 * Fetch a unique record that has <code>ip = value</code>
	 */
	public com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.pojos.BandaBannedIp fetchOneByIp(byte[] value) {
		return fetchOne(BandaBannedIp.BANDA_BANNED_IP.IP, value);
	}
}
