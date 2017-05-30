package com.example.usuario.cookiereader.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.usuario.cookiereader.domain.Cidade;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usuario on 28/05/2017.
 */

public class CidadeDAO {

    private SQLiteDatabase conn;

    public CidadeDAO(SQLiteDatabase conn){
        this.conn = conn;
    }


    private ContentValues preencheContentValues(Cidade Cidade)
    {
        ContentValues values = new ContentValues();

        values.put("nome", Cidade.getNome());
        values.put("siglaUf", Cidade.getSiglaUf());

        return values;

    }

    public void excluir(int id)
    {
        conn.delete("Cidade", " _cdCidade = ? ", new String[]{ String.valueOf( id ) });
    }

    public void alterar(Cidade Cidade)
    {
        ContentValues values = preencheContentValues(Cidade);
        conn.update("Cidade", values, " _cdCidade = ? ", new String[]{ String.valueOf( Cidade.getCdCidade()) } );

    }

    public void inserir(Cidade Cidade)
    {
        ContentValues values = preencheContentValues(Cidade);
        conn.insert("Cidade", null, values);
    }

    public Cidade buscar(int cidade){
        Cidade cidade1 = new Cidade();

        Cursor cursor = conn.rawQuery("select * from Cidade where _cdCidade = '"+Integer.toString(cidade)+"'", null);
        if(cursor.moveToFirst()) {
            cidade1.setCdCidade(cursor.getInt(cursor.getColumnIndex("_cdCidade")));
            cidade1.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            cidade1.setSiglaUf(cursor.getString(cursor.getColumnIndex("siglaUf")));
        }
        return  cidade1;
    }


    public List<Cidade> BuscarTodos(){
        try{
            return toList(conn.rawQuery("select * from Cidade", null), conn);

        }catch (Exception ex){
            conn.close();
            return null;
        }

    }

    public List<Cidade> toList(Cursor cursor, SQLiteDatabase db){
        ArrayList<Cidade> Cidade = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                Cidade cidade = new Cidade();
                cidade.setCdCidade(cursor.getInt(cursor.getColumnIndex("_cdCidade")));
                cidade.setSiglaUf(cursor.getString(cursor.getColumnIndex("siglaUf")));
                cidade.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                Cidade.add(cidade);
            }while (cursor.moveToNext());
        }
        return Cidade;
    }


    public List<Cidade> BuscarTodosPorUF(String uf){
        try{
            return toList(conn.rawQuery("select * from Cidade where siglaUf = '"+uf+"'", null), conn);

        }catch (Exception ex){
            conn.close();
            return null;
        }

    }
}
