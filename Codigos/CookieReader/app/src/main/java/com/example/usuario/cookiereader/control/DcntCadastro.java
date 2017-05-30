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

import com.example.usuario.cookiereader.DAO.DcntDAO;
import com.example.usuario.cookiereader.R;
import com.example.usuario.cookiereader.database.DataBase;
import com.example.usuario.cookiereader.domain.dcnt;

import java.util.List;

public class DcntCadastro extends AppCompatActivity {

    private DcntDAO dcntDAO;
    private DataBase dataBase;
    private SQLiteDatabase conn;
    private com.example.usuario.cookiereader.domain.dcnt dcnt;

    private Button btnInserir;
    private Button btnVer;
    private Button btnDeletar;
    private Button btnAlterar;

    private EditText editNome;
    private TextView editCd;

    private Button btn_back;
    private ListView listarDcnt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dcnt_cadastro);
        dataBase = new DataBase(this);
        conn = dataBase.getWritableDatabase();
        dcntDAO = new DcntDAO(conn);

        btnAlterar = (Button) findViewById(R.id.btnAlterar);
        btnVer = (Button) findViewById(R.id.btnVer);
        btnDeletar = (Button) findViewById(R.id.btnDeletar);
        btnInserir = (Button) findViewById(R.id.btnInserir);

        btn_back = (Button) findViewById(R.id.bnt_back);

        editCd =(TextView) findViewById(R.id.editCd);
        editNome = (EditText) findViewById(R.id.editNome);

        listarDcnt = (ListView) findViewById(R.id.listDcnt);
        listarDcnt();

        listarDcnt.setOnItemClickListener (new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                dcnt = (com.example.usuario.cookiereader.domain.dcnt) listarDcnt.getItemAtPosition(position);
            }
        });
    }



    public void listarDcnt(){
        List<com.example.usuario.cookiereader.domain.dcnt> dcnts;
        dcnts = dcntDAO.BuscarTodos();
        ArrayAdapter<com.example.usuario.cookiereader.domain.dcnt> arrayAdapter = new ArrayAdapter<com.example.usuario.cookiereader.domain.dcnt>(this, android.R.layout.simple_list_item_checked, dcnts);
        listarDcnt.setAdapter(arrayAdapter);
    }

    public void sair(View view){
        conn.close();
        finish();
    }

    public void inserir(View view){
        com.example.usuario.cookiereader.domain.dcnt dcnt = new com.example.usuario.cookiereader.domain.dcnt();

        dcnt.setNome(editNome.getText().toString());

        dcntDAO.inserir(dcnt);
        listarDcnt();
    }

    public void deletar(View view){
        dcntDAO.excluir(dcnt.getCdDcnt());
        listarDcnt();
    }

    public void ver(View view){

        editNome.setText(dcnt.getNome());
        editCd.setText(Integer.toString(dcnt.getCdDcnt()));
    }

    public void alterar(View view){
        dcnt.setNome(editNome.getText().toString());
        dcntDAO.alterar(dcnt);
        listarDcnt();
    }

    public void limpar(View view){
        editNome.setText(null);
        editCd.setText(null);

    }
}
