package com.mira;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.model.User;
import com.view.wheelView.JudgeDate;
import com.view.wheelView.ScreenInfo;
import com.view.wheelView.WheelMain;

public class MRUserInfoActivity extends Activity {

	private LinearLayout backbtn;

	private EditText et_sign;

	private EditText et_nickName;

	private RadioGroup rg_sex;

	private RadioButton rb_male;

	private RadioButton rb_female;

	private Button btn_save;

	private LinearLayout llBirthday;

	private LinearLayout llSex;

	private LinearLayout llNick;
	
	private TextView tvBirthday;
	
	private TextView tvSex;
	
	private TextView tvNick;

	private WheelMain wheelMain;

	DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

	private int selectSex;

	private User curUser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mr_activity_user_info);

		// et_sign = (EditText)
		// this.findViewById(R.id.userInfo_activity_et_user_sign);
		// et_nickName = (EditText)
		// this.findViewById(R.id.userInfo_activity_et_nick_name);
		// rg_sex = (RadioGroup)
		// this.findViewById(R.id.userInfo_activity_radioGroup_sex);
		// rb_male = (RadioButton)
		// this.findViewById(R.id.userInfo_activity_radioMale);
		// rb_female = (RadioButton)
		// this.findViewById(R.id.userInfo_activity_radioFemale);

		tvBirthday = (TextView) this.findViewById(R.id.userInfo_activity_tv_birthday_value);
		llBirthday = (LinearLayout) this.findViewById(R.id.userInfo_activity_ll_birthday_value);
		Calendar calendar = Calendar.getInstance();
		tvBirthday.setText(calendar.get(Calendar.YEAR) + "-"
				+ (calendar.get(Calendar.MONTH) + 1) + "-"
				+ calendar.get(Calendar.DAY_OF_MONTH) + "");
		llBirthday.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				LayoutInflater inflater = LayoutInflater
						.from(MRUserInfoActivity.this);
				final View timepickerview = inflater.inflate(
						R.layout.timepicker, null);
				ScreenInfo screenInfo = new ScreenInfo(MRUserInfoActivity.this);
				wheelMain = new WheelMain(timepickerview);
				wheelMain.screenheight = screenInfo.getHeight();
				String time = tvBirthday.getText().toString();
				Calendar calendar = Calendar.getInstance();
				if (JudgeDate.isDate(time, "yyyy-MM-dd")) {
					try {
						calendar.setTime(dateFormat.parse(time));
					} catch (ParseException e) {
						e.printStackTrace();
					}
				}
				int year = calendar.get(Calendar.YEAR);
				int month = calendar.get(Calendar.MONTH);
				int day = calendar.get(Calendar.DAY_OF_MONTH);
				wheelMain.initDateTimePicker(year, month, day);
				new AlertDialog.Builder(MRUserInfoActivity.this)
						.setTitle("生日")
						.setView(timepickerview)
						.setPositiveButton("确定",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {

										String selectTime = wheelMain.getTime();
										if(!selectTime.equalsIgnoreCase(curUser.getBirthday())){
											tvBirthday.setText(selectTime);
											curUser.setBirthday(selectTime);
											AppContext.getInstance().updateUserInfo(curUser);
										}
									}
								})
						.setNegativeButton("取消",
								new DialogInterface.OnClickListener() {
									@Override
									public void onClick(DialogInterface dialog,
											int which) {
									}
								}).show();
			}
		});

		backbtn = (LinearLayout) this.findViewById(R.id.userInfoBackbtn);
		backbtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				MRUserInfoActivity.this.finish();
			}
		});

		tvSex = (TextView) this.findViewById(R.id.userInfo_activity_tv_sex_value);
		llSex = (LinearLayout) this.findViewById(R.id.userInfo_activity_ll_sex_value);
		llSex.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						MRUserInfoActivity.this);
				builder.setTitle("性别");
				final String[] sex = { "男", "女", "保密" };

				int i = 0;
				String curSex = tvSex.getText().toString();
				if ("男".equalsIgnoreCase(curSex)) {
					i = 0;
				} else if ("女".equalsIgnoreCase(curSex)) {
					i = 1;
				} else if ("保密".equalsIgnoreCase(curSex)) {
					i = 2;
				}
				// 设置一个单项选择下拉框
				/**
				 * 第一个参数指定我们要显示的一组下拉单选框的数据集合 第二个参数代表索引，指定默认哪一个单选框被勾选上，1表示默认'女'
				 * 会被勾选上 第三个参数给每一个单选项绑定一个监听器
				 */
				builder.setSingleChoiceItems(sex, i,
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								selectSex = which;
							}
						});
				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								if(curUser.getSex() != selectSex + 1){
									tvSex.setText(sex[selectSex]);
									curUser.setSex(selectSex + 1);
									AppContext.getInstance().updateUserInfo(curUser);
								}

							}
						});
				builder.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {

							}
						});
				builder.show();
			}
		});

		tvNick = (TextView) this.findViewById(R.id.userInfo_activity_tv_nick_name_value);
		llNick = (LinearLayout) this.findViewById(R.id.userInfo_activity_ll_nick_name_value);
		llNick.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				AlertDialog.Builder builder = new AlertDialog.Builder(
						MRUserInfoActivity.this);
				builder.setTitle("昵称");
				// 通过LayoutInflater来加载一个xml的布局文件作为一个View对象
				View view = LayoutInflater.from(MRUserInfoActivity.this)
						.inflate(R.layout.nick_name, null);
				// 设置我们自己定义的布局文件作为弹出框的Content
				builder.setView(view);

				final EditText username = (EditText) view
						.findViewById(R.id.ev_nick_name);
				username.setText(tvNick.getText());

				builder.setPositiveButton("确定",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {
								String nickName = username.getText().toString().trim();
								// 将输入的用户名和密码打印出来
								if (nickName != null && !nickName.isEmpty()) {
									if(!nickName.equalsIgnoreCase(curUser.getNickName())){
										tvNick.setText(nickName);
										curUser.setNickName(nickName);
										AppContext.getInstance().updateUserInfo(curUser);
									}
								}
							}
						});
				builder.setNegativeButton("取消",
						new DialogInterface.OnClickListener() {
							@Override
							public void onClick(DialogInterface dialog,
									int which) {

							}
						});
				builder.show();
			}
		});

		// 保存按钮
		btn_save = (Button) this.findViewById(R.id.userInfo_activity_btn_save);
		btn_save.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				/*
				 * String sign = et_sign.getText().toString(); String nickName =
				 * et_nickName.getText().toString();
				 * if(StringUtils.isEmpty(nickName)){
				 * Toast.makeText(v.getContext(),
				 * getString(R.string.message_nickName_is_null),
				 * Toast.LENGTH_SHORT).show(); return; }
				 * 
				 * User user = new User(); user.setNickName(nickName);
				 * user.setSign(sign);; if(rg_sex.getCheckedRadioButtonId() ==
				 * R.id.userInfo_activity_radioMale){ user.setSex("male");
				 * }else{ user.setSex("female"); }
				 * AppContext.getInstance().saveUserInfo(user);
				 */
				MRUserInfoActivity.this.finish();
			}
		});

		initUI();
	}

	/**
	 * 初始化
	 */
	private void initUI() {
		curUser = AppContext.getInstance().getLoginUser();
		if (curUser != null) {
			tvNick.setText(curUser.getNickName());
			tvBirthday.setText(curUser.getBirthday());
			int sex = curUser.getSex();
			if (sex == 1) {
				tvSex.setText("男");
			} else if (sex == 2) {
				tvSex.setText("女");
			} else if (sex == 3) {
				tvSex.setText("保密");
			}
		}
	}
}
