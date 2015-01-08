package com.geo.dgpsi.geocampus;

import android.content.ContentValues;
import android.content.Context;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
/**
 * Created by Giraldillo on 05/01/2015.
 */
public class DbManager {

    public static final String TABLE_NAME = "gplocales";

    public static final String CN_ID = "_id";
    public static final String CN_LON = "longitud";
    public static final String CN_LAT = "latitud";
    public static final String CN_TAG = "etiqueta";
    public static final String CN_COM = "comentario";


    public static final String CREATE_TABLE = "create table "+ TABLE_NAME +" ("
            + CN_ID +" integer primary key autoincrement,"
            + CN_LON +" real not null,"
            + CN_LAT +" real not null,"
            + CN_TAG +" text not null,"
            + CN_COM +" text not null);";

    public static final String ROW_NUMBER = "select count(*)";

    private SQLiteDatabase db;
    private DbHelper helper;
    public DbManager(Context context) {
        helper = new DbHelper(context);
        db = helper.getWritableDatabase();
    }

    public void insertar(float longitud, float latitud, String etiqueta, String comentario){
        ContentValues valores = new ContentValues();
        valores.put(CN_LON,longitud);
        valores.put(CN_LAT,latitud);
        valores.put(CN_TAG,etiqueta);
        valores.put(CN_COM,comentario);

        db.insert(TABLE_NAME,null,valores);
    }

    public long getSize(){
        return DatabaseUtils.queryNumEntries(db,TABLE_NAME);
    }
}
