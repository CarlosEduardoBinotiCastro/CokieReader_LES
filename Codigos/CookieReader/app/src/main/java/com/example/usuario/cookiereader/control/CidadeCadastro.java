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
import android.widget.Spinner;
import android.widget.TextView;

import com.example.usuario.cookiereader.DAO.CidadeDAO;
import com.example.usuario.cookiereader.DAO.UfDAO;
import com.example.usuario.cookiereader.R;
import com.example.usuario.cookiereader.database.DataBase;
import com.example.usuario.cookiereader.domain.Cidade;
import com.example.usuario.cookiereader.domain.UF;

import java.util.List;

public class CidadeCadastro extends AppCompatActivity {

    private CidadeDAO cidadeDAO;
    private DataBase dataBase;
    private SQLiteDatabase conn;
    private Cidade cidade;

    private Button btnInserir;
    private Button btnVer;
    private Button btnDeletar;
    private Button btnAlterar;

    private EditText editNome;
    private TextView editCd;

    private Button btn_back;
    private ListView listCidade;

    private Spinner spinUf;
    List<UF> ufs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cidade_cadastro);
        dataBase = new DataBase(this);
        conn = dataBase.getWritableDatabase();
        cidadeDAO = new CidadeDAO(conn);

        btnAlterar = (Button) findViewById(R.id.btnAlterar);
        btnVer = (Button) findViewById(R.id.btnVer);
        btnDeletar = (Button) findViewById(R.id.btnDeletar);
        btnInserir = (Button) findViewById(R.id.btnInserir);
        btn_back = (Button) findViewById(R.id.bnt_back);

        editCd =(TextView) findViewById(R.id.editCd);
        editNome = (EditText) findViewById(R.id.editNome);

        spinUf = (Spinner) findViewById(R.id.spinUf);
        carregarComboBox();

        listCidade = (ListView) findViewById(R.id.listCidade);
        listarCidades();

        listCidade.setOnItemClickListener (new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                cidade = (Cidade) listCidade.getItemAtPosition(position);
            }
        });
    }

    public void listarCidades(){
        List<Cidade> cidade;
        cidade = cidadeDAO.BuscarTodos();
        ArrayAdapter<Cidade> arrayAdapter = new ArrayAdapter<Cidade>(this, android.R.layout.simple_list_item_checked, cidade);
        listCidade.setAdapter(arrayAdapter);
    }

    public void carregarComboBox(){
        UfDAO ufDAO = new UfDAO(conn);
        ufs = ufDAO.BuscarTodos();
        ArrayAdapter<UF> arrayAdapter = new ArrayAdapter<UF>(this, android.R.layout.simple_spinner_item, ufs);
        spinUf.setAdapter(arrayAdapter);
    }

    public void sair(View view){
        conn.close();
        finish();
    }

    public void inserir(View view){
        Cidade cidade = new Cidade();
        UF uf = new UF();
        uf = (UF) spinUf.getSelectedItem();
        cidade.setNome(editNome.getText().toString());
        cidade.setSiglaUf(uf.getSigla());

        cidadeDAO.inserir(cidade);
        listarCidades();
    }

    public void deletar(View view){
        cidadeDAO.excluir(cidade.getCdCidade());
        listarCidades();
    }

    public void ver(View view){
        int posicao = 0;
        UfDAO ufDAO = new UfDAO(conn);
        UF uf;

        uf = ufDAO.buscar(cidade.getSiglaUf());
        editNome.setText(cidade.getNome());
        editCd.setText(Integer.toString(cidade.getCdCidade()));

        for(int i =0; i < ufs.size(); i++){
            if(ufs.get(i).getNome().equals(uf.getNome())){
                posicao = i;
            }
        }

        spinUf.setSelection(posicao);
    }

    public void alterar(View view){
        UF uf;
        uf = (UF) spinUf.getSelectedItem();
        cidade.setNome(editNome.getText().toString());
        cidade.setSiglaUf(uf.getSigla());
        cidadeDAO.alterar(cidade);
        listarCidades();
    }

    public void limpar(View view){
        editNome.setText(null);
        editCd.setText(null);
        spinUf.setSelection(0);
    }

}
