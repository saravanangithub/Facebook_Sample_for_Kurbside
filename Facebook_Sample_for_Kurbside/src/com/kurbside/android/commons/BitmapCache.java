package com.kurbside.android.commons;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import com.volley.android.toolbox.ImageLoader;

/**
 * Created by tmeng999 on 7/2/13.
 */
public class BitmapCache extends LruCache implements ImageLoader.ImageCache
{
  public BitmapCache(int maxSize) {
    super(maxSize);
  }


  @Override
  public Bitmap getBitmap(String url)
  {
    return (Bitmap)get(url);
  }


  @Override
  public void putBitmap(String url, Bitmap bitmap)
  {
    put(url, bitmap);
  }
}
