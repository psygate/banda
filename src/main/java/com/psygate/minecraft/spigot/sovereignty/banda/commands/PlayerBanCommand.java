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
import com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.records.BandaBannedPlayerRecord;
import com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.records.BandaBansRecord;
import com.psygate.minecraft.spigot.sovereignty.nucleus.commands.util.CommandException;
import com.psygate.minecraft.spigot.sovereignty.nucleus.commands.util.NucleusCommand;
import com.psygate.minecraft.spigot.sovereignty.nucleus.util.player.PlayerManager;
import org.bukkit.BanList;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jooq.Record1;
import org.jooq.impl.DSL;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.psygate.minecraft.spigot.sovereignty.banda.db.model.Tables.*;

/**
 * Created by psygate (https://github.com/psygate) on 04.04.2016.
 */
public class PlayerBanCommand extends NucleusCommand {
    public PlayerBanCommand() {
        super(2, 100);
    }

    @Override
    protected void subOnCommand(CommandSender commandSender, Command command, String s, String[] strings) throws Exception {
        String pre = strings[0];
        String reason = strings[1];

        for (int i = 2; i < strings.length; i++) {
            reason += " " + strings[i];
        }
        try {
            Bukkit.getBanList(BanList.Type.NAME).addBan(pre, reason, new Date(Long.MAX_VALUE), commandSender.getName());
            Bukkit.getOnlinePlayers().stream().filter(p -> p.getName().equals(pre)).forEach(p -> p.kickPlayer(Banda.getConfiguration().getBanMesage()));
        } catch (Exception e) {
            commandSender.sendMessage(ChatColor.RED + "Bukkit ban failed. Check if this causes issues.");
        }
        UUID banner = (commandSender instanceof ConsoleCommandSender) ? new UUID(0, 0) : ((Player) commandSender).getUniqueId();

        UUID uuid = null;
        try {
            uuid = UUID.fromString(pre);
        } catch (Exception e) {
            try {
                uuid = PlayerManager.getInstance().toUUID(pre);
            } catch (Exception ex) {
                throw new CommandException("Can't resolve UUID for \"" + pre + "\"");
            }
        }

        if (commandSender instanceof Player && uuid.equals(((Player) commandSender).getUniqueId())) {
            throw new CommandException("Issued ban would ban you. Refusing to execute.");
        }

        if (Bukkit.getPlayer(uuid) != null) {
            Bukkit.getPlayer(uuid).kickPlayer(Banda.getConfiguration().getBanMesage());
        }

        UUID finalUuid = uuid;
        String finalReason = reason;
        Banda.DBI().submit((conf) -> {
            long id = DSL.using(conf).insertInto(BANDA_BANS)
                    .set(new BandaBansRecord(null, banner, finalReason, new Timestamp(System.currentTimeMillis())))
                    .returning(BANDA_BANS.BAN_ID)
                    .fetchOne()
                    .getBanId();
            DSL.using(conf).insertInto(BANDA_BANNED_PLAYER)
                    .set(new BandaBannedPlayerRecord(id, finalUuid))
                    .execute();
        });

        commandSender.sendMessage(ChatColor.GREEN + "Banned " + uuid);

    }

    @Override
    protected String[] getName() {
        return new String[]{"banuuid", "banplayer", "ban"};
    }
}
