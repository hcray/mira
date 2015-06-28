package com.mira;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ViewSwitcher.ViewFactory;

public class MRIndexActivity extends Activity implements
	OnItemSelectedListener, ViewFactory {
	
	private static String TAG = "MRIndexActivity";
	
	/**
	 * 相机 
	 */
	private ImageButton ibCamera;
	
	/**
	 * 日期选择
	 */
	private ImageButton ibCalender;
	
	/**
	 * 图片展示
	 */
	private ImageSwitcher mSwitcher;
	
	/**
	 * 日期显示
	 */
	private EditText etSelectDate;
	
//	private ImageView imageView;
	
	private List<String> imagePathList;
	private String[] list;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mr_activity_index);
		
//		imageView = (ImageView) this.findViewById(R.id.imageView);
		etSelectDate = (EditText) this.findViewById(R.id.et_selectDate);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		String picName = format.format(new Date());
		etSelectDate.setText(picName);
		
		
		mSwitcher = (ImageSwitcher) this.findViewById(R.id.imgSwitcher);
		
		ibCamera = (ImageButton) this.findViewById(R.id.ib_camera);
		
		ibCamera.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {  
		            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE); 
		            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
		            String picName = format.format(new Date());
		            
		            File out = new File(Environment.getExternalStorageDirectory().toString() + "/mira", picName + ".jpg");
		            if (!out.getParentFile().exists()) {
		            	out.getParentFile().mkdirs();
					}
		            Log.v(TAG, "Environment.getExternalStorageDirectory(): " + Environment.getExternalStorageDirectory());
		            Uri uri = Uri.fromFile(out);  
		            intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);  
		            startActivityForResult(intent, 0); 
		            Log.v(TAG, "startActivityForResult");
		        } catch (Exception e) {  
		            e.printStackTrace();  
		            Log.e(TAG, "message: " + e.getMessage() + " cause:" + e.getCause());
		        }  
			}
		});
		
		imagePathList = getImagePathFromSD();
		Log.v(TAG, "imagePathList: " + imagePathList);
		list = imagePathList.toArray(new String[imagePathList.size()]);

		mSwitcher.setFactory(this);
		/* 设定载入Switcher的模式 */
		mSwitcher.setInAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.fade_in));
		/* 设定输出Switcher的模式 */
		mSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this,
				android.R.anim.fade_out));
		mSwitcher.setOnClickListener(new OnClickListener() {

			public void onClick(View v) {
				// Toast.makeText(ImageSwitcherAndGalleryActivity.this,
				// "你点击了ImageSwitch上的图片",
				// Toast.LENGTH_SHORT).show();

			}

		});
		
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mrindex, menu);
		return true;
	}
	
	@Override 
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        try {  
            Log.v(TAG, "onActivityResult requestCode = " + requestCode + "; resultCode =  " 
                    + resultCode);  
            if (requestCode != 0) {  
                return;  
            }  
            if (resultCode == 0) {  
                //finish();  
                return;  
            }
            
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            String picName = format.format(new Date());
            
            String pathString = Environment.getExternalStorageDirectory()  
                    .toString() + "/mira/" + picName + ".jpg";  
            Log.v(TAG, "pathString = " + pathString);  
//            Bitmap b = BitmapFactory.decodeFile(pathString);  
//            imageView.setImageBitmap(b); 
 
        } catch (Exception e) {  
            Log.e(TAG, e.getMessage());  
        }  
    }  
	
	/** 从SD卡中获取资源图片的路径 */
	private List<String> getImagePathFromSD() {
		/* 设定目前所在路径 */
		List<String> it = new ArrayList<String>();

		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			
		}else {
//			showToast("无存储卡!");
		}
		// 根据自己的需求读取SDCard中的资源图片的路径
		String imagePath = Environment.getExternalStorageDirectory().toString()
				+ "/mira/";

		File mFile = new File(imagePath);
		File[] files = mFile.listFiles();

		/* 将所有文件存入ArrayList中 */
		for (int i = 0; i < files.length; i++) {
			File file = files[i];
			if (checkIsImageFile(file.getPath()))
				it.add(file.getPath());
		}
		return it;
	}

	/** 判断是否相应的图片格式 */
	private boolean checkIsImageFile(String fName) {
		boolean isImageFormat;

		/* 取得扩展名 */
		String end = fName
				.substring(fName.lastIndexOf(".") + 1, fName.length())
				.toLowerCase();

		/* 按扩展名的类型决定MimeType */
		if (end.equals("jpg") || end.equals("gif") || end.equals("png")
				|| end.equals("jpeg") || end.equals("bmp")) {
			isImageFormat = true;
		} else {
			isImageFormat = false;
		}
		return isImageFormat;
	}

	/* 改写BaseAdapter自定义一ImageAdapter class */
	public class ImageAdapter extends BaseAdapter {
		/* 声明变量 */
		int mGalleryItemBackground;
		private Context mContext;
		private List<String> lis;

		/* ImageAdapter的构造符 */
		public ImageAdapter(Context c, List<String> li) {
			mContext = c;
			lis = li;
			/*
			 * 使用res/values/attrs.xml中的<declare-styleable>定义 的Gallery属性.
			 */
			//TypedArray mTypeArray = obtainStyledAttributes(R.styleable.Gallery);
			/* 取得Gallery属性的Index id */
			//mGalleryItemBackground = mTypeArray.getResourceId(R.styleable.Gallery_android_galleryItemBackground, 0);
			/* 让对象的styleable属性能够反复使用 */
			//mTypeArray.recycle();
		}

		/* 重写的方法getCount,传回图片数目 */
		public int getCount() {
			return lis.size();
		}

		/* 重写的方法getItem,传回position */
		public Object getItem(int position) {
			return position;
		}

		/* 重写的方法getItemId,传并position */
		public long getItemId(int position) {
			return position;
		}

		/* 重写方法getView,传并几View对象 */
		public View getView(int position, View convertView, ViewGroup parent) {
			/* 产生ImageView对象 */
			ImageView i = new ImageView(mContext);
			/* 设定图片给imageView对象 */
			Bitmap bm = BitmapFactory.decodeFile(lis.get(position).toString());
			i.setImageBitmap(bm);
			/* 重新设定图片的宽高 */
			i.setScaleType(ImageView.ScaleType.FIT_XY);
			/* 重新设定Layout的宽高 */
			//i.setLayoutParams(new Gallery.LayoutParams(136, 88));
			/* 设定Gallery背景图 */
			i.setBackgroundResource(mGalleryItemBackground);
			/* 传回imageView对象 */
			return i;
		}
	}

	@Override
	public View makeView() {
		ImageView iv = new ImageView(this);
		iv.setBackgroundColor(0xFF000000);
		iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
		iv.setLayoutParams(new ImageSwitcher.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		return iv;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		String photoURL = list[position];
		Log.i("A", String.valueOf(position));

		mSwitcher.setImageURI(Uri.parse(photoURL));
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}

}
