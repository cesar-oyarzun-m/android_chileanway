package com.word.chileanway.adapter;

import com.word.chileanway.model.WordVO;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
/**
 * Word adapter list
 * @author Cesar Oyarzun
 *
 */
public class WordAdapter extends ArrayAdapter<WordVO> {

	public WordAdapter(Context context, int resource) {
		super(context, resource);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		return super.getView(position, convertView, parent);
	}
}
