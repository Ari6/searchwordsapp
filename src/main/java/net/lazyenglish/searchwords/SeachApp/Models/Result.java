package net.lazyenglish.searchwords.SeachApp.Models;

import java.util.List;

import org.springframework.web.bind.annotation.ModelAttribute;

public class Result {
	private String word;
	private List<Item> definitions;
	
	/*
	 * Methods	
	 */
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	
	public List<Item> getDefinitions() {
		return definitions;
	}

	public void setDefinitions(List<Item> definitions) {
		this.definitions = definitions;
	}

}
