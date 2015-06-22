package com.bll;

import java.util.ArrayList;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.database.MRDataBase;
import com.model.TestModel;

public class MRTestBLL {
	public static void addTestModel(TestModel model,Context context)
	{
		String sql="insert or replace into test (type,status,wendu,shidu,shuifen,ziwaixian,time,weatherpm,weatherziwaixian,weatherwendu,weathercity) values (?,?,?,?,?,?,?,?,?,?,?)";
		SQLiteDatabase database= MRDataBase.getDataBase(context);
		database.execSQL(sql, new Object[]{model.type,model.status,model.wenDu,model.shiDu,model.shuiFen,model.ziWaiXian,model.time,model.weatherpm,model.weatherziwaixian,model.weatherwendu,model.weathercity});
		
	}
	public static ArrayList<TestModel> getTestList(int type,Context context)
	{
		ArrayList<TestModel> list=new ArrayList<TestModel>();
		String sql="select * from test where type=? order by time desc";
		SQLiteDatabase database= MRDataBase.getDataBase(context);
		Cursor c=database.rawQuery(sql, new String[]{String.valueOf(type)});
		while(c.moveToNext())
		{
			TestModel model=new TestModel();
			model.id=c.getInt(c.getColumnIndex("_id"));
			model.type=c.getInt(c.getColumnIndex("type"));
			model.wenDu=c.getShort(c.getColumnIndex("wendu"));
			model.shiDu=c.getShort(c.getColumnIndex("shidu"));
			model.shuiFen=c.getShort(c.getColumnIndex("shuifen"));
			model.ziWaiXian=c.getShort(c.getColumnIndex("ziwaixian"));
			model.time=c.getLong(c.getColumnIndex("time"));
			model.status=c.getInt(c.getColumnIndex("status"));
			model.weatherpm=c.getInt(c.getColumnIndex("weatherpm"));
			model.weatherziwaixian=c.getString(c.getColumnIndex("weatherziwaixian"));
			model.weatherwendu=c.getString(c.getColumnIndex("weatherwendu"));
			model.weathercity=c.getString(c.getColumnIndex("weathercity"));
			list.add(model);
		}
		return list;
	}
	public static void deleteTestModel(int id,Context context)
	{
		String sql="delete from test where _id=?";
		SQLiteDatabase database= MRDataBase.getDataBase(context);
		database.execSQL(sql, new Object[]{id});
	}
}
