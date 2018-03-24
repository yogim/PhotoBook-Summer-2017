package com.example.photobook;

import com.photobook.dao.Logintablesource;
import com.photobook.model.Logindata;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;


public class ForgotpasswordActivity extends ActionBarActivity {
	
	Button sendpassword;
	EditText emailid;
	Logintablesource loginsource;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_forgotpassword);
		
		sendpassword= (Button) findViewById(R.id.sendpasswordbutton);
		emailid = (EditText) findViewById(R.id.email_edittext);
		loginsource= new Logintablesource(getApplicationContext());
		
		sendpassword.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				//Logindata data= new Logindata();  
						
						loginsource.open();
						Logindata data =	loginsource.getusernamepassword(emailid.getText().toString());
						if(data!=null)
						{
				Log.e(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>", data.getUsername());
						}
						else
						{
							Log.e(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>", "no data");
						}
				loginsource.close();
			}
		});

		
	}

	
	

	

}
