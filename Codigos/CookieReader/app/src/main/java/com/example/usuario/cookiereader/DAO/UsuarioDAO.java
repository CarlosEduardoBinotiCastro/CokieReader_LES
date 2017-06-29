package com.example.usuario.cookiereader.DAO;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.usuario.cookiereader.domain.Usuario;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Usuario on 29/05/2017.
 */

public class UsuarioDAO {

    private SQLiteDatabase conn;

    public UsuarioDAO(SQLiteDatabase conn){
        this.conn = conn;
    }


    private ContentValues preencheContentValues(Usuario Usuario)
    {
        ContentValues values = new ContentValues();

        values.put("nome", Usuario.getNome());
        values.put("cdCidade", Usuario.getCdCidade());
        values.put("login", Usuario.getLogin());
        values.put("senha",Usuario.getSenha());
        values.put("cpf", Usuario.getCpf());
        return values;

    }



    public void excluir(int id)
    {
        conn.delete("Usuario", " _cdUsuario = ? ", new String[]{ String.valueOf( id ) });
    }

    public void alterar(Usuario Usuario)
    {
        ContentValues values = preencheContentValues(Usuario);
        conn.update("Usuario", values, " _cdUsuario = ? ", new String[]{ String.valueOf( Usuario.getCdUsuario()) } );

    }

    public void alterarData(Usuario Usuario)
    {
        ContentValues values = new ContentValues();
        values.put("dataSessao", Usuario.getDataSessao());
        conn.update("Usuario", values, " _cdUsuario = ? ", new String[]{ String.valueOf( Usuario.getCdUsuario()) } );

    }
    public void inserir(Usuario Usuario)
    {
        ContentValues values = preencheContentValues(Usuario);
        conn.insert("Usuario", null, values);
    }

    public Usuario buscar(int Usuario){
        Usuario Usuario1 = new Usuario();

        Cursor cursor = conn.rawQuery("select * from Usuario where _cdUsuario = '"+Integer.toString(Usuario)+"'", null);
        if(cursor.moveToFirst()) {
            Usuario1.setCdUsuario(cursor.getInt(cursor.getColumnIndex("_cdUsuario")));
            Usuario1.setNome(cursor.getString(cursor.getColumnIndex("nome")));
            Usuario1.setCdCidade(cursor.getInt(cursor.getColumnIndex("cdCidade")));
            Usuario1.setCpf(cursor.getString(cursor.getColumnIndex("cpf")));
            Usuario1.setSenha(cursor.getString(cursor.getColumnIndex("senha")));
            Usuario1.setLogin(cursor.getString(cursor.getColumnIndex("login")));
            Usuario1.setDataSessao(cursor.getString(cursor.getColumnIndex("dataSessao")));
        }
        return  Usuario1;
    }


    public List<Usuario> BuscarTodos(){
        try{
            return toList(conn.rawQuery("select * from Usuario", null), conn);

        }catch (Exception ex){
            conn.close();
            return null;
        }

    }




    public List<Usuario> toList(Cursor cursor, SQLiteDatabase db){
        ArrayList<Usuario> Usuarios = new ArrayList<>();
        if(cursor.moveToFirst()){
            do{
                Usuario Usuario1 = new Usuario();
                Usuario1.setCdUsuario(cursor.getInt(cursor.getColumnIndex("_cdUsuario")));
                Usuario1.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                Usuario1.setCdCidade(cursor.getInt(cursor.getColumnIndex("cdCidade")));
                Usuario1.setCpf(cursor.getString(cursor.getColumnIndex("cpf")));
                Usuario1.setSenha(cursor.getString(cursor.getColumnIndex("senha")));
                Usuario1.setLogin(cursor.getString(cursor.getColumnIndex("login")));
                Usuario1.setDataSessao(cursor.getString(cursor.getColumnIndex("dataSessao")));
                Usuarios.add(Usuario1);
            }while (cursor.moveToNext());
        }
        return Usuarios;
    }

}
