package com.word.chileanway.fragments;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.SearchManager;
import android.app.SearchableInfo;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.ListFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.word.chileanway.DetailWordsPagerActivity;
import com.word.chileanway.R;
import com.word.chileanway.model.ChileanWayModel;
import com.word.chileanway.model.WordVO;

/**
 * List Words Fragment
 * 
 * @author Cesar Oyarzun
 * 
 */
@SuppressLint("NewApi")
public class ListWordsFragment extends ListFragment implements
		SearchView.OnQueryTextListener {
	private static final int REQUEST_CODE = 1234;
	private static final int RESULT_OK=-1;
	private ArrayAdapter<WordVO> wordAdapter=null;
	private SearchView searchView=null;
	private TextView emptyText;
	
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setHasOptionsMenu(true);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_main, container,
				false);
		emptyText = (TextView)rootView.findViewById(android.R.id.empty);
		
		wordAdapter = new ArrayAdapter<>(getActivity(),
				android.R.layout.simple_list_item_1, ChileanWayModel
						.getInstance().getWordList());
		setListAdapter(wordAdapter);
		
		return rootView;
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		WordVO word = (WordVO) (getListAdapter()).getItem(position);
		// Start PagerActivity
		Intent i = new Intent(getActivity(), DetailWordsPagerActivity.class);
		i.putExtra(DetailWordsFragment.SELECTED_WORD, word);
		startActivity(i);
		// clean search
		getListView().clearTextFilter();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getListView().setTextFilterEnabled(false);
		getListView().setEmptyView(emptyText);
	}

	@TargetApi(11)
	@Override
	public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
		// Inflate the menu items for use in the action bar
		super.onCreateOptionsMenu(menu, inflater);
		inflater.inflate(R.menu.main, menu);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
			// Pull out the SearchView
			MenuItem searchItem = menu.findItem(R.id.menu_item_search);
			searchView = (SearchView) searchItem.getActionView();
			searchView.setOnQueryTextListener(this);
			searchView.setQueryHint(getActivity().getApplicationContext().getString(R.string.search_hint));
			// Get the data from our searchable.xml as a SearchableInfo
			SearchManager searchManager = (SearchManager) getActivity()
					.getSystemService(Context.SEARCH_SERVICE);
			ComponentName name = getActivity().getComponentName();
			SearchableInfo searchInfo = searchManager.getSearchableInfo(name);
			searchView.setSearchableInfo(searchInfo);
		}
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		switch (item.getItemId()) {
		case R.id.menu_item_searchVoice:
			startVoiceRecognitionActivity();
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}

	/**
	 * Fire an intent to start the voice recognition activity.
	 */
	private void startVoiceRecognitionActivity()
	{
		Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
        RecognizerIntent.LANGUAGE_MODEL_WEB_SEARCH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_PREFERENCE, "es-ES");
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,"Please start speaking");
        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, "es-ES");
        startActivityForResult(intent, REQUEST_CODE);
	}
	
	@Override
	public boolean onQueryTextChange(String newText) {
		if (TextUtils.isEmpty(newText)) {
			wordAdapter.getFilter().filter("");
		} else {
			wordAdapter.getFilter().filter(newText);
		}
		return true;
	}

	@Override
	public boolean onQueryTextSubmit(String arg0) {
		return false;
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		 if (requestCode == REQUEST_CODE && resultCode == RESULT_OK)
		    {
		        // Populate the wordsList with the String values the recognition engine thought it heard
		        ArrayList<String> matches = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
		        wordAdapter.getFilter().filter(matches.get(0));
		        searchView.setQueryHint("text");
		        searchView.callOnClick();
		        
		    }else if(resultCode == RecognizerIntent.RESULT_AUDIO_ERROR){
		        showToastMessage("Audio Error");
		       }else if(resultCode == RecognizerIntent.RESULT_CLIENT_ERROR){
		        showToastMessage("Client Error");
		       }else if(resultCode == RecognizerIntent.RESULT_NETWORK_ERROR){
		        showToastMessage("Network Error");
		       }else if(resultCode == RecognizerIntent.RESULT_NO_MATCH){
		        showToastMessage("No Match");
		       }else if(resultCode == RecognizerIntent.RESULT_SERVER_ERROR){
		        showToastMessage("Server Error");
		       }
		super.onActivityResult(requestCode, resultCode, data);
	}
	
	/**
	 * Helper method to show the toast message
	 **/
	 void showToastMessage(String message){
	  Toast.makeText(this.getActivity(), message, Toast.LENGTH_SHORT).show();
	 }

	

}
