package csv;
 
import java.util.SortedSet;
import java.util.function.Function;

public interface CSVObjectParser<T extends CSVObject> extends Function<String, T> {
	
	SortedSet<CSVObjectParsingScheme<T>> getParsingSchemes();
	
	default T parse(String str) {
		T toReturn;
		
		// Cycle through possible parsing schemes and look for a valid object.
		for(CSVObjectParsingScheme<T> p: getParsingSchemes()) {
			try {
				toReturn = p.from(str);
				if (toReturn == null) continue;
			} catch (CSVParseException e) {
				continue;
			}
			
			// Passes test, Valid object 
			return toReturn;
		}
		
		// All tests have failed, return null
		return null;
	}

	@Override
	default T apply(String t) {
		return parse(t);
	}
}
