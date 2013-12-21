/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.stutiguias.yaps.commands;

import me.stutiguias.yaps.init.Yaps;
import me.stutiguias.yaps.init.Util;
import me.stutiguias.yaps.model.Area;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Daniel
 */
public class YAPSCommands extends Util implements CommandExecutor {
     
    private String[] args;
    private final String MsgHr = "&e-----------------------------------------------------";
    
    public YAPSCommands(Yaps plugin) {
        super(plugin);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        
        if (sender.getName().equalsIgnoreCase("CONSOLE")) return true;
        if (!(sender instanceof Player)) return false;
        
        this.sender = sender;
        this.args = args;
        
        if (args.length == 0) return Help();
        
        switch(args[0].toLowerCase())
        {
            case "reload":
                if(!plugin.hasPermission(sender.getName(),"yaps.reload")) return false;
                return Reload();
            case "update":
                if(!plugin.hasPermission(sender.getName(),"yaps.update")) return false;
                return Update();
                
            case "w":
            case "wand":
                if(!plugin.hasPermission(sender.getName(),"yaps.wand")) return false;
                return Wand();
            case "d":
            case "define" :
                if(!plugin.hasPermission(sender.getName(),"yaps.define")) return false;
                return Define();
            case "dl":
            case "delete":
                if(!plugin.hasPermission(sender.getName(),"yaps.delete")) return false;
                return Delete();
            case "tp":
            case "teleport":
                if(!plugin.hasPermission(sender.getName(),"yaps.tp")) return false;
                return teleportToArea();
            case "i":
            case "info":
                if(!plugin.hasPermission(sender.getName(),"yaps.info")) return false;
                return Info();
            case "l":
            case "list":
                if(!plugin.hasPermission(sender.getName(),"yaps.list")) return false;
                return List();
            case "se":
            case "setexit":
                if(!plugin.hasPermission(sender.getName(),"yaps.setexit")) return false;
                return SetExit();
                
            case "?":
            case "help":
            default:
                return Help();
        }       
    } 
        
    public boolean List() {
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
       
    public boolean SetExit(){
        Player player = (Player)sender;
        
        Location location = player.getTargetBlock(null,2).getLocation();
        
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
       
    public boolean Info(){
        Player player = (Player)sender;

        Location location = player.getLocation();
        
        int index = plugin.getAreaIndex(location);
        
        if(index == -1) {
            SendMessage("&4 Not inside any area");
            return true;
        }
        Area area = Yaps.Areas.get(index);
        
        SendMessage(MsgHr);
        SendMessage("&3Name: &6%s", new Object[]{ area.getName() });
        SendMessage("&3Owner: &6%s", new Object[]{ area.getOwner() });
        SendMessage("&3Flags: &6%s", new Object[]{ area.getFlags() });
        SendMessage(MsgHr);
        
        return true;
    }
    
    private boolean Delete() {
                     
        if (args.length < 1) {
            SendMessage("&4Wrong arguments");
            return true;
        }   
        
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
    
    public boolean Update() {
        plugin.Update();
        return true;
    }
    
    public boolean Wand() {
        Player player = (Player)sender;
        ItemStack itemStack = new ItemStack(Material.STICK,1);
        
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("YAPS Wand");
        itemStack.setItemMeta(itemMeta);
        
        player.setItemInHand(itemStack);
        SendMessage("&6Use Right and left click to set an area");
        return true;
    }
        
    public boolean Reload() {
        SendMessage("&6Reloading!");
        plugin.OnReload();
        SendMessage("&6Reload Done!");    
        return true;
    }
    
    public boolean Help() {
        
        SendMessage(MsgHr);
        SendMessage(" &7Yet Another Protect System ");
        
        SendMessage(MsgHr);
        
        if(plugin.hasPermission(sender.getName(),"yaps.define")){
            SendMessage("&6/yaps <d or define> <areaName> &e| &7Save Select area");
            SendMessage("&6/yaps <d or define> <areaName> <owner> &e| &7Save Select area");
        }
        
        if(plugin.hasPermission(sender.getName(),"yaps.wand")){
            SendMessage("&6/yaps <w or wand>  &e| &7Get Special Wand to make area");
        }
      
        if(plugin.hasPermission(sender.getName(),"yaps.list")){
            SendMessage("&6/yaps <l or list> &e| &7List all areas");
        }   
                
        if(plugin.hasPermission(sender.getName(),"yaps.info")){
            SendMessage("&6/yaps <i or info> &e| &7info about area you are");
        }   
        
        if(plugin.hasPermission(sender.getName(),"yaps.delete")){
            SendMessage("&6/yaps <dl or delete> <areaName> &e| &7Delete an area");
        }
        
        if(plugin.hasPermission(sender.getName(),"yaps.tp")){
            SendMessage("&6/yaps <tp or teleport> <areaName> &e| &7Teleport to an area");
        }
  
        if(plugin.hasPermission(sender.getName(),"yaps.update")){
            SendMessage("&6/yaps update &e| &7 Update the plugin");
        }
        
        if(plugin.hasPermission(sender.getName(),"yaps.reload")){
            SendMessage("&6/yaps reload &e| &7Reload the plugin");
        }
        
        SendMessage(MsgHr);
        
        return true;
    }

    public boolean teleportToArea() {
        Player player = (Player)sender;
               
        if (args.length < 2) {
            SendMessage("&4Wrong arguments on command tp");
            return true;
        }
        
        String name = args[1];
        
        for(Area area:Yaps.Areas) {
            if(area.getName().equalsIgnoreCase(name)) {
                player.teleport(area.getFirstSpot());
            }
        }
        
        return true;
    }
    
    public boolean Define() {
           
        if(!Yaps.AreaCreating.containsKey((Player)sender)
        || Yaps.AreaCreating.get((Player)sender).getFirstSpot() == null
        || Yaps.AreaCreating.get((Player)sender).getSecondSpot() == null) {
            SendMessage("&4Need to set all points");
            return false;
        }
        
        if (args.length < 2) {
            SendMessage("&4Wrong arguments on command define");
            return true;
        }
        
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
        
        SendMessage("&4Erro on Insert to DB!");
        return true;                
    }

}
