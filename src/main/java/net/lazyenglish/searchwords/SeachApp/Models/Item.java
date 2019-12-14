package net.lazyenglish.searchwords.SeachApp.Models;

public class Item {
	private String definition;
	private String partOfSpeech;
	
	/*
	 * Constructors
	 */
	public Item() {
		
	}
	public Item(String definition, String partOfSpeech) {
		this.definition = definition;
		this.partOfSpeech = partOfSpeech;
	}
	
	/*
	 * Methods
	 */
	public String getDefinition() {
		return definition;
	}
	public void setDefinition(String definition) {
		this.definition = definition;
	}
	public String getPartOfSpeech() {
		return partOfSpeech;
	}
	public void setPartOfSpeech(String partOfSpeech) {
		this.partOfSpeech = partOfSpeech;
	}
	
}