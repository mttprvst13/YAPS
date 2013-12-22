/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package me.stutiguias.yaps.listener;

import me.stutiguias.yaps.init.Yaps;
import me.stutiguias.yaps.init.Util;
import me.stutiguias.yaps.model.Area;
import me.stutiguias.yaps.model.BlockProtected;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
/**
 *
 * @author Daniel
 */
public class PlayerListener extends Util implements Listener {
    
    public PlayerListener(Yaps plugin) {
        super(plugin);
    }
        
    @EventHandler(priority= EventPriority.NORMAL)
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
 
        if(Yaps.config.UpdaterNotify && plugin.hasPermission(player,"yaps.update") && Yaps.update)
        {
          SendMessage(player,"&6An update is available: " + Yaps.name + ", a " + Yaps.type + " for " + Yaps.version + " available at " + Yaps.link);
          SendMessage(player,"&6Type /cd update if you would like to automatically update.");
        }

    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockPlace(BlockPlaceEvent event) {
        
        String block = String.valueOf(event.getBlock().getTypeId());
        Player player = (Player)event.getPlayer();
        Location location = event.getBlock().getLocation();       

        if(Yaps.config.Protected.contains(block)){ 
            if(isInsideArea(location)) {
               if(Yaps.config.AllowProtectedBlockInsideArea) {
                   Protect(location, player, block);
               }
            }else{
                Protect(location, player, block);
            }
        }
        
        if(Yaps.Areas.isEmpty()) return;

        if(!isValidEvent(player, location,"place")) {
            if(plugin.hasPermission(player,"yaps.bypass")) return;
            event.setCancelled(true);
        }
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockBreak(BlockBreakEvent event) {
                
        Location location = event.getBlock().getLocation();       
        Player player = (Player)event.getPlayer();
        
        if(Yaps.Protected.containsKey(location)) {
           BlockProtected blockProtected = Yaps.Protected.get(location);
           if(!blockProtected.getOwner().equals(event.getPlayer().getName())) {
               SendMessage(player,"Block protected");
               event.setCancelled(true);
           }
        }
        
        if(Yaps.Areas.isEmpty()) return;
        
        if(!isValidEvent(player, location,"break")) {
            if(plugin.hasPermission(player,"yaps.bypass")) return;
            event.setCancelled(true);
        }
        
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void onMoveInside(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location location = event.getTo();

        if(!isValidEvent(player, location,"move")) {
            if(plugin.hasPermission(player,"yaps.bypass")) return;
            Area area = plugin.getArea(location);
            if(area == null || area.getExit() == null) {
                Location tpTo = event.getFrom();
                tpTo.setZ(event.getFrom().getZ() - 1);
                player.teleport(tpTo);
            }else{
                player.teleport(area.getExit());
            }
        }
    
    }
    
    @EventHandler(priority = EventPriority.NORMAL)
    public void PlayerInteract(PlayerInteractEvent event){
        if( !event.hasItem() 
         || !event.getItem().hasItemMeta() 
         || !event.getItem().getItemMeta().hasDisplayName()
         || !event.getItem().getItemMeta().getDisplayName().equals("YAPS Wand")) return;
        
        if(!event.hasBlock()) return;
        
        Player player = event.getPlayer();
        if(!Yaps.AreaCreating.containsKey(player)) Yaps.AreaCreating.put(player, new Area());
        
        Location location = event.getClickedBlock().getLocation();
        String setOn = String.format("( %s,%s )",new Object[] { location.getX() , location.getZ() } );
        
        if(event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            Yaps.AreaCreating.get(player).setFirstSpot(location);

            SendMessage(player,"&6First Spot Set on %s",new Object[] { setOn });
            event.setCancelled(true);
        }
        
        if(event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            Yaps.AreaCreating.get(player).setSecondSpot(location);
            
            SendMessage(player,"&6Second Spot Set on %s",new Object[] { setOn });
            event.setCancelled(true);            
        }
        
    }
    
    public boolean isValidEvent(Player player,Location location,String event) {

        Area area = plugin.getArea(location);
        if(area == null) return true;

        switch (event) {
            case "place":
                return isValidPlace(area, player);
            case "break":
                return isValidBreak(area, player);
            case "move":
                return isValidMove(area, player);
            default:
                return true;
        }
    }
    
    public boolean isValidPlace(Area area,Player player) {
        return isAllowed(area,player);
    }    
    
    public boolean isValidBreak(Area area,Player player) {
        return isAllowed(area,player);
    }
    
    public boolean isValidMove(Area area,Player player) {
        
        if(area.getFlags().contains("AllowMoveInside")) return true;
        
        return isAllowed(area,player);
    }
    
    public boolean isAllowed(Area area,Player player){
        return area.getOwner().equals(player.getName());
    }
    
    public boolean isInsideArea(Location location) {
        return !Yaps.Areas.isEmpty() && plugin.getArea(location) != null;
    }
    
    public void Protect(Location location,Player player,String block) {
        Yaps.Protected.put(location,new BlockProtected(location, player.getName() , block));
        SendMessage(player,"You place protected block");
    }
}
