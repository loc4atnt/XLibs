package loc4atnt.xlibs.inv.MenuConfigX;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import loc4atnt.xlibs.config.SimpleConfig;
import loc4atnt.xlibs.item.ItemIdFormat;
import loc4atnt.xlibs.item.ItemX;
import loc4atnt.xlibs.stringutil.Color;

public class MenuConfigX {

	private final String menuName;
	private final int rows;
	private final HashMap<String, MenuSlots> slotMap;

	@SuppressWarnings("unchecked")
	public MenuConfigX(SimpleConfig cfg, String path) {
		this.menuName = Color.convert(cfg.getString(path + ".name", "NULL"));
		this.rows = cfg.getInt(path + ".row", 6);
		this.slotMap = new HashMap<String, MenuSlots>();

		if (cfg.contains(path + ".item"))
			for (String key : cfg.getConfigurationSection(path + ".item").getKeys(false)) {
				ItemX item = getItemFromConfig(cfg, path + "." + key);
				List<Integer> slots = (List<Integer>) cfg.getList(path + "." + key + ".slots",
						new ArrayList<Integer>());
				MenuSlots menuSlot = new MenuSlots(item, slots);
				slotMap.put(key, menuSlot);
			}
	}

	public String getMenuName() {
		return menuName;
	}

	public int getRows() {
		return rows;
	}

	public HashMap<String, MenuSlots> getSlotMap() {
		return slotMap;
	}

	/**
	 * path.name; path.lore; path.id;
	 * 
	 * @return ItemStack với name, lore, id lấy từ config
	 */
	@SuppressWarnings("unchecked")
	public static ItemX getItemFromConfig(SimpleConfig cfg, String path) {
		List<String> colorLore = Color
				.convertRawToColorLore((List<String>) cfg.getList(path + ".lore", new ArrayList<String>()));
		String name = Color.convert(cfg.getString(path + ".name", ""));
		String idFormat = cfg.getString(path + ".id", "2");
		ItemIdFormat itemId = ItemIdFormat.getItemIdFormat(idFormat);
		ItemX itemX;
		itemX = new ItemX(itemId.getTypeId(), 1, (short) 1, itemId.getData());
		itemX.setName(name);
		itemX.setLore(colorLore);
		return itemX;
	}
}
