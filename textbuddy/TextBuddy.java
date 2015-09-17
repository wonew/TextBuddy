package textbuddy;

import java.io.*;
import java.util.*;

/**
 * This TextBuddy program allows users to add, delete, and display information
 * into a .txt file using specified formats. The command format is given by the
 * example interaction below:
 * 
 * Welcome to TextBuddy. mytextfile.txt is ready for use
 * command: add Hey Jude,
 * added to mytextfile.txt: "Hey Jude, "
 * command: add don't make it bad
 * added to mytextfile.txt: "don't make it bad"
 * command: add Take a sad song and make it better 
 * added to mytextfile.txt: "Take a sad song and make it better"
 * command: display
 * 1. Hey Jude, 
 * 2. don't make it bad 
 * 3. Take a sad song and make it better 
 * command: delete 2
 * deleted from mytextfile.txt: "don't make it bad"
 * command: display 
 * 1. Hey Jude, 
 * 2. Take a sad song and make it better 
 * command: clear 
 * all content deleted from mytextfile.txt
 * command: display
 * mytextfile.txt is empty
 * command: exit
 * 
 *
 * @author June Lim
 * @version 0.1
 * @since 2015-08-28
 */

public class TextBuddy {

	private static final String MESSAGE_WELCOME = "Welcome to TextBuddy. %1$s is ready for use";
	private static final String MESSAGE_EMPTY_FILE = "%1$s is empty";
	private static final String MESSAGE_READ_FILE_ERROR = "Error reading file '%1$s'";
	private static final String MESSAGE_WRITE_FILE_ERROR = "Error writing to file '%1$s'";
	private static final String MESSAGE_FILE_NOT_FOUND = "Unable to open file '%1$s'";
	private static final String MESSAGE_FILE_NOT_CREATED = "Error. File is not created.";
	private static final String MESSAGE_REQUEST_COMMAND = "command: ";
	private static final String MESSAGE_INVALID_FORMAT = "Error: command not accepted. Please try again.";
	private static final String MESSAGE_ADDED = "added to %1$s: \"%2$s\"";
	private static final String MESSAGE_DELETED_LINE = "deleted from %1$s: \"%2$s\"";
	private static final String MESSAGE_NO_CONTENT_TO_DELETE = "There is nothing to delete in %1$s.";
	private static final String MESSAGE_INVALID_DELETE = "Delete failed: The line does not exist.";
	private static final String MESSAGE_DELETE_ALL = "all content deleted from %1$s";
	private static final String MESSAGE_DISPLAY_NUMBER = "%1$s. %2$s";
	private static final String MESSAGE_INVALID_DELETE_ARGUMENT = "Please enter a non-zero positive integer to delete line";
	private static final String ERROR_INVALID_SEARCH = "You have entered an invalid search keyword. Please try again.";
	private static final String MESSAGE_NOT_FOUND = "not found";
	private static final String MESSAGE_LINES_NOT_FOUND = "lines containing \"%1$s\": not found";
	private static final String MESSAGE_LINES_CONTAINING = "Lines containing \"%1$s\": ";

	/**
	 * This is the main method which uses the command line argument to get the
	 * user's input on what to name the .txt file.
	 * 
	 * @param args
	 *            This is the only parameter to get the name of the txt file
	 * @return Nothing.
	 * @exception None.
	 * @see None.
	 */
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);
		String nameOfFile = args[0];

		createFile(nameOfFile);

		while (true) {
			requestInput(sc, nameOfFile);
		}
	}

	/**
	 * This method creates a .txt file based on the file name provided by the
	 * user and displays a welcome message.
	 * 
	 * @param fileName
	 *            This is the only parameter for the name of the file to be
	 *            created
	 * @return Nothing.
	 * @exception IOException
	 *                On input error.
	 * @see IOException
	 */
	private static void createFile(String fileName) {

		File file = new File(fileName);

		if ( !(file.exists()) ) {
			try {
				file.createNewFile();
			} catch (IOException e) {
				System.out.println(MESSAGE_FILE_NOT_CREATED);
			}
		}

		System.out.println(String.format(MESSAGE_WELCOME, fileName));
	}

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
	private static void requestInput(Scanner sc, String nameOfFile) {
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
		writeFile(nameOfFile, contents, command, newContent);
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
		writeFile(nameOfFile, contents, command, "");
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
		ArrayList<String> linesFound = search(contents, keyword);
	
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

	/**
	 * This method sorts all existing content in the .txt file
	 * 
	 * @param contents
	 *            Contains all existing content
	 * @return ArrayList<String> Returns the sorted contents of the .txt file in
	 *         alphabetical order
	 * @exception None.
	 * @see None.
	 */
	public static ArrayList<String> sortContents(ArrayList<String> contents) {
		Collections.sort(contents);
		return contents;
	}

	/**
	 * This method searches for specified keywords in the contents of the .txt
	 * file
	 * 
	 * @param contents
	 *            Contains all existing content
	 * @param keyword
	 *            The keyword to be searched for
	 * @return ArrayList<String> Returns the list of lines containing the
	 *         specified keyword
	 * @exception None.
	 * @see None.
	 */
	public static ArrayList<String> search(ArrayList<String> contents, String keyword) {

		ArrayList<String> linesFound = new ArrayList<String>();

		if (keyword == null || contents.size() == 0) {
			return linesFound;
		} else {
			keyword = keyword.toUpperCase();
			int numOfLines = contents.size();

			for (int i = 0; i < numOfLines; i++) {
				String content = contents.get(i);
				content = content.toUpperCase();
				if (content.contains(keyword)) {
					linesFound.add(contents.get(i));
				}
			}
			
			if (linesFound.size() == 0) {
				linesFound.add(MESSAGE_NOT_FOUND);
			}
			return linesFound;
		}
	}

	/**
	 * This method writes all existing content into the .txt file
	 * 
	 * @param bufferedWriter
	 *            Pointer to be able to write into the file
	 * @param contents
	 *            Contains all existing content
	 * @param fileName
	 *            The name of the file to write into
	 * @return Nothing.
	 * @exception IOException
	 *                On writing file.
	 * @see IOException
	 */
	private static void writeExistingContent(BufferedWriter bufferedWriter, ArrayList<String> contents,
			String fileName) {
		try {
			int numOfLines = contents.size();
			for (int i = 0; i < numOfLines; i++) {
				bufferedWriter.write(contents.get(i));
				bufferedWriter.newLine();
			}
		} catch (IOException ex) {
			System.out.println(String.format(MESSAGE_WRITE_FILE_ERROR, fileName));
		}
	}

	/**
	 * This method writes all existing AND new content into the .txt file
	 * 
	 * @param fileName
	 *            The name of the file to write into
	 * @param contents
	 *            Contains all existing content
	 * @param command
	 *            The command to edit the file
	 * @param newContent
	 *            Contains the new content to be modified into the .txt file
	 * @return Nothing.
	 * @exception IOException
	 *                On writing file.
	 * @see IOException
	 */
	private static void writeFile(String fileName, ArrayList<String> contents, String command, String newContent) {

		try {
			FileWriter fileWriter = new FileWriter(fileName);
			BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);

			switch (command) {
			case "ADD":
				writeAdd(contents, newContent, bufferedWriter, fileName);
				break;

			case "DELETE":
				if ( !(isValidDelete(newContent, contents, fileName, bufferedWriter)) ) {
					break;
				} else {
					writeDelete(bufferedWriter, contents, fileName, newContent);
				}
				break;

			case "CLEAR":
				writeClear(fileName);
				break;
			}

			bufferedWriter.close();

		} catch (IOException ex) {
			System.out.println(String.format(MESSAGE_WRITE_FILE_ERROR, fileName));
		}
	}
	
	/**
	 * This method writes new content into the file and displays a confirmation message
	 * that the new content has been successfully added
	 * 
	 * @param contents
	 *            The existing content in the file
	 * @param newContent
	 *            The new content to be added
	 * @param bufferedWriter
	 *            The BufferedWriter object to write content into the file
	 * @param fileName
	 *            The name of the file to be written into
	 * @return None.
	 * @exception None.
	 * @see None.
	 */
	private static void writeAdd (ArrayList<String> contents, String newContent, BufferedWriter bufferedWriter, String fileName) {
		
		contents.add(newContent);
		sortContents(contents);
		writeExistingContent(bufferedWriter, contents, fileName);
		System.out.println(String.format(MESSAGE_ADDED, fileName, newContent));
	}
	
	/**
	 * This method determines whether the delete parameter specified by the user is valid or not
	 * and displays a message if the delete parameter is invalid
	 * 
	 * @param newContent
	 *            The new content to be deleted
	 * @param contents
	 *            The existing content in the file
	 * @param fileName
	 *            The name of the file to delete the content from
	 * @param bufferedWriter
	 *            The BufferedWriter object to delete content from the file
	 * @return boolean
	 *            Whether the delete parameter is valid or not
	 * @exception None.
	 * @see None.
	 */
	private static boolean isValidDelete(String newContent, ArrayList<String> contents, String fileName, BufferedWriter bufferedWriter) {
		
		try {
			Integer.parseInt(newContent); // check if newContent is a valid integer
			return true;
		} catch (NumberFormatException e) {
			// still write whatever you already have into the text file
			writeExistingContent(bufferedWriter, contents, fileName);
			System.out.println(MESSAGE_INVALID_DELETE_ARGUMENT);
			return false;
		}
	}
	
	/**
	 * This method deletes the content specified by the user and displays error messages
	 * if the delete parameter is illogical (e.g. if there is no content to delete or
	 * if the line to delete does not exist). If the line to delete is valid, the file
	 * will be updated and a confirmation message will be displayed
	 * 
	 * @param bufferedWriter
	 *            The BufferedWriter object to delete content from the file
	 * @param contents
	 *            The existing content in the file
	 * @param fileName
	 *            The name of the file to delete the content from
	 * @param newContent
	 *            The new content to be deleted
	 * @return None.
	 * @exception None.
	 * @see None.
	 */
	private static void writeDelete(BufferedWriter bufferedWriter, ArrayList<String> contents, String fileName, String newContent) {
		
		int numOfLines = contents.size();
		int lineToDelete = Integer.parseInt(newContent);
	
		if (numOfLines == 0 && lineToDelete > 0) {
			System.out.println(String.format(MESSAGE_NO_CONTENT_TO_DELETE, fileName));
		} else if (lineToDelete > numOfLines || lineToDelete <= 0) {
			writeExistingContent(bufferedWriter, contents, fileName);
			System.out.println(String.format(MESSAGE_INVALID_DELETE));
		} else {
			String removedElement = contents.remove(lineToDelete - 1);
			writeExistingContent(bufferedWriter, contents, fileName);
			System.out.println(String.format(MESSAGE_DELETED_LINE, fileName, removedElement));
		}
	}
	
	/**
	 * This method clears the file of all content.
	 * 
	 * @param fileName
	 *            The name of the file to clear the content from
	 * @return None.
	 * @exception IOException
	 *                On writing file.
	 * @see IOException
	 */
	private static void writeClear(String fileName) {
		
		try {
			File file = new File(fileName);
			file.createNewFile();
			System.out.println(String.format(MESSAGE_DELETE_ALL, fileName));
		} catch (IOException ex) {
			System.out.println(String.format(MESSAGE_WRITE_FILE_ERROR, fileName));
		}
	}

}