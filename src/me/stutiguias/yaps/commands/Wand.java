/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package me.stutiguias.yaps.commands;

import me.stutiguias.yaps.init.Yaps;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

/**
 *
 * @author Daniel
 */
public class Wand extends CommandHandler {

    public Wand(Yaps plugin) {
        super(plugin);
    }

    @Override
    protected Boolean OnCommand(CommandSender sender, String[] args) {
        this.sender = sender;
        
        if(isInvalid(sender, args)) return true;
        Player player = (Player)sender;
        ItemStack itemStack = new ItemStack(Material.STICK,1);
        
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("YAPS Wand");
        itemStack.setItemMeta(itemMeta);
        
        player.setItemInHand(itemStack);
        SendMessage("&6Use Right and left click to set an area");
        return true;
    }

    @Override
    protected Boolean isInvalid(CommandSender sender, String[] args) {
         if(!plugin.hasPermission(sender.getName(),"yaps.wand")) {
             SendMessage("&4Not have permission");
             return false;
         }
         return true;
    }
    
}
