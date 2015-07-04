package com.mira;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class MRGradesRuleActivity extends Activity {

	private LinearLayout backbtn;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mr_activity_grades_rule);

		backbtn = (LinearLayout) this.findViewById(R.id.gradesRuleBackbtn);
		backbtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				MRGradesRuleActivity.this.finish();
			}
		});
	}
}
