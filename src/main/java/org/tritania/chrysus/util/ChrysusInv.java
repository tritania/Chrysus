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
package org.tritania.chrysus.util;
import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.inventory.*;

import org.tritania.chrysus.Chrysus;

public class ChrysusInv
{
	public Chrysus chrysus;

    public ChrysusInv(Chrysus chrysus)
    {
        this.chrysus = chrysus;
    }
    
    public  boolean hasItems(Player player, Material boughtItem, int amount) //call to make sure the user actually has the items he is trying to sell
    {
        return player.getInventory().contains(boughtItem, amount);
    }
    
    public  void addItems(Player player, Material boughtItem, int amount) //will work past 64
    {
        ItemStack bought = new ItemStack(boughtItem, amount); 
        player.getInventory().addItem(bought);
    }
    
    public  void removeItems(Player player, Material boughtItem, int amount) //will work past 64
    {
        ItemStack bought = new ItemStack(boughtItem, amount); 
        player.getInventory().removeItem(bought);
    }
}
