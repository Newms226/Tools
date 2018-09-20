package util;

import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;

import edu.princeton.cs.algs4.StdDraw;

public class Grapher {
	public static final Comparator<Entry<Double, Double>> X_Y_SORTED = 
			(a, b) -> {
				if (a.getKey() < b.getKey()) return -1;
				if (a.getKey() > b.getKey()) return 1;
				if (a.getValue() < b.getValue()) return -1;
				if (a.getValue() > b.getValue()) return 1;
				return 0;
			};
	
	private final TreeSet<Entry<Double, Double>> statPlot;
	private double xRange, yRange, xMin, xMax, yMin, yMax;
	private double increaseBy; // amount to increase by from point to point
	
	
	public Grapher(Map<Double, Double> statPlot) {
		if (statPlot.size() < 2) {
			throw new IllegalArgumentException("Can only graph with at least "
					+ "2 values. Size: " + statPlot.size());
		}
		
		this.statPlot = new TreeSet<>(X_Y_SORTED);
		this.statPlot.addAll(statPlot.entrySet());
		
		prepareXMinAndMax();
		prepareYMinAndMax(statPlot);
	} 
	
	private void prepareYMinAndMax(Map<Double, Double> statPlot) {
		List<Double> sortedYValues = statPlot.values().stream()
				.sorted()
				.collect(Collectors.toList());
		yMin = sortedYValues.get(0);
		yMax = sortedYValues.get(sortedYValues.size() - 1);
	}
	
	private void prepareXMinAndMax() {
		xMin = statPlot.first().getKey();
		xMax = statPlot.last().getKey();
	}
	
	public void graph(){
		double x, y;
		System.out.println("Generating graph...");
		generateAxis();
		System.out.println("Graphing...");
		
		StdDraw.setPenColor(StdDraw.PRINCETON_ORANGE);
		Iterator<Entry<Double, Double>> it = statPlot.iterator();
		Entry<Double, Double> current = it.next(), 
				              next = it.next();
		drawLine(it, current, next);
		drawPoints();
	} // end graph
	
	private void drawLine(Iterator<Entry<Double, Double>> it, 
			              Entry<Double, Double> start, Entry<Double, Double> end) {
		if (end == null) return; // base case
		
		StdDraw.line(start.getKey(), start.getValue(), end.getKey(), end.getValue());
		
		if (!it.hasNext()) return;
		// else
		drawLine(it, end, it.next());
	}
	
	private void drawPoints() {
		StdDraw.setPenColor(StdDraw.BLUE);
		StdDraw.setPenRadius(.005);
		statPlot.stream()
			.forEach(entry -> {
				StdDraw.point(entry.getKey(), entry.getValue());
			});
		System.out.println("Graphing complete.");
	}
	
	/* generateAxsis draws the max and min of both the
	 * x and y axis, draws a line to represent them, and
	 * plots axis points at every 1/20 of the respective
	 * range. 
	 */
	private void generateAxis() {
		xRange = Math.abs(xMin) + Math.abs(xMax);
		yRange = Math.abs(yMin) + Math.abs(yMax);
		StdDraw.setXscale(xMin - (xRange / 10), xMax);
		StdDraw.setYscale(yMin - (yRange / 10), yMax);
		
		StdDraw.setPenColor(StdDraw.BLACK);
		StdDraw.line(xMin, 0, xMax, 0); // xAxis
		StdDraw.line(0, yMin, 0, yMax); //yAxis
		
		// draw x axis points
		StdDraw.setPenColor(StdDraw.GRAY);
		double xLineHeigth = yRange/80;
		for (double i = xMin; i < xMax; i += xRange/20) {
			StdDraw.line(i, -xLineHeigth, i, xLineHeigth);
		}
		// draw y axis points
		double yLineHeigth = xRange/80;
		for (double i = yMin; i < yMax; i += yRange/20) {
			StdDraw.line(-yLineHeigth, i, yLineHeigth, i);
		}
		
		// print xMin, xMax, yMin, yMax
		StdDraw.setPenColor(StdDraw.RED);
		StdDraw.textRight(xMax, 0, "" + (int)xMax);
		StdDraw.textLeft(xMin, 0, "" + (int)xMin);
		StdDraw.textLeft(0, yMax - yMax/25, "" + (int)yMax);
		StdDraw.textLeft(0, yMin - yMin/25, "" + (int)yMin);
	} // end generateAxis
	
	
	
	// begin main
	public static void main(String[] args) {
		SortedMap<Double, Double> entrySet = new TreeMap<>();
		entrySet.put(new Double(5), new Double(10));
		entrySet.put(new Double(1), new Double(8));
		entrySet.put(new Double(0), new Double(7));
		
		Grapher test = new Grapher(entrySet);
		test.graph();
	} // end main
} // end Grapher class
