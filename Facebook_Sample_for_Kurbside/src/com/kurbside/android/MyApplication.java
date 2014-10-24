package com.kurbside.android;

import android.app.Application;
import android.content.Context;

/**
 * Created by tmeng999 on 7/12/13.
 */
public class MyApplication extends Application
{
  private static Context _context;

  @Override
  public void onCreate()
  {
    super.onCreate();

    _context = getApplicationContext();
      
  }

  public static Context getContext()
  {
    return _context;
  }
}
