package com.word.chileanway.fragments;

import java.util.Locale;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.Fragment;
import android.support.v4.app.NavUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.word.chileanway.R;
import com.word.chileanway.model.WordVO;

/**
 * Detail Word Fragment 
 * @author Cesar Oyarzun
 *
 */
public class DetailWordsFragment extends Fragment implements TextToSpeech.OnInitListener{

	public static final String SELECTED_WORD = "selected_word";
	private TextToSpeech textToSpeech=null;
	private WordVO selectedWord=null;

	public static DetailWordsFragment newInstance(WordVO word) {
		Bundle args = new Bundle();
		args.putSerializable(SELECTED_WORD, word);
		DetailWordsFragment fragment = new DetailWordsFragment();
		fragment.setArguments(args);
		return fragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
		textToSpeech=new TextToSpeech(getActivity(), this);
	}

	@TargetApi(11)
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View inflate = inflater.inflate(R.layout.fragment_detail_word,container, false);
		
		selectedWord = (WordVO) getArguments().getSerializable(SELECTED_WORD);
		
		TextView textViewSpanish = (TextView) inflate.findViewById(R.id.spanishWord);
		textViewSpanish.setText(selectedWord.getSpanish());
		
		TextView textViewExample_es = (TextView) inflate.findViewById(R.id.example_es);
		textViewExample_es.setText(selectedWord.getExapmple_es());
		
		TextView textViewEnglish = (TextView) inflate.findViewById(R.id.englishWord);
		textViewEnglish.setText(selectedWord.getEnglish());
		
		TextView textViewExample_us = (TextView) inflate.findViewById(R.id.example_us);
		textViewExample_us.setText(selectedWord.getExapmple_us());

		//Play button
		ImageButton playButton = (ImageButton) inflate.findViewById(R.id.playWord);
		playButton.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				speakOut();
			}
		});
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// if (NavUtils.getParentActivityName(getActivity()) != null) {
			getActivity().getActionBar().setDisplayHomeAsUpEnabled(true);
		}

		return inflate;
	}

	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		super.onCreateOptionsMenu(menu, inflater);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case android.R.id.home:
			if (NavUtils.getParentActivityName(getActivity()) != null) {
				NavUtils.navigateUpFromSameTask(getActivity());
			}
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	@Override
	public void onInit(int status) {

		if (status == TextToSpeech.SUCCESS) {
			Locale locale=new Locale("es-CL");
          int result = textToSpeech.setLanguage(locale);
          if (TextToSpeech.LANG_MISSING_DATA==result || TextToSpeech.LANG_NOT_SUPPORTED==result){
				Log.e("error", "This Language is not supported");
			} 
		} else {
			Log.e("error", "Initilization Failed!");
		}
	}
	
	private void speakOut() {
        String text = selectedWord.getSpanish();
        textToSpeech.speak(text, TextToSpeech.QUEUE_FLUSH, null);
    }
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		if(textToSpeech!=null){
			textToSpeech.stop();
			textToSpeech.shutdown();
		}
		super.onDestroy();
	}
	
}
