package net.chocomint.math.bigNumber;

import net.chocomint.math.base.Computable;
import net.chocomint.math.base.IBase;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

import net.chocomint.math.bigNumber.DigitUtils.*;

public class BigNumber implements Computable<BigNumber>, IBase<BigNumber>, Comparable<BigNumber> {
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
		if (Pattern.matches("^-?[1234567890]+$", s)) {
			positive = (s.toCharArray()[0] != '-');
			number = new ArrayList<>();
			StringBuilder builder = new StringBuilder(s);
			if (s.toCharArray()[0] == '-')
				builder.delete(0, 1);
			for (char c : builder.reverse().toString().toCharArray()) {
				number.add(Digit.values()[c - '0']);
			}
		} else throw new IllegalArgumentException("The String argument is not a number");
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
		this.number.clear();
		other.number.forEach(this::append);
	}

	public int length() {
		return number.size();
	}

	private Digit at(int index) {
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
				DigitWithCarry at_i = DigitUtils.addWithCarry(carry, this.at(i), other.at(i));
				res.append(at_i.digit());
				carry = at_i.carry();
			}
			if (carry)
				res.append(Digit.N1);
			res.positive = this.positive;
			return res;
		} else if (!this.positive) return other.subtract(this.copy().negate());
		else return this.subtract(other.copy().negate());
	}

	@Override
	public BigNumber subtract(BigNumber other) {
		if (this.positive == other.positive)
		{
			if (this.compareTo(other) < 0)
				return other.subtract(this).negate().copy();
			else if (this.equals(other))
				return new BigNumber(0);
			else {
				int l1 = this.length(), l2 = other.length();
				int max = Math.max(l1, l2);

				BigNumber inverse = new BigNumber();
				other.number.forEach(digit -> inverse.append(digit.inverse()));
				BigNumber res = new BigNumber();
				for (int i = 0; i < max; i++) {
					res.append(DigitUtils.add(this.at(i), inverse.at(i)).digit());
				}

				for (int i = 0; i < max; i++) {
					if (i >= 1 && other.at(i - 1).n > this.at(i - 1).n) {
						res.set(i, res.at(i).sub1());
					}
				}
				while (res.at(res.length() - 1) == Digit.N0)
					res.number.remove(res.length() - 1);

				return res;
			}
		} else if (!this.positive) return other.add(this.copy().negate()).negate();
		else return this.add(other.copy().negate());
	}

	public BigNumber multiply(Digit digit) {
		BigNumber res = new BigNumber();
		Digit carry = Digit.N0;
		for (int i = 0; i < this.length(); i++) {
			DigitPair p = DigitUtils.multiplyAndAdd(this.at(i), digit, carry);
			res.append(p.d2());
			carry = p.d1();
		}
		if (carry != Digit.N0)
			res.append(carry);
		return res;
	}

	public BigNumber multiply10n(int times) {
		BigNumber res = new BigNumber();
		for (int i = 0; i < times; i++) {
			res.append(Digit.N0);
		}
		this.number.forEach(res::append);
		return res;
	}

	@Override
	public BigNumber multiply(BigNumber other) {
		BigNumber res = new BigNumber();
		for (int i = 0; i < other.length(); i++) {
			if (other.at(i) != Digit.N0) {
				BigNumber tmp = this.multiply(other.at(i)).multiply10n(i);
				for (int j = 0; j < other.length() - i - 1; j++) {
					System.out.print(" ");
				}
				System.out.println(tmp);
				res.set(res.add(tmp));
			}
		}
		return res;
	}

	@Override
	@Deprecated
	public BigNumber multiply(double other) {
		return null;
	}

	@Override
	@Deprecated
	public BigNumber divide(double other) {
		return null;
	}

	@Override
	public String toString() {
		StringBuilder res = new StringBuilder();
		for (int i = 0; i < this.length(); i++)
			res.append(this.at(i).toString());
		return (this.positive ? "" : "-") + res.reverse();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		BigNumber bigNumber = (BigNumber) o;
		if (this.length() != bigNumber.length()) return false;
		if (this.positive != bigNumber.positive) return false;
		int max = Math.max(this.length(), bigNumber.length());
		for (int i = 0; i < max; i++) {
			if (this.at(i).n != bigNumber.at(i).n)
				return false;
		}
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(positive, number);
	}

	@Override
	public int compareTo(BigNumber other) {
		if (this.equals(other))
			return 0;
		else if (this.positive != other.positive) {
			return this.positive ? 1 : -1;
		} else if (this.length() != other.length()) {
			return this.length() - other.length();
		} else {
			int l = this.length() - 1;
			for (int i = l; i >= 0; i--) {
				if (this.at(i).n != other.at(i).n) {
					return this.at(i).n - other.at(i).n;
				}
			}
			return 0;
		}
	}
}
