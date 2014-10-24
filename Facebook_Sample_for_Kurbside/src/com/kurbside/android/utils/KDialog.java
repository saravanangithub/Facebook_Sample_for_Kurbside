package com.kurbside.android.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.view.View;
import com.kurbside.android.R;
import com.kurbside.android.activities.SplashLoginActivity;

public class KDialog
{
	private static Dialog _dialog;
	private static ProgressDialog _progressDialog;
	private static ProgressDialogBuilder _progressDialogbuilder;


	public static void launchLoginDialog(final Activity activity)
	{
		KDialog.AlertDialogBuilder builder = new KDialog.AlertDialogBuilder(activity);
		builder.setTitle(R.string.Account_Needed);
		builder.setMessage(R.string.Log_In_Continue);
		builder.setPositiveButton(R.string.Log_In_Sign_Up, new OnClickListener()
		{
			@Override
			public void onClick(DialogInterface dialogInterface, int i)
			{
				activity.finish();
			}
		});
		builder.setNegativeButton(R.string.Cancel, null);
		builder.show();
	}


	public static void showMessage(final Activity activity, final String msg)
	{
		activity.runOnUiThread(new Runnable()
		{

			@Override
			public void run()
			{
				KDialog.AlertDialogBuilder builder = new KDialog.AlertDialogBuilder(activity);
				builder.setTitle("Error");
				builder.setMessage(msg);
				builder.setNegativeButton("OK", null);
				builder.show();
			}
		});
	}


	public static void showModalDialog(Activity activity, String title, String msg, String okText,
			DialogInterface.OnClickListener onOk, String cancelText, DialogInterface.OnClickListener onCancel)
	{
		KDialog.AlertDialogBuilder builder = new KDialog.AlertDialogBuilder(activity);
		builder.setTitle(title);
		builder.setMessage(msg);
		builder.setPositiveButton(okText, onOk);
		builder.setNegativeButton(cancelText, onCancel);
		builder.show();
	}


	public static void showLoadingDialog(Context context, String msg)
	{
		showLoadingDialog(context, msg, true);
	}

	public static void showLoadingDialog(Context context, String msg, boolean cancelable)
	{
		_progressDialogbuilder = new ProgressDialogBuilder(context);
		_progressDialogbuilder.setMessage(msg);
		_progressDialogbuilder.setCancelable(cancelable);
		_progressDialogbuilder.show();
	}


	public static void showLoadingDialog(Context context, int msgRes)
	{
		_progressDialogbuilder = new ProgressDialogBuilder(context);
		_progressDialogbuilder.setMessage(msgRes);
		_progressDialogbuilder.show();
	}

	public static void setCancelable(boolean isCancelable)
	{
		_progressDialogbuilder.setCancelable(isCancelable);
	}
	
	public static void dismiss()
	{
		if (_dialog != null && _dialog.isShowing())
		{
			_dialog.dismiss();
		}
		if (_progressDialog != null && _progressDialog.isShowing())
		{
			_progressDialog.dismiss();
		}
	}


	public static class AlertDialogBuilder
	{
		private Context _context;
		private AlertDialog.Builder _builder;


		public AlertDialogBuilder(Context context)
		{
			_context = context;
			_builder = new AlertDialog.Builder(context);
		}


		public AlertDialogBuilder setCustomView(View view)
		{
			_builder.setView(view);
			return this;
		}


		public AlertDialogBuilder setTitle(String title)
		{
			_builder.setTitle(title);
			return this;
		}


		public AlertDialogBuilder setTitle(int title)
		{
			_builder.setTitle(title);
			return this;
		}


		public AlertDialogBuilder setMessage(String message)
		{
			_builder.setMessage(message);
			return this;
		}


		public AlertDialogBuilder setMessage(int message)
		{
			_builder.setMessage(message);
			return this;
		}


		public AlertDialogBuilder setItems(int itemsRes, OnClickListener onClickListener)
		{
			_builder.setItems(itemsRes, onClickListener);
			return this;
		}


		public AlertDialogBuilder setPositiveButton(String text, OnClickListener onClickListener)
		{
			_builder.setPositiveButton(text, onClickListener);
			return this;
		}


		public AlertDialogBuilder setPositiveButton(int textRes, OnClickListener onClickListener)
		{
			_builder.setPositiveButton(_context.getString(textRes), onClickListener);
			return this;
		}


		public AlertDialogBuilder setNegativeButton(String text, OnClickListener onClickListener)
		{
			_builder.setNegativeButton(text, onClickListener);
			return this;
		}


		public AlertDialogBuilder setNegativeButton(int textRes, OnClickListener onClickListener)
		{
			_builder.setNegativeButton(textRes, onClickListener);
			return this;
		}


		public AlertDialogBuilder setNeutralButton(int textRes, OnClickListener onClickListener)
		{
			_builder.setNeutralButton(textRes, onClickListener);
			return this;
		}


		public void show(DialogInterface.OnDismissListener onDismissListener)
		{
			_dialog = _builder.create();
			_dialog.setOnDismissListener(onDismissListener);
			_dialog.show();
		}


		public void show()
		{
			_dialog = _builder.create();
			_dialog.show();
		}
	}


	public static class ProgressDialogBuilder
	{
		private Context _context;


		public ProgressDialogBuilder(Context context)
		{
			_context = context;
			_progressDialog = new ProgressDialog(context);
		}


		public ProgressDialogBuilder setTitle(int titleRes)
		{
			_progressDialog.setTitle(titleRes);
			return this;
		}


		public ProgressDialogBuilder setMessage(int messageRes)
		{
			_progressDialog.setMessage(_context.getString(messageRes));
			return this;
		}


		public ProgressDialogBuilder setMessage(String message)
		{
			_progressDialog.setMessage(message);
			return this;
		}


		public ProgressDialogBuilder setOnDismissListener(DialogInterface.OnDismissListener onDismissListener)
		{
			_progressDialog.setOnDismissListener(onDismissListener);
			return this;
		}


		public ProgressDialogBuilder setCancelable(boolean cancelable)
		{
			_progressDialog.setCancelable(cancelable);
			_progressDialog.setCanceledOnTouchOutside(cancelable);
			return this;
		}


		public void show()
		{
			_progressDialog.show();
		}
	}

}