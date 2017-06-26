package com.example.usuario.cookiereader.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.usuario.cookiereader.domain.DCNTpeso;
import com.example.usuario.cookiereader.domain.Nutrientes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usuario on 26/06/2017.
 */

public class DcntPesoDAO {

    private SQLiteDatabase conn;

    public DcntPesoDAO (SQLiteDatabase conn){
        this.conn = conn;
    }

    private ContentValues preencheContentValues(DCNTpeso dcnTpeso)
    {
        ContentValues values = new ContentValues();

        values.put("cdNutriente", dcnTpeso.getCdNutriente());
        values.put("cdDcnt", dcnTpeso.getCdDcnt());
        values.put("peso", dcnTpeso.getPeso());

        return values;

    }

    public void excluir(int cdDcnt, int cdNutriente)
    {
        conn.delete("DCNTpeso","cdDcnt=? and cdNutriente=?",new String[]{String.valueOf(cdDcnt),String.valueOf(cdNutriente)});
    }

    public void alterar(DCNTpeso dcnTpeso)
    {
        ContentValues values = preencheContentValues(dcnTpeso);
        conn.update("DCNTpeso", values, "cdDcnt = ? ", new String[]{ String.valueOf( dcnTpeso.getCdDcnt()) } );

    }

    public void inserir(DCNTpeso dcnTpeso)
    {
        ContentValues values = preencheContentValues(dcnTpeso);
        conn.insert("DCNTpeso", null, values);
    }



    public List<DCNTpeso> BuscarTodos(int cdDcnt){
        try{
            return toList(conn.rawQuery("select * from DCNTpeso where cdDcnt = '"+cdDcnt+"'", null), conn);

        }catch (Exception ex){
            conn.close();
            return null;
        }

    }



    public List<DCNTpeso> toList(Cursor cursor, SQLiteDatabase db){

        ArrayList<DCNTpeso> dcnTpesos = new ArrayList<>();
        if(cursor.moveToFirst()){

            do{
                NutrienteDAO nutrienteDAO = new NutrienteDAO(conn);
                Nutrientes nutrientes = nutrienteDAO.buscar(cursor.getInt(cursor.getColumnIndex("cdNutriente")));

                DCNTpeso dcnTpeso1 = new DCNTpeso();
                dcnTpeso1.setCdDcnt(cursor.getInt(cursor.getColumnIndex("cdDcnt")));
                dcnTpeso1.setCdNutriente(cursor.getInt(cursor.getColumnIndex("cdNutriente")));
                dcnTpeso1.setPeso(cursor.getInt(cursor.getColumnIndex("peso")));
                dcnTpeso1.setNomeNutriente(nutrientes.getNome());
                dcnTpesos.add(dcnTpeso1);
            }while (cursor.moveToNext());
        }
        return dcnTpesos;
    }
}
