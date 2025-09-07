package com.demora.demoramidas.utils;

import com.demora.demoramidas.DemoraMidasPlugin;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

import java.util.Arrays;
import java.util.List;

public class MidasSwordUtils {
    
    private static final String MIDAS_SWORD_KEY = "midas_sword";
    private static final String SHARPNESS_LEVEL_KEY = "sharpness_level";
    
    public static void giveMidasSword(Player player, DemoraMidasPlugin plugin) {
        ItemStack sword = createMidasSword(plugin);
        player.getInventory().addItem(sword);
    }
    
    public static ItemStack createMidasSword(DemoraMidasPlugin plugin) {
        ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta meta = sword.getItemMeta();
        
        if (meta != null) {

            meta.setDisplayName(ChatColor.translateAlternateColorCodes('&', "&6&lМеч Мидаса"));
            

            

            meta.addEnchant(Enchantment.UNBREAKING, 200, true);
            

            int initialSharpness = plugin.getConfig().getInt("midas-sword.initial-sharpness", 1);
            meta.addEnchant(Enchantment.SHARPNESS, initialSharpness, true);
            

            meta.setCustomModelData(1);
            

            NamespacedKey midasKey = new NamespacedKey(plugin, MIDAS_SWORD_KEY);
            meta.getPersistentDataContainer().set(midasKey, PersistentDataType.BOOLEAN, true);
            
            NamespacedKey sharpnessKey = new NamespacedKey(plugin, SHARPNESS_LEVEL_KEY);
            meta.getPersistentDataContainer().set(sharpnessKey, PersistentDataType.INTEGER, initialSharpness);
            
            sword.setItemMeta(meta);
        }
        
        return sword;
    }
    
    public static boolean isMidasSword(ItemStack item, DemoraMidasPlugin plugin) {
        if (item == null || item.getType() != Material.DIAMOND_SWORD) {
            return false;
        }
        
        ItemMeta meta = item.getItemMeta();
        if (meta == null) {
            return false;
        }
        
        NamespacedKey key = new NamespacedKey(plugin, MIDAS_SWORD_KEY);
        return meta.getPersistentDataContainer().has(key, PersistentDataType.BOOLEAN);
    }
    
    public static int getSharpnessLevel(ItemStack sword, DemoraMidasPlugin plugin) {
        if (!isMidasSword(sword, plugin)) {
            return 0;
        }
        
        ItemMeta meta = sword.getItemMeta();
        if (meta == null) {
            return 0;
        }
        
        NamespacedKey key = new NamespacedKey(plugin, SHARPNESS_LEVEL_KEY);
        return meta.getPersistentDataContainer().getOrDefault(key, PersistentDataType.INTEGER, 0);
    }
    
    public static void increaseSharpness(ItemStack sword, DemoraMidasPlugin plugin) {
        if (!isMidasSword(sword, plugin)) {
            return;
        }
        
        ItemMeta meta = sword.getItemMeta();
        if (meta == null) {
            return;
        }
        
        int currentSharpness = getSharpnessLevel(sword, plugin);
        int maxSharpness = plugin.getConfig().getInt("midas-sword.max-sharpness", 10);
        
        if (currentSharpness >= maxSharpness) {
            return;
        }
        
        int newSharpness = currentSharpness + 1;
        

        meta.removeEnchant(Enchantment.SHARPNESS);
        meta.addEnchant(Enchantment.SHARPNESS, newSharpness, true);
        

        NamespacedKey key = new NamespacedKey(plugin, SHARPNESS_LEVEL_KEY);
        meta.getPersistentDataContainer().set(key, PersistentDataType.INTEGER, newSharpness);

        
        sword.setItemMeta(meta);
    }
}
