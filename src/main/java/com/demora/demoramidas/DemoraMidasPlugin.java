package com.demora.demoramidas;

import com.demora.demoramidas.commands.DemCommand;
import com.demora.demoramidas.listeners.PlayerKillListener;
import org.bukkit.plugin.java.JavaPlugin;

public class DemoraMidasPlugin extends JavaPlugin {
    
    private static DemoraMidasPlugin instance;
    
    @Override
    public void onEnable() {
        instance = this;
        

        saveDefaultConfig();
        

        getCommand("dem").setExecutor(new DemCommand(this));
        

        getServer().getPluginManager().registerEvents(new PlayerKillListener(this), this);
        
        getLogger().info("DemoraMidas плагин успешно загружен!");
        getLogger().info("Автор: __DEMORA__");
    }
    
    @Override
    public void onDisable() {
        getLogger().info("DemoraMidas плагин отключен!");
    }
    
    public static DemoraMidasPlugin getInstance() {
        return instance;
    }
}
