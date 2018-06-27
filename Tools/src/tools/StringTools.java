package tools;
/*StringTools.java
 * Programmer: Michael Newman
 * Date: 4/21/18
 * 
 * Description: StringTools is a static library of useful parsing methods for Strings.
 * 
 * IO: N/A
 * 
 * Assumptions & Limitations
 *   - N/A
 */

import java.util.ArrayList;  // used to split a String by a char

public class StringTools {
	private static StringBuffer buffer; // buffer for String generation
	private static char testChar;       // char to test by various methods
	
	/* splitByChar splits a String by the char delimiter passed as a parameter
	 * @param: str is the String to work on
	 *         delimiter is the Char on which to split the String into an array element
	 * @return: String array split by the delimiter
	 */
	public static String[] splitByChar(String str, char delimiter) {
		buffer = new StringBuffer();
		ArrayList<String> list = new ArrayList<>();
		for (int i = 0; i <= str.length(); i++) {
			try {
				testChar = str.charAt(i);
				if (testChar == delimiter) throw new Exception();
				buffer.append(testChar);
			} catch (Exception e) {
				list.add(buffer.toString());
				buffer = new StringBuffer();
			}	
		}
		String[] toReturn = new String[list.size()];
		toReturn = list.toArray(toReturn);
		return toReturn;
	}
	
	/* removeLeadingSpaces removes all spaces which prefix a string
	 * @param: str is the string to work on
	 * @return: String with all prefixing spaces removed
	 */
	public static String removeLeadingSpaces(String str) {
		buffer = new StringBuffer();
		int i = 0;
		while (str.charAt(i) == ' ') i++;
		while (i < str.length()) {
			buffer.append(str.charAt(i));
			i++;
		}
		return buffer.toString();
	}
	
	/* removeTrailingSpaces removes all spaces which follow a String
	 * @param: str is the String to work on
	 * @return: a String with all following spaces removed.
	 */
	public static String removeTrailingSpaces(String str) {
		buffer = new StringBuffer();
		int i = str.length() - 1;
		while (str.charAt(i) == ' ') i--;
		int j = 0;
		while (j <= i) {
			buffer.append(str.charAt(j));
			j++;
		}
		return buffer.toString();
	}
	
	/* removeAllSpaces remove ALL spaces from a String regardless of where they are
	 * @param: str is the String to work on
	 * @return: String with all spaces removed
	 */
	public static String removeAllSpaces(String str) {
		buffer = new StringBuffer();
		for (int i = 0; i < str.length(); i++) {
			testChar = str.charAt(i);
			if (testChar != ' ') buffer.append(testChar);
		}
		return buffer.toString();
	}
	
	/* contains tests whether a certain string contains a partial String
	 * @param: fullString is the String to test for a partial
	 *         partialString is the String to test the fullString against.
	 * @return: true if fullString contains partialString, false otherwise
	 */
	public static boolean contains(String fullString, String partialString) {
		int found = fullString.indexOf(partialString.charAt(0));
		if (found == -1) return false;
		
		int i,    // counter for Full String
		    j,    // counter for Partial String
		    temp, // holder for i
		    cutOff = fullString.length() - partialString.length();
		
		for (i = found; i <= cutOff; i++) {
			temp = i;
			j = 0;
			try {
				while (fullString.charAt(i) == partialString.charAt(j)) {
					j++;
					i++;
				}
			} catch (StringIndexOutOfBoundsException e) {
				if (j == partialString.length()) return true;
			}
			i = temp;
		}
		return false;
	}
	
	/* pad enables uniform printing when generating a list of values. For example, 
	 * if you wish to print the values 0-100 all right aligned. In this case, your 
	 * length would be 3 (the length of the largest value) and str would be the specific
	 * number you are intending to pad. If you passed in 4, you would get a the String
	 * "__4". 10 would result in "_10". 100 would result in "100".
	 * @param: length is the length of the largest value to be padded
	 *         str is the String to be padded.
	 * @return: the padded String determined from the length - str.length
	 */
	public static String padOnLeft(int length, String str) {
		int lengthToPad;
		StringBuffer buffer = new StringBuffer();
		lengthToPad = length - str.length();
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
	
	/* padToCenter pads a String to the center of the length feild to the best of its 
	 * ability. Note that the centered nature of the return String is entirely dependent
	 * upon the length of the String parameter and the oddness of the length.
	 * @param: length is the length of the feild
	 *         str is the String to pad
	 * @return: a padded string dependent upon the length and the String parameter.
	 */
	public static String padToCenter(int length, String str) {
		StringBuffer buffer = new StringBuffer(str);
		int toPad = length - str.length();
		if (toPad == 0) return str;
		if (toPad < 0) throw new IllegalArgumentException("Cannot pad a negative length: " 
								+ str + " (Max size: " + length + ")");
		boolean left = true;
		for (int i = 0; i < toPad; i++, left = !left) {
			if (left) buffer.insert(0, " ");
			else buffer.append(" ");
		}
		return buffer.toString();	
	}
	
	public static String removeLastComma(StringBuffer buffer) {
		return removeLastComma(buffer.toString());
	}
	
	public static String removeLastComma(String str) {
		int finalIndex = str.lastIndexOf(",");
		if (finalIndex == -1) return str;
		return str.substring(0, finalIndex);
	}
	
	public static String trimBraces(String str) {
		int MIN_LENGTH = 3;
		str = str.trim();
		if (str.length() < MIN_LENGTH) {
			throw new IllegalArgumentException("Cannot trim braces from " + str 
					+ "\n  length: " + str.length() + " (length >= " + MIN_LENGTH + ")");
		}
		return str.substring(1, str.length() - 1);
	}

	public static void main(String[] args) {
		System.out.println("|" + StringTools.padToCenter(8, "1234567890") + "|"
				+ "\n|" + StringTools.padToCenter(8, "123") + "|");
	}
}