package com.common;

import java.io.IOException;

import org.apache.http.client.ClientProtocolException;

import com.baidu.location.BDLocation;
import com.baidu.location.BDLocationListener;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.location.LocationClientOption.LocationMode;
import com.google.gson.Gson;
import com.model.MRWeatherModel;
import com.model.MRWeatherModel.MRWeatherResultModel;
import com.model.MRWeatherModel.MRWeatherResultModel.MRWeatherResultData;
import com.model.MRWeatherModel.MRWeatherResultModel.MRWeatherResultIndex;
import com.model.TestModel;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Handler;
class MyLocationListener implements BDLocationListener 
{
	Context context;
	public MyLocationListener(Context context)
	{
		this.context=context;
	}
	@Override
	public void onReceiveLocation(BDLocation location) {
		if(!BaiduLocation.isAuto)
		{
			if(BaiduLocation.mLocationClient!=null)
			{
				if(BaiduLocation.mLocationClient.isStarted())
				{
					BaiduLocation.mLocationClient.stop();
				}
			}
		}
		if(location==null || (location.getLocType()!=61 && location.getLocType()!=65 && location.getLocType()!=161))
		{
			if(BaiduLocation.handler!=null)
			{
				BaiduLocation.handler.sendObject(BaiduLocation.getBufLocation(context));
				if(!BaiduLocation.isAuto)
				{
					BaiduLocation.handler=null;
				}
			}
		}
		else
		{
			BaiDuLocationModel model=new BaiDuLocationModel();
			if(location.getCity()==null)
			{
				model.City="";
			}
			else
			{
				model.City=location.getCity().replace("省", "").replace("市", "").replace("特别行政区", "");
			}
			if(location.getProvince()==null)
			{
				model.Province="";
			}
			else
			{
				model.Province=location.getProvince().replace("省", "").replace("市", "").replace("特别行政区", "");
			}
			model.Lat=location.getLatitude();
			model.Lng=location.getLongitude();
			if(location.getAddrStr()==null)
			{
				model.Address="";
			}
			else
			{
				model.Address=location.getAddrStr();
			}
			model.Radius=location.getRadius();
			model.Direction=location.getDirection();
			if(model.Province.equals(model.City))
			{
				if(location.getDistrict()==null)
				{
					model.City="";
				}
				else
				{
					model.City="";
				}
			}
			BaiduLocation.setBufLocation(context, model);
			if(BaiduLocation.handler!=null)
			{
				BaiduLocation.handler.sendObject(model);
				if(!BaiduLocation.isAuto)
				{
					BaiduLocation.handler=null;
				}
			}
		}
		
	}
	
}
public class BaiduLocation {
	static BaiDuLocationModel bufLocationModel;
	static LocationClient mLocationClient;
	static HandlerEvent<BaiDuLocationModel> handler;
	static boolean isAuto;
	static TestModel weatherModel;
	static boolean isWeather;
	static LocationClient createBaiDuLocation(Context context)
	{
		LocationClient locationClient=new LocationClient(context);
		locationClient.registerLocationListener(new MyLocationListener(context));
		LocationClientOption option = new LocationClientOption();  
		option.setLocationMode(LocationMode.Hight_Accuracy);//设置定位模式  
		option.setCoorType("bd09ll");//返回的定位结果是百度经纬度,默认值gcj02  
		option.setScanSpan(1100);//设置发起定位请求的间隔时间为5000ms
		option.setIsNeedAddress(true);//返回的定位结果包含地址信息  
		option.setNeedDeviceDirect(true);//返回的定位结果包含手机机头的方向  
		option.setProdName("com.android.yys");
		locationClient.setLocOption(option);
		return locationClient;
	}
	public static void getLocation(Context context,HandlerEvent<BaiDuLocationModel> handler)
	{
		getLocation(context,handler,false);
	}
	public static void getLocation(Context context,HandlerEvent<BaiDuLocationModel> handler,boolean isAuto)
	{
		BaiduLocation.isAuto=isAuto;
		BaiduLocation.handler=handler;
		if(mLocationClient==null)
		{
			mLocationClient=createBaiDuLocation(context);
		}
		if(!mLocationClient.isStarted())
		{
			mLocationClient.start();
		}
		mLocationClient.requestLocation();
	}
	public static void stopLocation()
	{
		if(BaiduLocation.mLocationClient!=null)
		{
			if(BaiduLocation.mLocationClient.isStarted())
			{
				BaiduLocation.mLocationClient.stop();
			}
		}
		BaiduLocation.handler=null;
	}
	public static BaiDuLocationModel getBufLocation(Context context)
	{
		try
		{
			if(bufLocationModel!=null)
			{
				return bufLocationModel;
			}
			BaiDuLocationModel model=new BaiDuLocationModel();
			SharedPreferences sp=context.getSharedPreferences("Location", Context.MODE_PRIVATE);
			model.City=sp.getString("City", "");
			model.Province=sp.getString("Province", "");
			model.Lat=Double.valueOf(sp.getString("Lat", "0"));
			model.Lng=Double.valueOf(sp.getString("Lng", "0"));
			model.Address=sp.getString("Address", "");
			return model;
		}
		catch(Exception e)
		{
			return null;
		}
	}
	public static void setBufLocation(Context context,BaiDuLocationModel model)
	{
		try
		{
			Editor ed=context.getSharedPreferences("Location", Context.MODE_PRIVATE).edit();
			ed.putString("City", model.City);
			ed.putString("Province", model.Province);
			ed.putString("Lat", model.Lat+"");
			ed.putString("Lng", model.Lng+"");
			ed.putString("Address", model.Address);
			ed.commit();
			bufLocationModel=model;
		}
		catch(Exception e)
		{
			
		}
	}
	public static TestModel getWeather(final Context context)
	{
		if(!isWeather)
		{
			isWeather=true;
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					BaiDuLocationModel model=BaiduLocation.getBufLocation(context);
					BaiduLocation.getLocation(context,new HandlerEvent<BaiDuLocationModel>(){
						@Override
						public void handleMessage(BaiDuLocationModel result) {
							
							
							super.handleMessage(result);
						}
					});
					if(model==null)
					{
						isWeather=false;
						return;
					}
					try {
						String text=MRCommon.getHttpData("http://api.map.baidu.com/telematics/v3/weather?location="+model.Lng+","+model.Lat+"&output=json&;ak=C849c6992c5232a86e0f6e4426e7ce29&coord_type=wgs84&qq-pf-to=pcqq.discussion");
						Gson gson=new Gson();
						MRWeatherModel weatherModel=gson.fromJson(text, MRWeatherModel.class);
						if(weatherModel!=null && weatherModel.results!=null)
						{
							MRWeatherResultModel result=weatherModel.results[0];
							BaiduLocation.weatherModel=new TestModel();
							BaiduLocation.weatherModel.weatherpm=result.pm25;
							BaiduLocation.weatherModel.weathercity=result.currentCity;
							for (MRWeatherResultIndex i : result.index) {
								if(i.title.equals("紫外线强度"))
								{
									BaiduLocation.weatherModel.weatherziwaixian=i.zs;
								}
							}
							MRWeatherResultData data=result.weather_data[0];
							BaiduLocation.weatherModel.weatherwendu=data.temperature;
						}
					} catch (ClientProtocolException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					isWeather=false;
					
				}
			}).start();
		}
		return weatherModel;
	}
}
