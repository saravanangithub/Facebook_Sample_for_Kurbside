package com.kurbside.android.activities;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.kurbside.android.R;

public class SuccessActivity extends Activity
{

	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.success);
		
		Button exit = (Button) findViewById(R.id.exit_button);
		exit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) 
			{
				SuccessActivity.this.finish();
			}
		});
	}
	
	
}
