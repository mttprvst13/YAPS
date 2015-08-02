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

	private final HashMap<String, CommandHandler> avaibleCommands;

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
		InfoWand infowand = new InfoWand(plugin);
		SetExit setexit = new SetExit(plugin);

		// Area Command
		avaibleCommands.put("d", define);
		avaibleCommands.put("define", define);
		avaibleCommands.put("dl", delete);
		avaibleCommands.put("delete", delete);
		avaibleCommands.put("w", wand);
		avaibleCommands.put("wand", wand);
		avaibleCommands.put("list", list);
		avaibleCommands.put("l", list);
		avaibleCommands.put("tp", teleport);
		avaibleCommands.put("teleport", teleport);
		avaibleCommands.put("i", info);
		avaibleCommands.put("info", info);
		avaibleCommands.put("iw", infowand);
		avaibleCommands.put("infowand", infowand);
		avaibleCommands.put("se", setexit);
		avaibleCommands.put("setexit", setexit);

		// Block Command
		avaibleCommands.put("listp", new ListProtectedBlocks(plugin));
		avaibleCommands.put("on", new On(plugin));
		avaibleCommands.put("off", new Off(plugin));

		// Communs
		avaibleCommands.put("h", help);
		avaibleCommands.put("?", help);
		avaibleCommands.put("help", help);

		avaibleCommands.put("purge", new Purge(plugin));

		avaibleCommands.put("reload", new Reload(plugin));
		avaibleCommands.put("update", new Update(plugin));

	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		this.sender = sender;

		if (sender.getName().equalsIgnoreCase("CONSOLE"))
			return true;
		if (!(sender instanceof Player))
			return false;

		String executedCommand;
		if (args.length < 0 || args.length == 0) {
			executedCommand = "help";
		} else {
			executedCommand = args[0].toLowerCase();
		}

		if (avaibleCommands.containsKey(executedCommand))
			return avaibleCommands.get(executedCommand).OnCommand(sender, args);
		else
			return CommandNotFound();
	}

	private boolean CommandNotFound() {
		SendMessage("&eCommand not found try /yaps help ");
		return true;
	}

}
