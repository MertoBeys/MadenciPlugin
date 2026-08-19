package com.madenci;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class NPCInteractionListener implements Listener {
    
    @EventHandler
    public void onPlayerInteractEntity(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof org.bukkit.entity.Entity)) {
            return;
        }
        
        Player player = event.getPlayer();
        org.bukkit.entity.Entity entity = event.getRightClicked();
        
        // NPC kontrolü - armor stand veya villager olabilir
        if (entity instanceof org.bukkit.entity.ArmorStand || entity instanceof org.bukkit.entity.Villager) {
            // Bu bir madenci NPC mi kontrol et
            MinerNPC miner = MinerManager.getMiner(player);
            if (miner != null && miner.getNPC() != null && miner.getNPC().equals(entity)) {
                event.setCancelled(true);
                MadenciGUI.openMainMenu(player);
            }
        }
    }
}
