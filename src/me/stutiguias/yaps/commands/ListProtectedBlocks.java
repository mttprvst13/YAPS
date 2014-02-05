/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.stutiguias.yaps.commands;

import me.stutiguias.yaps.init.Yaps;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Daniel
 */
public class ListProtectedBlocks extends  CommandHandler {

    public ListProtectedBlocks(Yaps plugin) {
        super(plugin);
    }

    @Override
    protected Boolean OnCommand(CommandSender sender, String[] args) {
        this.sender =  sender;
        
        if(!isInvalid(sender, args)) return true;
        
        if(Yaps.config.Protected.isEmpty()) {
            SendMessage("&4 Protected Blocks empty");
            return true;
        }
        
        if(args.length > 1) {
            ShowAllProtectedBlocks();
        }else{
            isProtected(args[1]);
        }
        
        return true;
    }
    
    
    public void ShowAllProtectedBlocks() {
        SendMessage(MsgHr);
        for(String blockId:Yaps.config.Protected){
            SendMessage("&3Id: &6%s &3- &6%s", new Object[]{ blockId , Material.getMaterial(Integer.parseInt(blockId)) });
        }
        SendMessage(MsgHr);
    }
    
    public void isProtected(String id) {
        String check = "is &4not &6protected";
        SendMessage(MsgHr);
        for(String blockId:Yaps.config.Protected) {
            if(blockId.equals(id)) check = "is protected";
        }
        SendMessage("&3Id: &6%s &6- %s",new Object[] { id , check });
        SendMessage(MsgHr); 
    }

    @Override
    protected Boolean isInvalid(CommandSender sender, String[] args) {
       if(!plugin.hasPermission(sender.getName(),"yaps.listprotectionblocks")) {
           SendMessage("&4Not have permission");
           return false;
       }
       return true;
    }
}
