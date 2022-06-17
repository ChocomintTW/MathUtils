package net.chocomint.math.calculus;

import net.chocomint.math.complex.Complex;

@FunctionalInterface
public interface D2CFunction {
	Complex f(double x);
}
