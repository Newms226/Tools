package csv;

import java.util.Collection;

public interface CSVCollection<T extends CSVObject> extends Collection<T> {
	boolean add(String CSVString, CSVObjectParser<T> objectParser);
	
	boolean add(T[] values);
	
	CSVObjectParser<T> getObjectParser();
	
	
}
