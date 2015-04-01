
package com.telstra.exercise;

import android.app.Application;

public class MainApplication extends Application {

	private static MainApplication mInstance;

	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();

		mInstance = this;
	}

	public static MainApplication getInstance() {
		return mInstance;
	}
}
