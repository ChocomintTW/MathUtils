package net.chocomint.math;

import net.chocomint.math.complex.Complex;

import java.util.function.DoubleFunction;
import java.util.function.DoubleUnaryOperator;

public class Calculus {
	public static final double DELTA = 1e-10;
	private static final int INTEGRAL_CONST = 1000000;

	public static double differentiate(DoubleUnaryOperator func, double at) {
		return (func.applyAsDouble(at + DELTA) - func.applyAsDouble(at - DELTA)) / (2 * DELTA);
	}

	public static double integrate(DoubleUnaryOperator func, double lb, double ub) {
		double sec = (ub - lb) / INTEGRAL_CONST, res = 0, low, up;
		for (int i = 0; i < INTEGRAL_CONST; i++) {
			low = lb + sec * i;
			up  = lb + sec * (i + 1);
			res += ((up - low) / 6) * (func.applyAsDouble(low) + 4 * func.applyAsDouble((low + up) / 2) + func.applyAsDouble(up));
		}
		return res;
	}

	public static Complex complexIntegrate(DoubleFunction<Complex> func, double lb, double ub) {
		double sec = (ub - lb) / INTEGRAL_CONST, low, up;
		Complex res = Complex.ZERO.copy();
		for (int i = 0; i < INTEGRAL_CONST; i++) {
			low = lb + sec * i;
			up  = lb + sec * (i + 1);
			res.add((func.apply(low).add(func.apply((low + up) / 2).multiply(4)).add(func.apply(up))).multiply((up - low) / 6));
		}
		return res;
	}
}
