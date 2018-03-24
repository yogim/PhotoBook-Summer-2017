package com.photobook.activity;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Locale;

import com.example.photobook.R;
import com.photobook.adapter.Bookpagesadapter;
import com.photobook.dao.Bookpagessource;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

public class BookPagesActivity extends ActionBarActivity {

	private static final int CAMERA_CAPTURE_IMAGE_REQUEST_CODE = 100;
	final int MEDIA_TYPE_IMAGE = 2;
	Button click;
	ImageView image;
	Uri fileuri;
	int camera_capture = 100;

	GridView gridactivity;
	Bookpagessource source;
	public Bookpagesadapter adapter;
	ArrayList<String> list;
	String bookid;
  // ViewPager pager ;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.e(">>>>>>>>>>>>>>>>>>>>>>>>", "activity 2");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_book_pages);
		gridactivity = (GridView) findViewById(R.id.grid_view);
		Bundle b= getIntent().getExtras();
		 bookid= b.getString("book_id");
		//pager = findViewById(R.id.)
		source = new Bookpagessource(getApplicationContext());
		//pageadapter= new Imageviewer(getParent(), list);
		source.open();
		
		
		
		list = source.getallpath(bookid);
		adapter = new Bookpagesadapter(getApplicationContext(), list);
		gridactivity.setAdapter(adapter);
		
		gridactivity.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(getApplicationContext(), Fullscreen.class);
				String path= list.get(position);
				intent.putExtra("path",list);
				intent.putExtra("postion", position);
				startActivity(intent);
				
				
			}
		});

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.book_pages, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		int id = item.getItemId();
		if (id == R.id.Add_pages) {
			captureimage();
		}
		return super.onOptionsItemSelected(item);
	}

	private void captureimage() {
		if (cameraavail()) {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			fileuri = getmediafileuri(MEDIA_TYPE_IMAGE);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, fileuri);

			startActivityForResult(intent, CAMERA_CAPTURE_IMAGE_REQUEST_CODE);
		} else {
			Toast.makeText(getApplicationContext(),
					"device with not camera support", Toast.LENGTH_SHORT)
					.show();
		}
	}

	@Override
	protected void onActivityResult(int resultrequest, int resultcode,
			Intent data) {
		
		super.onActivityResult(resultrequest, resultcode, data);

		if (resultrequest == CAMERA_CAPTURE_IMAGE_REQUEST_CODE) {

			if (resultcode == RESULT_OK) {

				

				source.createpicture(fileuri.getPath(),bookid);
				

				ArrayList<String> list1 = source.getallpath(bookid);
				
                  
				list.clear();
				//list = list1;
				
				list.addAll(list1);
				
				adapter.notifyDataSetChanged();

			} else if (resultcode == RESULT_CANCELED) {
				Toast.makeText(getApplicationContext(),
						"User cancelled image capture", Toast.LENGTH_SHORT)
						.show();
			}

		}
	}

	private Uri getmediafileuri(int arg) {
		File mediafile = getmediafile(arg);

		return Uri.fromFile(mediafile);
	}

	private File getmediafile(int type) {

		File mediadir = new File(Environment.getExternalStorageDirectory()
				+ "/myimages");
		if (!mediadir.exists()) {
			mediadir.mkdirs();
		}

		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss",
				Locale.getDefault()).format(new Date());

		Integer ct = 0;
		File mediafile;

		if (type == MEDIA_TYPE_IMAGE) {
			ct++;
			mediafile = new File(mediadir.getPath() + File.separator + "img_"
					+ timeStamp + ".png");
			
			

		} else {
			return null;
		}
		return mediafile;
	}

	private boolean cameraavail() {
		if (getApplicationContext().getPackageManager().hasSystemFeature(
				PackageManager.FEATURE_CAMERA))
			return true;
		else
			return false;

	}
	
}
