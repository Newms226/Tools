package tools;

import org.apache.logging.log4j.Logger;

public class Log4JTools {
	private Log4JTools() {}
	
	public static void assertNonNull(Object o, Logger l) {
		if (o == null) {
			throw l.throwing(new NullPointerException());
		}
	}
}
