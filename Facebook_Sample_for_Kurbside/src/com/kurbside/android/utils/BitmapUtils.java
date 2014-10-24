package com.kurbside.android.utils;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.*;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.widget.ImageView;
import com.kurbside.android.commons.Constants;

import java.lang.ref.WeakReference;

/**
 * Created by tmeng999 on 6/25/13.
 */
public class BitmapUtils
{
  public enum ScalingLogic
  {
    FIT,
    CROP
  }


  public static void loadBitmap(Context context, byte[] data, ImageView imageView, String id)
  {
    if (cancelPotentialWork(id, imageView))
    {
      final BitmapWorkerThread task = new BitmapWorkerThread(id, data, imageView);
      final AsyncDrawable asyncDrawable = new AsyncDrawable(context.getResources(), null, task);
      imageView.setImageDrawable(asyncDrawable);
      task.start();
    }
  }


  public static void loadBitmap(Context context, String filePath, ImageView imageView)
  {
    final BitmapWorkerThread task = new BitmapWorkerThread(filePath, imageView);
    final AsyncDrawable asyncDrawable = new AsyncDrawable(context.getResources(), null, task);
    imageView.setImageDrawable(asyncDrawable);
    task.start();
  }


  public static Bitmap createScaledBitmap(Bitmap unscaledBitmap, int dstWidth, int dstHeight, ScalingLogic scalingLogic)
  {
    Rect srcRect = calculateSrcRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight(), dstWidth, dstHeight, scalingLogic);
    Rect dstRect = calculateDstRect(unscaledBitmap.getWidth(), unscaledBitmap.getHeight(), dstWidth, dstHeight, scalingLogic);
    WeakReference<Bitmap> scaledBitmap = new WeakReference<Bitmap>(Bitmap.createBitmap(dstRect.width(), dstRect.height(), Bitmap.Config.ARGB_8888));
    WeakReference<Canvas> canvas = new WeakReference<Canvas>(new Canvas(scaledBitmap.get()));
    canvas.get().drawBitmap(unscaledBitmap, srcRect, dstRect, new Paint(Paint.FILTER_BITMAP_FLAG));
    return scaledBitmap.get();
  }


  private static boolean cancelPotentialWork(String id, ImageView imageView)
  {
    BitmapWorkerThread bitmapWorkerTask = getBitmapWorkerTask(imageView);

    if (bitmapWorkerTask != null)
    {
      final String taskId = bitmapWorkerTask._id;
      if (!taskId.equals(id))
      {
        // Cancel previous task
        Log.e(Constants.LOG_TAG, "Will Cancel");
        bitmapWorkerTask.interrupt();
      }
      else
      {
        // The same work is already in progress
        return false;
      }
    }
    // No task associated with the ImageView, or an existing task was cancelled
    return true;
  }


  private static BitmapWorkerThread getBitmapWorkerTask(ImageView imageView)
  {
    if (imageView != null)
    {
      Drawable drawable = imageView.getDrawable();
      if (drawable instanceof AsyncDrawable)
      {
        AsyncDrawable asyncDrawable = (AsyncDrawable) drawable;
        return asyncDrawable.getBitmapWorkerTask();
      }
    }
    return null;
  }


  private static Bitmap decodeBitmapFromByteArray(byte[] data, int reqWidth, int reqHeight)
  {
    // First decode with inJustDecodeBounds=true to check dimensions
    final BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeByteArray(data, 0, data.length, options);

    // Calculate inSampleSize
    options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

    // Decode bitmap with inSampleSize set
    options.inJustDecodeBounds = false;
    return BitmapFactory.decodeByteArray(data, 0, data.length, options);
  }


  private static Bitmap decodeBitmapFromFilePath(String filePath, int width, int height)
  {
    // First decode with inJustDecodeBounds=true to check dimensions
    final BitmapFactory.Options options = new BitmapFactory.Options();
    options.inJustDecodeBounds = true;
    BitmapFactory.decodeFile(filePath, options);

    // Calculate inSampleSize
    options.inSampleSize = calculateInSampleSize(options, width, height);

    // Decode bitmap with inSampleSize set
    options.inJustDecodeBounds = false;
    return BitmapFactory.decodeFile(filePath, options);
  }


  private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight)
  {
    // Raw height and width of image
    final int height = options.outHeight;
    final int width = options.outWidth;
    int inSampleSize = 1;

    if (height > reqHeight || width > reqWidth)
    {

      // Calculate ratios of height and width to requested height and width
      final int heightRatio = Math.round((float) height / (float) reqHeight);
      final int widthRatio = Math.round((float) width / (float) reqWidth);

      // Choose the smallest ratio as inSampleSize value, this will guarantee
      // a final image with both dimensions larger than or equal to the
      // requested height and width.
      inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
    }

    return inSampleSize;
  }


  private static Rect calculateSrcRect(int srcWidth, int srcHeight, int dstWidth, int dstHeight, ScalingLogic scalingLogic)
  {
    if (scalingLogic == ScalingLogic.CROP)
    {
      final float srcAspect = (float)srcWidth / (float)srcHeight;
      final float dstAspect = (float)dstWidth / (float)dstHeight;
      if (srcAspect > dstAspect)
      {
        final int srcRectWidth = (int)(srcHeight * dstAspect);
        final int srcRectLeft = (srcWidth - srcRectWidth) / 2;
        return new Rect(srcRectLeft, 0, srcRectLeft + srcRectWidth, srcHeight);
      }
      else
      {
        final int srcRectHeight = (int)(srcWidth / dstAspect);
        final int scrRectTop = (int)(srcHeight - srcRectHeight) / 2;
        return new Rect(0, scrRectTop, srcWidth, scrRectTop + srcRectHeight);
      }
    }
    else
    {
      return new Rect(0, 0, srcWidth, srcHeight);
    }
  }


  private static Rect calculateDstRect(int srcWidth, int srcHeight, int dstWidth, int dstHeight, ScalingLogic scalingLogic)
  {
    if (scalingLogic == ScalingLogic.FIT)
    {
      final float srcAspect = (float)srcWidth / (float)srcHeight;
      final float dstAspect = (float)dstWidth / (float)dstHeight;
      if (srcAspect > dstAspect)
      {
        return new Rect(0, 0, dstWidth, (int)(dstWidth / srcAspect));
      }
      else
      {
        return new Rect(0, 0, (int)(dstHeight * srcAspect), dstHeight);
      }
    }
    else
    {
      return new Rect(0, 0, dstWidth, dstHeight);
    }
  }


  private static int calculateSampleSize(int srcWidth, int srcHeight, int dstWidth, int dstHeight, ScalingLogic scalingLogic)
  {
    if (scalingLogic == ScalingLogic.FIT)
    {
      final float srcAspect = (float)srcWidth / (float)srcHeight;
      final float dstAspect = (float)dstWidth / (float)dstHeight;
      if (srcAspect > dstAspect)
      {
        return srcWidth / dstWidth;
      }
      else
      {
        return srcHeight / dstHeight;
      }
    }
    else
    {
      final float srcAspect = (float)srcWidth / (float)srcHeight;
      final float dstAspect = (float)dstWidth / (float)dstHeight;
      if (srcAspect > dstAspect)
      {
        return srcHeight / dstHeight;
      }
      else
      {
        return srcWidth / dstWidth;
      }
    }
  }


  private static class AsyncDrawable extends BitmapDrawable
  {
    private BitmapWorkerThread _bitmapWorkerThread;

    public AsyncDrawable(Resources res, Bitmap bitmap, BitmapWorkerThread bitmapWorkerTask)
    {
      super(res, bitmap);
      _bitmapWorkerThread = bitmapWorkerTask;
    }


    public BitmapWorkerThread getBitmapWorkerTask()
    {
      return _bitmapWorkerThread;
    }
  }


  private static class BitmapWorkerThread extends Thread
  {
    private final String _id;
    private final WeakReference<ImageView> _imageViewReference;
    private final byte[] _data;
    private final String _filePath;
    private Handler _handler;


    public BitmapWorkerThread(String id, byte[] data, ImageView imageView)
    {
      _id = id;
      _data = data;
      _filePath = "";
      _imageViewReference = new WeakReference<ImageView>(imageView);
      _handler = new Handler(Looper.getMainLooper());
    }


    public BitmapWorkerThread(String filePath, ImageView imageView)
    {
      _id = "";
      _data = null;
      _filePath = filePath;
      _imageViewReference = new WeakReference<ImageView>(imageView);
      _handler = new Handler(Looper.getMainLooper());
    }


    @Override
    public void run()
    {
      super.run();
      if(isInterrupted())
      {
        Log.e(Constants.LOG_TAG, "INTERRUPTED");
        return;
      }

      WeakReference<Bitmap> bitmap;
      if(_data != null)
      {
        bitmap = new WeakReference<Bitmap>(decodeBitmapFromByteArray(_data, _imageViewReference.get().getWidth(), _imageViewReference.get().getHeight()));
      }
      else
      {
        bitmap = new WeakReference<Bitmap>(decodeBitmapFromFilePath(_filePath, _imageViewReference.get().getWidth(), _imageViewReference.get().getHeight()));
      }
      final WeakReference<Bitmap> scaledBitmap = new WeakReference<Bitmap>(createScaledBitmap(bitmap.get(), _imageViewReference.get().getWidth(), _imageViewReference.get().getHeight(), ScalingLogic.CROP));

      _handler.post(new Runnable()
      {
        @Override
        public void run()
        {
          if(isInterrupted())
          {
            Log.e(Constants.LOG_TAG, "INTERRUPTED");
            return;
          }

          ImageView imageView = _imageViewReference.get();
          imageView.setImageBitmap(scaledBitmap.get());
        }
      });
    }
  }
}
