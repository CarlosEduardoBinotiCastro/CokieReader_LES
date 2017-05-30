package com.example.usuario.cookiereader.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.usuario.cookiereader.domain.Biscoito;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usuario on 29/05/2017.
 */

public class BiscoitoDAO {

    private SQLiteDatabase conn;

    public BiscoitoDAO(SQLiteDatabase conn){
        this.conn = conn;
    }


    private ContentValues preencheContentValues(Biscoito Biscoito)
    {
        ContentValues values = new ContentValues();

        values.put("nome", Biscoito.getNome());
        values.put("cdEmpresa", Biscoito.getCdEmpresa());

        return values;

    }

    public void excluir(int id)
    {
        conn.delete("Biscoito", " _cdBiscoito = ? ", new String[]{ String.valueOf( id ) });
    }

    public void alterar(Biscoito Biscoito)
    {
        ContentValues values = preencheContentValues(Biscoito);
        conn.update("Biscoito", values, " _cdBiscoito = ? ", new String[]{ String.valueOf( Biscoito.getCdBiscoito()) } );

    }

    public void inserir(Biscoito Biscoito)
    {
        ContentValues values = preencheContentValues(Biscoito);
        conn.insert("Biscoito", null, values);
    }

    public Biscoito buscar(int Biscoito){
        Biscoito Biscoito1 = new Biscoito();

        Cursor cursor = conn.rawQuery("select * from Biscoito where _cdBiscoito = '"+Integer.toString(Biscoito)+"'", null);
        if(cursor.moveToFirst()) {
            Biscoito1.setCdBiscoito(cursor.getInt(cursor.getColumnIndex("_cdBiscoito")));
            Biscoito1.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            Biscoito1.setCdEmpresa(cursor.getInt(cursor.getColumnIndex("cdEmpresa")));
        }
        return  Biscoito1;
    }


    public List<Biscoito> BuscarTodos(){
        try{
            return toList(conn.rawQuery("select * from Biscoito", null), conn);

        }catch (Exception ex){
            conn.close();
            return null;
        }

    }

    public List<Biscoito> toList(Cursor cursor, SQLiteDatabase db){
        ArrayList<Biscoito> Biscoito = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                Biscoito Biscoito1 = new Biscoito();
                Biscoito1.setCdBiscoito(cursor.getInt(cursor.getColumnIndex("_cdBiscoito")));
                Biscoito1.setCdEmpresa(cursor.getInt(cursor.getColumnIndex("cdEmpresa")));
                Biscoito1.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                Biscoito.add(Biscoito1);
            }while (cursor.moveToNext());
        }
        return Biscoito;
    }


}
