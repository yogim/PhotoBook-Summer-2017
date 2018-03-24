package com.example.photobook;

import android.os.Bundle;

import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;

import com.photobook.dao.Logintablesource;
import com.photobook.model.Logindata;

public class RegistrationForm extends ActionBarActivity {
	
	Logintablesource loginsource;
	Button submitbutton;
	EditText usernameedit;
	EditText passwordedit;
EditText emaitedit;
	Logindata logindata;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_registration_form);
		
		loginsource= new Logintablesource(getApplicationContext());
		submitbutton = (Button) findViewById(R.id.submitbutton);
		
		usernameedit = (EditText) findViewById(R.id.username_edittext);
		passwordedit = (EditText) findViewById(R.id.password_edittext);
	emaitedit= (EditText) findViewById(R.id.email_edittext);
		
		submitbutton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				loginsource.open();
				logindata = new Logindata(usernameedit.getText().toString(), passwordedit.getText().toString(),emaitedit.getText().toString());
				Log.e(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>", logindata.toString());
				loginsource.createuser(logindata);
				
				loginsource.close();
				
			}
		});

		
		
		
		
	}

	

	

	

	
	

}
