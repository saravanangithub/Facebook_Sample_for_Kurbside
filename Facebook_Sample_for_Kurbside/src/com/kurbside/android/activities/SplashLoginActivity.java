package com.kurbside.android.activities;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;

import com.kurbside.android.R;

public class SplashLoginActivity extends KurbsideEntryActivity
{
	private LinearLayout _buttonsLayout;
	private Button _signupButton;
	private Button _browseButton;
	private Button _facebookLoginButton;


	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		return super.onCreateOptionsMenu(menu);
	}


	@Override
	public boolean onOptionsItemSelected(MenuItem item)
	{
		switch (item.getItemId())
		{
		case R.id.signup:

			break;
		}
		return super.onOptionsItemSelected(item);
	}


	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.splash);

		_buttonsLayout = (LinearLayout) findViewById(R.id.buttons_layout);
		_facebookLoginButton = (Button) findViewById(R.id.login_with_facebook);

		_facebookLoginButton.setOnClickListener(new FacebookLoginClickListener());
		startButtonsAnimation();
	}


	private void startButtonsAnimation()
	{
		Handler handler = new Handler(Looper.getMainLooper());
		handler.postDelayed(new Runnable()
		{
			@Override
			public void run()
			{
				if (_loggingIn == true)
				{
					return;
				}
				Animation fadeInAnimation = AnimationUtils.loadAnimation(SplashLoginActivity.this, android.R.anim.fade_in);
				_buttonsLayout.startAnimation(fadeInAnimation);
				_buttonsLayout.setVisibility(View.VISIBLE);				
			}
		}, 1500);
	}


	private class BrowseClickListener implements OnClickListener
	{
		@Override
		public void onClick(View v)
		{
		}
	}

	private class SignupClickLitener implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			launchSignUpDialog();
		}

	}
	
	private class FacebookLoginClickListener implements OnClickListener
	{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			loginWithFacebook();
		}
		
	}
}