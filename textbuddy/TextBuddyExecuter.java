package textbuddy;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class TextBuddyExecuter {
	
	private static final String MESSAGE_REQUEST_COMMAND = "command: ";
	private static final String MESSAGE_INVALID_FORMAT = "Error: command not accepted. Please try again.";
	private static final String ERROR_INVALID_SEARCH = "You have entered an invalid search keyword. Please try again.";
	private static final String MESSAGE_NOT_FOUND = "not found";
	private static final String MESSAGE_LINES_NOT_FOUND = "lines containing \"%1$s\": not found";
	private static final String MESSAGE_EMPTY_FILE = "%1$s is empty";
	private static final String MESSAGE_DISPLAY_NUMBER = "%1$s. %2$s";
	private static final String MESSAGE_FILE_NOT_FOUND = "Unable to open file '%1$s'";
	private static final String MESSAGE_READ_FILE_ERROR = "Error reading file '%1$s'";
	private static final String MESSAGE_LINES_CONTAINING = "Lines containing \"%1$s\": ";
	
	/**
	 * This method reads in command inputs from the user and processes the
	 * command
	 * 
	 * @param sc
	 *            The Scanner pointer created in the main method to scan for
	 *            user input
	 * @param nameOfFile
	 *            The name of the .txt file to edit into
	 * @return Nothing.
	 * @exception None.
	 * @see None.
	 */
	protected static void requestInput(Scanner sc, String nameOfFile) {
		System.out.print(MESSAGE_REQUEST_COMMAND);
		String command = readCommand(sc);
		ArrayList<String> contents = new ArrayList<String>();

		switch (command) {
		case "ADD":
		case "DELETE":
			String newContent = readNewContent(sc);
			if (isInvalidFormat(newContent)) {
				break;
			} else {
				executeAddDelete(nameOfFile, command, contents, newContent);
			}
			break;

		case "DISPLAY":
			executeDisplay(nameOfFile, contents);
			break;

		case "CLEAR":
			excecuteClear(nameOfFile, contents, command);
			break;

		case "SEARCH":
			executeSearch(sc, nameOfFile, contents);
			break;

		case "EXIT":
			System.exit(0);

		default:
			System.out.println(MESSAGE_INVALID_FORMAT);
			sc.nextLine(); // read in the remaining whatever the user typed so that user can try again
			break;
		}
	}
	
	/**
	 * This method determines whether the input for the add and delete commands are valid or not
	 * and prints out an error message if the inputs are invalid
	 * 
	 * @param newContent
	 *            The new content to be added or deleted
	 * @return boolean
	 *            Whether the format is valid or not
	 * @exception None.
	 * @see None.
	 */
	private static boolean isInvalidFormat(String newContent) {
		if (newContent.equals(MESSAGE_INVALID_FORMAT)) {
			System.out.println(MESSAGE_INVALID_FORMAT);
			return true;
		}
		return false;
	}
	
	/**
	 * This method executes the adding and deleting of content
	 * 
	 * @param nameOfFile
	 *            The name of the file to write to
	 * @param command
	 *            The add or delete command
	 * @param contents
	 *            The existing content in the file
	 * @param newContent
	 *            The new content to be added or deleted
	 * @return None.
	 * @exception None.
	 * @see None.
	 */
	private static void executeAddDelete(String nameOfFile, String command, ArrayList<String> contents, String newContent) {
		contents = readFile(nameOfFile);
		TextBuddyWriter.writeFile(nameOfFile, contents, command, newContent);
	}
	
	/**
	 * This method executes the display feature to display all existing content
	 * 
	 * @param nameOfFile
	 *            The name of the file to display
	 * @param contents
	 *            The existing content in the file
	 * @return None.
	 * @exception None.
	 * @see None.
	 */
	private static void executeDisplay(String nameOfFile, ArrayList<String> contents) {
		contents = readFile(nameOfFile);
		int numOfLines = contents.size();
		if (numOfLines == 0) {
			System.out.println(String.format(MESSAGE_EMPTY_FILE, nameOfFile));
		}
		for (int i = 0; i < numOfLines; i++) {
			System.out.println(String.format(MESSAGE_DISPLAY_NUMBER, (i + 1), contents.get(i)));
		}
	}
	
	/**
	 * This method executes the clear feature to clear all existing content
	 * 
	 * @param nameOfFile
	 *            The name of the file to display
	 * @param contents
	 *            The existing content in the file
	 * @param command
	 *            The command to clear all existing content in the file
	 * @return None.
	 * @exception None.
	 * @see None.
	 */
	private static void excecuteClear(String nameOfFile, ArrayList<String> contents, String command) {
		contents = readFile(nameOfFile);
		TextBuddyWriter.writeFile(nameOfFile, contents, command, "");
	}
	
	/**
	 * This method executes the search feature to search for content in the file using keywords
	 * 
	 * @param sc
	 *            The scanner object to read in the search keyword
	 * @param contents
	 *            The existing content in the file
	 * @param contents
	 *           The existing content in the file
	 * @return None.
	 * @exception None.
	 * @see None.
	 */
	private static void executeSearch(Scanner sc, String nameOfFile, ArrayList<String> contents) {
		contents = readFile(nameOfFile);
		String keyword = readNewContent(sc);
		ArrayList<String> linesFound = TextBuddyFeatures.search(contents, keyword);
	
		if (linesFound == null || keyword.equals(MESSAGE_INVALID_FORMAT)) {
			System.out.println(ERROR_INVALID_SEARCH);
		} else if (linesFound.get(0).equals(MESSAGE_NOT_FOUND)) {
			System.out.println(String.format(MESSAGE_LINES_NOT_FOUND, keyword));
		} else {
			printSearchResult(linesFound, keyword);
		}
	}
	
	/**
	 * This method prints out the lines that contains the search keyword
	 * 
	 * @param linesFound
	 *            Contains the lines that contains the search keyword
	 * @param keyword
	 *            The search keyword used
	 * @return Nothing.
	 * @exception None.
	 * @see None.
	 */
	private static void printSearchResult(ArrayList<String> linesFound, String keyword) {
		
		int numOfLines = linesFound.size();

		System.out.println(String.format(MESSAGE_LINES_CONTAINING, keyword));
		for (int i = 0; i < numOfLines; i++) {
			System.out.println(String.format(MESSAGE_DISPLAY_NUMBER, (i + 1), linesFound.get(i)));
		}
	}

	/**
	 * This method reads the cammands from the user and converts the entire
	 * String into upper case
	 * 
	 * @param sc
	 *            The Scanner pointer created in the main method to scan for
	 *            user input
	 * @return String The command that is read from user input
	 * @exception None.
	 * @see None.
	 */
	private static String readCommand(Scanner sc) {
		
		String command = sc.next().toUpperCase();
		return command;
	}

	/**
	 * This method reads the new content to be modified into the .txt file
	 * 
	 * @param sc
	 *            The Scanner pointer created in the main method to scan for
	 *            user input
	 * @return String The new content that is read from user input
	 * @exception None.
	 * @see None.
	 */
	
	private static String readNewContent(Scanner sc) {

		String newContent = "";

		try {
			newContent = sc.nextLine().substring(1);
		} catch (StringIndexOutOfBoundsException e) {
			return MESSAGE_INVALID_FORMAT;
		}

		return newContent;

	}

	/**
	 * This method reads the content/s of the .txt file
	 * 
	 * @param fileName
	 *            The name of the .txt file to be read
	 * @return ArrayList<String> Returns the content/s of the .txt file line by
	 *         line in an ArrayList
	 * @exception FileNotFoundException
	 *                On reading file.
	 * @exception IOException
	 *                On reading file.
	 * @see FileNotFoundException
	 * @see IOException
	 */
	
	private static ArrayList<String> readFile(String fileName) {

		ArrayList<String> contents = new ArrayList<String>();
		String line = null; // reference one line at a time

		try {
			FileReader fileReader = new FileReader(fileName);
			BufferedReader bufferedReader = new BufferedReader(fileReader);

			while ((line = bufferedReader.readLine()) != null) {
				contents.add(line);
			}

			bufferedReader.close();

		} catch (FileNotFoundException ex) {
			System.out.println(String.format(MESSAGE_FILE_NOT_FOUND, fileName));
		} catch (IOException ex) {
			System.out.println(String.format(MESSAGE_READ_FILE_ERROR, fileName));
		}
		
		return contents;
	}
}
