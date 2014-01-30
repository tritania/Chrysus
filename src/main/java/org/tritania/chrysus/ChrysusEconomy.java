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

package org.tritania.chrysus;

import java.util.*;
import java.sql.*;

import org.bukkit.entity.Player;
import org.bukkit.Material;
import org.bukkit.inventory.*;
import org.bukkit.entity.Entity;


import org.tritania.chrysus.util.Log;
import org.tritania.chrysus.util.BlockTranslator;
import org.tritania.chrysus.Chrysus;
import org.tritania.chrysus.ChrysusStorage;

public class ChrysusEconomy
{
    private static HashMap<UUID, Integer> wallet = new HashMap<UUID, Integer>(); //stores player UUID and wallet value in int
    
	private Chrysus plugin;

	public ChrysusEconomy(Chrysus plugin)
	{
		this.plugin = plugin;
	}
    
    public static int getWalletValue(Player player)
    {
        return 3; //debugging
    }
    
    public static void addMoney(Player player, int amount)
    {
        
    }
    
    public static void removeMoney(Player player, int amount)
    {
        
    }
    
    public static void activateWallet(Player player)
    {
        wallet.put(player.getUniqueId(), getWalletValue(player));
    }
    
    public static void deactivateWallet(Player player)
    {
        
    }
    
    public static void dynamicPrice(int OrderID)
    {
        
    }
    
    public static void placeOrder(ItemStack itemin, int price, int amount, Player player)
    {
        dynamicPrice(0); //just for debugging at the moment will pass the actual order ID when finished 
    }
    

    
}
