package com.mira;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.common.StringUtils;
import com.model.User;

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
	
	/**
	 * 返回按钮
	 */
	private LinearLayout backbtn;
	
	/**
	 * 第三方登录
	 */
	private TextView tvThirdPartyLogin;
	
	/**
	 *第三方登录详细 
	 */
	private LinearLayout lyThirdPartyAccountDetail;
	
	private InputMethodManager imm;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mr_activity_login);
		Log.v(TAG, "onCreate()");
		imm = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);
		
		lyThirdPartyAccountDetail = (LinearLayout) this.findViewById(R.id.login_activity_ly_third_party_account_detail);
		
		loginAccount = (AutoCompleteTextView) this.findViewById(R.id.login_account);
		
		loginPassword = (EditText) this.findViewById(R.id.login_password);
		
		btn_login = (Button) this.findViewById(R.id.login_activity_bt_login);
		
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
				//保存用户信息
				User user = new User();
				user.setAccount(Integer.parseInt(account));
				AppContext.getInstance().saveUserInfo(user);
				
				MRLoginActivity.this.finish();
			}
		});
		
        tvThirdPartyLogin = (TextView) this.findViewById(R.id.login_activity_tv_third_party_login);
        //展开第三方登录
        tvThirdPartyLogin.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		int visibleValue = lyThirdPartyAccountDetail.getVisibility();
        		if(View.VISIBLE == visibleValue){
        			lyThirdPartyAccountDetail.setVisibility(View.INVISIBLE);
//        			Drawable rightDrawable = getResources().getDrawable(R.drawable.icon_new);
//        			rightDrawable.setBounds(0, 0, rightDrawable.getMinimumWidth(), rightDrawable.getMinimumHeight());
//        			tvVersionStatus.setCompoundDrawables(null, null, rightDrawable, null);

        		}else{
        			lyThirdPartyAccountDetail.setVisibility(View.VISIBLE);
        			
        		}
        	}
        });
        
        
        //忘记密码
        tvForget.setOnClickListener(new View.OnClickListener() {
     			public void onClick(View v) {
     				Intent intent = new Intent(v.getContext(), MRInputAccountActivity.class);
     		    	startActivity(intent);
     			}
        });
        
        //注册
        tvSignIn.setOnClickListener(new View.OnClickListener() {
        	public void onClick(View v) {
        		Intent intent = new Intent(v.getContext(), MRInputAccountActivity.class);
        		startActivity(intent);
        	}
        });
        
        //返回按钮
    	backbtn = (LinearLayout) this.findViewById(R.id.activityLoginBackbtn);
		backbtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				MRLoginActivity.this.finish();
			}
		});
        
	}
	
	
}
