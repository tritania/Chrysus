/*
 * Copyright 2012-2014 Erik Wilson <erikwilson@magnorum.com>
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

package org.tritania.chrysus.command;

/*Start Imports*/
import org.bukkit.permissions.PermissibleBase;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

import org.tritania.chrysus.Chrysus;
import org.tritania.chrysus.util.Message;
/*End Imports*/

public class Set implements CommandExecutor 
{
	public Chrysus chrysus;

    public Set(Chrysus chrysus)
    {
        this.chrysus = chrysus;
    }
    
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		Player player = (Player) sender;
		if (player.hasPermission("chrysus.set"))
		{
			if (args.length < 1) 
			{
				Message.info(sender, command.getUsage());
				return true;
			}
			else if(args[0].equals("price"))
			{
				chrysus.economy.setItemValue(args[1], Integer.parseInt(args[2]));
			}
			else if(args[0].equals("wallet"))
			{
				Player receiver = Bukkit.getPlayer(args[1]);
				int amount = Integer.parseInt(args[2]);
				chrysus.economy.setMoney(receiver, amount);
			}
		}
		else
		{
			 Message.info(sender, "You don't have permission for that");
		}
		return true;
	}
}

//used to set the value of items and or the amount of currency in a players wallet
