package loc4atnt.xlibs.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

public class ItemUtil {

	public static void giveToInvOrDrop(Player p, ItemStack item) {
		PlayerInventory inv = p.getInventory();
		int firstEmpty = inv.firstEmpty();
		if (firstEmpty < 0) {
			p.getWorld().dropItemNaturally(p.getLocation(), item);
		} else {
			inv.addItem(item);
		}
	}

	public static boolean removeAmountOfItemFromPlayerInv(ItemStack removedItem, int amount, Player p) {// true: remove
		PlayerInventory inv = p.getInventory();
		ItemStack invItem[] = inv.getContents();
		int index = -1;
		for (ItemStack item : invItem) {
			index++;
			if (item == null)
				continue;
			if (item.getType().equals(Material.AIR))
				continue;
			if (item.isSimilar(removedItem)) {
				int itemAmount = item.getAmount();
				if (itemAmount > amount) {
					item.setAmount(itemAmount - amount);
					amount = 0;
				} else {
					p.getInventory().clear(index);
					amount -= itemAmount;
				}
			}
			if (amount <= 0)
				return true;
		}
		return false;
	}
}
