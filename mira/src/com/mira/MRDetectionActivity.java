package com.mira;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MRDetectionActivity extends Activity implements OnClickListener{

	private TextView tvHead;
	
	private TextView tvFace;
	
	private TextView tvNose;
	
	private TextView tvChin;
	
	private TextView menuSelected;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mr_activity_detection);
		
		tvHead = (TextView) this.findViewById(R.id.detection_activity_head);
		
		tvFace = (TextView) this.findViewById(R.id.detection_activity_face);
		
		tvNose = (TextView) this.findViewById(R.id.detection_activity_nose);
		
		tvChin = (TextView) this.findViewById(R.id.detection_activity_chin);
		
		menuSelected = (TextView) this.findViewById(R.id.detection_activity_menu_selected);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.detection_activity_head:
		    
			break;
		case R.id.detection_activity_face:
			
			break;
		case R.id.detection_activity_nose:
			
			break;
		case R.id.detection_activity_chin:
			
			break;
		default:
			break;
		}
		
	}
}
