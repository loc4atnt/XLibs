package loc4atnt.xlibs.item.command;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import loc4atnt.xlibs.item.ItemX;

public class XNBTTagCommand implements CommandExecutor {

	@SuppressWarnings("deprecation")
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] arg) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§cIngame only!");
			return true;
		}

		Player p = (Player) sender;
		if ((p.getItemInHand() == null) || (p.getItemInHand().getType().equals(Material.AIR))) {
			sender.sendMessage("§cNothing in hand!");
			return true;
		}

		ItemX itemX = new ItemX(p.getItemInHand());

		if ((arg.length == 3) && (arg[0].equalsIgnoreCase("val"))) {
			if (arg[1].equalsIgnoreCase("int")) {
				int val = itemX.getIntFromNBTTag(arg[2], -1);
				p.sendMessage("§bValue: " + val);
				return true;
			} else if (arg[1].equalsIgnoreCase("string")) {
				String val = itemX.getStringFromNBTTag(arg[2], "NULL");
				p.sendMessage("§bValue: " + val);
				return true;
			} else if (arg[1].equalsIgnoreCase("intlist")) {
				String val = "";
				List<Integer> listInt = itemX.getListIntFromNBTTag(arg[2], null);
				for (int i = 0; i < (listInt.size() - 1); i++) {
					val += listInt.get(i).toString() + ", ";
				}
				val += listInt.get(listInt.size() - 1).toString();
				p.sendMessage("§bValue: " + val);
				return true;
			}
		} else if ((arg.length == 3) && (arg[0].equalsIgnoreCase("addint"))) {
			try {
				int val = Integer.parseInt(arg[2]);
				itemX.setIntToNBTTag(arg[1], val);
				p.setItemInHand(itemX.toItemStack());
				p.sendMessage("§aXong!");
			} catch (NumberFormatException e) {
				p.sendMessage("§cError Value!");
			}
			return true;
		} else if ((arg.length == 3) && (arg[0].equalsIgnoreCase("addstring"))) {
			itemX.setStringToNBTTag(arg[1], arg[2]);
			p.setItemInHand(itemX.toItemStack());
			p.sendMessage("§aXong!");
			return true;
		} else if ((arg.length >= 3) && (arg[0].equalsIgnoreCase("addintlist"))) {
			try {
				List<Integer> val = new ArrayList<Integer>();
				for (int i = 2; i < arg.length; i++) {
					int number = Integer.parseInt(arg[i]);
					val.add(number);
				}
				itemX.setListIntToNBTTag(arg[1], val);
				p.setItemInHand(itemX.toItemStack());
				p.sendMessage("§aXong!");
			} catch (NumberFormatException e) {
				p.sendMessage("§cError Value!");
			}
			return true;
		}

		sender.sendMessage("§a/xtag");
		sender.sendMessage("§a/xtag addint <tag> <value>");
		sender.sendMessage("§a/xtag addstring <tag> <value>");
		sender.sendMessage("§a/xtag addintlist <tag> <value_0> <value_1> ... <value_n>");
		sender.sendMessage("§a/xtag val <int/string/intlist> <tag>");
		return true;
	}
}
