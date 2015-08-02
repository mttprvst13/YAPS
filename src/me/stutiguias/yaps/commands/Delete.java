/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.stutiguias.yaps.commands;

import me.stutiguias.yaps.init.Yaps;
import me.stutiguias.yaps.model.Area;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Daniel
 */
public class Delete extends CommandHandler {

    public Delete(Yaps plugin) {
        super(plugin);
    }

    @Override
    protected Boolean OnCommand(CommandSender sender, String[] args) {
        this.sender = sender;
                
        if(!isInvalid(sender, args)) return true;
        
        String name = args[1];
        
        Area area = plugin.getArea(name);
        
        if(area == null) {
         SendMessage("&4Area name not found.");
         return true;
        }
        
        Yaps.Areas.remove(area);
        Yaps.db.Delete(area);
        return true;
    }

    @Override
    protected Boolean isInvalid(CommandSender sender, String[] args) {
        
        if(!plugin.hasPermission(sender.getName(),"yaps.delete")) {
            SendMessage("&4Not have permission");
            return false;
        }
        
        if (args.length < 1) {
            SendMessage("&4Wrong arguments");
            return false;
        }   
        return true;
    }
    
}
