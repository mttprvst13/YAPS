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
public class List extends CommandHandler {

    public List(Yaps plugin) {
        super(plugin);
    }

    @Override
    protected Boolean OnCommand(CommandSender sender, String[] args) {
        this.sender = sender;
        
        if(!isInvalid(sender, args)) return true;
        
        if(Yaps.Areas.isEmpty()) {
            SendMessage("&4 Areas empty");
            return true;
        }
        
        SendMessage(MsgHr);
        for(Area area:Yaps.Areas){
            SendMessage("&3Name: &6%s", new Object[]{ area.getName() });
        }
        SendMessage(MsgHr);
        return true;
    }

    @Override
    protected Boolean isInvalid(CommandSender sender, String[] args) {
        if(!plugin.hasPermission(sender.getName(),"yaps.list")) {
            SendMessage("&4Not have permission");
            return false;
        }
        return true;
    }
    
}
