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
import com.psygate.minecraft.spigot.sovereignty.banda.data.IPRange4;
import com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.records.BandaBannedIpRangeRecord;
import com.psygate.minecraft.spigot.sovereignty.banda.db.model.tables.records.BandaBansRecord;
import com.psygate.minecraft.spigot.sovereignty.nucleus.commands.util.CommandException;
import com.psygate.minecraft.spigot.sovereignty.nucleus.commands.util.NucleusCommand;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jooq.impl.DSL;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;
import java.util.UUID;

import static com.psygate.minecraft.spigot.sovereignty.banda.db.model.Tables.BANDA_BANNED_IP_RANGE;
import static com.psygate.minecraft.spigot.sovereignty.banda.db.model.Tables.BANDA_BANS;

/**
 * Created by psygate (https://github.com/psygate) on 04.04.2016.
 */
public class IPRangeBanCommand extends NucleusCommand {
    private final static byte[] FULL_NETMASK = new byte[]{~0, ~0, ~0, ~0};

    public IPRangeBanCommand() {
        super(2, 2);
    }

    @Override
    protected void subOnCommand(CommandSender commandSender, Command command, String s, String[] strings) throws Exception {
        String pre = strings[0];
        String reason = strings[1];
        byte[][] ipmask;

        UUID banner = (commandSender instanceof ConsoleCommandSender) ? new UUID(0, 0) : ((Player) commandSender).getUniqueId();
        IPRange4 range = new IPRange4(pre);

        try {
            if (commandSender instanceof Player && range.contains(((Player) commandSender).getAddress().getAddress().getAddress())) {
                throw new CommandException("Command would ban you. Refusing to execute.");
            }

            for (Player player : Bukkit.getOnlinePlayers()) {
                if (range.contains(player.getAddress().getAddress().getAddress())) {
                    player.kickPlayer(Banda.getConfiguration().getBanMesage());
                }
            }

            Banda.DBI().submit((conf) -> {
                long id = DSL.using(conf).insertInto(BANDA_BANS)
                        .set(new BandaBansRecord(null, banner, reason, new Timestamp(System.currentTimeMillis())))
                        .returning(BANDA_BANS.BAN_ID)
                        .fetchOne()
                        .getBanId();
                DSL.using(conf).insertInto(BANDA_BANNED_IP_RANGE)
                        .set(new BandaBannedIpRangeRecord(id, range.getIp(), range.getMask()))
                        .execute();
            });
            commandSender.sendMessage(ChatColor.GREEN + "Banned " + range);
        } catch (Exception e) {
            throw new CommandException(e.getMessage());
        }
    }

    private byte[] bitwiseAnd(byte[] bytes, byte[] address) {
        byte[] out = new byte[bytes.length];
        for (int i = 0; i < bytes.length; i++) {
            out[i] = (byte) (bytes[i] & address[i]);
        }

        return out;
    }

    private byte[][] decodeIPMask(String pre) throws UnknownHostException {
        String ip1 = pre.split("/")[0];
        String ip2 = pre.split("/")[1];

        byte[] one = InetAddress.getByName(ip1).getAddress();
        byte[] two = buildMask(ip2);

        byte[] ip = one;
        byte[] mask = two;

        return new byte[][]{one, two};
    }

    private byte[] buildMask(String ip2) {
        int target = Integer.parseInt(ip2);
        int out = 0;
        for (int i = 0; i < 32; i++) {
            if (i < target) {
                out = (out | 1);
            }
            out = out << 1;
        }

        return new byte[]{(byte) ((out >>> 24) & 0xFF), (byte) ((out >>> 16) & 0xFF), (byte) ((out >>> 8) & 0xFF), (byte) (out & 0xFF)};
    }

    private byte[][] decodeIPIP(String pre) throws UnknownHostException {
        String ip1 = pre.split("-")[0];
        String ip2 = pre.split("-")[2];

        byte[] one = InetAddress.getByName(ip1).getAddress();
        byte[] two = InetAddress.getByName(ip2).getAddress();

        byte[] ip = one;
        byte[] mask = two;

        return new byte[][]{one, two};
    }

    @Override
    protected String[] getName() {
        return new String[]{"baniprange"};
    }
}
