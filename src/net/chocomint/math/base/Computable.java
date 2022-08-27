package net.chocomint.math.base;

/**
 * This interface provides uniform computation functions for objects of each class that implements it
 *
 * @param <T> the type of the object that is computable
 */
public interface Computable<T> {

	/**
	 * Negate the object
	 */
	T negate();

	/**
	 * Add two objects
	 */
	T add(T other);

	/**
	 * Subtract two objects
	 */
	T subtract(T other);

	/**
	 * Multiply object with constant number
	 * @param other constant number
	 */
	T multiply(double other);

	/**
	 * Divide object with constant number
	 * @param other constant number
	 */
	T divide(double other);

	/**
	 * Multiply two objects
	 * @param other another objects that you want to multiply
	 * @throws UnsupportedOperationException If the class doesn't implement this
	 */
	default T multiply(T other) {
		throw new UnsupportedOperationException("Multiply operation is not support in this class");
	}

	/**
	 * Divide two objects
	 * @param other another objects that you want to divide
	 * @throws UnsupportedOperationException If the class doesn't implement this
	 */
	default T divide(T other) {
		throw new UnsupportedOperationException("Divide operation is not support in this class");
	}
}
