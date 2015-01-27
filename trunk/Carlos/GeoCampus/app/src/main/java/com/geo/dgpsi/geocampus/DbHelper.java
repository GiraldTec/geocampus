package com.geo.dgpsi.geocampus;

import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.geo.dgpsi.geocampus.DbManager.CREATE_TABLE_PROPIO;
import static com.geo.dgpsi.geocampus.DbManager.CREATE_TABLE_GLOBAL;

/**
 * Created by Giraldillo on 05/01/2015.
 */
public class DbHelper extends SQLiteOpenHelper{

    private static final String DB_NAME = "geoCampusDB.sqlite";
    private static final int DB_VERSION = 1 ;

    public DbHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_PROPIO);
        db.execSQL(CREATE_TABLE_GLOBAL);
        //System.out.println("tamaniotamanio"+DatabaseUtils.queryNumEntries(db, DbManager.TABLE_NAME));
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
