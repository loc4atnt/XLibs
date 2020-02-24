package loc4atnt.xlibs.inv;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import loc4atnt.xlibs.main.XLibs;
import loc4atnt.xlibs.thread.ThreadUtil;

public class PlayerInvUtil {

	public static void giveItemToPlayerOrWarnCleanInv(Player p, ItemStack item) {
		PlayerInventory inv = p.getInventory();
		if (inv.firstEmpty() != -1) {
			inv.addItem(item);
		} else {
			XLibs.getInst().getServer().getScheduler().runTaskAsynchronously(XLibs.getInst(), new Runnable() {

				@Override
				public void run() {
					while (inv.firstEmpty() == -1) {
						p.sendMessage("§cTúi đồ của bạn đã đầy! Cất hoặc vứt bớt đồ để nhận vật phẩm!");
						ThreadUtil.delay(3000);
					}
					inv.addItem(item);
				}
			});
		}
	}
}
