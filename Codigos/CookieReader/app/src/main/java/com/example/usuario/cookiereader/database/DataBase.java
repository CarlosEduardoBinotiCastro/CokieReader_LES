package com.example.usuario.cookiereader.database;

/**
 * Created by Paulo on 28/03/2015.
 */

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.*;


public class DataBase extends SQLiteOpenHelper{

    private static final int VERSION = 2;
    private static final String NAME = "cookiereader";

    public DataBase(Context context)
    {
        super(context, NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL( ScriptSQL.getBiscoito());
        db.execSQL( ScriptSQL.getBiscoitoNutriente() );
        db.execSQL( ScriptSQL.getCidade() );
        db.execSQL( ScriptSQL.getDcnt() );
        db.execSQL( ScriptSQL.getDCNTpeso() );
        db.execSQL( ScriptSQL.getUsuario() );
        db.execSQL( ScriptSQL.getUf() );
        db.execSQL( ScriptSQL.getEmpresa() );
        db.execSQL( ScriptSQL.getScanBiscoito() );
        db.execSQL( ScriptSQL.getNutriente() );



        /*String sql = ("CREATE TABLE IF NOT EXISTS Jogadores " +
                "( _id INTEGER PRIMARY KEY AUTOINCREMENT, "+
                "nome TEXT, "+
                "gols INTEGER, "+
                "clube TEXT);");

        db.execSQL(sql);*/


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
