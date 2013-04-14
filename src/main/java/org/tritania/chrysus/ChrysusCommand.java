/*
 * Copyright 2012-2013 Erik Wilson <erikwilson@magnorum.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.tritania.chrysus;

//IMPORTS
import java.lang.String;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
//ENDIMPORTS

public class ChrysusCommand implements CommandExecutor 
{
	private Chrysus plugin;
		
	public ChrysusCommand(Chrysus plugin)
	{
		this.plugin = plugin;
	}

	public boolean onCommand(
		CommandSender sender,
		Command command,
		String label,
		String[] args)
	{
		if(args.length < 1)
			return false;
		
		if(args[0].equalsIgnoreCase("clean"))
			return clean(sender, args); //
		else if(args[0].equalsIgnoreCase("info"))
			return info(sender, args);
		else if(args[0].equalsIgnoreCase("sell"))
			return info(sender, args);
		
		return false;
	}
//all commands will need permission setup 
	private boolean clean(CommandSender sender, String[] args)
	{	
		if(args.length < 2)
			{
			Message.info(sender, "clean [Playername | All]");
			return true;
			}
		
		if(sender instanceof Player)
			ChrysusStorage.cleanData(args[1]);
			Message.info(sender, "Data wiped");
			return true;
	}
	
	private boolean info(CommandSender sender, String[] args)
	{
		return true;
	}
	
	private boolean sell(CommandSender sender, String[] args)
	{
		if(sender instanceof Player)
			ChrysusStorage.sell(args[0], args[1]);
			Message.info(sender, "Item sold");
			return true;
	}
}
		
		
