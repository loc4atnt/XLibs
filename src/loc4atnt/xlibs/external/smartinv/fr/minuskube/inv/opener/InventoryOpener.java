package loc4atnt.xlibs.external.smartinv.fr.minuskube.inv.opener;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

import loc4atnt.xlibs.external.smartinv.fr.minuskube.inv.ClickableItem;
import loc4atnt.xlibs.external.smartinv.fr.minuskube.inv.SmartInventory;
import loc4atnt.xlibs.external.smartinv.fr.minuskube.inv.content.InventoryContents;

public interface InventoryOpener {

	Inventory open(SmartInventory inv, Player player, boolean isAsync);

	boolean supports(InventoryType type);

	default void fill(Inventory handle, InventoryContents contents) {
		ClickableItem[][] items = contents.all();

		for (int row = 0; row < items.length; row++) {
			for (int column = 0; column < items[row].length; column++) {
				if (items[row][column] != null)
					handle.setItem(9 * row + column, items[row][column].getItem());
			}
		}
	}

}
