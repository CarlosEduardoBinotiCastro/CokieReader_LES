package com.example.usuario.cookiereader.control;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.usuario.cookiereader.DAO.EmpresaDAO;
import com.example.usuario.cookiereader.R;
import com.example.usuario.cookiereader.database.DataBase;
import com.example.usuario.cookiereader.domain.Empresa;
import com.example.usuario.cookiereader.domain.dcnt;

import java.util.List;

public class ListarEmpresas extends AppCompatActivity {


    DataBase dataBase;
    SQLiteDatabase conn;

    private ListView listEmpresas;
    private List<Empresa> empresas;
    private EmpresaDAO empresaDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_empresas);

        dataBase = new DataBase(this);
        conn = dataBase.getWritableDatabase();
        empresaDAO = new EmpresaDAO(conn);

        listEmpresas = (ListView) findViewById(R.id.listEmpresas);
        empresas = empresaDAO.buscarPorScan();

        ArrayAdapter<Empresa> arrayAdapter = new ArrayAdapter<Empresa>(this, android.R.layout.simple_list_item_checked, empresas);
        listEmpresas.setAdapter(arrayAdapter);

    }


    public void sair(View view){
        finish();
    }
}
