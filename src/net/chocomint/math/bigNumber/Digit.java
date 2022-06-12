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

	Digit inverse() {
		return this.n == 0 ? N0 : Digit.values()[10 - this.n];
	}

	Digit sub1() {
		return this.n == 0 ? N0 : Digit.values()[this.n - 1];
	}

	@Override
	public String toString() {
		return "" + n;
	}
}
