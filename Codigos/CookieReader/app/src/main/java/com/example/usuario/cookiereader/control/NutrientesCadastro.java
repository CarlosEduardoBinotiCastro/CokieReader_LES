package com.example.usuario.cookiereader.control;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;


import com.example.usuario.cookiereader.DAO.NutrienteDAO;
import com.example.usuario.cookiereader.R;
import com.example.usuario.cookiereader.database.DataBase;
import com.example.usuario.cookiereader.domain.Nutrientes;
import com.example.usuario.cookiereader.domain.dcnt;

import java.util.List;

public class NutrientesCadastro extends AppCompatActivity {

    private NutrienteDAO nutrienteDAO;
    private DataBase dataBase;
    private SQLiteDatabase conn;
    private Nutrientes nutriente;

    private Button btnInserir;
    private Button btnVer;
    private Button btnDeletar;
    private Button btnAlterar;

    private EditText editNome;
    private TextView editCd;

    private Button btn_back;
    private ListView listNutriente;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nutrientes_cadastro);
        dataBase = new DataBase(this);
        conn = dataBase.getWritableDatabase();
        nutrienteDAO = new NutrienteDAO(conn);

        btnAlterar = (Button) findViewById(R.id.btnAlterar);
        btnVer = (Button) findViewById(R.id.btnVer);
        btnDeletar = (Button) findViewById(R.id.btnDeletar);
        btnInserir = (Button) findViewById(R.id.btnInserir);

        btn_back = (Button) findViewById(R.id.bnt_back);

        editCd =(TextView) findViewById(R.id.editCd);
        editNome = (EditText) findViewById(R.id.editNome);

        listNutriente = (ListView) findViewById(R.id.listNutriente);
        listarNutrientes();

        listNutriente.setOnItemClickListener (new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                nutriente = (Nutrientes) listNutriente.getItemAtPosition(position);
            }
        });
    }

    public void listarNutrientes(){
        List<Nutrientes> nutrientes;
        nutrientes = nutrienteDAO.BuscarTodos();
        ArrayAdapter<Nutrientes> arrayAdapter = new ArrayAdapter<Nutrientes>(this, android.R.layout.simple_list_item_checked, nutrientes);
        listNutriente.setAdapter(arrayAdapter);
    }

    public void sair(View view){
        conn.close();
        finish();
    }

    public void inserir(View view){
        Nutrientes nutrientes = new Nutrientes();

        nutrientes.setNome(editNome.getText().toString());

        nutrienteDAO.inserir(nutrientes);
        listarNutrientes();
    }

    public void deletar(View view){
        nutrienteDAO.excluir(nutriente.getCdNutriente());
        listarNutrientes();
    }

    public void ver(View view){

        editNome.setText(nutriente.getNome());
        editCd.setText(Integer.toString(nutriente.getCdNutriente()));
    }

    public void alterar(View view){
        nutriente.setNome(editNome.getText().toString());
        nutrienteDAO.alterar(nutriente);
        listarNutrientes();
    }

    public void limpar(View view){
        editNome.setText(null);
        editCd.setText(null);

    }
}
