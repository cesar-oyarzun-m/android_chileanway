package com.word.chileanway.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.word.chileanway.R;
import com.word.chileanway.model.WordVO;

/**
 * Word Adapter for list View
 * 
 * @author Cesar Oyarzun
 * 
 */

public class WordAdapter extends ArrayAdapter<WordVO> implements Filterable {

	private List<WordVO> mObjects;
	private final Object mLock = new Object();
	private Context mContext;

	// A copy of the original mObjects array, initialized from and then used
	// instead as soon as
	// the mFilter ArrayFilter is used. mObjects will then only contain the
	// filtered values.
	private ArrayList<WordVO> mOriginalValues;
	private ArrayFilter mFilter;
	private LayoutInflater mInflater;

	static class ViewHolder {
		TextView txtTitle;
	}

	public WordAdapter(Context context, int resource, List<WordVO> objects) {
		super(context, resource, objects);
		init(context, resource, objects);
	}

	private void init(Context context, int resource, List<WordVO> objects) {
		mContext = context;
		mInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		// mInflater.setFilter(getFilter());
		mObjects = objects;
	}

	/**
	 * Returns the context associated with this array adapter. The context is
	 * used to create views from the resource passed to the constructor.
	 * 
	 * @return The Context associated with this adapter.
	 */
	@Override
	public Context getContext() {
		return mContext;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getCount() {
		return mObjects.size();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public WordVO getItem(int position) {
		return mObjects.get(position);
	}

	/**
	 * Returns the position of the specified item in the array.
	 * 
	 * @param item
	 *            The item to retrieve the position of.
	 * 
	 * @return The position of the specified item.
	 */
	@Override
	public int getPosition(WordVO item) {
		return mObjects.indexOf(item);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		ViewHolder holder;
		if (convertView == null) {
			convertView = mInflater.inflate(R.layout.list_row, parent, false);
			holder = new ViewHolder();
			holder.txtTitle = (TextView) convertView.findViewById(R.id.name);
			convertView.setTag(holder);
		} else {
			holder = (ViewHolder) convertView.getTag();
		}
		holder.txtTitle.setText(mObjects.get(position).getSpanish());
		return convertView;
	}

	@Override
	public Filter getFilter() {
		if (mFilter == null) {
			mFilter = new ArrayFilter();
		}
		return mFilter;
	}

	/**
	 * <p>
	 * An array filter constrains the content of the array adapter with a
	 * prefix. Each item that does not start with the supplied prefix is removed
	 * from the list.
	 * </p>
	 */
	private class ArrayFilter extends Filter {
		@Override
		protected FilterResults performFiltering(CharSequence prefix) {
			FilterResults results = new FilterResults();

			if (mOriginalValues == null) {
				synchronized (mLock) {
					mOriginalValues = new ArrayList<WordVO>(mObjects);
				}
			}

			if (prefix == null || prefix.length() == 0) {
				ArrayList<WordVO> list;
				synchronized (mLock) {
					list = new ArrayList<WordVO>(mOriginalValues);
				}
				results.values = list;
				results.count = list.size();
			} else {
				String prefixString = prefix.toString();

				ArrayList<WordVO> values;
				synchronized (mLock) {
					values = new ArrayList<WordVO>(mOriginalValues);
				}

				final int count = values.size();
				final ArrayList<WordVO> newValues = new ArrayList<WordVO>();

				for (int i = 0; i < count; i++) {
					final WordVO value = values.get(i);
					final String valueText = value.toString();

					// First match against the whole, non-splitted value
					if (valueText.startsWith(prefixString)) {
						newValues.add(value);
					} else {
						final String[] words = valueText.split(" ");
						final int wordCount = words.length;

						// Start at index 0, in case valueText starts with
						// space(s)
						for (int k = 0; k < wordCount; k++) {
							if (words[k].startsWith(prefixString)) {
								newValues.add(value);
								break;
							}
						}
					}
				}

				results.values = newValues;
				results.count = newValues.size();
			}

			return results;
		}

		@SuppressWarnings("unchecked")
		@Override
		protected void publishResults(CharSequence constraint, FilterResults results) {
			// noinspection unchecked
			mObjects = (List<WordVO>) results.values;
			if (results.count > 0) {
				notifyDataSetChanged();
			} else {
				notifyDataSetInvalidated();
			}
		}
	}
}
