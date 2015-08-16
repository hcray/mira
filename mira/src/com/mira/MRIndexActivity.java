package com.mira;

import java.io.File;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.view.ViewPager.LayoutParams;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageButton;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewSwitcher.ViewFactory;
import cn.aigestudio.datepicker.interfaces.OnDateSelected;
import cn.aigestudio.datepicker.views.DatePicker;

import com.bll.MRTestBLL;
import com.common.MiraConstants;
import com.common.StringUtils;
import com.service.BluetoothService;
import com.utils.DateUtil;

public class MRIndexActivity extends Activity implements
	OnItemSelectedListener, ViewFactory {
	
	private Long mExitTime = 0l;
	
	private static String TAG = "MRIndexActivity";

	private Uri origUri;
	
	private String theLarge;
	
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
	//private ImageSwitcher mSwitcher;
	
	/**
	 * 日期显示
	 */
	private TextView etSelectDate;
	
	
	/**
	 * 朝左切换图片的按钮
	 */
	private ImageButton arrowLeft;

	
	/**
	 * 朝右切换图片的按钮
	 */
	private ImageButton arrowRight;
	
//	private ImageView imageView;
	
	/**
	 * 路径集合
	 */
	private List<String> imagePathList;
	
	/**
	 * 路径集合
	 */
	private String[] pathList;
	
	/**
	 * 当前位置
	 */
	private int curPosition = -1;
	
	/**
	 * 选择城市
	 */
	private TextView tvCity;
	
	/**
	 * 额头
	 */
	private TextView tvHeadValue;
	
	private ImageView ivHeadWaring;
	
	/**
	 * 脸颊
	 */
	private TextView tvFaceValue;
	
	private ImageView ivFaceWaring;
	
	/**
	 * 鼻子
	 */
	private TextView tvNoseValue;
	
	private ImageView ivNoseWaring;
	
	/**
	 * 下巴
	 */
	private TextView tvChinValue;
	
	private ImageView ivChinWaring;
	
	private LinearLayout llTestHistory;
	
	private SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mr_activity_index);
		
//		imageView = (ImageView) this.findViewById(R.id.imageView);
		etSelectDate = (TextView) this.findViewById(R.id.index_activity_selectDate);
		String picName = format.format(new Date());
		//显示今天的时间
		etSelectDate.setText(picName);
		
		arrowLeft = (ImageButton) this.findViewById(R.id.index_activity_ib_arrowLeft);
		
		arrowRight = (ImageButton) this.findViewById(R.id.index_activity_ib_arrowRight);
		
		//mSwitcher = (ImageSwitcher) this.findViewById(R.id.imgSwitcher);
		
		tvHeadValue = (TextView) this.findViewById(R.id.index_activity_tv_head_value);
		ivHeadWaring = (ImageView) this.findViewById(R.id.index_activity_iv_head_waring);
		
		tvFaceValue = (TextView) this.findViewById(R.id.index_activity_tv_face_value);
		ivFaceWaring = (ImageView) this.findViewById(R.id.index_activity_iv_face_waring);
		
		tvNoseValue = (TextView) this.findViewById(R.id.index_activity_tv_nose_value);
		ivNoseWaring = (ImageView) this.findViewById(R.id.index_activity_iv_nose_waring);
		
		tvChinValue = (TextView) this.findViewById(R.id.index_activity_tv_chin_value);
		ivChinWaring = (ImageView) this.findViewById(R.id.index_activity_iv_chin_waring);
		
		ibCamera = (ImageButton) this.findViewById(R.id.ib_camera);
		
		ibCamera.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					/*
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
		            */
					startTakePhoto();
		        } catch (Exception e) {  
		            e.printStackTrace();  
		            Log.e(TAG, "message: " + e.getMessage() + " cause:" + e.getCause());
		        }  
			}
		});
		
		imagePathList = getImagePathFromSD();
		Log.v(TAG, "imagePathList: " + imagePathList);
		pathList = imagePathList.toArray(new String[imagePathList.size()]);

		//mSwitcher.setFactory(this);
		/* 设定载入Switcher的模式 */
		//mSwitcher.setInAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_in));
		/* 设定输出Switcher的模式 */
		//mSwitcher.setOutAnimation(AnimationUtils.loadAnimation(this, android.R.anim.fade_out));
		//mSwitcher.setOnClickListener(new OnClickListener() {

			//public void onClick(View v) {
				// Toast.makeText(ImageSwitcherAndGalleryActivity.this,
				// "你点击了ImageSwitch上的图片",
				// Toast.LENGTH_SHORT).show();

			//}

		//});
		
		arrowLeft.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.i(TAG, "arrowLeft click() curPosition: " + curPosition + " pathList.length: " + pathList.length);
//				if(curPosition > 0){
//					curPosition --;
//					String photoURL = pathList[curPosition];
//					Log.i(TAG, "photoURL: " + photoURL);
//					
//					//mSwitcher.setImageURI(Uri.parse(photoURL));
//				}else{
//					Toast.makeText(v.getContext(), "已经是最前一张", Toast.LENGTH_SHORT).show();
//				}
				
				String curDateStr = etSelectDate.getText().toString();
				try {
					Date curDate = format.parse(curDateStr);
					Date shiftDate = DateUtil.getShiftDay(curDate, -1);
					String shiftDateStr = format.format(shiftDate);
					etSelectDate.setText(shiftDateStr);
					showTestRet();
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		});
		
		arrowRight.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
//				Log.i(TAG, "arrowRight click() curPosition: " + curPosition + " pathList.length: " + pathList.length);
//				if(curPosition < pathList.length-1){
//					curPosition ++;
//					String photoURL = pathList[curPosition];
//					Log.i(TAG, "photoURL: " + photoURL);
//					
//					//mSwitcher.setImageURI(Uri.parse(photoURL));
//				}else{
//					Toast.makeText(v.getContext(), "已经是最后一张", Toast.LENGTH_SHORT).show();
//				}
				
				
				String curDateStr = etSelectDate.getText().toString();
				try {
					Date curDate = format.parse(curDateStr);
					Date shiftDate = DateUtil.getShiftDay(curDate, 1);
					String shiftDateStr = format.format(shiftDate);
					etSelectDate.setText(shiftDateStr);
					showTestRet();
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		});
		
		ibCalender = (ImageButton) this.findViewById(R.id.index_activity_ib_calender);
		ibCalender.setOnClickListener(new View.OnClickListener() {
		            @Override
		            public void onClick(View v) {
		                final AlertDialog dialog = new AlertDialog.Builder(MRIndexActivity.this).create();
		                dialog.show();

		                DatePicker datePicker = new DatePicker(MRIndexActivity.this);
		                //不显示农历
		                //datePicker.isLunarDisplay(false);
		                //单选
		                datePicker.isMultiSelect(false);
		                datePicker.setOnDateSelected(new OnDateSelected() {
		                    @Override
		                    public void selected(List<String> date) {
		                        StringBuilder sb = new StringBuilder();
		                        for (String s : date) {
		                            sb.append(s).append("\n");
		                        }
		                        etSelectDate.setText(sb.toString());
		                        showTestRet();
//		                        Toast.makeText(MRIndexActivity.this, sb.toString(),Toast.LENGTH_SHORT).show();
		                        dialog.dismiss();
		                    }
		                });

		                ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(ViewGroup.LayoutParams
		                        .WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
		                dialog.getWindow().setContentView(datePicker, params);
		                dialog.getWindow().setGravity(Gravity.CENTER);
		            }
		        });
		
		/*
		if(pathList.length > 0){
			//取最后一张图片
			curPosition = pathList.length - 1;
			String photoURL = pathList[curPosition];
			Log.i(TAG, "photoURL: " + photoURL);
			mSwitcher.setImageURI(Uri.parse(photoURL));
		}
		*/
		
		tvCity = (TextView) this.findViewById(R.id.index_activity_tv_city);
		tvCity.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	Intent intent = new Intent(v.getContext(), MRCityActivity.class);
            	startActivityForResult(intent, 1);
            }
		});
		//设置定位到的城市
		SharedPreferences preferences = this.getApplicationContext().getSharedPreferences("Location", Context.MODE_PRIVATE);
		String city = preferences.getString(MiraConstants.SELECTED_CITY, "");
		if(city.isEmpty()){
			city = preferences.getString("City", "");
		}
		tvCity.setText(city);
		
		//检测历史记录
		llTestHistory = (LinearLayout) this.findViewById(R.id.index_activity_ll_test_history);
		llTestHistory.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(v.getContext(), MRHistoryActivity.class);
				startActivity(intent);
			}
		});
		
		//设置显示的数据
		showTestRet();
		
		//启动蓝牙
		BluetoothAdapter mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
		if (mBluetoothAdapter == null || !mBluetoothAdapter.isEnabled()){
			Log.d(TAG, "start bluetooth...");
			Intent enableBtIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
			startActivity(enableBtIntent);
		} else {
			Log.d(TAG, "start bluetooth servcie...");
			Intent intent = new Intent(MRIndexActivity.this, BluetoothService.class);
		    startService(intent);
		}
	}

	/**
	 * 开启相机
	 */
    private void startTakePhoto() {
        Intent intent;
        // 判断是否挂载了SD卡
        String savePath = "";
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            savePath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/mira/Camera/";
            File savedir = new File(savePath);
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
        }

        // 没有挂载SD卡，无法保存文件
        if (StringUtils.isEmpty(savePath)) {
        	Toast.makeText(this.getBaseContext(), "无法保存照片，请检查SD卡是否挂载", Toast.LENGTH_SHORT).show();
            return;
        }

        Log.v(TAG, "savePath: " + savePath);
        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        String fileName = "mira_" + timeStamp + ".jpg";// 照片命名
        File out = new File(savePath, fileName);
        Uri uri = Uri.fromFile(out);
        origUri = uri;

        theLarge = savePath + fileName;// 该照片的绝对路径

        intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
        startActivityForResult(intent, 0);
    }
	
    /***
     * 返回方法
     */
	@Override 
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {  
        try {  
            Log.v(TAG, "onActivityResult requestCode = " + requestCode + "; resultCode =  " 
                    + resultCode);  
            if (requestCode == 1 && resultCode == RESULT_OK) {  
            	Bundle bundle = data.getExtras();
            	String selectCity = bundle.getString(MiraConstants.SELECTED_CITY);
            	if(null != selectCity && !selectCity.isEmpty()){
            		tvCity.setText(selectCity);
            	}
            	
            }  
            if (resultCode == 0) {  
                //finish();  
                return;  
            }
            
            //SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd", Locale.CHINA);
            //String picName = format.format(new Date());
            
           // String pathString = Environment.getExternalStorageDirectory().toString() + "/mira/" + picName + ".jpg";  
            
            //data.getExtras()
           // Log.v(TAG, "pathString = " + pathString);  
            
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

		/*
		String status = Environment.getExternalStorageState();
		if (status.equals(Environment.MEDIA_MOUNTED)) {
			
		}else {
			//showToast("无存储卡!");
		}
		// 根据自己的需求读取SDCard中的资源图片的路径
		String imagePath = Environment.getExternalStorageDirectory().toString()
				+ "/mira/";

		 */
        // 判断是否挂载了SD卡
        String savePath = "";
        String storageState = Environment.getExternalStorageState();
        if (storageState.equals(Environment.MEDIA_MOUNTED)) {
            savePath = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + "/mira/Camera/";
            File savedir = new File(savePath);
            if (!savedir.exists()) {
                savedir.mkdirs();
            }
        }
        
        if(StringUtils.isEmpty(savePath)){
        	//Toast.makeText(this.getBaseContext(), "无法保存照片，请检查SD卡是否挂载", Toast.LENGTH_SHORT).show();
        }else{
			File mFile = new File(savePath);
			File[] files = mFile.listFiles();
			/* 将所有文件存入ArrayList中 */
			for (int i = 0; i < files.length; i++) {
				File file = files[i];
				if (checkIsImageFile(file.getPath()))
					it.add(file.getPath());
			}
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


	@Override
	public View makeView() {
		ImageView iv = new ImageView(this);
		iv.setBackgroundColor(0xFF000000);
		iv.setScaleType(ImageView.ScaleType.FIT_CENTER);
		iv.setLayoutParams(new ImageSwitcher.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT));
		return iv;
	}

	@Override
	public void onItemSelected(AdapterView<?> parent, View view, int position,
			long id) {
		// TODO Auto-generated method stub
		String photoURL = pathList[position];
		Log.i("A", String.valueOf(position));

		//mSwitcher.setImageURI(Uri.parse(photoURL));
	}

	@Override
	public void onNothingSelected(AdapterView<?> parent) {
		// TODO Auto-generated method stub
		
	}
	
	
	private void showTestRet(){
		//当前显示数据的日期
		String curDateStr = etSelectDate.getText().toString();
		try {
			Date curDate = format.parse(curDateStr);
			//开始时间
			long startTime = DateUtil.getTimesMorning(curDate);
			//结束时间
			long endTime = DateUtil.getTimesNight(curDate);
			int headValue = MRTestBLL.getTestMax(MiraConstants.PART_HEAD, startTime, endTime, MRIndexActivity.this);
			int faceValue = MRTestBLL.getTestMax(MiraConstants.PART_FACE, startTime, endTime, MRIndexActivity.this);
			int noseValue = MRTestBLL.getTestMax(MiraConstants.PART_NOSE, startTime, endTime, MRIndexActivity.this);
			int chinValue = MRTestBLL.getTestMax(MiraConstants.PART_CHIN, startTime, endTime, MRIndexActivity.this);
			
			//今日的最小值、最大值
			int minValue = headValue;
			int maxValue = headValue;
			
			if(faceValue < minValue){
				minValue = faceValue;
			}
			if(noseValue < minValue){
				minValue = noseValue;
			}
			if(chinValue < minValue){
				minValue = chinValue;
			}
			
			if(faceValue > maxValue){
				maxValue = faceValue;
			}
			if(noseValue > maxValue){
				maxValue = noseValue;
			}
			if(chinValue > maxValue){
				maxValue = chinValue;
			}
			
			tvHeadValue.setText(String.valueOf(headValue));
			if(headValue == minValue){
				ivHeadWaring.setVisibility(View.VISIBLE);
				tvHeadValue.setTextColor(Color.parseColor("#FD9A00"));
			} else {
				ivHeadWaring.setVisibility(View.GONE);
				tvHeadValue.setTextColor(Color.parseColor("#81d8cf"));
			}
			
			tvFaceValue.setText(String.valueOf(faceValue));
			if(faceValue == minValue){
				ivFaceWaring.setVisibility(View.VISIBLE);
				tvFaceValue.setTextColor(Color.parseColor("#FD9A00"));
			} else {
				ivFaceWaring.setVisibility(View.GONE);
				tvFaceValue.setTextColor(Color.parseColor("#81d8cf"));
			}
			
			tvNoseValue.setText(String.valueOf(noseValue));
			if(noseValue == minValue){
				ivNoseWaring.setVisibility(View.VISIBLE);
				tvNoseValue.setTextColor(Color.parseColor("#FD9A00"));
			} else {
				ivNoseWaring.setVisibility(View.GONE);
				tvNoseValue.setTextColor(Color.parseColor("#81d8cf"));
			}
			
			tvChinValue.setText(String.valueOf(chinValue));
			if(chinValue == minValue){
				ivChinWaring.setVisibility(View.VISIBLE);
				tvChinValue.setTextColor(Color.parseColor("#FD9A00"));
			} else {
				ivChinWaring.setVisibility(View.GONE);
				tvChinValue.setTextColor(Color.parseColor("#81d8cf"));
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 监听返回--是否退出程序
	 */
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean flag = true;
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if ((System.currentTimeMillis() - mExitTime) > 2000) {
				Toast.makeText(this, R.string.back_exit_tips, 2000).show();
				mExitTime = System.currentTimeMillis();
			} else {
				Intent intent = new Intent(MRIndexActivity.this, BluetoothService.class);
			    stopService(intent);
				int pid = android.os.Process.myTid();
	            android.os.Process.killProcess(pid);
			}
		return flag;
		}
		return super.onKeyDown(keyCode, event);
	}

}
