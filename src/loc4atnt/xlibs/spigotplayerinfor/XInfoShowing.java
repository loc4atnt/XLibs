package loc4atnt.xlibs.spigotplayerinfor;

import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;

public class XInfoShowing {
	
	public static String diffChar[] = {ChatColor.AQUA.toString()+ChatColor.RESET.toString()
							, ChatColor.BLACK.toString()+ChatColor.RESET.toString()
							, ChatColor.BLUE.toString()+ChatColor.RESET.toString()
							, ChatColor.BOLD.toString()+ChatColor.RESET.toString()
							, ChatColor.DARK_AQUA.toString()+ChatColor.RESET.toString()
							, ChatColor.DARK_BLUE.toString()+ChatColor.RESET.toString()
							, ChatColor.DARK_GRAY.toString()+ChatColor.RESET.toString()
							, ChatColor.DARK_GREEN.toString()+ChatColor.RESET.toString()
							, ChatColor.DARK_PURPLE.toString()+ChatColor.RESET.toString()
							, ChatColor.DARK_RED.toString()+ChatColor.RESET.toString()
							, ChatColor.GOLD.toString()+ChatColor.RESET.toString()
							, ChatColor.GRAY.toString()+ChatColor.RESET.toString()
							, ChatColor.GREEN.toString()+ChatColor.RESET.toString()
							, ChatColor.ITALIC.toString()+ChatColor.RESET.toString()
							, ChatColor.LIGHT_PURPLE.toString()+ChatColor.RESET.toString()
							, ChatColor.MAGIC.toString()+ChatColor.RESET.toString()
							, ChatColor.RED.toString()+ChatColor.RESET.toString()
							, ChatColor.STRIKETHROUGH.toString()+ChatColor.RESET.toString()
							, ChatColor.UNDERLINE.toString()+ChatColor.RESET.toString()
							, ChatColor.WHITE.toString()+ChatColor.RESET.toString()
							, ChatColor.YELLOW.toString()+ChatColor.RESET.toString()};
	public static int diffCharArrSize = 21;

	@SuppressWarnings("deprecation")
	public static void sendBoard(Player p, String title, List<String> text) {
		ScoreboardManager boardMng = Bukkit.getScoreboardManager();
		Scoreboard board = boardMng.getNewScoreboard();
		Objective obj = board.registerNewObjective(p.getName()+"_obj_0", "dummy");
		obj.setDisplayName(title);
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		int size = text.size();
		for (int index = size - 1; index >= 0; index--) {
			int diffCharIndex = index % diffCharArrSize;
			String line = text.get(index) + diffChar[diffCharIndex];
			if(line.length()>40)
				line = line.substring(0, 40);
			Score score = obj.getScore(line);
			score.setScore(size - index);
		}
		p.setScoreboard(board);
	}
	
	public static void removeBoard(Player p) {
		p.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
	}
}
