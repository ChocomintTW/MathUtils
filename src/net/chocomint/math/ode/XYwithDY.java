package net.chocomint.math.ode;

import net.chocomint.math.geometry.dim2.Vec2d;

import java.util.ArrayList;
import java.util.List;

public class XYwithDY {
	double x, y, dy;

	public XYwithDY(double x, double y, double dy) {
		this.x = x;
		this.y = y;
		this.dy = dy;
	}

	public static List<Vec2d> toXYList(List<XYwithDY> list) {
		List<Vec2d> res = new ArrayList<>();
		list.forEach(XYwithDY -> res.add(new Vec2d(XYwithDY.getX(), XYwithDY.getY())));
		return res;
	}

	public XYwithDY copy() {
		return new XYwithDY(x, y, dy);
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getDY() {
		return dy;
	}
}
