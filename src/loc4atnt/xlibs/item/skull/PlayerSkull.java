package loc4atnt.xlibs.item.skull;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import loc4atnt.xlibs.main.XLibs;

public class PlayerSkull {

	private ItemStack item;

	public PlayerSkull(Player owner) {
		item = new ItemStack(Material.PLAYER_HEAD);
		XLibs.getInst().getNMS().setOwnerSkull(item, owner);
	}

	public PlayerSkull(String playerName) {
		item = new ItemStack(Material.PLAYER_HEAD);
		XLibs.getInst().getNMS().setOwnerSkull(item, playerName);
	}

	public ItemStack toItem() {
		return item;
	}
}
