package tools;
/*DriverToos.java
 * Programmer: Michael Newman
 * Date: 3.20.18
 * 
 * Description: Contains static methods which are useful to driver classes.
 * 		Main use is to facilitate terminal menus and user validation
 * 
 * IO: N/A
 * 
 * Assumptions & Limitations:
 *   - Assumed the user wants input to be validated.
 */

import java.util.InputMismatchException; // Invalid User Input
import java.util.Scanner;                // Keyboard input

public class DriverTools {
	private static Scanner key = new Scanner(System.in);  // keyboard scanner for input
	private static boolean validate;                      // used to validate input
	
	private DriverTools() {} // prevent instance creation
	
	/*validate is a method to validate that the user has chosen the correct input
	 * @param: N/A
	 * @return: true if user enters 'Y', false if user enters 'N'
	 */
	public static boolean validate() {
		// return 1 if yes, 0 otherwise
		String input;
		System.out.println("'Y' or 'N' (case-insensitive)");
		while(true) {
			input = key.nextLine();
			if (input.equalsIgnoreCase("y")) {
				return true;
			}
			else if (input.equalsIgnoreCase("n")) {
				return false;
			}
			else {
				System.out.println("Must enter 'Y' or 'N' (case-insensitive)");
			} // end if-elseif-else
		} // end while loop for validation
	}
	
	/*validate(message) prints a message before asking the user yes or no. Used to specify
	 * what the program is asking the user to validate. 
	 * @param: message is the message to be displayed before the method returns.
	 * @return: true if user enters 'Y', false if user enters 'N'
	 */
	public static boolean validate(String message) {
		System.out.println(message);
		return validate();
	}
	
	/* generateString is a simple method to return a String from the static scanner
	 * employed in this class.
	 * @param: N/A
	 * @return: String from keyboard
	 */
	public static String generateString() {
		return key.nextLine();
	}
	
	/* Overloaded method which prints a message before the string is generated.
	 * Main use is to specify what the string to be returned is intended to 
	 * represent.
	 * @param: message is the String to be printed
	 * @return String generated from keyboard
	 */
	public static String generateString(String message) {
		System.out.println(message);
		return key.nextLine();
	}
	
	/* DEPRECATED: Use the Menu package and the Menu class.
	 * 
	 * selection returns a user selection depending on the menu. method protects 
	 * against an imperfect user by ensuring one enters an integer between 1 
	 * and the max of the given menu. Options should be one-based and the max
	 * should be the final valid choice.
	 * @param: Max is defined as the total amount of options in a given menu.
	 *             Used to validate user choice. Inclusive.
	 *         Options is the string which contains the menu options.
	 * @return: the users menu selection represented as an int
	 */
	@Deprecated
 	public static int selection(String options, int max) {
 		int choice = 0;
 		validate = false;
		while (!validate) {
			System.out.println(options + "\nWhat would you like do?");
			try { choice = key.nextInt(); }
			catch (InputMismatchException e) {
				System.out.println("Integer Only\n");
				key.nextLine(); // consume incorrect string char
				continue;       // restart loop
			}
			// validate that user choice is valid per the menu options & max
			if (choice <= max && choice > 0) validate = true;
			else System.out.println("Invalid input option. "
					+ "1 <= x <=" + max + "/n/n");
		} // end validate
		key.nextLine(); // consume new line
		return choice;
	} // end selection
 	

	
	public static void main(String[] args) {}
}