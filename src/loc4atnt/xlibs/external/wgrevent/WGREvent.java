package loc4atnt.xlibs.external.wgrevent;

import org.bukkit.plugin.Plugin;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;

import loc4atnt.xlibs.main.XLibs;

public class WGREvent {

	private WGREventListener listener;
	private WorldGuardPlugin wgPlugin;

	public void register() {
		this.wgPlugin = getWGPlugin();
		if (this.wgPlugin == null) {
			XLibs.getInst().getLogger().warning("Could not find WorldGuard, disabling WorldGuardEvent - DOL.");
			return;
		}
		this.listener = new WGREventListener(this.wgPlugin);
		XLibs.getInst().getServer().getPluginManager().registerEvents(this.listener, this.wgPlugin);
	}

	private WorldGuardPlugin getWGPlugin() {
		Plugin plugin = XLibs.getInst().getServer().getPluginManager().getPlugin("WorldGuard");
		if ((plugin == null) || (!(plugin instanceof WorldGuardPlugin))) {
			return null;
		}
		return (WorldGuardPlugin) plugin;
	}
}