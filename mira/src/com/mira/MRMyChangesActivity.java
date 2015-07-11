package com.mira;

import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import com.adapter.ImageGridAdapter;
import com.gif.JpgToGif;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MRMyChangesActivity extends Activity {

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
	 * 分享按钮 
	 */
	private ImageButton ibShare;
	
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
		
		ibShare = (ImageButton) this.findViewById(R.id.myChanges_share);
		
		ibSetting = (Button) this.findViewById(R.id.myChange_setting);

		mGirdView = (GridView) this.findViewById(R.id.myChanges_gridView);

		backbtn = (LinearLayout) this.findViewById(R.id.myChangesBackbtn);
		backbtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				MRMyChangesActivity.this.finish();
			}
		});
		initGridView();

		
		ibShare.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				int checkedItemCount = mGirdView.getCheckedItemCount();
				Toast.makeText(v.getContext(), "选中了" + checkedItemCount +"张图片", Toast.LENGTH_SHORT).show();
				/*
				String gifSavePath = Environment.getExternalStorageDirectory()
		                    .getAbsolutePath() + "/mira/gif/";
		            File gifSavedir = new File(gifSavePath);
		            if (!gifSavedir.exists()) {
		            	gifSavedir.mkdirs();
		            }
		            
		            String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		            String gifFileName = "mira_" + timeStamp + ".gif";// 照片命名
		            
		            String imgPath = Environment.getExternalStorageDirectory()
		    				.getAbsolutePath() + "/mira/Camera/";
		            
		            String[] paths = new String[mImgs.size()];
		            for (int i = 0; i < mImgs.size(); i++) {
		            	paths[i] = imgPath + mImgs.get(i);
		            }
		            
		            jpgToGif.jpgToGif(paths, gifSavePath + gifFileName);
				*/
			}
		});
		
		ibSetting.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
		});
	}

	private void initGridView() {
		String imgPath = Environment.getExternalStorageDirectory()
				.getAbsolutePath() + "/mira/Camera/";
		mImgDir = new File(imgPath);
		mImgs = Arrays.asList(mImgDir.list(new FilenameFilter() {
			@Override
			public boolean accept(File dir, String filename) {
				if (filename.endsWith(".jpg") || filename.endsWith(".png")
						|| filename.endsWith(".jpeg"))
					return true;
				return false;
			}
		}));
		/**
		 * 可以看到文件夹的路径和图片的路径分开保存，极大的减少了内存的消耗；
		 */
		mAdapter = new ImageGridAdapter(getApplicationContext(), mImgs,
				R.layout.grid_item, mImgDir.getAbsolutePath());
		mGirdView.setAdapter(mAdapter);
	}
}
