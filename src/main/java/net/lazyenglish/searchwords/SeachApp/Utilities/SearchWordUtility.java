package net.lazyenglish.searchwords.SeachApp.Utilities;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import net.lazyenglish.searchwords.SeachApp.Models.Item;
import net.lazyenglish.searchwords.SeachApp.Models.Result;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class SearchWordUtility {
	public final static int MAX_WORDS = 10;
	/** 
	 * This checks number of words in the form.
	 * @param origin This is original input from the user.
	 * @return returns true if the number of words exceeded MAX_WORDS
	 */
	public static boolean isOverWords(List<String> splited) {
		if(splited.size() > MAX_WORDS) {
			return true;
		}
		return false;
	}
	
	/**
	 * To get result from WordsAPI
	 * @param original The word you want to search
	 * @return Entire result from the API
	 */
	public static String request(String original) {
		OkHttpClient client = new OkHttpClient();
		
		Request request = new Request.Builder()
			.url("https://wordsapiv1.p.rapidapi.com/words/" + original + "/definitions")
			.get()
			.addHeader("x-rapidapi-host", "wordsapiv1.p.rapidapi.com")
			.addHeader("x-rapidapi-key", System.getenv("NET_LAZYENGLISH_SEARCHWORDS_APIKEY"))
			.build();
		try (Response response = client.newCall(request).execute()) {
			return response.body().string();
		} catch (IOException e) {
			System.out.println(e.getLocalizedMessage());
			return null;
		}
	}
	
	/*
	 * JSON to object 
	 */
	public static Result convertJSONToObject(String origin, String json) {
		ObjectMapper mapper = new ObjectMapper();
		Result result = new Result();

		try {
			result = mapper.readValue(json, Result.class);
		} catch (Exception e) {
			System.out.println(e.getLocalizedMessage());
			result.setWord(origin);
			List<Item> itemList = new ArrayList<>(Arrays.asList(new Item("Word not found", " - ")));
			result.setDefinitions(itemList);
		}
		return result;
	}
}
