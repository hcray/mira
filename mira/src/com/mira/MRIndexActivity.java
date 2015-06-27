package com.mira;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.common.MRCommon;

public class MRIndexActivity extends Activity {
	WebView webView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mr_activity_index);
		
		/**
		webView=(WebView)findViewById(R.id.webView);
		
		webView.getSettings().setAllowFileAccess(true);// 设置允许访问文件数据
		webView.getSettings().setJavaScriptEnabled(true);
		webView.getSettings().setBuiltInZoomControls(true);
		webView.getSettings().setSupportZoom(true); 
		webView.getSettings().setLoadWithOverviewMode(true);
		webView.getSettings().setDomStorageEnabled(true);
		webView.setWebChromeClient(new WebChromeClient(){
			
		});
		webView.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				if(url.contains("jump://"))
				{
					url=url.replace("jump://", "http://");
					if(url.contains(".taobao.com/"))
					{
						url=url.replace("http://", "taobao://");
						if(MRCommon.isAvilible(MRIndexActivity.this, "com.taobao.taobao"))
						{
							Intent intent= new Intent(); 
                            intent.setAction(Intent.ACTION_VIEW);
//                            intent.addCategory(Intent.CATEGORY_DEFAULT);// 
//                            intent.addCategory(Intent.CATEGORY_BROWSABLE);
                            Uri content_url = Uri.parse(url);  
                            intent.setData(content_url);  
                            startActivity(intent);
						}
						else
						{
							Intent intent=new Intent(MRIndexActivity.this, MRWebViewActivity.class).putExtra("url", url);
							MRIndexActivity.this.startActivity(intent);
						}
					}
					else
					{
						Intent intent=new Intent(MRIndexActivity.this, MRWebViewActivity.class).putExtra("url", url);
						MRIndexActivity.this.startActivity(intent);
					}
				}
				else
				{
					view.loadUrl(url);
				}
				return true;
			}
		});
		webView.loadUrl("http://apinet.miramask.com/app");
		*/
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.mrindex, menu);
		return true;
	}

}
