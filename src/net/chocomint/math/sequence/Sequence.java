package net.chocomint.math.sequence;

@FunctionalInterface
public interface Sequence<T> {
	T get(int k);
}
