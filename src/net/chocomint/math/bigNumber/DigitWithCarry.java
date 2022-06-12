package net.chocomint.math.bigNumber;

public record DigitWithCarry(boolean carry, Digit digit) {
	@Override
	public String toString() {
		return "" + (carry ? 1 : 0) + digit;
	}
}
