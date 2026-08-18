package com.madenci;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MinerManager {
    
    private static Map<UUID, MinerNPC> miners = new HashMap<>();
    
    public static void createMiner(Player player) {
        Location loc = player.getLocation().add(0, 0, 1);
        
        // Claim kontrolü
        if (Main.getInstance().getConfig().getBoolean("Settings.useClaims")) {
            // Claim kontrolü yapılmalı
        }
        
        MinerNPC miner = new MinerNPC(player.getUniqueId(), loc);
        miners.put(player.getUniqueId(), miner);
    }
    
    public static void removeMiner(Player player) {
        MinerNPC miner = miners.get(player.getUniqueId());
        if (miner != null) {
            miner.removeNPC();
            miners.remove(player.getUniqueId());
        }
    }
    
    public static boolean hasMiner(Player player) {
        return miners.containsKey(player.getUniqueId());
    }
    
    public static MinerNPC getMiner(Player player) {
        return miners.get(player.getUniqueId());
    }
    
    public static void showStock(Player player) {
        MinerNPC miner = miners.get(player.getUniqueId());
        if (miner == null) {
            player.sendMessage(ChatColor.RED + "Madencin yok!");
            return;
        }
        
        Map<String, Integer> stock = miner.getStock();
        if (stock.isEmpty()) {
            player.sendMessage(ChatColor.YELLOW + "Stok boş!");
            return;
        }
        
        player.sendMessage(ChatColor.GOLD + "=== Madenci Stok ===");
        for (Map.Entry<String, Integer> entry : stock.entrySet()) {
            player.sendMessage(ChatColor.WHITE + entry.getKey() + ": " + ChatColor.GREEN + entry.getValue());
        }
        player.sendMessage(ChatColor.GRAY + "Toplam: " + miner.getCurrentStock() + "/" + miner.getCapacity());
    }
    
    public static void sellItems(Player player) {
        MinerNPC miner = miners.get(player.getUniqueId());
        if (miner == null) {
            player.sendMessage(ChatColor.RED + "Madencin yok!");
            return;
        }
        
        miner.sellAllItems();
        player.sendMessage(ChatColor.GREEN + "Tüm stok satıldı!");
    }
    
    public static void handleAutoBreak(org.bukkit.event.block.BlockBreakEvent event) {
        Block block = event.getBlock();
        Material material = block.getType();
        
        // Maden blokları listesi
        String[] miningBlocks = {
            "IRON_ORE", "GOLD_ORE", "DIAMOND_ORE", "COAL_ORE", 
            "REDSTONE_ORE", "LAPIS_ORE", "EMERALD_ORE", 
            "ANCIENT_DEBRIS", "COPPER_ORE", "DEEPSLATE_IRON_ORE",
            "DEEPSLATE_GOLD_ORE", "DEEPSLATE_DIAMOND_ORE", 
            "DEEPSLATE_COAL_ORE", "DEEPSLATE_COPPER_ORE"
        };
        
        boolean isMiningBlock = false;
        for (String blockName : miningBlocks) {
            if (material.name().equals(blockName)) {
                isMiningBlock = true;
                break;
            }
        }
        
        if (!isMiningBlock) {
            return;
        }
        
        Player player = event.getPlayer();
        MinerNPC miner = miners.get(player.getUniqueId());
        
        if (miner == null) {
            return;
        }
        
        // Bloğu kır ve itemi stoka ekle
        event.setDropItems(false);
        
        // Item'i belirle
        String itemName = getMaterialItemName(material);
        if (itemName != null) {
            miner.addItem(itemName, 1);
            
            if (Main.getInstance().getConfig().getBoolean("DetailedSettings.playSound")) {
                player.playSound(player.getLocation(), org.bukkit.Sound.BLOCK_STONE_BREAK, 1.0f, 1.0f);
            }
        }
    }
    
    public static void handleAutoCollect(org.bukkit.event.entity.ItemSpawnEvent event) {
        Item item = event.getEntity();
        Material material = item.getItemStack().getType();
        
        // Maden itemleri kontrolü
        String itemName = getMaterialItemName(material);
        if (itemName == null) {
            return;
        }
        
        // Yakındaki madenciyi bul
        Location itemLoc = item.getLocation();
        MinerNPC nearestMiner = null;
        double nearestDistance = 10.0;
        
        for (MinerNPC miner : miners.values()) {
            double distance = miner.getLocation().distance(itemLoc);
            if (distance < nearestDistance) {
                nearestDistance = distance;
                nearestMiner = miner;
            }
        }
        
        if (nearestMiner != null) {
            event.setCancelled(true);
            nearestMiner.addItem(itemName, item.getItemStack().getAmount());
        }
    }
    
    private static String getMaterialItemName(Material material) {
        FileConfiguration itemsConfig = Main.getInstance().getConfig();
        
        for (String key : itemsConfig.getConfigurationSection("Items").getKeys(false)) {
            String materialName = itemsConfig.getString("Items." + key + ".material");
            if (materialName != null && material.name().equalsIgnoreCase(materialName.replace(" ", "_").toUpperCase())) {
                return key;
            }
        }
        
        // Otomatik eşleştirme
        switch (material.name()) {
            case "IRON_INGOT": return "demir";
            case "GOLD_INGOT": return "altin";
            case "DIAMOND": return "elmas";
            case "EMERALD": return "zumrut";
            case "COAL": return "komur";
            case "REDSTONE": return "kiziltas";
            case "LAPIS_LAZULI": return "lapis";
            case "IRON_NUGGET": return "demir_parçasi";
            case "GOLD_NUGGET": return "altin_parçasi";
            case "OBSIDIAN": return "obsidyen";
            case "QUARTZ": return "kuvars";
            case "COPPER_INGOT": return "bakir";
            default: return null;
        }
    }
    
    public static Map<UUID, MinerNPC> getAllMiners() {
        return new HashMap<>(miners);
    }
    
    public static void shutdownAll() {
        for (MinerNPC miner : miners.values()) {
            miner.removeNPC();
        }
        miners.clear();
    }
}
