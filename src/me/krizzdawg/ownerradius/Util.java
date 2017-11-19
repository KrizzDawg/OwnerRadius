package me.krizzdawg.ownerradius;

public class Util {

	public static Integer tryParseInt(String string) {
		Integer parsedInteger = null;
		try {
			return parsedInteger = Integer.parseInt(string);
		} catch (Exception e) {
			return parsedInteger;
		}

	}
}
