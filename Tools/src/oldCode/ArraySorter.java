package oldCode;
/* ArraySorter.Java
 * Programmer: Michael Newman
 * Date: 3.20.18
 * 
 * Description: This class is a collection of sorting methods designed
 * 		to work on arrays of objects.
 * 
 * Assumptions / Limitations
 *    1. Assumed that any array passed into it implements Comparable
 *    2. Does not work on primitives
 *    	* ArrayTools includes a method to parse int and double arrays
 *        into their respective Wrapper classes in order to deal with this
 * References: Algorithms, Edition 4, Segwick & Wayne
 */

import java.util.Arrays;

import edu.princeton.cs.algs4.StdRandom;
import tools.ArrayTools;


public class ArraySorter<T extends Comparable<Object>> extends ArrayTools {
	public final static int CUT_OFF = 30; // cut off to insertion
	
	private static Comparable<Object>[] copy;     // used by merge sort
	
	private ArraySorter() {}
	
	public static Comparable<Object>[] quickSort(Comparable<Object>[] data) {
		// quick sort driver
		Comparable<Object>[] copy = Arrays.copyOf(data, data.length);
		quickSort(copy, 0, data.length - 1);
		return copy;
	} // end method
	
	private static void quickSort(Comparable<Object>[] data, int low, int high) {
		// quick sort helper
		if (low >= high) return;
		if (high - low <= CUT_OFF) insertionSort(data, low, high);
		else {
			int j = partition(data, low, high);
			quickSort(data, low, j-1);
			quickSort(data, j+1, high);
		}
	} // end method
	
	private static int partition(Comparable<Object>[] data, int low, int high) {
		int i = low;
		int j = high+1;
		Comparable<Object> v = data[low];
		Comparable<Object> toSwap;
		
		while (true) {
			while (v.compareTo(data[++i]) > 0) if (i == high) break;
			while (v.compareTo(data[--j]) < 0) if (j == low) break;

			if (i >= j) break;
			toSwap = data[i];
			data[i] = data[j];
			data[j] = toSwap;
		} // end while
		toSwap = data[low];
		data[low] = data[j];
		data[j] = toSwap;
		
		return j;
	} // end method

	public static Comparable<Object>[] mergeSort(Comparable<Object>[] array) {
		// merge sort driver
		Comparable<Object>[] data = Arrays.copyOf(array, array.length);
		copy = Arrays.copyOf(array, array.length);
		mergeSort(data, copy, 0, data.length - 1);
		merge(data, copy, 0, (array.length - 1)/2 , (array.length - 1));
		return data;
	} // end method
	
	public static void mergeSort(Comparable<Object>[] data, Comparable<Object>[] copy, int low, int high) {
		// merge sort helper
		if (low >= high) return;
		int mid = low + (high - low)/2;
		if (high - low <= CUT_OFF) {
			insertionSort(copy, low, high);
			return;
		}
		mergeSort(copy, data, low, mid);
		mergeSort(copy, data, mid + 1, high);
		merge(copy, data, low, mid, high);
	} // end method
	
	private static void merge(Comparable<Object>[] data, Comparable<Object>[] copy, int low, int mid, int high) {
		int i = low;
		int j = mid + 1;
	
		// i = left, j = right
		for (int k = low; k <= high; k++) {
			if (i > mid) data[k] = copy[j++]; // left exausted, take from right
			else if (j > high) data[k] = copy[i++]; // right exausted, take from left
			else if (copy[j].compareTo (copy[i]) < 0) data[k] = copy[j++]; // if right smaller, take right
			else data[k] = copy[i++]; // else left smaller, take left
		} // end for
	} // end method
	
	public static void merge(Comparable<Object>[] data, Comparable<Object>[] copy) {
		// TODO
	}
	
	public static Comparable<Object>[] insertionSort(Comparable<Object>[] data) {
		// insertion sort driver
		data = Arrays.copyOf(data, data.length);
		insertionSort(data, 0, data.length - 1);
		return data;
	}// end method
	
	public static void insertionSort(Comparable<Object>[] data, int low, int high) {
		// insertion sort helper
		Comparable<Object> tempShift;
		int j, i;
		
		for (i = low; i <= high; i++) { // LESS THAN OR EQUAL TO! NOT LESS THAN =)
			j = i;
			while (j > low) {
				if (data[j].compareTo(data[j-1]) > 0) {
					break; // break to outer for
				}
				tempShift = data[j-1];
				data[j-1] = data[j];
				data[j] = tempShift;
				j--;
			} // end inner while
		} // end for
	} // end insertion sort helper
	
	public static void shuffle(Comparable<Object>[] data){
		StdRandom.shuffle(data);
	}
	
//	public static void insertionSort(PerfectArray data) {
//		// TODO
//	}
	
//	public static Comparable findKth(Comparable[] data, int k) {
//		// TODO
//		return 1;
//	}
	
	/* ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 * 																		  |
	 *                               SORTED METHODS							  |
	 * 																		  |
	 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	 */
	
	public static <T extends Comparable<Object>> boolean isSorted(T[] array) {
		for (int i = 0; i < array.length - 1; i++) {
			if (array[i].compareTo(array[i + 1]) > 0 ) {
				return false;
			}
		} // end for
		return true;
	}
	
	public static <T extends Comparable<Object>> boolean isSorted(T[] array, int low, int high) {
		for (int i = low; i < high - 1; i++) {
			if (array[i].compareTo(array[i + 1]) > 0 ) {
				return false;
			}
		} // end for
		return true;
	}

	public static void main(String[] args) {
		 
	}

}
