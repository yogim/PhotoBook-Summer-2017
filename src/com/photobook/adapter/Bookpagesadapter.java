package com.photobook.adapter;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.util.Log;

import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

public class Bookpagesadapter extends BaseAdapter {
	Context context;
	public ArrayList<String> list;

	public Bookpagesadapter(Context context, ArrayList<String> list) {
		super();
		this.context = context;
		this.list = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return list.size();
	}

	@Override
	public String getItem(int arg0) {
		// TODO Auto-generated method stub
		return list.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int arg0, View vi, ViewGroup arg2) {

		ImageView imageView;
		if (vi == null) {
			imageView = new ImageView(this.context);
			imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
			imageView.setLayoutParams(new GridView.LayoutParams(150, 150));
			imageView.setPadding(4, 4, 4, 4);
		} else {
			imageView = (ImageView) vi;
		}

		Log.v("image path-->", "path-" + list.get(arg0));

		Bitmap bp = decodeScaledBitmapFromSdCard(list.get(arg0), 150, 150);

		imageView.setImageBitmap(bp);
		// imageView.setImageURI(Uri.parse(list.get(arg0)));
		return imageView;

	}

	public static Bitmap decodeScaledBitmapFromSdCard(String filePath,
			int reqWidth, int reqHeight) {

		// First decode with inJustDecodeBounds=true to check dimensions
		final BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(filePath, options);

		// Calculate inSampleSize
		options.inSampleSize = calculateInSampleSize(options, reqWidth,
				reqHeight);

		// Decode bitmap with inSampleSize set
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeFile(filePath, options);
	}

	public static int calculateInSampleSize(BitmapFactory.Options options,
			int reqWidth, int reqHeight) {
		// Raw height and width of image
		final int height = options.outHeight;
		final int width = options.outWidth;
		int inSampleSize = 1;

		while ((height / inSampleSize) > reqHeight
				&& (width / inSampleSize) > reqWidth) {
			inSampleSize *= 2;
		}

		return inSampleSize;
	}

}
