package net.chocomint.math.matrix;

import net.chocomint.math.base.IBase;

import java.util.List;

/**
 * The {@code ComputableMatrix} extends {@link BasicMatrix} and implements {@link IBase}
 * @param <T> the type of stored data
 * @param <M> the implemented matrix
 */
public abstract class ComputableMatrix<T, M extends ComputableMatrix<T, ?>> extends BasicMatrix<T, M> implements IBase<M> {

	/**
	 * Initialize {@code ComputableMatrix} object with a 2D array
	 * @param matrix a 2D array
	 */
	public ComputableMatrix(T[][] matrix) {
		super(matrix);
	}

	/**
	 * Initialize {@code ComputableMatrix} object with several lists
	 * @param lists several lists
	 */
	@SafeVarargs
	public ComputableMatrix(List<T>... lists) {
		super(lists);
	}

	/**
	 * Initialize {@code ComputableMatrix} object with fixed {@code row}, {@code column} and {@code init}
	 * @param row    Row size of the matrix
	 * @param column Column size of the matrix
	 * @param init   Initial value of each element
	 */
	public ComputableMatrix(int row, int column, T init) {
		super(row, column, init);
	}

	/**
	 * Copy the {@code ComputableMatrix}<br>
	 * implemented from {@link IBase}
	 */
	public abstract M copy();

	/**
	 * Negate the {@code ComputableMatrix}
	 */
	public M negate() {
		return this.multiply(-1);
	}

	/**
	 * Add two {@code ComputableMatrix}
	 * @throws MatrixSizeMismatchException if the size of two {@code ComputableMatrix} are NOT the same
	 */
	public abstract M add(M other) throws MatrixSizeMismatchException;

	/**
	 * Subtract two {@code ComputableMatrix}
	 * @throws MatrixSizeMismatchException if the size of two {@code ComputableMatrix} are NOT the same
	 */
	public abstract M subtract(M other) throws MatrixSizeMismatchException;

	/**
	 * Multiply {@code ComputableMatrix} with constant number
	 * @param other constant number
	 */
	public abstract M multiply(double other);

	/**
	 * Divide {@code ComputableMatrix} with constant number
	 * @param other constant number
	 */
	public abstract M divide(double other);

	/**
	 * Multiply two {@code ComputableMatrix}
	 * @throws MatrixSizeMismatchException
	 * If the column size of the {@code ComputableMatrix} is NOT the same as the row size of the other one
	 */
	public abstract M multiply(M other) throws MatrixSizeMismatchException;
}
