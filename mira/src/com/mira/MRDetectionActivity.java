package com.mira;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.view.LineCharView;

public class MRDetectionActivity extends Activity {
	private LinearLayout backbtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mr_activity_detection);

		LineCharView lcv = (LineCharView) findViewById(R.id.detection_activity_lcv);
		List<String> x_coords = new ArrayList<String>();
		x_coords.add("1");
		x_coords.add("2");
		x_coords.add("3");
		x_coords.add("4");
		x_coords.add("5");
		x_coords.add("6");
		x_coords.add("7");

		List<Integer> x_coord_values = new ArrayList<Integer>();
		x_coord_values.add(0);
		x_coord_values.add(40);
		x_coord_values.add(28);
		x_coord_values.add(52);
		x_coord_values.add(90);
		x_coord_values.add(72);
		x_coord_values.add(65);
		lcv.setBgColor(Color.WHITE);
		lcv.setValue(x_coords, x_coord_values);

		backbtn = (LinearLayout) this.findViewById(R.id.detection_activity_backbtn);
		backbtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				MRDetectionActivity.this.finish();
			}
		});
	}
}
