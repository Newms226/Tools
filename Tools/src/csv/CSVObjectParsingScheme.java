package csv;

import java.io.Serializable;
import java.util.function.Consumer;
import java.util.function.Function;

//@FunctionalInterface
public interface CSVObjectParsingScheme<T extends CSVObject> extends Serializable {
	/**
	 * Strategy for parsing a SINGLE CSVObject into the object.
	 * 
	 * Should ALWAYS return null or throw a {@link CSVParseException} if method failed to read.
	 * @see CSVObjectParser#parse(String)
	 * @param str
	 * @return
	 */
	T from(String str) throws CSVParseException;

}
