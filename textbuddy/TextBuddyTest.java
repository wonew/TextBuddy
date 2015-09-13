package textbuddy;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Test;

public class TextBuddyTest {

	private static final String SORTED_TEXT_1 = "Apple";
	private static final String SORTED_TEXT_2 = "Banana";
	private static final String SORTED_TEXT_3 = "Carrot";
	private static final String SORTED_TEXT_4 = "Donkey";
	private static final String SORTED_TEXT_5 = "Zebra";
	
	

	@Test
	public void testIsSorted() {
		
		ArrayList<String> listToTest = new ArrayList<String>();
		ArrayList<String> sortedList = new ArrayList<String>();
		
		sortedList.add(SORTED_TEXT_1);
		sortedList.add(SORTED_TEXT_2);
		sortedList.add(SORTED_TEXT_3);
		sortedList.add(SORTED_TEXT_4);
		sortedList.add(SORTED_TEXT_5);
		
		listToTest.add(SORTED_TEXT_5);
		listToTest.add(SORTED_TEXT_4);
		listToTest.add(SORTED_TEXT_3);
		listToTest.add(SORTED_TEXT_2);
		listToTest.add(SORTED_TEXT_1);
		
		assertEquals(sortedList, TextBuddy.sortContents(listToTest));
	}

}
