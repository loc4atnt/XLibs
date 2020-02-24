package loc4atnt.xlibs.external.wgrevent.events;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.player.PlayerEvent;

import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import loc4atnt.xlibs.external.wgrevent.MovementWay;

public class RegionLeaveEvent
  extends RegionEvent
  implements Cancellable
{
  private boolean cancelled;
  private boolean cancellable;
  
  public RegionLeaveEvent(ProtectedRegion region, Player player, MovementWay movement, PlayerEvent parent)
  {
    super(region, player, movement, parent);
    this.cancelled = false;
    this.cancellable = true;
    if ((movement == MovementWay.SPAWN) || 
      (movement == MovementWay.DISCONNECT)) {
      this.cancellable = false;
    }
  }
  
  public void setCancelled(boolean cancelled)
  {
    if (!this.cancellable) {
      return;
    }
    this.cancelled = cancelled;
  }
  
  public boolean isCancelled()
  {
    return this.cancelled;
  }
  
  public boolean isCancellable()
  {
    return this.cancellable;
  }
  
  protected void setCancellable(boolean cancellable)
  {
    this.cancellable = cancellable;
    if (!this.cancellable) {
      this.cancelled = false;
    }
  }
}
