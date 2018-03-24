package com.photobook.activity;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.ImageView;

public class pageradapter extends PagerAdapter {
	Context context;
	ArrayList<String> filepath;

	pageradapter(Context context, ArrayList<String> filepath) {
		this.context = context;
		this.filepath = filepath;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return filepath.size();
	}

	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		// TODO Auto-generated method stub
		return arg0 == ((ImageView) (arg1));
	}

	@Override
	public Object instantiateItem(View container, int position) {
		ImageView view = new ImageView(context);
		view.setPadding(10, 10, 10, 10);
		view.setScaleType(ImageView.ScaleType.FIT_XY);
		String path = filepath.get(position);
		Bitmap bp = decodeScaledBitmapFromSdCard(path, 500, 500);
		view.setImageBitmap(bp);
		((ViewPager) container).addView(view, 0);
		return view;
	}

	@Override
	public void destroyItem(View container, int position, Object object) {
		// TODO Auto-generated method stub
		((ViewPager) container).removeView((View) object);
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
