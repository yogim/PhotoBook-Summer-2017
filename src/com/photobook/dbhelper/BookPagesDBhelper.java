package com.photobook.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class BookPagesDBhelper extends SQLiteOpenHelper {
	public static final String Table_grid ="gridtable";
	 public static final String column_ID ="ID";
	 public static final String column_URI ="URI";
	 public static final String book_id ="bookid";
	 
	 public static  final String DB_name = "photobook.db";
	 public static  final int version =1;
	 public static  final String Database_create = "create table "+Table_grid+ " (" 
           // + column_ID +" integer primary key autoincrement," 
           + book_id +" text not null," 
             + column_URI + " text not null);";
	 
	 
	 public BookPagesDBhelper(Context context) {
			super(context, DB_name, null, version);
			
			Log.e(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>", "db formed");
					}


	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(Database_create);
		
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS"+ Table_grid);
		onCreate(db);
	}
	 

}
