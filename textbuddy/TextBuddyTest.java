package textbuddy;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Collections;

import org.junit.Test;

public class TextBuddyTest {

	private static final String SORTED_TEXT_1 = "Apple Seoul Carrot";
	private static final String SORTED_TEXT_2 = "Amsterdam Copenhagen Stockholm";
	private static final String SORTED_TEXT_3 = "Seoul Tokyo Singapore";
	private static final String SORTED_TEXT_4 = "Football Skateboard Swimming";
	private static final String SORTED_TEXT_5 = "Zebra Seoul Dog";
	
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
		
		Collections.sort(sortedList); // to make sure that this list to test against is sorted
		
		assertEquals(sortedList, TextBuddy.sortContents(listToTest));
	}
	
	@Test
	public void testSearch() {
		
		ArrayList<String> linesFound = new ArrayList<String>();
		ArrayList<String> contents = new ArrayList<String>();
		String searchKeyword = "seoul";
		
		contents.add(SORTED_TEXT_5);
		contents.add(SORTED_TEXT_4);
		contents.add(SORTED_TEXT_3);
		contents.add(SORTED_TEXT_2);
		contents.add(SORTED_TEXT_1);
		
		Collections.sort(contents); // to make sure that this list to test against is sorted
		
		linesFound.add(SORTED_TEXT_1);
		linesFound.add(SORTED_TEXT_3);
		linesFound.add(SORTED_TEXT_5);
		
		assertEquals(linesFound, TextBuddy.search(contents, searchKeyword));
	}
	
	@Test
	public void testNullSearchKeyword() {
		
		ArrayList<String> contents = new ArrayList<String>();
		ArrayList<String> linesFound = new ArrayList<String>();
		String searchKeyword = null;
		
		contents.add(SORTED_TEXT_5);
		contents.add(SORTED_TEXT_4);
		contents.add(SORTED_TEXT_3);
		contents.add(SORTED_TEXT_2);
		contents.add(SORTED_TEXT_1);
		
		Collections.sort(contents); // to make sure that this list to test against is sorted
		
		assertEquals(linesFound, TextBuddy.search(contents, searchKeyword)); // returns null ArrayList
	}
	
	@Test
	public void testNullContentArraylist() {
		
		ArrayList<String> contents = new ArrayList<String>();
		ArrayList<String> linesFound = new ArrayList<String>();
		String searchKeyword = "stockholm";
		
		assertEquals(linesFound, TextBuddy.search(contents, searchKeyword)); // returns null ArrayList
	}
	
	@Test
	public void testKeywordNotFound() {
		
		ArrayList<String> contents = new ArrayList<String>();
		ArrayList<String> linesFound = new ArrayList<String>();
		String searchKeyword = "paris";
		
		contents.add(SORTED_TEXT_5);
		contents.add(SORTED_TEXT_4);
		contents.add(SORTED_TEXT_3);
		contents.add(SORTED_TEXT_2);
		contents.add(SORTED_TEXT_1);
		
		linesFound.add(searchKeyword + " not found.");
		
		assertEquals(linesFound, TextBuddy.search(contents, searchKeyword));
	}
}
