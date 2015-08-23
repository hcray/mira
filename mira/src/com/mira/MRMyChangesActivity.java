package com.mira;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.adapter.ImageGridAdapter;
import com.gif.JpgToGif;

public class MRMyChangesActivity extends Activity {
	
	private final static String tag = "MRMyChangesActivity";

	private LinearLayout backbtn;

	private GridView mGirdView;

	private ImageGridAdapter mAdapter;

	/**
	 * 图片数量最多的文件夹
	 */
	private File mImgDir;
	/**
	 * 所有的图片
	 */
	private List<String> mImgs;
	
	
	/**
	 * 拍照按钮 
	 */
//	private ImageView iv_camera;
	
	/**
	 * 删除按钮
	 */
	private ImageView ivDel;
	
	/**
	 * 设置按钮
	 */
	private Button ibSetting;
	
	private JpgToGif jpgToGif;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mr_activity_my_changes);
		
		jpgToGif = new JpgToGif();
		
//		iv_camera = (ImageView) this.findViewById(R.id.my_changes_activity_iv_camera);
		
//		ibSetting = (Button) this.findViewById(R.id.myChange_setting);

		mGirdView = (GridView) this.findViewById(R.id.myChanges_gridView);
		
		ivDel = (ImageView) this.findViewById(R.id.my_changes_activity_iv_del);
		ivDel.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				List<String> selectItem = mAdapter.mSelectedImage;
				int size = selectItem.size();
				List<String> delList = new ArrayList<String>();
				for (String path : selectItem) {
					File file = new File(path);
					file.delete();
					delList.add(path);
				}
				selectItem.removeAll(delList);
				initGridView();
				Toast.makeText(v.getContext(), "删除了" + size + "张照片",Toast.LENGTH_SHORT).show();
			}
		});
		

		backbtn = (LinearLayout) this.findViewById(R.id.myChangesBackbtn);
		backbtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				MRMyChangesActivity.this.finish();
			}
		});
		initGridView();
		
//		iv_camera.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				//开启照相机
//				
//			}
//		});
		
//		ibSetting.setOnClickListener(new View.OnClickListener() {
//			
//			@Override
//			public void onClick(View v) {
//				// TODO Auto-generated method stub
//				
//			}
//		});
	}

	private void initGridView() {
		String imgPath = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/mira/Camera/";
		mImgDir = new File(imgPath);
		mImgs = Arrays.asList(mImgDir.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String filename) {
				if (filename.endsWith(".jpg") || filename.endsWith(".png")
						|| filename.endsWith(".jpeg")){
					return true;
				}
				return false;
			}
		}));
		
		//最后拍照的放到前面
		Collections.reverse(mImgs);
		/**
		 * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消耗；
		 */
		mAdapter = new ImageGridAdapter(getApplicationContext(), mImgs,
				R.layout.grid_item, mImgDir.getAbsolutePath());
		mGirdView.setAdapter(mAdapter);
	}
}
