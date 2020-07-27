package loc4atnt.xlibs.inv.MenuConfigX;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import loc4atnt.xlibs.item.ItemX;
import loc4atnt.xlibs.main.XLibs;

public class MenuSlots {

	private final ItemX item;
	private final List<Integer> slots;
	private String skinHead;

	public MenuSlots(ItemX item, List<Integer> slots, String skinHead) {
		this.item = item;
		this.slots = new ArrayList<Integer>(slots);
		this.skinHead = skinHead;
	}
	
	public MenuSlots(List<Integer> slots) {
		this.item = null;
		this.slots = new ArrayList<Integer>(slots);
		this.skinHead = null;
	}

	public MenuSlots(MenuSlots menuSlots) {
		this.item = new ItemX(menuSlots.item);
		this.slots = new ArrayList<Integer>(menuSlots.slots);
		this.skinHead = ((menuSlots.skinHead == null) ? null : new String(menuSlots.skinHead));
	}

	public ItemX getItem(Player clicker) {
		if (skinHead == null || (!item.getType().equals(Material.PLAYER_HEAD)))
			return item;

		ItemStack itemStack = item.toItemStack();
		String clone = new String(skinHead);
		while (clone.contains("{player}"))
			clone = clone.replace("{player}", clicker.getName());
		XLibs.getInst().getNMS().setOwnerSkull(itemStack, clone);
		return new ItemX(itemStack);
	}

	public List<Integer> getSlots() {
		return slots;
	}

	public MenuSlots replacePlaceholder(PlaceholderReplace... replaceList) {
		MenuSlots newMenuSlots = new MenuSlots(this);
		newMenuSlots.replacePlaceholderForce(replaceList);
		return newMenuSlots;
	}

	private void replacePlaceholderForce(PlaceholderReplace... replaceList) {
		List<String> lore = item.getLore();
		for (int i = 0; i < lore.size(); i++) {
			String line = lore.get(i);
			for (PlaceholderReplace replace : replaceList)
				while (line.contains(replace.getPlaceholder()))
					line = line.replace(replace.getPlaceholder(), replace.getReplace());
			lore.set(i, line);
		}
		item.setLore(lore);

		String name = item.getName();
		for (PlaceholderReplace replace : replaceList)
			while (name.contains(replace.getPlaceholder()))
				name = name.replace(replace.getPlaceholder(), replace.getReplace());
		item.setName(name);

		if (skinHead != null)
			for (PlaceholderReplace replace : replaceList)
				while (skinHead.contains(replace.getPlaceholder()))
					skinHead = skinHead.replace(replace.getPlaceholder(), replace.getReplace());
	}
}
