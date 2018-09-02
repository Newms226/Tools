package tools;

import java.util.Arrays;

/**
 * @author Michael Newman
 * @since 3.20.18
 * 
 * Description: ArrayTools is a group of static methods designed to work on arrays
 * 		* Similar to java.util.Arrays
 * 
 * Assumptions / Limitations:
 *    1. Assumes that the user ensures that the generic has been checked
 *    2. Only works with primative int and double
 */

public class ArrayTools {
	
	public static Object[] condenseArray(Object[] a) {
		Object[] toReturn = new Object[a.length];
		Object temp;
		int i = 0, j = 0;
		while (i < a.length) {
			temp = a[i];
			if (temp == null) {
				i++;
				continue;
			}
			toReturn[j] = temp;
			i++;
			j++;
		}
		return Arrays.copyOf(toReturn, j);
	}
	
	public static <T> int fillCount(T[] a) {
		int count = 0;
		for (T t: a) if (t != null) count++;
		return count;
	}
	
	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 																		  |
	 *                               PRESENT METHODS							  |
	 * 																		  |
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	
	public static boolean isPresent(int[] array, int key) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == key) return true;
		}
		return false;
	}
	
	public static boolean isPresent(double[] array, double key) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == key) return true;
		}
		return false;
	}
	
	public static <T> boolean isPresent(T[] array, T key) {
		for (int i = 0; i < array.length; i++) {
			if (array[i] == key) return true;
		}
		return false;
	}
	
//	public static <T> boolean isPresent(PerfectArray<T> array, T key) {
//		for (int i = 0; i < array.size(); i++) {
//			if (array.get(i) == key) return true;
//		}
//		return false;
//	}
	
	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 																		  |
	 *                               COPY METHODS								  |
	 * 																		  |
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	
//	public static <T extends Object> T[] copy(T[] toCopy){
//		int size = toCopy.length;
//		T[] copy = (T[]) new Object[size];
//		
//		for (int i = 0; i < size; i++) {
//			copy[i] = toCopy[i];
//		}
//		
//		return copy;
//	}
//	
//	public static Comparable<Object>[] copy(Comparable<Object>[] toCopy){
//		int size = toCopy.length;
//		Comparable<Object>[] copy = new Comparable[size];
//		
//		for (int i = 0; i < size; i++) {
//			copy[i] = toCopy[i];
//		}
//		
//		return copy;
//	}
//	
//	public static <T> T[] copy(T[] toCopy, int newLength){
//		T[] copy = (T[]) new Object[newLength];
//		
//		for (int i = 0; i < newLength; i++) {
//			copy[i] = toCopy[i];
//		}
//		
//		return copy;
//	}
//
//	public static <T> T[] copy(T[] toCopy, int low, int high){
//		int size = Math.abs((high - low)) + 1;
//		T[] copy = (T[]) new Object[size];
//		
//		for (int i = 0; i < size; i++) {
//			copy[i] = toCopy[i];
//		}
//		return copy;
//	}
	
	
	
	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 																		  |
	 *                               WRAPPER METHODS							  |
	 * 																		  |
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	
	public static Integer[] parseintArray(int[] array) {
		Integer[] copy = new Integer[array.length];
		
		for(int i = 0; i < array.length; i++) {
			copy[i] = array[i];
		}
		
		return copy;
	}
	
	public static int[] parseIntegerArray(Integer[] array) {
		int[] copy = new int[array.length];
		
		for(int i = 0; i < array.length; i++) {
			copy[i] = array[i];
		}
		
		return copy;
	}
	
	public static Double[] parseDoubleArray(Double[] array) {
		Double[] copy = new Double[array.length];
		
		for(int i = 0; i < array.length; i++) {
			copy[i] = array[i];
		}
		
		return copy;
	}
	
	public static Double[] parsedoubleArray(double[] array) {
		Double[] copy = new Double[array.length];
		
		for(int i = 0; i < array.length; i++) {
			copy[i] = array[i];
		}
		
		return copy;
	}
	
	public static Long[] parselongArray(long[] array) {
		Long[] copy = new Long[array.length];
		
		for(int i = 0; i < array.length; i++) {
			copy[i] = array[i];
		}
		
		return copy;
	}
	
	public static Float[] parsefloatArray(float[] array) {
		Float[] copy = new Float[array.length];
		
		for(int i = 0; i < array.length; i++) {
			copy[i] = array[i];
		}
		
		return copy;
	}
	
	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 																		  |
	 *                              PRINT METHODS  							  |
	 * 																		  |
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	
	public static String toString(Object[] array) {
		return toString(array, 0, array.length - 1);
	}
	
	public static String toString(Object[] array, int low, int high) {
		StringBuffer buffer = new StringBuffer();
		for (int i = low; i <= high; i++) {
			buffer.append(array[i].toString() + "\n");
		}
		return buffer.toString();
	}
	
	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 																		  |
	 *                              RANDOM METHODS  							  |
	 * 																		  |
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	
//	public static int[] getRandomIntArray(int size, int maxValue) {
//		//TODO
//	}
//	public static int[] getRandomIntArray(int size, int maxValue, int minValue) {
//		//TODO
//	}
//	
//	public static double[] getRandomDoubleArray(int size, double maxValue) {
//		//TODO
//	}
//	
//	public static double[] getRandomDoubleArray(int size, double maxValue, double minValue) {
//		//TODO
//	}
	
	public static boolean[][] copy2D(boolean[][] toCopy) {
		int length = toCopy.length;
		boolean[][] toReturn = new boolean[length][];
		for (int column = 0; column < length; column++) {
			toReturn[column] = new boolean[toCopy[column].length];
			for (int row = 0; row < toCopy[column].length; row++) {
				toReturn[column][row] = toCopy[column][row]; 
			}
		}
		return toReturn;
	}
	
	public static String print2D(boolean[][] toPrint, boolean lineSeperated) {
		StringBuffer buffer = new StringBuffer("{");
		int rowLength,
		    columnLength = toPrint.length;
		for(int column = 0; column < columnLength; column++) {
			buffer.append("[");
			rowLength = toPrint[column].length;
			for (int row = 0; row < rowLength; row++) {
				buffer.append(toPrint[column][row]);
				if (row != rowLength - 1) {
					buffer.append(",");
				}
			}
			buffer.append("]");
			
			if (column != columnLength - 1) {
				buffer.append(",");
				if (lineSeperated) buffer.append("\n");
			}
		}
		buffer.append("}");
		return buffer.toString();
	}
	
	public static double[] toDoubleArray(long[] a) {
		int length = a.length;
		double[] toReturn = new double[length];
		for (int i = 0; i < length; i++) {
			toReturn[i] = a[i];
		}
		return toReturn;
	}
	
	
	public static void main(String[] args) {
		boolean[][] staticA = {{true, true},{true,true}};
	    boolean[][] b = copy2D(staticA);
	    
	    staticA[0][0] = false;
		System.out.println(ArrayTools.print2D(b, false));
		
	}
	
}
