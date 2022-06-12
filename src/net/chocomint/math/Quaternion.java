package net.chocomint.math;

import net.chocomint.math.base.Computable;
import net.chocomint.math.base.IBase;

public class Quaternion implements Computable<Quaternion>, IBase<Quaternion> {
	public static final Quaternion i = new Quaternion(0, 1, 0, 0);
	public static final Quaternion j = new Quaternion(0, 0, 1, 0);
	public static final Quaternion k = new Quaternion(0, 0, 0, 1);
	public static final Quaternion ZERO = new Quaternion(0, Vec3d.ORIGIN.copy());

	private double real;
	private Vec3d imag;

	public Quaternion(double real, double i, double j, double k) {
		this.real = real;
		this.imag = new Vec3d(i, j, k);
	}

	public Quaternion(double real, Vec3d imag) {
		this.real = real;
		this.imag = imag.copy();
	}

	public Quaternion(double real) {
		this.real = real;
		this.imag = Vec3d.ORIGIN.copy();
	}

	@Override
	public void set(Quaternion other) {
		this.real = other.real;
		this.imag.set(other.imag);
	}

	@Override
	public Quaternion copy() {
		return new Quaternion(this.real, this.imag);
	}

	@Override
	public Quaternion negate() {
		this.multiply(-1);
		return this;
	}

	@Override
	public Quaternion add(Quaternion other) {
		this.real += other.real;
		this.imag.add(other.imag);
		return this;
	}

	@Override
	public Quaternion subtract(Quaternion other) {
		this.real -= other.real;
		this.imag.subtract(other.imag);
		return this;
	}

	@Override
	public Quaternion multiply(double other) {
		this.real *= other;
		this.imag.multiply(other);
		return this;
	}

	@Override
	public Quaternion multiply(Quaternion other) {
		return new Quaternion(
				this.real * other.real - this.imag.dot(other.imag),
				(this.imag.copy().multiply(other.real))
						.copy().add(other.imag.copy().multiply(this.real))
						.copy().add(this.imag.copy().cross(other.imag))
		);
	}

	@Override
	public Quaternion divide(double other) {
		this.real /= other;
		this.imag.divide(other);
		return this;
	}

	public double dot(Quaternion other) {
		return this.real * other.real + this.imag.copy().dot(other.imag);
	}

	public Vec3d outerProduct(Quaternion other) {
		return this.conj().multiply(other).subtract(other.conj().multiply(this)).divide(2).imag;
	}

	public Quaternion evenProduct(Quaternion other) {
		return this.copy().multiply(other).add(other.copy().multiply(this)).divide(2);
	}

	public Vec3d cross(Quaternion other) {
		return this.imag.copy().cross(other.imag);
	}

	public Quaternion conj() {
		return new Quaternion(this.real, this.imag.copy().negate());
	}

	public double length() {
		return Math.sqrt(this.dot(this));
	}

	public double squareLength() {
		return this.dot(this);
	}

	public Quaternion inverse() {
		return this.conj().divide(this.squareLength());
	}

	public Quaternion unit() {
		return this.copy().divide(this.length());
	}

	public double arg() {
		return Math.acos(this.real / this.length());
	}

	public static Quaternion exp(Quaternion q) {
		return new Quaternion(Math.cos(q.imag.length()), q.imag.unit().multiply(Math.sin(q.imag.length()))).multiply(Math.exp(q.real));
	}

	public static Quaternion ln(Quaternion q) {
		return new Quaternion(Math.log(q.length()), q.imag.unit().multiply(q.arg()));
	}

	public static Quaternion pow(Quaternion base, Quaternion power) {
		return exp(ln(base).multiply(power));
	}

	public static Quaternion sqrt(Quaternion q) {
		return pow(q, new Quaternion(0.5));
	}

	public static Quaternion sin(Quaternion q) {
		return new Quaternion(Math.sin(q.real) * Math.cosh(q.imag.length()),
				q.imag.copy().multiply(Math.cos(q.real) * Math.sinh(q.imag.length())));
	}

	public static Quaternion cos(Quaternion q) {
		return new Quaternion(Math.cos(q.real) * Math.cosh(q.imag.length()),
				q.imag.copy().multiply(-Math.sin(q.real) * Math.sinh(q.imag.length())));
	}

	public static Quaternion tan(Quaternion q) {
		return sin(q).divide(cos(q)).copy();
	}

	public static Quaternion sinh(Quaternion q) {
		return new Quaternion(Math.sinh(q.real) * Math.cos(q.imag.length()),
				q.imag.copy().multiply(Math.cosh(q.real) * Math.sin(q.imag.length())));
	}

	public static Quaternion cosh(Quaternion q) {
		return new Quaternion(Math.cosh(q.real) * Math.cos(q.imag.length()),
				q.imag.copy().multiply(Math.sinh(q.real) * Math.sin(q.imag.length())));
	}

	public static Quaternion tanh(Quaternion q) {
		return sinh(q).divide(cosh(q)).copy();
	}

	public static Quaternion asinh(Quaternion q) {
		return ln(q.copy().add(sqrt(q.copy().multiply(q).add(new Quaternion(1)))));
	}

	public static Quaternion of(Vec3d axis, double angle) {
		return new Quaternion(angle, axis);
	}

	public static Quaternion fromEuler(double yaw, double pitch, double roll) {
		double cy = Math.cos(yaw   / 2);
		double sy = Math.sin(yaw   / 2);
		double cp = Math.cos(pitch / 2);
		double sp = Math.sin(pitch / 2);
		double cr = Math.cos(roll  / 2);
		double sr = Math.sin(roll  / 2);

		return new Quaternion(
			cr * cp * cy + sr * sp * sy,
			sr * cp * cy - cr * sp * sy,
			cr * sp * cy + sr * cp * sy,
			cr * cp * sy - sr * sp * cy
		);
	}

	public double getReal() {
		return real;
	}

	public Vec3d getImag() {
		return imag;
	}

	@Override
	public String toString() {
		return element(real, "", false)
				+ element(imag.getX(), "i", real != 0)
				+ element(imag.getY(), "j", real != 0 || imag.getX() != 0)
				+ element(imag.getZ(), "k", real != 0 || imag.getX() != 0 || imag.getY() != 0);
	}

	private String element(double d, String part, boolean sign) {
		return d == 0 ? "" : (d == 1 ? (sign ? "+" : "") + part : (d == -1 ? "-" + part : (d > 0 ? (sign ? "+" : "") : "") + d + part));
	}
}
