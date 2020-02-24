package loc4atnt.xlibs.location;

import org.bukkit.Location;

public class LocationUtil {

	public static double getBirdFlyingDistance(Location l1, Location l2) {
		return Math.sqrt(Math.pow(l1.getX() - l2.getX(), 2) + Math.pow(l1.getZ() - l2.getZ(), 2));
	}
}
