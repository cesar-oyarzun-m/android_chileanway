package com.word.chileanway.model;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.res.AssetManager;
/**
 * Model singleton class
 * @author Cesar Oyarzun
 *
 */
public class ChileanWayModel {
	private static final String UTF_8 = "UTF-8";
	private static final String DICTIONARY_JSON = "dictionary.json";
	private static final String DATA = "data";
	private static final String WORD_US = "word_us";
	private static final String WORD_ES = "word_es";
	private ArrayList<WordVO> wordList;
	private static ChileanWayModel model=null;
	
	/**
	 * Get Model Instance
	 * @return {@link ChileanWayModel}
	 */
	public static ChileanWayModel getInstance(){
		if(model==null){
			model=new ChileanWayModel();
		}
		return model;
	}
	
	/**
	 * Load Json Model from json File
	 * @param context
	 * @param assetManaget
	 * @return {@link ArrayList}
	 */
	public  ArrayList<WordVO> loadJsonFile(Context context,AssetManager assetManaget) {
		wordList = new ArrayList<WordVO>();
		String jsonStr = loadJSONFromAsset(assetManaget);
		JSONObject jsonObj;
		try {
			jsonObj = new JSONObject(jsonStr);
			
			// Getting data JSON Array nodes
			JSONArray dictionaryArray = jsonObj.getJSONArray(DATA);
			for (int i = 0; i < dictionaryArray.length(); i++) {
				JSONObject word = dictionaryArray.getJSONObject(i);
				String spanishWord = word.getString(WORD_ES);
				String englishWord = word.getString(WORD_US);
				String definition_es=word.getString("definition_es");
				WordVO wordObject=new WordVO(spanishWord,englishWord,definition_es);
				wordList.add(wordObject);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return wordList;
	}
	
	/**
	 * Loaad Json dictionary from local phone store
	 * @param assetManaget
	 * @return
	 */
	private  String loadJSONFromAsset(AssetManager assetManaget) {
		String json = null;
		try {
			InputStream is = assetManaget.open(DICTIONARY_JSON);
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			json = new String(buffer, UTF_8);

		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
		return json;
	}
	/**
	 * Get model
	 * @return {@link ArrayList}
	 */
	public  ArrayList<WordVO> getWordList() {
		return wordList;
	}
}
