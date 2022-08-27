package net.chocomint.math.geometry.dim3;

public class Plane {
	private final double a, b, c, d;

	/**
	 * {@code E: ax+by+cz+d=0}
	 */
	public Plane(double a, double b, double c, double d) {
		this.a = a;
		this.b = b;
		this.c = c;
		this.d = d;
	}

	public Plane(Vec3d point, Vec3d normalVector) {
		double x0 = point.getX();
		double y0 = point.getY();
		double z0 = point.getZ();
		this.a = normalVector.getX();
		this.b = normalVector.getY();
		this.c = normalVector.getZ();
		this.d = -(a * x0 + b * y0 + c * z0);
	}

	public Vec3d getNormalVector() {
		return new Vec3d(a, b, c);
	}

	public double getD() {
		return d;
	}

	public static Plane through(Vec3d pt1, Vec3d pt2, Vec3d pt3) {
		Vec3d n = (pt1.copy().subtract(pt2)).cross(pt1.copy().subtract(pt3));
		return new Plane(pt1, n);
	}

	public static double angle(Plane E1, Plane E2) {
		return Vec3d.angle(E1.getNormalVector(), E2.getNormalVector());
	}

	@Override
	public String toString() {
		return a + "x+" + b + "y+" + c + "z+" + d + "=0";
	}
}
