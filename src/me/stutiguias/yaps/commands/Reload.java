/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.stutiguias.yaps.commands;

import me.stutiguias.yaps.init.Yaps;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Daniel
 */
public class Reload extends CommandHandler {

    public Reload(Yaps plugin) {
        super(plugin);
    }

    @Override
    protected Boolean OnCommand(CommandSender sender, String[] args) {
        this.sender = sender;
        
        if(!isInvalid(sender, args)) return true;
                
        SendMessage("&6Reloading!");
        plugin.OnReload();
        plugin.reloadConfig();
        SendMessage("&6Reload Done!");    
        return true;
    }

    @Override
    protected Boolean isInvalid(CommandSender sender, String[] args) {
        if(!plugin.hasPermission(sender.getName(),"yaps.reload")) {
            SendMessage("&4Not have permission");
            return false;
        }
        return true;
    }
    
}
