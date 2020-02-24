package loc4atnt.xlibs.item;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import loc4atnt.xlibs.main.XLibs;
import loc4atnt.xlibs.nms.NMS;

public class ItemX {

	private ItemStack itemStack;
	private NMS nms = XLibs.getInst().getNMS();

	@SuppressWarnings("deprecation")
	public ItemX(int typeId, int amount) {
		this.itemStack = new ItemStack(typeId, amount);
	}

	@SuppressWarnings("deprecation")
	public ItemX(int typeId, int amount, short damage, byte data) {
		this.itemStack = new ItemStack(typeId, amount, damage, data);
	}

	public ItemX(Material material, int amount) {
		this.itemStack = new ItemStack(material, amount);
	}

	public ItemX(Material material) {
		this.itemStack = new ItemStack(material, 1);
	}

	@SuppressWarnings("deprecation")
	public ItemX(Material material, int amount, short damage, byte data) {
		this.itemStack = new ItemStack(material, amount, damage, data);
	}

	public ItemX(ItemStack item) {
		this.itemStack = new ItemStack(item);
	}

	public ItemX(ItemX itemX) {
		this.itemStack = new ItemStack(itemX.itemStack);
	}

	private ItemX(ItemStack item, Object o) {
		this.itemStack = item;
	}

	public static ItemX toItemX(ItemStack item) {
		return new ItemX(item, null);
	}

	public ItemStack toItemStack() {
		return this.itemStack;
	}

	public int getDura() {
		return itemStack.getDurability();
	}

	public ItemX setDurability(short dura) {
		itemStack.setDurability(dura);
		return this;
	}

	public ItemX setName(String name) {
		ItemMeta meta = itemStack.getItemMeta();
		meta.setDisplayName(name);
		itemStack.setItemMeta(meta);
		return this;
	}

	public String getName() {
		ItemMeta meta = itemStack.getItemMeta();
		return meta.getDisplayName();
	}

	public ItemX setLore(List<String> lore) {
		ItemMeta meta = itemStack.getItemMeta();
		meta.setLore(lore);
		itemStack.setItemMeta(meta);
		return this;
	}

	public ItemX addLoreLine(String... line) {
		ItemMeta meta = itemStack.getItemMeta();
		List<String> loreList = meta.hasLore() ? meta.getLore() : new ArrayList<String>();
		loreList.addAll(Arrays.asList(line));
		meta.setLore(loreList);
		itemStack.setItemMeta(meta);
		return this;
	}

	public ItemX clearLore() {
		ItemMeta meta = itemStack.getItemMeta();
		meta.setLore(new ArrayList<String>());
		itemStack.setItemMeta(meta);
		return this;
	}

	public ItemX addEnchant(Enchantment ench, int level) {
		itemStack.addUnsafeEnchantment(ench, level);
		return this;
	}

	public ItemX removeEnchant(Enchantment ench) {
		itemStack.removeEnchantment(ench);
		return this;
	}

	public ItemX addFlag(ItemFlag... flag) {
		ItemMeta meta = itemStack.getItemMeta();
		meta.addItemFlags(flag);
		itemStack.setItemMeta(meta);
		return this;
	}

	public ItemX removeFlag(ItemFlag... flag) {
		ItemMeta meta = itemStack.getItemMeta();
		meta.removeItemFlags(flag);
		itemStack.setItemMeta(meta);
		return this;
	}

	public ItemX setStringToNBTTag(String tag, String value) {
		this.itemStack = nms.setStringToNBTTag(itemStack, tag, value);
		return this;
	}

	public String getStringFromNBTTag(String tag, String defValue) {
		return nms.getStringFromNBTTag(itemStack, tag, defValue);
	}

	public String getStringFromNBTTag(String tag) {
		return getStringFromNBTTag(tag, null);
	}

	public ItemX setListIntToNBTTag(String tag, List<Integer> value) {
		this.itemStack = nms.setListIntToNBTTag(itemStack, tag, value);
		return this;
	}

	public List<Integer> getListIntFromNBTTag(String tag, List<Integer> defValue) {
		return nms.getListIntFromNBTTag(itemStack, tag, defValue);
	}

	public List<Integer> getListIntFromNBTTag(String tag) {
		return getListIntFromNBTTag(tag, null);
	}

	public ItemX setIntToNBTTag(String tag, int value) {
		this.itemStack = nms.setIntToNBTTag(itemStack, tag, value);
		return this;
	}

	public int getIntFromNBTTag(String tag, int defValue) {
		return nms.getIntFromNBTTag(itemStack, tag, defValue);
	}

	public int getIntFromNBTTag(String tag) {
		return getIntFromNBTTag(tag, 0);
	}

	public ItemX setAmount(int amount) {
		this.itemStack.setAmount(amount);
		return this;
	}

	public ItemX setUnbreakable(boolean toggle) {
		ItemMeta meta = itemStack.getItemMeta();
		meta.setUnbreakable(toggle);
		itemStack.setItemMeta(meta);
		return this;
	}
}
