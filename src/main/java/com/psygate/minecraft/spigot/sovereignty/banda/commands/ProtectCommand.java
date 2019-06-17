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

package com.psygate.minecraft.spigot.sovereignty.banda.commands;

import com.psygate.minecraft.spigot.sovereignty.banda.Banda;
import com.psygate.minecraft.spigot.sovereignty.banda.data.BanManager;
import com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.records.BandaProtectionRecord;
import com.psygate.minecraft.spigot.sovereignty.nucleus.commands.util.CommandException;
import com.psygate.minecraft.spigot.sovereignty.nucleus.commands.util.NucleusCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jooq.Record1;
import org.jooq.impl.DSL;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.psygate.minecraft.spigot.sovereignty.banda.db.model.Tables.BANDA_PLAYER_LOG;
import static com.psygate.minecraft.spigot.sovereignty.banda.db.model.Tables.BANDA_PROTECTION;

/**
 * Created by psygate (https://github.com/psygate) on 04.04.2016.
 */
public class ProtectCommand extends NucleusCommand {
    private final static byte[] FULL_NETMASK = new byte[]{~0, ~0, ~0, ~0};

    public ProtectCommand() {
        super(2, 4);
    }

    @Override
    protected void subOnCommand(CommandSender commandSender, Command command, String s, String[] strings) throws Exception {
       /*
           player_uuid                 BINARY(16)          NOT NULL,
    protect_ip_range_ban        INTEGER             NOT NULL,
    protect_imposter_ban        INTEGER             NOT NULL,
    protect_multiaccount_ban    INTEGER             NOT NULL,
    PRIMARY KEY(player_uuid)
        */

        String pre = strings[0];
        UUID uuid = null;

        try {
            uuid = UUID.fromString(pre);
        } catch (Exception e) {
            Optional<UUID> opt = Bukkit.getOnlinePlayers().stream().filter(p -> p.getName().equals(pre)).map(Player::getUniqueId).findAny();

            if (!opt.isPresent()) {
                List<UUID> list = Banda.DBI().submit((conf) -> {
                    return DSL.using(conf).selectDistinct(BANDA_PLAYER_LOG.PLAYER_UUID)
                            .from(BANDA_PLAYER_LOG)
                            .where(BANDA_PLAYER_LOG.PLAYER_NAME.eq(pre))
                            .fetch(Record1<UUID>::value1);
                });

                if (list.size() > 1) {
                    throw new CommandException("Multiple UUIDs match the name " + pre + ", use a specific UUID for this ban. (" + pre + ")");
                } else {
                    uuid = list.get(0);
                }
            } else {
                uuid = opt.get();
            }
        }

        String[] args = Arrays.copyOfRange(strings, 1, strings.length);

        UUID finalUuid = uuid;
        BandaProtectionRecord rec = toRecord(finalUuid, args);
        Banda.DBI().submit((conf) -> {
            DSL.using(conf).insertInto(BANDA_PROTECTION)
                    .set(rec)
                    .onDuplicateKeyUpdate()
                    .set(rec)
                    .execute();
        });

        BanManager.getInstance().setProtection(rec);

        commandSender.sendMessage(ChatColor.GREEN + pre + " now protected: iprange: "
                + rec.getProtectIpRangeBan()
                + " ip: " + rec.getProtectIpBan()
                + " multiaccount: " + rec.getProtectMultiaccountBan());
    }

    @Override
    protected String[] getName() {
        return new String[]{"protect"};
    }

    private BandaProtectionRecord toRecord(UUID uuid, String[] args) {
//        usage: protect <playername|uuid> (iprange=(true|false)) (ip=(true|false)) (multiaccount=(true|false))
        BandaProtectionRecord rec = new BandaProtectionRecord(uuid, false, false, false);
        for (String s : args) {
            String[] data = s.split("=");
            String type = data[0];
            boolean enabled = Boolean.parseBoolean(data[1]);

            if (type.trim().toLowerCase().equals("iprange")) {
                rec.setProtectIpRangeBan(enabled);
            }

            if (type.trim().toLowerCase().equals("ip")) {
                rec.setProtectIpBan(enabled);
            }

            if (type.trim().toLowerCase().equals("multiaccount")) {
                rec.setProtectMultiaccountBan(enabled);
            }
        }

        return rec;
    }
}
