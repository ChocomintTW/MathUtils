package net.chocomint.math.matrix;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static net.chocomint.math.matrix.MatrixSizeMismatchException.SizeMismatchType.SAME_SIZE;

public abstract class BasicMatrix<T, M extends BasicMatrix<T, ?>> {
	List<List<T>> matrix = new ArrayList<>();

	public BasicMatrix(T[][] matrix) {
		for (T[] array : matrix) {
			this.matrix.add(new ArrayList<>(Arrays.asList(array)));
		}
	}

	@SafeVarargs
	public BasicMatrix(List<T>... lists) {
		for (List<T> array : lists) {
			this.matrix.add(new ArrayList<>(array));
		}
	}

	public BasicMatrix(int row, int column, T init) {
		for (int i = 0; i < row; i++) {
			ArrayList<T> list = new ArrayList<>();
			for(int j = 0; j < column; j++) {
				list.add(init);
			}
			matrix.add(list);
		}
	}

	public void set(M other) throws MatrixSizeMismatchException {
		if (sizeMatch(this, other)) {
			for (int i = 0; i < other.getRowSize(); i++) {
				for (int j = 0; j < other.getColumnSize(); j++) {
					this.set(i, j, other.get(i, j));
				}
			}
		} else throw new MatrixSizeMismatchException(SAME_SIZE);
	}

	public void set(int i, int j, T value) {
		List<T> list = this.matrix.get(i);
		list.set(j, value);
		this.matrix.set(i, list);
	}

	public T get(int i, int j) {
		return this.matrix.get(i).get(j);
	}

	public List<T> getRow(int row) {
		return this.matrix.get(row);
	}

	public List<T> getColumn(int column) {
		List<T> col = new ArrayList<>();
		for (int i = 0; i < this.getRowSize(); i++) {
			col.add(this.get(i, column));
		}
		return col;
	}

	public List<List<T>> getAsList() {
		return matrix;
	}

	public int getRowSize() {
		return this.matrix.size();
	}

	public int getColumnSize() {
		return this.matrix.get(0).size();
	}

	public static boolean sizeMatch(BasicMatrix<?, ?> left, BasicMatrix<?, ?> right) {
		return left.getRowSize() == right.getRowSize() && left.getColumnSize() == right.getColumnSize();
	}

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
