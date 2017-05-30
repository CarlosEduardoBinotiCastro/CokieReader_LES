package com.example.usuario.cookiereader.DAO;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;

import com.example.usuario.cookiereader.domain.UF;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usuario on 25/05/2017.
 */

public class UfDAO {

    private SQLiteDatabase conn;

    public UfDAO(SQLiteDatabase conn){
        this.conn = conn;
    }


    private ContentValues preencheContentValues(UF uf)
    {
        ContentValues values = new ContentValues();

        values.put("siglaUf", uf.getSigla());
        values.put("nome", uf.getNome());


        return values;

    }

    public void excluir(String id)
    {
        conn.delete("Uf", " siglaUf = ? ", new String[]{ String.valueOf( id ) });
    }

    public void alterar(UF uf)
    {
        ContentValues values = preencheContentValues(uf);
        conn.update("Uf", values, " siglaUf = ? ", new String[]{ String.valueOf( uf.getSigla()) } );

    }

    public void inserir(UF uf)
    {
        ContentValues values = preencheContentValues(uf);
        conn.insert("Uf", null, values);
    }

    public UF buscar(String uf){
        UF uf2 = new UF();

        Cursor cursor = conn.rawQuery("select * from Uf where siglaUf = '"+uf+"'", null);
        if(cursor.moveToFirst()) {
            uf2.setSigla(cursor.getString(cursor.getColumnIndex("siglaUf")));
            uf2.setNome(cursor.getString(cursor.getColumnIndex("nome")));
        }
        return  uf2;
    }

    public List<UF> BuscarTodos(){
        try{
            return toList(conn.rawQuery("select * from Uf", null), conn);

        }catch (Exception ex){
            conn.close();
            return null;
        }

    }

    public List<UF> toList(Cursor cursor, SQLiteDatabase db){
        ArrayList<UF> ufs = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                UF uf = new UF();
                uf.setSigla(cursor.getString(cursor.getColumnIndex("siglaUf")));
                uf.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                ufs.add(uf);
            }while (cursor.moveToNext());
        }
        return ufs;
    }
}
