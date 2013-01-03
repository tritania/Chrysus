package org.tritania.chrysus;

import java.lang.Math;
import java.lang.String;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.bukkit.block.BlockState;
import org.bukkit.Chunk;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockFromToEvent;
import org.bukkit.event.entity.EntityCreatePortalEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Location;
import org.bukkit.plugin.PluginManager;
import org.bukkit.PortalType;
import org.bukkit.World;
import org.bukkit.World.Environment;

public class ChrysusListener implements Listener
{
	private Chrysus plugin;

	public ChrysusListener(Chrysus plugin)
	{
		this.plugin = plugin;
	}
	
	public void register()
	{
		PluginManager manager;
		
		manager = plugin.getServer().getPluginManager();
		manager.registerEvents(this, plugin);
	} 
	
	@EventHandler(priority = EventPriority.LOW, ignoreCancelled = true)
	public void onPlayerJoin(PlayerJoinEvent event)
	{
		//will alret player if any of his orders have been filled
	}
}
