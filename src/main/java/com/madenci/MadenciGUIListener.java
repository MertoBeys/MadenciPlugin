package com.madenci;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

public class MadenciGUIListener implements Listener {
    
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!(event.getWhoClicked() instanceof Player)) return;
        
        Player player = (Player) event.getWhoClicked();
        String title = event.getView().getTitle();
        
        if (!title.equals(ChatColor.GOLD + "Madenci Menüsü") && 
            !title.equals(ChatColor.GOLD + "Madenci Stok") && 
            !title.equals(ChatColor.GOLD + "Seviye Bilgisi") &&
            !title.startsWith(ChatColor.GOLD + "İşlem: ") &&
            !title.equals(ChatColor.GOLD + "Yönetici Paneli") &&
            !title.equals(ChatColor.GOLD + "Sistem Ayarları")) {
            return;
        }
        
        event.setCancelled(true);
        
        ItemStack clicked = event.getCurrentItem();
        if (clicked == null || !clicked.hasItemMeta()) return;
        
        String itemName = clicked.getItemMeta().getDisplayName();
        
        // Ana Menü
        if (title.equals(ChatColor.GOLD + "Madenci Menüsü")) {
            handleMainMenuClick(player, itemName);
        }
        // Stok Menüsü
        else if (title.equals(ChatColor.GOLD + "Madenci Stok")) {
            handleStockMenuClick(player, itemName, event.getSlot(), event);
        }
        // Seviye Menüsü
        else if (title.equals(ChatColor.GOLD + "Seviye Bilgisi")) {
            handleLevelMenuClick(player, itemName);
        }
        // Item Menüsü
        else if (title.startsWith(ChatColor.GOLD + "İşlem: ")) {
            String targetItem = title.replace(ChatColor.GOLD + "İşlem: ", "");
            handleItemMenuClick(player, targetItem, itemName);
        }
        // Yönetici Paneli
        else if (title.equals(ChatColor.GOLD + "Yönetici Paneli")) {
            handleManagementPanelClick(player, itemName);
        }
        // Sistem Ayarları
        else if (title.equals(ChatColor.GOLD + "Sistem Ayarları")) {
            handleSystemsPanelClick(player, itemName);
        }
    }
    
    private void handleMainMenuClick(Player player, String itemName) {
        if (itemName.equals(ChatColor.GREEN + "Madenci Koy")) {
            if (!player.hasPermission("madenci.koy")) {
                player.sendMessage(ChatColor.RED + "Buna yetkin yok!");
                return;
            }
            MinerManager.createMiner(player);
            player.sendMessage(ChatColor.GREEN + "Madenci konuldu!");
            player.closeInventory();
        }
        else if (itemName.equals(ChatColor.YELLOW + "Yönetici Paneli")) {
            MadenciGUI.openManagementPanel(player);
        }
        else if (itemName.equals(ChatColor.YELLOW + "Stok Görüntüle")) {
            if (!player.hasPermission("madenci.stok")) {
                player.sendMessage(ChatColor.RED + "Buna yetkin yok!");
                return;
            }
            MadenciGUI.openStockMenu(player);
        }
        else if (itemName.equals(ChatColor.RED + "Kapat")) {
            player.closeInventory();
        }
    }
    
    private void handleManagementPanelClick(Player player, String itemName) {
        if (itemName.equals(ChatColor.GREEN + "Seviye Atla")) {
            MinerNPC miner = MinerManager.getMiner(player);
            if (miner == null) {
                player.sendMessage(ChatColor.RED + "Madencin yok!");
                return;
            }
            
            if (miner.getLevel() >= 3) {
                player.sendMessage(ChatColor.RED + "Zaten maksimum seviyedesin!");
                return;
            }
            
            miner.upgradeLevel();
            player.sendMessage(ChatColor.GREEN + "Seviye atladın! Yeni seviye: " + miner.getLevel());
            MadenciGUI.openManagementPanel(player);
        }
        else if (itemName.equals(ChatColor.YELLOW + "Satış Ayarı")) {
            player.sendMessage(ChatColor.YELLOW + "Satış ayarı değiştirildi!");
            MadenciGUI.openManagementPanel(player);
        }
        else if (itemName.equals(ChatColor.AQUA + "Yer Değiştirme")) {
            player.sendMessage(ChatColor.YELLOW + "Yer değiştirme modu aktif! Yeni konuma git!");
            player.closeInventory();
        }
        else if (itemName.equals(ChatColor.RED + "Sistemler")) {
            MadenciGUI.openSystemsPanel(player);
        }
        else if (itemName.equals(ChatColor.YELLOW + "Geri")) {
            MadenciGUI.openMainMenu(player);
        }
    }
    
    private void handleSystemsPanelClick(Player player, String itemName) {
        MinerNPC miner = MinerManager.getMiner(player);
        if (miner == null) {
            player.sendMessage(ChatColor.RED + "Madencin yok!");
            return;
        }

        if (itemName.equals(ChatColor.YELLOW + "Oto Satış")) {
            boolean newState = !miner.isAutoSell();
            miner.setAutoSell(newState);
            player.sendMessage(ChatColor.YELLOW + "Oto satış durumu: " + (newState ? ChatColor.GREEN + "Açıldı" : ChatColor.RED + "Kapatıldı"));
            MadenciGUI.openSystemsPanel(player);
        }
        else if (itemName.equals(ChatColor.AQUA + "Oto Toplama")) {
            boolean newState = !miner.isAutoCollect();
            miner.setAutoCollect(newState);
            player.sendMessage(ChatColor.YELLOW + "Oto toplama durumu: " + (newState ? ChatColor.GREEN + "Açıldı" : ChatColor.RED + "Kapatıldı"));
            MadenciGUI.openSystemsPanel(player);
        }
        else if (itemName.equals(ChatColor.GREEN + "Oto Kırma")) {
            boolean newState = !miner.isAutoBreak();
            miner.setAutoBreak(newState);
            player.sendMessage(ChatColor.YELLOW + "Oto kırma durumu: " + (newState ? ChatColor.GREEN + "Açıldı" : ChatColor.RED + "Kapatıldı"));
            MadenciGUI.openSystemsPanel(player);
        }
        else if (itemName.equals(ChatColor.YELLOW + "Geri")) {
            MadenciGUI.openManagementPanel(player);
        }
    }
    
    private void handleStockMenuClick(Player player, String itemName, int slot, InventoryClickEvent event) {
        if (itemName.equals(ChatColor.GREEN + "Tümünü Sat")) {
            if (!player.hasPermission("madenci.sat")) {
                player.sendMessage(ChatColor.RED + "Buna yetkin yok!");
                return;
            }
            MinerManager.sellItems(player);
            player.sendMessage(ChatColor.GREEN + "Tüm stok satıldı!");
            MadenciGUI.openStockMenu(player);
        }
        else if (itemName.equals(ChatColor.YELLOW + "Geri")) {
            MadenciGUI.openMainMenu(player);
        }
        else if (slot < 45 && !itemName.equals(ChatColor.GREEN + "Tümünü Sat") && !itemName.equals(ChatColor.YELLOW + "Geri")) {
            String targetItem = ChatColor.stripColor(itemName);
            MinerNPC miner = MinerManager.getMiner(player);
            if (miner == null) {
                player.sendMessage(ChatColor.RED + "Madencin yok!");
                return;
            }
            
            int currentAmount = miner.getItemAmount(targetItem);
            if (currentAmount == 0) {
                player.sendMessage(ChatColor.RED + "Bu item stokta yok!");
                return;
            }
            
            if (event.isLeftClick() && !event.isShiftClick()) {
                int takeAmount = Math.min(64, currentAmount);
                miner.removeItem(targetItem, takeAmount);
                giveItemToPlayer(player, targetItem, takeAmount);
                player.sendMessage(ChatColor.GREEN + String.valueOf(takeAmount) + " adet " + targetItem + " aldın!");
                MadenciGUI.openStockMenu(player);
            }
            else if (event.isRightClick() && !event.isShiftClick()) {
                miner.removeItem(targetItem, currentAmount);
                giveItemToPlayer(player, targetItem, currentAmount);
                player.sendMessage(ChatColor.GREEN + "Tüm " + targetItem + " aldın!");
                MadenciGUI.openStockMenu(player);
            }
            else if (event.isRightClick() && event.isShiftClick()) {
                miner.removeItem(targetItem, currentAmount);
                double price = Main.getInstance().getConfig().getDouble("Items." + targetItem + ".price", 0.0);
                double total = price * currentAmount;
                player.sendMessage(ChatColor.GREEN + "Tüm " + targetItem + " sattın! Kazanç: " + total);
                MadenciGUI.openStockMenu(player);
            }
        }
    }
    
    private void handleItemMenuClick(Player player, String targetItem, String buttonName) {
        MinerNPC miner = MinerManager.getMiner(player);
        if (miner == null) {
            player.sendMessage(ChatColor.RED + "Madencin yok!");
            return;
        }
        
        int currentAmount = miner.getItemAmount(targetItem);
        if (currentAmount == 0) {
            player.sendMessage(ChatColor.RED + "Bu item stokta yok!");
            MadenciGUI.openStockMenu(player);
            return;
        }
        
        if (buttonName.equals(ChatColor.GREEN + "1 Al")) {
            if (currentAmount >= 1) {
                miner.removeItem(targetItem, 1);
                giveItemToPlayer(player, targetItem, 1);
                player.sendMessage(ChatColor.GREEN + "1 adet " + targetItem + " aldın!");
                MadenciGUI.openItemMenu(player, targetItem);
            }
        }
        else if (buttonName.equals(ChatColor.GREEN + "64 Al")) {
            int takeAmount = Math.min(64, currentAmount);
            miner.removeItem(targetItem, takeAmount);
            giveItemToPlayer(player, targetItem, takeAmount);
            player.sendMessage(ChatColor.GREEN + String.valueOf(takeAmount) + " adet " + targetItem + " aldın!");
            MadenciGUI.openItemMenu(player, targetItem);
        }
        else if (buttonName.equals(ChatColor.GREEN + "Hepsini Al")) {
            miner.removeItem(targetItem, currentAmount);
            giveItemToPlayer(player, targetItem, currentAmount);
            player.sendMessage(ChatColor.GREEN + "Tüm " + targetItem + " aldın!");
            MadenciGUI.openStockMenu(player);
        }
        else if (buttonName.equals(ChatColor.RED + "1 Sat")) {
            if (currentAmount >= 1) {
                miner.removeItem(targetItem, 1);
                double price = Main.getInstance().getConfig().getDouble("Items." + targetItem + ".price", 0.0);
                player.sendMessage(ChatColor.GREEN + "1 adet " + targetItem + " sattın! Kazanç: " + price);
                MadenciGUI.openItemMenu(player, targetItem);
            }
        }
        else if (buttonName.equals(ChatColor.RED + "64 Sat")) {
            int sellAmount = Math.min(64, currentAmount);
            miner.removeItem(targetItem, sellAmount);
            double price = Main.getInstance().getConfig().getDouble("Items." + targetItem + ".price", 0.0);
            double total = price * sellAmount;
            player.sendMessage(ChatColor.GREEN + String.valueOf(sellAmount) + " adet " + targetItem + " sattın! Kazanç: " + total);
            MadenciGUI.openItemMenu(player, targetItem);
        }
        else if (buttonName.equals(ChatColor.RED + "Hepsini Sat")) {
            miner.removeItem(targetItem, currentAmount);
            double price = Main.getInstance().getConfig().getDouble("Items." + targetItem + ".price", 0.0);
            double total = price * currentAmount;
            player.sendMessage(ChatColor.GREEN + "Tüm " + targetItem + " sattın! Kazanç: " + total);
            MadenciGUI.openStockMenu(player);
        }
        else if (buttonName.equals(ChatColor.YELLOW + "Geri")) {
            MadenciGUI.openStockMenu(player);
        }
    }
    
    private void giveItemToPlayer(Player player, String itemName, int amount) {
        org.bukkit.Material material = getMaterialFromItemName(itemName);
        if (material != null) {
            org.bukkit.inventory.ItemStack item = new org.bukkit.inventory.ItemStack(material, amount);
            player.getInventory().addItem(item);
            player.updateInventory();
        }
    }
    
    private org.bukkit.Material getMaterialFromItemName(String itemName) {
        switch (itemName.toLowerCase()) {
            case "demir": return org.bukkit.Material.IRON_INGOT;
            case "altin": return org.bukkit.Material.GOLD_INGOT;
            case "elmas": return org.bukkit.Material.DIAMOND;
            case "zumrut": return org.bukkit.Material.EMERALD;
            case "komur": return org.bukkit.Material.COAL;
            case "kiziltas": return org.bukkit.Material.REDSTONE;
            case "lapis": return org.bukkit.Material.LAPIS_LAZULI;
            case "demir_parçasi": return org.bukkit.Material.IRON_NUGGET;
            case "altin_parçasi": return org.bukkit.Material.GOLD_NUGGET;
            case "obsidyen": return org.bukkit.Material.OBSIDIAN;
            case "kuvars": return org.bukkit.Material.QUARTZ;
            case "bakir": return org.bukkit.Material.COPPER_INGOT;
            default: return org.bukkit.Material.STONE;
        }
    }
    
    private void handleLevelMenuClick(Player player, String itemName) {
        if (itemName.equals(ChatColor.GREEN + "Seviye Atla")) {
            MinerNPC miner = MinerManager.getMiner(player);
            if (miner == null) {
                player.sendMessage(ChatColor.RED + "Madencin yok!");
                return;
            }
            
            if (miner.getLevel() >= 3) {
                player.sendMessage(ChatColor.RED + "Zaten maksimum seviyedesin!");
                return;
            }
            
            miner.upgradeLevel();
            player.sendMessage(ChatColor.GREEN + "Seviye atladın! Yeni seviye: " + miner.getLevel());
            MadenciGUI.openLevelMenu(player);
        }
        else if (itemName.equals(ChatColor.YELLOW + "Geri")) {
            MadenciGUI.openMainMenu(player);
        }
    }
}