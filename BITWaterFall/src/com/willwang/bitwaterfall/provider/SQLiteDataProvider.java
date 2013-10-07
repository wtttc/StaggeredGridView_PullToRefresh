package com.willwang.bitwaterfall.provider;

import android.content.Context;

import com.willwang.bitwaterfall.model.PostInformation;

public class SQLiteDataProvider {

	//private ActivityDB db = null;

	public SQLiteDataProvider(Context context) {
		//this.db = ((BaseApplication) (context.getApplicationContext())).getDB();
	}


	public PostInformation getPostInformation(int id) {
		return null;
        //return db.getPostInformation(id);
    }

    public void savePostInformation(int id, String json) {
        //db.saveUser(id, json);
    }

}
