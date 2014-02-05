/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.stutiguias.yaps.commands;

import me.stutiguias.yaps.init.Yaps;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Daniel
 */
public class Off extends CommandHandler {

    public Off(Yaps plugin) {
        super(plugin);
    }

    @Override
    protected Boolean OnCommand(CommandSender sender, String[] args) {
        this.sender = sender;
        
        if(!isInvalid(sender, args)) return true;
        
        Player player = (Player)sender;
        
        Yaps.notProtectPlace.add(player.getName());
        
        SendMessage("&eNow place protected blocks is &4OFF");
        return true;
    }

    @Override
    protected Boolean isInvalid(CommandSender sender, String[] args) {
        if(!plugin.hasPermission(sender.getName(),"yaps.onoff")) {
            SendMessage("&4Not have permission");
            return false;
        }
        return true;
    }
    
}
