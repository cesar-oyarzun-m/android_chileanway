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
	private ArrayList<WordVO> wordList;
	private static ChileanWayModel model=null;
	
	public static ChileanWayModel getInstance(){
		if(model==null){
			model=new ChileanWayModel();
		}
		return model;
	}
	
	public  ArrayList<WordVO> loadJsonFile(Context context,AssetManager assetManaget) {
		wordList = new ArrayList<WordVO>();
		String jsonStr = loadJSONFromAsset(assetManaget);
		JSONObject jsonObj;
		try {
			jsonObj = new JSONObject(jsonStr);
			
			// Getting data JSON Array nodes
			JSONArray dictionaryArray = jsonObj.getJSONArray("data");
			for (int i = 0; i < dictionaryArray.length(); i++) {
				JSONObject word = dictionaryArray.getJSONObject(i);
				String spanishWord = word.getString("word_es");
				String englishWord = word.getString("word_us");
				String example_es=word.getString("example_es");
				String example_us=word.getString("example_us");
				WordVO wordObject=new WordVO(spanishWord,englishWord,example_es,example_us);
				wordList.add(wordObject);
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return wordList;
	}
	
	
	private  String loadJSONFromAsset(AssetManager assetManaget) {
		String json = null;
//		Log.d("MODEL", "READING MODEL");
		try {
			InputStream is = assetManaget.open("dictionary.json");
			int size = is.available();
			byte[] buffer = new byte[size];
			is.read(buffer);
			is.close();
			json = new String(buffer, "UTF-8");

		} catch (IOException ex) {
			ex.printStackTrace();
			return null;
		}
		return json;
	}
	
	public  ArrayList<WordVO> getWordList() {
		return wordList;
	}
}
