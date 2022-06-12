package net.chocomint.math.matrix;

public class MatrixSizeMismatchException extends Exception {
	public static final boolean ONLY = true;
	public static final boolean BIGGER = false;

	public MatrixSizeMismatchException(SizeMismatchType type) {
		super(type.massage);
	}

	public MatrixSizeMismatchException(int size, boolean type) {
		super(type ? "The operation only require " + size + "x" + size + " matrix!"
				: "The operation require the size of the matrix bigger than" + size + "x" + size + "!");
	}

	public enum SizeMismatchType {
		SAME_SIZE("The size of two matrix are not the same!"),
		COL_ROW("The former matrix's row-size should equal to the later one's column-size!"),
		VEC_MULTIPLY("The matrix's size should equal to the vector's size!"),
		SQUARE("The matrix should be square!");

		final String massage;

		SizeMismatchType(String msg) {
			massage = msg;
		}
	}
}
