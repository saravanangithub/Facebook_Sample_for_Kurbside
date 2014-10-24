package com.kurbside.android.commons;

import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;

import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.LoggingBehavior;
import com.facebook.Request;
import com.facebook.Request.GraphUserCallback;
import com.facebook.RequestAsyncTask;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.Settings;
import com.facebook.model.GraphUser;
import com.kurbside.android.R;
import com.kurbside.android.models.KurbsideResponse;
import com.kurbside.android.utils.KDialog;
import com.kurbside.android.utils.NetworkUtil;
import com.kurbside.android.utils.Utils;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseFacebookUtils.Permissions;
import com.parse.ParseUser;
import com.parse.SaveCallback;

public class FacebookService 
{
	private static FacebookService _instance; 
	private static Context _context;

	public static FacebookService getInstance(Context context) 
	{
		_context = context;
		if (_instance == null) 
		{
			Settings.addLoggingBehavior(LoggingBehavior.INCLUDE_ACCESS_TOKENS);
			_instance = new FacebookService();
		}

		return _instance;
	}

	public void loginWithFacebook(final Activity activity, final Closure<KurbsideResponse> successClosure, final Closure<KurbsideResponse> errorClosure) 	
	{ 
		if(!NetworkUtil.isConnectivityEnabled(_context))
		{
			Utils.longToast(_context, _context.getResources().getString(R.string.Connection_Failed));
			errorClosure.callback(null);
			return;
		}
		ParseFacebookUtils.logIn(Arrays.asList(Constants.EMAIL, Permissions.Friends.ABOUT_ME), activity, new LogInCallback() 		
		{
			@Override
			public void done(ParseUser user, ParseException err) 
			{

				if(err!=null)
				{
					err.printStackTrace();
				}
				if (user == null)
				{
					errorClosure.callback(null);
				}
				else if (user.isNew()) 		
				{
					new OnParseUserSaveCallBack(user, successClosure).done(null);
				}
				else 	
				{
					successClosure.callback(null); 
				}
			}
		});
	}


	
	private class OnParseUserSaveCallBack extends SaveCallback
	{
		private ParseUser parseUser;
		private Closure<KurbsideResponse> successClosure;

		public OnParseUserSaveCallBack(ParseUser user, Closure<KurbsideResponse> successClosure)
		{
			this.parseUser = user;
			this.successClosure = successClosure;
		}

		@Override
		public void done(ParseException arg0) 
		{
			Session session = ParseFacebookUtils.getSession();
			Request.newMeRequest(session, new OnGetGraphUserDone(this.parseUser, successClosure)).executeAsync();
		}
	}

	private class OnGetGraphUserDone implements GraphUserCallback 
	{
		private ParseUser parseUser;
		private Closure<KurbsideResponse> successClosure;

		public OnGetGraphUserDone(ParseUser user, Closure<KurbsideResponse> successClosure)
		{
			this.parseUser = user;
			this.successClosure = successClosure;
		}

		@Override
		public void onCompleted(GraphUser user, Response response) 
		{
			JSONObject userJson = user.getInnerJSONObject();
			String email;

			try	
			{
				email = userJson.getString(Constants.EMAIL);
				parseUser.setEmail(email);
				parseUser.put(Constants.LOGIN, email);
			}
			catch (JSONException e)	
			{
				e.printStackTrace();
			}

			parseUser.put(Constants.DISPLAY_NAME, user.getName());		

			parseUser.saveInBackground(new SaveCallback()
			{
				@Override
				public void done(ParseException e)
				{
					successClosure.callback(null);
				}
			});
		}
	}

	private class LoginSessionStatusCallback implements Session.StatusCallback 
	{
		Closure<KurbsideResponse> successClosure;
		Closure<KurbsideResponse> errorClosure;

		public LoginSessionStatusCallback(Closure<KurbsideResponse> successClosure,Closure<KurbsideResponse> errorClosure) 
		{
			this.errorClosure = errorClosure;
			this.successClosure = successClosure;
		}

		@Override
		public void call(Session session, SessionState state, Exception exception)
		{
			if(state.isOpened())
			{
				successClosure.callback(null);
				session.removeCallback(this);  
				return;
			}

			if(state.equals(SessionState.CLOSED_LOGIN_FAILED)||state.equals(SessionState.CLOSED))
			{
				session.removeCallback(this); 
				errorClosure.callback(null);
			} 
		}
	}

	public void loginToFacebookForPhoneLogin(Activity activity,Closure<KurbsideResponse> successClosure,Closure<KurbsideResponse> errorClosure) 
	{
		if(!NetworkUtil.isConnectivityEnabled(_context))
		{
			Utils.longToast(_context, _context.getResources().getString(R.string.Connection_Failed));
			errorClosure.callback(null);
			return;
		}
		Session.openActiveSession(activity, true, new LoginSessionStatusCallback(successClosure, errorClosure)); 
	}

	public void cancelFacebookLogin()
	{
		if(Session.getActiveSession().isOpened())
		{
			Session.getActiveSession().close();
		}
	}
}

