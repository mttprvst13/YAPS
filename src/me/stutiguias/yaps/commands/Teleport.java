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
public class Teleport extends CommandHandler {

    public Teleport(Yaps plugin) {
        super(plugin);
    }

    @Override
    protected Boolean OnCommand(CommandSender sender, String[] args) {
        this.sender = sender;
        
        if(!isInvalid(sender, args)) return true;
                
        Player player = (Player)sender;
               
        if (args.length < 2) {
            SendMessage("&4Wrong arguments on command tp");
            return true;
        }
        
        String name = args[1];
        
        for(Area area:Yaps.Areas) {
            if(area.getName().equalsIgnoreCase(name)) {
                Location tplocation = area.getFirstSpot();
                tplocation.setY(tplocation.getY() + 1);
                player.teleport(tplocation);
            }
        }
        
        return true;
    }

    @Override
    protected Boolean isInvalid(CommandSender sender, String[] args) {
        if(!plugin.hasPermission(sender.getName(),"yaps.tp")) {
            SendMessage("&4Not have permission");
            return false;
        }
        return true;
    }
    
}
