package com.bll;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.database.MRDataBase;
import com.model.CityModel;
import com.model.TestModel;

public class MRTestBLL {
	
	/**
	 * 保存测试结果
	 * @param model
	 * @param context
	 */
	public static void addTestModel(TestModel model, Context context) {
		String sql = "insert or replace into " + MRDataBase.DATABASE_TABLE_TEST_RESULT + " (type,status,wendu,shidu,shuifen,score,ziwaixian,time,weatherpm,weatherziwaixian,weatherwendu,weathercity) values (?,?,?,?,?,?,?,?,?,?,?,?)";
		//SQLiteDatabase database = MRDataBase.getDataBase(context);
		MRDataBase dbAdapter = new MRDataBase(context);
		dbAdapter.open();
		SQLiteDatabase database = dbAdapter.getmDb();
		
		database.execSQL(sql, new Object[] {
				model.type, model.status,model.wenDu,
				model.shiDu, model.shuiFen,model.score,
				model.ziWaiXian,model.time, model.weatherpm,
				model.weatherziwaixian,model.weatherwendu, model.weathercity });
		dbAdapter.close();

	}

	/**
	 * 查询保存的数据
	 * @param type
	 * @param context
	 * @return
	 */
	public static ArrayList<TestModel> getTestList(int type, Context context) {
		ArrayList<TestModel> list = new ArrayList<TestModel>();
		String sql = "select * from " + MRDataBase.DATABASE_TABLE_TEST_RESULT + " where type=? order by time desc";
//		SQLiteDatabase database = MRDataBase.getDataBase(context);
		MRDataBase dbAdapter = new MRDataBase(context);
		dbAdapter.open();
		SQLiteDatabase database = dbAdapter.getmDb();
		Cursor c = database
				.rawQuery(sql, new String[] { String.valueOf(type) });
		while (c.moveToNext()) {
			TestModel model = new TestModel();
			model.id = c.getInt(c.getColumnIndex("_id"));
			model.type = c.getInt(c.getColumnIndex("type"));
			model.wenDu = c.getShort(c.getColumnIndex("wendu"));
			model.shiDu = c.getShort(c.getColumnIndex("shidu"));
			model.shuiFen = c.getShort(c.getColumnIndex("shuifen"));
			model.score = c.getShort(c.getColumnIndex("score"));
			model.ziWaiXian = c.getShort(c.getColumnIndex("ziwaixian"));
			model.time = c.getLong(c.getColumnIndex("time"));
			model.status = c.getInt(c.getColumnIndex("status"));
			model.weatherpm = c.getInt(c.getColumnIndex("weatherpm"));
			model.weatherziwaixian = c.getString(c.getColumnIndex("weatherziwaixian"));
			model.weatherwendu = c.getString(c.getColumnIndex("weatherwendu"));
			model.weathercity = c.getString(c.getColumnIndex("weathercity"));
			list.add(model);
		}
		dbAdapter.close();
		return list;
	}
	
	/**
	 * 查询区间最大的数据
	 * @param type
	 * @param context
	 * @return
	 */
	public static short getTestMax(int type,long startTime, long endTime, Context context) {
		String sql = "select max(shuifen) as shuifen  from " + MRDataBase.DATABASE_TABLE_TEST_RESULT + " where type = ? and time >= ? and time <= ?";
		short ret = 0;
		MRDataBase dbAdapter = new MRDataBase(context);
		dbAdapter.open();
		SQLiteDatabase database = dbAdapter.getmDb();
		Cursor c = database.rawQuery(sql, new String[] { String.valueOf(type) , String.valueOf(startTime), String.valueOf(endTime)});
		while (c.moveToNext()) {
			ret = c.getShort(c.getColumnIndex("shuifen"));
		}
		dbAdapter.close();
		return ret;
	}
	
	/**
	 * 查询区间的平均数据
	 * @param type
	 * @param context
	 * @return
	 */
	public static int getTestAverage(int type, long startTime, long endTime, Context context) {
		String sql = "select avg(shuifen) as shuifen  from " + MRDataBase.DATABASE_TABLE_TEST_RESULT + " where type = ? and time >= ? and time <= ?";
		int ret = 0;
		MRDataBase dbAdapter = new MRDataBase(context);
		dbAdapter.open();
		SQLiteDatabase database = dbAdapter.getmDb();
		Cursor c = database.rawQuery(sql, new String[] { String.valueOf(type) , String.valueOf(startTime), String.valueOf(endTime)});
		while (c.moveToNext()) {
			ret = c.getInt(c.getColumnIndex("shuifen"));
		}
		dbAdapter.close();
		return ret;
	}
	
	/**
	 * 查询今天数据
	 * @param type
	 * @param context
	 * @return
	 */
	public static ArrayList<TestModel> getTestList4Today(int type, Context context, long time) {
		ArrayList<TestModel> list = new ArrayList<TestModel>();
		String sql = "select * from " + MRDataBase.DATABASE_TABLE_TEST_RESULT + " where type=? and time > ? order by time desc";
//		SQLiteDatabase database = MRDataBase.getDataBase(context);
		MRDataBase dbAdapter = new MRDataBase(context);
		dbAdapter.open();
		SQLiteDatabase database = dbAdapter.getmDb();
		Cursor c = database.rawQuery(sql, new String[] { String.valueOf(type), String.valueOf(time) });
		while (c.moveToNext()) {
			TestModel model = new TestModel();
			model.id = c.getInt(c.getColumnIndex("_id"));
			model.type = c.getInt(c.getColumnIndex("type"));
			model.wenDu = c.getShort(c.getColumnIndex("wendu"));
			model.shiDu = c.getShort(c.getColumnIndex("shidu"));
			model.shuiFen = c.getShort(c.getColumnIndex("shuifen"));
			model.score = c.getShort(c.getColumnIndex("score"));
			model.ziWaiXian = c.getShort(c.getColumnIndex("ziwaixian"));
			model.time = c.getLong(c.getColumnIndex("time"));
			model.status = c.getInt(c.getColumnIndex("status"));
			model.weatherpm = c.getInt(c.getColumnIndex("weatherpm"));
			model.weatherziwaixian = c.getString(c.getColumnIndex("weatherziwaixian"));
			model.weatherwendu = c.getString(c.getColumnIndex("weatherwendu"));
			model.weathercity = c.getString(c.getColumnIndex("weathercity"));
			list.add(model);
		}
		dbAdapter.close();
		return list;
	}
	
	/**
	 * 查询今天数据
	 * @param type
	 * @param context
	 * @return
	 */
	public static TestModel getTestModel4Today(int type, Context context,  long startTime, long endTime) {
		TestModel model = new TestModel();
		String sql = "select * from " + MRDataBase.DATABASE_TABLE_TEST_RESULT + " where type=? and time > ? and time < ? order by time desc";
//		SQLiteDatabase database = MRDataBase.getDataBase(context);
		MRDataBase dbAdapter = new MRDataBase(context);
		dbAdapter.open();
		SQLiteDatabase database = dbAdapter.getmDb();
		Cursor c = database.rawQuery(sql, new String[] { String.valueOf(type), String.valueOf(startTime), String.valueOf(endTime) });
		if (c.moveToNext()) {
			model.id = c.getInt(c.getColumnIndex("_id"));
			model.type = c.getInt(c.getColumnIndex("type"));
			model.wenDu = c.getShort(c.getColumnIndex("wendu"));
			model.shiDu = c.getShort(c.getColumnIndex("shidu"));
			model.shuiFen = c.getShort(c.getColumnIndex("shuifen"));
			model.score = c.getShort(c.getColumnIndex("score"));
			model.ziWaiXian = c.getShort(c.getColumnIndex("ziwaixian"));
			model.time = c.getLong(c.getColumnIndex("time"));
			model.status = c.getInt(c.getColumnIndex("status"));
			model.weatherpm = c.getInt(c.getColumnIndex("weatherpm"));
			model.weatherziwaixian = c.getString(c.getColumnIndex("weatherziwaixian"));
			model.weatherwendu = c.getString(c.getColumnIndex("weatherwendu"));
			model.weathercity = c.getString(c.getColumnIndex("weathercity"));
			
		}
		dbAdapter.close();
		return model;
	}
	
	/**
	 * 查询指定时间区间的数据
	 * @param type
	 * @param context
	 * @return
	 */
	public static ArrayList<TestModel> getTestListByTime(int type, Context context, long startTime, long endTime) {
		ArrayList<TestModel> list = new ArrayList<TestModel>();
		String sql = "select * from " + MRDataBase.DATABASE_TABLE_TEST_RESULT + " where type=? and time > ? and time < ? order by time desc";
//		SQLiteDatabase database = MRDataBase.getDataBase(context);
		MRDataBase dbAdapter = new MRDataBase(context);
		dbAdapter.open();
		SQLiteDatabase database = dbAdapter.getmDb();
		Cursor c = database.rawQuery(sql, new String[] { String.valueOf(type), String.valueOf(startTime), String.valueOf(endTime) });
		while (c.moveToNext()) {
			TestModel model = new TestModel();
			model.id = c.getInt(c.getColumnIndex("_id"));
			model.type = c.getInt(c.getColumnIndex("type"));
			model.wenDu = c.getShort(c.getColumnIndex("wendu"));
			model.shiDu = c.getShort(c.getColumnIndex("shidu"));
			model.shuiFen = c.getShort(c.getColumnIndex("shuifen"));
			model.score = c.getShort(c.getColumnIndex("score"));
			model.ziWaiXian = c.getShort(c.getColumnIndex("ziwaixian"));
			model.time = c.getLong(c.getColumnIndex("time"));
			model.status = c.getInt(c.getColumnIndex("status"));
			model.weatherpm = c.getInt(c.getColumnIndex("weatherpm"));
			model.weatherziwaixian = c.getString(c.getColumnIndex("weatherziwaixian"));
			model.weatherwendu = c.getString(c.getColumnIndex("weatherwendu"));
			model.weathercity = c.getString(c.getColumnIndex("weathercity"));
			list.add(model);
		}
		dbAdapter.close();
		return list;
	}

	/**
	 * 删除保存的数据
	 * @param id
	 * @param context
	 */
	public static void deleteTestModel(int id, Context context) {
		String sql = "delete from " + MRDataBase.DATABASE_TABLE_TEST_RESULT + " where _id=?";
//		SQLiteDatabase database = MRDataBase.getDataBase(context);
		MRDataBase dbAdapter = new MRDataBase(context);
		dbAdapter.open();
		SQLiteDatabase database = dbAdapter.getmDb();
		database.execSQL(sql, new Object[] { id });
		dbAdapter.close();
	}
	
	
	/**
	 * 模糊查询
	 * @param con
	 * @return
	 */
	public static ArrayList<CityModel> getSelectCityNames(String con, Context context) {
		ArrayList<CityModel> names = new ArrayList<CityModel>();
		MRDataBase dbAdapter = new MRDataBase(context);
		dbAdapter.open();
		SQLiteDatabase database = dbAdapter.getmDb();
		// 判断查询的内容是不是汉字
		Pattern p_str = Pattern.compile("[\\u4e00-\\u9fa5]+");
		Matcher m = p_str.matcher(con);
		String sqlString = null;
		if (m.find() && m.group(0).equals(con)) {
			sqlString = "SELECT * FROM t_city WHERE AllNameSort LIKE " + "\""
					+ con + "%" + "\"" + " ORDER BY CityName";
		} else {
			sqlString = "SELECT * FROM t_city WHERE NameSort LIKE " + "\""
					+ con + "%" + "\"" + " ORDER BY CityName";
		}
		Cursor cursor = database.rawQuery(sqlString, null);
		for (int i = 0; i < cursor.getCount(); i++) {
			cursor.moveToPosition(i);
			CityModel cityModel = new CityModel();
			cityModel.setCityName(cursor.getString(cursor.getColumnIndex("AllNameSort")));
			cityModel.setNameSort(cursor.getString(cursor.getColumnIndex("CityName")));
			names.add(cityModel);
		}
		cursor.close();
		dbAdapter.close();
		return names;
	}

	/**
	 * 从数据库获取城市数据
	 * 
	 * @return
	 */
	public static ArrayList<CityModel> getCityNames(Context context) {
		ArrayList<CityModel> names = new ArrayList<CityModel>();
		MRDataBase dbAdapter = new MRDataBase(context);
		dbAdapter.open();
		SQLiteDatabase database = dbAdapter.getmDb();
		Cursor cursor = database.rawQuery("SELECT * FROM t_city ORDER BY CityName", null);
		for (int i = 0; i < cursor.getCount(); i++) {
			cursor.moveToPosition(i);
			CityModel cityModel = new CityModel();
			cityModel.setCityName(cursor.getString(cursor.getColumnIndex("AllNameSort")));
			cityModel.setNameSort(cursor.getString(cursor.getColumnIndex("CityName")));
			names.add(cityModel);
		}
		cursor.close();
		dbAdapter.close();
		return names;
	}
}
