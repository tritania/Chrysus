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
		
		
        ChrysusStorage.initialize();
        pm.registerEvents(new ChrysusListener(this), this);
        
        //item prices storage
        File items = new File(getDataFolder(), "items.csv");
        if (!items.exists())
        {
			saveResource("items.csv", true); 
			String queryind = "LOAD DATA LOCAL INFILE 'plugins/Chrysus/items.csv' INTO TABLE PRICES FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' (item, price)";
			ChrysusStorage.Store(queryind);
		}
		else
		{
			String queryind = "LOAD DATA LOCAL INFILE 'plugins/Chrysus/items.csv' REPLACE INTO TABLE PRICES FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' (item, price)";
			ChrysusStorage.Store(queryind);
			saveResource("items.csv", true); 
			queryind = "LOAD DATA LOCAL INFILE 'plugins/Chrysus/items.csv' IGNORE INTO TABLE PRICES FIELDS TERMINATED BY ',' LINES TERMINATED BY '\n' (item, price)";
			ChrysusStorage.Store(queryind);
		}
       
        getCommand("cbuy").setExecutor(new Buy(this));
        getCommand("csell").setExecutor(new Sell(this));
        getCommand("cset").setExecutor(new Set(this));
        getCommand("cinfo").setExecutor(new OrderInfo(this));
		
	}
	
	public void onDisable()
	{
        ChrysusStorage.closeConnection();
    }
    
	public void reload()
	{
		config.load();
	}
}

/*
 * Price modififer based on the amount currently in the world (exclude placed blocks)
 * MCscoreboard support
 */
