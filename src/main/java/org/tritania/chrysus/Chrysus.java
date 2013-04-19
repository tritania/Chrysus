/*
 * Copyright 2012-2013 Erik Wilson <erikwilson@magnorum.com>
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
import org.tritania.chrysus.util.Utils;
/*End Imports*/

public class Chrysus extends JavaPlugin
{
	public Configuration config;
	
	public static String SQLurl;
	public static String SQLpass;
	public static String SQLuser;
	
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
		
		getCommand("csell").setExecutor(new CSell(this));
		getCommand("cbuy").setExecutor(new CBuy(this));
		getCommand("cdesc").setExecutor(new CDesc(this));
		getCommand("cclean").setExecutor(new CClean(this));
		getCommand("cinc").setExecutor(new CInc(this));
		getCommand("cisk").setExecutor(new CIsk(this));
		
		SQLuser    = this.config.SQLuser;
		SQLpass    = this.config.SQLpass;
		SQLurl     = this.config.SQLurl;
		
		ChrysusStorage.initialize();
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
