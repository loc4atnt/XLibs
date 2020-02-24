package loc4atnt.xlibs.spigotplayerinfor;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

public class XTeamInfo {

	private Player leader;
	private ScoreboardManager manager;
	private Scoreboard board;
	private Team team;
	private Objective obj;

	public XTeamInfo(Player leader) {
		this.leader = leader;
		manager = Bukkit.getScoreboardManager();
		board = manager.getNewScoreboard();
		team = board.registerNewTeam(leader.getName());
		obj = board.registerNewObjective(leader.getName() + "_obj_0", "dummy");

		team.setCanSeeFriendlyInvisibles(true);
		team.setAllowFriendlyFire(false);
	}

	public Player getLeader() {
		return leader;
	}

	@SuppressWarnings("deprecation")
	public List<OfflinePlayer> destroyTeam() {
		List<OfflinePlayer> pList = new ArrayList<OfflinePlayer>(team.getPlayers());
		for (int i = 0; i < pList.size(); i++) {
			removeMember(pList.get(i));
		}
		return pList;
	}

	@SuppressWarnings("deprecation")
	public void addMember(Player p) {
		team.addPlayer(p);
		p.setScoreboard(board);
	}

	@SuppressWarnings("deprecation")
	public void removeMember(OfflinePlayer p) {
		if (team.removePlayer(p)) {
			if (p instanceof Player)
				((Player) p).setScoreboard(manager.getNewScoreboard());
		}
	}

	@SuppressWarnings("deprecation")
	public void updateBoard(String title, List<String> text) {
		obj.unregister();
		obj = board.registerNewObjective(leader.getName() + "_obj_0", "dummy");
		obj.setDisplayName(title);
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		int size = text.size();
		for (int index = size - 1; index >= 0; index--) {
			int diffCharIndex = index % XInfoShowing.diffCharArrSize;
			String line = text.get(index) + XInfoShowing.diffChar[diffCharIndex];
			if (line.length() > 40)
				line = line.substring(0, 40);
			Score score = obj.getScore(line);
			score.setScore(size - index);
		}
		team.getPlayers().forEach(p -> {
			if (p instanceof Player)
				((Player) p).setScoreboard(board);
		});
	}
}
