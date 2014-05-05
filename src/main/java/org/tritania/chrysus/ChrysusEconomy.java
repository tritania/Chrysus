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
import org.tritania.chrysus.Chrysus;


public class ChrysusEconomy
{
    private  HashMap<UUID, Integer> wallet = new HashMap<UUID, Integer>(); 
    
	private Chrysus chrysus;

	public ChrysusEconomy(Chrysus chrysus)
	{
		this.chrysus = chrysus;
	}
    
    
    public  int getWalletValue(Player player)
    {
        UUID playerId = player.getUniqueId();
        int balance = wallet.get(playerId);
        
        return balance;
    }
    
    public  void addMoney(Player player, int amount)
    {
        UUID playerId = player.getUniqueId();
        int balance = wallet.get(playerId);
        balance = balance + amount;
        wallet.remove(playerId);
        wallet.put(playerId, balance);
    }
    
    public  void removeMoney(Player player, int amount)
    {
        UUID playerId = player.getUniqueId();
        int balance = wallet.get(playerId);
        balance = balance - amount;
        wallet.remove(playerId);
        wallet.put(playerId, balance);
    }
    
    public void activateWallet(Player player, int value)
    {
        wallet.put(player.getUniqueId(), value);
    }
   
    public void deactivateWallet(Player player) //needs to read into sql first
    {
        wallet.remove(player.getUniqueId());
    }
    
    public void dynamicPrice(int OrderID)
    {
        
    }
    
    public void placeOrder(ItemStack itemin, int price, int amount, Player player)
    {
        dynamicPrice(0); //just for debugging at the moment will pass the actual order ID when finished 
    }
    
    public int getItemValue(String itemIn) 
    {
		String query = "SELECT price FROM `PRICES` WHERE LOWER(item)  = LOWER('" + itemIn + "') OR  LOWER(alias)  = LOWER('" + itemIn + "') limit 1";
		ArrayList<String> data = chrysus.sqlengine.getData(query);
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
