package net.chocomint.math.geometry.dim2;

import net.chocomint.math.base.IBase;

import java.util.ArrayList;
import java.util.List;

public class Polygon implements IBase<Polygon> {
	private List<Vec2d> points;

	public Polygon(Vec2d... points) {
		this.points = new ArrayList<>(List.of(points));
	}

	public List<Vec2d> getPoints() {
		return points;
	}

	@Override
	public void set(Polygon other) {
	}

	@Override
	public Polygon copy() {
		Polygon p = new Polygon();
		this.getPoints().forEach(point -> p.getPoints().add(point.copy()));
		return p;
	}

	public List<Vec2d> getNorms() {
		List<Vec2d> norms = new ArrayList<>();
		for (int i = 0; i < points.size() - 1; i++) {
			norms.add(Line2d.through(points.get(i), points.get(i + 1)).normalVector());
		}
		norms.add(Line2d.through(points.get(0), points.get(points.size() - 1)).normalVector());
		return norms;
	}
}
