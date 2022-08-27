package net.chocomint.math;

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
		if (Pattern.matches("^0x[0123456789ABCDEFabcdef]+$", hex)) {
			long res = 0;
			long x = 1;
			for (char c : new StringBuilder(hex).reverse().toString().toCharArray()) {
				if (c >= '0' && c <= '9') {
					res += x * (c - '0');
				} else if (c >= 'A' && c <= 'F') {
					res += x * (c - 'A' + 10);
				} else if (c >= 'a' && c <= 'f') {
					res += x * (c - 'a' + 10);
				}
				x *= 16;
			}
			return res;
		} else throw new IllegalArgumentException("The number is not matched hex pattern!");
	}
}
