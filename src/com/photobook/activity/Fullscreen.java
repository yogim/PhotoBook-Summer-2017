package com.photobook.activity;

import java.util.ArrayList;

import com.example.photobook.R;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ViewFlipper;

public class Fullscreen extends ActionBarActivity {

	Bitmap bp = null;
	ViewFlipper flipper;
	ViewPager viewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fullscreen_image);
		viewPager = (ViewPager) findViewById(R.id.view_pager);
		

		Bundle b = new Bundle();

		b = getIntent().getExtras();
		ArrayList<String> list = b.getStringArrayList("path");
		Integer position = b.getInt("postion");
		
		pageradapter adapter = new pageradapter(this, list);

		viewPager.setAdapter(adapter);
		viewPager.setCurrentItem(position);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {

		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.fullscreen, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_fullscreen,
					container, false);
			return rootView;
		}
	}

}
