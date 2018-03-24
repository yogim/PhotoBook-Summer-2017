package com.photobook.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class Listviewdbhelper extends SQLiteOpenHelper {
	public static final String Db_name = "photobook";
	public static final String Table_booklist = "booklist";
	public static final String Column_ID = "ID";
	public static final String Column_bookname = "bookname";
	public static final String Column_authorname = "authorname";
	public static final String Column_datecreated = "datecreated";

	public static final int Version = 1;

	public static final String Database_create_booklist = "create table "
			+ Table_booklist + " (" + Column_ID
			+ " integer primary key autoincrement," + Column_bookname
			+ " text not null," + Column_authorname + " text not null,"
			+ Column_datecreated + " text not null);";

	public static final String Table_grid = "gridtable";
	public static final String column_ID = "ID";
	public static final String column_URI = "URI";
	public static final String book_id = "bookid";

	
	public static final String Database_create = "create table " + Table_grid
			+ " (" + column_ID + " integer primary key autoincrement,"
			+ book_id + " text not null," + column_URI + " text not null);";
	
	
	
	
	
	
	
	
	public static final String Table_login = "logintable";
	public static final String Column_ID1 = "ID";
	public static final String Column_username = "username";
	public static final String Column_password = "password";
	public static final String Column_emailid = "emailid";

	

	public static final String Database_create_logintable = "create table " + Table_login + " (" + Column_ID1
			+ " integer primary key autoincrement," + Column_username
			+ " text not null," + Column_password + " text not null,"
			+ Column_emailid + " text not null);";
	
	
	/*public static final String Database_create_logintable = "create table " + Table_login + " (" + Column_ID1
			+ " integer primary key autoincrement," + Column_username
			+ " text not null," + Column_password + " text not null);";
	
	*/

	public Listviewdbhelper(Context context) {
		super(context, Db_name, null, Version);

	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(Database_create);
		db.execSQL(Database_create_booklist);
		db.execSQL(Database_create_logintable);
		//db.e
		Log.e(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>.", "inside oncreate listsource");
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int arg1, int arg2) {
		db.execSQL("DROP TABLE IF EXISTS" + Table_booklist);
		onCreate(db);

	}

}
