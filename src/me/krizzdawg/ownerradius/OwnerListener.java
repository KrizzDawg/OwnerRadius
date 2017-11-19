package me.krizzdawg.ownerradius;

import javax.swing.plaf.basic.BasicInternalFrameTitlePane.MaximizeAction;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import net.md_5.bungee.api.ChatColor;

public class OwnerListener implements Listener {

	@EventHandler
	public void onCommandUsage(PlayerCommandPreprocessEvent event) {
		if (!event.getMessage().toLowerCase().startsWith("/f owner "))
			return;

		event.setCancelled(true);
		Player player = event.getPlayer();
		String input = event.getMessage().split(" ")[2];
		Integer inputRadius = Util.tryParseInt(input);

		if (inputRadius == null) {
			player.sendMessage(
					ChatColor.translateAlternateColorCodes('&', Lang.INVALID_RADIUS.replace("%input%", input)));
			return;
		}

		Integer radius = inputRadius > OwnerRadius.getMaxRadius() ? OwnerRadius.getMaxRadius() : inputRadius;

		OwnerRadius.ownerChunksInRadius(player, radius);

		if (inputRadius > OwnerRadius.getMaxRadius()) {
			player.sendMessage(ChatColor.translateAlternateColorCodes('&',
					Lang.RADIUS_TOO_LARGE.replace("%maxradius%", String.valueOf(OwnerRadius.getMaxRadius()))));
		}

	}

}
