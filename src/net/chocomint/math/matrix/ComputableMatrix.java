package net.chocomint.math.matrix;

import net.chocomint.math.base.IBase;

import java.util.List;

public abstract class ComputableMatrix<T, M extends ComputableMatrix<T, ?>> extends BasicMatrix<T, M> implements IBase<M> {

	public ComputableMatrix(T[][] matrix) {
		super(matrix);
	}

	@SafeVarargs
	public ComputableMatrix(List<T>... lists) {
		super(lists);
	}

	public ComputableMatrix(int row, int column, T init) {
		super(row, column, init);
	}

	public abstract M copy();

	public M negate() {
		return this.multiply(-1);
	}

	public abstract M add(M other) throws MatrixSizeMismatchException;

	public abstract M subtract(M other) throws MatrixSizeMismatchException;

	public abstract M multiply(double other);

	public abstract M divide(double other);

	public abstract M multiply(M other) throws MatrixSizeMismatchException;
}
