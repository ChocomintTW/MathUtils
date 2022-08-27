package net.chocomint.math.ode;

import net.chocomint.math.geometry.dim2.Vec2d;
import net.chocomint.math.utils.XYPlotHelper;

import java.util.ArrayList;
import java.util.List;

public class ODESolver {
	public static List<Vec2d> solutionPlotsListFor1stODE(DoubleParameterFunction func, Vec2d init, double step, double finalValue) {
		List<Vec2d> list = new ArrayList<>();
		Vec2d last = init.copy();
		list.add(last); // init value
		for (double i = step; i <= finalValue; i += step) {
			Vec2d now = new Vec2d(i, last.getY() + step * func.apply(last.getX(), last.getY()));
			list.add(now);
			last = now;
		}
		return list;
	}
	public static List<Vec2d> solutionPlotsListFor2ndODE(Acceleration acc, XYwithDY init, double step, double finalValue) {
		List<XYwithDY> list = new ArrayList<>();
		XYwithDY last = init.copy();
		list.add(last); // init value
		for (double i = step; i <= finalValue; i += step) {
			XYwithDY now = new XYwithDY(i, last.getY() + step * last.getDY(), last.getDY() + step * acc.apply(last.getX(), last.getY(), last.getDY()));
			list.add(now);
			last = now;
		}
		return XYwithDY.toXYList(list);
	}
}
