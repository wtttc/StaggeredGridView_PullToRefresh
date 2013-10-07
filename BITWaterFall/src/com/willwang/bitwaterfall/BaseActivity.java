package com.willwang.bitwaterfall;

import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import org.apache.http.conn.ConnectTimeoutException;
import org.apache.http.conn.HttpHostConnectException;
import org.json.JSONException;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.actionbarsherlock.app.ActionBar;
import com.actionbarsherlock.app.SherlockFragmentActivity;
import com.willwang.bitwaterfall.R;
import com.willwang.bitwaterfall.provider.DataProvider;
import com.willwang.bitwaterfall.util.Utils;


public class BaseActivity extends SherlockFragmentActivity {

    protected BaseApplication mApp = null;
    protected DataProvider mProvider = null;
    protected ActionBar mActionBar = null;

    @Override
	public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mApp = (BaseApplication) getApplicationContext();
        mProvider = mApp.getProvider();

        mApp.addActivity(this);
    }

    @Override
	public void onDestroy() {
        mApp.removeActivity(this);
        super.onDestroy();
    }

    private final Handler exceptionHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Exception e = (Exception) msg.obj;
            handleException0(e);
        }

    };

    @SuppressLint("HandlerLeak")
    public void handleException(Exception exception) {
        exception.printStackTrace();
        Message msg = exceptionHandler.obtainMessage();
        msg.obj = exception;
        msg.sendToTarget();
    }

    protected void handleException0(Exception exception) {
        int err = 0;

        try {
            throw exception;
        } catch (UnknownHostException e) {
            err = R.string.error_host;
        } catch (HttpHostConnectException e) {
            err = R.string.error_host;
        } catch (ConnectTimeoutException e) {
            err = R.string.error_timeout;
        } catch (SocketTimeoutException e) {
            err = R.string.error_timeout;
        } catch (JSONException e) {
            err = R.string.error_json;
        } catch (Exception e) {
            err = R.string.error_unknow;
        }

        if (err != 0) {
            Utils.showToast(this, err);
        }
    }


}

