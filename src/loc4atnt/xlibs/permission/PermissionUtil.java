package loc4atnt.xlibs.permission;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.plugin.RegisteredServiceProvider;

import loc4atnt.xlibs.main.XLibs;
import net.milkbowl.vault.permission.Permission;

public class PermissionUtil {

	private static PermissionUtil inst;
	private XLibs plugin;

	private Permission permission = null;

	public PermissionUtil() {
		inst = this;
		plugin = XLibs.getInst();

		setupPermissions();
	}

	private boolean setupPermissions() {
		RegisteredServiceProvider<Permission> permissionProvider = plugin.getServer().getServicesManager()
				.getRegistration(Permission.class);
		if (permissionProvider != null) {
			permission = permissionProvider.getProvider();
		}
		return (permission != null);
	}

	public static PermissionUtil getInst() {
		return inst;
	}

	public int getMaxIntFromPermission(Player p, String prePerm) {
		int maxVal = -1;
		Set<PermissionAttachmentInfo> permSet = p.getEffectivePermissions();
		for (PermissionAttachmentInfo perm : permSet) {
			if (perm.getPermission().startsWith(prePerm)) {
				List<String> args = split(perm.getPermission(), '.');
				int index = args.size() - 1;
				int val = ((index >= 0) ? Integer.parseInt(args.get(index)) : -1);
				if (val > maxVal)
					maxVal = val;
			}
		}
		return maxVal;
	}

	public List<String> split(String target, char regex) {
		List<String> list = new ArrayList<String>();
		int bgIndex = 0;
		int endIndex = 0;
		for (int i = 0; i < target.length(); i++) {
			if (target.charAt(i) == regex) {
				endIndex = i;
				String element = target.substring(bgIndex, endIndex);
				list.add(element);
				bgIndex = i + 1;
			}
		}
		if (bgIndex < target.length()) {
			String lastElement = target.substring(bgIndex);
			list.add(lastElement);
		}
		return list;
	}
}
