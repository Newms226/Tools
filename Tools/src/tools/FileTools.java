package tools;
/* FileTools
 * Programmer: Michael Newman
 * Date: 3.20.18
 * 
 * Description: FileTools is designed to enable quick and easy IO. The class
 * 		protects against many forms of an imperfect user, as noted by the
 * 		various methods.
 * 		The prime advantage of this class is to enable a default IO location
 * 		which can then be easily accessed by the "~" short cut (see defaultMessage)
 * 
 * IO: Programs responsibility is to handle IO. Capable of reading and writing from files.
 * 
 * Assumptions and Limitations
 *   - In general, the methods require the use a default destination in order to facilitate a
 *     default destination for all IO. If the shortcut is not desired, class is to be avoided
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Time;
import java.text.SimpleDateFormat;
import java.util.Scanner;
import java.util.regex.PatternSyntaxException;

public class FileTools {
	public static final String DEFAULT_IO_DIRECTORY = "/Users/Michael/Documents/IO/", // default IO location
            DEFAULT_DESTINATION_MESSAGE = "\n'~' shortcut."
  			+ "\nThis is designed to allow you to set a default destination"
				+ "\nfor all input and output. When promped to enter a locaton"
				+ "\nif you preceed your location with '~' it will automatically"
				+ "\nadd the text you enter here to the front of the file name."
				+ "\nBy default the location is set to a folder on the creators"
				+ "\ncomputer: " + DEFAULT_IO_DIRECTORY
				+ "\nWhatever you enter here will replace the default, so ensure"
				+ "\nproper form for your operating system or expect "
				+ "\nerrors down the line when using the shortcut!"
				+ "\nYou must both start and end with '/' for unix systems", // message explaining '~' shortcut
			LINE_BREAK = "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~\n"; // Default line break in files
	private static SimpleDateFormat USDate = new SimpleDateFormat("MM/dd/yyyy  hh:mma"); // date Format
	private static Scanner key = new Scanner(System.in);                                 // keyboard input
	
	private static File directory,   // directory reference 
	                    output;      // output file reference
	private static Time time;        // time reference (used when printing to a file)
	private static boolean valid;    // for user input validation
	private static String location;  // String representation of a location to work on
	
	private FileTools() {} // prevent instance creation
	
	/* method to validate that the abstract String pathway passed into it is both
	 * a valid location and a valid directory
	 * @param: Location is the abstract pathway to test against
	 * @return: true if valid directory, false otherwise
	 */
	public static boolean validateDirectory(String location) {
		directory = new File(location);
		if (!directory.exists() || !directory.isDirectory()) return false;
		return true;
	}
	
	
	public static boolean createDirectory(String location) {
		directory = new File(location);
		if (directory.exists() && directory.isDirectory()) return true;
		if (directory.mkdirs()) return true;
		// else
		return false;
	}
	
	/* A Wrapper method for the {@link File} class which attempts
	 * to create all the necessary parent directories of the abstract directory path 
	 * passed in as a parameter. (See File.mkdirs())
	 * @param: location is the abstract pathname to attempt to create
	 * @return: true if directory creation succeeded, false otherwise
	 */
	public static File getValidDirectoryFile(File location) throws IOException {
		if (location.exists() && location.isDirectory()) return location;
		if (location.mkdirs()) return location;
		
		// else
		throw new IOException("FAILED TO CREATE DIRECTORY AT: " + location);
	} 
	
	/* generateDirectoryString handles all of the necessary overhead to generate an 
	 * abstract directory file path as a String.
	 * @param: N/A
	 * @return: String representation of the directory path
	 */
	public static String generateDirectoryString()  {
		valid = false;
		while (!valid) {
			System.out.println("\nPlease enter a valid location (Proper Syntax required)");
			location = key.nextLine();
			directory = new File(location);
			if (!(directory.exists() && directory.isDirectory())) {
				// directory invalid
				if(DriverTools.validate("Invalid Directory. Would you like to attempt creation?")) {
					// choose yes
					if (!directory.mkdirs()) { 
						// directory creation failed. Continue on loop
						System.out.println("Could not create. Please try again");
						continue;
					} // end if to create directory
				} else continue; // choose not to attempt creation
			} // validate directory if
			System.out.println("\nYou entered:\n" + location
					+ "\nIs this correct?");
			if (DriverTools.validate()) valid = true; // choose yes (else: loop restarts)
		} // end while
		return location;
	} // end setDefaultIOLocation
	
	/* overloaded method that allows the programmer to print an intro message before the 
	 * method generates a directory String. General use is to specify what the directory
	 * will represent.
	 * @param: intro is the message to be printed
	 * @return: String representing the directory path
	 */
	public static String generateDirectoryString(String intro)  {
		System.out.println(intro);
		return generateDirectoryString();
	} // end setDefaultIOLocation
	
	public static File generateDirectoryFile() {
		return new File(generateDirectoryString());
	}
	
	public static File generateDirectoryFile(String intro) {
		return new File(generateDirectoryString(intro));
	}
	
	/* normalizeLocationText is designed to ensure that the file location
	 * passed into it is of valid form. Note that if you type ~ before
	 * a location, it will automatically suffix the defaultLocation string
	 * to the beginning of the location.
	 * @param: defaultLocation is the default IO Location to be suffixed
	 *         str is the String to be normalized
	 * @returns: valid location string
	 * @throws: PatternSyntaxException if the string passed into it does not
	 * 		match any of its regrexes.
	 */
	private static String normalizeTextLocation(String defaultLocation, String str) 
			throws PatternSyntaxException {
		directory = new File(defaultLocation);
		if (!(directory.exists() && directory.isDirectory())) {
			defaultLocation = generateDirectoryString("Invalid default IO destination");
		}
		str = validateTextFileExtension(str); // throws PatternSyntaxException
		// Test if shortcut is used
		if (str.matches("\\~[0-9A-Za-z/]+\\.txt$")) {
			str = str.substring(1);
			return defaultLocation + str;
		}
		// else invalid regrex:
		throw new PatternSyntaxException("Cannot match " + str + " to any valid regrex", str, -1);
	} // end returnValidLocationText
	
	/* validateTextFileExtension ensures that the string passed as a parameter is suffixed
	 * with the valid '.txt' file extension.
	 * @param: str is the string to validate / append
	 * @return: a valid text path String
	 * @throws: PatternSyntaxException if string cannot be matched to any of the regrexes.
	 */
	public static String validateTextFileExtension(String str) {
		// Test if in valid, normal form
		if (str.matches("[\\~]*[0-9A-Za-z/\\.]+\\.txt$")) {
			return str;
		}
		// test if missing .txt at the end
		if (str.matches("[\\~]*[0-9A-Za-z/]+$")) {
			return str + ".txt";
		}
		throw new PatternSyntaxException("Cannot match " + str + " to any valid regrex", str, -1);
	}
	
	public static String generateTextLocation(String intro, String defaultLocation) {
		System.out.println(intro);
		return generateTextLocation(defaultLocation);
	}
	
	/* generateTextLocation generates a valid abstract pathname for a .txt file
	 * @param: defaultLocation is the default IO location to enable the "~" shortcut
	 * @return: the abstract .txt file location as path way
	 */
	public static String generateTextLocation(String defaultLocation) {
		// ensure default location passed in is valid
		directory = new File(defaultLocation);
		if (!(directory.exists() && directory.isDirectory())) {
			defaultLocation = generateDirectoryString("Invalid default destination. Resolving...");
		}
		
		// generate text location
		boolean valid = false;
		while (!valid) {
			try {
				System.out.println("Please enter a valid location for a .txt file:");
				location = key.nextLine();
				location = normalizeTextLocation(defaultLocation, location); // throws PatternSyntaxException
				valid = true;
			} catch (PatternSyntaxException e) {
				System.out.println(e.getMessage());
			}
		}
		return location;
	}
	
	public static File generateTextFileObject(String defaultLocation) {
		return new File(generateTextLocation(defaultLocation));
	}
	
	/* generatePrintWriter generates a valid printWriter object with the '~' shortcut enabled.
	 * @param: defaultLocaton is the proper location for IO depending on what
	 *         you are trying to do. By default, you should pass in 
	 *         FileTools.defaultDestiation
	 * @return: valid PrintWriter object, enabling a shortcut for
	 *          a default location. Protects against an imperfect user 
	 *          by ensuring all locations are valid.
	 */
	public static PrintWriter generatePrintWriter(String defaultLocation) {
		// validation of defaultLocation
		directory = new File(defaultLocation);
		if (!(directory.exists() && directory.isDirectory())) {
			// if invalid directory, attempt to resolve
			if (!directory.mkdirs()) {
				// if directory cannot be created, ask user to supply new location
				defaultLocation = generateDirectoryString("Invalid default IO destination");
			}
		}
		
		// PrintWriter generation
		PrintWriter write = null;
		valid = false;
		while (!valid) {
			System.out.println("\nWhere would you like the file to go?");
			
			output = new File(generateTextLocation(defaultLocation));
			if (output.exists()) {
				if (!DriverTools.validate("That file already exists."
						+ "\nWould you like to write over that file?")) continue; // choose no
			} // outer if
			try {
				write = new PrintWriter(output);
				valid = true;
			} catch (FileNotFoundException e) {
				System.out.println("Directory/File non-exsistant");
			} // end try-catch
		} // end while loop for validate
		System.out.println("Output selected as"
				+ "\n" + output);
		return write;
	} // end generateOutputFile
	
	/* getPrintWriter allows the programmer to exclude the throws clause from their program
	 * by validating the directory and the file name.
	 * @param: parentDirectory is the directory for the file
	 *         fileName is the name of the file without any path information
	 * @return: validated PrintWriterObject
	 * @throws: Error if the validation of the directory fails (should not ever be encountered)
	 */
	public static PrintWriter getPrintWriter(String parentDirectory, String fileName) {
		directory = new File(parentDirectory);
		if (!(directory.exists() && directory.isDirectory())) {
			System.out.println("Passed invalid location to getPrintWriter. Attempting to resolve...");
			if (!directory.mkdirs()) {
				parentDirectory = generateDirectoryString("Could not resolve.");
			}
		}
		// ensure that fileName contains .txt
		fileName = validateTextFileExtension(fileName);
		
		try {
			return new PrintWriter(parentDirectory + "/" + fileName);
		} catch (IOException e) {
			// should not be thrown
			throw new Error("Unexcpeted Error");
		}
	}
	
	/* writeToFile enables quick and easy writing to file, while handing all exceptions thrown
	 * by PrintWriter & ensuring that no null parameters were passed in
	 * @param: write is the PrintWriter object which will perform the writing
	 *         header is the text to print before the file text
	 *         toWrite is the file text to write
	 * @return: N/A
	 */
	public static void writeToFile(PrintWriter write, String header, Object toWrite) {
		// validate no null parameters
		if (write == null) {
			System.out.println("\nWrite was not initialized");
			write = generatePrintWriter(generateDirectoryString("Please enter a default IO location"));
		}
		if (header == null || header == "") {
			System.out.println("Please enter a header:");
			header = key.nextLine();
		}
		if (toWrite == null) {
			throw new Error("Cannot write no text. toWrite is null");
		}
		
		// write to file
		write.print(header + "\n" + USDate.format(new Time(System.currentTimeMillis()))
				+ "\n~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
				+ "\n" + toWrite);
		System.out.println("\nPrinting complete. PrintWriter closed.");
		write.close();
	} // end writeToFile
	
	/* overloaded method which removes the need for a header on the file
	 * @param: write is the PrintWriter object which will perform the writing
	 *         toWrite is the file text to write
	 * @return: N/A
	 */
	public static void writeToFile(PrintWriter write, Object toWrite, boolean timed) {
		time = new Time(System.currentTimeMillis());
		
		// validate no null parameters
		if (write == null) {
			System.out.println("\nWrite was not initialized");
			write = generatePrintWriter(generateDirectoryString("Please enter a default IO location"));
		}
		if (toWrite == null) {
			throw new Error("Cannot write no text. toWrite is null");
		}
		
		// write to file
		if (timed) write.print(USDate.format(time) + "\n\n"); 
		write.print(toWrite);
		System.out.println("\nPrinting complete. PrintWriter closed.");
		write.close();
	} // end writeToFile
	
	public static void writeToFile(PrintWriter write, Object toWrite) {
		writeToFile(write, toWrite, true);
	}
	
	/* generateFileWriter handles all the necessary overhead to generate a valid FileWriter
	 * object with the '~' shortcut enabled.
	 * @param: defaultLocation is the location used by the '~' shortcut
	 * @return: valid FileWriter object
	 */
	public static FileWriter generateFileWriter(String defaultLocation) {
		// validate defaultLocation
		directory = new File(defaultLocation);
		if (!(directory.exists() && directory.isDirectory())) {
			// attempt to resolve the directory by creation
			if (!directory.mkdirs()) {
				// if creation fails, have user input a new location
				defaultLocation = generateDirectoryString("Invalid directory passed into getFileWriter. Resolving...");
			}
		}
		
		// generate FileWriter
		location = "";
		FileWriter write = null;
		
		valid = false;
		while (!valid) {
			try {
				System.out.println("\nWhere is the file located?");
				output = new File(generateTextLocation(defaultLocation));
				if (output.isDirectory()) {
					System.out.println("Cannot write to a directory");
					continue;
				}
				write = new FileWriter(output); // throws IOException
				valid = true;
			} catch (Exception e) {
				System.out.println(e.getMessage() + "\nTry Again");
			}
		} // end while loop for validate
		System.out.println("Output selected as"
				+ "\n" + output);
		return write;
	}
	
	/* getFileWriter handles all necessary exceptions in order to create a FileWriter object.
	 * prevents the user from writing to a directory by creating a new File based on the 
	 * current time at method call.
	 * @param: file is the file to append
	 * @return: validated FileWriter object
	 * @throws: Error if the validation of the File object fails
	 */
	public static FileWriter getFileWriter(File file) {
		// prevent from writing to a directory
		if (file.isDirectory()) {
			// if file is directory, resolve by generating a new file
			File newFile = new File(file.toString() + "/" + new Time(System.currentTimeMillis()) + ".txt");
			System.out.println("Passed a directory to getFileWriter: " + file 
					+ "\nResoloved by returning a writer pointed at " + newFile);
			file = newFile;
		}
		
		// return a valid FileWriter object
		try {
			return new FileWriter(file);
		} catch (Exception e) {
			throw new Error(e.getMessage());
		}
	}
	
	/* overloaded method which allows the programmer to pass in a String location instead 
	 * of a file.
	 * @param: location is the String to be converted into a File before method call
	 * @return: valid FileWriter object
	 */
	public static FileWriter getFileWriter(String location) {
		return getFileWriter(new File(location));
	}
	
	/* returns a valid Scanner object pointing at a .txt UTF-8 encoded file, enabling a 
	 * shortcut for a default location. Ensures file exists & contains at least one line 
	 * @param: defaultLocaton is the default IO location which enables the '~' shortcut
	 * @return: Scanner object pointed at the file specified by this method
	 */
	public static Scanner generateInputScanner(String defaultLocation) {
		// validate defaultLocation
		directory = new File(defaultLocation);
		if (!(directory.exists() && directory.isDirectory())) {
			// attempt to resolve the directory by creation
			if (!directory.mkdirs()) {
				// if creation fails, have user input a new location
				defaultLocation = generateDirectoryString("Invalid directory passed into getFileWriter. Resolving...");
			}
		}
		
		Scanner read = null;
		
		valid = false;
		while (!valid) {
			location = generateTextLocation(defaultLocation);
			if (validateFileIsReadableTextFile(new File(location))) {
				read = new Scanner(location);
				valid = true;
			}
		} // end validate while loop
		System.out.println("Input selected as"
				+ "\n" + location);
		return read;
	} // end generateInputFile
	
	public static boolean validateFileIsReadableTextFile(File toTest) {
		if(!toTest.exists()) {
			System.out.println("That file does not exist.");
			return false;
		}
		// validate location is not a directory
		if (toTest.isDirectory()) {
			System.out.println("Cannot read from a directory");
			return false;
		}
		// validate privileges
		if (!toTest.canRead()) {
			System.out.println("You do not have read access to this file");
			return false;
		}
//		try (Scanner read = new Scanner(toTest)) {
//			// ensure input file is not empty
//			if (!read.hasNextLine()) {
//				System.out.println("File is empty");
//				return false;
//			}
//		} catch (Exception e) {
//			System.out.println(e.getMessage());
//			return false;
//		}
		return true;
	}
	
	/**
	 * Ensures that a {@link File} with the Path passed to this method does not already exist. Note
	 * that this implementation is only designed to work with .txt files.
	 * 
	 * @param file a {@link String} containing the path to ensure is unique
	 * 
	 * @return a unique {@link File}
	 */
	public static File getUniqueTxtFile(String location) {
		File file = new File(location);
		if (!(file.exists() && file.isDirectory())) return file;
		
		// else
		String filePath = file.toString().replaceAll(".txt", "");
		
		int i = 1;
		do {
			file = new File(filePath + "_" + i + ".txt");
			i++;
		} while (file.exists());
		
		return file;
	}
	
	public static void main(String[] args) {
		System.out.println(!(false && true));
	}
}
