package loc4atnt.xlibs.external.smartinv.fr.minuskube.inv.opener;

import com.google.common.collect.ImmutableList;

import loc4atnt.xlibs.external.smartinv.fr.minuskube.inv.InventoryManager;
import loc4atnt.xlibs.external.smartinv.fr.minuskube.inv.SmartInventory;
import loc4atnt.xlibs.main.XLibs;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import java.util.List;

public class SpecialInventoryOpener implements InventoryOpener {

	private static final List<InventoryType> SUPPORTED = ImmutableList.of(InventoryType.FURNACE,
			InventoryType.WORKBENCH, InventoryType.DISPENSER, InventoryType.DROPPER, InventoryType.ENCHANTING,
			InventoryType.BREWING, InventoryType.ANVIL, InventoryType.BEACON, InventoryType.HOPPER);

	@Override
	public Inventory open(SmartInventory inv, Player player, boolean isAsync) {
		InventoryManager manager = inv.getManager();
		Inventory handle = Bukkit.createInventory(player, inv.getType(), inv.getTitle());

		if (isAsync) {
			Bukkit.getScheduler().runTaskAsynchronously(XLibs.getInst(), new Runnable() {

				@Override
				public void run() {
					fill(handle, manager.getContents(player).get());
					Bukkit.getScheduler().scheduleSyncDelayedTask(XLibs.getInst(), new Runnable() {

						@Override
						public void run() {
							player.openInventory(handle);
						}
					});
				}
			});
		} else {
			fill(handle, manager.getContents(player).get());
			player.openInventory(handle);
		}
		return handle;
	}

	@Override
	public boolean supports(InventoryType type) {
		return SUPPORTED.contains(type);
	}

}
