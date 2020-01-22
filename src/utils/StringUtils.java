package utils;

import java.util.List;

public class StringUtils {


	public static String convertListToString(List<String> arg) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < arg.size(); i++) {
			sb.append(arg.get(i));
		}
		return sb.toString();
	}
	
	public static boolean isDouble(String val){
		try {
		    Double.parseDouble(val);
		    return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}
}
