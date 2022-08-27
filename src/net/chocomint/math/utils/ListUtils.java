package net.chocomint.math.utils;

import net.chocomint.math.complex.Complex;

import java.util.ArrayList;
import java.util.List;
import java.util.function.DoubleUnaryOperator;

public class ListUtils {

	public static List<Double> create(int from, int to, DoubleUnaryOperator op) {
		List<Double> list = new ArrayList<>();
		for (int i = from; i <= to; i++) {
			list.add(op.applyAsDouble(i));
		}
		return list;
	}

	public static List<Double> create(int from, int to) {
		return create(from, to, DoubleUnaryOperator.identity());
	}

	public static Complex generalLength(List<Complex> vector) {
		Complex c = new Complex(0);
		vector.forEach(complex -> c.add(complex.square()));
		return c.sqrt();
	}

	public static List<Complex> unit(List<Complex> vector) {
		List<Complex> res = new ArrayList<>();
		Complex div = generalLength(vector);
		vector.forEach(complex -> res.add(complex.copy().divide(div)));
		return res;
	}
}
