package com.photobook.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.photobook.dbhelper.Listviewdbhelper;
import com.photobook.dbhelper.Logintable;
import com.photobook.model.Logindata;

public class Logintablesource {
       
	Listviewdbhelper logindbhelper;
         SQLiteDatabase db;
         String[] Column_name = { Logintable.Column_ID,Logintable.Column_username,
        		 Logintable.Column_password,Logintable.Column_emailid
        		      };
         
        public Logintablesource(Context context)
         {
        	 logindbhelper= new Listviewdbhelper(context);
         }
        
        
        public void open() {
    		db = logindbhelper.getWritableDatabase();
    	}

    	public void close() {
    		logindbhelper.close();
    	}
         
         public void createuser(Logindata data)
         {
        	 ContentValues value = new ContentValues();
        	 value.put(Logintable.Column_username, data.getUsername());
        	 value.put(Logintable.Column_password, data.getPassword());
        	 value.put(Logintable.Column_emailid, data.getEmail());
        	 db.insert(Logintable.Table_login, null, value);
        	 
         }
         
       public int checkuserpass(String username,String password)
         {
    	   
    	   Log.e(">>>>>>>>>>>>>>>>>>>>>>>>>>>	","inside checkuserpass");
    	   //db.delete(Listviewdbhelper.Table_login, "1", null);
        	String[] selectionArgs = new String[]{username, password};
            try
            {
                int i = 0;
                Cursor c = null;
                c = db.rawQuery("select * from logintable where username=? and password=?", selectionArgs);
                c.moveToFirst();
                Log.e("username:====> ",c.getString(1).toString());
                Log.e("Password:====> ",c.getString(2).toString());
                Log.e("username:====> ",c.getString(3).toString());
                
                i = c.getCount(); 
                c.close(); 
                return i;
            }
            catch(Exception e)
            {
                e.printStackTrace();
            }
            return 0;
         }
         
       
      public Logindata getusernamepassword(String email)
       {
    	Logindata data = new Logindata();
    	String[] selectionArgs = new String[]{email};
    	try
        {
            
   
            String query = "SELECT * FROM  logintable  WHERE emailid = ? ";  //AND " + COLUMN_UPLOAD_TYPE + "=?";

    	
    		Cursor c = db.rawQuery(query, selectionArgs);

    		if (c != null) {
    		
            
         
            c.moveToFirst();
            Log.e("username:====> ",c.getString(c.getColumnIndex("username")));
            Log.e("Password:====> ",c.getString(c.getColumnIndex("password")));
            
            data.setUsername(c.getString(1));
            data.setPassword(c.getString(2));
          } 
            
            
            c.close(); 
            return data;
        }
        catch(Exception e)
        {
            e.printStackTrace();
        }
        return data;
    	
  
    	  
       }
         
}
