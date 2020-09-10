package loc4atnt.xlibs.item;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import loc4atnt.xlibs.main.XLibs;
import loc4atnt.xlibs.thread.ThreadUtil;

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

	public static void giveItemToPlayerOrWarnCleanInv(Player p, ItemStack item) {
		giveItemToPlayerOrWarnCleanInv(p, item, 1);
	}

	public static void giveItemToPlayerOrWarnCleanInv(Player p, ItemStack item, int giveTimes) {
		PlayerInventory inv = p.getInventory();
		if (inv.firstEmpty() != -1) {
			inv.addItem(item);
		} else {
			XLibs.getInst().getServer().getScheduler().runTaskAsynchronously(XLibs.getInst(), new Runnable() {

				@Override
				public void run() {
					for (int i = 0; i < giveTimes; i++) {
						while (inv.firstEmpty() == -1) {
							p.sendMessage("§cTúi đồ của bạn đã đầy! Cất hoặc vứt bớt đồ để nhận vật phẩm!");
							ThreadUtil.delay(3000);
						}
						inv.addItem(item);
					}
				}
			});
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

	public static boolean checkEnoughAmountOfItem(ItemStack removedItem, int amount, Player p) {
		PlayerInventory inv = p.getInventory();
		ItemStack invItem[] = inv.getContents();
		for (ItemStack item : invItem) {
			if (item == null)
				continue;
			if (item.getType().equals(Material.AIR))
				continue;
			if (item.isSimilar(removedItem)) {
				int itemAmount = item.getAmount();
				if (itemAmount > amount)
					amount = 0;
				else
					amount -= itemAmount;
			}
			if (amount <= 0)
				return true;
		}
		return false;
	}

//	@SuppressWarnings("deprecation")
//	public static boolean isArmor(ItemStack item) {
//		if (item == null || (item.getType().equals(Material.AIR)))
//			return false;
//
//		int typeId = item.getTypeId();
//		return (typeId >= 298 && typeId <= 317);
//	}
}
