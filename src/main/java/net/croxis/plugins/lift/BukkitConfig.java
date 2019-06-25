/*
 * This file is part of Lift.
 *
 * Copyright (c) ${project.inceptionYear}-2013, croxis <https://github.com/croxis/>
 *
 * Lift is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Lift is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with Lift. If not, see <http://www.gnu.org/licenses/>.
 */
package net.croxis.plugins.lift;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Material;

public class BukkitConfig extends Config{
	public static HashMap<Material, Double> blockSpeeds = new HashMap<>();
	public static HashSet<Material> floorMaterials = new HashSet<>();
    public static HashSet<Material> buttonMaterials = new HashSet<>();
    public static HashSet<Material> signMaterials = new HashSet<>();
	static boolean useNoCheatPlus = false;
	static boolean metricbool = true;
	static boolean serverFlight;

    public void loadConfig(BukkitLift plugin){
        plugin.getConfig().options().copyDefaults(true);
        liftArea = plugin.getConfig().getInt("maxLiftArea");
        BukkitConfig.maxHeight = plugin.getConfig().getInt("maxHeight");
        BukkitConfig.debug = plugin.getConfig().getBoolean("debug");
        BukkitConfig.liftMobs = plugin.getConfig().getBoolean("liftMobs");
        BukkitConfig.autoPlace = plugin.getConfig().getBoolean("autoPlace");
        BukkitConfig.checkFloor = plugin.getConfig().getBoolean("checkFloor", false);
        BukkitConfig.preventEntry = plugin.getConfig().getBoolean("preventEntry", false);
        BukkitConfig.preventLeave = plugin.getConfig().getBoolean("preventLeave", false);
        BukkitConfig.redstone = plugin.getConfig().getBoolean("redstone", false);
        Set<String> baseBlockKeys = plugin.getConfig().getConfigurationSection("baseBlockSpeeds").getKeys(false);
        for (String key : baseBlockKeys){
            BukkitConfig.blockSpeeds.put(Material.valueOf(key), plugin.getConfig().getDouble("baseBlockSpeeds." + key));
        }
        List<String> configFloorMaterials = plugin.getConfig().getStringList("floorBlocks");
        for (String key : configFloorMaterials){
            if (key.contains("*")){
                // Probably be smarter to iterate through the material list first, then see if config matches
                for (Material material : Material.values()){
                    if (material.toString().matches(key.replace("*", ".*?"))){
                        BukkitConfig.floorMaterials.add(material);
                        plugin.logInfo("Floor material added: " + material.toString());
                    }

                };
            } else {
                BukkitConfig.floorMaterials.add(Material.valueOf(key));
                plugin.logInfo("Floor material added: " + key);
            }
        }

        List<String> configButtonMaterials = plugin.getConfig().getStringList("buttonBlocks");
        for (String key : configButtonMaterials){
            if (key.contains("*")){
                // Probably be smarter to iterate through the material list first, then see if config matches
                for (Material material : Material.values()){
                    if (material.toString().matches(key.replace("*", ".*?"))){
                        BukkitConfig.buttonMaterials.add(material);
                        plugin.logInfo("Button material added: " + material.toString());
                    }

                };
            } else {
                BukkitConfig.buttonMaterials.add(Material.valueOf(key));
                plugin.logInfo("Button material added: " + key);
            }
        }

        List<String> configSignMaterials = plugin.getConfig().getStringList("signBlocks");
        for (String key : configSignMaterials){
            if (key.contains("*")){
                // Probably be smarter to iterate through the material list first, then see if config matches
                for (Material material : Material.values()){
                    if (material.toString().matches(key.replace("*", ".*?"))){
                        BukkitConfig.signMaterials.add(material);
                        plugin.logInfo("Sign material added: " + material.toString());
                    }

                };
            } else {
                BukkitConfig.signMaterials.add(Material.valueOf(key));
                plugin.logInfo("Sign material added: " + key);
            }
        }

        BukkitConfig.stringOneFloor = plugin.getConfig().getString("STRING_oneFloor", "There is only one floor silly.");
        BukkitConfig.stringCurrentFloor = plugin.getConfig().getString("STRING_currentFloor", "Current Floor:");
        BukkitConfig.stringDestination = plugin.getConfig().getString("STRING_dest", "Dest:");
        BukkitConfig.stringCantEnter = plugin.getConfig().getString("STRING_cantEnter", "Can't enter elevator in use");
        BukkitConfig.stringCantLeave = plugin.getConfig().getString("STRING_cantLeave", "Can't leave elevator in use");

        BukkitConfig.metricbool = plugin.getConfig().getBoolean("metrics", true);
        plugin.saveConfig();

        BukkitConfig.serverFlight = plugin.getServer().getAllowFlight();

        if (BukkitConfig.preventEntry){
            Bukkit.getServer().getPluginManager().registerEvents(plugin, plugin);
        }

        if(plugin.getServer().getPluginManager().getPlugin("NoCheatPlus") != null){
            BukkitConfig.useNoCheatPlus = true;
            plugin.logDebug("Hooked into NoCheatPlus");
        }
    }
}
