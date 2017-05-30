package com.example.usuario.cookiereader.Misc;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;

import com.example.usuario.cookiereader.DAO.CidadeDAO;
import com.example.usuario.cookiereader.DAO.EmpresaDAO;
import com.example.usuario.cookiereader.DAO.UfDAO;
import com.example.usuario.cookiereader.domain.Cidade;
import com.example.usuario.cookiereader.domain.Empresa;
import com.example.usuario.cookiereader.domain.UF;

/**
 * Created by Usuario on 22/05/2017.
 */

public class FirstLogin {

    private Context context;
    private SQLiteDatabase conn;

    public FirstLogin(Context context, SQLiteDatabase conn){
        this.context = context;
        this.conn = conn;
    }

    public void firstLogin(){
        Md5 md5gerator = new Md5();
        Cursor cur = conn.rawQuery("SELECT COUNT(*) FROM Usuario", null);
        String senha = md5gerator.gerarMd5("admin");

        if(cur != null) {
            cur.moveToFirst();
            if (cur.getInt (0) == 0) {
                ContentValues values = new ContentValues();
                values.put("nome", "admin");
                values.put("login", "admin");
                values.put("senha", senha);
                values.put("cpf", "111.111.111-11");
                values.put("cdCidade", 1);
                conn.insert("Usuario", null, values);

                CidadeDAO cidadeDAO = new CidadeDAO(conn);
                UfDAO ufDAO = new UfDAO(conn);
                EmpresaDAO empresaDAO = new EmpresaDAO(conn);

                Empresa empresa = new Empresa();
                UF uf = new UF();
                Cidade cidade = new Cidade();

                uf.setNome("Espirito Santo");
                uf.setSigla("ES");
                ufDAO.inserir(uf);

                cidade.setNome("Cachoeiro");
                cidade.setSiglaUf("ES");
                cidadeDAO.inserir(cidade);

                empresa.setCdCidade(1);
                empresa.setNome("Selita");
                empresaDAO.inserir(empresa);

            } else {

            }
        }

    }

}
