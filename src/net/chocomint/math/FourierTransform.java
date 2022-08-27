package net.chocomint.math;

import net.chocomint.math.complex.Complex;
import net.chocomint.math.matrix.ComplexMatrix;
import net.chocomint.math.matrix.MatrixSizeMismatchException;

import java.util.ArrayList;
import java.util.List;

public class FourierTransform {
	public static List<Double> DFT(List<Double> data) {
		final int n = data.size();
		ComplexMatrix F = new ComplexMatrix(n, n, Complex.ZERO);
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				F.set(i, j, Complex.pow(Complex.exp(Complex.i.copy().multiply(-2 * Math.PI / n)), i * j));
			}
		}
		ComplexMatrix cData = new ComplexMatrix(n, 1, Complex.ZERO);
		for (int i = 0; i < n; i++) {
			cData.set(i, 0, new Complex(data.get(i)));
		}

		List<Double> res = new ArrayList<>();
		try {
			F.copy().multiply(cData).getColumn(0).forEach(complex -> res.add(complex.norm() / n));
		} catch (MatrixSizeMismatchException e) {
			e.printStackTrace();
		}
		return res;
	}
}
