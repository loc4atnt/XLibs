package loc4atnt.xlibs.external.wgrevent;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import loc4atnt.xlibs.external.wgrevent.events.RegionEnterEvent;
import loc4atnt.xlibs.external.wgrevent.events.RegionEnteredEvent;
import loc4atnt.xlibs.external.wgrevent.events.RegionLeaveEvent;
import loc4atnt.xlibs.external.wgrevent.events.RegionLeftEvent;
import loc4atnt.xlibs.main.XLibs;

public class WGREventListener implements Listener {

	private HashMap<Player, Set<ProtectedRegion>> playerRegions;

	public WGREventListener() {
		this.playerRegions = new HashMap<Player, Set<ProtectedRegion>>();
	}

	@EventHandler
	public void onPlayerKick(PlayerKickEvent e) {
		Set<ProtectedRegion> regions = this.playerRegions.remove(e.getPlayer());
		if (regions != null) {
			for (ProtectedRegion region : regions) {
				RegionLeaveEvent leaveEvent = new RegionLeaveEvent(region, e.getPlayer(), MovementWay.DISCONNECT, e);
				RegionLeftEvent leftEvent = new RegionLeftEvent(region, e.getPlayer(), MovementWay.DISCONNECT, e);
				XLibs.getInst().getServer().getPluginManager().callEvent(leaveEvent);
				XLibs.getInst().getServer().getPluginManager().callEvent(leftEvent);
			}
		}
	}

	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent e) {
		Set<ProtectedRegion> regions = this.playerRegions.remove(e.getPlayer());
		if (regions != null) {
			for (ProtectedRegion region : regions) {
				RegionLeaveEvent leaveEvent = new RegionLeaveEvent(region, e.getPlayer(), MovementWay.DISCONNECT, e);
				RegionLeftEvent leftEvent = new RegionLeftEvent(region, e.getPlayer(), MovementWay.DISCONNECT, e);
				XLibs.getInst().getServer().getPluginManager().callEvent(leaveEvent);
				XLibs.getInst().getServer().getPluginManager().callEvent(leftEvent);
			}
		}
	}

	@EventHandler
	public void onPlayerMove(PlayerMoveEvent e) {
		e.setCancelled(updateRegions(e.getPlayer(), MovementWay.MOVE, e.getTo(), e));
	}

	@EventHandler
	public void onPlayerTeleport(PlayerTeleportEvent e) {
		Location fromLocation = e.getFrom();
		Location toLocation = e.getTo();
		if (fromLocation.getWorld().equals(toLocation.getWorld())) {
			e.setCancelled(updateRegions(e.getPlayer(), MovementWay.TELEPORT_INSIDE_WORLD, e.getTo(), e));
		} else {
			e.setCancelled(updateRegionsWhenChangeWorld(e.getPlayer(), MovementWay.TELEPORT_OUTSIDE_WORLD, e));
		}
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		updateRegions(e.getPlayer(), MovementWay.SPAWN, e.getPlayer().getLocation(), e);
	}

	@EventHandler
	public void onPlayerRespawn(PlayerRespawnEvent e) {
		updateRegions(e.getPlayer(), MovementWay.SPAWN, e.getRespawnLocation(), e);
	}

	private synchronized boolean updateRegions(final Player player, final MovementWay movement, Location to,
			final PlayerEvent event) {
		Set<ProtectedRegion> regions;
		if (this.playerRegions.get(player) == null) {
			regions = new HashSet<ProtectedRegion>();
		} else {
			regions = new HashSet<ProtectedRegion>((Collection<ProtectedRegion>) this.playerRegions.get(player));
		}
		Set<ProtectedRegion> oldRegions = new HashSet<ProtectedRegion>(regions);

		RegionManager rm = XLibs.getInst().getNMS().getWGRegionManager(to.getWorld());
		if (rm == null) {
			return false;
		}
		ApplicableRegionSet appRegions = XLibs.getInst().getNMS().getApplicableRegionSet(to);
		for (final ProtectedRegion region : appRegions) {
			if (!regions.contains(region)) {
				RegionEnterEvent e = new RegionEnterEvent(region, player, movement, event);
				XLibs.getInst().getServer().getPluginManager().callEvent(e);
				if (e.isCancelled()) {
					regions.clear();
					regions.addAll(oldRegions);

					return true;
				}
				Bukkit.getScheduler().runTaskLater(XLibs.getInst(), new Runnable() {
					public void run() {
						RegionEnteredEvent e = new RegionEnteredEvent(region, player, movement, event);
						XLibs.getInst().getServer().getPluginManager().callEvent(e);
					}
				}, 1L);
				regions.add(region);
			}
		}
		Collection<ProtectedRegion> app = appRegions.getRegions();
		Iterator<ProtectedRegion> itr = regions.iterator();
		while (itr.hasNext()) {
			final ProtectedRegion region = (ProtectedRegion) itr.next();
			if (!app.contains(region)) {
				if (rm.getRegion(region.getId()) != region) {
					itr.remove();
				} else {
					RegionLeaveEvent e = new RegionLeaveEvent(region, player, movement, event);
					XLibs.getInst().getServer().getPluginManager().callEvent(e);
					if (e.isCancelled()) {
						regions.clear();
						regions.addAll(oldRegions);
						return true;
					}
					Bukkit.getScheduler().runTaskLater(XLibs.getInst(), new Runnable() {
						public void run() {
							RegionLeftEvent e = new RegionLeftEvent(region, player, movement, event);
							XLibs.getInst().getServer().getPluginManager().callEvent(e);
						}
					}, 1L);
					itr.remove();
				}
			}
		}
		this.playerRegions.put(player, regions);
		return false;
	}

	private synchronized boolean updateRegionsWhenChangeWorld(final Player p, final MovementWay movement,
			final PlayerTeleportEvent event) {
		Location toLoca = event.getTo();

		Set<ProtectedRegion> oldRegions;
		if (this.playerRegions.get(p) == null) {
			oldRegions = new HashSet<ProtectedRegion>();
		} else {
			oldRegions = new HashSet<ProtectedRegion>((Collection<ProtectedRegion>) this.playerRegions.get(p));
		}

		for (ProtectedRegion rg : oldRegions) {
			RegionLeaveEvent e = new RegionLeaveEvent(rg, p, movement, event);
			XLibs.getInst().getServer().getPluginManager().callEvent(e);
			if (e.isCancelled()) {
				return true;
			}
			Bukkit.getScheduler().runTaskLater(XLibs.getInst(), new Runnable() {
				public void run() {
					RegionLeftEvent e = new RegionLeftEvent(rg, p, movement, event);
					XLibs.getInst().getServer().getPluginManager().callEvent(e);
				}
			}, 1L);
		}

		Set<ProtectedRegion> newRegions = new HashSet<ProtectedRegion>();
		RegionManager regionToManager = XLibs.getInst().getNMS().getWGRegionManager(toLoca.getWorld());
		if (regionToManager != null) {
			ApplicableRegionSet appRegionsLocaTo = XLibs.getInst().getNMS().getApplicableRegionSet(toLoca);
			;
			for (ProtectedRegion rg : appRegionsLocaTo) {
				RegionEnterEvent e = new RegionEnterEvent(rg, p, movement, event);
				XLibs.getInst().getServer().getPluginManager().callEvent(e);
				if (e.isCancelled()) {
					return true;
				}
				Bukkit.getScheduler().runTaskLater(XLibs.getInst(), new Runnable() {
					public void run() {
						RegionEnteredEvent e = new RegionEnteredEvent(rg, p, movement, event);
						XLibs.getInst().getServer().getPluginManager().callEvent(e);
					}
				}, 1L);
				newRegions.add(rg);
			}
		}

		this.playerRegions.put(p, newRegions);
		return false;
	}
}
