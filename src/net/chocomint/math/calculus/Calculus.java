package net.chocomint.math.calculus;

import net.chocomint.math.annotations.Utils;
import net.chocomint.math.complex.Complex;

@Utils
public class Calculus {
	public static final double DELTA = 1e-10;
	private static final int INTEGRAL_CONST = 2000000;

	public static double differentiate(D2DFunction func, double at) {
		return (func.f(at + DELTA) - func.f(at - DELTA)) / (2 * DELTA);
	}

	public static double integrate(D2DFunction func, double lb, double ub) {
		double sec = (ub - lb) / INTEGRAL_CONST, res = 0, low, up;
		for (int i = 0; i < INTEGRAL_CONST; i++) {
			low = lb + sec * i;
			up  = lb + sec * (i + 1);
			res += ((up - low) / 6) * (func.f(low) + 4 * func.f((low + up) / 2) + func.f(up));
		}
		return res;
	}

	public static Complex complexIntegrate(D2CFunction func, double lb, double ub) {
		double sec = (ub - lb) / INTEGRAL_CONST, low, up;
		Complex res = Complex.ZERO.copy();
		for (int i = 0; i < INTEGRAL_CONST; i++) {
			low = lb + sec * i;
			up  = lb + sec * (i + 1);
			res.add((func.f(low).add(func.f((low + up) / 2).multiply(4)).add(func.f(up))).multiply((up - low) / 6));
		}
		return res;
	}
}
