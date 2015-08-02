/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.stutiguias.yaps.listener;

import me.stutiguias.yaps.init.Yaps;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.SignChangeEvent;

/**
 *
 * @author Daniel
 */
public class SignListener implements Listener {
        
    private final Yaps plugin;
    
    public SignListener(Yaps plugin) {
        this.plugin = plugin;
    }
        
    @EventHandler(priority = EventPriority.NORMAL)
    public void onBlockBreak(BlockBreakEvent event) {

    }
        
    @EventHandler(priority = EventPriority.NORMAL)
    public void onSignChange(SignChangeEvent event) {
   

    } 
 
    private void CancelEvent(SignChangeEvent event,Player player,Block thisSign,String msg) {
            event.setCancelled(true);
            player.sendMessage(plugin.prefix + msg);
    }
}
