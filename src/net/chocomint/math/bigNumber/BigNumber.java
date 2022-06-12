package net.chocomint.math.bigNumber;

import net.chocomint.math.base.Computable;
import net.chocomint.math.base.IBase;

import java.util.ArrayList;
import java.util.List;

public class BigNumber implements Computable<BigNumber>, IBase<BigNumber> {
	private boolean positive;
	private List<Digit> number;

	protected BigNumber(boolean p, List<Digit> number) {
		this.positive = p;
		this.number = number;
	}

	public BigNumber(long n) {
		positive = (n > 0);
		number = new ArrayList<>();
		String s = Long.toString(n);
		StringBuilder builder = new StringBuilder(s);
		if (n < 0) builder.delete(0, 1);
		for (char c : builder.reverse().toString().toCharArray())
			number.add(Digit.values()[c - '0']);
	}

	public BigNumber(String s) {
		positive = (s.toCharArray()[0] != '-');
		number = new ArrayList<>();
		StringBuilder builder = new StringBuilder(s);
		if (s.toCharArray()[0] == '-')
			builder.delete(0, 1);
		for (char c : builder.reverse().toString().toCharArray()) {
			if (!(c >= '0' && c <= '9'))
				throw new IllegalArgumentException("The String argument is not a number");
			number.add(Digit.values()[c - '0']);
		}
	}

	public BigNumber() {
		positive = true;
		number = new ArrayList<>();
	}

	public BigNumber copy() {
		List<Digit> copied = new ArrayList<>(this.number);
		return new BigNumber(positive, copied);
	}

	@Override
	public void set(BigNumber other) {
	}

	public int length() {
		return number.size();
	}

	public Digit at(int index) {
		return index >= this.length() ? Digit.N0 : this.number.get(index);
	}

	public boolean isPositive() {
		return positive;
	}

	private void set(int index, Digit value) {
		this.number.set(index, value);
	}

	private void append(Digit value) {
		this.number.add(value);
	}

	public BigNumber negate() {
		this.positive = !this.positive;
		return this;
	}

	@Override
	public BigNumber add(BigNumber other) {
		if (this.positive == other.positive)
		{
			int l1 = this.length(), l2 = other.length();
			boolean carry = false;
			BigNumber res = new BigNumber();
			for (int i = 0; i < Math.max(l1, l2); i++) {
				DigitWithCarry at_i = Digit.addWithCarry(carry, this.at(i), other.at(i));
				res.append(at_i.digit());
				carry = at_i.carry();
			}
			if (carry)
				res.append(Digit.N1);
			res.positive = this.positive;
			return res;
		} else if (!this.positive) return other.subtract(this);
		else return this.subtract(other);
	}

	@Override
	public BigNumber subtract(BigNumber other) {
		return null;
	}

	@Override
	public BigNumber multiply(double other) {
		return null;
	}

	@Override
	public BigNumber divide(double other) {
		return null;
	}

	public static BigNumber negate(BigNumber n) {
		return new BigNumber(!n.positive, n.number);
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		for (int i = 0; i < this.length(); i++)
			res.append(this.at(i).toString());
		return (this.positive ? "" : "-") + res.reverse();
	}
}
