package net.chocomint.math.radix;

import java.util.regex.Pattern;

public class Radix {
	public static final String HEX = "0123456789ABCDEF";

	public static String Dec2Hex(long dec) {
		long l = dec;
		int x;
		StringBuilder builder = new StringBuilder();
		while (l > 0) {
			x = (int) (l % 16);
			builder.append(HEX.charAt(x));
			l /= 16;
		}
		return builder.reverse().toString();
	}

	public static long Hex2Dec(String hex) {
		if (Pattern.matches("^[0123456789ABCDEF]+$", hex)) {
			return 1;
		} else throw new IllegalArgumentException("The number is not matched hex pattern!");
	}
}
