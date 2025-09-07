package com.demora.demoramidas.listeners;

import com.demora.demoramidas.DemoraMidasPlugin;
import com.demora.demoramidas.utils.MidasSwordUtils;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerKillListener implements Listener {
    
    private final DemoraMidasPlugin plugin;
    
    public PlayerKillListener(DemoraMidasPlugin plugin) {
        this.plugin = plugin;
    }
    
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();
        
        if (killer == null) {
            return;
        }
        
        ItemStack weapon = killer.getInventory().getItemInMainHand();
        

        if (!MidasSwordUtils.isMidasSword(weapon, plugin)) {
            return;
        }
        

        MidasSwordUtils.increaseSharpness(weapon, plugin);
        

        createKillEffects(victim, killer);
        

        for (int i = 0; i < 5; i++) {
            victim.getWorld().dropItemNaturally(victim.getLocation(), new ItemStack(Material.GOLD_BLOCK));
        }
        

        killer.sendMessage(ChatColor.GOLD + "§lМеч Мидаса усилился! Острота: " + 
            MidasSwordUtils.getSharpnessLevel(weapon, plugin));
    }
    
    private void createKillEffects(Player victim, Player killer) {
        Location location = victim.getLocation();
        World world = victim.getWorld();
        

        world.createExplosion(location, 0.0f, false, false);
        

        for (int i = 0; i < 50; i++) {
            double x = location.getX() + (Math.random() - 0.5) * 4;
            double y = location.getY() + Math.random() * 3;
            double z = location.getZ() + (Math.random() - 0.5) * 4;
            
            world.spawnParticle(Particle.HAPPY_VILLAGER, x, y, z, 1, 0, 0, 0, 0);
        }
        

        for (int i = 0; i < 30; i++) {
            double x = location.getX() + (Math.random() - 0.5) * 6;
            double y = location.getY() + Math.random() * 4;
            double z = location.getZ() + (Math.random() - 0.5) * 6;
            
            world.spawnParticle(Particle.ENCHANT, x, y, z, 1, 0, 0, 0, 0);
        }
        

        world.playSound(location, Sound.ENTITY_PLAYER_LEVELUP, 1.0f, 1.0f);
        world.playSound(location, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f, 0.5f);
        world.playSound(location, Sound.BLOCK_ANVIL_LAND, 0.5f, 1.5f);
        
        world.strikeLightningEffect(location);
    }
}