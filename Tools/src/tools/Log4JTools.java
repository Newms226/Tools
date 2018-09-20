package tools;

import java.util.Arrays;
import java.util.Collection;

import org.apache.logging.log4j.Logger;

public class Log4JTools {
	
	private static final int VERY_LARGE_ARRAY = Integer.m
	
	private Log4JTools() {}
	
	/**
	 * Performs the same functions as {@link Objects#requireNonNull()} but logs
	 * the exception thrown.
	 * 
	 * @param t the generic to test.
	 * @param l the logger to log to.
	 * @param otherVar the variables to test
	 * 
	 * @return the generic <i>only if the test is passed<i>
	 * 
	 * @throws NullPointerException if the object is null.
	 */
	@SafeVarargs
	public static <T> T assertNonNull(T t, Logger l, T...otherVar) 
			throws NullPointerException 
	{
		if (t == null) {
			throw l.throwing(new NullPointerException());
		}
		
		if (otherVar.length > )
		
		// else
		return l.traceExit("Non-Null test passed: ", t);
	}
	
	public static <T> T assertNonNullParrallel(Collection<T> t)
}
