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
import com.example.usuario.cookiereader.DAO.EmpresaDAO;
import com.example.usuario.cookiereader.DAO.UfDAO;
import com.example.usuario.cookiereader.R;
import com.example.usuario.cookiereader.database.DataBase;
import com.example.usuario.cookiereader.domain.Cidade;
import com.example.usuario.cookiereader.domain.Empresa;
import com.example.usuario.cookiereader.domain.UF;

import java.util.List;

public class EmpresaCadastro extends AppCompatActivity {

    private EmpresaDAO empresaDAO;
    private DataBase dataBase;
    private SQLiteDatabase conn;
    private Empresa empresa;

    private Button btnInserir;
    private Button btnVer;
    private Button btnDeletar;
    private Button btnAlterar;

    private EditText editNome;
    private TextView editCd;

    private Button btn_back;
    private ListView listEmpresa;

    private Spinner spinUf;
    private Spinner spinCidade;
    List<UF> ufs;
    List<Cidade> cidades;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_empresa_cadastro);
        dataBase = new DataBase(this);
        conn = dataBase.getWritableDatabase();
        empresaDAO = new EmpresaDAO(conn);

        btnAlterar = (Button) findViewById(R.id.btnAlterar);
        btnVer = (Button) findViewById(R.id.btnVer);
        btnDeletar = (Button) findViewById(R.id.btnDeletar);
        btnInserir = (Button) findViewById(R.id.btnInserir);
        btn_back = (Button) findViewById(R.id.bnt_back);

        editCd =(TextView) findViewById(R.id.editCd);
        editNome = (EditText) findViewById(R.id.editNome);

        spinUf = (Spinner) findViewById(R.id.spinUf);
        carregarComboBoxUf();
        spinCidade = (Spinner) findViewById(R.id.spinCidade);
        carregarComboBoxCidade();

        listEmpresa = (ListView) findViewById(R.id.listEmpresa);
        listarEmpresas();

        listEmpresa.setOnItemClickListener (new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                empresa = (Empresa) listEmpresa.getItemAtPosition(position);
            }
        });

        spinUf.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                carregarComboBoxCidade();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }

    public void listarEmpresas(){
        List<Empresa> empresas;
        empresas = empresaDAO.BuscarTodos();
        ArrayAdapter<Empresa> arrayAdapter = new ArrayAdapter<Empresa>(this, android.R.layout.simple_list_item_checked, empresas);
        listEmpresa.setAdapter(arrayAdapter);
    }

    public void carregarComboBoxCidade(){
        UF uf = (UF) spinUf.getSelectedItem();
        CidadeDAO cidadeDAO = new CidadeDAO(conn);
        cidades = cidadeDAO.BuscarTodosPorUF(uf.getSigla());
        ArrayAdapter<Cidade> arrayAdapter = new ArrayAdapter<Cidade>(this, android.R.layout.simple_spinner_item, cidades);
        spinCidade.setAdapter(arrayAdapter);
    }

    public void carregarComboBoxUf(){
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
        Empresa empresa = new Empresa();
        Cidade cidade;

        cidade = (Cidade) spinCidade.getSelectedItem();

        empresa.setNome(editNome.getText().toString());
        empresa.setCdCidade(cidade.getCdCidade());

        empresaDAO.inserir(empresa);
        listarEmpresas();
    }

    public void deletar(View view){
        empresaDAO.excluir(empresa.getCdEmpresa());
        listarEmpresas();
    }

    public void alterar(View view){
        Cidade cidade;
        cidade = (Cidade) spinCidade.getSelectedItem();

        empresa.setNome(editNome.getText().toString());
        empresa.setCdCidade(cidade.getCdCidade());
        empresaDAO.alterar(empresa);
        listarEmpresas();
    }

    public void limpar(View view){
        editNome.setText(null);
        editCd.setText(null);
        spinUf.setSelection(0);
    }

    public void ver(View view){
        int posicao = 0;
        CidadeDAO cidadeDAO = new CidadeDAO(conn);
        Cidade cidade = cidadeDAO.buscar(empresa.getCdCidade());

        UfDAO ufDAO = new UfDAO(conn);
        UF uf;
        uf = ufDAO.buscar(cidade.getSiglaUf());
        editNome.setText(empresa.getNome());
        editCd.setText(Integer.toString(empresa.getCdEmpresa()));
        for(int i =0; i < ufs.size(); i++){
            if(ufs.get(i).getNome().equals(uf.getNome())){
                posicao = i;
            }
        }
        spinUf.setSelection(posicao);
        carregarComboBoxCidade();
        for (int i = 0; i < cidades.size(); i++){
            if(cidades.get(i).getCdCidade()== cidade.getCdCidade()){
                posicao = i;
            }

        }
        spinCidade.setSelection(posicao);
    }

}
