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
import com.psygate.minecraft.spigot.sovereignty.nucleus.commands.util.NucleusCommand;
import com.psygate.minecraft.spigot.sovereignty.nucleus.util.WorkerPool;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.jooq.Record2;
import org.jooq.Record3;
import org.jooq.RecordMapper;
import org.jooq.impl.DSL;

import java.util.*;
import java.util.stream.Collectors;

import static com.psygate.minecraft.spigot.sovereignty.banda.db.model.Tables.BANDA_PLAYER_LOG;

/**
 * Created by psygate (https://github.com/psygate) on 04.04.2016.
 */
public class PlayerInfoCommand extends NucleusCommand {
    public PlayerInfoCommand() {
        super(1, 1);
    }

    @Override
    protected void subOnCommand(CommandSender commandSender, Command command, String s, String[] strings) throws Exception {
        UUID uuid;
        String name = strings[0];
        try {
            uuid = UUID.fromString(name);
            commandSender.sendMessage("Querying by UUID.");
            WorkerPool.submit(() -> processUUIDs(commandSender, (Arrays.asList(uuid))));
        } catch (Exception e) {
            commandSender.sendMessage("Querying by name.");
            resolveName(commandSender, name);
        }
    }

    private void resolveName(CommandSender commandSender, String name) {
        Banda.DBI().asyncSubmit((conf) -> {
            List<UUID> uuids = DSL.using(conf)
                    .selectDistinct(BANDA_PLAYER_LOG.PLAYER_UUID, BANDA_PLAYER_LOG.PLAYER_NAME)
                    .from(BANDA_PLAYER_LOG)
                    .where(BANDA_PLAYER_LOG.PLAYER_NAME.eq(name))
                    .fetch(Record2<UUID, String>::value1);
            WorkerPool.submit(() -> {
                processUUIDs(commandSender, uuids);
            });
        });
    }

    private void processUUIDs(CommandSender commandSender, List<UUID> uuids) {
        if (uuids.isEmpty()) {
            commandSender.sendMessage(ChatColor.RED + "No matches found for name.");
        } else if (uuids.size() > 1) {
            commandSender.sendMessage(ChatColor.RED + "Multiple matches for query. Use a specific UUID to query for this name.");
        } else {
            UUID uuid = uuids.get(0);
            commandSender.sendMessage("Processing data, please be patient.");
            LinkedList<String> output = new LinkedList<>();
            output.add(ChatColor.YELLOW + "UUID: " + uuid);
            List<PlayerContainer> recs = Banda.DBI().submit((conf) -> {
                        return DSL.using(conf)
                                .selectDistinct(BANDA_PLAYER_LOG.PLAYER_UUID, BANDA_PLAYER_LOG.PLAYER_NAME, BANDA_PLAYER_LOG.IP)
                                .from(BANDA_PLAYER_LOG)
                                .where(BANDA_PLAYER_LOG.PLAYER_UUID.eq(uuid))
                                .fetch(new RecordMapper<Record3<UUID, String, byte[]>, PlayerContainer>() {
                                    @Override
                                    public PlayerContainer map(Record3<UUID, String, byte[]> record) {
                                        return new PlayerContainer(record);
                                    }
                                });
                    }
            );
            output.add(ChatColor.YELLOW + padLine(output, '-'));
            output.add(ChatColor.YELLOW + "Known Names:");
            output.add(ChatColor.WHITE + recs.stream().map(PlayerContainer::getName).distinct().reduce("", (a, b) -> a + " " + b));
            output.add(ChatColor.YELLOW + padLine(output, '-'));
            output.add(ChatColor.YELLOW + "Known IPs:");
            output.add(ChatColor.WHITE + recs.stream().map(PlayerContainer::getIp).distinct().map(IP::toString).reduce("", (a, b) -> a + " " + b));

            List<PlayerContainer> altrecs = Banda.DBI().submit((conf) -> {
                        return DSL.using(conf)
                                .selectDistinct(BANDA_PLAYER_LOG.PLAYER_UUID, BANDA_PLAYER_LOG.PLAYER_NAME, BANDA_PLAYER_LOG.IP)
                                .from(BANDA_PLAYER_LOG)
                                .where(BANDA_PLAYER_LOG.PLAYER_UUID.ne(uuid))
                                .and(BANDA_PLAYER_LOG.IP.in(recs.stream().map(PlayerContainer::getIp).map(IP::getIp).collect(Collectors.toList())))
                                .fetch(new RecordMapper<Record3<UUID, String, byte[]>, PlayerContainer>() {
                                    @Override
                                    public PlayerContainer map(Record3<UUID, String, byte[]> record) {
                                        return new PlayerContainer(record);
                                    }
                                });
                    }
            );
            output.add(ChatColor.YELLOW + padLine(output, '-'));
            output.add(ChatColor.YELLOW + "IP shared with:");
            altrecs.stream().map(ar -> ar.getName() + ChatColor.DARK_AQUA + " (" + ar.getUuid() + ") " + ChatColor.GREEN + ar.getIp().toString())
                    .forEach(output::add);
            commandSender.sendMessage(output.toArray(new String[output.size()]));
        }
    }

    private String padLine(Collection<String> list, char pad) {
//        int size = list.stream().mapToInt(String::length).max().orElseGet(() -> 0);
        int size = 20;
        char[] line = new char[size];

        for (int i = 0; i < 20; i++) {
            line[i] = pad;
        }

        return new String(line);
    }

    private final static class PlayerContainer {
        private final IP ip;
        private final UUID uuid;
        private final String name;


        public PlayerContainer(Record3<UUID, String, byte[]> record) {
            ip = new IP(record.value3());
            uuid = record.value1();
            name = record.value2();
        }

        public IP getIp() {
            return ip;
        }

        public UUID getUuid() {
            return uuid;
        }

        public String getName() {
            return name;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            PlayerContainer that = (PlayerContainer) o;

            if (ip != null ? !ip.equals(that.ip) : that.ip != null) return false;
            if (uuid != null ? !uuid.equals(that.uuid) : that.uuid != null) return false;
            return name != null ? name.equals(that.name) : that.name == null;

        }

        @Override
        public int hashCode() {
            int result = ip != null ? ip.hashCode() : 0;
            result = 31 * result + (uuid != null ? uuid.hashCode() : 0);
            result = 31 * result + (name != null ? name.hashCode() : 0);
            return result;
        }
    }

    private final static class IP {
        private final byte[] ip;

        public IP(byte[] ip) {
            this.ip = ip;
        }

        public byte[] getIp() {
            return ip;
        }

        @Override
        public String toString() {
            if (ip.length == 4) {
                int[] ip = new int[4];
                for (int i = 0; i < this.ip.length; i++) {
                    ip[i] = this.ip[i] & 0xFF;
                }

                return Arrays.stream(ip).mapToObj(Integer::toString).reduce("", (a, b) -> a + "." + b);
            } else {
                return bytesToHex(ip);
            }
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            IP ip1 = (IP) o;

            return Arrays.equals(ip, ip1.ip);

        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(ip);
        }
    }

    @Override
    protected String[] getName() {
        return new String[]{"playerinfo"};
    }

    final protected static char[] hexArray = "0123456789ABCDEF".toCharArray();

    public static String bytesToHex(byte[] bytes) {
        char[] hexChars = new char[bytes.length * 2];
        for (int j = 0; j < bytes.length; j++) {
            int v = bytes[j] & 0xFF;
            hexChars[j * 2] = hexArray[v >>> 4];
            hexChars[j * 2 + 1] = hexArray[v & 0x0F];
        }
        return new String(hexChars);
    }
}
