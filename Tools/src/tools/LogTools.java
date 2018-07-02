package tools;

import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.logging.Logger;

public class LogTools {

	private LogTools() {}
	
	public static <T, COLLECTION extends Iterable<T>> boolean aggregateTestAndLog(Logger log,
																	            Predicate<T> predicate, 
																	            Supplier<COLLECTION> collectionSupplier, 
																	            Supplier<T> itemSupplier, 
																	            String className,
																	            String methodName)
	{
		log.entering(className, methodName);
		boolean actionPerformed = false;
		for (T t : collectionSupplier.get()) {
			if (predicate.test(t)) {
				actionPerformed = true;
				log.info(methodName + " performed on: " + t);
			}
		}
		return actionPerformed;
	}
}
