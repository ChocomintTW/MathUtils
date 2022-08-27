package net.chocomint.math.matrix;

import net.chocomint.math.complex.Complex;

import java.util.List;

import static net.chocomint.math.matrix.MatrixSizeMismatchException.SizeMismatchType.*;

public class ComplexMatrix extends ComputableMatrix<Complex, ComplexMatrix> {

	public ComplexMatrix(Complex[][] matrix) {
		super(matrix);
	}

	@SafeVarargs
	public ComplexMatrix(List<Complex>... lists) {
		super(lists);
	}

	public ComplexMatrix(int row, int column, Complex init) {
		super(row, column, init.copy());
	}

	public static ComplexMatrix I(int n) {
		ComplexMatrix i = new ComplexMatrix(n, n, Complex.ZERO);
		for (int j = 0; j < n; j++) {
			i.set(j, j, new Complex(1));
		}
		return i.copy();
	}

	@Override
	public ComplexMatrix copy() {
		ComplexMatrix matrix = new ComplexMatrix(this.getRowSize(), this.getColumnSize(), Complex.ZERO);
		for (int i = 0; i < this.getRowSize(); i++) {
			for (int j = 0; j < this.getColumnSize(); j++) {
				matrix.set(i, j, this.get(i, j).copy());
			}
		}
		return matrix;
	}

	@Override
	public ComplexMatrix add(ComplexMatrix other) throws MatrixSizeMismatchException {
		if (sizeMatch(this, other)) {
			for (int i = 0; i < other.getRowSize(); i++) {
				for (int j = 0; j < other.getColumnSize(); j++) {
					this.get(i, j).add(other.get(i, j));
				}
			}
			return this;
		} else throw new MatrixSizeMismatchException(SAME_SIZE);
	}

	@Override
	public ComplexMatrix subtract(ComplexMatrix other) throws MatrixSizeMismatchException {
		if (sizeMatch(this, other)) {
			for (int i = 0; i < other.getRowSize(); i++) {
				for (int j = 0; j < other.getColumnSize(); j++) {
					this.get(i, j).subtract(other.get(i, j));
				}
			}
			return this;
		} else throw new MatrixSizeMismatchException(SAME_SIZE);
	}

	@Override
	public ComplexMatrix multiply(double value) {
		for (int i = 0; i < this.getRowSize(); i++) {
			for (int j = 0; j < this.getColumnSize(); j++) {
				this.get(i, j).multiply(value);
			}
		}
		return this;
	}

	@Override
	public ComplexMatrix divide(double value) {
		for (int i = 0; i < this.getRowSize(); i++) {
			for (int j = 0; j < this.getColumnSize(); j++) {
				this.get(i, j).divide(value);
			}
		}
		return this;
	}

	public ComplexMatrix divide(Complex value) {
		for (int i = 0; i < this.getRowSize(); i++) {
			for (int j = 0; j < this.getColumnSize(); j++) {
				this.get(i, j).divide(value);
			}
		}
		return this;
	}

	@Override
	public ComplexMatrix multiply(ComplexMatrix other) throws MatrixSizeMismatchException {
		if (this.getColumnSize() == other.getRowSize()) {
			int row = this.getRowSize();
			int column = other.getColumnSize();
			int n = this.getColumnSize();
			ComplexMatrix matrix = new ComplexMatrix(row, column, Complex.ZERO.copy());
			for (int i = 0; i < row; i++) {
				for (int j = 0; j < column; j++) {
					Complex value = Complex.ZERO.copy();
					for (int k = 0; k < n; k++) {
						value.add(this.get(i, k).copy().multiply(other.get(k, j).copy()));
					}
					matrix.set(i, j, value.copy());
				}
			}
			return matrix;
		} else throw new MatrixSizeMismatchException(COL_ROW);
	}

	public ComplexMatrix trans() {
		ComplexMatrix res = new ComplexMatrix(this.getColumnSize(), this.getRowSize(), Complex.ZERO);
		for (int i = 0; i < this.getRowSize(); i++) {
			for (int j = 0; j < this.getColumnSize(); j++) {
				res.set(j, i, this.get(i, j).copy());
			}
		}
		return res;
	}

	public Complex det() throws MatrixSizeMismatchException {
		if (this.isSquare()) {
			int n = this.getRowSize();
			if (n == 2) {
				return this.detDiagonal(1, 1);
			}
			else {
				ComplexMatrix b = new ComplexMatrix(n - 1, n - 1, Complex.ZERO.copy());
				for (int i = 0; i < n - 1; i++) {
					for (int j = 0; j < n - 1; j++) {
						b.set(i, j, this.detDiagonal(i + 1, j + 1));
					}
				}
				return b.det().copy().divide(Complex.pow(this.get(0, 0), new Complex(n - 2)).PV());
			}
		} else throw new MatrixSizeMismatchException(SQUARE);
	}

	private Complex detDiagonal(int m, int n) {
		return this.get(0, 0).copy().multiply(this.get(m, n)).subtract(this.get(0, n).copy().multiply(this.get(m, 0)));
	}

	public Complex M(int row, int col) throws MatrixSizeMismatchException {
		int n = this.getRowSize();
		if (n == 2)
			throw new MatrixSizeMismatchException(2, MatrixSizeMismatchException.BIGGER);

		if (this.isSquare()) {
			ComplexMatrix tmp = new ComplexMatrix(n - 1, n - 1, Complex.ZERO.copy());
			for (int i = 0; i < n - 1; i++) {
				for (int j = 0; j < n - 1; j++) {
					if (i < row && j < col)
						tmp.set(i, j, this.get(i, j));
					else if (i < row)
						tmp.set(i, j, this.get(i, j + 1));
					else if (j < col)
						tmp.set(i, j, this.get(i + 1, j));
					else tmp.set(i, j, this.get(i + 1, j + 1));
				}
			}
			return tmp.det();
		}
		else
			throw new MatrixSizeMismatchException(SQUARE);
	}

	public Complex A(int row, int col) throws MatrixSizeMismatchException {
		return this.M(row, col).copy().multiply((row + col) % 2 == 0 ? 1 : -1);
	}

	public ComplexMatrix adj() throws MatrixSizeMismatchException {
		int n = this.getRowSize();
		if (n == 2)
			throw new MatrixSizeMismatchException(2, MatrixSizeMismatchException.BIGGER);

		if (this.isSquare()) {
			ComplexMatrix tmp = new ComplexMatrix(n, n, Complex.ZERO.copy());
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					tmp.set(i, j, this.A(i, j));
				}
			}
			return tmp.trans();
		}
		else
			throw new MatrixSizeMismatchException(SQUARE);
	}

	public ComplexMatrix inverse() throws MatrixSizeMismatchException {
		int n = this.getRowSize();
		if (this.isSquare()) {
			if (n == 2) {
				ComplexMatrix matrix = new ComplexMatrix(2, 2, Complex.ZERO.copy());
				Complex d = this.get(1, 1);
				matrix.set(1, 1, this.get(0, 0).copy());
				matrix.set(0, 0, d.copy());
				matrix.set(0, 1, this.get(0, 1).copy().negate());
				matrix.set(1, 0, this.get(1, 0).copy().negate());
				return matrix.divide(matrix.det());
			}
			else
				return this.adj().divide(this.det());
		}
		else
			throw new MatrixSizeMismatchException(SQUARE);
	}

	public ComplexMatrix getMatrixFrom(int row, int column) {
		if (row == 0 && column == 0) return this.copy();
		else {
			int rowSize = this.getRowSize();
			int colSize = this.getColumnSize();
			ComplexMatrix matrix = new ComplexMatrix(rowSize - row, colSize - column, Complex.ZERO.copy());
			for (int i = row; i < rowSize; i++) {
				for (int j = column; j < colSize; j++) {
					matrix.set(i - row, j - column, this.get(i, j));
				}
			}
			return matrix;
		}
	}
}
