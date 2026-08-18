package com.madenci;

import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Villager;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class MinerNPC {
    
    private UUID ownerUUID;
    private Location location;
    private Villager villager;
    private Map<String, Integer> stock;
    private int level;
    private int capacity;
    
    public MinerNPC(UUID ownerUUID, Location location) {
        this.ownerUUID = ownerUUID;
        this.location = location;
        this.stock = new HashMap<>();
        this.level = 1;
        this.capacity = Main.getInstance().getConfig().getInt("MinerLevels.1.Capacity");
        
        spawnNPC();
    }
    
    private void spawnNPC() {
        villager = (Villager) location.getWorld().spawnEntity(location, EntityType.VILLAGER);
        villager.setCustomName("§6Madenci");
        villager.setCustomNameVisible(true);
        villager.setAI(false);
        villager.setInvulnerable(true);
    }
    
    public void addItem(String itemName, int amount) {
        int currentAmount = stock.getOrDefault(itemName, 0);
        int newAmount = currentAmount + amount;
        
        if (newAmount <= capacity) {
            stock.put(itemName, newAmount);
        } else {
            stock.put(itemName, capacity);
            
            // Stok doluysa oto satış
            if (Main.getInstance().getConfig().getBoolean("AddonSettings.autoSell.feature")) {
                sellAllItems();
            }
        }
    }
    
    public int getItemAmount(String itemName) {
        return stock.getOrDefault(itemName, 0);
    }
    
    public Map<String, Integer> getStock() {
        return new HashMap<>(stock);
    }
    
    public void removeItem(String itemName, int amount) {
        int currentAmount = stock.getOrDefault(itemName, 0);
        int newAmount = Math.max(0, currentAmount - amount);
        
        if (newAmount == 0) {
            stock.remove(itemName);
        } else {
            stock.put(itemName, newAmount);
        }
    }
    
    public void sellAllItems() {
        double totalMoney = 0;
        
        for (Map.Entry<String, Integer> entry : stock.entrySet()) {
            String itemName = entry.getKey();
            int amount = entry.getValue();
            
            double price = Main.getInstance().getConfig().getDouble("Items." + itemName + ".price");
            totalMoney += price * amount;
        }
        
        // Vergi kesintisi
        double taxRate = Main.getInstance().getConfig().getDouble("tax.taxRate") / 100.0;
        double taxAmount = totalMoney * taxRate;
        double finalMoney = totalMoney - taxAmount;
        
        // Para yatırma işlemi (Vault entegrasyonu gerekli)
        // EconomyManager.deposit(ownerUUID, finalMoney);
        
        stock.clear();
        
        if (Main.getInstance().getConfig().getBoolean("AddonSettings.autoSell.sendMessage")) {
            // Mesaj gönderme
        }
    }
    
    public void upgradeLevel() {
        if (level < 3) {
            level++;
            String levelPath = "MinerLevels." + level;
            capacity = Main.getInstance().getConfig().getInt(levelPath + ".Capacity");
        }
    }
    
    public void removeNPC() {
        if (villager != null && villager.isValid()) {
            villager.remove();
        }
    }
    
    public UUID getOwnerUUID() {
        return ownerUUID;
    }
    
    public Location getLocation() {
        return location;
    }
    
    public int getLevel() {
        return level;
    }
    
    public int getCapacity() {
        return capacity;
    }
    
    public int getCurrentStock() {
        int total = 0;
        for (int amount : stock.values()) {
            total += amount;
        }
        return total;
    }
}
