package com.example.usuario.cookiereader.control;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import com.example.usuario.cookiereader.DAO.UfDAO;
import com.example.usuario.cookiereader.R;
import com.example.usuario.cookiereader.database.DataBase;
import com.example.usuario.cookiereader.domain.UF;

import java.util.List;

public class UfCadastro extends AppCompatActivity {

    private UfDAO ufDAO;
    private DataBase dataBase;
    private SQLiteDatabase conn;
    private UF uf;

    private Button btnInserir;
    private Button btnVer;
    private Button btnDeletar;
    private Button btnAlterar;

    private EditText editNome;
    private EditText editSigla;

    private Button btn_back;
    private ListView listUf;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_uf_cadastro);
        dataBase = new DataBase(this);
        conn = dataBase.getWritableDatabase();
        ufDAO = new UfDAO(conn);

        btnAlterar = (Button) findViewById(R.id.btnAlterar);
        btnVer = (Button) findViewById(R.id.btnVer);
        btnDeletar = (Button) findViewById(R.id.btnDeletar);
        btnInserir = (Button) findViewById(R.id.btnInserir);

        btn_back = (Button) findViewById(R.id.bnt_back);

        editNome = (EditText) findViewById(R.id.editNome);
        editSigla = (EditText) findViewById(R.id.editSigla);

        listUf = (ListView) findViewById(R.id.listUf);
        listarUf();

        listUf.setOnItemClickListener (new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view,int position, long id)
            {
                uf = (UF)listUf.getItemAtPosition(position);
            }
        });
    }



    public void listarUf(){
        List<UF> ufs;
        ufs = ufDAO.BuscarTodos();
        ArrayAdapter<UF> arrayAdapter = new ArrayAdapter<UF>(this, android.R.layout.simple_list_item_checked, ufs);
        listUf.setAdapter(arrayAdapter);
    }

    public void sair(View view){
        conn.close();
        finish();
    }

    public void inserir(View view){
        UF uf = new UF();

        uf.setNome(editNome.getText().toString());
        uf.setSigla(editSigla.getText().toString());

        ufDAO.inserir(uf);
        listarUf();
    }

    public void deletar(View view){
        ufDAO.excluir(uf.getSigla());
        listarUf();
    }

    public void ver(View view){
        editNome.setText(uf.getNome());
        editSigla.setText(uf.getSigla());
    }

    public void alterar(View view){
        uf.setNome(editNome.getText().toString());
        ufDAO.alterar(uf);
        listarUf();
    }

    public void limpar(View view){
        editNome.setText(null);
        editSigla.setText(null);

    }
}
