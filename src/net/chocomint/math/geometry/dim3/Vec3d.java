package net.chocomint.math.geometry.dim3;

import net.chocomint.math.Quaternion;
import net.chocomint.math.base.Computable;
import net.chocomint.math.base.IBase;
import net.chocomint.math.matrix.Matrix;
import net.chocomint.math.matrix.MatrixSizeMismatchException;

import java.util.List;
import java.util.Objects;

public class Vec3d implements Computable<Vec3d>, IBase<Vec3d> {
	public static final Vec3d AXIS_X = new Vec3d(1, 0, 0);
	public static final Vec3d AXIS_Y = new Vec3d(0, 1, 0);
	public static final Vec3d AXIS_Z = new Vec3d(0, 0, 1);
	public static final Vec3d ORIGIN = new Vec3d(0, 0, 0);

	private double x, y, z;

	public Vec3d(double x, double y, double z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}

	Vec3d(Vec3d vec3d) {
		this.x = vec3d.x;
		this.y = vec3d.y;
		this.z = vec3d.z;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getZ() {
		return z;
	}

	public double length() {
		return this.distanceTo(ORIGIN);
	}

	public double distanceTo(Vec3d vec3d) {
		return Math.sqrt(Math.pow(this.x - vec3d.x, 2) + Math.pow(this.y - vec3d.y, 2) + Math.pow(this.z - vec3d.z, 2));
	}

	public Vec3d projection(Vec3d vec3d) {
		return vec3d.copy().multiply(this.dot(vec3d) / Math.pow(vec3d.length(), 2));
	}

	@Override
	public void set(Vec3d other) {
		this.x = other.x;
		this.y = other.y;
		this.z = other.z;
	}

	@Override
	public Vec3d copy() {
		return new Vec3d(this.x, this.y, this.z);
	}

	@Override
	public Vec3d negate() {
		this.multiply(-1);
		return this;
	}

	@Override
	public Vec3d add(Vec3d other) {
		this.x += other.x;
		this.y += other.y;
		this.z += other.z;
		return this;
	}

	@Override
	public Vec3d subtract(Vec3d other) {
		this.x -= other.x;
		this.y -= other.y;
		this.z -= other.z;
		return this;
	}

	@Override
	public Vec3d multiply(double other) {
		this.x *= other;
		this.y *= other;
		this.z *= other;
		return this;
	}

	@Override
	public Vec3d divide(double other) {
		this.x /= other;
		this.y /= other;
		this.z /= other;
		return this;
	}

	public double dot(Vec3d other) {
		return this.x * other.x + this.y * other.y + this.z * other.z;
	}

	public Vec3d cross(Vec3d other) {
		return new Vec3d(
				this.y * other.z - other.y * this.z,
				this.z * other.x - other.z * this.x,
				this.x * other.y - other.x * this.y
		);
	}

	public Vec3d unit() {
		return this.copy().divide(this.length());
	}

	public Vec3d rotate(Quaternion rot) {
		Quaternion q = Quaternion.exp(new Quaternion(0, rot.getImag()).multiply(rot.getReal() / 2));
		return q.multiply(new Quaternion(0, this)).multiply(q.conj()).getImag().copy();
	}

	public Vec3d transform(Matrix trans) throws MatrixSizeMismatchException {
		Matrix vec = new Matrix(3, 1, 0);
		vec.set(0, 0, this.x);
		vec.set(1, 0, this.y);
		vec.set(2, 0, this.z);
		try {
			List<Double> l = trans.multiply(vec).getColumn(0);
			return new Vec3d(l.get(0), l.get(1), l.get(2));
		} catch (MatrixSizeMismatchException e) {
			throw new MatrixSizeMismatchException(3, MatrixSizeMismatchException.ONLY);
		}
	}

	public static double angle(Vec3d v1, Vec3d v2) {
		return Math.acos(v1.copy().dot(v2) / (v1.length() * v2.length()));
	}

	@Override
	public String toString() {
		return "[" + x + ", " + y + ", " + z + "]";
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		Vec3d vec3d = (Vec3d) o;
		return this.x == vec3d.x && this.y == vec3d.y && this.z == vec3d.z;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y, z);
	}
}
