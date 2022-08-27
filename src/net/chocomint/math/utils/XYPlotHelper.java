package net.chocomint.math.utils;

import net.chocomint.math.geometry.dim2.Vec2d;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.renderer.xy.XYLineAndShapeRenderer;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.UnaryOperator;

public class XYPlotHelper {

	public static JFreeChart createXYChart(String title, List<Vec2d> points, XYLineAndShapeRenderer renderer) {
		XYSeries series = new XYSeries("");
		points.forEach(p -> series.add(p.getX(), p.getY()));
		XYSeriesCollection data = new XYSeriesCollection();
		data.addSeries(series);
		JFreeChart chart = ChartFactory.createXYLineChart(title, "x", "y", data);
		chart.getXYPlot().setRenderer(renderer);
		chart.removeLegend();

		return chart;
	}

	public static JFreeChart createXYChart(String title, List<Vec2d> points) {
		return createXYChart(title, points, getXYDefaultRenderer());
	}

	public static List<Vec2d> fromFunction(UnaryOperator<Double> func, double from, double to, double step) {
		List<Vec2d> list = new ArrayList<>();
		for (double i = from; i <= to; i += step) {
			list.add(new Vec2d(i, func.apply(i)));
		}
		return list;
	}

	public static XYLineAndShapeRenderer getXYDefaultRenderer() {
		XYLineAndShapeRenderer renderer = new XYLineAndShapeRenderer();
		renderer.setSeriesPaint(0, Color.BLUE);
		renderer.setSeriesShapesVisible(0, false);
		return renderer;
	}
}
