package net.chocomint.math.complex;

import net.chocomint.math.base.Computable;
import net.chocomint.math.base.IBase;
import net.chocomint.math.matrix.Matrix;
import net.chocomint.math.matrix.MatrixSizeMismatchException;

import java.util.List;
import java.util.Objects;

public class Complex implements Computable<Complex>, IBase<Complex> {
	public static final Complex i = new Complex(0, 1);
	public static final Complex ZERO = new Complex(0, 0);

	private double real, imag;

	public Complex(double real, double imag) {
		this.real = real;
		this.imag = imag;
	}

	public Complex(double n) {
		this.real = n;
		this.imag = 0;
	}

	Complex(Complex duplicate) {
		this.real = duplicate.real;
		this.imag = duplicate.imag;
	}

	public double Re() {
		return real;
	}

	public double Im() {
		return imag;
	}

	@Override
	public void set(Complex other) {
		this.real = other.real;
		this.imag = other.imag;
	}

	@Override
	public Complex copy() {
		return new Complex(this);
	}

	@Override
	public Complex add(Complex other) {
		this.real += other.real;
		this.imag += other.imag;
		return this;
	}

	@Override
	public Complex subtract(Complex other) {
		this.real -= other.real;
		this.imag -= other.imag;
		return this;
	}

	@Override
	public Complex negate() {
		this.multiply(-1);
		return this;
	}

	@Override
	public Complex multiply(Complex other) {
		double r = this.real, i = this.imag;
		this.real = r * other.real - i * other.imag;
		this.imag = r * other.imag + i * other.real;
		return this;
	}

	@Override
	public Complex multiply(double val) {
		this.set(new Complex(real * val, imag * val));
		return this;
	}

	@Override
	public Complex divide(Complex other) {
		return this.multiply(other.conj()).divide(Math.pow(other.real, 2) + Math.pow(other.imag, 2));
	}

	@Override
	public Complex divide(double val) {
		this.set(new Complex(real / val, imag / val));
		return this;
	}

	public Complex conj() {
		return new Complex(real, -imag);
	}

	public double norm() {
		return Math.sqrt(real * real + imag * imag);
	}

	public double Arg() {
		return Math.atan2(imag, real);
	}

	public Complex unit() {
		return this.copy().divide(this.norm());
	}

	public Complex sqrt() {
		return pow(this, new Complex(0.5)).PV();
	}

	public static Complex exp(Complex c) {
		return new Complex(Math.cos(c.Im()), Math.sin(c.Im())).multiply(Math.exp(c.Re()));
	}

	public static Complex sin(Complex c) {
		Complex tmp = c.copy().multiply(i);
		return (exp(tmp).subtract(exp(tmp.copy().negate()))).divide(i.copy().multiply(2));
	}

	public static Complex cos(Complex c) {
		Complex tmp = c.copy().multiply(i);
		return (exp(tmp).add(exp(tmp.copy().negate()))).divide(2);
	}

	public static Complex tan(Complex c) {
		return sin(c).divide(cos(c));
	}

	public static Complex sinh(Complex c) {
		return (exp(c).subtract(exp(c.copy().negate()))).divide(2);
	}

	public static Complex cosh(Complex c) {
		return (exp(c).add(exp(c.copy().negate()))).divide(2);
	}

	public static Complex tanh(Complex c) {
		return sinh(c).divide(cosh(c));
	}

	// Multivalued

	public static Multivalued Ln(Complex c) {
		return new Multivalued(k -> new Complex(Math.log(c.norm()), c.Arg() + 2 * k * Math.PI));
	}

	public static Multivalued pow(Complex base, Complex power) {
		return new Multivalued(k -> exp(power.copy().multiply(Ln(base).PV().add(i.copy().multiply(2 * k * Math.PI)))));
	}

	@Override
	public String toString() {
		return (real != 0 ? real : "") + (real != 0 && imag > 0 ? "+" : "") +
				(imag != 0 ? (imag == 1 ? "" : imag == -1 ? "-" : imag) + "i" : "");
	}

	// Another

	public Matrix asMatrix() {
		try {
			return Matrix.I(2).copy().multiply(this.real).add(new Matrix(List.of(0d, -1d), List.of(1d, 0d)).multiply(this.imag));
		} catch (MatrixSizeMismatchException e) {
			e.printStackTrace();
		}
		return null;
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Double) return this.real == ((Double) o) && this.imag == 0;
		else if (o == null || getClass() != o.getClass()) return false;
		Complex complex = (Complex) o;
		return complex.real == real && complex.imag == imag;
	}

	@Override
	public int hashCode() {
		return Objects.hash(real, imag);
	}
}
