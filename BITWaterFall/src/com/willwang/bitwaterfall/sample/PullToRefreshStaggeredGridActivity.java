package com.willwang.bitwaterfall.sample;


import java.util.Arrays;
import java.util.LinkedList;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshStaggeredGridView;
import com.origamilabs.library.views.StaggeredGridView;
import com.willwang.bitwaterfall.BaseActivity;
import com.willwang.bitwaterfall.R;

public final class PullToRefreshStaggeredGridActivity extends BaseActivity {


	private LinkedList<String> mListItems;
	private PullToRefreshStaggeredGridView mPullRefreshStaggeredGridView;
	private StaggeredGridView mStaggeredGridView;
	private ArrayAdapter<String> mAdapter;

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ptr_staggered_grid);

		mPullRefreshStaggeredGridView = (PullToRefreshStaggeredGridView) findViewById(R.id.pull_refresh_staggered_grid);
		
		mPullRefreshStaggeredGridView.setMode(Mode.BOTH);    // mode refresh for top and bottom
		
		mStaggeredGridView = mPullRefreshStaggeredGridView.getRefreshableView();

		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshStaggeredGridView.setOnRefreshListener(new OnRefreshListener2<StaggeredGridView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<StaggeredGridView> refreshView) {
				Toast.makeText(PullToRefreshStaggeredGridActivity.this, "Pull Down!", Toast.LENGTH_SHORT).show();
				new GetDataTask().execute();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<StaggeredGridView> refreshView) {
				Toast.makeText(PullToRefreshStaggeredGridActivity.this, "Pull Up!", Toast.LENGTH_SHORT).show();
				new GetDataTask().execute();
			}

		});

		mListItems = new LinkedList<String>();
		
		mListItems.addAll(Arrays.asList(mStrings));
		mAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, mListItems);
		mStaggeredGridView.setAdapter(mAdapter);
	}

	private class GetDataTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}
			return mStrings;
		}

		@Override
		protected void onPostExecute(String[] result) {
			mListItems.addFirst("Added after refresh...");
			mListItems.addAll(Arrays.asList(result));
			mAdapter.notifyDataSetChanged();

			// Call onRefreshComplete when the list has been refreshed.
			mPullRefreshStaggeredGridView.onRefreshComplete();

			super.onPostExecute(result);
		}
	}


	private String[] mStrings = { "Abbaye de Belloc", "Abbaye du Mont des Cats", "Abertam", "Abondance", "Ackawi",
			"Acorn", "Adelost", "Affidelice au Chablis", "Afuega'l Pitu", "Airag", "Airedale", "Aisy Cendre",
			"Allgauer Emmentaler" };
}
