package loc4atnt.xlibs.inv;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.inventory.ItemStack;

import loc4atnt.xlibs.external.smartinv.fr.minuskube.inv.ClickableItem;
import loc4atnt.xlibs.external.smartinv.fr.minuskube.inv.InventoryListener;
import loc4atnt.xlibs.external.smartinv.fr.minuskube.inv.SmartInventory;
import loc4atnt.xlibs.external.smartinv.fr.minuskube.inv.content.InventoryContents;
import loc4atnt.xlibs.external.smartinv.fr.minuskube.inv.content.InventoryProvider;
import loc4atnt.xlibs.external.smartinv.fr.minuskube.inv.content.Pagination;
import loc4atnt.xlibs.external.smartinv.fr.minuskube.inv.content.SlotIterator;
import loc4atnt.xlibs.item.ItemX;

public abstract class XMenu implements InventoryProvider {

	private SmartInventory inv;
	private Player p;

	@SuppressWarnings("rawtypes")
	public static int getRowsByList(List list) {
		int rows = (int) list.size() / 9;
		if (list.size() % 9 != 0)
			rows++;
		return rows;
	}

	public XMenu(Player p, String id, String title, int row, int column, boolean denyPlayerClosing) {
		this.p = p;
		this.inv = SmartInventory.builder().id(p.getName() + id).title(title).size(row, column).provider(this)
				.closeable(!denyPlayerClosing).build();
	}

	public XMenu(String id, String title, int row, int column) {
		this.p = null;
		this.inv = SmartInventory.builder().id(id).title(title).size(row, column).provider(this).build();
	}

	public XMenu(Player p, String id, String title, int row) {
		this.p = p;
		this.inv = SmartInventory.builder().id(p.getName() + id).title(title).size(row, 9).provider(this).build();
	}

	public XMenu(Player p, String id, String title, int row, int column) {
		this.p = p;
		this.inv = SmartInventory.builder().id(p.getName() + id).title(title).size(row, column).provider(this)
				.build();
	}

	public XMenu(Player p, String id, String title, int row, int column, boolean denyPlayerClosing, InventoryListener<? extends Event> listener) {
		this.p = p;
		this.inv = SmartInventory.builder().id(p.getName() + id).title(title).size(row, column).provider(this)
				.closeable(!denyPlayerClosing).listener(listener).build();
	}

	public XMenu(Player p, String id, String title, int row, int column, InventoryListener<? extends Event> listener) {
		this.p = p;
		this.inv = SmartInventory.builder().id(p.getName() + id).title(title).size(row, column).provider(this)
				.listener(listener).build();
	}
	
///////////// Old Version
	public XMenu(Player p, String id, String title, int row, int column, boolean denyPlayerClosing, InventoryProvider provider) {
		this.p = p;
		this.inv = SmartInventory.builder().id(p.getName() + id).title(title).size(row, column).provider(provider)
				.closeable(!denyPlayerClosing).build();
	}

	public XMenu(String id, String title, int row, int column, InventoryProvider provider) {
		this.p = null;
		this.inv = SmartInventory.builder().id(id).title(title).size(row, column).provider(provider).build();
	}

	public XMenu(Player p, String id, String title, int row, InventoryProvider provider) {
		this.p = p;
		this.inv = SmartInventory.builder().id(p.getName() + id).title(title).size(row, 9).provider(provider).build();
	}

	public XMenu(Player p, String id, String title, int row, int column, InventoryProvider provider) {
		this.p = p;
		this.inv = SmartInventory.builder().id(p.getName() + id).title(title).size(row, column).provider(provider)
				.build();
	}

	public XMenu(Player p, String id, String title, int row, int column, boolean denyPlayerClosing, InventoryListener<? extends Event> listener, InventoryProvider provider) {
		this.p = p;
		this.inv = SmartInventory.builder().id(p.getName() + id).title(title).size(row, column).provider(provider)
				.closeable(!denyPlayerClosing).listener(listener).build();
	}

	public XMenu(Player p, String id, String title, int row, int column, InventoryListener<? extends Event> listener, InventoryProvider provider) {
		this.p = p;
		this.inv = SmartInventory.builder().id(p.getName() + id).title(title).size(row, column).provider(provider)
				.listener(listener).build();
	}
///////////////

	public void open() {
		if (p == null) {
			Bukkit.getConsoleSender().sendMessage("§cError With XMenu - XLibs: null player open menu!");
			return;
		}
		inv.open(p);
	}

	public void open(int page) {
		if (p == null) {
			Bukkit.getConsoleSender().sendMessage("§cError With XMenu - XLibs: null player open menu!");
			return;
		}
		inv.open(p, page);
	}

	public void open(Player player) {
		inv.open(player);
	}

	public void open(Player player, int page) {
		inv.open(player, page);
	}

	@SuppressWarnings("deprecation")
	public static ClickableItem getInteractClickableItem(InventoryContents cont, ItemStack firstItem, int row,
			int column) {
		return ClickableItem.of(firstItem, e -> {
			ItemStack cursorItem = e.getCursor();
			ItemStack putItem = e.getCurrentItem();
			if (putItem != null) {
				e.setCursor(putItem);
				cont.editItem(row, column, cursorItem);
			} else {
				if (cursorItem != null) {
					cont.editItem(row, column, cursorItem);
					e.setCursor(null);
				}
			}
		});
	}

	public static ClickableItem getInteractClickableItem(InventoryContents cont, int row, int column) {
		return getInteractClickableItem(cont, null, row, column);
	}

	public static void setupPage3X9(Player p, InventoryContents cont, XMenu previousMenu, ClickableItem fillClick,
			ClickableItem... clicks) {
		cont.fillBorders(fillClick);

		Pagination page = cont.pagination();
		page.setItems(clicks);
		page.setItemsPerPage(7);
		page.addToIterator(cont.newIterator(SlotIterator.Type.HORIZONTAL, 1, 1));

		if (!page.isFirst()) {
			ItemStack previousPage = new ItemX(Material.ARROW).setName("§aTrang trước").toItemStack();
			ClickableItem previousClick = ClickableItem.of(previousPage, e -> {
				cont.inventory().open(p, page.previous().getPage());
			});
			cont.set(2, 2, previousClick);
		}

		if (!page.isLast()) {
			ItemStack nextPage = new ItemX(Material.ARROW).setName("§aTrang sau").toItemStack();
			ClickableItem nextClick = ClickableItem.of(nextPage, e -> {
				cont.inventory().open(p, page.next().getPage());
			});
			cont.set(2, 6, nextClick);
		}

		if (previousMenu != null) {
			ItemStack closeItem = new ItemX(Material.BARRIER).setName("§cTrở lại").toItemStack();
			ClickableItem closeClick = ClickableItem.of(closeItem, c -> {
				cont.inventory().close(p);
				previousMenu.open();
			});
			cont.set(2, 4, closeClick);
		}
	}

	public static void setupPage3X9(Player p, InventoryContents cont, XMenu previousMenu, ClickableItem... clicks) {
		setupPage3X9(p, cont, previousMenu,
				ClickableItem.empty(new ItemX(Material.STAINED_GLASS_PANE, 1, (short) 1, (byte) 12).toItemStack()),
				clicks);
	}

	public static void setupPage3X9(Player p, InventoryContents cont, ClickableItem... clicks) {
		setupPage3X9(p, cont, null, clicks);
	}
	
	//public abstract void init(Player player, InventoryContents contents);
	//public abstract void update(Player player, InventoryContents contents);
}
