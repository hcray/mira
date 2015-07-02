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

public class MRInputPasswordActivity extends Activity {
	
	private EditText etVcode;
	
	private EditText etPassword;
	
	private Button btnNext;

	private Button btnResetSend;
	
	private InputMethodManager imm;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mr_activity_input_password);
		
		imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
		
		etVcode = (EditText) this.findViewById(R.id.inputPassword_et_vcode);
		
		etPassword = (EditText) this.findViewById(R.id.inputPassword_et_password);
		
		btnResetSend = (Button) this.findViewById(R.id.inputPassword_btn_reset_send);
		
		btnResetSend.setOnClickListener(new View.OnClickListener() {
     			public void onClick(View v) {
     				//TODO 
     				//发送验证码给手机
     			}
        });
		
		btnNext = (Button) this.findViewById(R.id.inputPassword_btn_next);
		
		btnNext.setOnClickListener(new View.OnClickListener() {
     			public void onClick(View v) {
     				//隐藏软键盘
    				imm.hideSoftInputFromWindow(v.getWindowToken(), 0);  
    				//验证码
    				String vcode = etVcode.getText().toString();
    				//输入的密码
    				String password = etPassword.getText().toString();
    				//判断输入的验证码
    				if(StringUtils.isEmpty(vcode)){
    					Toast.makeText(v.getContext(), getString(R.string.inputPassword_vcode_is_null), Toast.LENGTH_SHORT).show();
    					return;
					} else {
    					//TODO
						//判断验证码的正确性
    				}
    				
    				//判断输入的密码
    				if(StringUtils.isEmpty(password)){
    					Toast.makeText(v.getContext(), getString(R.string.inputPassword_password_is_null), Toast.LENGTH_SHORT).show();
    					return;
    				}
    				//TODO//传递到下一步
     				//下一步
     				Intent intent = new Intent(v.getContext(), MRCompleteInfoActivity.class);
     		    	startActivity(intent);
     			}
        });
	}
}
