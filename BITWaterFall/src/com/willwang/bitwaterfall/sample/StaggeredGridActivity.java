package com.willwang.bitwaterfall.sample;

import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.View;
import android.widget.Toast;

import com.actionbarsherlock.view.Menu;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.Mode;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener2;
import com.handmark.pulltorefresh.library.PullToRefreshStaggeredGridView;
import com.origamilabs.library.views.StaggeredGridView;
import com.willwang.bitwaterfall.BaseActivity;
import com.willwang.bitwaterfall.BaseApplication;
import com.willwang.bitwaterfall.R;
import com.willwang.bitwaterfall.adapter.PostListAdapter;
import com.willwang.bitwaterfall.model.PostInformation;

public final class StaggeredGridActivity extends BaseActivity {

	private PullToRefreshStaggeredGridView mPullRefreshStaggeredGridView;
	private StaggeredGridView mStaggeredGridView;
	private PostListAdapter mAdapter;
	private List<PostInformation> mData = null;

	private PostInformation tempPostInformation = null;

	private BaseApplication mApp = null;
	// Save ListView state
	private Parcelable state = null;

	// private DataProvider mProvider = null;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_ptr_staggered_grid);

		mApp = (BaseApplication) getApplicationContext();
		mProvider = mApp.getProvider();

		mData = new LinkedList<PostInformation>();

		addList(0);

		initUI();
	}

	public void initUI() {

		mPullRefreshStaggeredGridView = (PullToRefreshStaggeredGridView) findViewById(R.id.pull_refresh_staggered_grid);
		mPullRefreshStaggeredGridView.setMode(Mode.BOTH);

		mStaggeredGridView = mPullRefreshStaggeredGridView.getRefreshableView();
		// Set a listener to be invoked when the list should be refreshed.
		mPullRefreshStaggeredGridView.setOnRefreshListener(new OnRefreshListener2<StaggeredGridView>() {

			@Override
			public void onPullDownToRefresh(PullToRefreshBase<StaggeredGridView> refreshView) {
				Toast.makeText(StaggeredGridActivity.this, "Pull Down!", Toast.LENGTH_SHORT).show();
				new RefreshTask().execute();
			}

			@Override
			public void onPullUpToRefresh(PullToRefreshBase<StaggeredGridView> refreshView) {
				state = mPullRefreshStaggeredGridView.getRefreshableView().onSaveInstanceState();
				Toast.makeText(StaggeredGridActivity.this, "Pull Up!", Toast.LENGTH_SHORT).show();
				new GetmoreTask().execute();
			}

		});

		mStaggeredGridView = mPullRefreshStaggeredGridView.getRefreshableView();

		mAdapter = new PostListAdapter(this, mData);
		mStaggeredGridView.setAdapter(mAdapter);

		mStaggeredGridView.setOnItemClickListener(new StaggeredGridView.OnItemClickListener() {

			@Override
			public void onItemClick(StaggeredGridView parent, View view, int position, long id) {
				int index = position;
				if (index >= 0 && index < mData.size()) {

				}

			}
		});

		mStaggeredGridView.setAdapter(mAdapter);

	}

	private class RefreshTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
			}
			return urls;
		}

		@Override
		protected void onPostExecute(String[] result) {

			mAdapter.notifyDataSetChanged();
			mPullRefreshStaggeredGridView.onRefreshComplete();

			super.onPostExecute(result);
		}

	}

	private int i = 1;

	private class GetmoreTask extends AsyncTask<Void, Void, String[]> {

		@Override
		protected String[] doInBackground(Void... params) {
			// Simulates a background job.
			try {
				Thread.sleep(2000);
			} catch (InterruptedException e) {
			}

			addList(urls.length * i);
			i++;

			//mPullRefreshStaggeredGridView.getRefreshableView().onRestoreInstanceState(state);
			// onRefreshComplete hase to be call or the pull-up view can not
			// disappear

			return urls;


		}

		@Override
		protected void onPostExecute(String[] result) {
			synchronized (mPullRefreshStaggeredGridView) {

				mAdapter.notifyDataSetChanged();
				mPullRefreshStaggeredGridView.onRefreshComplete();

				mPullRefreshStaggeredGridView.getRefreshableView().onRestoreInstanceState(state);

				// restore again to retain the view postion
				// mPullRefreshStaggeredGridView.getRefreshableView().onRestoreInstanceState(state);

			}

			super.onPostExecute(result);
		}

	}

	private void addList(int t) {
		// 暂时模拟数据接口的初始化
		Random random = new Random();
		for (int i = t; i < testurls.length + t; i++) {
			tempPostInformation = new PostInformation();

			tempPostInformation.setPostHost("风信子俱乐部");
			tempPostInformation.setPostLocation("食堂对面");

			StringBuilder builder = new StringBuilder();
			builder.append("Hello!![");
			builder.append(i);
			builder.append("] ");

			char[] chars = new char[random.nextInt(20)];
			// if(i%2==0){
			// chars = new char[2];
			// }else if(i%3==0){
			// chars = new char[12];
			// }else if(i%5==0){
			// chars = new char[20];
			// }else if (i%7==0) {
			// chars = new char[35];
			// }else{
			// chars = new char[40];
			// }

			Arrays.fill(chars, '啊');
			builder.append(chars);
			tempPostInformation.setPostHeight(height[i - t]);
			tempPostInformation.setPostTitle("活动Test" + builder.toString());
			tempPostInformation.setPostId(i);
			Timestamp timestamp = new Timestamp(new Date().getTime());
			tempPostInformation.setPostTimestamp(timestamp);
			tempPostInformation.setPostImageUri(testurls[i - t]);
			mData.add(tempPostInformation);
		}
	}

	// List<PostInformation> ps = gson.fromJson(str, new
	// TypeToken<List<PostInformation>>(){}.getType());
	// for(int i = 0; i < ps.size() ; i++)
	// {
	// PostInformation p = ps.get(i);
	// }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		getSupportMenuInflater().inflate(R.menu.activity_main, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(com.actionbarsherlock.view.MenuItem item) {
		mPullRefreshStaggeredGridView.getRefreshableView().setSelectionToTop();
		return super.onOptionsItemSelected(item);
	}

	private final String urls[] = { "http://farm7.staticflickr.com/6101/6853156632_6374976d38_c.jpg", "http://farm8.staticflickr.com/7232/6913504132_a0fce67a0e_c.jpg",
			"http://farm5.staticflickr.com/4133/5096108108_df62764fcc_b.jpg", "http://farm5.staticflickr.com/4074/4789681330_2e30dfcacb_b.jpg",
			"http://farm9.staticflickr.com/8208/8219397252_a04e2184b2.jpg", "http://farm9.staticflickr.com/8483/8218023445_02037c8fda.jpg",
			"http://farm9.staticflickr.com/8335/8144074340_38a4c622ab.jpg", "http://farm9.staticflickr.com/8060/8173387478_a117990661.jpg",
			"http://farm9.staticflickr.com/8056/8144042175_28c3564cd3.jpg", "http://farm9.staticflickr.com/8183/8088373701_c9281fc202.jpg",
			"http://farm9.staticflickr.com/8185/8081514424_270630b7a5.jpg", "http://farm9.staticflickr.com/8462/8005636463_0cb4ea6be2.jpg",
			"http://farm9.staticflickr.com/8306/7987149886_6535bf7055.jpg", "http://farm9.staticflickr.com/8444/7947923460_18ffdce3a5.jpg",
			"http://farm9.staticflickr.com/8182/7941954368_3c88ba4a28.jpg", "http://farm9.staticflickr.com/8304/7832284992_244762c43d.jpg",
			"http://farm9.staticflickr.com/8163/7709112696_3c7149a90a.jpg", "http://farm8.staticflickr.com/7127/7675112872_e92b1dbe35.jpg",
			"http://farm8.staticflickr.com/7111/7429651528_a23ebb0b8c.jpg", "http://farm9.staticflickr.com/8288/7525381378_aa2917fa0e.jpg",
			"http://farm6.staticflickr.com/5336/7384863678_5ef87814fe.jpg", "http://farm8.staticflickr.com/7102/7179457127_36e1cbaab7.jpg",
			"http://farm8.staticflickr.com/7086/7238812536_1334d78c05.jpg", "http://farm8.staticflickr.com/7243/7193236466_33a37765a4.jpg",

			"http://farm8.staticflickr.com/7251/7059629417_e0e96a4c46.jpg", "http://farm8.staticflickr.com/7084/6885444694_6272874cfc.jpg" };



	private final String testurls[] = {
		    "http://img4.duitang.com/uploads/item/201210/16/20121016204538_HSSUE.thumb.200_0.jpeg",
		    "http://img4.duitang.com/uploads/item/201301/29/20130129201600_HVkxS.thumb.200_0.png",
		    "http://img4.duitang.com/uploads/item/201205/01/20120501082510_vaBXQ.thumb.200_0.jpeg",
		    "http://img4.duitang.com/uploads/item/201205/01/20120501082502_SJQd2.thumb.200_0.jpeg",
		    "http://img4.duitang.com/uploads/item/201204/30/20120430143546_KiVfZ.thumb.200_0.jpeg",
		    "http://img4.duitang.com/uploads/item/201210/27/20121027095723_zySLs.thumb.200_0.jpeg",
		    "http://img4.duitang.com/uploads/item/201204/30/20120430143535_EJPTd.thumb.200_0.jpeg",
		    "http://img4.duitang.com/uploads/item/201301/26/20130126185707_FrRhW.thumb.200_0.jpeg",
		    "http://img4.duitang.com/uploads/item/201302/04/20130204115138_dmiaP.thumb.200_0.jpeg",
		    "http://img4.duitang.com/uploads/item/201302/12/20130212204207_ZfyFt.thumb.200_0.jpeg",
		    "http://img4.duitang.com/uploads/item/201204/30/20120430143607_jSZ8R.thumb.200_0.jpeg",
		    "http://img4.duitang.com/uploads/item/201203/28/20120328195431_EJed5.thumb.200_0.jpeg",
		    "http://img4.duitang.com/uploads/item/201301/01/20130101120529_VjRd2.thumb.200_0.jpeg",
		    "http://img4.duitang.com/uploads/item/201211/06/20121106182637_NNWsQ.thumb.200_0.jpeg",
		    "http://img4.duitang.com/uploads/item/201211/06/20121106182503_KUtxF.thumb.200_0.jpeg",
		    "http://img4.duitang.com/uploads/item/201207/17/20120717153000_SACwG.thumb.200_0.jpeg",
		    "http://img4.duitang.com/uploads/item/201208/07/20120807180455_GuksP.thumb.200_0.jpeg",
		    "http://img4.duitang.com/uploads/item/201202/13/20120213114146_EUjA8.thumb.200_0.jpg",
		    "http://img4.duitang.com/uploads/item/201212/13/20121213204108_VePd4.thumb.200_0.jpeg",
		    "http://img4.duitang.com/uploads/item/201206/06/20120606022446_SVMuV.thumb.200_0.jpeg",
		    "http://img4.duitang.com/uploads/item/201205/06/20120506225354_ZZvWe.thumb.200_0.jpeg",
		    "http://img4.duitang.com/uploads/item/201301/18/20130118112147_5Rd4h.thumb.200_0.jpeg",
		    "http://img4.duitang.com/uploads/item/201301/18/20130118203846_43Pva.thumb.200_0.jpeg",
		    "http://img4.duitang.com/uploads/item/201208/01/20120801125858_4TCk5.thumb.200_0.jpeg"
		};
		private final int height[] = {
		    246,
		    199,
		    266,
		    266,
		    359,
		    240,
		    332,
		    282,
		    277,
		    266,
		    266,
		    306,
		    300,
		    284,
		    250,
		    1004,
		    282,
		    287,
		    1263,
		    1349,
		    1484,
		    274,
		    268,
		    282
		};

}
