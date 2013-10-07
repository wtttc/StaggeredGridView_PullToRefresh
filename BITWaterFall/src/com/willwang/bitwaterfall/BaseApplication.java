package com.willwang.bitwaterfall;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.os.Bundle;

import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.FadeInBitmapDisplayer;
import com.nostra13.universalimageloader.utils.StorageUtils;
import com.willwang.bitwaterfall.database.ActivityDB;
import com.willwang.bitwaterfall.provider.DataProvider;
import com.willwang.bitwaterfall.util.Constants;
import com.willwang.bitwaterfall.util.Logger;
import com.willwang.bitwaterfall.util.StrUtils;

public class BaseApplication extends Application {

	private final List<Activity> mActivityList = new ArrayList<Activity>();

	private SharedPreferences mPrefs = null;
	private DataProvider mProvider = null;
	private ActivityDB mDb = null;

	private Context context = null;
	private File imageLoaderCacheDir = null;
	public ImageLoaderConfiguration imageLoaderConfiguration = null;
	private DisplayImageOptions displayImageOptions = null;

	public SharedPreferences getPreferences() {
		if (mPrefs == null) {
			mPrefs = getSharedPreferences(Constants.SP_NAME, MODE_PRIVATE);
		}
		return mPrefs;
	}

	public File getimageLoaderCacheDir() {
		return imageLoaderCacheDir;
	}

	public void setimageLoaderCacheDir(File mImageLoaderCacheDir) {
		this.imageLoaderCacheDir = mImageLoaderCacheDir;
	}

	public DisplayImageOptions getdisplayImageOptions() {
		return displayImageOptions;
	}

	public void setmDisplayImageOptions(DisplayImageOptions mDisplayImageOptions) {
		this.displayImageOptions = mDisplayImageOptions;
	}

	public DataProvider getProvider() {
		if (mProvider == null) {
			mProvider = DataProvider.getInstance(this);
		}
		return mProvider;
	}

	public ActivityDB getDB() {
		if (mDb == null) {
			mDb = ActivityDB.getInstance(this);
		}
		return mDb;
	}

	@Override
	public void onCreate() {
		super.onCreate();

		try {
			Bundle bundle = getPackageManager().getApplicationInfo(getPackageName(), PackageManager.GET_META_DATA).metaData;
			Logger.DEBUG = bundle.getBoolean("DBLOG");
		} catch (NameNotFoundException e) {
			e.printStackTrace();
		}

		imageLoaderCacheDir = StorageUtils.getOwnCacheDirectory(getApplicationContext(), "UniversalImageLoader/Cache");

		context = this.getApplicationContext();

		imageLoaderConfiguration = new ImageLoaderConfiguration.Builder(context)
				.threadPriority(Thread.NORM_PRIORITY - 1)
				.discCacheFileCount(200)
				.discCacheFileNameGenerator(new Md5FileNameGenerator())
				.tasksProcessingOrder(QueueProcessingType.FIFO)
				.writeDebugLogs() // Remove
				.build();

		displayImageOptions = new DisplayImageOptions.Builder().showStubImage(R.drawable.ic_stub).showImageForEmptyUri(R.drawable.ic_empty).showImageOnFail(R.drawable.ic_error)
				.resetViewBeforeLoading(false)
				.imageScaleType(ImageScaleType.IN_SAMPLE_POWER_OF_2)
				.displayer(new FadeInBitmapDisplayer(200))
				.cacheInMemory(true) // default
				.cacheOnDisc(true) // default
				.build();

		ImageLoader.getInstance().init(imageLoaderConfiguration); // Do it on
																	// Application
																	// start
	}

	@Override
	public void onTerminate() {
		super.onTerminate();

		// close database
		if (mDb != null) {
			mDb.close();
			mDb = null;
		}
	}

	public void addActivity(Activity act) {
		mActivityList.add(act);
	}

	public void removeActivity(Activity act) {
		if (mActivityList.size() > 0) {
			mActivityList.remove(act);
		}
	}

	public void exitAll() {
		if (mActivityList.size() > 0) {
			for (Activity activity : mActivityList) {
				if (activity != null) {
					activity.finish();
				}
			}
		}
	}

	public void setLastRefreshTime(long time) {
		SharedPreferences.Editor editor = getPreferences().edit();
		editor.putLong(Constants.LAST_REFRESH_TIME_BROWSE_ACTIVITY, time);
		editor.commit();
	}

	public String getLastRefreshTime() {
		long time = getPreferences().getLong(Constants.LAST_REFRESH_TIME_BROWSE_ACTIVITY, 0);
		if (time == 0) {
			return "";
		}
		return getString(R.string.last_refresh_time) + StrUtils.getTime(time);
	}

	public void setIsLoggedIn(boolean logged) {
		SharedPreferences.Editor editor = getPreferences().edit();
		editor.putBoolean(Constants.IS_LOGGED_IN, logged);
		editor.commit();
	}

	public boolean getIsLoggedIn() {
		return getPreferences().getBoolean(Constants.IS_LOGGED_IN, false);
	}

	public void setCurrentCityId(String id) {
		SharedPreferences.Editor editor = getPreferences().edit();
		editor.putString(Constants.CURRENT_CITY_ID, id);
		editor.commit();
	}

	public String getCurrentCityId() {
		return getPreferences().getString(Constants.CURRENT_CITY_ID, "");
	}

	public void setCurrentCityName(String name) {
		SharedPreferences.Editor editor = getPreferences().edit();
		editor.putString(Constants.CURRENT_CITY_NAME, name);
		editor.commit();
	}

	public String getCurrentCityName() {
		return getPreferences().getString(Constants.CURRENT_CITY_NAME, "");
	}

}