package net.chocomint.math.bigNumber;

public enum Digit {
	N0(0),
	N1(1),
	N2(2),
	N3(3),
	N4(4),
	N5(5),
	N6(6),
	N7(7),
	N8(8),
	N9(9);

	final int n;

	Digit(int n) {
		this.n = n;
	}

	public static DigitWithCarry add(Digit d1, Digit d2) {
		int f = d1.n + d2.n;
		return f > 9 ? new DigitWithCarry(true, Digit.values()[f - 10]) : new DigitWithCarry(false, Digit.values()[f]);
	}

	public static DigitWithCarry addWithCarry(boolean carry, Digit d1, Digit d2) {
		DigitWithCarry dc = add(d1, d2);
		return new DigitWithCarry(dc.carry(), add(dc.digit(), carry ? N1 : N0).digit());
	}

	@Override
	public String toString() {
		return "" + n;
	}
}
