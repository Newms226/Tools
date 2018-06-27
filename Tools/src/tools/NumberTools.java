package tools;
/*NumberTools.java
 * Programmer: Michael Newman
 * Date: 3/20/18
 * 
 * Description: This class is designed as a static library to work with numbers.
 *              Simplifies input, formats input, and generates input while protecting
 *              against an imperfect user.
 *              
 * I/O: Enables validated user input, No output.
 * 
 * Assumptions & Limitations:
 *  - N/A
 */

import java.text.DecimalFormat;          // used to format numbers
import java.util.InputMismatchException; // thrown when invalid argument is encountered
import java.util.Scanner;                // keyboard input 

public class NumberTools {
	private static DecimalFormat doubleFormat = new DecimalFormat("#,##0.0000"), 
			                        // 10^-4 precision. Grouping of threes.
                                integerFormat = new DecimalFormat("#,##0"),
                                    // adds commas. No loss of precision.
                                percentFormat = new DecimalFormat("#,##0.00%");
	                                // generates percent. 10^-2 precision.
	private static Scanner key = new Scanner(System.in); // keyboard scanner for input
	private static boolean validate;                     // used to validate input
	private static StringBuffer buffer;                  // used to generate text
	
	/* format(double) formats a double by truncating after 4 decimal points and
	 * adds groupings with "," after 3 values. If a different precision is desired
	 * it's recommended that the next method is used.
	 * @param: x is the double to be formated
	 * @return: String representation of the parameter passed in.
	 */
	public static String format(double x) {
		return doubleFormat.format(x);
	}
	
	/* Overloaded method which allows a programmer to specify the precision on which
	 * the formating truncates.
	 * @param: x is the double to format
	 *         precision is the precision on which to truncate after.
	 * @return: returns a String representation of x after truncating to the specified
	 *          precision
	 */
	public static String format(double x, int precision) {
		buffer = new StringBuffer("#,##0.");
		for (int i = 0; i < precision; i++) {
			buffer.append("0");
		}
		DecimalFormat specifiedPrecisionFormat = new DecimalFormat(buffer.toString());
		buffer = null; // avoid loitering
		return specifiedPrecisionFormat.format(x);
	}
	
	/* Overloaded method which formats an int by adding ","  after 3 values. (US format)
	 * @param: x is the value to be formated
	 * @return: a String representation of the parameter after the formating has been 
	 *          applied.
	 */
	public static String format(int x) {
		return integerFormat.format(x);
	}
	
	/* formatPercent formats a double representing a percent before division by 100
	 * according to the standard DecimalFormat '%' modifier.
	 * @param: x is the percent to be formated 
	 * @return: a String representation of the double passed in, after division by 100
	 */
	public static String formatPercent(double x) {
		return percentFormat.format(x);
	}
	
	/* percentFormater returns the DecimalFormat object defined in this class for
	 * percents. Percent is truncated to 10^-2 precision.
	 * @param: N/A
	 * @return: DecimalFormat object to format percents.
	 */
	public static DecimalFormat percentFormater() {
		return percentFormat;
	}
	
	/* intFormatter returns the DecimalFormat object defined in this class for ints.
	 * Grouping is added after 3 digits according to US standards (',')
	 * @param: N/A
	 * @return: The int DecimalFormat object defined in this class.
	 */
	public static DecimalFormat intFormater() {
		return integerFormat;
	}
	
	/* doubleFormatter returns the DecimalFormat object defined in this class for 
	 * doubles. In specific, this object truncates after 10^-4 and adds groupings
	 * according to US standards (',' after 3 digits). If this is not desired, see
	 * following method.
	 * @param: N/A
	 * @return: DecimalFormat object according to the standards described above.
	 */
	public static DecimalFormat doubleFormatter() {
		return doubleFormat;
	}
	
	/* Overloaded method which allows the programmer to define the precision on
	 * which to truncate the double.
	 * @param: Precision is the defined as number of digits after which to truncate
	 * @return: DecimalFormat object adhering to the above considerations.
	 */
	public static DecimalFormat doubleFormater(int precision) {
		buffer = new StringBuffer("#,##0.");
		for (int i = 0; i < precision; i++) {
			buffer.append("0");
		}
		return new DecimalFormat(buffer.toString());
	}
	/*generateDouble handles everything to generate a double from the keyboard
	 * @param: format specifies whether or not to format the double printed after
	 *         input. Note that the formatter will only print 4 digits after the
	 *         decimal (see format(double). This will affect the String printed 
	 *         within this method but will not effect the double that is returned. 
	 *         Advised to be set to false whenever the double has a precision beyond 
	 *         10^-4.
	 *         lowerBound is the lowest valid return value (inclusive)
	 *         upperBound is the largest valid return value (inclusive)
	 * @return: validated double for general purpose
	 */
 	public static double generateDouble(boolean format, double lowerBound, double upperBound) {
		validate = false;
		double x = 0;
		while (!validate) {
			try {
				System.out.println("Please enter a double");
				x = key.nextDouble();
				if (lowerBound <= x && x <= upperBound) validate = true;
				else System.out.println("Out of specified bounds. " + lowerBound + " <= x <= " + upperBound);
			}
			catch (InputMismatchException e) {
				System.out.println("Double only. Try again");
				key.nextLine(); //consume incorrect string char
			}
		} // end validate
		if (format) System.out.print("You entered: " + NumberTools.format(x));
		return x;
	}
 	
 	/* overloaded method which allows the user to generate a double with a message
 	 * before generation. Often used to specify what the double represents. Sets format
 	 * to true by default. See following method to disable format and print a message. 
 	 * @param: message is the String to be printed before the method runs.
	 * @return: validated double for general purpose
 	 */
 	public static double generateDouble(String message) {
		System.out.println(message);
		return generateDouble(true, Double.MIN_VALUE, Double.MAX_VALUE);
	}
 	
 	/* overloaded method which prints a String before the method runs, but also allows the 
 	 * programmer to specify whether or not the double should be formated.
 	 * @param: message is the String to be printed before the method runs.
 	 *         format specifies whether the method should format the double when printed.
	 * @return: validated double for general purpose
 	 */
 	public static double generateDouble(String message, boolean format) {
		System.out.println(message);
		return generateDouble(format, Double.MIN_VALUE, Double.MAX_VALUE);
	}
 	
 	public static double generateDouble(String message, boolean format, double lowerBound, double upperBound) {
		System.out.println(message);
		return generateDouble(format, lowerBound, upperBound);
	}
	
 	/* generateInt handles everything requires to generate a valid int from a user
 	 * @param: format specifies whether the int should be formated when printing.
 	 *                Adds ',' in groups of three.
 	 *         lowerBound is the lowest possible return value (inclusive)
 	 *         maxBound is the maximum possible return value (inclusive)
 	 * @return: the validated int
 	 */
	public static int generateInt(boolean format, int lowerBound, int maxBound) {
		validate = false;
		int x = 0;
		while (!validate) {
			try {
				System.out.println("Please enter an integer");
				x = key.nextInt();
				if (lowerBound <= x && x <= maxBound) validate = true; 
				else System.out.println("Int out of range. " + lowerBound + " <= x <= " + maxBound + "");
			} catch (InputMismatchException e) {
				System.out.println("Integer only. Try again");
				key.nextLine(); //consume incorrect string char
			}
		} // end validate
		if (format) System.out.println("You entered: " + NumberTools.format(x));
		return x;
	}
	
	/* Overloaded method to generate an int which prints a message before and allows
	 * the user to specify whether or not the int should be formated when it's printed
	 * within the generateInt method.
	 * @param: message is the message to be printed before the method runs
	 *         format specifies whether to format the int (true) or not (false)
	 *         lowerBound is the lowest possible return value (inclusive)
 	 *         maxBound is the maximum possible return value (inclusive)
	 * @return: valid int
	 */
	public static int generateInt(String message, boolean format, int lowerBound, int maxBound) {
		System.out.println(message);
		return generateInt(format, lowerBound, maxBound);
	}
	
	/* overloaded method to generate an int. Method prints the message passed into 
	 * it before generating the int. Message is typically used to specify what the 
	 * int represents. By default, formating is turned on (see next method to
	 * specify whether or not the int should be formated). Allows the user to only worry about
	 * the message and the bounds.
	 * @param: message is the message to be printed
	 *         lowerBound is the lowest possible return value (inclusive)
 	 *         maxBound is the maximum possible return value (inclusive)
	 * @return: valid int
	 */
	public static int generateInt(String message, int lowerBound, int maxBound) {
		return generateInt(message, true, lowerBound, maxBound);
	}
	
	/* overloaded method which simplifies the int generation process. Allows the user to
	 * generate an int without any parameters.
	 * @param: N/A
	 * @return: valid int in the int bounds
	 */
	public static int generateInt() {
		return generateInt(true, Integer.MIN_VALUE, Integer.MAX_VALUE);
	}
	
	/* addOrdinalSuffix is a method which adds the appropriate ordinal suffix to a
	 * number (ex: 1st ("st"), 2nd ("nd) ).
	 * @param: x is the number on which to add the suffix
	 * @return: a String representation of the number with the valid suffix appended.
	 */
	public static String addOrdinalSuffix(int x) {
		int number = x;
		if (x > 13) {	
			String buffer = x + "";
			x = buffer.charAt(buffer.length() - 1) - 48;
			buffer = null; // avoid loitering
		}
		if (x == 1) return number + "st";
		else if (x == 2) return number + "nd";
		else if (x == 3) return number + "rd";
		else if (x == 11 || x == 12 || x == 13) return number + "th";
		// else
		return number + "th"; 
	}
	
	/* percentDifference calculates the percent difference between two numbers.
	 * @param: origional is the first number
	 *         newNumber is the number on which to calculate the difference
	 * @return: a double representation of the difference
	 */
	public static double percentDifference(double original, double newNumber) {
		return (newNumber - original) / original;
	}
	
	/* percentDifferenceAsString generates a formated String representation of the
	 * percent difference between two numbers, formated by the percent formatter
	 * defined by this class
	 * @param: origional is the first number
	 *         newNumber is the number on which to calculate the difference
	 * @return: a String representation of the difference, formatted according to the
	 *          percent formatter of this class
	 */
	public static String percentDifferenceAsString(double original, double newNumber) {
		return formatPercent((newNumber - original) / original);
	}
	
	/* pad enables uniform printing when generating a list of values. For example, 
	 * if you wish to print the values 0-100 all right aligned. In this case, your 
	 * length would be 3 (the length of the largest value) and str would be the specific
	 * number you are intending to pad. If you passed in 4, you would get a the String
	 * "__4". 10 would result in "_10". 100 would result in "100".
	 * @param: fieldLength is the length of the largest value to be padded
	 *         str is the String to be padded.
	 * @return: the padded String determined from the length - str.length
	 */
	public static String padOnLeft(int fieldLength, String str) {
		int lengthToPad;
		StringBuffer buffer = new StringBuffer();
		lengthToPad = fieldLength - str.length();
		for (int j = 0; j < lengthToPad; j++) {
			buffer.append(" ");
		}
		buffer.append(str);
		return buffer.toString();
	}
	
	/* Overloaded method which allows the programmer to pass an int as the value to be 
	 * padded directly. Works exactly as padOnLeft(int length, String str) works.
	 * @param: length is the length of the largest value to be padded
	 *         value is the int to be padded.
	 * @return: the padded String determined from the length - length of the int
	 */
	public static String padOnLeft(int length, int value) {
		return padOnLeft(length, Integer.toString(value));
	}
	
	public static double normalizeToRange(double value, double minValue, double maxValue) {
		if (minValue > value || maxValue < value) {
			throw new IllegalArgumentException(value + " is not within range. (" + minValue + " - " + maxValue + ")");
		}
		return 1 + (value - minValue) * (9) / (maxValue - minValue);
	}
	
//	public static double normalizeToRange(double value, double maxValue, double beginRange, double endRange) {
//		
//	}
	
	public static int factorial(int factorial) {
		if (factorial == 1) return 1;
		return factorial + factorial(factorial -1);
	}
	
	public static void assertWithinRange1_10(double x) {
		if (x < 1 || x > 10) {
			throw new IllegalArgumentException(format(x) + " is out of range [1,10]");
		}
	}
	
	public static void main(String[] args) {System.out.println(factorial(5));}
}