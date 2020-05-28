package loc4atnt.xlibs.permission;

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
				String[] args = perm.getPermission().split(".");
				int val = Integer.parseInt(args[args.length - 1]);
				if (val > maxVal)
					maxVal = val;
			}
		}
		return maxVal;
	}
}
