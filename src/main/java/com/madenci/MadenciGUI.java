package com.madenci;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class MadenciGUI {
    
    private static final String GUI_TITLE = ChatColor.GOLD + "Madenci Menüsü";
    
    public static void openMainMenu(Player player) {
        Inventory inv = Bukkit.createInventory(null, 27, GUI_TITLE);
        
        // Madenci Koy
        ItemStack placeMiner = createItem(Material.DIAMOND_PICKAXE, 
            ChatColor.GREEN + "Madenci Koy", 
            ChatColor.GRAY + "Madenci NPC koyar");
        inv.setItem(11, placeMiner);
        
        // Madenci Kaldır
        ItemStack removeMiner = createItem(Material.BARRIER, 
            ChatColor.RED + "Madenci Kaldır", 
            ChatColor.GRAY + "Madenci NPC kaldırır");
        inv.setItem(13, removeMiner);
        
        // Stok Görüntüle
        ItemStack viewStock = createItem(Material.CHEST, 
            ChatColor.YELLOW + "Stok Görüntüle", 
            ChatColor.GRAY + "Madenci stokunu gösterir");
        inv.setItem(15, viewStock);
        
        // Stok Sat
        ItemStack sellStock = createItem(Material.GOLD_INGOT, 
            ChatColor.GOLD + "Stok Sat", 
            ChatColor.GRAY + "Stoktaki itemleri satar");
        inv.setItem(20, sellStock);
        
        // Seviye Bilgisi
        ItemStack levelInfo = createItem(Material.EXPERIENCE_BOTTLE, 
            ChatColor.AQUA + "Seviye Bilgisi", 
            ChatColor.GRAY + "Madenci seviyesini gösterir");
        inv.setItem(22, levelInfo);
        
        // Kapat
        ItemStack close = createItem(Material.RED_STAINED_GLASS_PANE, 
            ChatColor.RED + "Kapat", 
            ChatColor.GRAY + "Menüyü kapatır");
        inv.setItem(26, close);
        
        player.openInventory(inv);
    }
    
    public static void openStockMenu(Player player) {
        MinerNPC miner = MinerManager.getMiner(player);
        if (miner == null) {
            player.sendMessage(ChatColor.RED + "Madencin yok!");
            return;
        }
        
        Inventory inv = Bukkit.createInventory(null, 54, ChatColor.GOLD + "Madenci Stok");
        
        java.util.Map<String, Integer> stock = miner.getStock();
        int slot = 0;
        
        for (java.util.Map.Entry<String, Integer> entry : stock.entrySet()) {
            String itemName = entry.getKey();
            int amount = entry.getValue();
            
            Material material = getMaterialFromItemName(itemName);
            ItemStack item = new ItemStack(material, Math.min(amount, 64));
            ItemMeta meta = item.getItemMeta();
            
            if (meta != null) {
                meta.setDisplayName(ChatColor.WHITE + itemName);
                List<String> lore = new ArrayList<>();
                lore.add(ChatColor.GRAY + "Miktar: " + ChatColor.GREEN + amount);
                lore.add(ChatColor.GRAY + "Fiyat: " + ChatColor.YELLOW + getPrice(itemName));
                meta.setLore(lore);
                item.setItemMeta(meta);
            }
            
            inv.setItem(slot, item);
            slot++;
            if (slot >= 45) break;
        }
        
        // Sat butonu
        ItemStack sellButton = createItem(Material.GOLD_INGOT, 
            ChatColor.GREEN + "Tümünü Sat", 
            ChatColor.GRAY + "Stoktaki tüm itemleri satar");
        inv.setItem(49, sellButton);
        
        // Geri butonu
        ItemStack backButton = createItem(Material.ARROW, 
            ChatColor.YELLOW + "Geri", 
            ChatColor.GRAY + "Ana menüye dön");
        inv.setItem(53, backButton);
        
        player.openInventory(inv);
    }
    
    public static void openLevelMenu(Player player) {
        MinerNPC miner = MinerManager.getMiner(player);
        if (miner == null) {
            player.sendMessage(ChatColor.RED + "Madencin yok!");
            return;
        }
        
        Inventory inv = Bukkit.createInventory(null, 27, ChatColor.GOLD + "Seviye Bilgisi");
        
        // Mevcut Seviye
        ItemStack currentLevel = createItem(Material.EXPERIENCE_BOTTLE, 
            ChatColor.AQUA + "Mevcut Seviye: " + miner.getLevel(), 
            ChatColor.GRAY + "Kapasite: " + ChatColor.GREEN + miner.getCapacity(),
            ChatColor.GRAY + "Mevcut Stok: " + ChatColor.YELLOW + miner.getCurrentStock());
        inv.setItem(13, currentLevel);
        
        // Seviye Atlama
        ItemStack upgrade = createItem(Material.DIAMOND, 
            ChatColor.GREEN + "Seviye Atla", 
            ChatColor.GRAY + "Sonraki seviyeye geç");
        inv.setItem(15, upgrade);
        
        // Geri butonu
        ItemStack backButton = createItem(Material.ARROW, 
            ChatColor.YELLOW + "Geri", 
            ChatColor.GRAY + "Ana menüye dön");
        inv.setItem(26, backButton);
        
        player.openInventory(inv);
    }
    
    private static ItemStack createItem(Material material, String name, String... lore) {
        ItemStack item = new ItemStack(material);
        ItemMeta meta = item.getItemMeta();
        
        if (meta != null) {
            meta.setDisplayName(name);
            if (lore.length > 0) {
                List<String> loreList = new ArrayList<>();
                for (String line : lore) {
                    loreList.add(line);
                }
                meta.setLore(loreList);
            }
            item.setItemMeta(meta);
        }
        
        return item;
    }
    
    private static Material getMaterialFromItemName(String itemName) {
        switch (itemName.toLowerCase()) {
            case "demir": return Material.IRON_INGOT;
            case "altin": return Material.GOLD_INGOT;
            case "elmas": return Material.DIAMOND;
            case "zumrut": return Material.EMERALD;
            case "komur": return Material.COAL;
            case "kiziltas": return Material.REDSTONE;
            case "lapis": return Material.LAPIS_LAZULI;
            case "demir_parçasi": return Material.IRON_NUGGET;
            case "altin_parçasi": return Material.GOLD_NUGGET;
            case "obsidyen": return Material.OBSIDIAN;
            case "kuvars": return Material.QUARTZ;
            case "bakir": return Material.COPPER_INGOT;
            default: return Material.STONE;
        }
    }
    
    private static double getPrice(String itemName) {
        return Main.getInstance().getConfig().getDouble("Items." + itemName + ".price", 0.0);
    }
}
