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


/**
 * Will need to design a better block number indication system, look into naming system
 * has to be an api call for that, config prices need to go somewhere need a 
 * new class to handle data/parsing
 * 
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

import org.tritania.chrysus.util.Log;
import org.tritania.chrysus.util.Message;
import org.tritania.chrysus.util.BlockTranslator;
import org.tritania.chrysus.Configuration;
import org.tritania.chrysus.ChrysusStorage;
/*End Imports*/

public class Chrysus extends JavaPlugin
{
	public Configuration config;
    public static BlockTranslator translator;
	
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
		
		pm.registerEvents(new ChrysusListener(this), this);
        translator = new BlockTranslator(this);
        ChrysusStorage.initialize();
		
	}
	
	public void onDisable()
	{
        ChrysusStorage.closeConnection();
    }
    
	public void reload()
	{
		config.load();
        translator = new BlockTranslator(this);
	}
}
