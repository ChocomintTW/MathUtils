package net.chocomint.math.base;

public interface IBase<T> {
	void set(T other) throws Exception;
	T copy();
}
