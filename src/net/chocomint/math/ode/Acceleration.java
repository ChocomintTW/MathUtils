package net.chocomint.math.ode;

public interface Acceleration {
	double apply(double x, double y, double vy);
}
