package loc4atnt.xlibs.stringutil;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;

public class Color {

	public static String convert(String s, char c) {
		return ChatColor.translateAlternateColorCodes(c, s);
	}
	
	public static String convert(String s) {
		return convert(s, '&');
	}
	
	public static List<String> convertRawToColorLore(List<String> loreList, char c){
		if(loreList==null)
			return null;
		List<String> returnList = new ArrayList<String>();
		for(String line:loreList) {
			String colorLine = convert(line, c);
			returnList.add(colorLine);
		}
		return returnList;
	}
	
	public static List<String> convertRawToColorLore(List<String> loreList){
		return convertRawToColorLore(loreList, '&');
	}
}
