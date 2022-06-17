package net.chocomint.math;

import net.chocomint.math.annotations.Remark;
import net.chocomint.math.annotations.Utils;
import net.chocomint.math.sequence.Series;

@Utils
public class Functions {
	public static final double EPS = 1e-8;

	@Remark("Euler–Mascheroni constant")
	public static final double GAMMA = 0.5772156649015328606065;

	@Remark("A characteristic S-shaped function")
	public static double sigmoid(double x) {
		return 1 / (1 + Math.exp(-x));
	}

	public static boolean doubleEqual(double x, double y) {
		return Math.abs(x - y) < 1e-12;
	}

	public static boolean doubleNear(double x, double y) {
		return Math.abs(x - y) < 1e-5;
	}

	public static boolean doubleNear(double x, double y, double eps) {
		return Math.abs(x - y) < eps;
	}

	public static long fraction(long x) {
		return x == 0 ? 1 : x * fraction(x - 1);
	}

	@Remark("Combination")
	public static long C(int n, int k) {
		return (long) (Series.product(t -> (double) t, n - k + 1, n) / fraction(k));
	}

	@Remark("Permutation")
	public static long P(int n, int k) {
		return (long) Series.product(t -> (double) t, n - k + 1, n);
	}

	@Remark("Combination with repetition")
	public static long H(int n, int k) {
		return C(n + k - 1, k);
	}

	public static double gamma(double x) {
		if (x > 1)
			return (x - 1) * gamma(x - 1);
		else if (Math.abs(x - 1) < EPS)
			return 1;
		else if (Math.abs(x - 0.5) < EPS)
			return Math.sqrt(Math.PI);
		else {
			int times = 3000000;
			double res = 1;
			// Weierstrass's definition
			for (int i = 1; i <= times; i++) {
				res *= Math.exp(x / i) / (1 + x / i);
			}
			return Math.exp(-GAMMA * x) / x * res;
		}
	}

	public static double beta(double x, double y) {
		return gamma(x) * gamma(y) / gamma(x + y);
	}

	public static double bessel(int alpha, double x) {
		try {
			return Series.sumInf(m -> Math.pow(-1, m) / (double) fraction(m) / fraction(m + alpha) * Math.pow(x / 2, 2 * m + alpha), 0);
		} catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
	}

}
