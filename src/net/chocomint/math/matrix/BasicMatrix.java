package net.chocomint.math.matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static net.chocomint.math.matrix.MatrixSizeMismatchException.SizeMismatchType.SAME_SIZE;

/**
 * The {@code BasicMatrix} class store a basic two-dimensional {@code List}.
 *
 * @param <T> the type of stored data
 * @param <M> the implemented matrix
 */
public abstract class BasicMatrix<T, M extends BasicMatrix<T, ?>> {
	List<List<T>> matrix = new ArrayList<>();

	/**
	 * Initialize {@code BasicMatrix} object with a 2D array
	 * @param matrix a 2D array
	 */
	public BasicMatrix(T[][] matrix) {
		for (T[] array : matrix) {
			this.matrix.add(new ArrayList<>(Arrays.asList(array)));
		}
	}

	/**
	 * Initialize {@code BasicMatrix} object with several lists
	 * @param lists several lists
	 */
	@SafeVarargs
	public BasicMatrix(List<T>... lists) {
		for (List<T> array : lists) {
			this.matrix.add(new ArrayList<>(array));
		}
	}

	/**
	 * Initialize {@code BasicMatrix} object with fixed {@code row}, {@code column} and {@code init}
	 * @param row    Row size of the matrix
	 * @param column Column size of the matrix
	 * @param init   Initial value of each element
	 */
	public BasicMatrix(int row, int column, T init) {
		for (int i = 0; i < row; i++) {
			ArrayList<T> list = new ArrayList<>();
			for(int j = 0; j < column; j++) {
				list.add(init);
			}
			matrix.add(list);
		}
	}

	/**
	 * Set this {@code BasicMatrix} to another {@code Matrix}
	 * @param other another {@code Matrix}. The type extends {@code BasicMatrix}
	 * @throws MatrixSizeMismatchException If the size of this {@code BasicMatrix} is different with another
	 */
	public void set(M other) throws MatrixSizeMismatchException {
		if (sizeMatch(this, other)) {
			for (int i = 0; i < other.getRowSize(); i++) {
				for (int j = 0; j < other.getColumnSize(); j++) {
					this.set(i, j, other.get(i, j));
				}
			}
		} else throw new MatrixSizeMismatchException(SAME_SIZE);
	}

	/**
	 * Set a value in the {@code BasicMatrix}
	 * @param i row index
	 * @param j colum index
	 * @param value the value you want to set
	 */
	public void set(int i, int j, T value) {
		List<T> list = this.matrix.get(i);
		list.set(j, value);
		this.matrix.set(i, list);
	}

	/**
	 * Get a value in the {@code BasicMatrix}
	 * @param i row index
	 * @param j column index
	 * @return the value at {@code (i, j)}
	 */
	public T get(int i, int j) {
		if (i < 0 || i >= this.getRowSize() || j < 0 || j >= this.getColumnSize())
			return null;
		return this.matrix.get(i).get(j);
	}

	/**
	 * Get a whole row as a {@link List}
	 * @param row row index
	 * @return the {@code List} of the row
	 */
	public List<T> getRow(int row) {
		return this.matrix.get(row);
	}

	/**
	 * Get a whole column as a {@link List}
	 * @param column row index
	 * @return the {@code List} of the column
	 */
	public List<T> getColumn(int column) {
		List<T> col = new ArrayList<>();
		for (int i = 0; i < this.getRowSize(); i++) {
			col.add(this.get(i, column));
		}
		return col;
	}

	/**
	 * Get the {@code BasicMatrix} as a 2D {@code List}
	 * @return 2D {@code List}
	 */
	public List<List<T>> getAsList() {
		return matrix;
	}

	/**
	 * Get row size of the {@code BasicMatrix}
	 * @return row size
	 */
	public int getRowSize() {
		return this.matrix.size();
	}

	/**
	 * Get column size of the {@code BasicMatrix}
	 * @return column size
	 */
	public int getColumnSize() {
		return this.matrix.get(0).size();
	}

	/**
	 * @param left  first {@code BasicMatrix}
	 * @param right second {@code BasicMatrix}
	 * @return {@code true } if two {@code BasicMatrix} are the same size <br>
	 *         {@code false} if two {@code BasicMatrix} are NOT the same size
	 */
	public static boolean sizeMatch(BasicMatrix<?, ?> left, BasicMatrix<?, ?> right) {
		return left.getRowSize() == right.getRowSize() && left.getColumnSize() == right.getColumnSize();
	}

	/**
	 * @return {@code true } if the {@code BasicMatrix} are square <br>
	 *         {@code false} if the {@code BasicMatrix} are NOT square
	 */
	public boolean isSquare() {
		return this.getRowSize() == this.getColumnSize();
	}

	@Override
	public boolean equals(Object o) {
		if (o == null || getClass() != o.getClass()) return false;
		BasicMatrix<?, ?> right = (BasicMatrix<?, ?>) o;
		for(int i = 0; i < this.getRowSize(); i++) {
			for (int j = 0; j < this.getColumnSize(); j++) {
				if(!Objects.equals(this.get(i, j), right.get(i, j)))
					return false;
			}
		}
		return true;
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), matrix);
	}

	@Override
	public String toString() {
		StringBuilder out = new StringBuilder();
		matrix.forEach(list -> out.append(list).append("\n"));
		return out.toString();
	}
}
