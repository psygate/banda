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
import com.psygate.minecraft.spigot.sovereignty.nucleus.commands.util.CommandException;
import com.psygate.minecraft.spigot.sovereignty.nucleus.commands.util.NucleusCommand;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jooq.Record1;
import org.jooq.impl.DSL;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.psygate.minecraft.spigot.sovereignty.banda.db.model.Tables.BANDA_PLAYER_LOG;

/**
 * Created by psygate (https://github.com/psygate) on 04.04.2016.
 */
public class BanInfoCommand extends NucleusCommand {
    public BanInfoCommand() {
        super(2, 2);
    }

    @Override
    protected void subOnCommand(CommandSender commandSender, Command command, String s, String[] strings) throws Exception {
        String pre = strings[0];
        String reason = strings[1];
        UUID banner = (commandSender instanceof ConsoleCommandSender) ? new UUID(0, 0) : ((Player) commandSender).getUniqueId();

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

        throw new CommandException("Not yet implemented.");

    }

    @Override
    protected String[] getName() {
        return new String[]{"baninfo"};
    }
}
