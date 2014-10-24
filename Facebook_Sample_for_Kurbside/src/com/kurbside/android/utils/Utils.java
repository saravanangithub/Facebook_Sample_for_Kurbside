package com.kurbside.android.utils;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class Utils {


	public static void longToast(final Activity activity, final String msg)
	{
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(activity, msg, Toast.LENGTH_LONG).show();
			}
		});
	}
	
	public static void longToast(final Context context, final String msg)
	{
		Toast.makeText(context, msg, Toast.LENGTH_LONG).show();
	}

	public static void shortToast(final Activity activity, final String msg)
	{
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				Toast.makeText(activity, msg, Toast.LENGTH_SHORT).show();
			}
		});
	}
	
	public static void finish(final Activity activity)
	{
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				activity.finish();
			}
		});
	}


}
