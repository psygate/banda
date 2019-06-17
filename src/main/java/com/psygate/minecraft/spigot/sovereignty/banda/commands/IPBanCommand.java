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
import com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.records.BandaBannedIpRecord;
import com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.records.BandaBansRecord;
import com.psygate.minecraft.spigot.sovereignty.nucleus.commands.util.CommandException;
import com.psygate.minecraft.spigot.sovereignty.nucleus.commands.util.NucleusCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jooq.impl.DSL;

import java.net.InetAddress;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.UUID;

import static com.psygate.minecraft.spigot.sovereignty.banda.db.model.Tables.BANDA_BANNED_IP;
import static com.psygate.minecraft.spigot.sovereignty.banda.db.model.Tables.BANDA_BANS;

/**
 * Created by psygate (https://github.com/psygate) on 04.04.2016.
 */
public class IPBanCommand extends NucleusCommand {
    private final static byte[] FULL_NETMASK = new byte[]{~0, ~0, ~0, ~0};

    public IPBanCommand() {
        super(2, 2);
    }

    @Override
    protected void subOnCommand(CommandSender commandSender, Command command, String s, String[] strings) throws Exception {
        String pre = strings[0];
        String reason = strings[1];
        UUID banner = (commandSender instanceof ConsoleCommandSender) ? new UUID(0, 0) : ((Player) commandSender).getUniqueId();

        try {
            byte[] ip = InetAddress.getByName(pre).getAddress();
            if (commandSender instanceof Player && Arrays.equals(((Player) commandSender).getAddress().getAddress().getAddress(), ip)) {
                throw new CommandException("Command would ban you. Refusing to execute.");
            }

            Banda.DBI().submit((conf) -> {
                long id = DSL.using(conf).insertInto(BANDA_BANS)
                        .set(new BandaBansRecord(null, banner, reason, new Timestamp(System.currentTimeMillis())))
                        .returning(BANDA_BANS.BAN_ID)
                        .fetchOne()
                        .getBanId();
                DSL.using(conf).insertInto(BANDA_BANNED_IP)
                        .set(new BandaBannedIpRecord(id, ip))
                        .execute();
            });

            commandSender.sendMessage(ChatColor.GREEN + "Banned " + pre);

        } catch (Exception e) {
            throw new CommandException(e.getMessage());
        }
    }

    @Override
    protected String[] getName() {
        return new String[]{"banip"};
    }
}
