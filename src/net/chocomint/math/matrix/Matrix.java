package net.chocomint.math.matrix;

import java.util.List;

import static net.chocomint.math.matrix.MatrixSizeMismatchException.SizeMismatchType.*;

public class Matrix extends ComputableMatrix<Double, Matrix> {

	public Matrix(Double[][] matrix) {
		super(matrix);
	}

	@SafeVarargs
	public Matrix(List<Double>... lists) {
		super(lists);
	}

	public Matrix(int row, int column, double init) {
		super(row, column, init);
	}

	public static Matrix I(int n) {
		Matrix i = new Matrix(n, n, 0);
		for (int j = 0; j < n; j++) {
			i.set(j, j, 1.0);
		}
		return i;
	}

	@Override
	public Double get(int i, int j) {
		if (i < 0 || i >= this.getRowSize() || j < 0 || j >= this.getColumnSize())
			return 0.0;
		else return super.get(i, j);
	}

	@Override
	public Matrix copy() {
		Matrix matrix = new Matrix(this.getRowSize(), this.getColumnSize(), 0);
		for (int i = 0; i < this.getRowSize(); i++) {
			for (int j = 0; j < this.getColumnSize(); j++) {
				matrix.set(i, j, this.get(i, j));
			}
		}
		return matrix;
	}

	@Override
	public Matrix add(Matrix other) throws MatrixSizeMismatchException {
		if (sizeMatch(this, other)) {
			for (int i = 0; i < other.getRowSize(); i++) {
				for (int j = 0; j < other.getColumnSize(); j++) {
					this.set(i, j, this.get(i, j) + other.get(i, j));
				}
			}
			return this;
		} else throw new MatrixSizeMismatchException(SAME_SIZE);
	}

	@Override
	public Matrix subtract(Matrix other) throws MatrixSizeMismatchException {
		if (sizeMatch(this, other)) {
			for (int i = 0; i < other.getRowSize(); i++) {
				for (int j = 0; j < other.getColumnSize(); j++) {
					this.set(i, j, this.get(i, j) - other.get(i, j));
				}
			}
			return this;
		} else throw new MatrixSizeMismatchException(SAME_SIZE);
	}

	@Override
	public Matrix multiply(double value) {
		for (int i = 0; i < this.getRowSize(); i++) {
			for (int j = 0; j < this.getColumnSize(); j++) {
				this.set(i, j, this.get(i, j) * value);
			}
		}
		return this;
	}

	@Override
	public Matrix divide(double value) {
		for (int i = 0; i < this.getRowSize(); i++) {
			for (int j = 0; j < this.getColumnSize(); j++) {
				this.set(i, j, this.get(i, j) / value);
			}
		}
		return this;
	}

	@Override
	public Matrix multiply(Matrix other) throws MatrixSizeMismatchException {
		if (this.getColumnSize() == other.getRowSize()) {
			int row = this.getRowSize();
			int column = other.getColumnSize();
			int n = this.getColumnSize();
			Matrix matrix = new Matrix(row, column, 0);
			for (int i = 0; i < row; i++) {
				for (int j = 0; j < column; j++) {
					double value = 0;
					for (int k = 0; k < n; k++) {
						value += this.get(i, k) * other.get(k, j);
					}
					matrix.set(i, j, value);
				}
			}
			return matrix;
		} else throw new MatrixSizeMismatchException(COL_ROW);
	}

	public Matrix trans() {
		Matrix res = new Matrix(this.getColumnSize(), this.getRowSize(), 0);
		for (int i = 0; i < this.getRowSize(); i++) {
			for (int j = 0; j < this.getColumnSize(); j++) {
				res.set(j, i, this.get(i, j));
			}
		}
		return res;
	}

	public double det() throws MatrixSizeMismatchException {
		if (this.isSquare()) {
			int n = this.getRowSize();
			if (n == 2) {
				return this.detDiagonal(1, 1);
			}
			else {
				Matrix b = new Matrix(n - 1, n - 1, 0);
				for (int i = 0; i < n - 1; i++) {
					for (int j = 0; j < n - 1; j++) {
						b.set(i, j, this.detDiagonal(i + 1, j + 1));
					}
				}
				return b.det() / Math.pow(this.get(0, 0), n - 2);
			}
		} else throw new MatrixSizeMismatchException(SQUARE);
	}

	private double detDiagonal(int m, int n) {
		return this.get(0, 0) * this.get(m, n) - this.get(0, n) * this.get(m, 0);
	}

	public double M(int row, int col) throws MatrixSizeMismatchException {
		int n = this.getRowSize();
		if (n == 2)
			throw new MatrixSizeMismatchException(2, MatrixSizeMismatchException.BIGGER);

		if (this.isSquare()) {
			Matrix tmp = new Matrix(n - 1, n - 1, 0);
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

	public double A(int row, int col) throws MatrixSizeMismatchException {
		return this.M(row, col) * ((row + col) % 2 == 0 ? 1 : -1);
	}

	public Matrix adj() throws MatrixSizeMismatchException {
		int n = this.getRowSize();
		if (n == 2)
			throw new MatrixSizeMismatchException(2, MatrixSizeMismatchException.BIGGER);

		if (this.isSquare()) {
			Matrix tmp = new Matrix(n, n, 0);
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

	public Matrix inverse() throws MatrixSizeMismatchException {
		int n = this.getRowSize();
		if (this.isSquare()) {
			if (n == 2) {
				Matrix matrix = new Matrix(2, 2, 0);
				double d = this.get(1, 1);
				matrix.set(1, 1, this.get(0, 0));
				matrix.set(0, 0, d);
				matrix.set(0, 1, -this.get(0, 1));
				matrix.set(1, 0, -this.get(1, 0));
				return matrix.divide(matrix.det());
			}
			else
				return this.adj().divide(this.det());
		}
		else
			throw new MatrixSizeMismatchException(SQUARE);
	}

	public static Matrix rowVector(Double... vector) {
		Matrix matrix = new Matrix(1, List.of(vector).size(), 0);
		for (int i = 0; i < List.of(vector).size(); i++) {
			matrix.set(0, i, List.of(vector).get(i));
		}
		return matrix;
	}

	public static Matrix columnVector(Double... vector) {
		Matrix matrix = new Matrix(List.of(vector).size(), 1, 0);
		for (int i = 0; i < List.of(vector).size(); i++) {
			matrix.set(i, 0, List.of(vector).get(i));
		}
		return matrix;
	}
}
