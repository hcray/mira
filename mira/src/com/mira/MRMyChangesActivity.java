package com.mira;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Arrays;
import java.util.List;

import com.adapter.ImageGridAdapter;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.GridView;
import android.widget.LinearLayout;

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

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mr_activity_my_changes);

		mGirdView = (GridView) this.findViewById(R.id.myChanges_gridView);

		backbtn = (LinearLayout) this.findViewById(R.id.myChangesBackbtn);
		backbtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				MRMyChangesActivity.this.finish();
			}
		});
		initGridView();

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
