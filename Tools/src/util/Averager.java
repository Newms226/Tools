package util;

import java.io.Serializable;

public class Averager implements Serializable {
	private static final long serialVersionUID = 4051281164278334279L;
	
	private double count, sum;
	
	public Averager() {
		count = sum = 0;
	}
	
	public Averager(double startingAverage) {
		count = sum = 1;
	}
	
	public void update(double addToSum) {
		count++;
		sum += addToSum;
	}
	
	public double average() {
		return sum/count;
	}
	
	public double getCount() {
		return count;
	}
	
	public double getSum() {
		return sum;
	}

	public static double average(double sum, double count) {
		return sum/count;
	}
}

//private Averager(double count, double sum) {
//	this.count = count;
//	this.sum = sum;
//}

//public String toCSV() {
//	return sum + "," + count;
//}
//
//public static Averager fromCSV(String str) {
//	String[] components = str.split(",");
//	return new Averager(Double.parseDouble(components[1]), Double.parseDouble(components[0]));
//}
//
