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

/*Start Imports*/
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import org.tritania.chrysus.command.*;

import org.tritania.chrysus.util.Log;
import org.tritania.chrysus.util.Message;
import org.tritania.chrysus.util.BlockTranslator;
import org.tritania.chrysus.Configuration;
import org.tritania.chrysus.ChrysusStorage;
import org.tritania.chrysus.util.ChrysusInv;
import org.tritania.chrysus.ChrysusEconomy;


/*End Imports*/

public class Chrysus extends JavaPlugin
{
	public Configuration config;
	public ChrysusStorage sqlengine;
	public ChrysusEconomy economy;
	public BlockTranslator blocktans;
	public ChrysusInv chyrsusInv;
	
	public void onLoad()
	{
		config = new Configuration(new File(getDataFolder(), "config.yml"));
	}
	
	public void onEnable()
	{
		
		PluginManager pm;
		Plugin p;
		
		Log.init(getLogger());
		Message.init(getDescription().getName());
		
		pm = getServer().getPluginManager();
		config.load();
		
		sqlengine = new ChrysusStorage(this);
        sqlengine.initialize();
        
        pm.registerEvents(new ChrysusListener(this), this);
        economy = new ChrysusEconomy(this);
        blocktans = new BlockTranslator(this);
        chyrsusInv = new ChrysusInv(this);
        
        loadPrices();

        getCommand("cbuy").setExecutor(new Buy(this));
        getCommand("csell").setExecutor(new Sell(this));
        getCommand("cset").setExecutor(new Set(this));
        getCommand("cinfo").setExecutor(new OrderInfo(this));
        getCommand("cwallet").setExecutor(new Wallet(this));
        getCommand("creload").setExecutor(new Reload(this));
		
	}
	
	public void onDisable() 
	{
		offloadPrices();
        sqlengine.closeConnection();
    }
    
	public void reload()
	{
		reloadPrices();
		config.load();
	}
	
	/*
	 * Divder for item.csv related methods
	 */ 
	
	public void offloadPrices()
	{
		String location = getDataFolder().getAbsolutePath();
		
		String query = "SELECT `item` FROM PRICES"; 
		String query2 = "SELECT `alias` FROM PRICES"; 
		String query3 = "SELECT `price` FROM PRICES"; 
		
		ArrayList<String> item = sqlengine.getData(query);
		ArrayList<String> alias = sqlengine.getData(query2);
		ArrayList<String> price = sqlengine.getData(query3);
		
		if (item.get(0) == "END_DATA_STREAM")
		{
			
		}
		else 
		{
			int length = item.size();
			try
			{
				FileWriter writer = new FileWriter(location + "/items.csv");
				for(int i = 0; i < length-1; i++)
				{
					writer.append(item.get(i));
					writer.append(',');
					writer.append(alias.get(i));
					writer.append(',');
					writer.append(price.get(i));
					writer.append('\n');
				}
				writer.close();
			}
			catch(IOException ex)
			{
				Log.severe("  " + ex.getMessage());
			}
			
		}
	}
	
	public void loadPrices()
	{
		String location = getDataFolder().getAbsolutePath();
		File items = new File(getDataFolder(), "items.csv");
        if (!items.exists())
        {
			saveResource("items.csv", true); 
			String queryind = "LOAD DATA LOCAL INFILE '" + location + "/items.csv' INTO TABLE PRICES FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' (item, alias, price)";
			sqlengine.Store(queryind);
		}
		else
		{
			String queryind = "LOAD DATA LOCAL INFILE '" + location + "/items.csv' REPLACE INTO TABLE PRICES FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' (item, alias, price)"; //error
			sqlengine.Store(queryind);
			saveResource("items.csv", true); 
			queryind = "LOAD DATA LOCAL INFILE '" + location + "/items.csv' IGNORE INTO TABLE PRICES FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' (item, alias, price)";
			sqlengine.Store(queryind);
		}
	}
	
	public void reloadPrices()
	{
		String location = getDataFolder().getAbsolutePath();
		String queryind = "LOAD DATA LOCAL INFILE '" + location + "/items.csv' INTO TABLE PRICES FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' (item, alias, price)";
	}
}

