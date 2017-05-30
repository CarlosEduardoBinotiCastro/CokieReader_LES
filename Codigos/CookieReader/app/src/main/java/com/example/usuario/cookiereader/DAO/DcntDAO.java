package com.example.usuario.cookiereader.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.usuario.cookiereader.domain.dcnt;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usuario on 27/05/2017.
 */

public class DcntDAO {

    private SQLiteDatabase conn;

    public DcntDAO(SQLiteDatabase conn){
        this.conn = conn;
    }


    private ContentValues preencheContentValues(dcnt dcnt)
    {
        ContentValues values = new ContentValues();

        values.put("nome", dcnt.getNome());

        return values;

    }

    public void excluir(int id)
    {
        conn.delete("dcnt", " _cdDcnt = ? ", new String[]{ String.valueOf( id ) });
    }

    public void alterar(dcnt dcnt)
    {
        ContentValues values = preencheContentValues(dcnt);
        conn.update("dcnt", values, " _cdDcnt = ? ", new String[]{ String.valueOf( dcnt.getCdDcnt()) } );

    }

    public void inserir(dcnt dcnt)
    {
        ContentValues values = preencheContentValues(dcnt);
        conn.insert("dcnt", null, values);
    }


    public List<dcnt> BuscarTodos(){
        try{
            return toList(conn.rawQuery("select * from dcnt", null), conn);

        }catch (Exception ex){
            conn.close();
            return null;
        }

    }

    public List<dcnt> toList(Cursor cursor, SQLiteDatabase db){
        ArrayList<dcnt> dcnts = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                dcnt dcnt = new dcnt();
                dcnt.setCdDcnt(cursor.getInt(cursor.getColumnIndex("_cdDcnt")));
                dcnt.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                dcnts.add(dcnt);
            }while (cursor.moveToNext());
        }
        return dcnts;
    }
}


