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

package com.psygate.minecraft.spigot.sovereignty.banda.listeners;

import com.psygate.minecraft.spigot.sovereignty.banda.Banda;
import com.psygate.minecraft.spigot.sovereignty.banda.data.BanManager;
import com.psygate.minecraft.spigot.sovereignty.banda.data.LoginResult;
import com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.records.BandaPlayerLogRecord;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.jooq.impl.DSL;

import java.sql.Timestamp;
import java.util.UUID;

import static com.psygate.minecraft.spigot.sovereignty.banda.db.model.Tables.BANDA_PLAYER_LOG;

/**
 * Created by psygate (https://github.com/psygate) on 03.04.2016.
 */
public class BanListener implements Listener {
    @EventHandler(ignoreCancelled = true, priority = EventPriority.LOWEST)
    public void onPlayerPreLogin(AsyncPlayerPreLoginEvent ev) {
        LoginResult result = LoginResult.GRANTED;
        if (BanManager.getInstance().isUUIDBanned(ev.getUniqueId())) {
            ev.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, Banda.getConfiguration().getBanMesage());
            ev.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_BANNED);
            result = LoginResult.REJECTED_UUID_BAN;
        }

        if (result == LoginResult.GRANTED) {
            if (BanManager.getInstance().isIPBanned(ev.getUniqueId(), ev.getAddress().getAddress())) {
                ev.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, Banda.getConfiguration().getBanMesage());
                ev.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_BANNED);
                result = LoginResult.REJECTED_IP_BAN;
            }
        }

        if (result == LoginResult.GRANTED) {
            if (BanManager.getInstance().isIPRangeBanned(ev.getUniqueId(), ev.getAddress().getAddress())) {
                ev.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, Banda.getConfiguration().getBanMesage());
                ev.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_BANNED);
                result = LoginResult.REJECTED_IP_RANGE_BAN;
            }
        }

        if (result == LoginResult.GRANTED) {
            if (BanManager.getInstance().isMultiAccountBanned(ev.getUniqueId(), ev.getAddress().getAddress())) {
                ev.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, Banda.getConfiguration().getBanMesage());
                ev.setLoginResult(AsyncPlayerPreLoginEvent.Result.KICK_BANNED);
                result = LoginResult.REJECT_MULTI_ACCOUNT;
            }
        }

        UUID uuid = ev.getUniqueId();
        String name = ev.getName();
        byte[] adress = ev.getAddress().getAddress();

        LoginResult finalResult = result;
        Banda.DBI().asyncSubmit((conf) -> {
            DSL.using(conf).insertInto(BANDA_PLAYER_LOG)
                    .set(new BandaPlayerLogRecord(
                            uuid,
                            name,
                            adress,
                            new Timestamp(System.currentTimeMillis()),
                            finalResult
                    )).execute();
        });
    }
}
