package com.mira;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.CountDownTimer;
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

		// 获取验证码按钮
		btn_getCode = (Button) this
				.findViewById(R.id.login_activity_btn_get_code);
		btn_getCode.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				String account = loginAccount.getText().toString();
				if (StringUtils.isEmpty(account)) {
					Toast.makeText(v.getContext(), getString(R.string.msg_login_account_null), Toast.LENGTH_SHORT).show();
					return;
				} else {
					// 判断是否有效的手机号码
					Pattern p = Pattern.compile("^(1[34578])\\d{9}$");
					Matcher m = p.matcher(account);
					if (m.matches()) {
						// TODO 调用获取的接口
						TimeCount time = new TimeCount(60000, 1000);
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
				String account = loginAccount.getText().toString();
				String pwd = loginCode.getText().toString();
				// 判断输入
				if (StringUtils.isEmpty(account)) {
					Toast.makeText(v.getContext(), getString(R.string.msg_login_account_null), Toast.LENGTH_SHORT).show();
					return;
				}
				if (StringUtils.isEmpty(pwd)) {
					Toast.makeText(v.getContext(), getString(R.string.msg_login_pwd_null), Toast.LENGTH_SHORT).show();
					return;
				}
				// 保存用户信息
				User user = new User();
				user.setAccount(Integer.parseInt(account));
				AppContext.getInstance().saveUserInfo(user);

				MRLoginActivity.this.finish();
			}
		});

		tvThirdPartyLogin = (TextView) this
				.findViewById(R.id.login_activity_tv_third_party_login);
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
	class TimeCount extends CountDownTimer {
		public TimeCount(long millisInFuture, long countDownInterval) {
			super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
		}

		@Override
		public void onFinish() {// 计时完毕时触发
			btn_getCode.setText(getString(R.string.login_activity_get_code));
			btn_getCode.setClickable(true);
		}

		@Override
		public void onTick(long millisUntilFinished) {// 计时过程显示
			btn_getCode.setClickable(false);
			btn_getCode.setText(millisUntilFinished / 1000 + "秒");
		}
	}

}
