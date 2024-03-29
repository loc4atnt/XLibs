package loc4atnt.xlibs.chat;

import java.util.HashMap;
import java.util.function.Consumer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import loc4atnt.xlibs.external.xseries.XSound;
import loc4atnt.xlibs.main.XLibs;

public class InputViaChatManager implements Listener {

	private static InputViaChatManager inst;

	private HashMap<Player, Consumer<AsyncPlayerChatEvent>> taskMap;

	public static InputViaChatManager getInst() {
		return inst;
	}

	public InputViaChatManager() {
		inst = this;
		taskMap = new HashMap<Player, Consumer<AsyncPlayerChatEvent>>();

		XLibs.getInst().getServer().getPluginManager().registerEvents(this, XLibs.getInst());
	}

	public void putInputTask(Player p, Consumer<AsyncPlayerChatEvent> c, String msg) {
		taskMap.put(p, c);
		p.sendMessage(msg);
		p.sendMessage("§aNhập huy để hủy.");
	}

	@EventHandler
	private void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if (!taskMap.containsKey(p))
			return;
		Consumer<AsyncPlayerChatEvent> task = taskMap.remove(p);
		if (!e.getMessage().equals("huy")) {
			task.accept(e);
		}
		e.setCancelled(true);
		p.playSound(p.getLocation(), XSound.BLOCK_NOTE_BLOCK_PLING.parseSound(), 1, 1);
	}
}
