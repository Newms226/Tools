package tools;

import org.apache.logging.log4j.Logger;

public class Log4JTools {
	private Log4JTools() {}
	
	/**
	 * Performs the same functions as {@link Objects#requireNonNull()} but logs
	 * the exception thrown.
	 * 
	 * @param o the object to test.
	 * @param l the logger to log to.
	 * 
	 * @throws NullPointerException if the object is null.
	 */
	public static void assertNonNull(Object o, Logger l) 
			throws NullPointerException 
	{
		if (o == null) {
			throw l.throwing(new NullPointerException());
		}
	}
}
