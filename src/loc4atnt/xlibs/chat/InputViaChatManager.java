package loc4atnt.xlibs.chat;

import java.util.HashMap;
import java.util.function.Consumer;

import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

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
		if (!e.getMessage().equals("huy")) {
			Consumer<AsyncPlayerChatEvent> task = taskMap.get(p);
			task.accept(e);
		}
		taskMap.remove(p);
		p.playSound(p.getLocation(), Sound.BLOCK_NOTE_BLOCK_BELL, 1, 1);
	}
}
