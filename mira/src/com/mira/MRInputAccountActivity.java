package com.mira;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.common.StringUtils;

public class MRInputAccountActivity extends Activity {

	/**
	 * 电话号码
	 */
	private EditText etPhoneNumber;
	
	/**
	 * 下一步按钮
	 */
	private Button btnNext;
	
	private InputMethodManager imm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mr_activity_input_account);
		
		imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
		
		etPhoneNumber = (EditText) this.findViewById(R.id.inputAccount_ev_account);
		
		btnNext = (Button) this.findViewById(R.id.inputAccount_btn_next);
		
		btnNext.setOnClickListener(new View.OnClickListener() {
     			public void onClick(View v) {
     				//隐藏软键盘
    				imm.hideSoftInputFromWindow(v.getWindowToken(), 0);  
    				//电话号码
    				String phoneNumber = etPhoneNumber.getText().toString();
    				//判断输入
    				if(StringUtils.isEmpty(phoneNumber)){
    					Toast.makeText(v.getContext(), getString(R.string.input_account_phone_number_null), Toast.LENGTH_SHORT).show();
    					return;
    				}
     				//下一步
     				Intent intent = new Intent(v.getContext(), MRInputPasswordActivity.class);
     		    	startActivity(intent);
     			}
        });
	}
}
