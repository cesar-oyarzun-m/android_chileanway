package com.word.chileanway;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;

import com.word.chileanway.fragments.DetailWordsFragment;
import com.word.chileanway.model.ChileanWayModel;
import com.word.chileanway.model.WordVO;

/**
 * Detail Word Pager Activity to swipe on the list
 * 
 * @author Cesar Oyarzun
 * 
 */
public class DetailWordsPagerActivity extends FragmentActivity  {
	private ViewPager mViewPager;
	private ArrayList<WordVO> wordsModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mViewPager = new ViewPager(this);
		mViewPager.setId(R.id.viewPager);
		setContentView(mViewPager);
		wordsModel = ChileanWayModel.getInstance().getWordList();
		FragmentManager fm = getSupportFragmentManager();
		mViewPager.setAdapter(new FragmentStatePagerAdapter(fm) {
			@Override
			public int getCount() {
				return wordsModel.size();
			}

			@Override
			public Fragment getItem(int pos) {
				WordVO word = wordsModel.get(pos);
				return DetailWordsFragment.newInstance(word);
			}
		});

		WordVO word = (WordVO) getIntent().getSerializableExtra(
				DetailWordsFragment.SELECTED_WORD);
		for (int i = 0; i < wordsModel.size(); i++) {
			if (wordsModel.get(i).getSpanish().equals(word.getSpanish())) {
				mViewPager.setCurrentItem(i);
				break;
			}
		}

		mViewPager
				.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
					public void onPageScrollStateChanged(int state) {
					}

					public void onPageScrolled(int pos, float posOffset,
							int posOffsetPixels) {
					}

					public void onPageSelected(int pos) {
						WordVO word = wordsModel.get(pos);
						if (word.getSpanish() != null) {
							setTitle(word.getSpanish());
						}
					}
				});
	}


}
