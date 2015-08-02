/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.stutiguias.yaps.commands;

import me.stutiguias.yaps.init.Yaps;
import me.stutiguias.yaps.task.PurgeOldRecordsTask;
import org.bukkit.command.CommandSender;

/**
 *
 * @author Daniel
 */
public class Purge extends CommandHandler {

    public Purge(Yaps plugin) {
        super(plugin);
    }

    @Override
    protected Boolean OnCommand(CommandSender sender, String[] args) {
        this.sender = sender;
        
        if(!isInvalid(sender, args)) return true;
        
        SendMessage("&ePurging old records now...");
        plugin.getServer().getScheduler().runTaskAsynchronously(plugin, new PurgeOldRecordsTask(plugin));
        SendMessage("&e...Purge sucess - See Console for more details");
        
        return true;
    }

    @Override
    protected Boolean isInvalid(CommandSender sender, String[] args) {
        if(!plugin.hasPermission(sender.getName(),"yaps.purge")) {
            SendMessage("&4Not have permission");
            return false;
        }
        return true;
    }
    
}
