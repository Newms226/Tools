package csv;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.LineNumberReader;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Scanner;
import java.util.Vector;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import org.apache.logging.log4j.Logger;

import tools.Log4JTools;

public class CSVUtil {
	
	private CSVUtil() {}
	
	public static <T extends CSVObject> List<T> readSequentialCSV(File file, 
			CSVObjectParser<T> objectReader,
			CSVFileReader fileReader,
			Logger log) 
					 throws FileNotFoundException,  // if file is invalid
					        NoSuchElementException, // if end of file is prematurely reached
					        IllegalStateException,  // if the scanner is closed too soon
					        NullPointerException    // if any parameters are null
	{
		log.traceEntry("readCollection({}, {}, {}, {})", file, objectReader, fileReader, log);
		
		Objects.requireNonNull(log);
		Log4JTools.assertNonNull(file, log);
		Log4JTools.assertNonNull(objectReader, log);
		Log4JTools.assertNonNull(fileReader, log);
		log.trace("Null tests passed");
		
		ArrayList<T> list = new ArrayList<>();
		try (Scanner scan = new Scanner(file)) {
			// skip till file line containing the selection
			int i = 0;
			for (; i < fileReader.getLocationOfCollection(); i++) {
				scan.nextLine(); // Can throw NoSuchElementException
			}
			log.trace("Read " + i + " lines.");
			
			// Split line into values
			String[] values = seperate(scan.nextLine(), fileReader, log);
			
			// Atempt to read in the values
			for (String str: values) {
				T toAdd = objectReader.parse(str);
				if (toAdd == null) {
					log.warn("Failed to parse: " + str);
				} else if (list.add(toAdd)) {
					log.trace("Parsed: {}  <  From: {}", toAdd, str);
				} else {
					log.warn("Failed to parse: " + str);
				}
			}
		} 
		return list;
	}
	
	// ASSUMES NO NULL INPUTS!
	private static String[] seperate(String line, CSVFileReader fileReader, Logger log) {
		String[] values = line
				.substring(fileReader.getStartIndex(),
						   fileReader.getEndIndex())
				.split(",");
		log.trace("Found " + values.length + " values. Index: " 
				+ fileReader.getStartIndex() + " - " + fileReader.getEndIndex()
					+ "(" + (line.length() - fileReader.getEndIndex()) + ")");
		return log.traceExit("", values);
	}
	
	public static <T extends CSVObject> Collection<T> readAsynch(File file, 
			CSVObjectParser<T> objectReader,
			CSVFileReader fileReader,
			Logger log) 
					 throws NoSuchElementException, // if end of file is prematurely reached
					        IllegalStateException,   // if the scanner is closed too soon
                             IOException, 
                             InterruptedException, ExecutionException
	{
		log.traceEntry("readAsynch({}, {}, {}, {})", file, objectReader, fileReader, log);
		
		Objects.requireNonNull(log);
		Log4JTools.assertNonNull(file, log);
		Log4JTools.assertNonNull(objectReader, log);
		Log4JTools.assertNonNull(fileReader, log);
		log.trace("Null tests passed");
		Collection<T> toReturn;
		
		
		ExecutorService ex = Executors.newCachedThreadPool();
		List<Future<T>> futures = ex.invokeAll(
				generateCallables(file, objectReader, fileReader, log)
			);
		
		
		
		toReturn = new ArrayList<>(futures.size());
		for (Future<T> future: futures) {
			toReturn.add(future.get());
		}
		return toReturn;
	}
	
	static <T extends CSVObject> List<AyschUnitServicer<T>> generateCallables(File file, 
			CSVObjectParser<T> objectReader,
			CSVFileReader fileReader,
			Logger log) 
	{
		
		
		return null;
	}
	
	private static class AyschUnitServicer<T extends CSVObject> implements Callable<T>{
		private final String str;
		private final CSVObjectParser<T> objectReader;
		private final Logger log;
		
		// Assumes that this instance is only created without the potential for a null pointer
		// but still validates the str.
		public AyschUnitServicer(String str, CSVObjectParser<T> objectReader, Logger log) {
			this.log = log;
			this.str = str; // TODO: String tests
			this.objectReader = objectReader;
			log.traceExit();
		}
		
		@Override
		public T call() throws Exception {
			return log.traceExit(objectReader.parse(str));
		}
		
		public String toString() {
			return str;
		}
		
	}
	
	public static <T extends CSVObject> Collection<T> readParallelLineSV(File file, 
			CSVObjectParser<T> objectReader,
			CSVFileReader fileReader,
			Logger log) 
					 throws NoSuchElementException, // if end of file is prematurely reached
					        IllegalStateException,   // if the scanner is closed too soon
                             IOException
	{
		log.traceEntry("readParallelLineSV({}, {}, {}, {})", file, objectReader, fileReader, log);
		
		Objects.requireNonNull(log);
		Log4JTools.assertNonNull(file, log);
		Log4JTools.assertNonNull(objectReader, log);
		Log4JTools.assertNonNull(fileReader, log);
		log.trace("Null tests passed");
		
		List<T> values = new ArrayList<>();
		try (LineNumberReader reader = new LineNumberReader(new FileReader(file))) {
			// skip till file line containing the selection
			reader.setLineNumber(fileReader.getLocationOfCollection());
			
			values = reader.lines()
				.parallel()
				.map(objectReader)
				.collect(Collectors.toList());
			
			
			log.trace("Found " + values.size() + " values by analyzing lines in parallel");

		}
		
		return values;
	}
}
