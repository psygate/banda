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
import com.psygate.minecraft.spigot.sovereignty.banda.db.model.Tables;
import com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.records.BandaPlayerLogRecord;
import com.psygate.minecraft.spigot.sovereignty.nucleus.commands.util.CommandException;
import com.psygate.minecraft.spigot.sovereignty.nucleus.commands.util.NucleusCommand;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jooq.impl.DSL;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by psygate (https://github.com/psygate) on 04.04.2016.
 */
public class BandaLogCommand extends NucleusCommand {
    private final static byte[] FULL_NETMASK = new byte[]{~0, ~0, ~0, ~0};

    public BandaLogCommand() {
        super(0, 1);
    }

    @Override
    protected void subOnCommand(CommandSender commandSender, Command command, String s, String[] strings) throws Exception {
        int page = 0;
        if (strings.length > 0) {
            try {
                page = Integer.parseInt(strings[0]);
            } catch (NumberFormatException e) {
                throw new CommandException("Illegal page number: " + strings[0]);
            }
        }
        int finalPage = page;
        List<BandaPlayerLogRecord> recs = Banda.DBI().submit((conf) -> {
            return DSL.using(conf).selectFrom(Tables.BANDA_PLAYER_LOG)
                    .limit(finalPage * 15, finalPage * 15 + 15)
                    .fetch();
        });

        LinkedList<String> output = new LinkedList<>();
        for (BandaPlayerLogRecord rec : recs) {
            output.add(rec.getLogTime().toString());
            output.add(" - " + ChatColor.YELLOW.toString() + rec.getPlayerUuid() + ChatColor.WHITE + " (" + rec.getPlayerName() + ")");
            output.add(" - " + rec.getEventResult());
        }

        commandSender.sendMessage(output.toArray(new String[output.size()]));
    }

    @Override
    protected String[] getName() {
        return new String[]{"bandalog"};
    }
}
