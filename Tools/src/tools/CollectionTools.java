package tools;

import java.util.Collection;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class CollectionTools {
	private static StringBuffer buffer;

	private CollectionTools() {}

	public static <T> String collectionPrinter(Collection<T> collection) {
		return collectionPrinter(collection, false);
	}
	
	public static <T> String collectionPrinter(Collection<T> collection, boolean numbered) {
		buffer = new StringBuffer();
		if (numbered) {	
			int i = 0;
			for (T t: collection) {
				i++;
				buffer.append(i + ": " + t.toString() + "\n");
			}
		} else {
			collection.parallelStream().forEach(c -> buffer.append(c + "\n"));
		}
		return buffer.toString();
	}
	
	public static <T> String collectionPrinter(String header, Collection<T> collection, boolean numbered) {
		buffer = new StringBuffer(header);
		if (numbered) {	
			int i = 0;
			for (T t: collection) {
				i++;
				buffer.append(i + ": " + t.toString() + "\n");
			}
		} else {
			collection.parallelStream().forEach(c -> buffer.append(c + "\n"));
		}
		return buffer.toString();
	}
	
	public static <T> Collection<T> findAllInParallel(
			Collection<T> source,
			Predicate<T> tester)
	{
		return source.parallelStream()
			.filter(tester)
			.collect(Collectors.toList());
	}
	
	public static <T> Collection<T> findAll(
			Collection<T> source,
			Predicate<T> tester)
	{
		return source.stream()
			.filter(tester)
			.collect(Collectors.toList());
	}
	
	public static <T> T findFirst(
			Collection<T> source,
			Predicate<T> tester)
	{
		for (T t: source) {
			if (tester.test(t)) return t;
		}
		// not found, return null
		return null;
 	}
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

}
