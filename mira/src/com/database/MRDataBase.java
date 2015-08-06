package com.database;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

public class MRDataBase {
	static SQLiteDatabase database;

	private static final String TAG = "MRDataBase";

	private final Context mCtx;

	private DatabaseHelper mDbHelper;

	private SQLiteDatabase mDb;

	private static final String DATABASE_NAME = "mira";

	// 城市
	private static final String DATABASE_TABLE_CITY = "t_city";
	// 检测结果
	public static final String DATABASE_TABLE_TEST_RESULT = "t_test_result";

	private static final int DATABASE_VERSION = 3;
	
	private final int BUFFER_SIZE = 400000;

	private static final String PACKAGE_NAME = "com.mira";
	public static final String DB_PATH = "/data"
			+ Environment.getDataDirectory().getAbsolutePath() + "/"
			+ PACKAGE_NAME; // 存放路径

	// 创建检测结果的数据库表
	private static String CREATE_TABLE_TEST_RESULT = "create table if not exists "
			+ DATABASE_TABLE_TEST_RESULT
			+ "("
			+ "_id integer primary key not null,"
			+ "type integer ,"
			+ "status integer,"
			+ "shuifen integer ,"
			+ "shidu integer,"
			+ "wendu integer,"
			+ "ziwaixian integer,"
			+ "time long,"
			+ "weatherpm int,"
			+ "weatherziwaixian text,"
			+ "weatherwendu text," + "weathercity text" + ")";
	
	// 创建检测结果的数据库表
	private static String CREATE_TABLE_CITY = "create table if not exists "
			+ DATABASE_TABLE_CITY
			+ "("
			+ "RecNo integer primary key not null,"
			+ "AllNameSort text,"
			+ "CityName text ,"
			+ "NameSort text"
			+  ")";

	public MRDataBase(Context ctx) {
		this.mCtx = ctx;
	}

	/**
	 * 打开数据库
	 * 
	 * @return
	 * @throws SQLException
	 */
	public MRDataBase open() throws SQLException {
		Log.d(TAG, "open()");
		mDbHelper = new DatabaseHelper(mCtx);
		mDb = mDbHelper.getWritableDatabase();
		return this;
	}

	/**
	 * 关闭数据库
	 */
	public void close() {
		Log.d(TAG, "close()");
		mDbHelper.close();
	}

	/**
	 * 获取数据库连接
	 * @return
	 */
	public SQLiteDatabase getmDb() {
		return mDb;
	}


	private static class DatabaseHelper extends SQLiteOpenHelper {
		DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
		}
		// 创建
		@Override
		public void onCreate(SQLiteDatabase db) {
			// 创建表
			db.execSQL(CREATE_TABLE_TEST_RESULT);
			db.execSQL(CREATE_TABLE_CITY);
			for(String sql : InitSQL.sqlList){
				db.execSQL(sql);
			}
		}
		// 升级
		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_CITY);
			db.execSQL("DROP TABLE IF EXISTS " + DATABASE_TABLE_TEST_RESULT);
			onCreate(db);
		}
	}
}
