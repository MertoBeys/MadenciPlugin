package com.madenci;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.entity.Player;
import org.bukkit.ChatColor;

public class MadenciListener implements Listener {
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        
        // Otomatik kırma özelliği
        if (Main.getInstance().getConfig().getBoolean("AddonSettings.autoBreak.feature")) {
            if (MinerManager.hasMiner(player)) {
                MinerManager.handleAutoBreak(event);
            }
        }
    }
    
    @EventHandler
    public void onItemSpawn(ItemSpawnEvent event) {
        // Otomatik toplama özelliği
        if (Main.getInstance().getConfig().getBoolean("AddonSettings.autoCollect.feature")) {
            MinerManager.handleAutoCollect(event);
        }
    }
}
