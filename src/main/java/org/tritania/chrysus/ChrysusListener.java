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
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.tritania.chrysus.util.Log;

public class ChrysusListener implements Listener
{
	private Chrysus chrysus;

	public ChrysusListener(Chrysus chrysus)
	{
		this.chrysus = chrysus;
	}
	
	public void register()
	{
		PluginManager manager;
		
		manager = chrysus.getServer().getPluginManager();
		manager.registerEvents(this, chrysus);
	} 
	
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onPlayerJoin(PlayerJoinEvent event)
	{
        Player player = event.getPlayer();
        String playerId = player.getUniqueId().toString();
		String query = "SELECT `value` FROM WALLET WHERE `player`='" + playerId + "';";
        ArrayList<String> data = chrysus.sqlengine.getData(query);
            if (data.get(0) == "END_DATA_STREAM")
            {
                String queryind = "INSERT INTO WALLET (player, value) VALUES ('" + playerId + "', 500)";
                chrysus.sqlengine.StoreTwo(queryind);
                chrysus.economy.activateWallet(player, 500);
            }
            else 
            {
                int value = 0;
                value = Integer.parseInt(data.get(0));
                chrysus.economy.activateWallet(player, value);
            }
	}
    
    @EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
    public void onPlayerLeave(PlayerQuitEvent event)
    {
        Player player = event.getPlayer();
        int value = chrysus.economy.getWalletValue(player);
        String playerId = player.getUniqueId().toString();
        String queryind = "UPDATE WALLET SET value = " + Integer.toString(value) + " WHERE player = '" + playerId + "'"; 
        chrysus.sqlengine.StoreTwo(queryind);
        chrysus.economy.deactivateWallet(player);
    }
    
    
}
