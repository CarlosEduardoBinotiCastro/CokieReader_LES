package com.example.usuario.cookiereader.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.usuario.cookiereader.domain.Scan;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usuario on 29/06/2017.
 */

public class ScanDAO {

    private SQLiteDatabase conn;

    public ScanDAO (SQLiteDatabase conn){
        this.conn = conn;
    }

    private ContentValues preencheContentValues(Scan Scan)
    {
        ContentValues values = new ContentValues();

        values.put("cdBiscoito", Scan.getCdBiscoito());
        values.put("cdDcnt", Scan.getCdDcnt());
        values.put("cdUsuario", Scan.getCdUsuario());
        values.put("favorito", 0);
        values.put("dataHora", Scan.getData());

        return values;

    }

    public void excluir(int cdDcnt, int cdNutriente)
    {
        conn.delete("Scan","cdDcnt=? and cdNutriente=?",new String[]{String.valueOf(cdDcnt),String.valueOf(cdNutriente)});
    }

    public void alterar(Scan Scan)
    {
        ContentValues values = preencheContentValues(Scan);
        conn.update("Scan", values, "cdDcnt = ? ", new String[]{ String.valueOf( Scan.getCdDcnt()) } );

    }

    public void inserir(Scan Scan)
    {
        ContentValues values = preencheContentValues(Scan);
        conn.insert("Scan", null, values);
    }



    public List<Scan> BuscarTodos(int cdUsuario){
        try{
            return toList(conn.rawQuery("select * from Scan where cdDcnt = '"+cdUsuario+"'", null), conn);

        }catch (Exception ex){
            conn.close();
            return null;
        }

    }



    public List<Scan> toList(Cursor cursor, SQLiteDatabase db){

        ArrayList<Scan> Scans = new ArrayList<>();
        if(cursor.moveToFirst()){

            do{
                Scan Scan1 = new Scan();
                Scan1.setCdDcnt(cursor.getInt(cursor.getColumnIndex("cdDcnt")));
                Scan1.setCdBiscoito(cursor.getInt(cursor.getColumnIndex("cdBiscoito")));
                Scan1.setCdUsuario(cursor.getInt(cursor.getColumnIndex("cdUsuario")));
                Scan1.setFavorito(cursor.getInt(cursor.getColumnIndex("favorito")));
                Scan1.setData(cursor.getString(cursor.getColumnIndex("dataHora")));
                Scans.add(Scan1);
            }while (cursor.moveToNext());
        }
        return Scans;
    }

}
