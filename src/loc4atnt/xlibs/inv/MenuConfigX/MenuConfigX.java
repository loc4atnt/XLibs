package loc4atnt.xlibs.inv.MenuConfigX;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Material;

import loc4atnt.xlibs.config.SimpleConfig;
import loc4atnt.xlibs.external.smartinv.fr.minuskube.inv.content.SlotPos;
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
				ItemX item = getItemFromConfig(cfg, path + ".item." + key);
				List<Integer> slots = (List<Integer>) cfg.getList(path + ".item." + key + ".slots",
						new ArrayList<Integer>());
				String skinHead = cfg.getString(path + ".item." + key + ".skin");
				MenuSlots menuSlot = new MenuSlots(item, slots, skinHead);
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
		String id = cfg.getString(path + ".id", "STONE");
		ItemX itemX;
		Material m = Material.matchMaterial(id);
		if (m == null)
			m = Material.DIRT;
		itemX = new ItemX(m, 1);
		itemX.setName(name);
		itemX.setLore(colorLore);
		return itemX;
	}

	public static SlotPos convertIndexToPos(int index) {
		int row = ((int) index / 9);
		int column = index % 9;
		return new SlotPos(row, column);
	}
}
