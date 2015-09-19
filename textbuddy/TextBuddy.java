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
	private static final String MESSAGE_FILE_NOT_CREATED = "Error. File is not created.";

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
			TextBuddyExecuter.requestInput(sc, nameOfFile);
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

}