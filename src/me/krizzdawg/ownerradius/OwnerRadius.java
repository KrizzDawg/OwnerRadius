package me.krizzdawg.ownerradius;

import org.bukkit.Chunk;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.plugin.java.JavaPlugin;

import com.massivecraft.factions.Board;
import com.massivecraft.factions.FLocation;
import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Faction;

public class OwnerRadius extends JavaPlugin implements Listener {

	private static int maxRadius;

	public void onEnable() {
		info();
		getServer().getPluginManager().registerEvents(new OwnerListener(), this);
		getConfig().options().copyDefaults(true);
		saveDefaultConfig();

		maxRadius = getConfig().getInt("maxRadius");
	}

	public static int getMaxRadius() {
		return maxRadius;
	}

	public boolean findHook() {
		return getServer().getPluginManager().getPlugin("Factions") != null;
	}

	public void info() {
		String prefix = "[" + this + "] ";
		getServer().getLogger().info("=============[" + this + "]=============");
		getServer().getLogger().info(prefix + "Searching for a factions plugin...");
		getServer().getLogger().info(prefix + (findHook() ? "A factions plugin has been found!"
				: "No factions plugin was found! stopping plugin..."));
		if (!findHook())
			getServer().getPluginManager().disablePlugin(this);
		else
			getServer().getLogger().info(prefix + "OwnerRadius has successfully started.");
	}

	public static void ownerChunksInRadius(Player player, int radius) {
		FPlayer fplayer = FPlayers.getInstance().getByPlayer(player);
		Faction faction = fplayer.getFaction();
		Chunk chunk = player.getLocation().getChunk();

		Integer maxX = chunk.getX() + radius, minX = chunk.getX() - radius, minZ = chunk.getZ() - radius,
				maxZ = chunk.getZ() + radius;

		if (faction.getFPlayerAdmin() != fplayer) {
			fplayer.sendMessage("§cYou must be the faction§l Admin§c to do this.");
			return;
		}

		if (!fplayer.isInOwnTerritory()) {
			fplayer.sendMessage("§cYou must be in your own faction territory to do this.");
			return;
		}


		
		for (int x = minX; x < maxX; x++) {
			for (int z = minZ; z < maxZ; z++) {
				Location chunkLocation = new Location(player.getWorld(), x * 16, 100, z * 16);
				FLocation factionLocation = new FLocation(chunkLocation);
				if (Board.getInstance().getFactionAt(factionLocation) == faction) {
					player.sendMessage("§eYou have ownered the chunk " + "§d (" + x + ", " + z + ")" + "§e.");
					faction.setPlayerAsOwner(fplayer, factionLocation);
				}
			}
		}
	}

}
