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
import org.jooq.DSLContext;
import org.jooq.impl.DSL;

import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import static com.psygate.minecraft.spigot.sovereignty.banda.db.model.Tables.BANDA_BANNED_PLAYER;
import static com.psygate.minecraft.spigot.sovereignty.banda.db.model.Tables.BANDA_BANS;

/**
 * Created by psygate (https://github.com/psygate) on 04.04.2016.
 */
public class PlayerUnbanCommand extends NucleusCommand {
    public PlayerUnbanCommand() {
        super(1, 1);
    }

    @Override
    protected void subOnCommand(CommandSender commandSender, Command command, String s, String[] strings) throws Exception {
        String pre = strings[0];

        try {
            Bukkit.getBanList(BanList.Type.NAME).pardon(pre);
        } catch (Exception e) {
            commandSender.sendMessage(ChatColor.RED + "Bukkit unban failed. Check if this causes issues.");
        }

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

        UUID finalUuid = uuid;
        Banda.DBI().submit((conf) -> {
            DSLContext ctx = DSL.using(conf);
            ctx.selectFrom(BANDA_BANNED_PLAYER).where(BANDA_BANNED_PLAYER.PLAYER_UUID.eq(finalUuid)).fetchOptional()
                    .ifPresent(rec -> {
                        ctx.deleteFrom(BANDA_BANS).where(BANDA_BANS.BAN_ID.eq(rec.getBanId())).execute();
                    });
        });

        BanManager.getInstance().flush(uuid);

        commandSender.sendMessage(ChatColor.GREEN + "Unbanned " + uuid);

    }

    @Override
    protected String[] getName() {
        return new String[]{"unbanuuid", "unbanplayer", "unban"};
    }
}
