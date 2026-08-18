package com.madenci;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

public class MadenciCommand implements CommandExecutor {
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "Bu komut sadece oyuncular için!");
            return true;
        }
        
        Player player = (Player) sender;
        
        if (args.length == 0) {
            MadenciGUI.openMainMenu(player);
            return true;
        }
        
        switch (args[0].toLowerCase()) {
            case "koy":
                if (!player.hasPermission("madenci.koy")) {
                    player.sendMessage(ChatColor.RED + "Buna yetkin yok!");
                    return true;
                }
                MinerManager.createMiner(player);
                player.sendMessage(ChatColor.GREEN + "Madenci konuldu!");
                break;
                
            case "kaldir":
                if (!player.hasPermission("madenci.kaldir")) {
                    player.sendMessage(ChatColor.RED + "Buna yetkin yok!");
                    return true;
                }
                MinerManager.removeMiner(player);
                player.sendMessage(ChatColor.GREEN + "Madenci kaldırıldı!");
                break;
                
            case "stok":
                if (!player.hasPermission("madenci.stok")) {
                    player.sendMessage(ChatColor.RED + "Buna yetkin yok!");
                    return true;
                }
                MinerManager.showStock(player);
                break;
                
            case "sat":
                if (!player.hasPermission("madenci.sat")) {
                    player.sendMessage(ChatColor.RED + "Buna yetkin yok!");
                    return true;
                }
                MinerManager.sellItems(player);
                break;
                
            case "reload":
                if (!player.hasPermission("madenci.reload")) {
                    player.sendMessage(ChatColor.RED + "Buna yetkin yok!");
                    return true;
                }
                Main.getInstance().reloadConfig();
                player.sendMessage(ChatColor.GREEN + "Config yeniden yüklendi!");
                break;
                
            default:
                sendHelp(player);
        }
        
        return true;
    }
    
    private void sendHelp(Player player) {
        player.sendMessage(ChatColor.GOLD + "=== Madenci Komutları ===");
        player.sendMessage(ChatColor.YELLOW + "/madenci koy" + ChatColor.WHITE + " - Madenci koy");
        player.sendMessage(ChatColor.YELLOW + "/madenci kaldir" + ChatColor.WHITE + " - Madenci kaldır");
        player.sendMessage(ChatColor.YELLOW + "/madenci stok" + ChatColor.WHITE + " - Stok görüntüle");
        player.sendMessage(ChatColor.YELLOW + "/madenci sat" + ChatColor.WHITE + " - Stok sat");
        player.sendMessage(ChatColor.YELLOW + "/madenci reload" + ChatColor.WHITE + " - Config yeniden yükle");
    }
}
