package net.chocomint.math.geometry.dim2;

import net.chocomint.math.Pair;
import net.chocomint.math.annotations.Utils;

import java.util.concurrent.atomic.AtomicReference;

@Utils
public class Collision {
	public static Pair<Double, Double> getMinMaxForPolygonOnSeparatingLine(Polygon polygon, Line2d separatingLine) {
		Vec2d dir = separatingLine.directionVector();
		AtomicReference<Double> min = new AtomicReference<>(polygon.getPoints().get(0).projection(dir).length());
		AtomicReference<Double> max = new AtomicReference<>(polygon.getPoints().get(0).projection(dir).length());
		polygon.getPoints().forEach(p -> {
			double proj = p.projection(dir).length();
			if (proj < min.get()) min.set(proj);
			if (proj > max.get()) max.set(proj);
		});
		return new Pair<>(min.get(), max.get());
	}

	public static boolean isCollision(Polygon p1, Polygon p2) {
		for (Vec2d norm1 : p1.getNorms()) {
			Pair<Double, Double> minMax1 = getMinMaxForPolygonOnSeparatingLine(p1, Line2d.from(norm1));
			Pair<Double, Double> minMax2 = getMinMaxForPolygonOnSeparatingLine(p2, Line2d.from(norm1));
			if (minMax1.getLeft() > minMax2.getRight() || minMax2.getLeft() > minMax1.getRight()) return false;
		}
		for (Vec2d norm2 : p2.getNorms()) {
			Pair<Double, Double> minMax1 = getMinMaxForPolygonOnSeparatingLine(p1, Line2d.from(norm2));
			Pair<Double, Double> minMax2 = getMinMaxForPolygonOnSeparatingLine(p2, Line2d.from(norm2));
			if (minMax1.getLeft() > minMax2.getRight() || minMax2.getLeft() > minMax1.getRight()) return false;
		}
		return true;
	}
}
