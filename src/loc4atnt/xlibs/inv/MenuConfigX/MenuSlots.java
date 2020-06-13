package loc4atnt.xlibs.inv.MenuConfigX;

import java.util.ArrayList;
import java.util.List;

import loc4atnt.xlibs.item.ItemX;

public class MenuSlots {

	private final ItemX item;
	private final List<Integer> slots;

	public MenuSlots(ItemX item, List<Integer> slots) {
		this.item = item;
		this.slots = new ArrayList<Integer>(slots);
	}

	public ItemX getItem() {
		return item;
	}

	public List<Integer> getSlots() {
		return slots;
	}

	public void replacePlaceholder(PlaceholderReplace... replaceList) {
		List<String> lore = item.getLore();
		for (int i = 0; i < lore.size(); i++) {
			String line = lore.get(i);
			for (PlaceholderReplace replace : replaceList)
				while (line.contains(replace.getPlaceholder()))
					line = line.replace(replace.getPlaceholder(), replace.getReplace());
			lore.set(i, line);
		}
		item.setLore(lore);
	}
}
