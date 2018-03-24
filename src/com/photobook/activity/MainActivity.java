package com.photobook.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.ContextMenu;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.AdapterContextMenuInfo;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.photobook.R;
import com.facebook.FacebookException;
import com.facebook.FacebookOperationCanceledException;
import com.facebook.FacebookRequestError;
import com.facebook.HttpMethod;
import com.facebook.Request;
import com.facebook.RequestBatch;
import com.facebook.Response;
import com.facebook.Session;
import com.facebook.SessionState;
import com.facebook.UiLifecycleHelper;
import com.facebook.model.OpenGraphAction;
import com.facebook.model.OpenGraphObject;
import com.facebook.widget.FacebookDialog;
import com.facebook.widget.WebDialog;
import com.facebook.widget.WebDialog.OnCompleteListener;
import com.photobook.adapter.Listviewadapter;
import com.photobook.dao.Bookpagessource;
import com.photobook.dao.Listviewdbsource;
import com.photobook.model.Book;

public class MainActivity extends Activity {
	ListView booklist;
	Listviewadapter adapter;
	ArrayList<Book> bookarray;
	Listviewdbsource listsource;
	EditText bookedit;
	 EditText authoredit;
	 Bookpagessource pagesource;
	 private static final List<String> PERMISSIONS = Arrays.asList("publish_actions");
	 private static final String TAG = "MainFragment";

		// Set to true to upload an image to the staging
		// resource before creating the Open Graph object.
		static final boolean UPLOAD_IMAGE = false;

		private ProgressDialog progressDialog;

		private UiLifecycleHelper uiHelper;
	    private Session.StatusCallback callback = new Session.StatusCallback() {
	        @Override
	        public void call(final Session session, 
	        		final SessionState state, 
	        		final Exception exception) {
	            onSessionStateChange(session, state, exception);
	        }
	    };
	    
		

		
		private static final String PENDING_PUBLISH_KEY = "pendingPublishReauthorization";

		private boolean pendingPublishReauthorization = false;

	 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		bookarray = new ArrayList<Book>();
		booklist = (ListView) findViewById(R.id.Booklist);
		listsource = new Listviewdbsource(getApplicationContext());
		pagesource= new Bookpagessource(getApplicationContext());
		
		
		listsource.open();
		registerForContextMenu(booklist);

		bookarray = listsource.getallbookforlistview();

		adapter = new Listviewadapter(bookarray, getApplicationContext());
		booklist.setAdapter(adapter);

		booklist.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(getApplicationContext(),
						BookPagesActivity.class);
				String bookid = bookarray.get(arg2).getID();
			
				intent.putExtra("book_id", bookid);
				startActivity(intent);

			}
		});

		
		uiHelper = new UiLifecycleHelper(this, callback);
        uiHelper.onCreate(savedInstanceState);
        
        
        

    }
	
		
	@Override
    public void onResume() {
        super.onResume();
        
        // For scenarios where the main activity is launched and user
		// session is not null, the session state change notification
		// may not be triggered. Trigger it if it's open/closed.
		Session session = Session.getActiveSession();
		if (session != null &&
				(session.isOpened() || session.isClosed()) ) {
			onSessionStateChange(session, session.getState(), null);
		}

        uiHelper.onResume();
    }
    
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
       // uiHelper.onActivityResult(requestCode, resultCode, data);
        uiHelper.onActivityResult(requestCode, resultCode, data, new FacebookDialog.Callback() {
            @Override
            public void onError(FacebookDialog.PendingCall pendingCall, Exception error, Bundle data) {
                Log.e("Activity", String.format("Error: %s", error.toString()));
            }

            @Override
            public void onComplete(FacebookDialog.PendingCall pendingCall, Bundle data) {
                Log.i("Activity", "Success!");
            }
        });
    }
    
    @Override
    public void onPause() {
        super.onPause();
        uiHelper.onPause();
    }
    
    @Override
    public void onDestroy() {
        super.onDestroy();
        uiHelper.onDestroy();
    }
    
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(PENDING_PUBLISH_KEY, pendingPublishReauthorization);
        uiHelper.onSaveInstanceState(outState);
    }

	private void onSessionStateChange(Session session, SessionState state, Exception exception) {
        //if (state.isOpened()) {
           
          /*  if (pendingPublishReauthorization && 
            		state.equals(SessionState.OPENED_TOKEN_UPDATED)) {
            	pendingPublishReauthorization = false;
            	String bookname = "abc";
            	//publishStory(String bookname);
            }
        } else if (state.isClosed()) {
           
        }*/
    }


	@Override
	public void onCreateContextMenu(ContextMenu m, View v,
			ContextMenuInfo menuInfo) {
		// TODO Auto-generated method stub
		super.onCreateContextMenu(m, v, menuInfo);
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.listmenu, m);

	}

	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// TODO Auto-generated method stub
		AdapterContextMenuInfo info = (AdapterContextMenuInfo) item
				.getMenuInfo();
		Integer index = info.position;
		String name = bookarray.get(index).getID();
		String bookname=bookarray.get(index).getBookname();
		Book book = new Book();
		book=bookarray.get(index);
		Log.e(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>",name);
		switch (item.getItemId()) {
		case R.id.Delete_book: {
			listsource.deletebook(name);
			pagesource.open();
			pagesource.deletepages(name);
			pagesource.close();
			ArrayList<Book> array1 = new ArrayList<Book>();
			array1=listsource.getallbookforlistview();
			Listviewadapter adapter1 = new Listviewadapter(array1, getApplicationContext());
			booklist.setAdapter(adapter1);
			

			break;
			
			
			
			
		}
		
		case R.id.Share_book:
		{
			//shareonfacebook(bookname);
		//sharebook(book);
			//String nameApp = "Photobooklet";
			//share( nameApp  );
			// postImage() ;
			publishStory(book);
		}
		}
		return super.onContextItemSelected(item);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// TODO Auto-generated method stub

		switch (item.getItemId()){
		case R.id.add_book:
		{
		showdialogbox();
		break;
		}
		case R.id.Logout:
		{
		facebooklogout();
		break;
		}
		}
		
		/*if (item.getItemId() == R.id.add_book) {
			showdialogbox();
			//facebooklogout();

		}*/
		return super.onOptionsItemSelected(item);
	}

	public void showdialogbox() {
		AlertDialog.Builder dialog = new AlertDialog.Builder(this);
		dialog.setTitle("Add Book");

		LayoutInflater inflater = (LayoutInflater) this
				.getSystemService(LAYOUT_INFLATER_SERVICE);

		View layout = inflater.inflate(R.layout.dialogaddbook, null);

		final EditText bookedit = (EditText) layout
				.findViewById(R.id.dialog_name_editbook);
  
		final EditText authoredit = (EditText) layout
				.findViewById(R.id.dialog_name_editAuthor);

		dialog.setPositiveButton("OK", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface arg0, int arg1) {
				String name = bookedit.getText().toString();
				if (name != null && name.trim().length() != 0)

				{
					String date = new SimpleDateFormat("yyyy-MM-dd")
							.format(new Date());
					listsource
							.createbookinlisview(new Book(bookedit.getText()
									.toString(), authoredit.getText()
									.toString(), date));
					ArrayList<Book> array = new ArrayList<Book>();
					array = listsource.getallbookforlistview();
					bookarray = array;
					adapter = new Listviewadapter(bookarray,
							getApplicationContext());
					booklist.setAdapter(adapter);

					adapter.notifyDataSetChanged();
				} else {
					Toast.makeText(getApplicationContext(),
							"please enter book name", Toast.LENGTH_SHORT)
							.show();

				}

			}

		});

		dialog.setNegativeButton("Cancel", null);
		;
		dialog.setView(layout);
		AlertDialog bookadd = dialog.create();
		bookadd.show();
	}
	
	
	public void facebooklogout()
	{
		Session session = Session.getActiveSession();
	    if (session != null) {

	        if (!session.isClosed()) {
	            session.closeAndClearTokenInformation();
	            //clear your preferences if saved
	        }
	    } else {

	        session = new Session(getApplicationContext());
	        Session.setActiveSession(session);

	        session.closeAndClearTokenInformation();
	            //clear your preferences if saved

	    }
	    if(session==null)
	    {
Log.e(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>", "logout facebook");
	    }
	    Intent intent = new Intent(getApplicationContext(), Login.class);
	    startActivity(intent);
	}
	
	public void shareonfacebook(String bookname)
	{
		if (checkPermissions()) {
            Request request = Request.newStatusUpdateRequest(
                    Session.getActiveSession(), bookname,
                    new Request.Callback() {
                        @Override
                        public void onCompleted(Response response) {
                            if (response.getError() == null)
                                Toast.makeText(getApplicationContext(),
                                		
                                        "Status updated successfully",
                                        Toast.LENGTH_LONG).show();
                        }
                    });
            request.executeAsync();
        } else {
            requestPermissions();
        }
		
	}

	public boolean checkPermissions() {
        Session s = Session.getActiveSession();
        if (s != null) {
            return s.getPermissions().contains("publish_actions");
        } else
            return false;
    }
 
    public void requestPermissions() {
        Session s = Session.getActiveSession();
        if (s != null)
            s.requestNewPublishPermissions(new Session.NewPermissionsRequest(
                    this, PERMISSIONS));
    }


	private void publishStory( Book b) {
		
		Session s1 = Session.getActiveSession();
		if(s1!=null){
		
		
		
		Bitmap bitmap = BitmapFactory.decodeResource(this.getResources(),
                R.drawable.bookimage);

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
        byte[] bitMapData = stream.toByteArray();


		Bundle params = new Bundle();
		Log.d(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>	", b.getBookname());
	    params.putString("name", b.getBookname());
	    params.putString("caption", b.getBookauthor());
	    params.putString("description", "excellent book ,must read");
	   
	   params.putString("picture", "http://pngimg.com/upload/book_PNG2116.png");
	  
	    WebDialog feedDialog = (
	        new WebDialog.FeedDialogBuilder(MainActivity.this,
	            Session.getActiveSession(),
	            params))
	        .setOnCompleteListener(new OnCompleteListener() {

	           

				@Override
				public void onComplete(Bundle values, FacebookException error) {
					if (error == null) {
	                    // When the story is posted, echo the success
	                    // and the post Id.
	                    final String postId = values.getString("post_id");
	                    if (postId != null) {
	                        Toast.makeText(getApplicationContext(),
	                            "Posted story, id: "+postId,
	                            Toast.LENGTH_SHORT).show();
	                    } else {
	                        // User clicked the Cancel button
	                        Toast.makeText(getApplicationContext().getApplicationContext(), 
	                            "Publish cancelled", 
	                            Toast.LENGTH_SHORT).show();
	                    }
	                } else if (error instanceof FacebookOperationCanceledException) {
	                    // User clicked the "x" button
	                    Toast.makeText(getApplicationContext().getApplicationContext(), 
	                        "Publish cancelled", 
	                        Toast.LENGTH_SHORT).show();
	                } else {
	                    // Generic, ex: network error
	                    Toast.makeText(getApplicationContext().getApplicationContext(), 
	                        "Error posting story", 
	                        Toast.LENGTH_SHORT).show();
	                }					
				}

	        })
	        .build();
	    feedDialog.show();
		}
	
		else
		{
			Toast.makeText(getApplicationContext(), "To share you must login with facebook", Toast.LENGTH_SHORT).show();
		}
	}
	
	
	public void dismissProgressDialog() {
		// Dismiss the progress dialog
		if (progressDialog != null) {
            progressDialog.dismiss();
            progressDialog = null;
        }
	}
	public boolean isSubsetOf(Collection<String> subset, Collection<String> superset) {
	    for (String string : subset) {
	        if (!superset.contains(string)) {
	            return false;
	        }
	    }
	    return true;
	}

}