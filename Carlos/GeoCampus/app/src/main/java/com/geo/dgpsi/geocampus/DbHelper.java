package com.geo.dgpsi.geocampus;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Giraldillo on 05/01/2015.
 */
public class DbHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "locales.sqlite";
    private static final int DB_VERSION = 1 ;

    public DbHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
