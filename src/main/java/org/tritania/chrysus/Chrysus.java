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

//IMPORTS
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.lang.String;
import java.util.Date;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.command.CommandSender;
import java.util.logging.Logger;
//END IMPORTS

public class Chrysus extends JavaPlugin
{
	public static String pluginName = "Chrysus";
	
	public 	Configuration 		config;
	public  ChrysusStorage		storage;
	private ChrysusListener 	listener;
	private ChrysusCommand		command;
	public static final Logger logger = Logger.getLogger("Minecraft");
	
	public static boolean usemySQL;
	public static String SQLuser;
	public static String SQLpass;
	public static String SQLurl;
	public static String configpath;
	
	public void onLoad() 
	{	
		copyConfig("config.yml");
		File configurationFile = new File(getDataFolder(), "config.yml");
		this.config = new Configuration(configurationFile, logger, this);
		
		this.storage  = new ChrysusStorage(this);
		this.listener = new ChrysusListener(this);
		this.command  = new ChrysusCommand(this);
	}
	
	public void onEnable() 
	{
		this.config.load();
		this.listener.register();
		
		getCommand("chrysus").setExecutor(command);
		
		SQLuser    = this.config.SQLuser;
		SQLpass    = this.config.SQLpass;
		SQLurl     = this.config.SQLurl;
		configpath = this.config.configpath;
		usemySQL   = this.config.usemySQL;
		ChrysusStorage.getConnection();
	}

	public void onDisable()
	{
		ChrysusStorage.closeConnection();
	}

	public void reload()
	{
	this.config.load();
	
	this.logger.info(getDescription().getVersion() + " reloaded.");
	}

	public boolean copyConfig(String filename)
	{
		File sourceFile;
		File destinationFile;
		try
		{
			if(!getDataFolder().exists())
				getDataFolder().mkdirs();

			destinationFile = new File(getDataFolder(), filename);

			if(!destinationFile.createNewFile())
				return false;

			InputStream inputStream = getClass().getResourceAsStream("/" +  filename);
			OutputStream out = new FileOutputStream(destinationFile);
			byte buffer[] = new byte[1024];
			int length;

			while((length = inputStream.read(buffer)) > 0)
				out.write(buffer, 0, length);

			out.close();
			inputStream.close();
			return true;
		}
		catch(Exception e)
		{
			logger.warning("Unable to copy " + filename + " to the plugin directory.");
			return false;
		}
	}
}

		





