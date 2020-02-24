package loc4atnt.xlibs.playerpoints;

import java.util.UUID;

import org.black_ixx.playerpoints.PlayerPoints;
import org.black_ixx.playerpoints.PlayerPointsAPI;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import loc4atnt.xlibs.main.XLibs;

public class PlayerPointsManager {

	private static PlayerPointsManager inst;
	private PlayerPointsAPI api;

	public static PlayerPointsManager getInst() {
		return inst;
	}

	public PlayerPointsManager() {
		inst = this;
		Plugin plugin = XLibs.getInst().getPlugin("PlayerPoints");
		PlayerPoints playerPoints = PlayerPoints.class.cast(plugin);
		api = playerPoints.getAPI();
	}

	@SuppressWarnings("deprecation")
	public boolean give(String playerName, int amount) {
		return api.give(playerName, amount);
	}

	public boolean give(UUID uuid, int amount) {
		return api.give(uuid, amount);
	}

	public boolean give(Player p, int amount) {
		return give(p.getUniqueId(), amount);
	}

	@SuppressWarnings("deprecation")
	public int check(String playerName) {
		return api.look(playerName);
	}

	public int check(UUID uuid) {
		return api.look(uuid);
	}

	public int check(Player p) {
		return api.look(p.getUniqueId());
	}

	@SuppressWarnings("deprecation")
	public boolean take(String playerName, int amount) {
		return api.take(playerName, amount);
	}

	public boolean take(UUID uuid, int amount) {
		return api.take(uuid, amount);
	}

	public boolean take(Player p, int amount) {
		return api.take(p.getUniqueId(), amount);
	}
}
