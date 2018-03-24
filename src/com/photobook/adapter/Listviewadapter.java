package com.photobook.adapter;

import java.util.ArrayList;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.photobook.R;
import com.photobook.model.Book;

public class Listviewadapter extends BaseAdapter {
	ArrayList<Book> booklist;
	Context context;

	public Listviewadapter(ArrayList<Book> booklist, Context context) {
		super();
		this.booklist = booklist;
		this.context = context;
	}

	@Override
	public int getCount() {

		return booklist.size();
	}

	@Override
	public Object getItem(int arg0) {

		return booklist.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		return arg0;
	}

	@Override
	public View getView(int arg0, View view, ViewGroup arg2) {
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(context.LAYOUT_INFLATER_SERVICE);
		view = inflater.inflate(R.layout.row, null);
		TextView bookname = (TextView) view.findViewById(R.id.bookname);
		TextView bookauthor = (TextView) view.findViewById(R.id.bookauthor);
		TextView datecreated = (TextView) view.findViewById(R.id.datecreated);
		ImageView bookimage = (ImageView) view.findViewById(R.id.bookimage);

		bookname.setText(bookname.getText() + " "
				+ booklist.get(arg0).getBookname());
		bookauthor.setText(bookauthor.getText() + " "
				+ booklist.get(arg0).getBookauthor());
		datecreated.setText(datecreated.getText() + " "
				+ booklist.get(arg0).getDatecreated());
		bookimage.setImageResource(R.drawable.bookimage);

		return view;
	}

}
