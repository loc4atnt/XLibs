package loc4atnt.xlibs.external.wgrevent.events;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import loc4atnt.xlibs.external.wgrevent.MovementWay;

public abstract class RegionEvent extends PlayerEvent {
	private static final HandlerList handlerList = new HandlerList();
	private ProtectedRegion region;
	private MovementWay movement;
	public PlayerEvent parentEvent;

	public RegionEvent(ProtectedRegion region, Player player, MovementWay movement, PlayerEvent parent) {
		super(player);
		this.region = region;
		this.movement = movement;
		this.parentEvent = parent;
	}

	public HandlerList getHandlers() {
		return handlerList;
	}

	public ProtectedRegion getRegion() {
		return this.region;
	}

	public static HandlerList getHandlerList() {
		return handlerList;
	}

	public MovementWay getMovementWay() {
		return this.movement;
	}

	public PlayerEvent getParentEvent() {
		return this.parentEvent;
	}
}
