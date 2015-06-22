package com.mira;

import com.common.MRCommon;
import com.common.MRDownload;

import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.TextView;

public class MRWebViewActivity extends Activity {
	WebView webView;
	String url;
	View leftView;
	TextView textView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mr_activity_webview);
		url=getIntent().getStringExtra("url");
		webView=(WebView)findViewById(R.id.webView);
		textView=(TextView)findViewById(R.id.textView1);
		webView.getSettings().setAllowFileAccess(true);// 设置允许访问文件数据
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setSupportZoom(true); 
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setDomStorageEnabled(true);
		webView.setWebChromeClient(new WebChromeClient(){
			@Override
			public void onReceivedTitle(WebView view, String title) {
				if(title!=null)
				{
					textView.setText(title);
				}
				super.onReceivedTitle(view, title);
			}
		});
		webView.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if(url.contains("jump://"))
				{
					url=url.replace("jump://", "http://");
					url=url.replace("jump://", "http://");
					if(url.contains(".taobao.com/"))
					{
						url=url.replace("http://", "taobao://");
						if(MRCommon.isAvilible(MRWebViewActivity.this, "com.taobao.taobao"))
						{
							Intent intent= new Intent(); 
                            intent.setAction(Intent.ACTION_VIEW);
//                            intent.addCategory(Intent.CATEGORY_DEFAULT);// 
//                            intent.addCategory(Intent.CATEGORY_BROWSABLE);
                            Uri content_url = Uri.parse(url);  
                            intent.setData(content_url);  
                            startActivity(intent);
						}
					}
					else
					{
						Intent intent=new Intent(MRWebViewActivity.this, MRWebViewActivity.class).putExtra("url", url);
						MRWebViewActivity.this.startActivity(intent);
					}
				}
				else if(url.contains("download://"))
				{
					url=url.replace("download://", "http://");
					MRDownload.downloadApk(url, MRWebViewActivity.this);
				}
				else
				{
					view.loadUrl(url);
				}
				return true;
			}
		});
		webView.loadUrl(url);
		leftView=findViewById(R.id.leftbtn);
		leftView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				MRWebViewActivity.this.finish();
				
			}
		});
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mrweb_view, menu);
		return true;
	}

}
