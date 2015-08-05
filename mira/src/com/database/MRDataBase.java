package com.database;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Environment;
import android.util.Log;

import com.mira.R;

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
	private static final String DATABASE_TABLE_TEST_RESULT = "t_test_result";

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
		String dbFile = DB_PATH + "/" + DATABASE_NAME;
		File file = new File(dbFile);
		if (!file.exists()) {
			//打开raw中得数据库文件，获得stream流
			InputStream stream = mCtx.getResources().openRawResource(R.raw.china_city);
			try {

				// 将获取到的stream 流写入道data中
				FileOutputStream outputStream = new FileOutputStream(dbFile);
				byte[] buffer = new byte[BUFFER_SIZE];
				int count = 0;
				while ((count = stream.read(buffer)) > 0) {
					outputStream.write(buffer, 0, count);
				}
				outputStream.close();
				stream.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
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
