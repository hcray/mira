package com.mira;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.model.User;

public class MRUserInfoActivity extends Activity {

	private LinearLayout backbtn;
	
	private EditText et_sign;

	private EditText et_nickName;

	private RadioGroup rg_sex;
	
	private RadioButton rb_male;
	
	private RadioButton rb_female;
	
	private Button btn_save;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mr_activity_user_info);
		
//		et_sign = (EditText) this.findViewById(R.id.userInfo_activity_et_user_sign);
//		et_nickName = (EditText) this.findViewById(R.id.userInfo_activity_et_nick_name);
//		rg_sex = (RadioGroup) this.findViewById(R.id.userInfo_activity_radioGroup_sex);
//		rb_male = (RadioButton) this.findViewById(R.id.userInfo_activity_radioMale);
//		rb_female = (RadioButton) this.findViewById(R.id.userInfo_activity_radioFemale);
		
		backbtn = (LinearLayout) this.findViewById(R.id.userInfoBackbtn);
		backbtn.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				MRUserInfoActivity.this.finish();
			}
		});
		
		//保存按钮
		btn_save = (Button) this.findViewById(R.id.userInfo_activity_btn_save);
		btn_save.setOnClickListener(new View.OnClickListener() {
			public void onClick(View v) {
				/*String sign = et_sign.getText().toString();
				String nickName = et_nickName.getText().toString();
				if(StringUtils.isEmpty(nickName)){
					Toast.makeText(v.getContext(), getString(R.string.message_nickName_is_null), Toast.LENGTH_SHORT).show();
					return;
				}
				
				User user = new User();
				user.setNickName(nickName);
				user.setSign(sign);;
				if(rg_sex.getCheckedRadioButtonId() == R.id.userInfo_activity_radioMale){
					user.setSex("male");
				}else{
					user.setSex("female");
				}
				AppContext.getInstance().saveUserInfo(user);*/
				MRUserInfoActivity.this.finish();
			}
		});
		
		initUI();
	}
	
	
	/**
	 * 初始化
	 */
	private void initUI(){
		/*User user = AppContext.getInstance().getLoginUser();
		et_nickName.setText(user.getNickName());
		et_sign.setText(user.getSign());
		String sex = user.getSex();
		if("male".equalsIgnoreCase(sex)){
			rb_male.setChecked(true);
		}else if("female".equalsIgnoreCase(sex)){
			rb_female.setChecked(true);
		}*/
		
	}
}
