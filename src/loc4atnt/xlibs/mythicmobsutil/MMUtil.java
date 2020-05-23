package loc4atnt.xlibs.mythicmobsutil;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import io.lumine.xikage.mythicmobs.MythicMobs;
import io.lumine.xikage.mythicmobs.api.bukkit.BukkitAPIHelper;
import io.lumine.xikage.mythicmobs.api.exceptions.InvalidMobTypeException;
import io.lumine.xikage.mythicmobs.mobs.ActiveMob;
import loc4atnt.xlibs.main.XLibs;

public class MMUtil {

	private MythicMobs MythicMobPlugin;
	private static MMUtil inst;

	public MMUtil() {
		inst = this;
		Plugin plugin = XLibs.getInst().getServer().getPluginManager().getPlugin("MythicMobs");
		if (plugin != null)
			if (plugin instanceof MythicMobs)
				MythicMobPlugin = (MythicMobs) plugin;
	}

	public static MMUtil getInst() {
		return inst;
	}

	public BukkitAPIHelper getAPI() {
		return MythicMobPlugin.getAPIHelper();
	}

	public void spawnMythicMob(List<Entity> list, String mobTypeName, Location spawnMobLocation, Player p, int amount) {
		if (MythicMobPlugin == null)
			return;
		if (amount <= 0)
			return;
		BukkitAPIHelper api = MythicMobPlugin.getAPIHelper();
		try {
			for (int i = 0; i < amount; i++) {
				Entity mob = api.spawnMythicMob(mobTypeName, spawnMobLocation);
				if (list != null) {
					synchronized (list) {
						list.add(mob);
					}
				}
				if (p != null) {
					api.taunt(mob, p);
				}
			}
		} catch (InvalidMobTypeException e) {
			//
		}
	}

	public void taunt(Entity mob, LivingEntity target) {
		ActiveMob aM = MythicMobPlugin.getAPIHelper().getMythicMobInstance(mob);
		aM.resetTarget();
		MythicMobPlugin.getAPIHelper().taunt(mob, target);
	}

	public void spawnMythicMob(List<Entity> list, String mobTypeName, Location spawnMobLocation, Player p) {
		spawnMythicMob(list, mobTypeName, spawnMobLocation, p, 1);
	}

	public void spawnMythicMob(String mobTypeName, Location spawnMobLocation) {
		spawnMythicMob(null, mobTypeName, spawnMobLocation, null, 1);
	}

	public void spawnMythicMob(String mobTypeName, Location spawnMobLocation, Player p) {
		spawnMythicMob(null, mobTypeName, spawnMobLocation, p, 1);
	}

	public void spawnMythicMob(String mobTypeName, Location spawnMobLocation, Player p, int amount) {
		spawnMythicMob(null, mobTypeName, spawnMobLocation, p, amount);
	}

	public void spawnMythicMob(List<Entity> list, String mobTypeName, Location spawnMobLocation, int amount) {
		spawnMythicMob(list, mobTypeName, spawnMobLocation, null, amount);
	}
}
