package com.mira;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.Header;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
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

import com.bean.ResultBean;
import com.bean.RetUserBean;
import com.common.MiraConstants;
import com.common.StringUtils;
import com.google.gson.Gson;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.model.User;
import com.utils.CountDownTimerUtil;
import com.utils.CyptoUtils;
import com.utils.HttpKit;

public class MRLoginActivity extends Activity {

	private static String TAG = "MRLoginActivity";

	/**
	 * 账号
	 */
	private AutoCompleteTextView loginAccount;

	/**
	 * 验证码
	 */
	private EditText loginCode;

	/**
	 * 获取验证码
	 */
	private Button btn_getCode;

	/**
	 * 登陆按钮
	 */
	private Button btn_login;

	/**
	 * 返回按钮
	 */
	private LinearLayout backbtn;

	/**
	 * 第三方登录
	 */
	private TextView tvThirdPartyLogin;

	/**
	 * 第三方登录详细
	 */
	private LinearLayout lyThirdPartyAccountDetail;

	private InputMethodManager imm;
	
	private TimeCount time;
	
	private String account;
	
	private String UUID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mr_activity_login);
		Log.v(TAG, "onCreate()");
		imm = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

		lyThirdPartyAccountDetail = (LinearLayout) this
				.findViewById(R.id.login_activity_ly_third_party_account_detail);

		loginAccount = (AutoCompleteTextView) this
				.findViewById(R.id.login_account);

		loginCode = (EditText) this.findViewById(R.id.login_vcode);

		UUID = AppContext.getInstance().getAppId();
		// 获取验证码按钮
		btn_getCode = (Button) this.findViewById(R.id.login_activity_btn_get_code);
		btn_getCode.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				account = loginAccount.getText().toString();
				if (StringUtils.isEmpty(account)) {
					Toast.makeText(v.getContext(), getString(R.string.msg_login_account_null), Toast.LENGTH_SHORT).show();
					return;
				} else {
					// 判断是否有效的手机号码
					Pattern p = Pattern.compile("^(1[34578])\\d{9}$");
					Matcher m = p.matcher(account);
					if (m.matches()) {
	                	HttpKit.getVerificationCode(UUID , account, mHandler);
						//验证码按钮可用倒计时
						time = new TimeCount(60000, 1000);
						time.start();
						
					} else {
						Toast.makeText(v.getContext(), getString(R.string.msg_login_account_invalid), Toast.LENGTH_SHORT).show();
						return;
					}
				}

			}
		});

		btn_login = (Button) this.findViewById(R.id.login_activity_bt_login);
		btn_login.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				// 隐藏软键盘
				imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
				// 账号 密码
				account = loginAccount.getText().toString();
				String pwd = loginCode.getText().toString();
				// 判断输入
				if (StringUtils.isEmpty(account)) {
					Toast.makeText(v.getContext(), getString(R.string.msg_login_account_null), Toast.LENGTH_SHORT).show();
					return;
				}
				if (StringUtils.isEmpty(pwd)) {
					Toast.makeText(v.getContext(), getString(R.string.msg_login_pwd_null), Toast.LENGTH_SHORT).show();
					return;
				} else {
					// 判断是否有效的手机号码
					Pattern p = Pattern.compile("^(1[34578])\\d{9}$");
					Matcher m = p.matcher(account);
					if (m.matches()) {
						HttpKit.userLogin(UUID, account, MiraConstants.LOGIN_TYPE_DYNAMIC, pwd, handler);
						Toast.makeText(v.getContext(), getString(R.string.msg_login_loging), Toast.LENGTH_SHORT).show();
						
					} else {
						Toast.makeText(v.getContext(), getString(R.string.msg_login_account_invalid), Toast.LENGTH_SHORT).show();
						return;
					}
				}
			}
		});

		tvThirdPartyLogin = (TextView) this.findViewById(R.id.login_activity_tv_third_party_login);
		// 展开第三方登录
		tvThirdPartyLogin.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				int visibleValue = lyThirdPartyAccountDetail.getVisibility();
				// 点击的时候如果是展开的，则隐藏
				if (View.VISIBLE == visibleValue) {
					// 隐藏
					lyThirdPartyAccountDetail.setVisibility(View.INVISIBLE);
					// 箭头朝下
					Drawable arrowDownDrawable = getResources().getDrawable(
							R.drawable.arrow_down);
					arrowDownDrawable.setBounds(0, 0,
							arrowDownDrawable.getMinimumWidth(),
							arrowDownDrawable.getMinimumHeight());
					tvThirdPartyLogin.setCompoundDrawables(null, null, null,
							arrowDownDrawable);

				} else {
					// 展开
					lyThirdPartyAccountDetail.setVisibility(View.VISIBLE);
					// 箭头朝上
					Drawable arrowUpDrawable = getResources().getDrawable(
							R.drawable.arrow_up);
					arrowUpDrawable.setBounds(0, 0,
							arrowUpDrawable.getMinimumWidth(),
							arrowUpDrawable.getMinimumHeight());
					tvThirdPartyLogin.setCompoundDrawables(null, null, null,
							arrowUpDrawable);

				}
			}
		});

		// 返回按钮
		backbtn = (LinearLayout) this.findViewById(R.id.activityLoginBackbtn);
		backbtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				MRLoginActivity.this.finish();
			}
		});

	}

	/**
	 * 重新发送信息的定时器
	 * @author 21829
	 *
	 */
	class TimeCount extends CountDownTimerUtil {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		}

		@Override
		public void onFinish() {// 计时完毕时触发
			if(!isFinishing()){
				btn_getCode.setBackgroundColor(Color.parseColor("#49c6bd"));
				btn_getCode.setTextColor(Color.WHITE);
				btn_getCode.setText(getString(R.string.login_activity_get_code));
				btn_getCode.setClickable(true);
			}
		}

		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
			if(isFinishing()){
				time.cancel();
			}else{
				btn_getCode.setClickable(false);
				btn_getCode.setBackgroundColor(Color.parseColor("#e5e5e5"));
				btn_getCode.setTextColor(Color.parseColor("#070606"));
				btn_getCode.setText("重新获取"+millisUntilFinished / 1000 + "秒");
			}
		}
	}
	
	/**
	 * 获取验证码
	 */
	private final JsonHttpResponseHandler mHandler = new JsonHttpResponseHandler() {

		@Override
		public void onFailure(int statusCode, Header[] headers,
				Throwable throwable, JSONObject errorResponse) {
			Log.d(TAG, "mHandler: " + statusCode);
			Toast.makeText(MRLoginActivity.this, getString(R.string.tip_no_internet), Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			Log.d(TAG, "mHandler: " + response.toString());
			Gson gson = new Gson();
			ResultBean retBean = gson.fromJson(response.toString(), ResultBean.class);
			Log.d(TAG, "mHandler:ResultCode: " + retBean.getResultCode());
			//未授权
			if(retBean.getResultCode() == -2){
				Log.d(TAG, "mHandler: ResultCode == -2");
				String token = CyptoUtils.encodeMd5(UUID + "mira2015");
				HttpKit.createToken(UUID, "1", token, cHandler);
			} else if(retBean.getResultCode() == 0){
				Toast.makeText(MRLoginActivity.this, getString(R.string.msg_vcode_send_success), Toast.LENGTH_SHORT).show();
				
			} else {
				Toast.makeText(MRLoginActivity.this, getString(R.string.msg_vcode_send_fail), Toast.LENGTH_SHORT).show();
				
			}
		}
	};
	
	/**
	 * 创建token
	 */
	private final JsonHttpResponseHandler cHandler = new JsonHttpResponseHandler() {
		
		@Override
		public void onFailure(int statusCode, Header[] headers,
				Throwable throwable, JSONObject errorResponse) {
			Log.d(TAG, "cHandler: " + statusCode);
			Toast.makeText(MRLoginActivity.this, getString(R.string.tip_no_internet), Toast.LENGTH_SHORT).show();
		}
		
		@Override
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			Log.d(TAG, "cHandler: " + response.toString());
			Gson gson = new Gson();
			ResultBean retBean = gson.fromJson(response.toString(), ResultBean.class);
			//成功
			if(retBean.getResultCode() == 0){
            	HttpKit.getVerificationCode(UUID , account, mHandler);
			}
		}
	};
	
	/**
	 * 登录
	 */
	private final JsonHttpResponseHandler handler = new JsonHttpResponseHandler() {
		
		@Override
		public void onFailure(int statusCode, Header[] headers,
				Throwable throwable, JSONObject errorResponse) {
			Log.d(TAG, "handler: " + errorResponse.toString());
			Toast.makeText(MRLoginActivity.this, getString(R.string.tip_login_error_for_network), Toast.LENGTH_SHORT).show();
		}
		
		@Override
		public void onSuccess(int statusCode, Header[] headers,
				JSONObject response) {
			Log.d(TAG, "handler: " + response.toString());
			Gson gson = new Gson();
			RetUserBean retBean = gson.fromJson(response.toString(), RetUserBean.class);
			//成功
			if(retBean.getResultCode() == 0){
				// 保存用户信息
				User user = new User();
				user.setAccount(account);
				user.setUserId(retBean.getUserId());
				user.setNickName(retBean.getNickName());
				user.setBirthday(retBean.getBirthday());
				user.setSex(retBean.getSex());
				user.setFace(retBean.getPhoto());
				AppContext.getInstance().saveUserInfo(user);
				//跳转主页面
	    		Intent intent = new Intent(MRLoginActivity.this, MRMainActivity.class);
	    		startActivity(intent);
				MRLoginActivity.this.finish();
			} else {
				Toast.makeText(MRLoginActivity.this, retBean.getMessage(), Toast.LENGTH_SHORT).show();
			}
		}
	};

}
