package com.demora.demoramidas.commands;

import com.demora.demoramidas.DemoraMidasPlugin;
import com.demora.demoramidas.utils.MidasSwordUtils;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DemCommand implements CommandExecutor {
    
    private final DemoraMidasPlugin plugin;
    
    public DemCommand(DemoraMidasPlugin plugin) {
        this.plugin = plugin;
    }
    
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!sender.hasPermission("demoramidas.admin")) {
            sender.sendMessage(ChatColor.RED + "У вас нет прав для использования этой команды!");
            return true;
        }
        

        if (args.length != 3 || !args[0].equalsIgnoreCase("give") || !args[1].equalsIgnoreCase("midas")) {
            sender.sendMessage(ChatColor.RED + "Использование: /dem give midas <игрок>");
            return true;
        }
        

        Player targetPlayer = Bukkit.getPlayer(args[2]);
        if (targetPlayer == null) {
            sender.sendMessage(ChatColor.RED + "Игрок " + ChatColor.YELLOW + args[2] + ChatColor.RED + " не найден!");
            return true;
        }
        

        MidasSwordUtils.giveMidasSword(targetPlayer, plugin);
        
 
        sender.sendMessage(ChatColor.GOLD + "Меч Мидаса был выдан игроку " + ChatColor.YELLOW + targetPlayer.getName() + ChatColor.GOLD + "!");
        targetPlayer.sendMessage(ChatColor.GOLD + "Вы получили Меч Мидаса!");
        
        return true;
    }
}