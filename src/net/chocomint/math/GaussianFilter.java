package net.chocomint.math;

import net.chocomint.math.matrix.Matrix;

public class GaussianFilter {
	public static Matrix getGaussianKernel(int n, double sigma) {
		Matrix kernel = new Matrix(n, n, 0);
		int x = (n - 1) / 2;
		double sum = 0;
		for (int i = -x; i <= x; i++) {
			for (int j = -x; j <= x; j++) {
				double g = Functions.gaussian(i, j, sigma);
				sum += g;
				kernel.set(i + x, j + x, g);
			}
		}
		return kernel.divide(sum).copy();
	}
}
