package net.chocomint.math;

import net.chocomint.math.geometry.dim2.Vec2d;
import net.chocomint.math.sequence.Series;

import java.util.Arrays;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

public class NumericAnalysis {
	public static double average(double... data) {
		double total = 0;
		int cnt = 0;
		for (double d : data) {
			total += d;
			cnt++;
		}
		return total / cnt;
	}

	public static double variance(double... data) {
		double avg = average(data);
		double total = 0;
		int cnt = 0;
		for (double d : data) {
			total += Math.pow(d - avg, 2);
			cnt++;
		}
		return total / cnt;
	}

	public static double standardDeviation(double... data) {
		return Math.sqrt(variance(data));
	}

	public static DoubleUnaryOperator LagrangeInterpolating(Vec2d... data) {
		List<Vec2d> list = Arrays.asList(data);
		int k = list.size() - 1;
		return x -> Series.sum(j -> list.get(j).getY() * Series.product(i -> (x - list.get(i).getX()) / (list.get(j).getX() - list.get(i).getX()), 0, k, index -> index != j), 0, k);
	}
}
