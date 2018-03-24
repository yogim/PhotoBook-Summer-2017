package com.photobook.dbhelper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class Logintable extends SQLiteOpenHelper {
	

	public static final String Db_name = "photobook";
	public static final String Table_login = "logintable";
	public static final String Column_ID = "ID";
	public static final String Column_username = "username";
	public static final String Column_password = "password";
	public static final String Column_emailid = "emailid";

	public static final int Version = 1;

	public static final String Database_create_logintable = "create table "
			+ Table_login + " (" + Column_ID
			+ " integer primary key autoincrement," + Column_username
			+ " text not null," + Column_password + " text not null,"
			+ Column_emailid + " text not null);";

	
	public Logintable(Context context)
	{
		super(context, Db_name, null, Version);
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		db.execSQL(Database_create_logintable);
		
	}


	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		db.execSQL("DROP TABLE IF EXISTS" + Table_login);
		onCreate(db);		
	}
	

}
