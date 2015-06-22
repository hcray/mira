package com.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


public class MRDataBase {
	static SQLiteDatabase database;
	public static void Init(Context context)
	{
		if(database==null)
		{
			database=context.openOrCreateDatabase("Test", Context.MODE_PRIVATE, null);
			CreateTable();
		}
	}
	public static SQLiteDatabase getDataBase(Context context)
	{
		Init(context);
		return database;
	}
	public static void CreateTable()
	{
		String CREATE_TABLE_TEST = "create table if not exists test("
				+ "_id integer primary key not null," + "type integer ,"
				+ "status integer," + "shuifen integer ,"
				+ "shidu integer," + "wendu integer," + "ziwaixian integer,"
				+ "time long,weatherpm int,weatherziwaixian text,weatherwendu text,weathercity text"
				+ ")";
		database.execSQL(CREATE_TABLE_TEST);
	}
}
