package app.oukanan.gtune.jpweather3.util;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

import app.oukanan.gtune.jpweather3.model.Main;
import app.oukanan.gtune.jpweather3.model.Sub1;
import app.oukanan.gtune.jpweather3.model.Sub2;

/**
 * Created by 王佳楠 on 2016/07/25.
 */
public class JPWeatherDB {

    /**
     * 数据库名
     */
    public static final String DB_NAME = "jp_weather";

    /**
     * 数据库版本
     */
    public static final int VERSION = 1;

    private static JPWeatherDB jpweatherDB;

    private SQLiteDatabase db;

    /**
     * 将构造方法私有化
     */
    private JPWeatherDB(Context context) {
        JPWeatherOpenHelper dbHelper = new JPWeatherOpenHelper(context,
                DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();
    }

    /**
     * 获取CoolWeatherDB的实例。
     */
    public synchronized static JPWeatherDB getInstance(Context context) {
        if (jpweatherDB == null) {
            jpweatherDB = new JPWeatherDB(context);
        }
        return jpweatherDB;
    }


    public List<Main> loadMain() {
        List<Main> list = new ArrayList<Main>();
        Cursor cursor = db
                .query("Main", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Main main = new Main();
                main.setId(cursor.getInt(cursor.getColumnIndex("id")));
                main.setMain_name(cursor.getString(cursor
                        .getColumnIndex("main_name")));
                main.setMain_code(cursor.getString(cursor
                        .getColumnIndex("main_code")));
                list.add(main);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public List<Sub1> loadSub1(String maincode) {
        List<Sub1> list = new ArrayList<Sub1>();
        Cursor cursor = db.query("Sub1", null, "main_code = ?",
                new String[]{String.valueOf(maincode)}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Sub1 sub1 = new Sub1();
                sub1.setId(cursor.getInt(cursor.getColumnIndex("id")));
                sub1.setSub1_name(cursor.getString(cursor
                        .getColumnIndex("sub1_name")));
                sub1.setSub1_code(cursor.getString(cursor
                        .getColumnIndex("sub1_code")));
                sub1.setMain_code(maincode);
                list.add(sub1);
            } while (cursor.moveToNext());
        }
        return list;
    }

    public List<Sub2> loadSub2(String sub1code) {
        List<Sub2> list = new ArrayList<Sub2>();
        Cursor cursor = db.query("Sub2", null, "sub1_code = ?",
                new String[]{String.valueOf(sub1code)}, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                Sub2 sub2 = new Sub2();
                sub2.setId(cursor.getInt(cursor.getColumnIndex("id")));
                sub2.setSub2_name(cursor.getString(cursor
                        .getColumnIndex("sub2_name")));
                sub2.setSub2_code(cursor.getString(cursor
                        .getColumnIndex("sub2_code")));
                sub2.setSub1_code(sub1code);
                list.add(sub2);
            } while (cursor.moveToNext());
        }
        return list;
    }

    /**
     * 将County实例存储到数据库。
     */
    public void saveMain(Main main) {
        if (main != null) {
            ContentValues values = new ContentValues();
            values.put("main_name", main.getMain_name());
            values.put("main_code", main.getMain_code());
            db.insert("MAIN", null, values);
        }
    }

    /**
     * 将County实例存储到数据库。
     */
    public void saveSub1(Sub1 sub1) {
        if (sub1 != null) {
            ContentValues values = new ContentValues();
            values.put("sub1_name", sub1.getSub1_name());
            values.put("sub1_code", sub1.getSub1_code());
            values.put("main_code", sub1.getMain_code());
            db.insert("SUB1", null, values);
        }
    }

    /**
     * 将County实例存储到数据库。
     */
    public void saveSub2(Sub2 sub2) {
        if (sub2 != null) {
            ContentValues values = new ContentValues();
            values.put("sub2_name", sub2.getSub2_name());
            values.put("sub2_code", sub2.getSub2_code());
            values.put("sub1_code", sub2.getSub1_code());
            db.insert("SUB2", null, values);
        }
    }
}
