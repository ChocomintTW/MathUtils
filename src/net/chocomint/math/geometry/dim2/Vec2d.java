package net.chocomint.math.geometry.dim2;

import net.chocomint.math.base.Computable;
import net.chocomint.math.base.IBase;

public class Vec2d implements Computable<Vec2d>, IBase<Vec2d> {
	public static final Vec2d ORIGIN = new Vec2d(0, 0);

	private double x, y;

	public Vec2d(double x, double y) {
		this.x = x;
		this.y = y;
	}

	Vec2d(Vec2d vec2d) {
		this.x = vec2d.x;
		this.y = vec2d.y;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double length() {
		return this.distanceTo(ORIGIN);
	}

	public double distanceTo(Vec2d vec2d) {
		return Math.sqrt(Math.pow(this.x - vec2d.x, 2) + Math.pow(this.y - vec2d.y, 2));
	}

	public Vec2d projection(Vec2d other) {
		return other.copy().multiply(this.dot(other) / Math.pow(other.length(), 2));
	}

	@Override
	public void set(Vec2d other) {
		this.x = other.x;
		this.y = other.y;
	}

	@Override
	public Vec2d copy() {
		return new Vec2d(this.x, this.y);
	}

	@Override
	public Vec2d negate() {
		this.multiply(-1);
		return this;
	}

	@Override
	public Vec2d add(Vec2d other) {
		this.x += other.x;
		this.y += other.y;
		return this;
	}

	@Override
	public Vec2d subtract(Vec2d other) {
		this.x -= other.x;
		this.y -= other.y;
		return this;
	}

	@Override
	public Vec2d multiply(double other) {
		this.x *= other;
		this.y *= other;
		return this;
	}

	@Override
	public Vec2d divide(double other) {
		this.x /= other;
		this.y /= other;
		return this;
	}

	public double dot(Vec2d other) {
		return this.x * other.x + this.y * other.y;
	}
}
