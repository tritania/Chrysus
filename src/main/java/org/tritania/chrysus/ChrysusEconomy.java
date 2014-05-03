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
        UUID playerId = player.getUniqueId();
        int balance = wallet.get(playerId);
        
        return balance;
    }
    
    public static void addMoney(Player player, int amount)
    {
        UUID playerId = player.getUniqueId();
        int balance = wallet.get(playerId);
        balance = balance + amount;
        wallet.remove(playerId);
        wallet.put(playerId, balance);
    }
    
    public static void removeMoney(Player player, int amount)
    {
        UUID playerId = player.getUniqueId();
        int balance = wallet.get(playerId);
        balance = balance - amount;
        wallet.remove(playerId);
        wallet.put(playerId, balance);
    }
    
    public static void activateWallet(Player player, int value)
    {
        wallet.put(player.getUniqueId(), value);
    }
    
    public static void deactivateWallet(Player player) //needs to read into sql first
    {
        wallet.remove(player.getUniqueId());
    }
    
    public static void dynamicPrice(int OrderID)
    {
        
    }
    
    public static void placeOrder(ItemStack itemin, int price, int amount, Player player)
    {
        dynamicPrice(0); //just for debugging at the moment will pass the actual order ID when finished 
    }
    
    public static int getItemValue(String item) 
    {
		String query = "SELECT `price` FROM PRICES where item ='" + item + "' limit 1;"; //need to include aliases
		ArrayList<String> data = ChrysusStorage.getData(query);
		if (data.get(0) == "END_DATA_STREAM")
		{
			return -1; //error as block does not appear in system.
		}
		else 
		{
			
			 return Integer.parseInt(data.get(0));
			
		}
	}    
}
