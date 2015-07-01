package com.mira;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MRLoginActivity extends Activity {
	
	private static String TAG = "MRLoginActivity";
	
	private AutoCompleteTextView loginAccount;
	
	private EditText loginPassword;
	
	private Button loginButton;
	
	private TextView forget;
	
	private TextView signIn;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mr_activity_login);
		
		loginAccount = (AutoCompleteTextView) this.findViewById(R.id.login_account);
		
		loginPassword = (EditText) this.findViewById(R.id.login_password);
		
		loginButton = (Button) this.findViewById(R.id.login_activity_bt_login);
		
		forget = (TextView) this.findViewById(R.id.login_activity_tv_forget);
		
		signIn = (TextView) this.findViewById(R.id.login_activity_tv_signIn);
		
		
		
	}
	
	
}
