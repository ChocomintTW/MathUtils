package net.chocomint.math.geometry.dim2;

import net.chocomint.math.annotations.Remark;

public class Line2d {
	private double slope, yIntercept;

	public Line2d(double slope, double yIntercept) {
		this.slope = slope;
		this.yIntercept = yIntercept;
	}

	@Remark("ax+by+c=0")
	public Line2d(double a, double b, double c) {
		this.slope = -a / b;
		this.yIntercept = -c / b;
	}

	public Vec2d directionVector() {
		return new Vec2d(1, slope);
	}

	public Vec2d normalVector() {
		return new Vec2d(slope, 1);
	}

	public static Line2d through(Vec2d point1, Vec2d point2) {
		double x1 = point1.getX();
		double y1 = point1.getY();
		double x2 = point2.getX();
		double y2 = point2.getY();
		double slope = (x2 - x1) / (y2 - y1);
		return new Line2d(slope, y1 - slope * x1);
	}

	public static Vec2d intersect(Line2d l1, Line2d l2) {
		double interX = -(l1.yIntercept - l2.yIntercept) / (l1.slope - l2.slope);
		return new Vec2d(interX, l1.slope * interX + l1.yIntercept);
	}

	public static Line2d from(Vec2d direction) {
		return new Line2d(direction.getY() / direction.getX(), 0);
	}
}
