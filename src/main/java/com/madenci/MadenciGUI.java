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
        MinerNPC miner = MinerManager.getMiner(player);
        
        Inventory inv = Bukkit.createInventory(null, 27, GUI_TITLE);
        
        if (miner != null) {
            // Yönetici Paneli
            ItemStack managementPanel = createItem(Material.PLAYER_HEAD, 
                ChatColor.YELLOW + "Yönetici Paneli", 
                ChatColor.GRAY + "Yönetici Paneline eriş",
                "",
                ChatColor.GOLD + "Madenci İstatistikleri:",
                ChatColor.GRAY + " ▪ Seviye: " + ChatColor.GREEN + miner.getLevel(),
                ChatColor.GRAY + " ▪ Kapasite: " + ChatColor.GREEN + miner.getCapacity(),
                ChatColor.GRAY + " ▪ Mevcut Stok: " + ChatColor.GREEN + miner.getCurrentStock(),
                "",
                ChatColor.YELLOW + "Yönetici Paneli için TIKLA!");
            inv.setItem(13, managementPanel);
            
            // Stok Görüntüle
            ItemStack viewStock = createItem(Material.CHEST, 
                ChatColor.YELLOW + "Stok Görüntüle", 
                ChatColor.GRAY + "Madenci stokunu gösterir");
            inv.setItem(15, viewStock);
        } else {
            // Madenci Koy
            ItemStack placeMiner = createItem(Material.DIAMOND_PICKAXE, 
                ChatColor.GREEN + "Madenci Koy", 
                ChatColor.GRAY + "Madenci NPC koyar");
            inv.setItem(13, placeMiner);
        }
        
        // Kapat
        ItemStack close = createItem(Material.RED_STAINED_GLASS_PANE, 
            ChatColor.RED + "Kapat", 
            ChatColor.GRAY + "Menüyü kapatır");
        inv.setItem(26, close);
        
        player.openInventory(inv);
    }
    
    public static void openManagementPanel(Player player) {
        MinerNPC miner = MinerManager.getMiner(player);
        if (miner == null) {
            player.sendMessage(ChatColor.RED + "Madencin yok!");
            return;
        }
        
        Inventory inv = Bukkit.createInventory(null, 45, ChatColor.GOLD + "Yönetici Paneli");
        
        // Seviye Atlama
        ItemStack upgrade = createItem(Material.DIAMOND, 
            ChatColor.GREEN + "Seviye Atla", 
            ChatColor.GRAY + "Madenci seviyesini yükselt",
            "",
            ChatColor.GRAY + "Mevcut Seviye: " + ChatColor.YELLOW + miner.getLevel(),
            ChatColor.GRAY + "Mevcut Kapasite: " + ChatColor.YELLOW + miner.getCapacity(),
            "",
            ChatColor.YELLOW + "Yükseltmek için TIKLA!");
        inv.setItem(12, upgrade);
        
        // Satış Ayarı
        ItemStack sellMode = createItem(Material.GOLD_INGOT, 
            ChatColor.YELLOW + "Satış Ayarı", 
            ChatColor.GRAY + "Satışların kime gideceğini belirle",
            "",
            ChatColor.GRAY + "Mevcut: " + ChatColor.YELLOW + "Oyuncu",
            "",
            ChatColor.YELLOW + "Değiştirmek için TIKLA!");
        inv.setItem(14, sellMode);
        
        // Yer Değiştirme
        ItemStack relocate = createItem(Material.COMPASS, 
            ChatColor.AQUA + "Yer Değiştirme", 
            ChatColor.GRAY + "Madencinin yerini değiştir",
            "",
            ChatColor.YELLOW + "Yer değiştirmek için TIKLA!");
        inv.setItem(16, relocate);
        
        // Sistemler
        ItemStack systems = createItem(Material.REDSTONE, 
            ChatColor.RED + "Sistemler", 
            ChatColor.GRAY + "Sistem ayarlarını aç",
            "",
            ChatColor.YELLOW + "Sistemler için TIKLA!");
        inv.setItem(22, systems);
        
        // Geri butonu
        ItemStack backButton = createItem(Material.ARROW, 
            ChatColor.YELLOW + "Geri", 
            ChatColor.GRAY + "Ana menüye dön");
        inv.setItem(40, backButton);
        
        player.openInventory(inv);
    }
    
    public static void openSystemsPanel(Player player) {
        MinerNPC miner = MinerManager.getMiner(player);
        if (miner == null) {
            player.sendMessage(ChatColor.RED + "Madencin yok!");
            return;
        }

        // MinerNPC sınıfınızda isAutoSell(), isAutoCollect(), isAutoBreak() gibi metodlar olduğunu varsayıyoruz.
        boolean autoSellStatus = miner.isAutoSell();
        boolean autoCollectStatus = miner.isAutoCollect();
        boolean autoBreakStatus = miner.isAutoBreak();
        
        Inventory inv = Bukkit.createInventory(null, 45, ChatColor.GOLD + "Sistem Ayarları");
        
        // Oto Satış
        ItemStack autoSell = createItem(Material.GOLD_BLOCK, 
            ChatColor.YELLOW + "Oto Satış", 
            ChatColor.GRAY + "Stok dolduğunda otomatik satış",
            "",
            ChatColor.GRAY + "Durum: " + (autoSellStatus ? ChatColor.GREEN + "Açık" : ChatColor.RED + "Kapalı"),
            "",
            ChatColor.YELLOW + "Değiştirmek için TIKLA!");
        inv.setItem(12, autoSell);
        
        // Oto Toplama
        ItemStack autoCollect = createItem(Material.HOPPER, 
            ChatColor.AQUA + "Oto Toplama", 
            ChatColor.GRAY + "Düşen itemleri otomatik toplar",
            "",
            ChatColor.GRAY + "Durum: " + (autoCollectStatus ? ChatColor.GREEN + "Açık" : ChatColor.RED + "Kapalı"),
            "",
            ChatColor.YELLOW + "Değiştirmek için TIKLA!");
        inv.setItem(14, autoCollect);
        
        // Oto Kırma
        ItemStack autoBreak = createItem(Material.DIAMOND_PICKAXE, 
            ChatColor.GREEN + "Oto Kırma", 
            ChatColor.GRAY + "Maden bloklarını otomatik kırar",
            "",
            ChatColor.GRAY + "Durum: " + (autoBreakStatus ? ChatColor.GREEN + "Açık" : ChatColor.RED + "Kapalı"),
            "",
            ChatColor.YELLOW + "Değiştirmek için TIKLA!");
        inv.setItem(16, autoBreak);
        
        // Geri butonu
        ItemStack backButton = createItem(Material.ARROW, 
            ChatColor.YELLOW + "Geri", 
            ChatColor.GRAY + "Yönetici paneline dön");
        inv.setItem(40, backButton);
        
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
                lore.add("");
                lore.add(ChatColor.GRAY + "Mevcut stok: " + ChatColor.WHITE + amount + ChatColor.GRAY + "/" + ChatColor.RED + miner.getCapacity());
                lore.add(ChatColor.GRAY + "Birim fiyatı: " + ChatColor.WHITE + getPrice(itemName));
                lore.add(ChatColor.GRAY + "Doluluk Oranı: " + ChatColor.WHITE + getProgressPercent(amount, miner.getCapacity()) + "%");
                lore.add(ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + "  [" + getProgressBar(amount, miner.getCapacity()) + ChatColor.DARK_GRAY.toString() + ChatColor.BOLD + "]");
                lore.add("");
                lore.add(ChatColor.GRAY + "64 adet almak için " + ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + "Sol Tık" + ChatColor.DARK_GRAY + "]");
                lore.add(ChatColor.GRAY + "Hepsini almak için " + ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + "Sağ Tık" + ChatColor.DARK_GRAY + "]");
                lore.add(ChatColor.GRAY + "Hepsini satmak için " + ChatColor.DARK_GRAY + "[" + ChatColor.YELLOW + "Shift + Sağ Tık" + ChatColor.DARK_GRAY + "]");
                lore.add("");
                lore.add(ChatColor.DARK_RED + "NOT: " + ChatColor.RED + "Hepsini satma işleminde");
                lore.add(ChatColor.RED + "madenciye vergi ödersin!");
                meta.setLore(lore);
                item.setItemMeta(meta);
            }
            
            inv.setItem(slot, item);
            slot++;
            if (slot >= 45) break;
        }
        
        // Tümünü Sat butonu
        ItemStack sellAllButton = createItem(Material.GOLD_INGOT, 
            ChatColor.GREEN + "Tümünü Sat", 
            ChatColor.GRAY + "Stoktaki tüm itemleri satar");
        inv.setItem(49, sellAllButton);
        
        // Geri butonu
        ItemStack backButton = createItem(Material.ARROW, 
            ChatColor.YELLOW + "Geri", 
            ChatColor.GRAY + "Ana menüye dön");
        inv.setItem(53, backButton);
        
        player.openInventory(inv);
    }
    
    public static void openItemMenu(Player player, String itemName) {
        MinerNPC miner = MinerManager.getMiner(player);
        if (miner == null) {
            player.sendMessage(ChatColor.RED + "Madencin yok!");
            return;
        }
        
        int amount = miner.getItemAmount(itemName);
        if (amount == 0) {
            player.sendMessage(ChatColor.RED + "Bu item stokta yok!");
            return;
        }
        
        Inventory inv = Bukkit.createInventory(null, 45, ChatColor.GOLD + "İşlem: " + itemName);
        
        Material material = getMaterialFromItemName(itemName);
        
        // Item bilgisi
        ItemStack infoItem = createItem(material, 
            ChatColor.WHITE + itemName, 
            ChatColor.GRAY + "Stok: " + ChatColor.GREEN + amount,
            ChatColor.GRAY + "Fiyat: " + ChatColor.YELLOW + getPrice(itemName) + " birim");
        inv.setItem(13, infoItem);
        
        // Tekli Al
        ItemStack takeOne = createItem(Material.GREEN_STAINED_GLASS_PANE, 
            ChatColor.GREEN + "1 Al", 
            ChatColor.GRAY + "1 adet item al");
        inv.setItem(19, takeOne);
        
        // 64'lü Al
        ItemStack take64 = createItem(Material.LIME_STAINED_GLASS_PANE, 
            ChatColor.GREEN + "64 Al", 
            ChatColor.GRAY + "64 adet item al");
        inv.setItem(20, take64);
        
        // Hepsini Al
        ItemStack takeAll = createItem(Material.GREEN_WOOL, 
            ChatColor.GREEN + "Hepsini Al", 
            ChatColor.GRAY + "Tüm itemleri al");
        inv.setItem(21, takeAll);
        
        // Tekli Sat
        ItemStack sellOne = createItem(Material.RED_STAINED_GLASS_PANE, 
            ChatColor.RED + "1 Sat", 
            ChatColor.GRAY + "1 adet item sat");
        inv.setItem(23, sellOne);
        
        // 64'lü Sat
        ItemStack sell64 = createItem(Material.RED_WOOL, 
            ChatColor.RED + "64 Sat", 
            ChatColor.GRAY + "64 adet item sat");
        inv.setItem(24, sell64);
        
        // Hepsini Sat
        ItemStack sellAll = createItem(Material.REDSTONE_BLOCK, 
            ChatColor.RED + "Hepsini Sat", 
            ChatColor.GRAY + "Tüm itemleri sat");
        inv.setItem(25, sellAll);
        
        // Geri butonu
        ItemStack backButton = createItem(Material.ARROW, 
            ChatColor.YELLOW + "Geri", 
            ChatColor.GRAY + "Stok menüsüne dön");
        inv.setItem(40, backButton);
        
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
    
    private static String getProgressPercent(int current, int max) {
        if (max == 0) return "0";
        double percent = (double) current / max * 100;
        return String.format("%.1f", percent);
    }
    
    private static String getProgressBar(int current, int max) {
        if (max == 0) return ChatColor.RED + "▪▪▪▪▪▪▪▪▪▪▪▪▪▪▪▪▪▪▪▪▪▪▪▪▪";
        double percent = (double) current / max;
        int filled = (int) (percent * 20);
        StringBuilder bar = new StringBuilder();
        for (int i = 0; i < 20; i++) {
            if (i < filled) {
                bar.append(ChatColor.GREEN).append("▪");
            } else {
                bar.append(ChatColor.RED).append("▪");
            }
        }
        return bar.toString();
    }
}