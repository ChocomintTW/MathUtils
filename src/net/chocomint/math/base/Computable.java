package net.chocomint.math.base;

public interface Computable<T> {
	T negate();
	T add(T other);
	T subtract(T other);
	T multiply(double other);
	T divide(double other);
	default T multiply(T other) {
		throw new UnsupportedOperationException("Multiply operation is not support in this class");
	}
	default T divide(T other) {
		throw new UnsupportedOperationException("Divide operation is not support in this class");
	}
}
