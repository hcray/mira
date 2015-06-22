package com.model;

import java.util.ArrayList;

public class MRWeatherModel {
	public class MRWeatherResultModel
	{
		public class MRWeatherResultIndex
		{
			public String title;
			public String zs;
			public String tipt;
			public String des;
		}
		public class MRWeatherResultData
		{
			public String temperature;
		}
		public int pm25;
		public String currentCity;
		public MRWeatherResultIndex[] index;
		public MRWeatherResultData[] weather_data;
	}
	public MRWeatherResultModel[] results;
}
