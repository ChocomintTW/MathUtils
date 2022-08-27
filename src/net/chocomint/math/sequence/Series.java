package net.chocomint.math.sequence;

import net.chocomint.math.Functions;

public class Series {
	public static final Sequence<Long> CATALAN = n -> Functions.C(n * 2, n) / (n + 1);

	public static double sum(Sequence<Double> sequence, int from, int to) {
		double res = 0;
		for (int i = from; i <= to; i++)
			res += sequence.get(i);
		return res;
	}

	public static double sumInf(Sequence<Double> sequence, int init) throws NotConservedException {
		double res = 0, val = 0;
		val = sequence.get(init);
		for (int i = init; !Functions.doubleNear(val, 0); i++) {
			if (i > 1000000) {
				throw new NotConservedException();
			}
			val = sequence.get(i);
			res += val;
		}
		return res;
	}

	public static double product(Sequence<Double> sequence, int from, int to) {
		double res = 1;
		for (int i = from; i <= to; i++) {
			res *= sequence.get(i);
		}
		return res;
	}

	public static double product(Sequence<Double> sequence, int from, int to, FitIndex isFit) {
		double res = 1;
		for (int i = from; i <= to; i++) {
			if (isFit.fit(i))
				res *= sequence.get(i);
		}
		return res;
	}
}
