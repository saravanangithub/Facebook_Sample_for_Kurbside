package com.kurbside.android.commons;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import com.volley.android.toolbox.DiskBasedCache;
import com.volley.android.toolbox.ImageLoader;

import java.io.File;
import java.nio.ByteBuffer;

/**
 * Created by tmeng999 on 7/2/13.
 */
public  class DiskBitmapCache extends DiskBasedCache implements ImageLoader.ImageCache
{
  public DiskBitmapCache(File rootDirectory, int maxCacheSizeInBytes) {
    super(rootDirectory, maxCacheSizeInBytes);
  }

  public DiskBitmapCache(File cacheDir) {
    super(cacheDir);
  }

  public Bitmap getBitmap(String url) {
    final Entry requestedItem = get(url);

    if (requestedItem == null)
      return null;

    return BitmapFactory.decodeByteArray(requestedItem.data, 0, requestedItem.data.length);
  }

  public void putBitmap(String url, Bitmap bitmap) {
    final Entry entry = new Entry();

    ByteBuffer buffer = ByteBuffer.allocate(bitmap.getByteCount());
    bitmap.copyPixelsToBuffer(buffer);
    entry.data = buffer.array();

    put(url, entry);
  }
}
