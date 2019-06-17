/*
 *     Copyright (C) 2016 psygate (https://github.com/psygate)
 *
 *     This program is free software: you can redistribute it and/or modify
 *     it under the terms of the GNU General Public License as published by
 *     the Free Software Foundation, either version 3 of the License, or
 *     (at your option) any later version.
 *
 *     This program is distributed in the hope that it will be useful,
 *     but WITHOUT ANY WARRANTY; without even the implied warranty of
 *     MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *     GNU General Public License for more details.
 *
 *     You should have received a copy of the GNU General Public License
 */

package com.psygate.minecraft.spigot.sovereignty.banda.data;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.psygate.minecraft.spigot.sovereignty.banda.Banda;
import com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.records.BandaProtectionRecord;
import org.jooq.impl.DSL;

import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import static com.psygate.minecraft.spigot.sovereignty.banda.db.model.Tables.*;

/**
 * Created by psygate (https://github.com/psygate) on 04.04.2016.
 */
public class BanManager {
    private final LoadingCache<UUID, Boolean> uuidBanCache = CacheBuilder.newBuilder()
            .initialCapacity(100)
            .maximumSize(1000)
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build(new CacheLoader<UUID, Boolean>() {
                @Override
                public Boolean load(UUID uuid) throws Exception {
                    return Banda.DBI().submit((conf) -> {
                        return DSL.using(conf)
                                .selectFrom(BANDA_BANNED_PLAYER)
                                .where(BANDA_BANNED_PLAYER.PLAYER_UUID.eq(uuid))
                                .fetchOptional();
                    }).isPresent();
                }
            });

    private final LoadingCache<UUID, BandaProtectionRecord> protectionCache = CacheBuilder.newBuilder()
            .initialCapacity(100)
            .maximumSize(1000)
            .expireAfterAccess(5, TimeUnit.MINUTES)
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build(new CacheLoader<UUID, BandaProtectionRecord>() {
                @Override
                public BandaProtectionRecord load(UUID uuid) throws Exception {
                    return Banda.DBI().submit((conf) -> {
                        Optional<BandaProtectionRecord> opt = DSL.using(conf)
                                .selectFrom(BANDA_PROTECTION)
                                .where(BANDA_PROTECTION.PLAYER_UUID.eq(uuid))
                                .fetchOptional();

                        BandaProtectionRecord rec;
                        if (!opt.isPresent()) {
                            rec = new BandaProtectionRecord(uuid, false, false, false);
                            DSL.using(conf).insertInto(BANDA_PROTECTION)
                                    .set(rec)
                                    .execute();
                        } else {
                            rec = opt.get();
                        }

                        return rec;
                    });
                }
            });

    private final static byte[] FULL_NETMASK = new byte[]{~0, ~0, ~0, ~0};
    private static BanManager instance;

    private BanManager() {

    }

    public static BanManager getInstance() {
        if (instance == null) {
            instance = new BanManager();
        }

        return instance;
    }

    public void flush() {
        uuidBanCache.invalidateAll();
        protectionCache.invalidateAll();
    }

    public void flush(UUID uuid) {
        uuidBanCache.invalidate(uuid);
        protectionCache.invalidate(uuid);
    }

    public boolean isUUIDBanned(UUID uuid) {
        return uuidBanCache.getUnchecked(uuid);
    }

    public boolean isIPBanned(UUID uuid, byte[] address) {
        return Banda.DBI().submit((conf) -> {
            return DSL.using(conf).selectFrom(BANDA_BANNED_IP)
                    .where(BANDA_BANNED_IP.IP.eq(address))
                    .fetchOptional().isPresent();
        })
                && !protectionCache.getUnchecked(uuid).getProtectIpBan();
    }

    public boolean isIPRangeBanned(UUID uuid, byte[] address) {
        return Banda.DBI().submit((conf) -> {
            return DSL.using(conf).selectFrom(BANDA_BANNED_IP_RANGE)
                    .where(BANDA_BANNED_IP_RANGE.NETMASK.bitAnd(address).eq(BANDA_BANNED_IP_RANGE.IP))
                    .fetchOptional().isPresent();
        })
                && !protectionCache.getUnchecked(uuid).getProtectIpRangeBan();
    }

    public boolean isMultiAccountBanned(UUID uuid, byte[] address) {
        return Banda.DBI().submit((conf) -> {
            return DSL.using(conf).selectFrom(BANDA_PLAYER_LOG)
                    .where(BANDA_PLAYER_LOG.PLAYER_UUID.ne(uuid))
                    .and(BANDA_PLAYER_LOG.IP.eq(address))
                    .limit(1)
                    .fetchOptional()
                    .isPresent();
        })
                && !protectionCache.getUnchecked(uuid).getProtectMultiaccountBan();
    }

    public void setProtection(BandaProtectionRecord protection) {
        protectionCache.put(protection.getPlayerUuid(), protection);
    }
}
