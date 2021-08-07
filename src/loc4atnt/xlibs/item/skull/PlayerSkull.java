package loc4atnt.xlibs.item.skull;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import loc4atnt.xlibs.main.XLibs;

public class PlayerSkull {

	private ItemStack item;

	public PlayerSkull(Player owner) {
		item = XLibs.getInst().getNMS().getSkullItem(true);
		XLibs.getInst().getNMS().setOwnerSkull(item, owner);
	}

	public PlayerSkull(String playerName) {
		item = XLibs.getInst().getNMS().getSkullItem(true);
		XLibs.getInst().getNMS().setOwnerSkull(item, playerName);
	}

	public PlayerSkull(boolean isItem, String playerName) {
		item = XLibs.getInst().getNMS().getSkullItem(isItem);
		XLibs.getInst().getNMS().setOwnerSkull(item, playerName);
	}

	public PlayerSkull(boolean isItem, Player owner) {
		item = XLibs.getInst().getNMS().getSkullItem(isItem);
		XLibs.getInst().getNMS().setOwnerSkull(item, owner);
	}

	public ItemStack toItem() {
		return item;
	}
}
