package com.madenci;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class DataManager {
    
    private static File dataFile;
    private static FileConfiguration dataConfig;
    
    public static void setup(Main plugin) {
        dataFile = new File(plugin.getDataFolder(), "data.yml");
        
        if (!dataFile.exists()) {
            try {
                dataFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
    }
    
    public static FileConfiguration getData() {
        return dataConfig;
    }
    
    public static void saveData() {
        try {
            dataConfig.save(dataFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    public static void reloadData() {
        dataConfig = YamlConfiguration.loadConfiguration(dataFile);
    }
    
    public static void saveMiner(UUID playerUUID, MinerNPC miner) {
        String path = "miners." + playerUUID.toString();
        
        dataConfig.set(path + ".owner", playerUUID.toString());
        dataConfig.set(path + ".location.world", miner.getLocation().getWorld().getName());
        dataConfig.set(path + ".location.x", miner.getLocation().getX());
        dataConfig.set(path + ".location.y", miner.getLocation().getY());
        dataConfig.set(path + ".location.z", miner.getLocation().getZ());
        dataConfig.set(path + ".level", miner.getLevel());
        
        // Stok kaydet
        for (java.util.Map.Entry<String, Integer> entry : miner.getStock().entrySet()) {
            dataConfig.set(path + ".stock." + entry.getKey(), entry.getValue());
        }
        
        saveData();
    }
    
    public static void removeMiner(UUID playerUUID) {
        dataConfig.set("miners." + playerUUID.toString(), null);
        saveData();
    }
    
    public static boolean hasMiner(UUID playerUUID) {
        return dataConfig.contains("miners." + playerUUID.toString());
    }
}
