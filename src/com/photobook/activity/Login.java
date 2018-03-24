package com.photobook.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.photobook.ForgotpasswordActivity;
import com.example.photobook.R;
import com.example.photobook.RegistrationForm;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.GraphUser;
import com.facebook.widget.LoginButton;
import com.facebook.widget.LoginButton.UserInfoChangedCallback;
import com.photobook.dao.Listviewdbsource;
import com.photobook.dao.Logintablesource;

public class Login extends ActionBarActivity {

	private static final String TAG = "Login";
	LoginButton loginbutton;
	private UiLifecycleHelper uiHelper;
	TextView username;
   Button register;
   Button login;
 //  Button forgotpassword;
   EditText usernameedit;
   EditText passwordedit;
   
   Logintablesource loginsource;
   Listviewdbsource listsource;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		uiHelper = new UiLifecycleHelper(this, callback);
		uiHelper.onCreate(savedInstanceState);
		

		setContentView(R.layout.fragment_login);
		loginsource = new Logintablesource(getApplicationContext());
		listsource = new Listviewdbsource(getApplicationContext());
		
		listsource.open();
		
		 register=(Button) findViewById(R.id.register_button);
		    login=(Button) findViewById(R.id.login_button);
		//    forgotpassword = (Button) findViewById(R.id.Forgotpassword_button);
		    usernameedit = (EditText) findViewById(R.id.username_edittext);
		    
		    passwordedit= (EditText) findViewById(R.id.password_edittext);
		
		listsource.close();

		loginbutton = (LoginButton) findViewById(R.id.fb_login_button);
		username = (TextView) findViewById(R.id.user_name);
		loginbutton.setUserInfoChangedCallback(new UserInfoChangedCallback() {
			@Override
			public void onUserInfoFetched(GraphUser user) {
				if (user != null) {
					username.setText("Hello, " + user.getName());
					
					Log.e(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>", "user info");
			/*Intent intent = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(intent);*/
				} else {
					username.setText("You are not logged");
				}
			}
		});

		
		
		
		login.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				String loginusername = usernameedit.getText().toString();
				
				String loginpassword = passwordedit.getText().toString();
				
				
				loginsource.open();
				int flag = loginsource.checkuserpass(loginusername, loginpassword);
				if(flag != 0)
				{
					Intent intent = new Intent(getApplicationContext(), MainActivity.class);
					startActivity(intent);
				}
				else
				{
					Toast.makeText(getApplicationContext(), "Invalid username or password", Toast.LENGTH_SHORT).show();
				}
				loginsource.close();
				
			}
		});
		
		
		register.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getApplicationContext(), RegistrationForm.class);
				startActivity(intent);
				
			}
		});
		
		
		
		
	/*//	forgotpassword.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
			
				Intent intent = new Intent(getApplicationContext(), ForgotpasswordActivity.class);
				startActivity(intent);
				
				
			}
		});
		*/
		
		
		
		
		
		
		
		
		

	}

	private Session.StatusCallback callback = new Session.StatusCallback() {
		@Override
		public void call(Session session, SessionState state,
				Exception exception) {
			onSessionStateChange(session, state, exception);
			Log.e(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>", "session "+session.toString());
			Log.e(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>", "session state" + state.toString());
			
			
			Log.e(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>", "session callback");
			
		}
	};

	private void onSessionStateChange(Session session, SessionState state,
			Exception exception) {
		
		Log.e(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>", "onsession");
		Log.e(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>", session.toString());

		if (state.isOpened()) {
			Log.i(TAG, "Logged in...");
			Intent intent = new Intent(getApplicationContext(), MainActivity.class);
			startActivity(intent);
			
		} else if (state.isClosed()) {
			Log.i(TAG, "Logged out...");
		}
	}

	@Override
	public void onResume() {
		super.onResume();
		Log.e(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>", "onresume");
		Session session = Session.getActiveSession();
		if (session != null && (session.isOpened() || session.isClosed())) {
			onSessionStateChange(session, session.getState(), null);
		}

		uiHelper.onResume();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		uiHelper.onActivityResult(requestCode, resultCode, data);
		Log.e(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>", "on activity result");
		Log.e(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>", data.getExtras().toString());

	}

	@Override
	public void onPause() {
		super.onPause();
		uiHelper.onPause();
		Log.e(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>", "onpause");
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		uiHelper.onDestroy();
		Log.e(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>", "destroy");
	
	}

}
