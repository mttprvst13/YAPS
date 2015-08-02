/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.stutiguias.yaps.commands;

import me.stutiguias.yaps.init.Yaps;
import me.stutiguias.yaps.model.Area;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Daniel
 */
public class SetExit extends CommandHandler {

    public SetExit(Yaps plugin) {
        super(plugin);
    }

    @Override
    protected Boolean OnCommand(CommandSender sender, String[] args) {
        this.sender = sender;
        
        if(!isInvalid(sender, args)) return true;
                
        Player player = (Player)sender;
        
        Location location = player.getLocation();
        
        if (args.length < 1) {
            SendMessage("&4Wrong arguments");
            return true;
        }   
 
        Area area = plugin.getArea(args[1]);
        
        if(area == null) {
         SendMessage("&4Area name not found.");
         return true;
        }
        
        int index = plugin.getAreaIndex(area.getFirstSpot());

        Yaps.Areas.get(index).setExit(location);
        Yaps.db.SetExit(Yaps.Areas.get(index));
        SendMessage("&6Exit Point for Area %s setup successful.", new Object[] { area.getName() });
        return true;
    }

    @Override
    protected Boolean isInvalid(CommandSender sender, String[] args) {
        if(!plugin.hasPermission(sender.getName(),"yaps.setexit")) {
            SendMessage("&4Not have permission");
            return false;
        }
        return true;
    }
    
}
