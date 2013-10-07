package com.willwang.bitwaterfall.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.willwang.bitwaterfall.R;


public class Utils {

    public static void showToast(Context context, int resid) {
        showToast(context, context.getResources().getString(resid));
    }

    public static void showToast(Context context, int resid, int duration) {
        showToast(context, context.getResources().getString(resid), duration);
    }

    public static void showToast(Context context, String msg) {
        showToast(context, msg, Toast.LENGTH_SHORT);
    }

    public static void showToast(Context context, String msg, int duration) {
        View toastLayout = LayoutInflater.from(context).inflate(R.layout.toast_layout, null);
        TextView tvToastMsg = (TextView) toastLayout.findViewById(R.id.text_toast);
        tvToastMsg.setText(msg);
        Toast toast = new Toast(context);
        // toast.setGravity(Gravity.CENTER, 0, 0);
        toast.setDuration(duration);
        toast.setView(toastLayout);
        toast.show();
    }

//    public static Dialog makeGoingDialog(Context context, int id) {
//        Dialog dialog = new LoadingDialog(context, R.style.LoadingDialogTheme, id);
//        return dialog;
//    }
//
//    public static Dialog makeGoingDialog(Context context, String msg) {
//        Dialog dialog = new LoadingDialog(context, R.style.LoadingDialogTheme, msg);
//        return dialog;
//    }

    /**
     * check if network is available
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            NetworkInfo[] infos = connectivity.getAllNetworkInfo();
            if (infos != null) {
                for (NetworkInfo info : infos) {
                    if (info.getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * convert dp to px
     */
    public static int dp2pixel(Context context, int dp) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dp * scale);
    }

    public static int getDensityScale(Context context){
    	final float scale = context.getResources().getDisplayMetrics().density;
    	final float width = context.getResources().getDisplayMetrics().widthPixels;

    	if(width>=800){
    		return 2;
    	}
    	if( scale>=2 ){
    		return 2;
    	}
    	return 1;
    }

}
