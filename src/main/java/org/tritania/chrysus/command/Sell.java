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
import org.bukkit.inventory.*;
import org.bukkit.Material;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.entity.Player;
import org.bukkit.Bukkit;

import org.tritania.chrysus.Chrysus;
import org.tritania.chrysus.util.Message;
import org.tritania.chrysus.util.BlockTranslator;
import org.tritania.chrysus.util.ChrysusInv;
/*End Imports*/

public class Sell implements CommandExecutor 
{
	public Chrysus chrysus;

    public Sell(Chrysus chrysus)
    {
        this.chrysus = chrysus;
    }
    
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
        if (args.length < 1) 
        {
            Message.info(sender, command.getUsage());
            return true;
        }
        
        else
        {
            
            Player player = (Player) sender;
            Material bought = BlockTranslator.getItem(args[0]);
            int amount = Integer.parseInt(args[1]);
            
            ChrysusInv.removeItems(player, bought, amount);
            
        }
        
		return true;
	}
}
