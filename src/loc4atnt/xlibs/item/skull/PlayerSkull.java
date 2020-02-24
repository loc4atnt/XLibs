package loc4atnt.xlibs.item.skull;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import loc4atnt.xlibs.main.XLibs;

public class PlayerSkull {

	private ItemStack item;

	public PlayerSkull(Player owner) {
		item = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		XLibs.getInst().getNMS().setOwnerSkull(item, owner);
	}

	public PlayerSkull(boolean isItem, Player owner) {
		item = new ItemStack((isItem) ? Material.SKULL_ITEM : Material.SKULL, 1, (short) 3);
		XLibs.getInst().getNMS().setOwnerSkull(item, owner);
	}

	public PlayerSkull(boolean isItem, String playerName) {
		item = new ItemStack((isItem) ? Material.SKULL_ITEM : Material.SKULL, 1, (short) 3);
		XLibs.getInst().getNMS().setOwnerSkull(item, playerName);
	}

	public ItemStack toItem() {
		return item;
	}
}
