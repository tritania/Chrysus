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

import java.util.ArrayList;

import org.bukkit.plugin.PluginManager;
import org.bukkit.Material;

import org.tritania.chrysus.Chrysus;

public class BlockTranslator 
{
	public Chrysus chrysus;   

	public BlockTranslator(Chrysus chrysus)
	{
		this.chrysus = chrysus;
	}
    
    
    public  Material getItem(String itemIn)
    {
		String query = "SELECT item FROM `PRICES` WHERE LOWER(item)  = LOWER('" + itemIn + "') OR  LOWER(alias)  = LOWER('" + itemIn + "') limit 1";
		
		ArrayList<String> data = chrysus.sqlengine.getData(query);
		if (data.get(0) == "END_DATA_STREAM")
		{
			Material item = Material.getMaterial("AIR"); //internal error block
			return item;
		}
		else 
		{
			Material item = Material.getMaterial(data.get(0));
			return item;
		}
    }
}
