package com.word.chileanway.model;

import java.io.Serializable;
import java.util.Locale;
/**
 * Word object
 * @author Cesar Oyarzun
 *
 */
public class WordVO implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String spanish;
	private String definition_es;
	private String english;
	private static final Locale localeES=new Locale("es-CL");
	
	
	public WordVO(String spanishWord, String englishWord, String definition_es) {
		this.spanish=spanishWord;
		this.english=englishWord;
		this.definition_es=definition_es;
	}

	
	public String getDefinition_es() {
		return definition_es;
	}

	public void setDefinition_es(String definition_es) {
		this.definition_es = definition_es;
	}

	public String getSpanish() {
		return spanish;
	}
	public void setSpanish(String spanish) {
		this.spanish = spanish;
	}
	
	public String getEnglish() {
		return english;
	}
	public void setEnglish(String english) {
		this.english = english;
	}
	@Override
	public String toString() {
		return getSpanish().toLowerCase(localeES);
	}
}
