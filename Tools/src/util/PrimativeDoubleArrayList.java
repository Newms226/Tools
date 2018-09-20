package util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class PrimativeDoubleArrayList {
	public static final int MAXIMUM_CAPACITY = Integer.MAX_VALUE - 8,
			                MINIMUM_CAPACITY = 1;
	
	private static final boolean DEBUGGING = true;
	
	private static final int DEFAULT_INITIAL_CAPACITY = 10,
			                 PRINT_ON_SEPERATE_LINES_CUTOFF = 10;
	private static final double SHRINK_AFTER_PERCENT = .3,
			                    SHRINK_TO_PERCENT = .6,
			                    GROW_BY_PERCENT = 1.5;
	
	private double[] array;
	private int filled, capacity;
	private int shrinkAfterSize;

	public PrimativeDoubleArrayList() {
		array = new double[DEFAULT_INITIAL_CAPACITY];
		capacity = DEFAULT_INITIAL_CAPACITY;
		shrinkAfterSize = generateShrinkAfterSize(DEFAULT_INITIAL_CAPACITY);
	}
	
	public PrimativeDoubleArrayList(int initalCapacity) throws IllegalArgumentException {
		if (MINIMUM_CAPACITY >= initalCapacity || initalCapacity >= MAXIMUM_CAPACITY) {
			throw new IllegalArgumentException("Invalid initial capacity. Range: "
					+ "(" + MINIMUM_CAPACITY + " - " + MAXIMUM_CAPACITY + ")");
		}
		
		array = new double[initalCapacity];
		capacity = initalCapacity;
		shrinkAfterSize = generateShrinkAfterSize(initalCapacity);
	}
	
	public PrimativeDoubleArrayList(PrimativeDoubleArrayList toCopy) {
		this(toCopy.array);
	}
	
	public PrimativeDoubleArrayList(double[] input) {
		ensureCapacity(input.length);
		addAll(input);
	}
	
	public double get(int i) throws ArrayIndexOutOfBoundsException {
		return array[i];
	}
	
	public double add(double toAdd) throws OutOfMemoryError {
		if (isFull()) {
			grow();
		}
		
		array[filled++] = toAdd;
		return toAdd;
	}
	
	public void addAll(double[] array) {
		for (int i = 0; i < array.length; i++) {
			add(array[i]);
		}
	}
	
	public double remove(int i) throws ArrayIndexOutOfBoundsException{
		rangeCheck(i);
		
		double toReturn = array[i];
		System.arraycopy(array, i + 1, array, i, filled - i - 1);
		
		filled--;
		
		if (isReadyToShrink()) {
			shrink();
		}
		
		return toReturn;
	}
	
	public void trimToCapacity() {
		double[] trimmed = new double[filled];
		
		System.arraycopy(array, 0, trimmed, 0, filled);
		capacity = filled;
		
		array = trimmed;
	}
	
	public boolean ensureCapacity(int capacity) {
		if (capacity <= filled) return true;
		if (capacity == MAXIMUM_CAPACITY) return false;
		
		grow(capacity);
		return true;
	}
	
	public double[] toArray() {
		return array;
	}
	
	public double[] toArrayCopy() {
		return Arrays.copyOfRange(array, 0, filled);
	}
	
	/******************************************************************************
	 *                                                                            *
	 *                              Sub-List Methods                              *
	 *                                                                            *
	 ******************************************************************************/
	
	public double[] subArray(int fromIndex, int toIndexEXCLUSIVE) {
		int size = toIndexEXCLUSIVE - fromIndex;
		if (size < 0) {
			throw new NegativeArraySizeException(fromIndex + " to " + toIndexEXCLUSIVE
					+ " is not a valid range. ");
		} else if (size == 0) {
			throw new IllegalArgumentException("Cannot create an array of size 0");
		}
		
		double[] toReturn = new double[size];
		for (int newIndex = 0; newIndex < size; newIndex++, fromIndex++) {
			toReturn[newIndex] = array[fromIndex];
		}
		return toReturn;
	}
	
	public List<Double> asList(){
		List<Double> toReturn = new ArrayList<>(filled);
		
		for (int i = 0; i < filled; i++) {
			toReturn.add(new Double(array[i]));
		}
		
		return toReturn;
	}
	
	public List<Double> asList(int ensuredCapacity) {
		if (ensuredCapacity <= filled && ensuredCapacity <= MAXIMUM_CAPACITY) {
			ensuredCapacity = filled;
		}
		
		List<Double> toReturn = new ArrayList<>(filled);
		
		for (int i = 0; i < ensuredCapacity; i++) {
				toReturn.add(new Double(array[i]));
		}
		
		return toReturn;
	}
	
	public double[] getAsSortedArray() {
		double[] copy = new double[filled];
		
		System.arraycopy(array, 0, copy, 0, filled);
		Arrays.sort(copy);
		
		return copy;
	}
	
	/******************************************************************************
	 *                                                                            *
	 *                           Informational Methods                            *
	 *                                                                            *
	 ******************************************************************************/
	
	public int size() {
		return filled;
	}
	
	public boolean isEmpty() {
		return filled == 0;
	}
	
	public boolean isFull() {
		return filled == capacity;
	}
	
	public boolean isSorted() {
		int stopAt = filled - 1;
		
		for (int i = 0; i < stopAt; i++) {
			if (array[i] > array[i + 1]) return false;
		}
		return true;
	}
	
	/******************************************************************************
	 *                                                                            *
	 *                               Search methods                               *
	 *                                                                            *
	 ******************************************************************************/
	
	public boolean contains(double toLookFor) {
		return internalNextIndexOf(0, toLookFor) != -1;
	}
	
	public int indexOf(double toLookFor) {
		return internalNextIndexOf(0, toLookFor);
	}
	
	public int nextIndexOf(int indexAfterEXCLUSIVE, double toLookFor) {
		rangeCheck(++indexAfterEXCLUSIVE);
		
		return internalNextIndexOf(indexAfterEXCLUSIVE, toLookFor);
	}
	
	public int findAfter(int indexAfterINCLUSIVE, double toLookFor) {
		rangeCheck(indexAfterINCLUSIVE);
		
		return internalNextIndexOf(indexAfterINCLUSIVE, toLookFor);
	}
	
	public int lastIndexOf(double toLookFor) {
		for (int i = filled - 1; i >= 0; i--) {
			if (array[i] == toLookFor) return i;
		}
		
		// else
		return -1;
	}
	
	private int internalNextIndexOf(int indexAfterINCLUSIVE, double toLookFor) {
		for (int i = indexAfterINCLUSIVE; i < filled; i++) {
			if (array[i] == toLookFor) return i;
		}
		
		return -1;
	}
	
	/******************************************************************************
	 *                                                                            *
	 *                               Sub List Methods                             *
	 *                                                                            *
	 ******************************************************************************/
	
	public List<Double> getGreaterThanOrEqual(double x) {
		List<Double> toReturn = new ArrayList<>();
		
		double[] copy = getAsSortedArray();
		
		int i = 0;
		while (i < filled && copy[i] > x) i++;
		
		if (i == filled) {
			return Collections.emptyList();
		}
		
		// else
		for ( ; i < filled; i++) {
			toReturn.add(copy[i]);
		}
		return toReturn;
	}
	
	public List<Double> getLessThanOrEqual(double x) {
		List<Double> toReturn = new ArrayList<>();
		
		double[] copy = getAsSortedArray();
		
		int i = 0;
		while (i < filled && copy[i] < x) i++;
		
		if (i == filled) {
			return Collections.emptyList();
		}
		
		// else
		for ( ; i < filled; i++) {
			toReturn.add(copy[i]);
		}
		return toReturn;
	}
	
	/******************************************************************************
	 *                                                                            *
	 *                             Override Methods                               *
	 *                                                                            *
	 ******************************************************************************/
	
	/**
	 * Note that this method determines the equality of the object by first comparing
	 * the fill count of the different objects and then tests and then sequentially
	 * testing every item in the list. Thus, two lists which contain the exact same 
	 * objects, yet have a different ordering, will still return false.
	 * <p>
	 * If it is desired to test the equality of two <code>DoubleArrayLists</code>
	 * code should first sort both objects and then run this method. 
	 * 
	 * @param o the object to test against
	 * @return <code> boolean </code> as defined by {@link Object#equals(Object)}
	 */
	@Override
	public boolean equals(Object o) {
		if (o == null) return false;
		if (o == this) return false;
		
		if (!o.getClass().equals(this.getClass())) return false;
		
		PrimativeDoubleArrayList that = (PrimativeDoubleArrayList) o;
		
		if (filled != that.filled) return false;
		for (int i = 0; i < filled; i++) {
			if (array[i] != that.array[i]) return false;
		}
		
		return true;
	}
	
	
	@Override
	public String toString() {
		StringBuffer buffer = new StringBuffer("[");
		
		boolean seperateLines = filled > PRINT_ON_SEPERATE_LINES_CUTOFF;
		
		int stopAfter = filled - 1;
		for (int i = 0; i < stopAfter; i++) {
			buffer.append(array[i] + "," + (seperateLines ? "\n" : " "));
		}
		buffer.append(array[stopAfter]);
		
		buffer.append("]");
		return buffer.toString();
	}
	
	/******************************************************************************
	 *                                                                            *
	 *                              Private Methods                               *
	 *                                                                            *
	 ******************************************************************************/
	
	private int generateShrinkAfterSize(int capacity) {
		return (int) Math.round(capacity * SHRINK_AFTER_PERCENT);
	}
	
	
	private void rangeCheck(int index) throws ArrayIndexOutOfBoundsException {
		if (isEmpty()) {
			throw new NullPointerException("EMPTY OBJECT: " + i + " is not a valid index");
		}
		if (0 > index || index >= filled) {
			throw new ArrayIndexOutOfBoundsException(
					"INVALID RANGE: " + index + " is not within the range."
							+ " 0 <= x < " + filled);
		}
	}
	
	private boolean isReadyToShrink() {
		if (capacity == MINIMUM_CAPACITY) return false;
		return filled >= shrinkAfterSize;
	}
	
	private void grow() throws OutOfMemoryError {
		if (DEBUGGING) {
			System.out.println("CALLED GROW > fill: " + filled + " capacity: " + capacity);
		}
		
		if (capacity == MAXIMUM_CAPACITY) {
			throw new OutOfMemoryError("CANNOT GROW PAST MAX CAPACITY:"
					+ " Already at max capacity");
		}
		
		// else:
		int newSize = (int) Math.round(capacity * GROW_BY_PERCENT);
		if (newSize > MAXIMUM_CAPACITY) {
			newSize = MAXIMUM_CAPACITY;
		}
		
		grow(newSize);
	}
	
	private void grow(int newCapacity) {
		double[] grown = new double[newCapacity];
		System.arraycopy(array, 0, grown, 0, filled);
		array = grown;
		capacity = newCapacity;
		
		shrinkAfterSize = generateShrinkAfterSize(capacity);
	}
	
	private void shrink() {
		if (DEBUGGING) {
			System.out.println("CALLED SHRINK > fill: " + filled + " capacity: " + capacity);
		}
		
		capacity = (int) (capacity * SHRINK_TO_PERCENT) + 1;
		
		double[] shrunk = new double[capacity];
		System.arraycopy(array, 0, shrunk, 0, filled);
		array = shrunk;
		
		shrinkAfterSize = generateShrinkAfterSize(DEFAULT_INITIAL_CAPACITY);
	}
	
	/******************************************************************************
	 *                                                                            *
	 *                            Static Testing Methods                          *
	 *                                                                            *
	 ******************************************************************************/
	
	private static void toStringTest() {
		PrimativeDoubleArrayList test = new PrimativeDoubleArrayList();
		for (int i = 0; i < 30; i++) {
			test.add(i);
		}
		System.out.println(test);
	}
	
	@SuppressWarnings("unused")
	private static void addThenGrowTest() {
		PrimativeDoubleArrayList test = new PrimativeDoubleArrayList();
		for (int i = 0; i < 30; i++) {
			test.add(i);
		}
	}
	
	private static void containsTest() {
		PrimativeDoubleArrayList test = new PrimativeDoubleArrayList();
		for (int i = 0; i < 30; i++) {
			test.add(i);
		}
		
		System.out.println("30 (false): " + test.contains(30));
		System.out.println("1 (true): " + test.contains(1));
	}
	
	private static void indexOfTest() {
		PrimativeDoubleArrayList test = new PrimativeDoubleArrayList();
		for (int i = 0; i < 30; i++) {
			test.add(i);
		}
		
		System.out.println("30 (-1): " + test.indexOf(30));
		System.out.println("1 (1): " + test.indexOf(1));
		System.out.println("NEXT_INDEX: 1 (-1): " + test.nextIndexOf(1, 1
				));
		test.add(1);
		System.out.println("NEXT_INDEX: 1 (30): " + test.nextIndexOf(1, 1));
	}
	
	public static void main(String[] args) {
		toStringTest();
	}

}
