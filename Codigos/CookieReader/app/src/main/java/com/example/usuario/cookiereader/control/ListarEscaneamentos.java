package com.example.usuario.cookiereader.control;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.usuario.cookiereader.DAO.ScanDAO;
import com.example.usuario.cookiereader.Misc.Sessao;
import com.example.usuario.cookiereader.R;
import com.example.usuario.cookiereader.database.DataBase;
import com.example.usuario.cookiereader.domain.Scan;
import com.example.usuario.cookiereader.domain.scanBiscoito;

import java.util.List;

public class ListarEscaneamentos extends AppCompatActivity {

    DataBase dataBase;
    SQLiteDatabase conn;

    private ListView listEscaneamentos;
    private List<Scan> scans;
    private ScanDAO scanDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listar_escaneamentos);
        Sessao sessao = new Sessao();
        dataBase = new DataBase(this);
        conn = dataBase.getWritableDatabase();
        scanDAO = new ScanDAO(conn);
        int teste = sessao.getUsuario().getCdUsuario();
        listEscaneamentos = (ListView) findViewById(R.id.listEscaneamento);
        scans = scanDAO.BuscarTodosUsuario(sessao.getUsuario().getCdUsuario());

        ArrayAdapter<Scan> arrayAdapter = new ArrayAdapter<Scan>(this, android.R.layout.simple_list_item_checked, scans);
        listEscaneamentos.setAdapter(arrayAdapter);

    }


    public void sair(View view){
        finish();
    }
}
