package com.word.chileanway.model;

import java.io.Serializable;
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
	private String exapmple_us;
	private String exapmple_es;
	private String english;
	
	public WordVO(String spanishWord,String english,String example_es,String example_us) {
		this.spanish=spanishWord;
		this.english=english;
		this.exapmple_es=example_es;
		this.exapmple_us=example_us;
	}
	
	public String getExapmple_us() {
		return exapmple_us;
	}

	public void setExapmple_us(String exapmple_us) {
		this.exapmple_us = exapmple_us;
	}

	public String getExapmple_es() {
		return exapmple_es;
	}

	public void setExapmple_es(String exapmple_es) {
		this.exapmple_es = exapmple_es;
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
		return getSpanish();
	}
}
