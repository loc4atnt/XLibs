package loc4atnt.xlibs.item;

import java.util.HashMap;

import org.bukkit.inventory.ItemStack;

import loc4atnt.xlibs.config.SimpleConfig;
import loc4atnt.xlibs.main.XLibs;

public class SavingItemManager {
	
	private static SavingItemManager inst;
	private SimpleConfig cfg;
	private HashMap<String,ItemStack> itemMap = new HashMap<String,ItemStack>();
	
	public static SavingItemManager getInst() {
		return inst;
	}
	
	public SavingItemManager() {
		inst = this;
		cfg = XLibs.getInst().getCfgMnger().getNewConfig("key_item.yml");
		getItemFromConfig();
	}

	private void getItemFromConfig() {
		itemMap.clear();
		for(String key:cfg.getConfigurationSection("").getKeys(false)) {
			ItemStack item = (ItemStack) cfg.get(key);
			itemMap.put(key, item);
		}
	}

	public ItemStack getItem(String key) {
		ItemStack item = itemMap.getOrDefault(key, null);
		return (item!=null)?(new ItemStack(item)):null;
	}
	
	public boolean containsKey(String key) {
		return itemMap.containsKey(key);
	}
	
	public void setItem(String key, ItemStack item) {
		itemMap.put(key, item);
		cfg.set(key, item);
		cfg.saveConfig();
	}
	
	public void removeKey(String key) {
		cfg.removeKey(key);
		itemMap.remove(key);
		cfg.saveConfig();
	}
}
