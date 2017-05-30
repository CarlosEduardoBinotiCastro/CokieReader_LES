package com.example.usuario.cookiereader.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.usuario.cookiereader.domain.Nutrientes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usuario on 28/05/2017.
 */

public class NutrienteDAO {

    private SQLiteDatabase conn;

    public NutrienteDAO(SQLiteDatabase conn){
        this.conn = conn;
    }


    private ContentValues preencheContentValues(Nutrientes Nutrientes)
    {
        ContentValues values = new ContentValues();

        values.put("nome", Nutrientes.getNome());

        return values;

    }

    public void excluir(int id)
    {
        conn.delete("Nutriente", " _cdNutriente = ? ", new String[]{ String.valueOf( id ) });
    }

    public void alterar(Nutrientes Nutrientes)
    {
        ContentValues values = preencheContentValues(Nutrientes);
        conn.update("Nutriente", values, " _cdNutriente = ? ", new String[]{ String.valueOf( Nutrientes.getCdNutriente()) } );

    }

    public void inserir(Nutrientes Nutrientes)
    {
        ContentValues values = preencheContentValues(Nutrientes);
        conn.insert("Nutriente", null, values);
    }


    public List<Nutrientes> BuscarTodos(){
        try{
            return toList(conn.rawQuery("select * from Nutriente", null), conn);

        }catch (Exception ex){
            conn.close();
            return null;
        }

    }

    public List<Nutrientes> toList(Cursor cursor, SQLiteDatabase db){
        ArrayList<Nutrientes> Nutrientes = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                Nutrientes Nutriente = new Nutrientes();
                Nutriente.setCdNutriente(cursor.getInt(cursor.getColumnIndex("_cdNutriente")));
                Nutriente.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                Nutrientes.add(Nutriente);
            }while (cursor.moveToNext());
        }
        return Nutrientes;
    }

}
