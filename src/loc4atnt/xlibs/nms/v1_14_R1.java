package loc4atnt.xlibs.nms;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_14_R1.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.managers.RegionManager;
import com.sk89q.worldguard.protection.regions.RegionContainer;

import net.minecraft.server.v1_14_R1.NBTBase;
import net.minecraft.server.v1_14_R1.NBTTagCompound;
import net.minecraft.server.v1_14_R1.NBTTagInt;
import net.minecraft.server.v1_14_R1.NBTTagIntArray;
import net.minecraft.server.v1_14_R1.NBTTagString;

public class v1_14_R1 implements NMS {

	public ItemStack setNBTTag(ItemStack itemStack, String tag, NBTBase value) {
		net.minecraft.server.v1_14_R1.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
		NBTTagCompound itemCompound = (nmsItemStack.hasTag()) ? nmsItemStack.getTag() : new NBTTagCompound();
		itemCompound.set(tag, value);
		nmsItemStack.setTag(itemCompound);
		itemStack = CraftItemStack.asBukkitCopy(nmsItemStack);
		return itemStack;
	}

	public NBTTagCompound getNBTTag(ItemStack itemStack) {
		net.minecraft.server.v1_14_R1.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
		NBTTagCompound itemCompound = (nmsItemStack.hasTag()) ? nmsItemStack.getTag() : new NBTTagCompound();
		return itemCompound;
	}

	public ItemStack setStringToNBTTag(ItemStack itemStack, String tag, String value) {
		return setNBTTag(itemStack, tag, new NBTTagString(value));
	}

	public String getStringFromNBTTag(ItemStack itemStack, String tag, String defValue) {
		NBTTagCompound itemCompound = getNBTTag(itemStack);
		return itemCompound.hasKey(tag) ? itemCompound.getString(tag) : defValue;
	}

	@Override
	public ItemStack setListIntToNBTTag(ItemStack itemStack, String tag, List<Integer> value) {
		return setNBTTag(itemStack, tag, new NBTTagIntArray(value));
	}

	@Override
	public List<Integer> getListIntFromNBTTag(ItemStack itemStack, String tag, List<Integer> defValue) {
		NBTTagCompound itemCompound = getNBTTag(itemStack);
		if (itemCompound.hasKey(tag)) {
			int[] temp = itemCompound.getIntArray(tag);
			List<Integer> result = new ArrayList<Integer>();
			for (int i : temp) {
				result.add(i);
			}
			return result;
		}
		return defValue;
	}

	@Override
	public ItemStack setIntToNBTTag(ItemStack itemStack, String tag, int value) {
		return setNBTTag(itemStack, tag, new NBTTagInt(value));
	}

	@Override
	public int getIntFromNBTTag(ItemStack itemStack, String tag, int defValue) {
		NBTTagCompound itemCompound = getNBTTag(itemStack);
		return itemCompound.hasKey(tag) ? itemCompound.getInt(tag) : defValue;
	}

	@Override
	public void sendTitle(Player p, String upTitle, String downTitle, int fadeIn, int duration, int fadeOut) {
		p.sendTitle(upTitle, downTitle, fadeIn, duration, fadeOut);
	}

	@Override
	public void setOwnerSkull(ItemStack item, Player owner) {
		SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
		skullMeta.setOwningPlayer(owner);
		item.setItemMeta(skullMeta);
	}

	@SuppressWarnings("deprecation")
	@Override
	public void setOwnerSkull(ItemStack item, String ownerName) {
		SkullMeta skullMeta = (SkullMeta) item.getItemMeta();
		skullMeta.setOwner(ownerName);
		item.setItemMeta(skullMeta);
	}

	@Override
	public boolean hasNBTTag(ItemStack itemStack, String tag) {
		net.minecraft.server.v1_14_R1.ItemStack nmsItemStack = CraftItemStack.asNMSCopy(itemStack);
		if (nmsItemStack.hasTag()) {
			NBTTagCompound itemCompound = nmsItemStack.getTag();
			return itemCompound.hasKey(tag);
		}
		return false;
	}

	@Override
	public RegionManager getWGRegionManager(World world) {
		RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
		return container.get(BukkitAdapter.adapt(world));
	}

	@Override
	public ApplicableRegionSet getApplicableRegionSet(Location loca) {
		RegionManager rm = getWGRegionManager(loca.getWorld());
		return rm.getApplicableRegions(BlockVector3.at(loca.getX(), loca.getY(), loca.getZ()));
	}
}
