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
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

import org.tritania.chrysus.Chrysus;
import org.tritania.chrysus.util.Message;
/*End Imports*/

public class Wallet implements CommandExecutor 
{
	public Chrysus chrysus;

    public Wallet(Chrysus chrysus)
    {
        this.chrysus = chrysus;
    }
    
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		Player player = (Player) sender;
		
		if (args.length < 1) 
        {
            Message.info(sender, command.getUsage());
            return true;
        }
        
        else if(args[0].equals("balance"))
        {
            Message.info(sender, "Your current balace is: " + chrysus.economy.getWalletValue(player));
        }
        
        else if(args[0].equals("transfer"))
        {
			if(args.length == 3)
			{
				int amount = Integer.parseInt(args[1]);
				if((chrysus.economy.getWalletValue(player) - amount) >= 0)
				{
					Player receiver = Bukkit.getPlayer(args[2]);
					chrysus.economy.removeMoney(player, amount);
					chrysus.economy.addMoney(receiver, amount);
				}
				else 
				{
					Message.info(sender, "You don't have enough money for that");
				}
			}
			else
			{
				Message.info(sender, command.getUsage());
				return true;
			} 
        }
        
		return true;
	}
}

