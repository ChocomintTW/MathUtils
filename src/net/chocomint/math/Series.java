package net.chocomint.math;

public class Series {
	public static double sum(Sequence sequence, int from, int to) {
		double res = 0;
		for (int i = from; i <= to; i++)
			res += sequence.get(i);
		return res;
	}

	public static double sumInf(Sequence sequence, int init) {
		double res = 0, val = 0;
		val = sequence.get(init);
		for (int i = init; !Functions.doubleNear(val, 0); i++) {
			if (i > 1000000) {
				return res;
			}
			val = sequence.get(i);
			res += val;
		}
		return res;
	}

	public static double product(Sequence sequence, int from, int to) {
		double res = 1;
		for (int i = from; i <= to; i++) {
			res *= sequence.get(i);
		}
		return res;
	}
}
