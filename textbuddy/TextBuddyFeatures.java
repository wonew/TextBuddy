package textbuddy;

import java.util.ArrayList;
import java.util.Collections;

public class TextBuddyFeatures {
	
	private static final String MESSAGE_NOT_FOUND = "not found";
	
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
}
