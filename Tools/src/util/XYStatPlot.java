package util;

import java.util.Set;

public class XYStatPlot {
	
	public class XYMapping implements Comparable<XYMapping>{
		final int x, y;
		
		public XYMapping(int x, int y) {
			this.x = x;
			this.y = y;
		}
		
		@Override
		public int compareTo(XYMapping that) {
			if (x < that.x) return -1;
			if (x > that.x) return 1;
			if (y < that.y) return -1;
			if (y > that.y) return 1;
			return 0;
		}
		
		@Override
		public boolean equals(Object o) {
			if (o == null) return false;
			if (o == this) return true;
			
			if (!o.getClass().equals(XYMapping.class)) return false;
			
			XYMapping that = (XYMapping) o;
			if (x != that.x) return false;
			if (y != that.y) return false;
			
			return true;
		}
		
		@Override
		public String toString() {
			return "(" + x + ", " + y + ")";
		}
	}
	
	private Set<XYMapping> statPlot;

	public XYStatPlot() {
		// TODO Auto-generated constructor stub
		statPlot = new HashSet<>();
	}
	
	public XYMapping add(XYMapping) {
		
	}
	
	public Set<XYMapping> sort() {
		
	}

}
