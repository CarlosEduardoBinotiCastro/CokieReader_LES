package com.example.usuario.cookiereader.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.usuario.cookiereader.domain.BiscoitoNutriente;
import com.example.usuario.cookiereader.domain.Nutrientes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usuario on 23/06/2017.
 */

public class BiscoitoNutrienteDAO {

    private SQLiteDatabase conn;

    public BiscoitoNutrienteDAO(SQLiteDatabase conn){
        this.conn = conn;
    }

    private ContentValues preencheContentValues(BiscoitoNutriente biscoitoNutriente)
    {
        ContentValues values = new ContentValues();

        values.put("cdNutriente", biscoitoNutriente.getCdNutrientes());
        values.put("cdBiscoito", biscoitoNutriente.getCdBiscoito());
        values.put("quant", biscoitoNutriente.getQuant());

        return values;

    }

    public void excluir(int id)
    {

        conn.delete("BiscoitoNutriente", "cdBiscoito = ?", new String[]{ String.valueOf( id ) });

    }

    public void alterar(BiscoitoNutriente biscoitoNutriente)
    {
        ContentValues values = preencheContentValues(biscoitoNutriente);
        conn.update("BiscoitoNutriente", values, "cdBiscoito = ? ", new String[]{ String.valueOf( biscoitoNutriente.getCdBiscoito()) } );

    }

    public void inserir(BiscoitoNutriente biscoitoNutriente)
    {
        ContentValues values = preencheContentValues(biscoitoNutriente);
        conn.insert("BiscoitoNutriente", null, values);
    }



    public List<BiscoitoNutriente> BuscarTodos(int cdBiscoito){
        try{
            return toList(conn.rawQuery("select * from BiscoitoNutriente where cdBiscoito = '"+cdBiscoito+"'", null), conn);

        }catch (Exception ex){
            conn.close();
            return null;
        }

    }



    public List<BiscoitoNutriente> toList(Cursor cursor, SQLiteDatabase db){

        ArrayList<BiscoitoNutriente> biscoitoNutriente = new ArrayList<>();
        if(cursor.moveToFirst()){

            do{
                NutrienteDAO nutrienteDAO = new NutrienteDAO(conn);
                Nutrientes nutrientes = nutrienteDAO.buscar(cursor.getInt(cursor.getColumnIndex("cdNutriente")));

                BiscoitoNutriente biscoitoNutriente1 = new BiscoitoNutriente();
                biscoitoNutriente1.setCdBiscoito(cursor.getInt(cursor.getColumnIndex("cdBiscoito")));
                biscoitoNutriente1.setCdNutrientes(cursor.getInt(cursor.getColumnIndex("cdNutriente")));
                biscoitoNutriente1.setQuant(cursor.getFloat(cursor.getColumnIndex("quant")));
                biscoitoNutriente1.setNomeNutriente(nutrientes.getNome());
                biscoitoNutriente.add(biscoitoNutriente1);
            }while (cursor.moveToNext());
        }
        return biscoitoNutriente;
    }

}
