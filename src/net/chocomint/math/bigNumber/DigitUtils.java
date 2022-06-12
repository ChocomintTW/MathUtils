package net.chocomint.math.bigNumber;

import net.chocomint.math.annotations.Utils;

@Utils
public class DigitUtils {

	public static DigitWithCarry add(Digit d1, Digit d2) {
		int f = d1.n + d2.n;
		return f > 9 ? new DigitWithCarry(true, Digit.values()[f - 10]) : new DigitWithCarry(false, Digit.values()[f]);
	}

	public static DigitWithCarry addWithCarry(boolean carry, Digit d1, Digit d2) {
		DigitWithCarry dc = add(d1, d2);
		return new DigitWithCarry(dc.carry(), add(dc.digit(), carry ? Digit.N1 : Digit.N0).digit());
	}

	public static DigitPair multiply(Digit d1, Digit d2) {
		String x = Integer.toString(d1.n * d2.n);
		if (x.length() == 1)
			return new DigitPair(Digit.N0 ,Digit.values()[x.charAt(0) - '0']);
		return new DigitPair(Digit.values()[x.charAt(0) - '0'], Digit.values()[x.charAt(1) - '0']);
	}

	public static DigitPair multiplyAndAdd(Digit d1, Digit d2, Digit add) {
		String x = Integer.toString(d1.n * d2.n + add.n);
		if (x.length() == 1)
			return new DigitPair(Digit.N0 ,Digit.values()[x.charAt(0) - '0']);
		return new DigitPair(Digit.values()[x.charAt(0) - '0'], Digit.values()[x.charAt(1) - '0']);
	}

	public record DigitWithCarry(boolean carry, Digit digit) {
		@Override
		public String toString() {
			return "" + (carry ? 1 : 0) + digit;
		}
	}

	public record DigitPair(Digit d1, Digit d2) {
		@Override
		public String toString() {
			return "" + d1 + d2;
		}
	}
}
