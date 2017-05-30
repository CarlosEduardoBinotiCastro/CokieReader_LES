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

import com.example.usuario.cookiereader.DAO.BiscoitoDAO;
import com.example.usuario.cookiereader.DAO.EmpresaDAO;
import com.example.usuario.cookiereader.R;
import com.example.usuario.cookiereader.database.DataBase;
import com.example.usuario.cookiereader.domain.Biscoito;
import com.example.usuario.cookiereader.domain.Empresa;
import com.example.usuario.cookiereader.domain.UF;

import java.util.List;

public class BiscoitoCadastro extends AppCompatActivity {

    private BiscoitoDAO biscoitoDAO;
    private DataBase dataBase;
    private SQLiteDatabase conn;
    private Biscoito biscoito;

    private Button btnInserir;
    private Button btnVer;
    private Button btnDeletar;
    private Button btnAlterar;

    private EditText editNome;
    private TextView editCd;

    private Button btn_back;
    private ListView listBiscoito;

    private Spinner spinEmpresa;
    List<Empresa> empresa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_biscoito_cadastro);

        dataBase = new DataBase(this);
        conn = dataBase.getWritableDatabase();
        biscoitoDAO = new BiscoitoDAO(conn);

        btnAlterar = (Button) findViewById(R.id.btnAlterar);
        btnVer = (Button) findViewById(R.id.btnVer);
        btnDeletar = (Button) findViewById(R.id.btnDeletar);
        btnInserir = (Button) findViewById(R.id.btnInserir);
        btn_back = (Button) findViewById(R.id.bnt_back);

        editCd =(TextView) findViewById(R.id.editCd);
        editNome = (EditText) findViewById(R.id.editNome);

        spinEmpresa = (Spinner) findViewById(R.id.spinEmpresa);
        carregarComboBox();

        listBiscoito = (ListView) findViewById(R.id.listBiscoito);
        listarBiscoitos();

        listBiscoito.setOnItemClickListener (new AdapterView.OnItemClickListener(){
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                biscoito = (Biscoito) listBiscoito.getItemAtPosition(position);
            }
        });
    }

    public void listarBiscoitos(){
        List<Biscoito> biscoitos;
        biscoitos = biscoitoDAO.BuscarTodos();
        ArrayAdapter<Biscoito> arrayAdapter = new ArrayAdapter<Biscoito>(this, android.R.layout.simple_list_item_checked, biscoitos);
        listBiscoito.setAdapter(arrayAdapter);
    }

    public void carregarComboBox(){
        EmpresaDAO empresaDAO = new EmpresaDAO(conn);
        empresa = empresaDAO.BuscarTodos();
        ArrayAdapter<Empresa> arrayAdapter = new ArrayAdapter<Empresa>(this, android.R.layout.simple_spinner_item, empresa);
        spinEmpresa.setAdapter(arrayAdapter);
    }

    public void sair(View view){
        conn.close();
        finish();
    }

    public void inserir(View view){
        Biscoito biscoito = new Biscoito();
        Empresa empresa;
        empresa = (Empresa) spinEmpresa.getSelectedItem();
        biscoito.setNome(editNome.getText().toString());
        biscoito.setCdEmpresa(empresa.getCdEmpresa());

        biscoitoDAO.inserir(biscoito);
        listarBiscoitos();
    }

    public void deletar(View view){
        biscoitoDAO.excluir(biscoito.getCdBiscoito());
        listarBiscoitos();
    }

    public void ver(View view){
        int posicao = 0;
        EmpresaDAO empresaDAO = new EmpresaDAO(conn);
        Empresa empresa;

        empresa = empresaDAO.buscar(biscoito.getCdEmpresa());
        editNome.setText(biscoito.getNome());
        editCd.setText(Integer.toString(biscoito.getCdBiscoito()));

        for(int i =0; i < this.empresa.size(); i++){
            if(this.empresa.get(i).getNome().equals(empresa.getNome())){
                posicao = i;
            }
        }

        spinEmpresa.setSelection(posicao);
    }

    public void alterar(View view){
        Empresa empresa;
        empresa = (Empresa) spinEmpresa.getSelectedItem();
        biscoito.setNome(editNome.getText().toString());
        biscoito.setCdBiscoito(empresa.getCdEmpresa());
        biscoitoDAO.alterar(biscoito);
        listarBiscoitos();
    }

    public void limpar(View view){
        editNome.setText(null);
        editCd.setText(null);
        spinEmpresa.setSelection(0);
    }

}
