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

package com.psygate.minecraft.spigot.sovereignty.banda;

import com.psygate.minecraft.spigot.sovereignty.banda.configuration.Configuration;
import com.psygate.minecraft.spigot.sovereignty.banda.listeners.BanListener;
import com.psygate.minecraft.spigot.sovereignty.nucleus.Nucleus;
import com.psygate.minecraft.spigot.sovereignty.nucleus.managment.NucleusPlugin;
import com.psygate.minecraft.spigot.sovereignty.nucleus.sql.DatabaseInterface;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;

/**
 * Created by psygate (https://github.com/psygate) on 03.04.2016.
 */
public class Banda extends JavaPlugin implements NucleusPlugin {
    private static Banda instance;
    private DatabaseInterface dbi;
    private Configuration conf;

    @Override
    public void onEnable() {
        instance = this;
        saveDefaultConfig();
        conf = new Configuration(getConfig());
        Nucleus.getInstance().register(this);
    }

    public static Banda getInstance() {
        if (instance == null) {
            throw new IllegalStateException("Uninitialized plugin.");
        }

        return instance;
    }

    public static DatabaseInterface DBI() {
        return getInstance().dbi;
    }

    public static Configuration getConfiguration() {
        return getInstance().conf;
    }

    @Override
    public int getWantedDBVersion() {
        return 0;
    }

    @Override
    public void fail() {
        getPluginLogger().severe("Failed to load banda.");
        Bukkit.getServer().shutdown();
    }

    @Override
    public void setLogger(Logger logger) {

    }

    @Override
    public Logger getSubLogger(String logname) {
        return null;
    }

    @Override
    public Logger getPluginLogger() {
        return null;
    }

    @Override
    public List<Listener> getListeners() {
        return Arrays.asList(
                new BanListener()
        );
    }

    @Override
    public void setDatabaseInterface(DatabaseInterface databaseInterface) {
        this.dbi = databaseInterface;
    }
}
