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
            !title.equals(ChatColor.GOLD + "Seviye Bilgisi")) {
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
            handleStockMenuClick(player, itemName, event.getSlot());
        }
        // Seviye Menüsü
        else if (title.equals(ChatColor.GOLD + "Seviye Bilgisi")) {
            handleLevelMenuClick(player, itemName);
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
        else if (itemName.equals(ChatColor.RED + "Madenci Kaldır")) {
            if (!player.hasPermission("madenci.kaldir")) {
                player.sendMessage(ChatColor.RED + "Buna yetkin yok!");
                return;
            }
            MinerManager.removeMiner(player);
            player.sendMessage(ChatColor.GREEN + "Madenci kaldırıldı!");
            player.closeInventory();
        }
        else if (itemName.equals(ChatColor.YELLOW + "Stok Görüntüle")) {
            if (!player.hasPermission("madenci.stok")) {
                player.sendMessage(ChatColor.RED + "Buna yetkin yok!");
                return;
            }
            MadenciGUI.openStockMenu(player);
        }
        else if (itemName.equals(ChatColor.GOLD + "Stok Sat")) {
            if (!player.hasPermission("madenci.sat")) {
                player.sendMessage(ChatColor.RED + "Buna yetkin yok!");
                return;
            }
            MinerManager.sellItems(player);
            player.closeInventory();
        }
        else if (itemName.equals(ChatColor.AQUA + "Seviye Bilgisi")) {
            MadenciGUI.openLevelMenu(player);
        }
        else if (itemName.equals(ChatColor.RED + "Kapat")) {
            player.closeInventory();
        }
    }
    
    private void handleStockMenuClick(Player player, String itemName, int slot) {
        if (itemName.equals(ChatColor.GREEN + "Tümünü Sat")) {
            if (!player.hasPermission("madenci.sat")) {
                player.sendMessage(ChatColor.RED + "Buna yetkin yok!");
                return;
            }
            MinerManager.sellItems(player);
            player.sendMessage(ChatColor.GREEN + "Tüm stok satıldı!");
            MadenciGUI.openStockMenu(player); // Menüyü yenile
        }
        else if (itemName.equals(ChatColor.YELLOW + "Geri")) {
            MadenciGUI.openMainMenu(player);
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
            
            // Seviye atlama mantığı (para kontrolü vs.)
            // Para kontrolü (Vault entegrasyonu gerekli)
            // int nextRankMoney = Main.getInstance().getConfig()
            //     .getInt("MinerLevels." + miner.getLevel() + ".nextRankMoney");
            // if (EconomyManager.getBalance(player) < nextRankMoney) {
            //     player.sendMessage(ChatColor.RED + "Yeterli paran yok! Gerekli: " + nextRankMoney);
            //     return;
            // }
            
            miner.upgradeLevel();
            player.sendMessage(ChatColor.GREEN + "Seviye atladın! Yeni seviye: " + miner.getLevel());
            MadenciGUI.openLevelMenu(player);
        }
        else if (itemName.equals(ChatColor.YELLOW + "Geri")) {
            MadenciGUI.openMainMenu(player);
        }
    }
}
