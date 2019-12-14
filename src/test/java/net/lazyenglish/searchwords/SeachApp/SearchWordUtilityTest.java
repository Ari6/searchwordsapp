package net.lazyenglish.searchwords.SeachApp;

import net.lazyenglish.searchwords.SeachApp.Models.Item;
import net.lazyenglish.searchwords.SeachApp.Models.Result;
import net.lazyenglish.searchwords.SeachApp.Utilities.SearchWordUtility;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import java.util.Arrays;
import org.junit.jupiter.api.*;

import com.fasterxml.jackson.databind.ObjectMapper;

public class SearchWordUtilityTest {
	class Failed {
		private boolean success;
		private String message;
		public boolean isSuccess() {
			return success;
		}
		public void setSuccess(boolean success) {
			this.success = success;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		
	}
	@Test
	public void requestTest() {
		/*
		 * The word is not found in the API
		 */
		String word1 = "ddd";
		ObjectMapper mapper1 = new ObjectMapper();
		String expected1 = "";
		
		Failed failed = new Failed();
		failed.setSuccess(false);
		failed.setMessage("word not found");
		
		try {
			expected1 = mapper1.writeValueAsString(failed);
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
			System.out.println(e.getStackTrace());
		}
		String actual1 = SearchWordUtility.request(word1);
		Assertions.assertEquals(expected1, actual1);
		
		/*
		 * The word is found in the API
		 * If API change their result, then this test does not work.
		 */
		String word2 = "talent";
		Result expectedResult2 = new Result();
		ObjectMapper mapper2 = new ObjectMapper();
		String expected2 = "";
		expectedResult2.setWord(word2);
		List<Item> definitions2 = new ArrayList<>(
				Arrays.asList(new Item("natural abilities or qualities","noun"),
							  new Item("a person who possesses unusual innate ability in some field or activity","noun")
							  ));
		expectedResult2.setDefinitions(definitions2);
		
		try {
			expected2 = mapper2.writeValueAsString(expectedResult2);
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
			System.out.println(e.getStackTrace());
		}
		String actual2 = SearchWordUtility.request(word2);
		Assertions.assertEquals(expected2, actual2);
		
	}
	
	@Test
	public void convertJASONToObject() {
		/*
		 * The word is not found in the API
		 */
		Result result1 = SearchWordUtility.convertJSONToObject("ddd", SearchWordUtility.request("ddd"));
		Assertions.assertEquals("ddd", result1.getWord());
		for(Item item1 : result1.getDefinitions()) {
			Assertions.assertEquals("Word not found", item1.getDefinition());
			Assertions.assertEquals(" - ", item1.getPartOfSpeech());
		}
		
		/*
		 * The word is found in the API
		 */
		Result result2 = SearchWordUtility.convertJSONToObject("talent", SearchWordUtility.request("talent"));
		Assertions.assertEquals("talent", result2.getWord());
		List<Item> item2 = result2.getDefinitions();
		Assertions.assertEquals("natural abilities or qualities", item2.get(0).getDefinition());
		Assertions.assertEquals("noun", item2.get(0).getPartOfSpeech());
		Assertions.assertEquals("a person who possesses unusual innate ability in some field or activity", item2.get(1).getDefinition());
		Assertions.assertEquals("noun", item2.get(1).getPartOfSpeech());
	}
}
