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
public class Help extends CommandHandler {

    public Help(Yaps plugin) {
        super(plugin);
    }

    @Override
    protected Boolean OnCommand(CommandSender sender, String[] args) {
        this.sender = sender;
        
        boolean hasPerm = false;
        
        SendMessage(MsgHr);
        SendMessage(" &7Yet Another Protect System ");
        
        SendMessage(MsgHr);
        
        if(plugin.hasPermission(sender.getName(),"yaps.define")){
            SendMessage("&6/yaps <d or define> <areaName> &e| &7Save Select area");
            SendMessage("&6/yaps <d or define> <areaName> <owner> &e| &7Save Select area");
            hasPerm = true;
        }
        
        if(plugin.hasPermission(sender.getName(),"yaps.listprotectionblocks")){
            SendMessage("&6/yaps <listp> &e| &7List Protected Blocks");
            SendMessage("&6/yaps <listp> <id> &e| &7Find if this block id is protected");
            hasPerm = true;
        }     
        
        if(plugin.hasPermission(sender.getName(),"yaps.onoff")){
            SendMessage("&6/yaps <on|off>  &e| &7Enable or Disable Protect Place Block");
            hasPerm = true;
        }       
        
        if(plugin.hasPermission(sender.getName(),"yaps.wand")){
            SendMessage("&6/yaps <w or wand>  &e| &7Get Special Wand to make area");
            hasPerm = true;
        }
      
        if(plugin.hasPermission(sender.getName(),"yaps.list")){
            SendMessage("&6/yaps <l or list> &e| &7List all areas");
            hasPerm = true;
        }   
                
        if(plugin.hasPermission(sender.getName(),"yaps.info")){
            SendMessage("&6/yaps <i or info> &e| &7Info about area you are");
            hasPerm = true;
        }   
        
        if(plugin.hasPermission(sender.getName(),"yaps.info")){
            SendMessage("&6/yaps <iw or infowand> &e| &7Get the info wand");
            hasPerm = true;
        }   
        
        if(plugin.hasPermission(sender.getName(),"yaps.list")){
            SendMessage("&6/yaps <se or setexit> &e| &7Set the exit point for all not allow players");
            hasPerm = true;
        }   
                
        if(plugin.hasPermission(sender.getName(),"yaps.purge")){
            SendMessage("&6/yaps purge &e| &7Delete Old Records");
            hasPerm = true;
        }
        
        if(plugin.hasPermission(sender.getName(),"yaps.delete")){
            SendMessage("&6/yaps <dl or delete> <areaName> &e| &7Delete an area");
            hasPerm = true;
        }
        
        if(plugin.hasPermission(sender.getName(),"yaps.tp")){
            SendMessage("&6/yaps <tp or teleport> <areaName> &e| &7Teleport to an area");
            hasPerm = true;
        }
  
        if(plugin.hasPermission(sender.getName(),"yaps.update")){
            SendMessage("&6/yaps update &e| &7 Update the plugin");
            hasPerm = true;
        }
        
        if(plugin.hasPermission(sender.getName(),"yaps.reload")){
            SendMessage("&6/yaps reload &e| &7Reload the plugin");
            hasPerm = true;
        }
        
        if(!hasPerm){
        	SendMessage("&4Sorry! &7You are not allowed to do anything with YAPS!");
        }
        
        SendMessage(MsgHr);
        
        return true;
    }

    @Override
    protected Boolean isInvalid(CommandSender sender, String[] args) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
