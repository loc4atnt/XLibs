package loc4atnt.xlibs.nms;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;

public interface NMS {

	public ItemStack setStringToNBTTag(ItemStack itemStack, String tag, String value);

	public String getStringFromNBTTag(ItemStack itemStack, String tag, String defValue);

	public ItemStack setListIntToNBTTag(ItemStack itemStack, String tag, List<Integer> value);

	public List<Integer> getListIntFromNBTTag(ItemStack itemStack, String tag, List<Integer> defValue);

	public ItemStack setIntToNBTTag(ItemStack itemStack, String tag, int value);

	public int getIntFromNBTTag(ItemStack itemStack, String tag, int defValue);

	public ItemStack setLongToNBTTag(ItemStack itemStack, String tag, long value);

	public long getLongFromNBTTag(ItemStack itemStack, String tag, long defValue);

	public void sendTitle(Player p, String upTitle, String downTitle, int fadeIn, int duration, int fadeOut);

	public void setOwnerSkull(ItemStack item, Player owner);

	public void setOwnerSkull(ItemStack item, String ownerName);

	public boolean hasNBTTag(ItemStack itemStack, String tag);

	public RegionManager getWGRegionManager(World world);

	public ApplicableRegionSet getApplicableRegionSet(Location loca);
}
