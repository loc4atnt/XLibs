package loc4atnt.xlibs.external.smartinv.fr.minuskube.inv.opener;

import loc4atnt.xlibs.external.smartinv.fr.minuskube.inv.InventoryManager;
import loc4atnt.xlibs.external.smartinv.fr.minuskube.inv.SmartInventory;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;

public class ChestInventoryOpener implements InventoryOpener {

    @Override
    public Inventory open(SmartInventory inv, Player player) {
        if(inv.getColumns() != 9)
        	player.sendMessage("The column count for the chest inventory must be 9, found: "+String.valueOf(inv.getColumns()));
        if(inv.getRows() < 1 || inv.getRows() > 6)
        	player.sendMessage("The row count for the chest inventory must be between 1 and 6, found: "+String.valueOf(inv.getRows()));

        InventoryManager manager = inv.getManager();
        Inventory handle = Bukkit.createInventory(player, inv.getRows() * inv.getColumns(), inv.getTitle());

        fill(handle, manager.getContents(player).get());

        player.openInventory(handle);
        return handle;
    }

    @Override
    public boolean supports(InventoryType type) {
        return type == InventoryType.CHEST || type == InventoryType.ENDER_CHEST;
    }

}
