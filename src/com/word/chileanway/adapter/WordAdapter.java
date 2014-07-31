package com.word.chileanway.adapter;

import java.util.ArrayList;

import android.app.Activity;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.word.chileanway.R;
import com.word.chileanway.model.WordVO;
/**
 * Word adapter list
 * @author Cesar Oyarzun
 *
 */
public class WordAdapter extends ArrayAdapter<WordVO> {

	private Activity context=null;
	private ArrayList<WordVO> items=null;
	
	public WordAdapter(Activity context, int resource,ArrayList<WordVO> items) {
		super(context, R.layout.list_row,items);
		this.context=context;
		this.items=items;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LayoutInflater layoutInflater = context.getLayoutInflater();
		View rowView = layoutInflater.inflate(R.layout.list_row, null,true);
		TextView txtTitle = (TextView) rowView.findViewById(R.id.name);
		txtTitle.setText(items.get(position).getSpanish());
		return rowView;
	}
	@Override
	public int getCount() {
		return this.items.size();
	}
}
