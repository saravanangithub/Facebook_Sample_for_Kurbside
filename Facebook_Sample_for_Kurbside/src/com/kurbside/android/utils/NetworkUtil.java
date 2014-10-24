package com.kurbside.android.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetworkUtil {

	public static int TYPE_WIFI=1;
	public static int TYPE_MOBILE=2;
	public static int TYPE_NOT_CONNECTED=3;
	
	public static int getConnectivityStatus(Context context)
	{
		ConnectivityManager connectivityManager=(ConnectivityManager)context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo activeNetwork=connectivityManager.getActiveNetworkInfo();
		if(activeNetwork!=null){
			if(activeNetwork.getType()==ConnectivityManager.TYPE_WIFI)
			     return TYPE_WIFI;
			     if(activeNetwork.getType()==ConnectivityManager.TYPE_MOBILE)
			    	 return TYPE_MOBILE;
			     
		}
			
		return TYPE_NOT_CONNECTED;
	} 
	
	public static String getConnectivityStatusString(Context context){
		
		int conn=NetworkUtil.getConnectivityStatus(context);
		String status=null;
		
		if(conn==NetworkUtil.TYPE_WIFI)
			status="Wifi Enabled";
		else if(conn==NetworkUtil.TYPE_MOBILE)
			status="Mobile data Enabled";
		else
			status="Not Connected to Internet";
				
		return status; 
	}
	
	public static boolean isConnectivityEnabled(Context context)
	{
		int conn=NetworkUtil.getConnectivityStatus(context);
		boolean isConnectivityEnabled=false;
		
		if(conn==NetworkUtil.TYPE_WIFI)
			isConnectivityEnabled=true;
		else if(conn==NetworkUtil.TYPE_MOBILE)
			isConnectivityEnabled=true;
		else
			isConnectivityEnabled=false;
				
		return isConnectivityEnabled;  
		
	}	

	public static boolean isMobileDataEnabled(Context context)
	{
		int conn=NetworkUtil.getConnectivityStatus(context);
		
		if(conn==NetworkUtil.TYPE_MOBILE)  
			return true;
		
		return false; 
	}
	
	public static boolean isWifiEnabled(Context context)
	{
		int conn=NetworkUtil.getConnectivityStatus(context);
		
		if(conn==NetworkUtil.TYPE_WIFI)  
			return true;
		
		return false; 
	}
}
