package com.example.usuario.cookiereader.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.usuario.cookiereader.domain.Empresa;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usuario on 28/05/2017.
 */

public class EmpresaDAO {

    private SQLiteDatabase conn;
    private Boolean teste = false;

    public EmpresaDAO(SQLiteDatabase conn){
        this.conn = conn;
    }


    private ContentValues preencheContentValues(Empresa Empresa)
    {
        ContentValues values = new ContentValues();

        values.put("nome", Empresa.getNome());
        values.put("cdCidade", Empresa.getCdCidade());

        return values;

    }


    public List<Empresa> buscarPorScan(){
        teste = true;
        try{
            return toList(conn.rawQuery("select e._cdEmpresa, e.nome, e.cdCidade, COUNT(*) as total from Empresa e, ScanBiscoito s, Biscoito b WHERE s.cdBiscoito = b._cdBiscoito AND b.cdEmpresa = e._cdEmpresa GROUP BY e._cdEmpresa ORDER BY total DESC;", null), conn);

        }catch (Exception ex){
            conn.close();
            return null;
        }
    }

    public void excluir(int id)
    {
        conn.delete("Empresa", " _cdEmpresa = ? ", new String[]{ String.valueOf( id ) });
    }

    public void alterar(Empresa Empresa)
    {
        ContentValues values = preencheContentValues(Empresa);
        conn.update("Empresa", values, " _cdEmpresa = ? ", new String[]{ String.valueOf( Empresa.getCdEmpresa()) } );

    }

    public void inserir(Empresa Empresa)
    {
        ContentValues values = preencheContentValues(Empresa);
        conn.insert("Empresa", null, values);
    }

    public Empresa buscar(int Empresa){
        Empresa Empresa1 = new Empresa();

        Cursor cursor = conn.rawQuery("select * from Empresa where _cdEmpresa = '"+Integer.toString(Empresa)+"'", null);
        if(cursor.moveToFirst()) {
            Empresa1.setCdEmpresa(cursor.getInt(cursor.getColumnIndex("_cdEmpresa")));
            Empresa1.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            Empresa1.setCdCidade(cursor.getInt(cursor.getColumnIndex("cdCidade")));
        }
        return  Empresa1;
    }


    public List<Empresa> BuscarTodos(){
        try{
            return toList(conn.rawQuery("select * from Empresa", null), conn);

        }catch (Exception ex){
            conn.close();
            return null;
        }

    }

    public List<Empresa> toList(Cursor cursor, SQLiteDatabase db){
        ArrayList<Empresa> Empresas = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                Empresa Empresa = new Empresa();
                Empresa.setCdEmpresa(cursor.getInt(cursor.getColumnIndex("_cdEmpresa")));
                Empresa.setCdCidade(cursor.getInt(cursor.getColumnIndex("cdCidade")));
                Empresa.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                if(teste){
                    Empresa.setScans(cursor.getInt(cursor.getColumnIndex("total")));
                }
                Empresas.add(Empresa);
            }while (cursor.moveToNext());
        }
        return Empresas;
    }

}
