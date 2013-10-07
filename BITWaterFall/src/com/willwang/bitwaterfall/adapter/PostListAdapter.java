package com.willwang.bitwaterfall.adapter;

import java.util.List;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.willwang.bitwaterfall.BaseApplication;
import com.willwang.bitwaterfall.R;
import com.willwang.bitwaterfall.model.PostInformation;
import com.willwang.bitwaterfall.util.Utils;

public class PostListAdapter extends BaseAdapter {

	private Activity activity = null;
	private List<PostInformation> data = null;
	private BaseApplication mApp = null;

	public PostListAdapter(Activity activity, List<PostInformation> data) {
		super();
		this.activity = activity;
		this.data = data;
		this.mApp = (BaseApplication) (activity.getApplicationContext());
	}

	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return data.get(position).getPostId();
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// convertView缓存
		ViewHolder viewHolder = null;
		if (convertView == null) {
			convertView = LayoutInflater.from(activity).inflate(R.layout.row_staggered, null);
			viewHolder = new ViewHolder();
			viewHolder.ivPost = (ImageView) convertView.findViewById(R.id.iv_post);
			viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.tv_title);
			viewHolder.tvTime = (TextView) convertView.findViewById(R.id.tv_time);
			viewHolder.tvLocation = (TextView) convertView.findViewById(R.id.tv_location);
			viewHolder.tvHost = (TextView) convertView.findViewById(R.id.tv_host);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		// TODO
		final PostInformation item = data.get(position);
		//设置view的大小，因为AsynctaskImage和view的自动销毁会产生位置计算的问题，所以一定要固定view的高度再加载图片
		int tempPostHeight = item.getPostHeight()* Utils.getDensityScale(mApp);
		viewHolder.ivPost.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, tempPostHeight));

		// 根据position,将对应的内容复制设置属性
		String url = item.getPostImageUri();
		// ImageSize minImageSize = new ImageSize();
		ImageLoader.getInstance().displayImage(url, viewHolder.ivPost, mApp.getdisplayImageOptions());

		viewHolder.tvTime.setText(item.getPostTimestamp().toString());
		viewHolder.tvLocation.setText(item.getPostLocation());
		viewHolder.tvHost.setText(item.getPostHost());
		viewHolder.tvTitle.setText(item.getPostTitle());
		return convertView;
	}

	private class ViewHolder {
		ImageView ivPost;
		TextView tvTitle;
		TextView tvTime;
		TextView tvLocation;
		TextView tvHost;
	}

}
