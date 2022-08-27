package net.chocomint.math.matrix;

import net.chocomint.math.complex.Complex;
import net.chocomint.math.utils.ListUtils;

import java.util.ArrayList;
import java.util.List;

public class MatrixUtils {

	public static ComplexMatrix rowVector(List<Complex> vector) {
		ComplexMatrix matrix = new ComplexMatrix(1, vector.size(), Complex.ZERO.copy());
		for (int i = 0; i < vector.size(); i++) {
			matrix.set(0, i, vector.get(i));
		}
		return matrix;
	}

	public static ComplexMatrix rowVector(Complex... vector) {
		return rowVector(List.of(vector));
	}

	public static ComplexMatrix columnVector(List<Complex> vector) {
		ComplexMatrix matrix = new ComplexMatrix(vector.size(), 1, Complex.ZERO.copy());
		for (int i = 0; i < vector.size(); i++) {
			matrix.set(i, 0, vector.get(i));
		}
		return matrix;
	}

	public static ComplexMatrix columnVector(Complex... vector) {
		return columnVector(List.of(vector));
	}

	public static ComplexMatrix selfTransMultiply(List<Complex> vector) {
		try {
			return columnVector(vector).multiply(rowVector(vector));
		} catch (MatrixSizeMismatchException e) {
			throw new RuntimeException("[MatrixSizeMismatchException] " + e);
		}
	}

	public record QR(ComplexMatrix Q, ComplexMatrix R) {
		@Override
		public String toString() {
			return "Q:\n" + Q + "\nR:\n" + R;
		}
	}

	public static QR QRDecompose(ComplexMatrix matrix) throws MatrixSizeMismatchException {
		if (matrix.isSquare()) {
			int size = matrix.getRowSize();
			ComplexMatrix m = matrix.copy();
			ComplexMatrix R = matrix.copy();
			ComplexMatrix Q = ComplexMatrix.I(size);

			for (int i = 0; i < size - 1; i++) {
				List<Complex> vector = new ArrayList<>();
				for (int j = i; j < size; j++) {
					vector.add(m.get(j, i));
				}

				Complex generalLength = ListUtils.generalLength(vector);
				vector.set(0, vector.get(0).copy().subtract(generalLength));
				List<Complex> omega = ListUtils.unit(vector);

				ComplexMatrix H_hat = ComplexMatrix.I(size - i).copy().subtract(MatrixUtils.selfTransMultiply(omega).multiply(2));
				ComplexMatrix H = new ComplexMatrix(size, size, Complex.ZERO.copy());
				for (int j = 0; j < i; j++) {
					H.set(j, j, new Complex(1));
				}
				for (int j = i; j < size; j++) {
					for (int k = i; k < size; k++) {
						H.set(j, k, H_hat.get(j - i, k - i));
					}
				}

				m = H.multiply(matrix);
				R = H.multiply(R);
				Q = Q.multiply(H);
			}
			return new QR(Q, R);
		} else {
			throw new MatrixSizeMismatchException(MatrixSizeMismatchException.SizeMismatchType.SQUARE);
		}
	}

	public static ComplexMatrix iterate(ComplexMatrix matrix) throws MatrixSizeMismatchException {
		ComplexMatrix m = matrix.copy();
		for (int i = 0; i < 5000; i++) {
			QR qr = QRDecompose(m);
			m = qr.R.multiply(qr.Q);
		}
		return m;
	}
}
