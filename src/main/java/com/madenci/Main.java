package com.madenci;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    
    private static Main instance;
    
    @Override
    public void onEnable() {
        instance = this;
        
        // Config dosyalarını kaydet
        saveDefaultConfig();
        saveResource("items.yml", false);
        
        getLogger().info("Madenci eklentisi aktif!");
        
        // Komutları kaydet
        getCommand("madenci").setExecutor(new MadenciCommand());
        
        // Event listener'ları kaydet
        Bukkit.getPluginManager().registerEvents(new MadenciListener(), this);
        Bukkit.getPluginManager().registerEvents(new MadenciGUIListener(), this);
    }
    
    @Override
    public void onDisable() {
        getLogger().info("Madenci eklentisi kapatıldı!");
    }
    
    public static Main getInstance() {
        return instance;
    }
}
