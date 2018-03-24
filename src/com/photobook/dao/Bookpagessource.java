package com.photobook.dao;

import java.util.ArrayList;

import com.photobook.dbhelper.Listviewdbhelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class Bookpagessource {
	
	Listviewdbhelper dbhelper;
	SQLiteDatabase database;
	String[] column_name = { Listviewdbhelper.column_ID,
			Listviewdbhelper.column_URI };

	public Bookpagessource(Context context) {
		dbhelper = new Listviewdbhelper(context);
	}

	public void open() {
		database = dbhelper.getWritableDatabase();
	}

	public void close() {
		dbhelper.close();
	}

	public void createpicture(String path, String bookid)
	
	{
		ContentValues value = new ContentValues();
		value.put(Listviewdbhelper.column_URI, path);
		value.put(Listviewdbhelper.book_id, bookid);
		database.insert(Listviewdbhelper.Table_grid, null, value);

	}

	public ArrayList<String> getallpath(String bookid)
	// public ArrayList<String>getallpath()
	{
		ArrayList<String> pathlist = new ArrayList<String>();
		// Cursor cursor = database.query(Listviewdbhelper.Table_grid,
		// column_name, null, null, null, null, null, null);
		Cursor cursor = database.rawQuery(
				"SELECT * FROM gridtable WHERE bookid="+bookid+"", null);

		if (cursor.moveToFirst())
			do {
				String path = new String();
				path = cursor.getString(2);

				pathlist.add(path);

			} while (cursor.moveToNext());
		cursor.close();

		return pathlist;
	}
	public void deletepages(String bookid)
	{
		Integer f=database.delete(Listviewdbhelper.Table_grid, Listviewdbhelper.book_id + "=" + bookid, null);
	Log.e(">>>>>>>>>>>>>>>>>no of row of pages", f.toString());
	}

}
