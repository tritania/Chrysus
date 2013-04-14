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

import java.io.File;
import java.lang.Integer;
import java.lang.Double;
import java.lang.String;
import java.util.List;
import java.util.logging.Logger;
import java.util.Map;
import java.util.Set;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;


public class Configuration extends YamlConfiguration {

		private File config;
		private Logger log;
		private Chrysus plugin;
		
		public String configpath;
		public String SQLurl;
		public String SQLuser;
		public String SQLpass;
		public boolean usemySQL;
		
		public boolean	tax;	//tax
		public boolean	wage;	//wages
		public boolean	pub;	//are stocks being traded
		public double	tr;		//tax rates
		public double	pay;	//cost of wages
		public double	ipo;	//inital public offering (percentage)
		public int		cfc; 	//company founding costs
		public int		icc;	//inital company capital
		public int		ipc;	//inital player capital 
		public boolean	wi; 
		


	public Configuration(File config, Logger log, Chrysus plugin) {
	
		this.config		= config;
		this.log		= log;
		this.plugin		= plugin;
	
		configpath  = null;
        SQLuser     = null;
		SQLpass     = null;
		usemySQL    = true;
		SQLurl      = String.format("jdbc:sqlite:%s%sdatabase.sqlite",
									config.getParent(), config.separator);
		tax		= true;
		wage	= true;
		pub		= true;
		tr		= 1.10;
		pay		= 11.25;
		ipo		= 0.10;
		cfc		= 12000;
		icc		= 4000;
		ipc		= 6000;
		wi		= true;
	}

	public void load() {
		
		boolean defaults = false;
	
		try {
			super.load(config);
		}
		catch(Exception e) {
			log.warning("cannot acces config, using preset values.");
			defaults = true;
		}
	
		configpath	= getString("configpath", configpath);
		SQLurl		= getString("SQLurl", SQLurl);
		SQLuser		= getString("SQLuser", SQLuser);
		SQLpass		= getString("SQLpass", SQLpass);
		usemySQL	= getBoolean("usemySQL", usemySQL);
		tax		= getBoolean("Tax",				tax);
		wage	= getBoolean("Wages",			wage);
		pub		= getBoolean("StockMarket",		pub);
		tr		= getDouble("TaxRate",			tr);
		pay		= getDouble("MinimumWage",		pay);	
		ipo		= getDouble("IPO",				ipo);
		cfc		= getInt("FoundingCost",		cfc);
		icc		= getInt("InitialCapitalC",		icc);
		ipc		= getInt("InitialCapitalP",		ipc);
		wi		= getBoolean("WebInterface",	wi);
	
		if(defaults)
			save();
		
		}
	
	public void save() {
		
		YamlConfiguration newConfig = new YamlConfiguration();
		
		newConfig.set("configpath", 		configpath);
		newConfig.set("SQLurl", 		SQLurl);
		newConfig.set("SQLuser", 		SQLuser);
		newConfig.set("SQLpass", 		SQLpass);
		newConfig.set("usemySQL", 		usemySQL);
		newConfig.set("tax",				tax);
		newConfig.set("Wages",				wage);
		newConfig.set("StockMarket",		pub);
		newConfig.set("TaxRate",			tr);
		newConfig.set("MinimumWage",		pay);
		newConfig.set("IPO",				ipo);
		newConfig.set("FoundingCost",		cfc);
		newConfig.set("InitialCapitalC",	icc);
		newConfig.set("InitialCapitalP",	ipc);
		newConfig.set("WebInterface",		wi);
		
		File ConfigurationFile = new File(plugin.getDataFolder(), "config.yml");
		
		try {
			newConfig.save(ConfigurationFile);
		}
		catch(Exception e) {
			log.warning("Could not write config.yml");
		}
	}
}
		
		

	

		
