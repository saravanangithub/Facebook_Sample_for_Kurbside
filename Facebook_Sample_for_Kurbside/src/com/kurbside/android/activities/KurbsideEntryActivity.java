package com.kurbside.android.activities;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.kurbside.android.R;
import com.kurbside.android.commons.Closure;
import com.kurbside.android.commons.FacebookService;
import com.kurbside.android.models.KurbsideResponse;
import com.kurbside.android.utils.KDialog;
import com.kurbside.android.utils.Utils;
import com.parse.Parse;
import com.parse.ParseFacebookUtils;
import com.parse.PushService;

abstract public class KurbsideEntryActivity extends Activity
{
	private static final String LOG_TAG = KurbsideEntryActivity.class.getName();
	protected EditText _signUpPhoneNumber;
	protected EditText _signUpName;
	protected EditText _signUpPassword;
	protected EditText _signUpConfirmPassword;
	protected Button _signUpButton;
	protected EditText _phoneNumber;
	protected EditText _password;
	protected Button _loginButton;
	protected boolean _loggingIn = false;
	protected boolean _goToMainActivityOnSuccessfulFetch = true;
	protected FacebookService _facebookService;



	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data)
	{    
		//Very Important call for facebook login!

		super.onActivityResult(requestCode, resultCode, data);
		ParseFacebookUtils.finishAuthentication(requestCode, resultCode, data); 
	}

	@Override
	public void onStart() { 
		super.onStart();
	}

	@Override
	public void onStop() {
		super.onStop();
	}

	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);
	}

	@Override
	protected void onDestroy()
	{
		super.onDestroy();
	}

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		Log.i(LOG_TAG, "~~~~~~~~ -- onCreate -- ~~~~~~~~~");
		_facebookService = FacebookService.getInstance(this);
		Parse.initialize(this, this.getString(R.string.Parse_app_id), this.getString(R.string.Parse_client_id));
		ParseFacebookUtils.initialize(this.getString(R.string.Facebook_app_id));
		//PushService.setDefaultPushCallback(this, this);
	}

	private void goToMainActivity()
	{
		Intent intent = new Intent(this, SuccessActivity.class);
		intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivity(intent);
		finish();
	}

	protected void launchSignUpDialog()
	{
	}

	private class SignupMethodListener implements DialogInterface.OnClickListener
	{
		@Override
		public void onClick(DialogInterface dialogInterface, int i)
		{
			loginWithFacebook();
		}
	}


	private class OnFBLoginSuccess extends Closure<KurbsideResponse>
	{
		@Override
		public void callback(KurbsideResponse response)
		{
			KDialog.dismiss();
			goToMainActivity();
		}
	}

	public class OnFBLoginError extends Closure<KurbsideResponse>
	{
		@Override
		public void callback(KurbsideResponse response)
		{
			KDialog.dismiss();
			Utils.longToast(KurbsideEntryActivity.this, getResources().getString(R.string.Login_Error));
		}
	}
	protected class LoginClickListener implements OnClickListener
	{
		@Override
		public void onClick(View view)
		{
		}
	}

	private class LoginMethodListener implements DialogInterface.OnClickListener
	{
		@Override
		public void onClick(DialogInterface dialogInterface, int i)
		{
			loginWithFacebook();
		}
	} 
	
	public void loginWithFacebook()
	{
		KDialog.dismiss();
		KDialog.showLoadingDialog(KurbsideEntryActivity.this, getResources().getString(R.string.Logging_In));
		_facebookService.loginWithFacebook(KurbsideEntryActivity.this, new OnFBLoginSuccess(), new OnFBLoginError());
	}
	
	
	
}
