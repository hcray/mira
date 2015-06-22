package com.common;

import java.io.IOException;
import java.security.interfaces.RSAKey;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Looper;

public class MRCommon {
	static boolean isUpdate;
   public static String bytesToHexString(byte[] src){
       StringBuilder stringBuilder = new StringBuilder("");
       if (src == null || src.length <= 0) {
           return null;
       }
       for (int i = 0; i < src.length; i++) {
           int v = src[i] & 0xFF;
           String hv = Integer.toHexString(v);
           if (hv.length() < 2) {
               stringBuilder.append(0);
           }
           stringBuilder.append(hv);
       }
       return stringBuilder.toString();
   }
   /**
    * Convert hex string to byte[]
    * @param hexString the hex string
    * @return byte[]
    */
   public static byte[] hexStringToBytes(String hexString) {
       if (hexString == null || hexString.equals("")) {
           return null;
       }
       hexString = hexString.toUpperCase();
       int length = hexString.length() / 2;
       char[] hexChars = hexString.toCharArray();
       byte[] d = new byte[length];
       for (int i = 0; i < length; i++) {
           int pos = i * 2;
           d[i] = (byte) (charToByte(hexChars[pos]) << 4 | charToByte(hexChars[pos + 1]));
       }
       return d;
   }
   /**
    * Convert char to byte
    * @param c char
    * @return byte
    */
    private static byte charToByte(char c) {
       return (byte) "0123456789ABCDEF".indexOf(c);
   }
    public static String getHttpData(String url) throws ClientProtocolException, IOException
    {
    	String result="";
		String serverURL = url;
		HttpGet httpRequest = new HttpGet(serverURL);// 建立http get联机
		HttpResponse httpResponse = new DefaultHttpClient()
				.execute(httpRequest);// 发出http请求
		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			result = EntityUtils.toString(httpResponse.getEntity());// 获取相应的字符串
		}
		return result;
    }
    public static void update(final Context context)
    {
    	if(isUpdate)
    	{
    		return;
    	}
    	isUpdate=true;
    	new Thread(new Runnable() {
    		Context c=context;
			@Override
			public void run() {
				try
				{
					String data=MRCommon.getHttpData("http://apinet.miramask.com/APP/VersionAndroid");
					String version=data.split("@")[0];
					final String url=data.split("@")[1];
					if(!version.equals("1"))
					{
						new Handler(Looper.getMainLooper()).post(new Runnable() {
							
							@Override
							public void run() {
								Builder b=new Builder(c);
								b.setTitle("版本更新");
								b.setMessage("发现新版本，是否更新?");
								b.setPositiveButton("更新", new OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										MRDownload.downloadApk(url, c);
									}
								});
								b.setNegativeButton("取消", new OnClickListener() {
									
									@Override
									public void onClick(DialogInterface dialog, int which) {
										// TODO Auto-generated method stub
										isUpdate=false;
									}
								});
								b.create().show();
								
							}
						});
						
					}
				}
				catch(Exception e)
				{
					isUpdate=false;
				}
				
				
				
			}
		}).start();
    }
    public static boolean isAvilible(Context context, String packageName)
    {           
    	final PackageManager packageManager = context.getPackageManager();//获取packagemanager           
    	List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);//获取所有已安装程序的包信息           
    	List<String> pName = new ArrayList<String>();//用于存储所有已安装程序的包名          //从pinfo中将包名字逐一取出，压入pName list中               
    	if(pinfo != null)
    	{               
    		for(int i = 0; i < pinfo.size(); i++)
    		{                   
    			String pn = pinfo.get(i).packageName;
    			pName.add(pn);
    		}
    	}           
    	return pName.contains(packageName);//判断pName中是否有目标程序的包名，有TRUE，没有FALSE     } 
    }
}
