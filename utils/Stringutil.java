package utils;

public class Stringutil {
	public static String formatDouble(double number, int places) {
		try {
			String[] aRet = Double.toString(number).split("\\.");
			String second = "";
			if (places < 0) return "";
			if (places > aRet[1].length()) {
				second = aRet[1];
			}
			else {
				second = aRet[1].substring(0, places);
			}
			return aRet[0] + "." + second;
		}
		catch(Exception e) {
			return "";
		}
	}
}
