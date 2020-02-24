package loc4atnt.xlibs.money;

import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import loc4atnt.xlibs.main.XLibs;
import net.milkbowl.vault.economy.Economy;

public class MoneyManager {

	private static MoneyManager inst;
	private Economy api;

	public MoneyManager() {
		inst = this;
		RegisteredServiceProvider<Economy> economyProvider = XLibs.getInst().getServer().getServicesManager()
				.getRegistration(net.milkbowl.vault.economy.Economy.class);
		if (economyProvider != null) {
			api = economyProvider.getProvider();
		}
	}

	public static MoneyManager getInst() {
		return inst;
	}
	
	public void give(Player p, double amount) {
		api.depositPlayer(p, amount);
	}
	
	public void take(Player p, double amount) {
		api.withdrawPlayer(p, amount);
	}
	
	public double get(Player p) {
		return api.getBalance(p);
	}
	
	public long getLong(Player p) {
		return (long) get(p);
	}
}
