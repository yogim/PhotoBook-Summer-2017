package com.photobook.dao;
import java.util.ArrayList;

import com.photobook.dbhelper.Listviewdbhelper;
import com.photobook.model.Book;

import android.R.bool;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;


public class Listviewdbsource {
	Listviewdbhelper dbhelper;
	SQLiteDatabase database;
	String[] Column_name = { Listviewdbhelper.Column_ID,
			Listviewdbhelper.Column_bookname,
			Listviewdbhelper.Column_authorname,
			Listviewdbhelper.Column_datecreated };

	public Listviewdbsource(Context context) {
		dbhelper = new Listviewdbhelper(context);
	}

	public void open() throws SQLException {
		database = dbhelper.getWritableDatabase();
	}

	public void close() {
		dbhelper.close();
	}

	public void createbookinlisview(Book book) {
		ContentValues value = new ContentValues();
		value.put(Listviewdbhelper.Column_bookname, book.getBookname());
		value.put(Listviewdbhelper.Column_authorname, book.getBookauthor());
		value.put(Listviewdbhelper.Column_datecreated, book.getDatecreated());
		database.insert(Listviewdbhelper.Table_booklist, null, value);

	}

	public void deletebook(String name)
	
	{
		/*index++;
		Log.e(">>>>>>>>>>>>>", index.toString());
		String id = index.toString();*/
		
		
		Integer f=database.delete(Listviewdbhelper.Table_booklist, Listviewdbhelper.Column_ID + "=" + name, null);
	
		//Integer f=database.delete(Listviewdbhelper.Table_booklist, Listviewdbhelper.column_ID + "=" + id, null);	
	Log.e(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> no of row for book", f.toString());
		
	}
	public void updatebook(Book book)
	{
		String id = book.getID();
		ContentValues value = new ContentValues();
		value.put(Listviewdbhelper.Column_bookname, book.getBookname());
		value.put(Listviewdbhelper.Column_authorname, book.getBookauthor());
		value.put(Listviewdbhelper.Column_datecreated, book.getDatecreated());
		database.update(Listviewdbhelper.Table_booklist, value, Listviewdbhelper.Column_ID+"="+id, null);
	}
	
	
	public ArrayList<Book> getallbookforlistview() {
		ArrayList<Book> alllistviewbook = new ArrayList<Book>();
		Cursor cursor = database.query(Listviewdbhelper.Table_booklist,
				Column_name, null, null, null, null, null);
		if (cursor.moveToFirst()) {
			do

			{
				Book b = new Book();
				b.setID(cursor.getString(0));
				b.setBookname(cursor.getString(1));
				b.setBookauthor(cursor.getString(2));
				b.setDatecreated(cursor.getString(3));
				alllistviewbook.add(b);

			} while (cursor.moveToNext());

		}
		cursor.close();

		return alllistviewbook;

	}

}
