package loc4atnt.xlibs.item.command;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import loc4atnt.xlibs.item.ItemUtil;
import loc4atnt.xlibs.item.SavingItemManager;
import loc4atnt.xlibs.main.XLibs;

public class SavingItemCommand implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] arg) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§cIngame!");
			return true;
		}
		Player p = (Player) sender;
		if (!p.hasPermission(XLibs.PERMISSION)) {
			p.sendMessage("§cAdmin only!");
			return true;
		}

		if (arg.length == 2) {
			if (arg[0].equalsIgnoreCase("save")) {
				if (p.getItemInHand() == null || p.getItemInHand().getType().equals(Material.AIR)) {
					sender.sendMessage("§cNothing in hand!");
					return true;
				}
				ItemStack item = p.getItemInHand().clone();
				item.setAmount(1);
				SavingItemManager.getInst().setItem(arg[1], item);
				p.sendMessage("§aXong!");
				return true;
			} else if (arg[0].equalsIgnoreCase("remove")) {
				SavingItemManager.getInst().removeKey(arg[1]);
				p.sendMessage("§aXong!");
				return true;
			} else if (arg[0].equalsIgnoreCase("get")) {
				ItemStack item = SavingItemManager.getInst().getItem(arg[1]);
				ItemUtil.giveItemToPlayerOrWarnCleanInv(p, item);
				p.sendMessage("§aXong!");
				return true;
			}
		}

		sender.sendMessage("§a/saveitem save <id>: Save item in hand with id");
		sender.sendMessage("§a/saveitem remove <id>: Remove item with id");
		sender.sendMessage("§a/saveitem get <id>: Get item with id");
		return true;
	}
}
