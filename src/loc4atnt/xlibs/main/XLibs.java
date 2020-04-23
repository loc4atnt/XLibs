package loc4atnt.xlibs.main;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import loc4atnt.xlibs.chat.InputViaChatManager;
import loc4atnt.xlibs.config.SimpleConfigManager;
import loc4atnt.xlibs.external.smartinv.fr.minuskube.inv.SmartInvsPlugin;
import loc4atnt.xlibs.external.wgrevent.WGREvent;
import loc4atnt.xlibs.item.SavingItemManager;
import loc4atnt.xlibs.item.command.SavingItemCommand;
import loc4atnt.xlibs.item.command.XNBTTagCommand;
import loc4atnt.xlibs.money.MoneyManager;
import loc4atnt.xlibs.mythicmobsutil.MMUtil;
import loc4atnt.xlibs.nms.NMS;
import loc4atnt.xlibs.nms.v_1_10_R1;
import loc4atnt.xlibs.nms.v_1_12_R1;
import loc4atnt.xlibs.playerpoints.PlayerPointsManager;

public class XLibs extends JavaPlugin {

	public static final String PERMISSION = "xlibs.*";
	private NMS nms;
	private static XLibs inst;
	private SimpleConfigManager cfgMnger;

	public SimpleConfigManager getCfgMnger() {
		return cfgMnger;
	}

	@Override
	public void onEnable() {
		inst = this;
		cfgMnger = new SimpleConfigManager(this);
		setupNMS();
		new SmartInvsPlugin().register();
		new WGREvent().register();
		new SavingItemManager();
		new PlayerPointsManager();
		new MoneyManager();
		if (getServer().getPluginManager().getPlugin("MythicMobs") != null)
			new MMUtil();
		new InputViaChatManager();

		getCommand("xtag").setExecutor(new XNBTTagCommand());
		getCommand("saveitem").setExecutor(new SavingItemCommand());
	}

	public static XLibs getInst() {
		return inst;
	}

	public NMS getNMS() {
		return nms;
	}

	private void setupNMS() {
		String version;
		try {
			version = Bukkit.getServer().getClass().getPackage().getName().replace(".", ",").split(",")[3];
			switch (version) {
			case "v1_10_R1":
				nms = new v_1_10_R1();
				break;
			case "v1_12_R1":
				nms = new v_1_12_R1();
				break;
			}
		} catch (ArrayIndexOutOfBoundsException exn) {
			exn.printStackTrace();
		}
	}

	public Plugin getPlugin(String string) {
		Plugin plugin = this.getServer().getPluginManager().getPlugin(string);
		return plugin;
	}
}
