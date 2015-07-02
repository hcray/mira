package com.mira;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.common.StringUtils;

public class MRLoginActivity extends Activity {
	
	private static String TAG = "MRLoginActivity";
	
	/**
	 * 账号
	 */
	private AutoCompleteTextView loginAccount;
	
	/**
	 * 密码
	 */
	private EditText loginPassword;
	
	/**
	 * 登陆按钮
	 */
	private Button btn_login;
	
	/**
	 * 忘记密码
	 */
	private TextView tvForget;
	
	/**
	 * 注册
	 */
	private TextView tvSignIn;
	
	private InputMethodManager imm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mr_activity_login);
		Log.v(TAG, "onCreate()");
		imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
		
		loginAccount = (AutoCompleteTextView) this.findViewById(R.id.login_account);
		
		loginPassword = (EditText) this.findViewById(R.id.login_password);
		
		btn_login = (Button) this.findViewById(R.id.login_activity_bt_login);
		
		tvForget = (TextView) this.findViewById(R.id.login_activity_tv_forget);
		
		tvSignIn = (TextView) this.findViewById(R.id.login_activity_tv_signIn);
		
        btn_login.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				//隐藏软键盘
				imm.hideSoftInputFromWindow(v.getWindowToken(), 0);  
				//账号 密码
				String account = loginAccount.getText().toString();
				String pwd = loginPassword.getText().toString();
				//判断输入
				if(StringUtils.isEmpty(account)){
					Toast.makeText(v.getContext(), getString(R.string.msg_login_account_null), Toast.LENGTH_SHORT).show();
					return;
				}
				if(StringUtils.isEmpty(pwd)){
					Toast.makeText(v.getContext(), getString(R.string.msg_login_pwd_null), Toast.LENGTH_SHORT).show();
					return;
				}
			}
		});
		
        //忘记密码
        tvForget.setOnClickListener(new View.OnClickListener() {
     			public void onClick(View v) {
     				//TODO
     			}
        });
        //注册
        tvSignIn.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		//TODO
        	}
        });
        
        
	}
	
	
}
