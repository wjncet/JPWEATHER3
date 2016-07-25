package app.oukanan.gtune.jpweather3.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by 王佳楠 on 2016/07/24.
 */
public class JPWeatherOpenHelper extends SQLiteOpenHelper {


    public static final String CREATE_MAIN = "create table MAIN ("
            + "id integer primary key autoincrement, "
            + "main_name text, "
            + "main_code text)";

    public static final String CREATE_SUB1 = "create table SUB1 ("
            + "id integer primary key autoincrement, "
            + "sub1_name text, "
            + "sub1_code text, "
            + "main_code text)";

    public static final String CREATE_SUB2 = "create table SUB2 ("
            + "id integer primary key autoincrement, "
            + "sub2_name text, "
            + "sub2_code text, "
            + "sub1_code text)";

    public static final String CREATE_CLOTHES = "create table CLOTHES ("
            + "id integer primary key autoincrement, "
            + "min integer, "
            + "max integer, "
            + "clothes text)";

    public JPWeatherOpenHelper(Context context, String name, CursorFactory factory,
                                 int version) {
        super(context, name, factory, version);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MAIN);
        db.execSQL(CREATE_SUB1);
        db.execSQL(CREATE_SUB2);
        db.execSQL(CREATE_CLOTHES);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}
