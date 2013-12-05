package loc4atnt.xlibs.main;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import loc4atnt.xlibs.item.skull.PlayerSkull;

public class TestCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] arg) {
		Player p = Bukkit.getPlayer("profun");
		PlayerSkull skull = new PlayerSkull(arg[0]);
		p.getInventory().addItem(skull.toItem());
		return true;
	}
}
