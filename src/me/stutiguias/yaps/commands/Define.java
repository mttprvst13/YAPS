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
public class Define extends CommandHandler {

    public Define(Yaps plugin) {
        super(plugin);
    }

    @Override
    protected Boolean OnCommand(CommandSender sender, String[] args) {
        this.sender = sender;
       
        if(!isInvalid(sender, args)) return true;
        
        String name = args[1];
        
        Area area = plugin.getArea(name);
        
        String owner;
        
        if (args.length == 3) {
            owner = args[2];
        }else{
            owner = ((Player)sender).getName();
        }
        
        if(area != null) {
         SendMessage("&4This name is already in use.");
         return true;
        }
        
        String flag = "";
        
        if(Yaps.config.AllowMoveInside) flag += "AllowMoveInside";
        
        Location FirstSpot = Yaps.AreaCreating.get((Player)sender).getFirstSpot();
        Location SecondSpot = Yaps.AreaCreating.get((Player)sender).getSecondSpot();

        Yaps.AreaCreating.remove((Player)sender);
        area = new Area(FirstSpot,SecondSpot,name,owner,flag);
        
        if(Yaps.db.InsertArea(area)){
            Yaps.Areas.add(area);
            SendMessage("&6Area %s successfully define",new Object[]{ name });
            return true;
        }
        
        SendMessage("&4Error on Insert to DB!");
        return true;    
    }

    @Override
    protected Boolean isInvalid(CommandSender sender, String[] args) {
        
        if(!plugin.hasPermission(sender.getName(),"yaps.define")) {
            SendMessage("&4Not have permission");
            return false;
        }
        
        if(!Yaps.AreaCreating.containsKey((Player)sender)
        || Yaps.AreaCreating.get((Player)sender).getFirstSpot() == null
        || Yaps.AreaCreating.get((Player)sender).getSecondSpot() == null) {
            SendMessage("&4Need to set all points");
            return false;
        }
        
        if (args.length < 2) {
            SendMessage("&4Wrong arguments on command define");
            return false;
        }
        return true;
    }
    
}
