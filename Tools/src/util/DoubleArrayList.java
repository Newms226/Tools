package util;

import java.util.ArrayList;
import java.util.List;

public class DoubleArrayList {
	private static final boolean DEBUGGING = true;
	
	private static final int DEFAULT_INITIAL_CAPACITY = 10,
			                 MAXIMUM_CAPACITY = Integer.MAX_VALUE - 8,
			                 MINIMUM_CAPACITY = 1;
	private static final double SHRINK_AFTER_PERCENT = .3,
			                    SHRINK_TO_PERCENT = .6,
			                    GROW_BY_PERCENT = 1.5;
	
	private double[] array;
	private int filled, capacity;
	private int shrinkAfterSize;

	public DoubleArrayList() {
		array = new double[DEFAULT_INITIAL_CAPACITY];
		capacity = DEFAULT_INITIAL_CAPACITY;
		shrinkAfterSize = (int) (DEFAULT_INITIAL_CAPACITY * SHRINK_AFTER_PERCENT + 1);
	}
	
	public DoubleArrayList(int initalCapacity) throws IllegalArgumentException {
		if (MINIMUM_CAPACITY >= initalCapacity || initalCapacity >= MAXIMUM_CAPACITY) {
			throw new IllegalArgumentException("Invalid initial capacity. Range: "
					+ "(" + MINIMUM_CAPACITY + " - " + MAXIMUM_CAPACITY + ")");
		}
		
		array = new double[initalCapacity];
		capacity = initalCapacity;
		shrinkAfterSize = generateShrinkAfterSize(initalCapacity);
	}
	
	private int generateShrinkAfterSize(int capacity) {
		return (int) Math.round(capacity * SHRINK_AFTER_PERCENT);
	}
	
	public double get(int i) throws ArrayIndexOutOfBoundsException {
		return array[i];
	}
	
	public void add(double toAdd) throws OutOfMemoryError {
		if (isFull()) {
			grow();
		}
		
		array[filled++] = toAdd;
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
	
	private int internalNextIndexOf(int indexAfterINCLUSIVE, double toLookFor) {
		for (int i = indexAfterINCLUSIVE; i < filled; i++) {
			if (array[i] == toLookFor) return i;
		}
		
		return -1;
	}
	
	public int size() {
		return filled;
	}
	
	public void trimToCapacity() {
		double[] trimmed = new double[filled];
		
		System.arraycopy(array, 0, trimmed, 0, filled);
		capacity = filled;
		
		array = trimmed;
	}
	
//	public void ensureCapacityAfterAdd(int futureCap) {
//		if ()
//	}
	
	public List<Double> asList(){
		List<Double> toReturn = new ArrayList<>(filled);
		
		for (int i = 0; i < filled; i++) {
			toReturn.add(new Double(array[i]));
		}
		
		return toReturn;
	}
	
	public boolean isEmpty() {
		return filled == 0;
	}
	
	public boolean isFull() {
		return filled == capacity;
	}
	
	private void rangeCheck(int index) throws ArrayIndexOutOfBoundsException {
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
		
		double[] grown = new double[newSize];
		System.arraycopy(array, 0, grown, 0, filled);
		array = grown;
		capacity = newSize;
		
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
	
	@SuppressWarnings("unused")
	private static void addThenGrowTest() {
		DoubleArrayList test = new DoubleArrayList();
		for (int i = 0; i < 30; i++) {
			test.add(i);
		}
	}
	
	private static void containsTest() {
		DoubleArrayList test = new DoubleArrayList();
		for (int i = 0; i < 30; i++) {
			test.add(i);
		}
		
		System.out.println("30 (false): " + test.contains(30));
		System.out.println("1 (true): " + test.contains(1));
	}
	
	private static void indexOfTest() {
		DoubleArrayList test = new DoubleArrayList();
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
		indexOfTest();
	}

}
