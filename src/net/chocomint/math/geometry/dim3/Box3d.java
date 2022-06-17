package net.chocomint.math.geometry.dim3;

public class Box3d {
	private final Vec3d startPoint;
	private final Vec3d endPoint;

	public Box3d(double x1, double y1, double z1, double x2, double y2, double z2) {
		this.startPoint = new Vec3d(x1, y1, z1);
		this.endPoint = new Vec3d(x2, y2, z2);
	}

	public Vec3d getCenter() {
		return startPoint.copy().add(endPoint).divide(2);
	}

	public static Box3d createUnit(double x, double y, double z) {
		return new Box3d(x, y, z, x + 1, y + 1, z + 1);
	}
}
