package com.word.chileanway;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

import com.word.chileanway.fragments.ListWordsFragment;
import com.word.chileanway.model.ChileanWayModel;

/**
 * Main Fragment Activity
 * @author Cesar Oyarzun
 * 
 */
public class MainActivity extends FragmentActivity{

	@TargetApi(Build.VERSION_CODES.HONEYCOMB)
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
