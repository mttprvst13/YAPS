package me.stutiguias.yaps.commands;

import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.stutiguias.yaps.init.Yaps;

public class InfoWand extends CommandHandler {

    public InfoWand(Yaps plugin) {
        super(plugin);
    }

    @SuppressWarnings("deprecation")
	@Override
    protected Boolean OnCommand(CommandSender sender, String[] args) {
        this.sender = sender;
        
        if(isInvalid(sender, args)) return true;
        Player player = (Player)sender;
        ItemStack itemStack = new ItemStack(Material.getMaterial(plugin.getConfig().getInt("InfoWandID")),1);
        
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName("YAPS Info Wand");
        itemStack.setItemMeta(itemMeta);
        
        player.setItemInHand(itemStack);
        SendMessage("&6Use Right and left click to get information of an area.");
        
        return true;
    }
	
    @Override
    protected Boolean isInvalid(CommandSender sender, String[] args) {
         if(!plugin.hasPermission(sender.getName(),"yaps.infowand")) {
             SendMessage("&4Not have permission");
             return true;
         }
         return false;
    }
    
}
