/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.stutiguias.yaps.commands;

import java.util.HashMap;
import me.stutiguias.yaps.init.Yaps;
import me.stutiguias.yaps.init.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 *
 * @author Daniel
 */
public class YAPSCommands extends Util implements CommandExecutor {
     
    private final HashMap<String,CommandHandler> avaibleCommands;

    public YAPSCommands(Yaps plugin) {
        super(plugin);
        avaibleCommands = new HashMap<>();
        
        Define define = new Define(plugin);
        Delete delete = new Delete(plugin);
        Help help = new Help(plugin);
        List list = new List(plugin);
        Wand wand = new Wand(plugin);
        Info info = new Info(plugin);
        Teleport teleport = new Teleport(plugin);
        
        avaibleCommands.put("d", define);
        avaibleCommands.put("define", define);
        
        avaibleCommands.put("dl", delete);
        avaibleCommands.put("delete", delete);
        
        avaibleCommands.put("h", help);
        avaibleCommands.put("?", help);
        avaibleCommands.put("help", help);
        
        avaibleCommands.put("listp", new ListProtectedBlocks(plugin));
        
        avaibleCommands.put("list", list);
        avaibleCommands.put("l", list);
        
        avaibleCommands.put("w",wand);
        avaibleCommands.put("wand", wand);
        
        avaibleCommands.put("on", new On(plugin));
        avaibleCommands.put("off", new Off(plugin));
        
        avaibleCommands.put("i", info);
        avaibleCommands.put("info", info);
        
        avaibleCommands.put("reload", new Reload(plugin));
        avaibleCommands.put("update", new Update(plugin));
        
        avaibleCommands.put("tp", teleport);
        avaibleCommands.put("teleport", teleport);
        
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        
        this.sender = sender;
        
        if (sender.getName().equalsIgnoreCase("CONSOLE")) return true;
        if (!(sender instanceof Player)) return false;
 
        if(args.length < 0 || args.length == 0) return CommandNotFound();
        
        String executedCommand = args[0].toLowerCase();

        if(avaibleCommands.containsKey(executedCommand))
            return avaibleCommands.get(executedCommand).OnCommand(sender,args);
        else
            return CommandNotFound();     
    } 
    
    private boolean CommandNotFound() {
        SendMessage("&eCommand not found try /yaps help ");
        return true;
    }

}
