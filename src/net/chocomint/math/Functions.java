package net.chocomint.math;

import net.chocomint.math.sequence.Series;

import java.util.ArrayList;
import java.util.List;

public class Functions {
	public static final double EPS = 1e-8;

	/**
	 * Euler-Mascheroni constant
	 */
	public static final double GAMMA = 0.5772156649015328606065;

	/**
	 * A characteristic S-shaped function <br>
	 * Wiki: <a href="https://en.wikipedia.org/wiki/Sigmoid_function">Sigmoid Function</a>
	 */
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

	/**
	 *
	 * @param n Total n items
	 * @param k Take k items
	 * @return Combination counts
	 */
	public static long C(int n, int k) {
		return (long) (Series.product(t -> (double) t, n - k + 1, n) / fraction(k));
	}

	/**
	 *
	 * @param n Total n items
	 * @param k Take k items
	 * @return Permutation counts
	 */
	public static long P(int n, int k) {
		return (long) Series.product(t -> (double) t, n - k + 1, n);
	}

	/**
	 *
	 * @param n Total n items
	 * @param k Take k items
	 * @return Combination with repetition counts
	 */
	public static long H(int n, int k) {
		return C(n + k - 1, k);
	}

	public static int gcd(int a, int b) {
		return 0;
	}

	public static List<Integer> createPrimeList(int n) {
		boolean[] A = new boolean[n + 1];
		for (int i = 0; i < n + 1; i++) { A[i] = true; } // all true
		for (int i = 2; i <= Math.sqrt(n); i++) {
			if (A[i]) {
				for (int j = i * i; j < n + 1; j += i)
					A[j] = false;
			}
		}
		List<Integer> primeList = new ArrayList<>();
		for (int i = 2; i < n + 1; i++)
			if (A[i]) primeList.add(i);
		return primeList;
	}

	public static boolean isPrime(int n) {
		List<Integer> prL = createPrimeList(n);
		return prL.get(prL.size() - 1) == n;
	}

	public static double gaussian(double x, double y, double sigma) {
		return 1.0 / (2 * Math.PI * sigma * sigma) * Math.exp(-(x * x + y * y) / (2 * sigma * sigma));
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
