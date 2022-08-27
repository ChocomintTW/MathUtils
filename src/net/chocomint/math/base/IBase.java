package net.chocomint.math.base;

/**
 * This interface provides {@code set} and {@code copy} function
 * @param <T>
 */
public interface IBase<T> {
	void set(T other) throws Exception;
	T copy();
}
