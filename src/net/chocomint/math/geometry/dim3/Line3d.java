package net.chocomint.math.geometry.dim3;

public class Line3d {
	private final Vec3d point;
	private final Vec3d directionVector;

	public Line3d(Vec3d point, Vec3d vector) {
		this.point = point;
		this.directionVector = vector;
	}

	/**
	 * Evaluate the distance of two skew {@code Line3d}
	 */
	public static double skewLinesDistance(Line3d l1, Line3d l2) {
		Vec3d n = l1.directionVector.cross(l2.directionVector);
		Plane E1 = new Plane(l1.point, n);
		Plane E2 = new Plane(l2.point, n);
		return Math.abs(E1.getD() - E2.getD()) / n.length();
	}

	public Vec3d intersectPlane(Plane E) {
		double d = new Vec3d(0, 0, -E.getD() / E.getNormalVector().getZ()).subtract(this.point).dot(E.getNormalVector()) / this.directionVector.copy().dot(E.getNormalVector());
		return this.point.copy().add(this.directionVector.copy().multiply(d));
	}
}
