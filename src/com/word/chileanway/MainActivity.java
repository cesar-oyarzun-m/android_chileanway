package com.word.chileanway;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.word.chileanway.fragments.ListWordsFragment;
import com.word.chileanway.model.ChileanWayModel;

/**
 * Main Fragment Activity
 * @author Cesar Oyarzun
 * 
 */
public class MainActivity extends ActionBarActivity{

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			if (ChileanWayModel.getInstance().getWordList() == null) {
				ChileanWayModel.getInstance().loadJsonFile(
						getApplicationContext(), getAssets());
			}
			getSupportFragmentManager().beginTransaction()
					.add(R.id.container, new ListWordsFragment()).commit();
		}
	}
}
