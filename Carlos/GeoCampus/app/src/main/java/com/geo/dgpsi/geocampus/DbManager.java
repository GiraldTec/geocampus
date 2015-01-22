package com.geo.dgpsi.geocampus;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
/**
 * Created by Giraldillo on 05/01/2015.
 */
public class DbManager {

    public static final String TABLE_NAME_PROPIOS = "gppropios";

    public static final String CN_ID_LOCAL = "_idLocal";
    public static final String CN_ID_GLOBAL = "_idGlobal";
    public static final String CN_LON = "longitud";
    public static final String CN_LAT = "latitud";
    public static final String CN_TAG = "etiqueta";
    public static final String CN_URI = "directorio";
    public static final String CN_COM = "comentario";


    public static final String CREATE_TABLE = "create table "+ TABLE_NAME_PROPIOS +" ("
            + CN_ID_LOCAL +" integer primary key autoincrement,"
            + CN_ID_GLOBAL +" integer not null,"
            + CN_LON +" real not null,"
            + CN_LAT +" real not null,"
            + CN_TAG +" text not null,"
            + CN_URI +" text not null,"
            + CN_COM +" text not null);";

    public static final String DROP_TABLE = "drop table "+TABLE_NAME_PROPIOS+";";

    public static final String ROW_NUMBER = "select count(*)";

    private SQLiteDatabase db;
    private DbHelper helper;
    public DbManager(Context context) {
        helper = new DbHelper(context);
        db = helper.getWritableDatabase();
        System.out.println(db.getPath());
    }

    public void insertarPropios( float longitud, float latitud, String etiqueta, String directorio, String comentario){
        ContentValues valores = new ContentValues();
        valores.put(CN_ID_GLOBAL,0);
        valores.put(CN_LON,longitud);
        valores.put(CN_LAT,latitud);
        valores.put(CN_TAG,etiqueta);
        valores.put(CN_URI,directorio);
        valores.put(CN_COM,comentario);

        db.insert(TABLE_NAME_PROPIOS,null,valores);
    }

    public void drop(){
        db.execSQL(DROP_TABLE);
    }

    public long getSize(){
        return DatabaseUtils.queryNumEntries(db,TABLE_NAME_PROPIOS);
    }

    public Cursor getAllPropios(){
        String[] columnas = new String[] {CN_ID_LOCAL,CN_ID_GLOBAL,CN_LON,CN_LAT,CN_TAG,CN_URI,CN_COM};
        return db.query(TABLE_NAME_PROPIOS,columnas,null,null,null,null,null);
    }

}
