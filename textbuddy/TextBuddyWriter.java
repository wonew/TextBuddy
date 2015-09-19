package textbuddy;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

public class TextBuddyWriter {
	
	private static final String MESSAGE_WRITE_FILE_ERROR = "Error writing to file '%1$s'";
	private static final String MESSAGE_ADDED = "added to %1$s: \"%2$s\"";
	private static final String MESSAGE_DELETE_ALL = "all content deleted from %1$s";
	private static final String MESSAGE_DELETED_LINE = "deleted from %1$s: \"%2$s\"";
	private static final String MESSAGE_NO_CONTENT_TO_DELETE = "There is nothing to delete in %1$s.";
	private static final String MESSAGE_INVALID_DELETE = "Delete failed: The line does not exist.";
	private static final String MESSAGE_INVALID_DELETE_ARGUMENT = "Please enter a non-zero positive integer to delete line";

	
	// refactor into TextBuddyWriter.java class
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
		 * @return String Outcome of whether the content has been added/deleted/cleared
		 * @exception IOException
		 *                On writing file.
		 * @see IOException
		 */
		protected static String writeFile(String fileName, ArrayList<String> contents, String command, String newContent) {

			String result = "";
			
			try {
				FileWriter fileWriter = new FileWriter(fileName);
				BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
				
				switch (command) {
				case "ADD":
					result = writeAdd(contents, newContent, bufferedWriter, fileName);
					break;

				case "DELETE":
					if ( !(isValidDelete(newContent, contents, fileName, bufferedWriter)) ) {
						break;
					} else {
						result = writeDelete(bufferedWriter, contents, fileName, newContent);
					}
					break;

				case "CLEAR":
					result = writeClear(fileName);
					break;
				}
				
				bufferedWriter.close();

			} catch (IOException ex) {
				System.out.println(String.format(MESSAGE_WRITE_FILE_ERROR, fileName));
			}
			return result;
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
		 * @return String Confirmation message that the new content has been added
		 * @exception None.
		 * @see None.
		 */
		private static String writeAdd (ArrayList<String> contents, String newContent, BufferedWriter bufferedWriter, String fileName) {
			
			contents.add(newContent);
			TextBuddyFeatures.sortContents(contents);
			writeExistingContent(bufferedWriter, contents, fileName);
			System.out.println(String.format(MESSAGE_ADDED, fileName, newContent));
			return String.format(MESSAGE_ADDED, fileName, newContent);
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
		 * @return String Confirmation of whether the line has been successfully deleted
		 * @exception None.
		 * @see None.
		 */
		private static String writeDelete(BufferedWriter bufferedWriter, ArrayList<String> contents, String fileName, String newContent) {
			
			int numOfLines = contents.size();
			int lineToDelete = Integer.parseInt(newContent);
		
			if (numOfLines == 0 && lineToDelete > 0) {
				System.out.println(String.format(MESSAGE_NO_CONTENT_TO_DELETE, fileName));
				return String.format(MESSAGE_NO_CONTENT_TO_DELETE, fileName);
			} else if (lineToDelete > numOfLines || lineToDelete <= 0) {
				writeExistingContent(bufferedWriter, contents, fileName);
				System.out.println(String.format(MESSAGE_INVALID_DELETE));
				return String.format(MESSAGE_INVALID_DELETE);
			} else {
				String removedElement = contents.remove(lineToDelete - 1);
				writeExistingContent(bufferedWriter, contents, fileName);
				System.out.println(String.format(MESSAGE_DELETED_LINE, fileName, removedElement));
				return String.format(MESSAGE_DELETED_LINE, fileName, removedElement);
			}
		}
		
		/**
		 * This method clears the file of all content.
		 * 
		 * @param fileName
		 *            The name of the file to clear the content from
		 * @return String Confirmation of whether all content in the file has been successfully cleared
		 * @exception IOException
		 *                On writing file.
		 * @see IOException
		 */
		private static String writeClear(String fileName) {
			
			try {
				File file = new File(fileName);
				file.createNewFile();
				System.out.println(String.format(MESSAGE_DELETE_ALL, fileName));
				return String.format(MESSAGE_DELETE_ALL, fileName);
			} catch (IOException ex) {
				System.out.println(String.format(MESSAGE_WRITE_FILE_ERROR, fileName));
				return String.format(MESSAGE_WRITE_FILE_ERROR, fileName);
			}
		}
}
